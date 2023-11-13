package rasterize;

import static java.lang.Math.abs;

public class FilledLineRasterizer extends LineRasterizer {
    int color;

    public FilledLineRasterizer(Raster raster) {
        super(raster);
    }

    @Override
    public void setColor(int rgb) {
        this.color = rgb;
    }

    @Override
    protected void drawLine(int x1,int y1,int x2, int y2) {//Bresenhamův algoritmus
        int dx = abs(x2 - x1), sideX = x1 < x2 ? 1 : -1; //nastavení směru pohybu (doleva(-1), doprava[1])
        int dy = -abs(y2 - y1), sideY = y1 < y2 ? 1 : -1;//nastavení směru pohybu (dolů(-1), nahoru[1])
        int err = dx + dy; //nastavení error marginu
        int foo;//pomocná proměnná

        while (true) {

            raster.setPixel(x1, y1, color);//obarvení pixelu na daných souřadnicích x a y
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
    @Override
    protected void drawDottedLine(int x1, int y1, int x2, int y2) {//
        int dx = abs(x2 - x1), sx = x1 < x2 ? 1 : -1;//nastavíme směr pohybu (doleva[-1], doprava[1])
        int dy = -abs(y2 - y1), sy = y1 < y2 ? 1 : -1;//nastavíme směr pohybu (dolů[-1], nahoru[1])
        int err = dx + dy, e2;//nastavíme chybu
        int count = 0;

        while (true) {
            if (count < 3) { //první 3 instnce -> bod, dalších 7 -> mezera
                raster.setPixel(x1, y1, color); //vykreslí bod
            }

            if (x1 == x2 && y1 == y2) {break;}//podmínka pro určení směru pohybu

            e2 = 2 * err;

            if (e2 > dy) {
                err += dy;
                x1 += sx;
            }

            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
            count = (count + 1) % 10; //Pokud se 10x zopakuje, count se vyresetuje
        }
    }
    @Override
    protected void drawPrecisionLine(int x1,int y1, int x2, int y2){
        int dx = x2 - x1; //nastavení rozdíl vzdáleností
        int dy = y2 - y1; //nastavení rozdíl vzdáleností

        if (Math.abs(dx) > Math.abs(dy)) { //zkontrolovat vzdálenost k horizontální ose, nasledující vykreslení horizontálně
            x2 = x1 + dx;
            y2 = y1;
        } else if(Math.abs(dx) < Math.abs(dy)) { //zkontrolovat vzdálenost k vertikální ose, nasledující vykreslení vertikálně
            x2 = x1;
            y2 = y1 + dy;
        } else{}//vykreslení diagonály
       drawLine(x1,y1,x2,y2);
    }

    @Override
    protected void drawDottedPrecisionline(int x1, int y1, int x2, int y2) {
        int dx = x2 - x1; //nastavit rozdíl vzdáleností
        int dy = y2 - y1; //nastavit rozdíl vzdáleností

        if (Math.abs(dx) > Math.abs(dy)) { //zkontrolovat vzdálenost k horizontální ose, nasledující vykreslení horizontálně
            x2 = x1 + dx;
            y2 = y1;
        } else if(Math.abs(dx) < Math.abs(dy)) { //zkontrolovat vzdálenost k vertikální ose, nasledující vykreslení vertikálně
            x2 = x1;
            y2 = y1 + dy;
        } else{}//vykreslení diagonály
        drawDottedLine(x1,y1,x2,y2);
    }
}

