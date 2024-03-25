package peces;

import inputs.Ratoli;
import main.Escacs;
import main.Tauler;

public abstract class Peca {

    public String nom;
    public boolean esblanc;
    public int x_pos;
    public int y_pos;
    public int moviment = 0;
    public static int jugada = 0;

    public Peca(int x_pos, int y_pos, boolean esblanc) {
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        this.esblanc = esblanc;
    }

    public abstract boolean MovLegal(int x, int y);

    public void set_XY(int x, int y) {
        x_pos = x;
        y_pos = y;
    }

    public void MoureA(int x, int y) {
        if(MovLegal(x, y)) {
            if(this instanceof Rei) {
                Rei rei = (Rei) this;
                if(rei.enroc) {
                    if(x - this.x_pos > 0) {
                        Peca torre = Escacs.getPeca(7, this.y_pos);
                        Escacs.ps.set(8*torre.y_pos + torre.x_pos, null);
                        Escacs.ps.set(8*torre.y_pos + 5, torre);
                        torre.set_XY(5, this.y_pos);
                    }
                    else if(x - this.x_pos < 0) {
                        Peca torre = Escacs.getPeca(0, this.y_pos);
                        Escacs.ps.set(8*torre.y_pos + torre.x_pos, null);
                        Escacs.ps.set(8*torre.y_pos + 3, torre);
                        torre.set_XY(3, this.y_pos);
                    }
                    rei.enroc = false;
                }
            }
            else if(this instanceof Peo) {
                Peo peo = (Peo) this;
                if(peo.enpassant) {
                    if(x == peo.peca_enpassant.x_pos) {
                        Escacs.ps.set(8*peo.peca_enpassant.y_pos + peo.peca_enpassant.x_pos, null);
                    }
                }
                peo.enpassant = false;
            }

            Escacs.ps.set(8*y_pos + x_pos, null);
            Escacs.ps.set(8*y + x, this);
            set_XY(x, y);
            
            moviment++;
            jugada++;
            Ratoli.pecaSeleccionada = null;
            Ratoli.seleccio = !Ratoli.seleccio;
            Tauler.ImprimirTauler();
            Escacs.so_moure.start();
        }
        else {
            if(Escacs.getPeca(x, y) instanceof Peca) {
                if(Escacs.getPeca(x, y).esblanc == this.esblanc) {
                    Ratoli.pecaSeleccionada = Escacs.getPeca(x, y);
                }
            }
        }
        Escacs.so_moure.setMicrosecondPosition(0);
    }

    public boolean HiHaPeca(int x, int y, boolean color_dif) {
        if(color_dif) {
            if(Escacs.getPeca(x, y) instanceof Peca) {
                if(Escacs.getPeca(x, y).esblanc!=this.esblanc) {
                    return true;
                }
            }
            return false;
        }
        else {
            if((Escacs.getPeca(x, y) instanceof Peca)) {
                return true;
            }
            return false;
        }
    }

    public boolean PosAmenacada(int x, int y) {
        for(Peca peca: Escacs.ps) {
            if(peca instanceof Peca) {
                if(peca.esblanc != this.esblanc) {
                    if(peca.nom == "Peo") {
                        if(Math.abs(peca.x_pos - x) == 1 && Math.abs(peca.y_pos - y) == 1) {
                            return true;
                        }
                    }
                    else if(peca.nom == "Rei") {
                        if(Math.abs(peca.x_pos - x) <= 1 && Math.abs(peca.y_pos - y) <= 1) {
                            return true;
                        }
                    }
                    else if(peca.MovLegal(x, y)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int[] MovLegals() {
        int[] movlegals = new int[64];

        for(int y=0;y<8;y++) {
            for(int x=0;x<8;x++) {
                if(this.MovLegal(x, y)) {
                    movlegals[8*y + x] = 1;
                }
                else {
                    movlegals[8*y + x] = 0;
                }
            }
        }
        return movlegals;
    }

    /**
     * Funció que dibuixa al terminal les posicions legals on es pot moure una peca.
     */
    public void ImprimirMovLegals() {
        if(Ratoli.seleccio) {
            for(int y=0;y<8;y++) {
                for(int x=0;x<8;x++) {
                    System.out.print(Ratoli.pecaSeleccionada.MovLegals()[8*y + x]);
                    System.out.print("\t");
                }
                System.out.println("\n");
            }
            System.out.println("****************************************************");
        }
    }
}
