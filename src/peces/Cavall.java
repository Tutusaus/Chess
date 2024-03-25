package peces;

import main.Escacs;

public class Cavall extends Peca {

    public Cavall(int x_pos, int y_pos, boolean esblanc) {
        super(x_pos, y_pos, esblanc);
        super.nom = "Cavall";
    }

    //////////////// FUNCIÓ ACABADA ////////////////
    public boolean MovLegal(int x, int y) {
        int mov_x = Math.abs(this.x_pos - x);
        int mov_y = Math.abs(this.y_pos - y);

        if((mov_x == 1 && mov_y == 2) || (mov_x == 2 && mov_y == 1)) {
            if(Escacs.getPeca(x, y) instanceof Peca) {
                if(Escacs.getPeca(x, y).esblanc != this.esblanc) {
                    return true;
                }
            }
            else {
                return true;
            }
        }
        return false;
    }
    ////////////////////////////////////////////////
}
