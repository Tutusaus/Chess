package peces;

//import java.util.LinkedList;

import main.Escacs;

public class Rei extends Peca {

    public boolean escac = false;
    public boolean enroc = false;
    //public LinkedList<Peca> peces_escac;
    
    public Rei(int x_pos, int y_pos, boolean esblanc) {
        super(x_pos, y_pos, esblanc);
        super.nom = "Rei";
    }

    public boolean MovLegal(int x, int y) {
        int mov_x = Math.abs(this.x_pos - x);
        int mov_y = Math.abs(this.y_pos - y);

        if(mov_x == 1) {
            if(mov_y <= 1) {
                if(HiHaPeca(x, y, true) || !HiHaPeca(x, y, false)) {
                    if(!PosAmenacada(x, y)) {
                        return true;
                    }
                }
            }
        }
        else if(mov_y == 1) {
            if(mov_x <= 1) {
                if(HiHaPeca(x, y, true) || !HiHaPeca(x, y, false)) {
                    if(!PosAmenacada(x, y)) {
                        return true;
                    }
                }
            }
        }
        else if(mov_y == 0) {
            if(mov_x == 2) {
                if(this.moviment == 0) {
                    if(x - this.x_pos > 0) {    // Enroc pel Rei
                        for(int i=1;i<(7 - this.x_pos);i++) {
                            if(HiHaPeca(this.x_pos + i, this.y_pos,false) || PosAmenacada(this.x_pos + i, this.y_pos)) {
                                return false;
                            }
                        }
                        if(Escacs.getPeca(7, this.y_pos) instanceof Torre && Escacs.getPeca(7, this.y_pos).moviment == 0) {
                            enroc = true;
                            return true;
                        }
                    }
                    else {  // Enroc per la Reina
                        for(int i=1;i<=(7 - this.x_pos);i++) {
                            if(HiHaPeca(this.x_pos - i, this.y_pos, false) || PosAmenacada(this.x_pos - i, this.y_pos)) {
                                return false;
                            }
                        }
                        if(Escacs.getPeca(0, this.y_pos) instanceof Torre && Escacs.getPeca(0, this.y_pos).moviment == 0) {
                            enroc = true;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean Escac() {
        if(PosAmenacada(x_pos, y_pos)) {
            escac = true;
            /* for(Peca peca: Escacs.ps) {
                if(peca instanceof Peca) {
                    if(peca.esblanc != this.esblanc) {
                        if(peca.nom == "Peo") {
                            if(Math.abs(peca.x_pos - x_pos) == 1 && Math.abs(peca.y_pos - y_pos) == 1) {
                                peces_escac.add(peca);
                            }
                        }
                        else if(peca.nom == "Rei") {
                            if(Math.abs(peca.x_pos - x_pos) <= 1 && Math.abs(peca.y_pos - y_pos) <= 1) {
                                peces_escac.add(peca);
                            }
                        }
                        else if(peca.MovLegal(x_pos, y_pos)) {
                            peces_escac.add(peca);
                        }
                    }
                }
            } */
        }
        else {
            escac = false;
        }

        return escac;
    }
}
