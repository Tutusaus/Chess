package peces;

import main.Escacs;

public class Peo extends Peca {

    public int sentit_y;
    public boolean enpassant = false;
    public Peca peca_enpassant;
    public int jugada_mov_dues;

    public Peo(int x_pos, int y_pos, boolean esblanc) {
        super(x_pos, y_pos, esblanc);
        super.nom = "Peo";

        if(this.y_pos == 6) {
            sentit_y = 1;
        }
        else {
            sentit_y = -1;
        }
    }

    public boolean MovLegal(int x, int y) {
        int mov_x = Math.abs(x - this.x_pos);
        int mov_y = Math.abs(y - this.y_pos);

        if((this.y_pos - y)*(sentit_y) > 0) {   //També es podria fer Math.signum(this.y_pos - y)==Math.signum(sentit_y)
            if(mov_y <= 2) {
                if(this.moviment == 0) {
                    if(mov_x == 0) {
                        if(mov_y == 1) {
                            if(!HiHaPeca(x, y, false)) {
                                return true;
                            }
                        }
                        else if(mov_y == 2) {
                            for(int i=1;i<=mov_y;i++) {
                                if(HiHaPeca(this.x_pos, this.y_pos - i*sentit_y, false)) {
                                    return false;
                                }
                            }
                            jugada_mov_dues = jugada;
                            return true;
                        }
                    }
                    else if (mov_x == 1) {
                        if(mov_y == 1) {
                            if(HiHaPeca(x, y, true)) {
                                return true;
                            }
                        }
                    }
                }
                else {
                    if(mov_y == 1) {
                        if(mov_x == 0) {
                            if(!HiHaPeca(x, y, false)) {
                                return true;
                            }
                        }
                        else if(mov_x == 1) {
                            if(HiHaPeca(x, y, true)) {
                                return true;
                            }
                            else if(HiHaPeca(x, y_pos, true)) {
                                if(Escacs.getPeca(x, y_pos) instanceof Peo) {
                                    Peo peo = (Peo) Escacs.getPeca(x, y_pos);
                                    if(peo.jugada_mov_dues + 1 == jugada) {
                                            if(esblanc) {
                                                if(y_pos == 3) {
                                                    peca_enpassant = peo;
                                                    enpassant = true;
                                                    return true;
                                                }
                                            }
                                            else {
                                                if(y_pos == 4) {
                                                    peca_enpassant = peo;
                                                    enpassant = true;
                                                    return true;
                                                }
                                            }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}

