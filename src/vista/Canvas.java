package vista;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import controlador.Aplicativo;

public class Canvas extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image a;
	
	public Canvas() {
		a=new ImageIcon("src/img/mapa.png").getImage();
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(Aplicativo.get().isPlay()){
					Aplicativo.get().settextButtonPlay("Play");
					Aplicativo.get().setPlay(false);
					Aplicativo.get().stop();
				}
				else{
					Aplicativo.get().settextButtonPlay("Stop");
					Aplicativo.get().setPlay(true);
					Aplicativo.get().play();
				}
				Aplicativo.get().repaintCanvas();
			}
		});
	}

	@Override
	public void paint(Graphics g) {
		
		super.paint(g);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(a,0,0, getWidth(), getHeight(), this);
		
		/**
		g.setColor(Color.GREEN);
		
		for(i=0; i<102; i++){
			for(j=0; j<63;j++){
				g.drawRect(i*getWidth()/102, j*getHeight()/63, getWidth()/102, getHeight()/63);
			}
		}*/
		//Paint
		Aplicativo.get().paint(g, getWidth(), getHeight());
		
		//1020-630
	}
}
