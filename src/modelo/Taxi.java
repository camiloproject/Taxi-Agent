package modelo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import javax.swing.ImageIcon;

import controlador.Aplicativo;

public class Taxi {

    private Image a;
    private int x, y;
    private LinkedList<Pasajero> pasajeros;
    private int capacidad;
    private int distancia;

    public Taxi() {
        capacidad = 3;
        pasajeros = new LinkedList<Pasajero>();
        a = new ImageIcon("src/img/taxi.png").getImage();
    }

    public void mover() {
        if (Aplicativo.get().hayPasajeros()) {
            int pasajerosEsperando = 0;
            for (Pasajero esperando : Aplicativo.get().getPasajeros()) {
                if (!esperando.isMove()) {
                    pasajerosEsperando++;
                }
            }
            capacidad = 3;
            if (pasajerosEsperando > 0 && pasajeros.size() == 0) {
                MoverHastaPasajeroMascercano();
                if (4 > pasajerosEsperando) {
                    capacidad = pasajerosEsperando - 1;
                }
            }
            while (capacidad != 0) {
                recojerOtroPasajero();
                capacidad--;
                Aplicativo.get().repaintCanvas();
            }
            if (pasajeros.size() != 0) {
                llevarTodosPasajeros();
            }
            mover();
        } else {
            MoverRamdon();
        }
        Aplicativo.get().repaintCanvas();

    }

    private void llevarTodosPasajeros() {
        while (pasajeros.size() != 0) {
            Pasajero cercano = getPasageroMasCercano();

            LinkedList<Nodo> camino = Aplicativo.get().getCamino(new Nodo(x, y), cercano.getEnd());
            for (Nodo i : camino) {
                try {
                    Thread.sleep(60);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                for (Pasajero actual : pasajeros) {
                    actual.setPosition(i.getI(), i.getJ());
                }
                setPosition(i.getI(), i.getJ());
                Aplicativo.get().repaintCanvas();
            }
            cercano.setMove(false);
            pasajeros.remove(cercano);
            Aplicativo.get().deletePasajero(cercano);
            Aplicativo.get().repaintCanvas();
        }
    }

    private void recojerOtroPasajero() {
        LinkedList<Pasajero> pasajerosSistema = Aplicativo.get().getPasajeros();
        int valor = 0;
        Pasajero otroPasajero = null;
        for (Pasajero i : pasajerosSistema) {
            if (!i.isMove()) {
                LinkedList<Nodo> recogida = Aplicativo.get().getCamino(new Nodo(x, y), i.getInitial());
                try {
                    LinkedList<Nodo> llegada = Aplicativo.get().getCamino(pasajeros.getFirst().getEnd(), i.getEnd());
                    if (valor == 0) {
                        valor = recogida.size() + llegada.size();
                        if (valor <= 100) {
                            otroPasajero = i;
                        }
                    }
                    if (recogida.size() + llegada.size() < valor) {
                        valor = recogida.size() + llegada.size();
                        if (valor <= 100) {
                            otroPasajero = i;
                        }
                    }
                } catch (NoSuchElementException e) {
                    System.out.println("ERROR: NoSuchElementException Taxi: line 102");
                }

            }
        }
        if (otroPasajero != null) {
            MoverHastaPasajero(otroPasajero);
        }
    }

    private void llevarpasageroHastaDestino() {

        LinkedList<Nodo> camino = Aplicativo.get().getCamino(new Nodo(x, y), pasajeros.getFirst().getEnd());
        distancia = camino.size();
        for (Nodo i : camino) {
            try {
                Thread.sleep(60);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            pasajeros.getFirst().setPosition(i.getI(), i.getJ());
            setPosition(i.getI(), i.getJ());
            Aplicativo.get().repaintCanvas();
        }
        pasajeros.getFirst().setMove(false);
        Pasajero pass = pasajeros.getFirst();
        pasajeros.remove(pass);
        Aplicativo.get().deletePasajero(pass);
        Aplicativo.get().repaintCanvas();

    }

    private void MoverHastaPasajero(Pasajero nuevo) {
        LinkedList<Nodo> camino = Aplicativo.get().getCamino(new Nodo(x, y), nuevo.getInitial());

        while (camino.size() != 0) {

            Nodo siguiente = camino.get(0);
            for (Pasajero actual : pasajeros) {
                actual.setPosition(siguiente.getI(), siguiente.getJ());
            }
            setPosition(siguiente.getI(), siguiente.getJ());

            try {
                Thread.sleep(60);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Aplicativo.get().repaintCanvas();
            camino.remove(siguiente);
        }
        if (!nuevo.isMove()) {
            nuevo.mover();
            pasajeros.add(nuevo);
        }

    }

    private void MoverHastaPasajeroMascercano() {
        Pasajero pasajero1 = Aplicativo.get().getPasageroMasCercano(new Nodo(x, y));
        LinkedList<Nodo> camino = Aplicativo.get().getCamino(new Nodo(x, y), pasajero1.getInitial());

        while (camino.size() != 0) {

            Nodo siguiente = camino.get(0);
            setPosition(siguiente.getI(), siguiente.getJ());

            try {
                Thread.sleep(60);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Aplicativo.get().repaintCanvas();
            camino.remove(siguiente);
        }
        if (!pasajero1.isMove()) {
            pasajero1.mover();
            pasajeros.add(pasajero1);
        }
    }

    private void MoverRamdon() {
        // TODO Auto-generated method stub

    }

    public void paint(Graphics g, int width, int height) {

        g.drawImage(a, x * width / 102 - 5, y * height / 63 - 5, width / 102 + 60, height / 63 + 40, null);
        if (pasajeros.size() != 0) {
            //g.setColor(Color.YELLOW);
            //g.fillOval(x*width/102 -10, y*height/63-30, width/102+20, height/63+20);

            g.setFont(new Font("Arial", 1, 25));
            g.setColor(Color.white);
            if (pasajeros.size() > 1) {
                g.drawString(pasajeros.size() + " Pasajeros", 45 * width / 102 - 10, 7 * height / 63 - 13);
            } else {
                g.drawString(pasajeros.size() + " Pasajero", 45 * width / 102 - 10, 7 * height / 63 - 13);
            }
            g.drawString("Espera = "+pasajeros.getFirst().getTiempoestera(), 42 * width / 102 - 10, 10 * height / 63 - 13);
            
        }
        if (pasajeros.size() == 0) {
            //g.setColor(Color.YELLOW);
            //g.fillOval(x*width/102 -20, y*height/63-50, width/102+35, height/63+34);

            g.setFont(new Font("Arial", 1, 30));
            g.setColor(Color.white);
            g.drawString("Taxi libre !", 44 * width / 102 - 10, 7 * height / 63 - 13);
            
            
            
        }
        
    }

    public void setPosition(int i, int j) {
        x = i;
        y = j;
    }

    public void bajarPasajeros() {
        pasajeros = new LinkedList<Pasajero>();

    }

    public Pasajero getPasageroMasCercano() {
        int distancia = 0;
        Pasajero optimo = null;

        for (Pasajero i : pasajeros) {

            int k = Aplicativo.get().getCamino(new Nodo(x, y), i.getEnd()).size();
            if (distancia == 0) {
                distancia = k;
                optimo = i;
            } else {
                if (k < distancia) {
                    distancia = k;
                    optimo = i;
                }
            }
        }
        return optimo;
    }

    public Nodo getPosition() {
        return new Nodo(x, y);
    }
}
