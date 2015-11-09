package modelo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import javax.rmi.CORBA.Tie;

import controlador.Aplicativo;
import java.io.BufferedReader;
import java.io.FileReader;

public class Model {

	private int[][] mapeoTablero;
	private int pasaje;
	private int pasajerosAtendidos;
	private int velCreaPasajeros;
	private float time;
	private Thread thread;
	private Thread thread1;
	ArrayList<Taxi> taxis;
	LinkedList<Nodo> nodos;
	LinkedList<Pasajero> pasajeros;
	LinkedList<Nodo> intersections;
	LinkedList<Thread> threads;
        ArrayList<ArrayList<Integer>> pop = new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> newpop = new ArrayList<ArrayList<Integer>>();
        ArrayList<Double> Fitness = new ArrayList<Double>(); 
 	
	public Model() {
		
		setMatrizMap();
		printMatriz();
		taxis = new ArrayList<Taxi>();
		time=0;
		velCreaPasajeros=5000;
		nodos = new LinkedList<Nodo>();
		pasajeros = new LinkedList<Pasajero>();
		intersections=new LinkedList<Nodo>();
		
		//Method Private
		
		createNodos();
		conectNodos();
		
		//Method Public
		//createPasagero();
		createPasagero();
		createPasagero();
		createPasagero();
		createPasagero();
                createPasagero();
		createPasagero();
		createPasagero();
		createPasagero();
		createTaxi();
               

	}
        //Generates a movement population.
        public void generarPoblacion(){
            Random rmd = new Random();
            for(int i=0;i<80;i++){
                ArrayList<Integer> mov = new ArrayList<Integer>();
                for (int j=0;j<100;j++){
                    mov.add(rmd.nextInt(5));
                }
                pop.add(mov);
            }
        }
        //Computes the fitness of each chromosome.
        public ArrayList<Double> fitness(ArrayList<ArrayList<Integer>> pop){
            for(int i=0;i<pop.size();i++){
                int count = 0;
                for(int j=0;i<pop.get(i).size();j++){
                    if(pop.get(i).get(j) != 4) count++;
                }
                Double fit = 1.0/count;
                Fitness.add(fit);
            }
            return Fitness;
        }
        //Select the best population with roulette wheel selection
        public int select(ArrayList<Double> fitness){
            //Computes the total weigth
            double peso_suma=0;
            for(int i=0;i<fitness.size();i++){
                peso_suma += fitness.get(i);
            }
            
            //Get a random value
            double valor = (new Random().nextDouble())*peso_suma;
            
            //// locate the random value based on the weights
            for(int i=0; i<fitness.size(); i++) {		
                valor -= fitness.get(i);		
                if(valor <= 0) return i;
            }
            // only when rounding errors occur
            return fitness.size() - 1;
        }
        
