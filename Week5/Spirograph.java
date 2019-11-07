
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Spirograph extends JPanel{
	static double timer = 0;
	static JFrame frame = new JFrame("Spirograph");
	
	/**
	 * main method sets up gui and
	 * start painting the graphs
	 * @param args
	 */
	public static void main(String[] args) {
		Spirograph game = new Spirograph();
		frame.add(game);
		frame.setSize(1920, 1080);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setVisible(true);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		Graphics g = game.getGraphics();
		game.paint(g);


	}	
	/**
	 * generates a random number between 1 and
	 * a number
	 * @param max max possible number
	 * @return the number generated
	 */
	public double rand(int max) {
		Random r = new Random();
		return (r.nextInt(max * 100) + 1) / 100;
	}
	/**
	 * paints the spirograph
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		boolean drawing = true;
		int numberOfPoints = 4;
		GetPoint[] points = new GetPoint[numberOfPoints];
		

		for (int i = 0; i < numberOfPoints; i++) {
			points[i] = new GetPoint(rand(11),rand(9),rand(9),
					Color.BLACK,600,400);
		}
		
		while (drawing) {
			timer += 5;
			double t = Math.toRadians(timer);

			for (int i = 0; i < numberOfPoints; i++) {
				points[i].doNext(t,g);
			}
			
			try {
				Thread.sleep(40);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
			

}





