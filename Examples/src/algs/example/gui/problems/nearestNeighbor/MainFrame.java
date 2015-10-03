package algs.example.gui.problems.nearestNeighbor;

import javax.swing.JFrame;

import algs.example.gui.canvas.ElementCanvas;
import algs.example.gui.canvas.KDTreeDecorator;
import algs.example.gui.canvas.NopDrawer;
import algs.example.gui.generator.GeneratorPanel;
import algs.example.gui.generator.IGeneratorManager;
import algs.example.gui.generator.IOutput;
import algs.example.gui.model.IModelUpdated;
import algs.example.gui.problems.nearestNeighbor.controller.MouseHandler;
import algs.example.gui.problems.nearestNeighbor.model.Model;
import algs.model.FloatingPoint;
import algs.model.IMultiPoint;
import algs.model.data.Generator;
import algs.model.data.nd.ConvertToND;
import algs.model.data.points.CircleGenerator;
import algs.model.data.points.HorizontalLineGenerator;
import algs.model.data.points.UnusualGenerator;
import algs.model.data.points.VerticalLineGenerator;
import algs.model.data.nd.UniformGenerator;
import algs.model.nd.Hyperpoint;
import algs.model.twod.TwoDPoint;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.Checkbox;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Primary window within which the application executes.
 *  
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */

public class MainFrame extends JFrame implements IOutput, IModelUpdated<IMultiPoint> {

	/** Keep eclipse happy. */
	private static final long serialVersionUID = 1L;
	
	/** Model of the information. */
	Model model;
	
	/**
	 * To enable scaling effortlessly, we maintain a copy of the generated
	 * items outside of the model.
	 */
	private IMultiPoint[] nativeItems;
	
	/** Primary drawing field. */
	MultiPointCanvas canvas;
	
	/** Decorate with kd-tree. Can be disabled. */
	KDTreeDecorator kdtreeDecorator;
	
	/** Where output is to be shown. */
	TextArea output;
	private Panel panel = null;
	private Checkbox showKDtree = null;

	/** Panel that shows algorithmic selections. */
	private Panel stylePanel = null;
	
	/** When points are generated, you may need to scale them to fit drawing region. */
	private Checkbox scaleCheckbox;
	
	/**
	 * This is the default constructor
	 */
	public MainFrame() {
		super();
		initialize();
	}

	// brute force search for all points.
	public TwoDPoint findNearest(final IMultiPoint pt) {
		double smallest = Double.POSITIVE_INFINITY;
		IMultiPoint result = null;
		
		// for all points in this tree, compute smallest distance. Once this is done,
		// all points within this region will have been checked for smallest distance, and
		// result[0] will contain the lucky neighbor (so far) which is determined to have
		// the distance smallest[0].
		for (IMultiPoint p : model.items()) {
			double d = p.distance(pt);
			if (FloatingPoint.lesser (d, smallest)) {
				smallest = d;
				result = p;
			}
		}
		
		return (TwoDPoint) result;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		
		model = new Model();
		model.setListener(this);
		
		this.setSize(850, 700);
		this.setContentPane(getPanel());
		this.setTitle("Algorithms in a Nutshell: Nearest Neighbor Example");
	}

	public void setCanvasAdapter( MultiPointCanvas canvas) {
		MouseHandler<IMultiPoint> handler = new MouseHandler<IMultiPoint> (canvas, this, model);
		
		canvas.addMouseListener(handler);
		canvas.addMouseMotionListener(handler);
	}

	/**
	 * This method initializes panel	
	 * 	
	 * @return java.awt.Panel	
	 */
	private Panel getPanel() {
		if (panel == null) {
			
			// note that these are all IPoint generators.
			GeneratorPanel<IMultiPoint> gp = new GeneratorPanel<IMultiPoint>();

			Generator<IMultiPoint> gen1 = new ConvertToND(new CircleGenerator(1));  // dummy argument
			Generator<IMultiPoint> gen2 = new UniformGenerator(2,100);  // dummy argument
			Generator<IMultiPoint> gen3 = new ConvertToND(new VerticalLineGenerator(99));
			Generator<IMultiPoint> gen4 = new ConvertToND(new HorizontalLineGenerator(99));
			Generator<IMultiPoint> gen5 = new ConvertToND(new UnusualGenerator(2.0));
			
			// order in this way...
			gp.addGenerator("Unusual Generator", gen5);
			gp.addGenerator("Horizontal Line", gen4);
			gp.addGenerator("Vertical Line", gen3);
			gp.addGenerator("Circle", gen1);
			gp.addGenerator("Uniform", gen2);
			
			// when generated items come out, refresh objects...
			gp.register(new IGeneratorManager<IMultiPoint>() {

				public void generate(IMultiPoint[] items) {
					updateDisplay(items);
				}
			});
			
			// for output...
			gp.register(this);
			
			panel = new Panel();
			panel.setSize(1050,600);

			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 0;
			gridBagConstraints11.gridwidth = 3;
			gridBagConstraints11.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints11.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints11.gridy = 2;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 2;
			gridBagConstraints2.gridheight = 2;
			gridBagConstraints2.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints2.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints2.gridy = 0;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;

			gridBagConstraints1.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints1.gridy = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints.gridy = 0;

			panel.setLayout(new GridBagLayout());
			panel.add(gp, gridBagConstraints);
			panel.add(getStylePanel(), gridBagConstraints1);
			panel.add(createCanvas(500,500), gridBagConstraints2);
			panel.add(getOutput(), gridBagConstraints11);

//			panel.add(getShowKDtree(), null);
		}
		return panel;
	}

