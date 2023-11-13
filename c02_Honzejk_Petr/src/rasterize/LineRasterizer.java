package rasterize;

import model.Line;

import java.awt.*;

import static java.lang.Math.abs;

public abstract class LineRasterizer {
    Raster raster;
    Color color;

    public LineRasterizer(Raster raster){
        this.raster = raster;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setColor(int color) {
        this.color = new Color(color);
    }

    public void rasterize(Line line) {
        // TODO: barvička?? :(
        drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
    }

    public void rasterize(int x1, int y1, int x2, int y2, Color color) {
        // TODO: barvička?? :(
        drawLine(x1, y1, x2, y2);
    }

    protected void drawLine(int x1, int y1, int x2, int y2) {
        int dx = abs(x2 - x1), sideX = x1 < x2 ? 1 : -1; //nastavení směru pohybu (doleva(-1), doprava[1])
        int dy = -abs(y2 - y1), sideY = y1 < y2 ? 1 : -1;//nastavení směru pohybu (dolů(-1), nahoru[1])
        int err = dx + dy; //nastavení error marginu
        int foo;//pomocná proměnná

        while (true) {

            raster.setPixel(x1, y1, 0xff0000);//obarvení pixelu na daných souřadnicích x a y
            if (x1 == x2 && y1 == y2) { //po dosáhnutí posledního bodu -> konec smyčky
                break;
            }

            foo = err << 1; //násobení 2

            if (foo > dy) { //pokud se pohybujeme po ose X, zvýšíme chybu o rozdíl souřadnic a posunume po ose x ve správném směru (sideX)
                err += dy;
                x1 += sideX;
            }

            if (foo < dx) { //pokud se pohybujeme po ose Y, zvýšíme chybu o rozdíl souřadnic a posunume po ose y ve správném směru (sideY)
                err += dx;
                y1 += sideY;
            }
        }
    }
}

