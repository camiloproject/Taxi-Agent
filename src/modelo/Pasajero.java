package modelo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Pasajero {
	
	private int x, y;
	private Nodo initial;
	private Nodo end;
	private boolean state;
	private float tiempoestera;
	private float tiempoInicio;
        private Image p_espera;
        private Image p_destino;
	
	public Pasajero() {
            p_espera=new ImageIcon("src/img/espera_pasajero.png").getImage();
            p_destino=new ImageIcon("src/img/pasajero_destino.png").getImage();
		tiempoestera=0;
	}
	
	public void mover(){
		state=true;
		
	}
	public void paint(Graphics g, int width, int height){
		if(!state){
			//g.setColor(Color.BLUE);
			//g.fillRect(x*width/102, y*height/63, width/102, height/63);
                        g.drawImage(p_espera,x*width/102 -5, y*height/63-5, width/102+25, height/63+30, null);
                        
		}
		else{
			//g.setColor(Color.RED);
			//g.fillRect(x*width/102+4, y*height/63-5, width/102-4, height/63-3);
                       
                        //g.setColor(Color.BLACK);
                        g.drawImage(p_destino,end.getI()*width/102 -5, end.getJ()*height/63-5, width/102+25, height/63+30, null);
			//g.fillRect(end.getI()*width/102, end.getJ()*height/63, width/102, height/63);
		}
		
	}
	public void setPosition(int i, int j) {
		x=i;
		y=j;
		initial.setPosition(i, j);
	}
	public Nodo getInitial() {
		return initial;
	}

	public void setInitial(Nodo initial) {
		this.initial = initial;
	}

	public Nodo getEnd() {
		return end;
	}

	public void setEnd(Nodo end) {
		this.end = end;
	}

	public boolean isMove() {
		return state;
	}

	public void setMove(boolean sate) {
		this.state = sate;
	}

	@Override
	public String toString() {
		return "Pasajero [initial=" + initial + ", end=" + end + ", state="
				+ state + "]";
	}

	public float getTiempoestera() {
		return tiempoestera;
	}

	public void setTiempoestera(float tiempoestera) {
		this.tiempoestera = tiempoestera;
	}

	public float getTiempoInicio() {
		return tiempoInicio;
	}

	public void setTiempoInicio(float tiempoInicio) {
		this.tiempoInicio = tiempoInicio;
	}
	
	
}