	/**
	 * Returns the output text area.	
	 */ 	
	private TextArea getOutput() {
		if (output == null) {
			output = new TextArea (6,100);
			output.setSize(580,120);
			output.setEditable(false);
		}
		
		return output;
	}
	
	protected ElementCanvas<IMultiPoint> createCanvas(int width, int height) {
		canvas = new MultiPointCanvas();
		canvas.setSize(width,height);
		
		kdtreeDecorator = new KDTreeDecorator(new NopDrawer(), canvas, model);
		NearestPointDecorator npd = new NearestPointDecorator (kdtreeDecorator, canvas, model);
		
		canvas.setDrawer (npd);
		canvas.setModel(model);	
		
		// we want active points as well as intersections...
		//canvas.setDrawer(...)
		
		// install handlers
		MouseHandler<IMultiPoint> mh = new MouseHandler<IMultiPoint>(canvas, this, model);
		
		canvas.addMouseMotionListener(mh);
		return canvas;
	}

	protected ElementCanvas<IMultiPoint> getCanvas() {
		return canvas;
	}
	
	private Panel getStylePanel() {
		if (stylePanel == null) {
			stylePanel = new Panel ();
			stylePanel.setLayout(new GridLayout(2,2));
			stylePanel.setSize (140,100);
			stylePanel.add (getScaleCheckbox());
			stylePanel.add (new Label(""));  // dummy for layout
			
			stylePanel.add (getShowKDtree());
		}
		
		return stylePanel;
	}
	
	/**
	 * This method initializes showKDtree	
	 * 	
	 * @return java.awt.Checkbox	
	 */
	private Checkbox getShowKDtree() {
		if (showKDtree == null) {
			showKDtree = new Checkbox();
			showKDtree.setBounds(new Rectangle(13, 187, 130, 23));
			showKDtree.setLabel("Show KD Tree");
			showKDtree.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					kdtreeDecorator.setVisible (showKDtree.getState());
					getCanvas().redrawState();
					getCanvas().repaint();
				}
			});
		}
		return showKDtree;
	}


	public boolean shouldScale () {
		return scaleCheckbox.getState();
	}
	
	/**
	 * When scale is selected, auto refresh objects.
	 */
	private Checkbox getScaleCheckbox() {
		if (scaleCheckbox == null) {
			scaleCheckbox = new Checkbox("Scale Points");
			scaleCheckbox.setSize(40, 20);
			
			scaleCheckbox.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					updateDisplay(nativeItems);
				}
				
				
			});
		}
		
		return scaleCheckbox;
	}
	
	/** Redisplay if possible, based upon user input. */
	public void updateDisplay(IMultiPoint[] items) {
		if (items == null) { return; } 
		model.setItems(items);
		
		// transform into AWT coordinates
		int width = getCanvas().getWidth();
		int height = getCanvas().getHeight();
			
		// create set of entities proportional to the image
		nativeItems = items;
		items = transform(items, width, height);
		
		model.setItems(items);

		// force redraw since state has changed. And then repaint
		getCanvas().redrawState();
		getCanvas().repaint();
	}

	/** How to transform line segments. */
	protected IMultiPoint[] transform(IMultiPoint[] points,
			int width, int height) {
		double minX = 0;
		double minY = 0;
		double maxX = 0;
		double maxY = 0;

		for (IMultiPoint mp: points) {
			double x1 = mp.getCoordinate(1);
			double y1 = mp.getCoordinate(2);

			if (x1 < minX) { minX = x1; }
			if (y1 < minY) { minY = y1; }
			if (x1 > maxX) { maxX = x1; }
			if (y1 > maxY) { maxY = y1; }
		}

		double xFactor = 1.0 * width / (maxX - minX);
		double yFactor = 1.0 * height / (maxY - minY);

		// Does user want to scale?
		if (!shouldScale()) {
			IMultiPoint[] copy = new IMultiPoint[points.length];
			for (int i = 0; i < copy.length; i++) {
				copy[i] = points[i];
			}
			return copy;
		}

		// Scale appropriately
		double[] vals = new double[2];
		IMultiPoint []retVal = new IMultiPoint[points.length];
		int idx = 0;
		for (IMultiPoint mp : points) {
			vals[0] = (mp.getCoordinate(1)-minX)*xFactor;
			vals[1] = (mp.getCoordinate(2)-minY)*yFactor;
			
			retVal[idx++] = new Hyperpoint (vals);
		}

		// convert as array
		return retVal;
	}

	
	public void error(String s) {
		output.append(s + "\n");
		output.setBackground(new Color (220, 0, 0));
	}

	public void message(String s) {
		output.append(s + "\n");
		output.setBackground(Color.white);
	}
	
	/**
	 * React to changes in model by requesting new intersection of the
	 * model.
	 * 
	 * @param model
	 */
	public void modelUpdated(algs.example.gui.model.Model<IMultiPoint> model) {
		
		getCanvas().redrawState();
		getCanvas().repaint();
	}
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
