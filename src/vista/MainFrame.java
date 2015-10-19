package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class MainFrame extends JFrame{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Canvas canvas;
	private Panel_controller controller;
	
	public MainFrame(String nameApp) {
		setTitle(nameApp);
		setSize(1200, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(  EXIT_ON_CLOSE );
		setMinimumSize(new Dimension(1000, 700));
		setLayout(new BorderLayout());
		canvas=new Canvas();
		controller=new Panel_controller();
		
		AddItems();
	}

	private void AddItems() {
		
		add(canvas, BorderLayout.CENTER);
		//add(controller, BorderLayout.SOUTH);
		
	}

	public void repaintCanvas() {
		canvas.repaint();
	}

	public void settextButtonPlay(String string) {
		controller.settextButtonPlay(string);
		
	}
}
