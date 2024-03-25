package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import main.Escacs;
import main.Tauler;
import peces.Peca;

public class Ratoli implements MouseListener, MouseMotionListener{

    public static boolean seleccio = false;
    public static Peca pecaSeleccionada=null;

    public Ratoli() {
        
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        //System.out.println("mouse dragged");
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //System.out.println("mouse moved");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //System.out.println("mouse clicked");
        int x_coor = e.getX()/Tauler.mida_casella;
        int y_coor = e.getY()/Tauler.mida_casella;

        if(Escacs.getPeca(x_coor, y_coor) instanceof Peca) {
            if(!seleccio) {
                if(Peca.jugada%2 == 0) {
                    if(Escacs.getPeca(x_coor, y_coor).esblanc) {
                        pecaSeleccionada = Escacs.getPeca(x_coor, y_coor);
                        seleccio = !seleccio;
                        pecaSeleccionada.ImprimirMovLegals();
                    }
                }
                else {
                    if(!Escacs.getPeca(x_coor, y_coor).esblanc) {
                        pecaSeleccionada = Escacs.getPeca(x_coor, y_coor);
                        seleccio = !seleccio;
                        pecaSeleccionada.ImprimirMovLegals();
                    }
                }
            }
            else {
                if(Escacs.getPeca(x_coor, y_coor).esblanc==pecaSeleccionada.esblanc) {
                    pecaSeleccionada = Escacs.getPeca(x_coor, y_coor);
                    pecaSeleccionada.ImprimirMovLegals();
                }
                else {
                    pecaSeleccionada.MoureA(x_coor, y_coor);
                }
            }
        }
        else {
            if(seleccio) {
                pecaSeleccionada.MoureA(x_coor, y_coor);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //System.out.println("mouse pressed");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //System.out.println("mouse released");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //System.out.println("mouse entered");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //System.out.println("mouse exited");
    }
    
}
