package algs.example.gui.generator;

import java.awt.Frame;
import java.awt.TextArea;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import algs.model.IPoint;
import algs.model.data.Generator;
import algs.model.data.points.CircleGenerator;
import algs.model.data.points.UniformGenerator;
import algs.model.data.points.UnusualGenerator;

/**
 * Provides a quick-and-dirty way to test the {@link GeneratorPanel} by exporting
 * the generated artifacts into a text area.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class GeneratorPreviewer {
	public static void main(String[] args) {
		final Frame f = new Frame();
		f.setSize(800, 600);
		f.setLayout(null);

		f.addWindowListener (new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				f.setVisible(false);
				System.exit(0);
			}
		});
		
		GeneratorPanel<IPoint> gp = new GeneratorPanel<IPoint> ();

		Generator<IPoint> gen1 = new UniformGenerator();
		Generator<IPoint> gen2 = new UnusualGenerator(40);

		gp.addGenerator("Uniform", gen1);
		gp.addGenerator("Unusual", gen2);
		gp.addGenerator("Circle", new CircleGenerator(9));
		
		f.add(gp);
		

		final TextArea ta = new TextArea(40,80);
		ta.setBounds(gp.getWidth()+20, 40, f.getWidth() - gp.getWidth() - 30, f.getHeight()-80);
		f.add(ta);
		
		gp.register(new IGeneratorManager<IPoint>() {

			public void generate(IPoint[] items) {
				StringBuilder sb = new StringBuilder();
				for (IPoint p : items) {
					sb.append(p.toString());
					sb.append("\n");
				}
				ta.setText(sb.toString());
			}
			
		});
		
		f.setVisible (true);
	}
}
