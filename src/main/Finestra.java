package main;

import javax.swing.JFrame;

public class Finestra extends JFrame {
    public static int marge_amplada = 14;   // Mida dels marges laterals
    public static int amplada = 640;
    public static int marge_llargada = 37;    // Mida del "title bar"
    public static int llargada = 640;

    Finestra(){
        setSize(amplada + marge_amplada, llargada + marge_llargada);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
