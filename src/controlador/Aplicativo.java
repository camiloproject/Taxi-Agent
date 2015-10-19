package controlador;

import java.awt.Graphics;
import java.util.LinkedList;

import modelo.Model;
import modelo.Nodo;
import modelo.Pasajero;
import vista.MainFrame;

public class Aplicativo {
	
	private static Aplicativo instace;
	private MainFrame frame;
	private Model model;
	private boolean isPlay;
	private Aplicativo(){
		
		model=new Model();
		frame= new MainFrame("AGENTE TAXI !");
	
		
	}
	
	public static Aplicativo get(){
		if(instace==null){
			instace=new Aplicativo();
		}
		return instace;
	}
	
	public void paint(Graphics g, int with, int heig){
		model.paint(g,with,heig);
	}
        public void createPasajero(){
		model.createPasagero();
	}
	
	
	public static void main(String[] args) {
		try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
		
		get();
		
		get().frame.setVisible(true);
	}

	public void repaintCanvas() {
		get().frame.repaintCanvas();
		
	}

	public boolean hayPasajeros() {
		return model.hayPasajeros();
	}

	public Pasajero getPasageroMasCercano(Nodo nodo) {
		return model.getPasageroMasCercano(nodo);
		
	}
        public LinkedList<Pasajero> getPasajeros() {
		return model.getPasajeros();
		
	}
	

	public void play() {
		model.play();
	}
	public void stop() {
		model.stop();
	}
	public LinkedList<Nodo> getCamino(Nodo nodo, Nodo initial) {
		return model.getCamino(nodo,initial);
		
	}

	public void deletePasajero(Pasajero first) {
		model.deletePasajero(first);
	}

	public LinkedList<Nodo> getNodosInter() {
		return model.getNodosInter();
	}

	public void chageVelCreatePasa(int value) {
		model.chageVelCreatePasa(value);
	}

	public boolean isPlay() {
		return isPlay;
	}

	public void setPlay(boolean isPlay) {
		this.isPlay = isPlay;
	}

	public void settextButtonPlay(String string) {
		frame.settextButtonPlay(string);
		
	}

	
	
}
