package main;

import java.awt.image.BufferedImage;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

//import inputs.Ratoli;
import peces.*;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class Escacs {

    public static Finestra finestra;
    public static Tauler tauler;
    public static Image imgs[] = new Image[12];
    public static Image punt;
    public static Clip so_moure;
    public static Clip audio;
    public static LinkedList<Peca> ps = new LinkedList<>();
    public int FPS = 2000;
    public double TempsPerFotograma = 1. / FPS; // Temps per Fotograma en Nanosegons

    public static Rei reiN = new Rei(4,0,false);
    public static Rei reiB = new Rei(4,7,true);

    public Escacs() throws IOException, UnsupportedAudioFileException, LineUnavailableException {

        //////////////////////////// IMPORTEM LES FITXES DEL JOC DES DE LA IMATGE FACILITADA ////////////////////////////////
        BufferedImage all = ImageIO.read(new File("src/assets/chess.png"));
        int ind=0;
        for(int y=0;y<all.getHeight();y+=all.getHeight()/2){
            for(int x=0;x<all.getWidth();x+=all.getWidth()/6){
                imgs[ind]=all.getSubimage(x, y, all.getWidth()/6, all.getWidth()/6).getScaledInstance(Tauler.mida_casella, Tauler.mida_casella, BufferedImage.SCALE_SMOOTH);
                ind++;
            }
        }
        ///////////// ////////////////////////////////////////////////////////////////////////////////////////////////////////
        BufferedImage img = ImageIO.read(new File("src/assets/punt.png"));
        punt = img.getScaledInstance(Tauler.mida_casella, Tauler.mida_casella, BufferedImage.SCALE_SMOOTH);

        AudioInputStream audioStream_so_moure = AudioSystem.getAudioInputStream(new File("src/assets/audio/move-self.wav"));
        so_moure = AudioSystem.getClip();
        so_moure.open(audioStream_so_moure);

        AudioInputStream audioStream_audio = AudioSystem.getAudioInputStream(new File("src/assets/audio/audio.wav"));
        audio = AudioSystem.getClip();
        audio.open(audioStream_audio);

        CrearPeces();
        tauler = new Tauler();
        finestra = new Finestra();
        finestra.add(tauler);
        run();
    }

    /**
     * Funció que crea totes les peces del Tauler.
     */
    public void CrearPeces() {
        // Negres
        Torre torreN0 = new Torre(0,0,false);
        Cavall cavallN0 = new Cavall(1,0,false);
        Alfil alfilN0 = new Alfil(2,0,false);
        Reina reinaN = new Reina(3,0,false);
        Alfil alfilN1 = new Alfil(5,0,false);
        Cavall cavallN1 = new Cavall(6,0,false);
        Torre torreN1 = new Torre(7,0,false);
        Peo peoN0 = new Peo(0,1,false);
        Peo peoN1 = new Peo(1,1,false);
        Peo peoN2 = new Peo(2,1,false);
        Peo peoN3 = new Peo(3,1,false);
        Peo peoN4 = new Peo(4,1,false);
        Peo peoN5 = new Peo(5,1,false);
        Peo peoN6 = new Peo(6,1,false);
        Peo peoN7 = new Peo(7,1,false);

        // Blanques
        Peo peoB0 = new Peo(0,6,true);
        Peo peoB1 = new Peo(1,6,true);
        Peo peoB2 = new Peo(2,6,true);
        Peo peoB3 = new Peo(3,6,true);
        Peo peoB4 = new Peo(4,6,true);
        Peo peoB5 = new Peo(5,6,true);
        Peo peoB6 = new Peo(6,6,true);
        Peo peoB7 = new Peo(7,6,true);
        Torre torreB0 = new Torre(0,7,true);
        Cavall cavallB0 = new Cavall(1,7,true);
        Alfil alfilB0 = new Alfil(2,7,true);
        Reina reinaB = new Reina(3,7,true);
        Alfil alfilB1 = new Alfil(5,7,true);
        Cavall cavallB1 = new Cavall(6,7,true);
        Torre torreB1 = new Torre(7,7,true);

        ps.add(torreN0);
        ps.add(cavallN0);
        ps.add(alfilN0);
        ps.add(reinaN);
        ps.add(reiN);
        ps.add(alfilN1);
        ps.add(cavallN1);
        ps.add(torreN1);
        ps.add(peoN0);
        ps.add(peoN1);
        ps.add(peoN2);
        ps.add(peoN3);
        ps.add(peoN4);
        ps.add(peoN5);
        ps.add(peoN6);
        ps.add(peoN7);

        for(int y=2;y<6;y++) {
            for(int x=0;x<8;x++) {
                ps.add(null);
            }
        }

        // Blanques
        ps.add(peoB0);
        ps.add(peoB1);
        ps.add(peoB2);
        ps.add(peoB3);
        ps.add(peoB4);
        ps.add(peoB5);
        ps.add(peoB6);
        ps.add(peoB7);
        ps.add(torreB0);
        ps.add(cavallB0);
        ps.add(alfilB0);
        ps.add(reinaB);
        ps.add(reiB);
        ps.add(alfilB1);
        ps.add(cavallB1);
        ps.add(torreB1);
    }

    /**
     * @param x Posició x de la peca al tauler. Del 0 al 7.
     * @param y Posició y de la peca al tauler. Del 0 al 7.
     * @return Posició (x, y) del Tauler. En cas de no haver-n'hi cap, retorna NULL.
     */
    public static Peca getPeca(int x, int y){
        for(Peca peca: ps){
            if(peca!=null) {
                if(peca.x_pos==x && peca.y_pos==y){
                    return peca;
                }
            }
        }
        return null;
    }

    public void run() {
        long UltimFotograma = System.nanoTime();
        long Temps;

        while(true) {
            Temps = System.nanoTime();
            if(Temps - UltimFotograma >= (1_000_000_000)*TempsPerFotograma) {
                /* if(Ratoli.pecaSeleccionada instanceof Rei) {
                    Rei rei = (Rei) Ratoli.pecaSeleccionada;
                    for (Peca peca : rei.peces_escac) {
                        System.out.println(peca);
                    }
                } */
                //System.out.println(1_000_000_000/(Temps - UltimFotograma));
                finestra.repaint();
                UltimFotograma = Temps;
            } 
        }
    }
}
