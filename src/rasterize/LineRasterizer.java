package rasterize;

import model.Line;

import java.awt.*;
import java.util.ArrayList;

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
        drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
    }

    public void rasterize(int x1, int y1, int x2, int y2) {
        drawLine(x1, y1, x2, y2);
    }

    public void rasterizePrecisionMode(int x1, int y1, int x2, int y2){
        drawPrecisionLine(x1,y1,x2,y2);
    }

    public void rasterizeDottedLine(int x1, int y1, int x2, int y2) {
        drawDottedLine(x1,y1,x2,y2);}

    public void rasterizeDottedPrecisionLine(int x1, int y1, int x2, int y2){
        drawDottedPrecisionline(x1, y1, x2, y2);}

    protected void drawLine(int x1, int y1, int x2, int y2) {}

    protected void drawPrecisionLine(int x1, int y1, int x2, int y2){}

    protected void drawDottedLine(int x1, int y1, int x2, int y2){}

    protected void drawDottedPrecisionline(int x1, int y1, int x2, int y2){}
}
