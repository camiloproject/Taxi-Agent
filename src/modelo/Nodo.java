package modelo;

import java.util.LinkedList;

import controlador.Aplicativo;

public class Nodo {
	
	private int i, j;
	private LinkedList<Nodo> vecinos;
	private LinkedList<Nodo> vecInter;
	private boolean intersetion;
	
	public Nodo(int i, int j) {
		vecinos=new LinkedList<Nodo>();
		vecInter=new LinkedList<Nodo>();
		
		this.i=i;
		this.j=j;
	}
	
	public void addVecinos(Nodo i){
		vecinos.add(i);
	}
	@Override
	public String toString() {
		return "["+i+","+j+"]";
	}

	public LinkedList<Nodo> getVecinos() {
		return vecinos;
	}

	public void setVecinos(LinkedList<Nodo> vecinos) {
		this.vecinos = vecinos;
	}

	public int getI() {
		return i;
	}

	public int getJ() {
		return j;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + i;
		result = prime * result + j;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Nodo other = (Nodo) obj;
		if (i != other.i)
			return false;
		if (j != other.j)
			return false;
		return true;
	}

	public void getCamino(LinkedList<Nodo> camino, Nodo fin2) {
		
		if(!this.equals(fin2)){
			if(isIntersection()){
				camino.add(this);
				int valor=0;
				Nodo optim=null;
				for(Nodo i: vecInter){
                                    
					if(valor==0){
						valor=i.getValor(fin2) + getValor(i);
						optim=i;
					}
					else{
						if(valor>i.getValor(fin2)+ getValor(i)){
							valor=i.getValor(fin2)+ getValor(i);
							optim=i;
						}
					}
				}
				if(valor <=getValor(fin2)){
					try {
						addCaminInter(optim, camino);
						optim.getCamino(camino, fin2);
					} catch (StackOverflowError e) {
						
					}
				}
				else{
					valor=0;
					for(Nodo i: vecinos){
						if(!estaEnCamino(camino, i)){
							if(valor==0){
								valor=i.getValor(fin2);
								optim=i;
							}
							else{
								if(valor>i.getValor(fin2)){
									valor=i.getValor(fin2);
									optim=i;
								}
							}
						}
					}
					optim.getCamino(camino, fin2);
				}
			}
			else{
				int valor=0;
				Nodo optim=null;
				//Nodo interMasCercano= getMasCercano();
				camino.add(this);
                                
				//if(!(getValor(fin2)>interMasCercano.getValor(fin2))){
					for(Nodo i: vecinos){
						if(!estaEnCamino(camino, i)){
							if(valor==0){
								valor=i.getValor(fin2);
								optim=i;
							}
							else{
								if(valor>i.getValor(fin2)){
									valor=i.getValor(fin2);
									optim=i;
								}
							}
						}
					}
                    if(optim!=null){
                    	optim.getCamino(camino, fin2);}
					}
					/*else{
						addCaminInter(interMasCercano, camino);
						interMasCercano.getCamino(camino, fin2);
					}*/
			//}
			}
			else {camino.add(this);
                //System.out.println("ENCONTRAMOS EL CAMINO");
		}
	}

	private Nodo getMasCercano() {
		int valor=0;
		Nodo op=null;
		for(Nodo i: Aplicativo.get().getNodosInter()){
			if(valor==0){
				valor=i.getValor(this);
				op=i;
			}
			else{
				if(valor>i.getValor(this)){
					valor=i.getValor(this);
					op=i;
				}
			}
		}
		return op;
	}

	private void addCaminInter(Nodo o, LinkedList<Nodo> camino) {
		if(i==o.i){
			if(j<=o.j){
				for(int m=j+1;m<o.j;m++){
						camino.add(new Nodo(i, m));
				}
			}
			else{
				for(int m=j-1;m>o.j;m--){
							camino.add(new Nodo(i, m));
				}
			}
		}
		else{
			if(i<=o.i){
				for(int m=i+1;m<o.i;m++){
							camino.add(new Nodo(m, j));
				}
			}else{
				for(int m=i-1;m>o.i;m--){
							camino.add(new Nodo(m, j));
				}
			}
		}
		
	}

	private int getValor(Nodo fin2) {
		return Math.abs(fin2.i-i)+Math.abs(fin2.j-j);
	}

	private static boolean estaEnCamino(LinkedList<Nodo> camino, Nodo i2) {
		for(Nodo i: camino){
			if(i.equals(i2)){
				return true;
			}
		}
		return false;
	}

	public void setPosition(int i2, int j2) {
		i=i2;
		j=j2;
	}

	

	public void setIntersertion() {
		intersetion= true;	
	}
	public boolean isIntersection(){
		return intersetion;
	}

	public void addVecinoInter(Nodo j2) {
		vecInter.add(j2);
	}

	public LinkedList<Nodo> getVeInter() {
		return vecInter;
	}

}