        //The crossover function.
        public void crossover(ArrayList<Integer> p1, ArrayList<Integer> p2){
            Random rnd = new Random();
            int divisor = rnd.nextInt(p1.size());
            ArrayList<Integer> p3 = new ArrayList<Integer>();
            ArrayList<Integer> p4 = new ArrayList<Integer>();
        }
	//The Thread for play
	public void play(){
		
		threads=new LinkedList<Thread>();
		
		for(Taxi i: taxis){
			Thread thread=new Thread(){
				public void run() {
					while(true){
						i.mover();
					}
				};
			};
			
			thread.start();
			threads.add(thread);
		}
		
		thread= new Thread(){
			public void run() {
				while(true){
					
					try {
						sleep(velCreaPasajeros);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					createPasagero();
					Aplicativo.get().repaintCanvas();
				}
			};
		};
		thread.start();
		
		thread1 = new Thread(){
			public void run() {
				while(true){
					try {
						sleep(1);
						time+=1 ;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
		};
		thread1.start();
	}
	public void createPasagero() {
		Pasajero pasajero=new Pasajero();
		boolean positioValite1=true;
		boolean positioValite2=true;
		while(positioValite1){
			int i=new Random().nextInt(100);
			int j=new Random().nextInt(63);
			if(mapeoTablero[i][j]==1){
				positioValite1=false;
				pasajero.setInitial(new Nodo(i, j));
				pasajero.setPosition(i, j);
			}
			
		}
		while(positioValite2){
			int i=new Random().nextInt(100);
			int j=new Random().nextInt(63);
			if(mapeoTablero[i][j]==1 & (pasajero.getInitial().getI()!=i&&pasajero.getInitial().getJ()!=j) ){
				positioValite2=false;
				pasajero.setEnd(new Nodo(i, j));
			}
			
		}
		pasajero.setTiempoInicio(time);
		pasajeros.add(pasajero);
		pasaje++;
	}
	
	private void conectNodos() {
		
		for(Nodo i: nodos){
			if(mapeoTablero[i.getI()-1][i.getJ()]==1){
				i.addVecinos(buscarVecino(i.getI()-1,i.getJ()));
			}
			if(mapeoTablero[i.getI()+1][i.getJ()]==1){
				i.addVecinos(buscarVecino(i.getI()+1,i.getJ()));
			}
			if(mapeoTablero[i.getI()][i.getJ()-1]==1){
				i.addVecinos(buscarVecino(i.getI(),i.getJ()-1));
			}
			if(mapeoTablero[i.getI()][i.getJ()+1]==1){
				i.addVecinos(buscarVecino(i.getI(),i.getJ()+1));
			}
		}

		createIntersections();
	}

	private void createIntersections() {
		for(Nodo i: nodos){
			if(i.getVecinos().size()==1){
				i.setIntersertion();
				intersections.add(i);
			}
			if(i.getVecinos().size()>=3){
				i.setIntersertion();
				intersections.add(i);
			}
		}
		
		for(Nodo i: intersections){
			for(Nodo j: intersections){
				if(!i.equals(j)){
					if(hayConection(i, j)){
						i.addVecinoInter(j);
					}
				}
			}
		}
	}

	private boolean hayConection(Nodo i, Nodo j) {
		if(i.getI()==j.getI()){
			if(i.getJ()<j.getJ()){
				for(int m=i.getJ()+1; m<j.getJ(); m++){
					Nodo node=getNodo(i.getI(), m);
					if(node!=null){
						if(node.isIntersection()){
							return false;
						}
					}
					else{
						return false;
					}
				}
				return true;
			}
			else{
				for(int m=i.getJ()-1; m>j.getJ(); m--){
					Nodo node=getNodo(i.getI(), m);
					if(node!=null){
						if(node.isIntersection()){
							return false;
						}
					}
					else{
						return false;
					}
				}
				return true;
			}
		}
		if(i.getJ()==j.getJ()){
			if(i.getI()<j.getI()){
				for(int m=i.getI()+1; m<j.getI(); m++){	
					Nodo node=getNodo(m, i.getJ());
					if(node!=null){
						if(getNodo(m, i.getJ()).isIntersection()){
							return false;
						}
					}
					else{
						return false;
					}
				}
				return true;
			}
			else{
				for(int m=i.getI()-1; m>j.getI(); m--){
					Nodo node=getNodo(m, i.getJ());
					if(node!=null){
						if(node.isIntersection()){
							return false;
						}
					}
					else{
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}

	public Nodo getNodo(int i, int j) {
		Nodo n=new Nodo(i,j);
		for(Nodo k: nodos){
			if (k.equals(n)) {
				return k;
			}
		}
		return null;
	}

	private Nodo buscarVecino(int k, int j) {
		for(Nodo i: nodos){
			if(i.getI()==k&& i.getJ()==j){
				return i;
			}
		}
		return null;
	}

	private void createNodos() {
		for(int i=0; i<102; i++){
			for(int j=0; j<63;j++){
				if(mapeoTablero[i][j]==1){
					Nodo nodo=new Nodo(i, j);
					nodos.add(nodo);
				}
			}
		}
	}

	private Taxi createTaxi() {
		Taxi taxi=new Taxi();
		boolean positioValite=true;
		while(positioValite){
			int i=new Random().nextInt(100);
			int j=new Random().nextInt(63);
			if(mapeoTablero[i][j]==1){
				positioValite=false;
				taxi.setPosition(i, j);
			}
			
		}
		taxis.add(taxi);
		return taxi;
	}

	public void paint(Graphics g, int width, int height) {
		g.setColor(Color.gray);
		
		for(int i=0; i<102; i++){
			for(int j=0; j<63;j++){
				if(mapeoTablero[i][j]==1){
					//g.drawRect(i*width/102, j*height/63, width/102, height/63);
				}
				
			}
		}
		float promEspera=0;
		float timeAll=0;
		for(Pasajero j: pasajeros){
			j.paint(g, width, height);
			if(!j.isMove()){
				j.setTiempoestera(time-j.getTiempoInicio());
				timeAll+=j.getTiempoestera();
			}
		}
		promEspera=timeAll/pasaje;
		for(Taxi i: taxis){
			i.paint(g, width, height);
		}
		
	}
	
	private void setMatrizMap() {            
            
		mapeoTablero=new int[102][63];
		for(int i=0; i<102; i++){
			for(int j=0; j<63; j++){
				mapeoTablero[i][j]=0;
			}
		}
		
                //calles horizontales de arriba a abajo
		for(int i=2;i<101;i++){
                    //calle 1
                    if((i>=3&&i<=38)||(i>=62&&i<=97)){mapeoTablero[i][2]=1;}
                    //calle 2
                    if((i>=38&&i<=81)){mapeoTablero[i][10]=1;}
		    //calle 3
                    if((i>=3&&i<=38)||(i>=62&&i<=81)){mapeoTablero[i][20]=1;}
                    //calle 4
                    if((i>=38&&i<=62)){mapeoTablero[i][29]=1;}
                    //calle 5
                    if((i>=3&&i<=97)){mapeoTablero[i][38]=1;}
                    //calle 6
                    if((i>49&&i<=81)){mapeoTablero[i][46]=1;}
		    //calle 7
                    if((i>=3&&i<=97)){mapeoTablero[i][55]=1;}
		}
                //carreras verticales de izquierda a derecha
		for(int j=1;j<62;j++){
                    //carrera 1
                    if((j>=2&&j<=55)){mapeoTablero[3][j]=1;}
                    //carrera 2
                    if((j>=2&&j<=38)){mapeoTablero[21][j]=1;}
                    //carrera 3
                    if((j>=2&&j<=38)){mapeoTablero[38][j]=1;}
                    //carrera 4
                    if((j>=29&&j<=55)){mapeoTablero[50][j]=1;}
                    //carrera 5
                    if((j>=2&&j<=55)){mapeoTablero[62][j]=1;}
                    //carrera 6
                    if((j>=2&&j<=46)){mapeoTablero[81][j]=1;}
                    //carrera 7
                    if((j>=2&&j<=55)){mapeoTablero[97][j]=1;}
		}
	}
	private void printMatriz() {
		for(int i=0; i<63; i++){
			for(int j=0; j<102; j++){
				System.out.print(mapeoTablero[j][i]);
			}
			System.out.println();
		}
	}

	public boolean hayPasajeros() {
		int k=0;
		for(Pasajero i: pasajeros){
			if(!i.isMove()){
				k++;
			}
		}
		if(k==0){
			return false;
		}
		return true;
	}

	public Pasajero getPasageroMasCercano(Nodo taxi) {
		int distancia=0;
		Pasajero optimo=null;
                
		for(Pasajero i: pasajeros){
			
			int k=getCamino(taxi, i.getInitial()).size();
			if(distancia==0){
				distancia=k;
				optimo=i;
			}
			else{
				if(k<distancia){
					distancia=k;
					optimo=i;
				}
			}
		}
		return optimo;
	}

	public LinkedList<Nodo> getCamino(Nodo initial, Nodo end) {
                generarPoblacion();
                fitness(pop);
                select(Fitness);
		LinkedList<Nodo> camino = new LinkedList<Nodo>();
		if(!initial.equals(end)){
			Nodo ini = null;
			Nodo fin = null;
			for(Nodo i:nodos){
				if(i.equals(initial)){
					ini=i;
				}
				if(i.equals(end)){
					fin=i;
				}
			}
			//System.out.println("START:"+ini+""+end);
			ini.getCamino(camino,fin);
			//System.out.println(camino);
		}
		return camino;
	}

	public void deletePasajero(Pasajero first) {
		pasajeros.remove(first);
		pasajerosAtendidos++;
	}
    public LinkedList<Pasajero> getPasajeros() {
		return pasajeros;
	}

	public void bajarPasajeros() {
		if(hayPasajeros()){
			for(Pasajero i: pasajeros){
				i.setMove(false);
			}
			for(Taxi i: taxis){
				i.bajarPasajeros();
			}
		}
	}

	public LinkedList<Nodo> getNodosInter() {
		return intersections;
	}
	public void stop() {
		for(Thread i: threads){
			i.stop();
		}
		bajarPasajeros();
		
		thread.stop();
		thread1.stop();
	}
	public void chageVelCreatePasa(int value) {
		velCreaPasajeros=value;
	}

	
	
}
