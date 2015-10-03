package algs.example.gui.problems.rangeQuery;

import javax.swing.JFrame;

import algs.example.gui.canvas.DrawingDecorator;
import algs.example.gui.canvas.ElementCanvas;
import algs.example.gui.canvas.KDTreeDecorator;
import algs.example.gui.canvas.NopDrawer;
import algs.example.gui.canvas.RectangleDecorator;
import algs.example.gui.generator.GeneratorPanel;
import algs.example.gui.generator.IGeneratorManager;
import algs.example.gui.generator.IOutput;
import algs.example.gui.model.IModelUpdated;
import algs.example.gui.problems.rangeQuery.controller.MouseHandler;
import algs.example.gui.problems.rangeQuery.model.Model;
import algs.example.gui.problems.rangeQuery.model.SelectableMultiPoint;
import algs.model.IMultiPoint;
import algs.model.IRectangle;
import algs.model.data.Generator;
import algs.model.data.nd.ConvertToND;
import algs.model.data.points.CircleGenerator;
import algs.model.data.points.HorizontalLineGenerator;
import algs.model.data.points.UnusualGenerator;
import algs.model.data.points.VerticalLineGenerator;
import algs.model.data.nd.UniformGenerator;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.Checkbox;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Main Frame for range Query application.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class MainFrame extends JFrame implements IOutput, IModelUpdated<SelectableMultiPoint> {

	/** Keep eclipse happy. */
	private static final long serialVersionUID = 1L;
	
	/** Model of the information. */
	algs.example.gui.problems.rangeQuery.model.Model<SelectableMultiPoint> model;
	
	/**
	 * To enable scaling effortlessly, we maintain a copy of the generated
	 * items outside of the model.
	 */
	private SelectableMultiPoint[] nativeItems;
	
	/** 
	 * Primary drawing field. Because we are reusing code that uses both 
	 * IMultiPoint and SelectableMultiPoint we have to remove the parameterization
	 * of this class; that is why we suppress warnings.
	 */
	@SuppressWarnings("rawtypes")
	ElementCanvas canvas;
	
	/** Will need access to enable/disable KD tree. */
	KDTreeDecorator kdtreeDecorator;
	
	/** Where output is to be shown. */
	TextArea output;
	private Panel panel = null;
	private Checkbox showKDtree = null;
	private Checkbox interactiveCheckbox = null;

	/** Panel that shows algorithmic selections. */
	private Panel stylePanel = null;
	
	/** When points are generated, you may need to scale them to fit drawing region. */
	private Checkbox scaleCheckbox;
	
	/** Should the KD tree be balanced? */
	private Checkbox balancedCheckbox;
	
	int styleIndex;
	final String[] styleListChoices = {"BruteForce", "KDTree"};
	final int bruteForceChoice= 0;
	final int kdtreeChoice = 1;
	
	protected String style = styleListChoices[0];

	/** The range query algorithm to use. */
	IRangeQuery<SelectableMultiPoint> rangeQueryAlgorithm = null;
	
	/** Selection of algorithmic choices. */
	private List styleList = null;
	
	/**
	 * This is the default constructor
	 */
	public MainFrame() {
		super();
		initialize();
	}	

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		
		model = new Model<SelectableMultiPoint>();
		model.setListener(this);
		
		// start with brute force
		rangeQueryAlgorithm = new BruteForceRangeQuery(model);
		
		this.setSize(800, 720);
		this.setContentPane(getPanel());
		this.setTitle("Algorithms in a Nutshell: Range Query Example");
	}

	/**
	 * This method initializes panel	
	 * 	
	 * @return java.awt.Panel	
	 */
	private Panel getPanel() {
		if (panel == null) {
			
			// note that these are all IPoint generators.
			GeneratorPanel<SelectableMultiPoint> gp = new GeneratorPanel<SelectableMultiPoint>();

			Generator<SelectableMultiPoint> gen1 = new ConvertToSelectable(new ConvertToND (new CircleGenerator(1)));  // dummy argument
			Generator<SelectableMultiPoint> gen2 = new ConvertToSelectable(new UniformGenerator(2,100));  // dummy argument
			Generator<SelectableMultiPoint> gen3 = new ConvertToSelectable(new ConvertToND(new VerticalLineGenerator(99)));
			Generator<SelectableMultiPoint> gen4 = new ConvertToSelectable(new ConvertToND(new HorizontalLineGenerator(99)));
			Generator<SelectableMultiPoint> gen5 = new ConvertToSelectable(new ConvertToND(new UnusualGenerator(2.0)));
			
			// order in this way...
			gp.addGenerator("Unusual Generator", gen5);
			gp.addGenerator("Horizontal Line", gen4);
			gp.addGenerator("Vertical Line", gen3);
			gp.addGenerator("Circle", gen1);
			gp.addGenerator("Uniform", gen2);
			
			// when generated items come out, refresh objects...
			gp.register(new IGeneratorManager<SelectableMultiPoint>() {

				public void generate(SelectableMultiPoint[] items) {
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

			// Initially not able to be selected
			getShowKDtree().setEnabled(false);
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
	
	/**
	 * This method initializes styleList	
	 */
	private List getStyleList() {
		if (styleList == null) {
			styleList = new List(styleListChoices.length);
			for (String s : styleListChoices) {
				styleList.add(s);
			}
			
			styleList.setSize(97, 117);
			styleList.select(0);
			styleList.addItemListener(new ItemListener() {

				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						styleIndex = styleList.getSelectedIndex();
						
						// enable visibility if we are in KD tree.
						getShowKDtree().setEnabled(styleIndex == kdtreeChoice);
						getBalanced().setEnabled(styleIndex == kdtreeChoice);
						
						if (styleIndex == bruteForceChoice) {
							rangeQueryAlgorithm = new BruteForceRangeQuery(model);
							getShowKDtree().setState(false);  // set to false also.
						} else {
							rangeQueryAlgorithm = new KDRangeQuery(model);
						}

						// properly configure KDtree decorator.
						kdtreeDecorator.setVisible (showKDtree.getState());
						
						// clear old query and any selections...
						model.setActiveRectangle(null);
						model.deselectAll();
						
						// force redraw since state has changed. And then repaint
						getCanvas().redrawState();
						getCanvas().repaint();
					}
				}
			});
		}
		return styleList;
	}
	
	private Panel getStylePanel() {
		if (stylePanel == null) {
			stylePanel = new Panel ();
			stylePanel.setLayout(new GridLayout(5,2));
			stylePanel.setSize (120,100);

			stylePanel.add (new Label("Select Algorithm:"));
			stylePanel.add (getStyleList());
			
			stylePanel.add (getScaleCheckbox());
			stylePanel.add (new Label(""));  // dummy for layout
			
			stylePanel.add (getShowKDtree());
			stylePanel.add (new Label(""));  // dummy for layout
			
			stylePanel.add (getBalanced());
			stylePanel.add (new Label(""));  // dummy for layout
			
			stylePanel.add (getInteractive());
			stylePanel.add (new Label(""));  // dummy for layout
			
		}
		
		return stylePanel;
	}
	
	/**
	 * Suppress warnings on this method because of the design issue we 
	 * faced, namely, having a Model based on trying to reuse code written
	 * for IMultiPoint and new code written for SelectableMultiPoint.
	 * 
	 * @param width    Desired width of canvas
	 * @param height   Desired height of canvas
	 */
	@SuppressWarnings("unchecked")
	protected ElementCanvas<SelectableMultiPoint> createCanvas(int width, int height) {
		canvas = new SelectablePointCanvas();
		canvas.setSize(width,height);
		
		// install rectangle decorator to track mouse drags.
		//DrawingDecorator chain = new RectangleDecorator(new NopDrawer(), canvas, model);
		//kdtreeDecorator = new KDTreeDecorator(chain, canvas, model);
		
		kdtreeDecorator = new KDTreeDecorator(new NopDrawer(), canvas, model);
		DrawingDecorator chain = new RectangleDecorator(kdtreeDecorator, canvas, model);
		
		canvas.setDrawer (chain);
		canvas.setModel(model);	
		
		// we want active points as well as intersections...
		//canvas.setDrawer(...)
		
		// install handlers (was <SelectableMultiPoint>) 
		MouseHandler<SelectableMultiPoint> mh = 
			new MouseHandler<SelectableMultiPoint>(this, canvas, this, model);
		
		canvas.addMouseMotionListener((java.awt.event.MouseMotionListener) mh);
		canvas.addMouseListener(mh);
		return canvas;
	}

	@SuppressWarnings("unchecked")
	protected ElementCanvas<SelectableMultiPoint> getCanvas() {
		return canvas;
	}
	
	/**
	 * This method initializes interactive choice	
	 * 	
	 * @return java.awt.Checkbox	
	 */
	private Checkbox getInteractive() {
		if (interactiveCheckbox == null) {
			interactiveCheckbox = new Checkbox();
			interactiveCheckbox.setSize(40, 20);
			interactiveCheckbox.setLabel("Interactive");
			interactiveCheckbox.setState(true);
			
			// hook in the model.
			interactiveCheckbox.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (interactiveCheckbox.getState()) {
						model.setListener(MainFrame.this);
					} else {
						model.setListener(null);
					}
					getCanvas().redrawState();
					getCanvas().repaint();
				}
			});
		}
		
		return interactiveCheckbox;
	}
	
	/**
	 * This method initializes balanced checkbox	
	 * 	
	 * @return java.awt.Checkbox	
	 */
	private Checkbox getBalanced() {
		if (balancedCheckbox == null) {
			balancedCheckbox = new Checkbox();
			balancedCheckbox.setSize(40, 20);
			balancedCheckbox.setLabel("Balanced KD Tree");
			balancedCheckbox.setState(true);
			
			// hook in the model.
			balancedCheckbox.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					model.setBalanced(balancedCheckbox.getState());
					
					// clear relevant items.
					model.deselectAll();
					model.setActiveRectangle(null);
					
					// reset everything with same data...
					updateDisplay(nativeItems);
					
					getCanvas().redrawState();
					getCanvas().repaint();
				}
			});
		}
		
		return balancedCheckbox;
	}
	
	/**
	 * This method initializes showKDtree	
	 * 	
	 * @return java.awt.Checkbox	
	 */
	private Checkbox getShowKDtree() {
		if (showKDtree == null) {
			showKDtree = new Checkbox();
			showKDtree.setSize(40, 20);
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
	public void updateDisplay(SelectableMultiPoint[] items) {
		if (items == null) { return; }
		
		// transform into AWT coordinates
		int width = getCanvas().getWidth();
		int height = getCanvas().getHeight();
			
		// create set of entities proportional to the image
		nativeItems = items;
		items = transform(items, width, height);

		// new items
		model.setItems(items);

		// clear old query
		model.setActiveRectangle(null);
		
		// reset the query algorithm.
		if (styleIndex == bruteForceChoice) {
			rangeQueryAlgorithm = new BruteForceRangeQuery(model);
			getShowKDtree().setState(false);  // set to false also.
		} else {
			rangeQueryAlgorithm = new KDRangeQuery(model);
		}
		
		// force redraw since state has changed. And then repaint
		getCanvas().redrawState();
		getCanvas().repaint();
	}

	/** How to transform points. */
	protected SelectableMultiPoint[] transform(SelectableMultiPoint[] points,
			int width, int height) {
		double minX = 0;
		double minY = 0;
		double maxX = 0;
		double maxY = 0;

		for (SelectableMultiPoint mp: points) {
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
			SelectableMultiPoint[] copy = new SelectableMultiPoint[points.length];
			for (int i = 0; i < copy.length; i++) {
				copy[i] = points[i];
			}
			return copy;
		}

		// Scale appropriately
		SelectableMultiPoint []retVal = new SelectableMultiPoint[points.length];
		int idx = 0;
		for (IMultiPoint mp : points) {
			double px = (mp.getCoordinate(1)-minX)*xFactor;
			double py = (mp.getCoordinate(2)-minY)*yFactor;
			
			retVal[idx++] = new SelectableMultiPoint ((int) px, (int) py);
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
	 * @param m    The model that was updated.
	 */
	public void modelUpdated(algs.example.gui.model.Model<SelectableMultiPoint> m) {
		 
		// reprocess the query.
		IRectangle query = model.getActiveRectangle();

		if (query != null) {
			int numFound = rangeQueryAlgorithm.compute(query);
			message (numFound + " point(s) found in " + rangeQueryAlgorithm.time());
		}
		
		getCanvas().redrawState();
		getCanvas().repaint();
	}
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
