package model;

public class Ellipse extends Polygon{
    public int radiusX, radiusY, xCenter, yCenter;
    public Ellipse(int radiusX, int radiusY, int xCenter, int yCenter) {
        this.radiusX = radiusX;
        this.radiusY = radiusY;
        this.xCenter = xCenter;
        this.yCenter = yCenter;
    }
}
