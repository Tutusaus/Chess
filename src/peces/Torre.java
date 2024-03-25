package peces;

import main.Escacs;

public class Torre extends Peca {

    public Torre(int x_pos, int y_pos, boolean esblanc) {
        super(x_pos, y_pos, esblanc);
        super.nom = "Torre";
    }

    public boolean MovLegal(int x, int y) {
        int mov_x = Math.abs(x - this.x_pos);
        int mov_y = Math.abs(y - this.y_pos);

        if(mov_x == 0 || mov_y == 0) {
            if(this.x_pos!=x) { //SABEM QUE MOV_Y==0
                int sentit_x = (int)(x - this.x_pos)/Math.abs(x - this.x_pos);
                for(int i=1;i<mov_x;i++) {
                    if(HiHaPeca(this.x_pos + i*sentit_x, this.y_pos, false)) {
                        return false;
                    }
                }
                if(HiHaPeca(this.x_pos + sentit_x*mov_x, this.y_pos, true) || Escacs.getPeca(this.x_pos + sentit_x*mov_x, this.y_pos) == null) {
                    return true;
                }
            }
            else if(this.y_pos!=y) { //SABEM QUE MOV_X==0
                int sentit_y = (int)(y - this.y_pos)/Math.abs(y - this.y_pos);
                for(int i=1;i<mov_y;i++) {
                    if(HiHaPeca(this.x_pos, this.y_pos + i*sentit_y, false)) {
                        return false;
                    }
                }
                if(HiHaPeca(this.x_pos, this.y_pos + sentit_y*mov_y, true) || Escacs.getPeca(this.x_pos, this.y_pos + sentit_y*mov_y) == null) {
                    return true;
                }
            }
        }
        return false;
    }
}
