package algs.example.gui.problems.segmentIntersection;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;

import algs.example.gui.canvas.ElementCanvas;
import algs.example.gui.generator.GeneratorPanel;
import algs.example.gui.generator.IGeneratorManager;
import algs.example.gui.generator.IOutput;
import algs.example.gui.problems.segmentIntersection.model.Model;
import algs.example.gui.problems.segmentIntersection.view.IntersectionDecorator;
import algs.model.problems.segmentIntersection.BruteForceAlgorithm;
import algs.model.problems.segmentIntersection.LineSweep;
import algs.model.problems.segmentIntersection.IntersectionDetection;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.List;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Abstract class to provide framework for GUI that allows users to 
 * explore the intersection of various entities.
 * 
 * @param <E>   the type of entity being considered for intersections.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public abstract class IntersectingEntitiesGUI<E> extends JFrame implements IOutput {

	int styleIndex;
	final String[] styleListChoices = {"BruteForce", "LineSweep"};
	final IntersectionDetection[] algs = {
			new BruteForceAlgorithm(), 
			new LineSweep(),
	};
	protected String style = styleListChoices[0];
	
	/** Needed to keep Eclipse happy. */
	private static final long serialVersionUID = 1L;
	
	/** Contents of window. */
	private Panel panel = null;
	
	/** Output of objects shown graphically here. */
	protected ElementCanvas<E> cnv = null;
	
	/** Find reference to Intersection drawer here. */
	protected IntersectionDecorator<E> intersecter = null;
	
	/** When points are generated, you may need to scale them to fit drawing region. */
	private Checkbox scaleCheckbox;
	
	/** 
	 * Model of entities 
	 */
	Model<E> model;
	
	/**
	 * To enable scaling effortlessly, we maintain a copy of the generated
	 * items outside of the model.
	 */
	private E[] nativeItems;
	
	/** Panel that shows algorithmic selections. */
	private Panel stylePanel = null;
	
	/** Selection of algorithmic choices. */
	private List styleList = null;

	/** Keep local copy of the detection algorithm, to retrieve times. */
	private IntersectionDetection algorithm = algs[0];
	
	/** Output. */
	private TextArea output;
	
	/**
	 * Construct GUI but also make sure model is available
	 */
	public IntersectingEntitiesGUI() {
		super();
		
		constructModel();
		initialize();
	}

	/** Subclasses know what to do. */
	protected abstract void constructModel();
	
	/**
	 * Instantiate GUI with reasonable title.
	 */
	private void initialize() {
		this.setSize(850, 600);
		this.setContentPane(getPanel());
		this.setTitle("Algorithms in a Nutshell: Intersection Example for " + model.types());
	}
	
	/** Customize as you need to. */
	protected abstract void customize (GeneratorPanel<E> gp);

	/** Return appropriate chain of decorated canvases. */
	protected abstract ElementCanvas<E> createCanvas(int width, int height);
	protected abstract ElementCanvas<E> getCanvas();
	
	/**
	 * This method initializes panel	
	 * 	
	 * @return java.awt.Panel	
	 */
	private Panel getPanel() {
		if (panel == null) {
			// generator of entities
			final GeneratorPanel<E> gp = new GeneratorPanel<E> ();

			// let subclass determine generators
			customize(gp);
			
			// when generated items come out, refresh objects...
			gp.register(new IGeneratorManager<E>() {

				public void generate(E[] items) {
					updateDisplay(items);
				}
			});
			
			// for output...
			gp.register(this);
			
			panel = new Panel();
			GroupLayout layout = new GroupLayout(panel);
			panel.setLayout(layout);
			panel.setSize(850,500);

			ElementCanvas<E> canvas = createCanvas(400,400);
			layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).
					addGroup(layout.createSequentialGroup().
						addGroup(layout.createParallelGroup(Alignment.LEADING).
							addComponent(gp, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE).
							addComponent(getStylePanel(), GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)).
						addComponent(canvas, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)).
					addComponent(getOutput(), GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE));

			layout.setVerticalGroup(layout.createSequentialGroup().
					addGroup(layout.createParallelGroup(Alignment.LEADING).
						addGroup(layout.createSequentialGroup().
								addComponent(gp, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE).
								addComponent(getStylePanel(), GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)).
						addComponent(canvas, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)).
					addComponent(getOutput(), GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE));
			
		}
		return panel;
	}

	// Properly refresh display with items.
	private void updateDisplay(E[] items) {
		// nothing to do.
		if (items == null) { return; }
		
		// transform into AWT coordinates
		int width = getCanvas().getWidth();
		int height = getCanvas().getHeight();
			
		// create set of entities proportional to the image
		nativeItems = items;
		items = transform(items, width, height);
		
		model.setItems(items);
		model.setDynamicEntity(null);  // clear away...
		refreshObjects(); 
		
		// force redraw since state has changed...
		getCanvas().redrawState();
	}

	/**
	 * Returns the output text area.	
	 */ 	
	private TextArea getOutput() {
		if (output == null) {
			output = new TextArea (6,100);
			output.append ("Output Goes Here...");
			output.setPreferredSize(new Dimension(580,120));
			output.setEditable(false);
		}
		
		return output;
	}
	
	private Panel getStylePanel() {
		if (stylePanel == null) {
			stylePanel = new Panel ();
			stylePanel.setLayout(new GridLayout(2,2));
			stylePanel.setPreferredSize (new Dimension(239, 100));
			stylePanel.add (getScaleCheckbox());
			stylePanel.add (new Label(""));  // dummy for layout
			
			stylePanel.add (new Label("Select Algorithm:"));
			stylePanel.add (getStyleList());
		}
		
		return stylePanel;
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
	
	/**
	 * This method initializes styleList	
	 */
	private List getStyleList() {
		if (styleList == null) {
			styleList = new List(styleListChoices.length);
			for (String s : styleListChoices) {
				styleList.add(s);
			}
			
			styleList.setPreferredSize(new Dimension(97, 117));
			styleList.select(0);
			styleList.addItemListener(new ItemListener() {

				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						styleIndex = styleList.getSelectedIndex();
						if (model.items() != null) {
							algorithm = algs[styleIndex];
							refreshObjects();
						}
					}
				}
			});
		}
		return styleList;
	}
	
	/** subclass knows how to transform (if necessary) entities to fit */
	protected abstract E[] transform(E[] segments, int width, int height);
	
	/**
	 * If there is a line being dragged (ActiveLineSegmentDecorator) we want
	 * to add that line to the objects b
	 */
	protected void refreshObjects() {
		if (model.items() == null) { error("Invalid Model!"); return; }
		
		message(model.items().length + " entities constructed");

		int numK = model.computeIntersections(algorithm);
		
		message("Number of intersections:" + numK);
		message("Time to compute:" + algorithm.time());
		
		getCanvas().redrawState();
		getCanvas().repaint();
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
	public void modelUpdated(algs.example.gui.model.Model<E> model) {
		Model<E> mod = (Model<E>) model;
		int numK = mod.intersections().size();
		
		message("Number of intersections:" + numK);
		message("Time to compute:" + mod.algorithmTime());
		
		getCanvas().redrawState();
		getCanvas().repaint();
	}
}
