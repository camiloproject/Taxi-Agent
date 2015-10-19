package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controlador.Aplicativo;

public class Panel_controller extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton play;
	private JSlider slider;
	private JLabel label;
	public Panel_controller() {
		play =new JButton("Play");
		slider=new JSlider(0, 30, 12);
		label= new JLabel("  Passenger for Min: 12");
		
		addItems();
		addEvents();
		slider.setMinorTickSpacing(5);
		//slider.setMajorTickSpacing(10);
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		slider.setPaintTrack(true);
	}

	private void addEvents() {
		play.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(Aplicativo.get().isPlay()){
					play.setText("Play");
					Aplicativo.get().setPlay(false);
					Aplicativo.get().stop();
				}
				else{
					play.setText("Stop");
					Aplicativo.get().setPlay(true);
					Aplicativo.get().play();
				}
				Aplicativo.get().repaintCanvas();
			}
		});
		slider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				Aplicativo.get().chageVelCreatePasa(60000/slider.getValue());
				label.setText("  Passenger for Min: "+slider.getValue());
			}
		});
	}

	private void addItems() {
		add(play);
		add(slider);
		add(label);
	}

	public void settextButtonPlay(String string) {
		play.setText(string);
	}

}
