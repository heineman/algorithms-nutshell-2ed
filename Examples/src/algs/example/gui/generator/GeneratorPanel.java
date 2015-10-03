package algs.example.gui.generator;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import algs.model.data.Generator;

/**
 * Panel that can be customized to enable the user to specify the kind of 
 * generator to use from the default set. Also, enables the use of reflection
 * if the user has constructed a more powerful generator of their own.
 * 
 * @param <E>    The type of entity produced by the generator.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class GeneratorPanel<E> extends Panel {

	/** pre-installed generators. */
	ArrayList<Generator<E>> generators = new ArrayList<Generator<E>>();
	
	/** Store generator classes here. Key = user string. Value = the class
	 * which ultimately extends Generator<E>. */ 
	Hashtable<String,Generator<E>> map = new Hashtable<String,Generator<E>>();
	
	/** Default generator to use. */
	Generator<E> defaultGenerator = null;
	
	/** Default output; can be overridden. */
	IOutput out = new IOutput() {

		public void error(String s) {
			System.err.println(s + "\n");
		}

		public void message(String s) {
			System.out.println(s + "\n");
		}
	};
	
	/**
	 * to stop eclipse from complaining.
	 */
	private static final long serialVersionUID = 1L;

	private Label paramLabel = null;
	private Choice currentChoice = null;
	private Label choiceLabel = null;
	private Button generateButton = null;
	private Label genLabel = null;
	private Label numLabel = null;
	private TextField numField = null;
	private Panel parametersPanel = null;
	private Label label1 = null;
	private TextField textField1 = null;
	private Label label2 = null;
	private TextField textField2 = null;
	private Label label3 = null;
	private TextField textField3 = null;
	private Label label4 = null;
	private TextField textField4 = null;
	private Label label5 = null;
	private TextField textField5 = null;
	private Label []labels;
	private TextField[]fields;

	
	
	/** Those interested in our events. */
	ArrayList<IGeneratorManager<E>> managers = new ArrayList<IGeneratorManager<E>>();
	
	/**
	 * Register those interested in events. 
	 * 
	 * @param m
	 */
	public void register(IGeneratorManager<E> m) {
		managers.add(m);
	}
	
	/**
	 * Register output interface
	 */
	public void register (IOutput out) {
		this.out = out;
	}
	
	/**
	 * When user selects a new generator, set up the parameters
	 * 
	 * @param e
	 */
	protected void updateChoice(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			String name = getCurrentChoice().getSelectedItem();
			select(name);
		}
	}

	/** Update parameters for this selection. */
	private void select(String name) {
		Generator<E> gen = map.get(name);
		String[] params = gen.parameters();
		
		// reset and make all labels/fields visible
		int i;
		for (i = 0; i < labels.length; i++) {
			labels[i].setText("Parameter " + (i+1) + ":");
			labels[i].setVisible(true);
			fields[i].setText("");
			fields[i].setVisible(true);
		}
		
		// take over text for the labels.
		for (i = 0 ; i < params.length; i++) {
			labels[i].setText(params[i]);
			fields[i].setEditable(true);
		}
		
		// make everything else invisible
		while (i < labels.length) {
			fields[i].setEditable(false);
			i++;
		}
	}
	
	/** 
	 * Generate the elements for the generator and emit to the managers.
	 */
	protected void generateElements() {
		String name = getCurrentChoice().getSelectedItem();
			
		Generator<E> gen = map.get(name);
		String[] params = gen.parameters();
		
		// num params:
		String []values = new String[labels.length];
		for (int i = 0; i < labels.length; i++) {
			if (labels[i].isVisible()) {
				values[i] = fields[i].getText();
			}
		} 
		
		// create generator anew.
		String []args = new String[params.length];
		System.arraycopy(values, 0, args, 0, params.length);
		Generator<E> realOne;
		
		try {
			realOne = gen.construct(args);		
		} catch (Exception e) {
			out.error("Unable to construct generator");
			out.error(e.getMessage());
			return;
		}
		
		try {
			int size = Integer.valueOf(getNumField().getText());
			
			E[] items = realOne.generate(size);
			// announce to everyone!
			for (IGeneratorManager<E> mgr: managers) {
				mgr.generate(items);
			}
		} catch (NumberFormatException e) {
			java.awt.Toolkit.getDefaultToolkit().beep();
			
			out.error(getNumField().getText() + " is not a number!");
		}
	}
	
	/**
	 * This is the default constructor
	 */
	public GeneratorPanel() {
		super();
		initialize();
	}
	
	/**
	 * Initialize the proper real estate for this GUI element.
	 * 
	 * Top part shows choice from which you can choose a generator. The default
	 * one (if set) is selected; otherwise the first one added is selected.
	 * There is a scrollable region within which potential parameters for the
	 * generator may exist.
	 * 
	 */
	private void initialize() {
		numLabel = new Label();
		numLabel.setText("Generate how many:");
		numLabel.setLocation(new Point(22, 300));
		numLabel.setSize(new Dimension(122, 23));
		genLabel = new Label();
		genLabel.setText("Execute Generator:");
		genLabel.setLocation(new Point(10, 270));
		genLabel.setSize(new Dimension(220, 23));
		choiceLabel = new Label();
		choiceLabel.setText("Selected Generator:");
		choiceLabel.setLocation(new Point(10, 25));
		choiceLabel.setSize(new Dimension(220, 23));
		paramLabel = new Label();
		paramLabel.setText("Construct Generator with parameters:");
		paramLabel.setLocation(new Point(10, 75));
		paramLabel.setSize(new Dimension(220, 24));
		
		this.setPreferredSize(new Dimension(239, 350));
		this.setLayout(null);
		this.setBackground(SystemColor.control);
		this.add(paramLabel, null);
		this.add(getCurrentChoice(), null);
		this.add(choiceLabel, null);
		this.add(getGenerateButton(), null);
		this.add(genLabel, null);
		this.add(numLabel, null);
		this.add(getNumField(), null);
		this.add(getParametersPanel(), null);
	}

	/**
	 * This method initializes currentChoice	
	 * 	
	 * @return java.awt.Choice	
	 */
	private Choice getCurrentChoice() {
		if (currentChoice == null) {
			currentChoice = new Choice();
			currentChoice.setSize(new Dimension(220, 25));
			currentChoice.setLocation(new Point(10, 50));
			
			currentChoice.addItemListener(new ItemListener() {

				public void itemStateChanged(ItemEvent e) {
					updateChoice(e);
				}
			});
		}
		return currentChoice;
	}

	/**
	 * This method initializes generateButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getGenerateButton() {
		if (generateButton == null) {
			generateButton = new Button();
			generateButton.setLabel("Generate");
			generateButton.setLocation(new Point(82, 330));
			generateButton.setSize(new Dimension(82, 23));
			generateButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					generateElements();
				}
				
			});
		}
		return generateButton;
	}

	/**
	 * This method initializes numField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getNumField() {
		if (numField == null) {
			numField = new TextField();
			numField.setSize(new Dimension(58, 24));
			numField.setLocation(new Point(152, 300));
		}
		return numField;
	}

	/**
	 * This method initializes parametersPanel	
	 * 	
	 * @return java.awt.Panel	
	 */
	private Panel getParametersPanel() {
		if (parametersPanel == null) {
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.fill = GridBagConstraints.BOTH;
			gridBagConstraints6.gridy = 4;
			gridBagConstraints6.weightx = 1.0;
			gridBagConstraints6.gridx = 1;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints5.gridy = 4;
			label5 = new Label();
			label5.setText("Parameter 5:");
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.fill = GridBagConstraints.BOTH;
			gridBagConstraints4.gridy = 3;
			gridBagConstraints4.weightx = 1.0;
			gridBagConstraints4.ipady = 0;
			gridBagConstraints4.gridx = 1;
			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			gridBagConstraints31.gridx = 0;
			gridBagConstraints31.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints31.ipady = 0;
			gridBagConstraints31.gridy = 3;
			label4 = new Label();
			label4.setText("Parameter 4:");
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.fill = GridBagConstraints.BOTH;
			gridBagConstraints21.gridy = 2;
			gridBagConstraints21.weightx = 1.0;
			gridBagConstraints21.gridx = 1;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 0;
			gridBagConstraints11.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints11.gridy = 2;
			label3 = new Label();
			label3.setText("Parameter 3:");
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.weightx = 1.0;
			gridBagConstraints3.ipady = 0;
			gridBagConstraints3.gridx = 1;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.anchor = GridBagConstraints.NORTH;
			gridBagConstraints2.fill = GridBagConstraints.BOTH;
			gridBagConstraints2.ipady = 0;
			gridBagConstraints2.gridy = 1;
			label2 = new Label();
			label2.setText("Parameter 2:");
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.ipadx = 86;
			gridBagConstraints1.ipady = 0;
			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.gridx = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.ipadx = 65;
			gridBagConstraints.ipady = 0;
			gridBagConstraints.gridheight = 1;
			gridBagConstraints.gridy = 0;
			label1 = new Label();
			label1.setText("Parameter 1:");
			parametersPanel = new Panel();
			parametersPanel.setLayout(new GridBagLayout());
			parametersPanel.setSize(new Dimension(220, 144));
			parametersPanel.setLocation(new Point(10, 105));
			parametersPanel.add(label1, gridBagConstraints);
			parametersPanel.add(getTextField1(), gridBagConstraints1);
			parametersPanel.add(label2, gridBagConstraints2);
			parametersPanel.add(getTextField2(), gridBagConstraints3);
			parametersPanel.add(label3, gridBagConstraints11);
			parametersPanel.add(getTextField3(), gridBagConstraints21);
			parametersPanel.add(label4, gridBagConstraints31);
			parametersPanel.add(getTextField4(), gridBagConstraints4);
			parametersPanel.add(label5, gridBagConstraints5);
			parametersPanel.add(getTextField5(), gridBagConstraints6);
			
			labels = new Label[] { label1, label2, label3, label4, label5 };
			fields = new TextField[] { textField1,textField2,textField3,textField4,textField5};
		}
		return parametersPanel;
	}

	/**
	 * This method initializes textField1	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getTextField1() {
		if (textField1 == null) {
			textField1 = new TextField();
		}
		return textField1;
	}

	/**
	 * This method initializes textField2	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getTextField2() {
		if (textField2 == null) {
			textField2 = new TextField();
		}
		return textField2;
	}

	/**
	 * This method initializes textField3	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getTextField3() {
		if (textField3 == null) {
			textField3 = new TextField();
		}
		return textField3;
	}

	/**
	 * This method initializes textField4	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getTextField4() {
		if (textField4 == null) {
			textField4 = new TextField();
		}
		return textField4;
	}

	/**
	 * This method initializes textField5	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getTextField5() {
		if (textField5 == null) {
			textField5 = new TextField();
		}
		return textField5;
	}

	/**
	 * Add known generator to the pre-installed set of generators. 
	 *
	 * Must be called before GUI is drawn.
	 * 
	 * @param name   Name to associate with generator.
	 * @param g      Generator capable of producing entity of type <E>
	 */
	public void addGenerator (String name, Generator<E> g) {
		map.put(name, g);
		getCurrentChoice().add(name);
		
		// duplicate b/c during startup we need both...
		getCurrentChoice().select(name);
		select (name);
	}

	/** 
	 * Of all the generators added, make sure this one is the default.
	 * 
	 * Note that this generator must have previously been added.
	 * 
	 * @param name    Name of generator to use
	 * @return   <code>true</code> if success; <code>false</code> otherwise.
	 */
	public boolean setDefault (String name) {
		if (map.containsKey(name)) {
			getCurrentChoice().select(name);
			
			// update GUI.
			select (name);
			return true;
		}
		
		return false;
	}
	
	/**
	 * Expose the available generators as an iterator.
	 */
	public Iterator<Generator<E>> iterator () {
		return generators.iterator();
	}
}
