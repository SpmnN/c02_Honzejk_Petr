package rasterize;

import model.Ellipse;

import java.text.DecimalFormat;

public class EllipseRasterizer {
    public EllipseRasterizer(LineRasterizer lineRasterizer) {
        this.lineRasterizer = lineRasterizer;
    }

    private LineRasterizer lineRasterizer;
    public void rasterize(int centerX, int centerY, double radiusX, double radiusY){
        double px =0, py=0;

        for (int i = 0; i <= 360; i++) {
            double x, y;
            x = radiusX * Math.sin(Math.toRadians(i));
            y = radiusY * Math.cos(Math.toRadians(i));

            if (i != 0) {
                lineRasterizer.rasterize((int) px + centerX, (int) py + centerY, (int) x + centerX, (int) y + centerY);
            }

            px = x;
            py = y;
        }
    }
}