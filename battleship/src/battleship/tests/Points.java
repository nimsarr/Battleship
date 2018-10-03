package battleship.tests;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

//THIS IS COPY PASTED FROM ONLINE, NOT BY ME
// was just trying to figure out what Java graphics were
//nothing to do with battleship
class Surface extends JPanel {

	private void doDrawing(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.blue);

		Dimension size = getSize();
		Insets insets = getInsets();

		int w = size.width - insets.left - insets.right;
		int h = size.height - insets.top - insets.bottom;

		Random r = new Random();

		for (int i = 0; i < 1000; i++) {

			int x = Math.abs(r.nextInt()) % w;
			int y = Math.abs(r.nextInt()) % h;
			g2d.drawLine(x, y, x, y);
		}
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		doDrawing(g);
	}
}

public class Points extends JFrame implements Runnable{


	public Points() {
		setTitle("Points");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		add(new Surface());

		setSize(350, 250);
		setLocationRelativeTo(null);
	}

	public static void main(String[] args){ 
		Points ps = new Points();
		ps.setVisible(true);
	}
	
	public void run() {
		
	}
}
