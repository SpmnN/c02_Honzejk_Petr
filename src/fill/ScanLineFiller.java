package fill;
import model.Point;
import model.Polygon;
import rasterize.LineRasterizer;
import rasterize.PolygonRasterizer;
import rasterize.Raster;
import model.Edge;

import java.util.ArrayList;
import java.util.Collections;

public class ScanLineFiller{

    private LineRasterizer lineRasterizer;
    private PolygonRasterizer polygonRasterizer;
    private Polygon polygon;
    public ScanLineFiller(LineRasterizer lineRasterizer, PolygonRasterizer polygonRasterizer, Polygon polygon) {
        this.lineRasterizer = lineRasterizer;
        this.polygonRasterizer = polygonRasterizer;
        this.polygon = polygon;
    }

    public void fill(Raster img) {
        ArrayList<Edge> edges = new ArrayList<>();
        int ymin = 0, ymax = 0;
        int canvasWidth = img.getWidth();
        int canvasHeight = img.getHeight();

        for(int i = 0; i< polygon.getSize(); i++)
        {
            Point p1 = polygon.getPoint(i);
            int indexB = i+1;
            if(indexB== polygon.getSize())
            indexB=0;
            Point p2 = polygon.getPoint(indexB);

            Edge edge = new Edge(p1.x, p1.y,p2.x,p2.y);
            if(edge.isHorizontal())
                continue;
            edge.orientate();
            edges.add(edge);
        }
        for(Edge edge : edges)
        {
            if(edge.y1 > ymax)
                ymax = edge.y1;
            if(edge.y2 < ymin)
                ymin = edge.y2;
        }

        for(int y= ymin; y < ymax;y++) {
            ArrayList<Integer> intersectionPoints = new ArrayList<>();
            for (int i = 0; i < edges.size(); i++) {
                Point p1 = new Point(edges.get(i).x1, edges.get(i).y1);
                Point p2 = new Point(edges.get(i).x2, edges.get(i).y2);//pl.getVertices().get((i + 1) % pl.getVertices().size());

                if ((p1.y < y && p2.y >= y) || (p2.y < y && p1.y >= y)) {
                    double x = p1.x + (double) (y - p1.y) * (p2.x - p1.x) / (p2.y - p1.y);
                    intersectionPoints.add((int) x);
                }
            }
            Collections.sort(intersectionPoints);
            for (int i = 0; i < intersectionPoints.size(); i += 2) {
                int x1 = intersectionPoints.get(i);
                int x2 = intersectionPoints.get(i + 1);

                // Vyplnění pixelů v daném rozsahu x souřadnice
                for (int x = x1; x <= x2; x++) {
                    if (x >= 0 && x < canvasWidth && y >= 0 && y < canvasHeight) {
                        img.setPixel(x, y, 140000);
                    }
                }
            }
        }



        //default hodnoty první point
        //iterace ymin, ymax
        //probíhat pro všechny hrany
        //  {
        //      1. zeptat se jestli existuje průsečík(v podmínce je zkrácení o jeden pixel)
        //      2. pokud existuje, spočítám ho a uložím do seznamu
        //  }

        //seřadím průsečíky
        //spojím lichý se sudým
        //obtáhnout hranici
    }
}
//k = (x2 - x1) / (y2 - y1);
//q = x1 - k * y1;

//průsečík  x = k*y + q
