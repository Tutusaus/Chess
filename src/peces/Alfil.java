package peces;

import main.Escacs;

public class Alfil extends Peca {

    public Alfil(int x_pos, int y_pos, boolean esblanc) {
        super(x_pos, y_pos, esblanc);
        super.nom = "Alfil";
    }

    public boolean MovLegal(int x, int y) {
        int mov_x = Math.abs(x - this.x_pos);
        int mov_y = Math.abs(y - this.y_pos);

        if((mov_x == mov_y) && (mov_x != 0)) {
            int sentit_x = (int)(x - this.x_pos)/mov_x;
            int sentit_y = (int)(y - this.y_pos)/mov_x;
            for(int i=1;i<mov_x;i++) {
                if(HiHaPeca(this.x_pos + i*sentit_x, this.y_pos + i*sentit_y, false)) {
                    return false;
                }
            }
            if(HiHaPeca(this.x_pos + sentit_x*mov_x, this.y_pos + sentit_y*mov_y, true) || Escacs.getPeca(this.x_pos + sentit_x*mov_x, this.y_pos + sentit_y*mov_y) == null) {
                return true;
            }
        }
        return false;
    }
}