package main;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import inputs.Ratoli;
import inputs.Teclat;
import peces.*;

public class Tauler extends JPanel{
    public static int mida_casella = (Finestra.llargada)/8;
    public static Font font = new Font("TimesRoman", Font.PLAIN, 14);

    Tauler() {
        Ratoli inputs_ratoli = new Ratoli();
        addKeyListener(new Teclat());
        addMouseListener(inputs_ratoli);
        addMouseMotionListener(inputs_ratoli);
    }

    /**
     * Funció que mostra al terminal la posició de les peces al Tauler. Per exemple: PeoB, ReiN, 0, etc.
     */
    public static void ImprimirTauler() {
        String color;
        int index = 0;
        for (Peca peca : Escacs.ps) {
            if(index%8==0) {
                System.out.print("\n");
            }
            if(peca==null){
                System.out.print("0\t");
            }
            else {
                if(peca.esblanc) {
                    color = "B";
                }
                else {
                    color = "N";
                }
                System.out.print(peca.nom + color + "\t");
            }
            index++;
        }
        System.out.println("\n**************************************************************");
    }

    /* (non-Javadoc)
     * Funció que s'encarrega de dibuixar totes les Peces, Tauler i Components a la Finestra. 
     * Només n'hi ha un de sol en tot el programa per tal d'estalviar-me problemes.
     */
    @Override
    public void paint(Graphics g) {
        DibuixarTauler(g);
        DibuixarInfoPeca(g);
        DibuixarPeces(g);
    }

    /**
     * @param g
     * Funció que dibuixa a la Finestra el Tauler 8 x 8.
     */
    public void DibuixarTauler(Graphics g) {
        g.setFont(font);
        boolean color1=true;
        for(int y=0;y<8;y++){
            String lletra;
            for(int x=0;x<8;x++){
                if(color1){
                    g.setColor(ColorTauler.color3[0]);
                }
                else{
                    g.setColor(ColorTauler.color3[1]);
                }
                g.fillRect(x*mida_casella, y*mida_casella, mida_casella, mida_casella);
                color1=!color1;
                switch(x) {
                    case 0:
                        lletra = "a";
                        break;
                    case 1:
                        lletra = "b";
                        break;
                    case 2:
                        lletra = "c";
                        break;
                    case 3:
                        lletra = "d";
                        break;
                    case 4:
                        lletra = "e";
                        break;
                    case 5:
                        lletra = "f";
                        break;
                    case 6:
                        lletra = "g";
                        break;
                    default:
                        lletra = "h";
                        break;
                }
                if(y==7)  {
                    g.setColor(Color.BLACK);
                    g.drawString(lletra, x*mida_casella + 71, y*mida_casella + 76);
                }
            }
            String num = String.valueOf(8-y);
            g.setColor(Color.BLACK);
            g.drawString(num, 2, y*mida_casella + 12);

            color1=!color1;
        }
    }

    /**
     * @param g
     * Funció que dibuixa a la Finestra les peces de la partida.
     */
    public void DibuixarPeces(Graphics g) {
        for(Peca peca: Escacs.ps){
            int ind=0;
            if(peca instanceof Rei){
                ind=0;
            }
            if(peca instanceof Reina){
                ind=1;
            }
            if(peca instanceof Alfil){
                ind=2;
            }
            if(peca instanceof Cavall){
                ind=3;
            }
            if(peca instanceof Torre){
                ind=4;
            }
            if(peca instanceof Peo){
                ind=5;
            }
            if(peca!=null) {
                if(!(peca.esblanc)){
                    ind+=6;
                }
                g.drawImage(Escacs.imgs[ind], peca.x_pos*mida_casella, peca.y_pos*mida_casella, this);
            }
        }
    }

    /**
     * @param g
     * Funció que dibuixa a la finestra les posicions legals on es pot moure una peca.
     */
    public void DibuixarInfoPeca(Graphics g) {      
        if(Ratoli.seleccio){
            g.setColor(Color.blue);
            g.drawRect(Ratoli.pecaSeleccionada.x_pos*mida_casella, Ratoli.pecaSeleccionada.y_pos*mida_casella, mida_casella, mida_casella);

            for(int y=0;y<8;y++) {
                for(int x=0;x<8;x++) {
                    if(Ratoli.pecaSeleccionada.MovLegals()[8*y + x] == 1) {
                        if(Escacs.getPeca(x, y) instanceof Peca) {
                            if(Escacs.getPeca(x, y).esblanc != Ratoli.pecaSeleccionada.esblanc) {
                                g.setColor(Color.RED);
                                g.drawRect(x*mida_casella,y*mida_casella, mida_casella, mida_casella);
                            }
                            else {
                                g.drawImage(Escacs.punt,x*mida_casella,y*mida_casella,this);
                            }
                        }
                        else {
                            g.drawImage(Escacs.punt,x*mida_casella,y*mida_casella,this);
                        }
                    }
                }
            }
        }

        for (Peca peca : Escacs.ps) {
            if(peca instanceof Peca) {
                if(peca.nom == "Rei") {
                    Rei rei = (Rei) peca;
                    if(rei.Escac()) {
                        g.setColor(Color.RED);
                        g.fillRect(rei.x_pos*mida_casella, rei.y_pos*mida_casella, mida_casella, mida_casella);
                    }
                }
                else if(peca.nom == "Peo") {
                    Peo peo = (Peo) peca;
                    if(peo.enpassant) {
                        if(Ratoli.pecaSeleccionada == peca) {
                            Peo peo2 = (Peo) peo.peca_enpassant;
                            if(peo2.jugada_mov_dues + 1 == Peca.jugada) {
                                g.setColor(Color.RED);
                                g.drawRect(peo.peca_enpassant.x_pos*mida_casella, peo.y_pos*mida_casella, mida_casella, mida_casella);
                            }
                        }
                        
                    }
                }
            }
        }

    }
}