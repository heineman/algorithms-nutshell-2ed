package algs.blog.graph.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import algs.blog.graph.gui.controller.DealController;
import algs.blog.graph.gui.view.CardImages;
import algs.blog.graph.gui.view.CardImagesLoader;
import algs.blog.graph.gui.view.FreeCellDrawing;
import algs.model.searchtree.IMove;

/**
 * Show a full solution to a board graphically.
 * 
 * @author George Heineman
 */
public class Solver {
	
	/** Card images. */
	public static CardImages cardImages;
	
	public static void main(String[] args) throws Exception {
		
		// solution found. Create GUI. 
		final JFrame frame = new JFrame();
		frame.setAlwaysOnTop(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {

			/** Once opened: load up the images. */
			public void windowOpened(WindowEvent e) {
				System.out.println("Loading card images...");
				cardImages = CardImagesLoader.getDeck(e.getWindow());
			}
		});
		
		frame.setSize(808,350);
		JList<IMove> list = new JList<IMove>();
		
	    // add widgets at proper location
	    frame.setLayout(null);
	    
	    // top row:
	    JPanel topLeft = new JPanel();
	    topLeft.setBounds(0, 0, 400, 40);
	    topLeft.add(new JLabel("Select Game:"));
	    final JTextField jtf = new JTextField (7);
	    topLeft.add(jtf);
	    frame.add(topLeft);
	    
	    JPanel topRight = new JPanel();
	    topRight.setBounds(400, 0, 400, 40);
	    String instructions = "Select moves from below list to see game state at that moment.";
	    topRight.add(new JLabel(instructions));
	    frame.add(topRight);
	    
	    // bottom row
	    FreeCellDrawing drawer = new FreeCellDrawing();
	    drawer.setBounds (0, 40, 400, 275);
	    drawer.setBackground(new java.awt.Color (0,128,0));
	    frame.add(drawer);
	    
	    // Create the GUI and put it in the window with scrollbars.
		JScrollPane scrollingPane = new JScrollPane(list);
	    scrollingPane.setAutoscrolls(true);
	    scrollingPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    scrollingPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
	    
	    scrollingPane.setBounds(400, 40, 400, 275);
	    frame.add(scrollingPane);
	   
	    // set up listeners and show everything
	    jtf.addActionListener(new DealController(frame, drawer, list));	    
	    frame.setVisible(true);
	}
}
