package fill;
import model.Point;
import model.Polygon;
import rasterize.LineRasterizer;
import rasterize.PolygonRasterizer;
import rasterize.Raster;
import model.Edge;

import java.util.ArrayList;

public class ScanLineFiller implements Filler{

    private LineRasterizer lineRasterizer;
    private PolygonRasterizer polygonRasterizer;
    private Polygon polygon;
    public ScanLineFiller(LineRasterizer lineRasterizer, PolygonRasterizer polygonRasterizer, Polygon polygon) {
        this.lineRasterizer = lineRasterizer;
        this.polygonRasterizer = polygonRasterizer;
        this.polygon = polygon;
    }
    @Override
    public void fill() {
        ArrayList<Edge> edges = new ArrayList<>();
        int ymin, ymax;

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
        //TODO: najít ymin a ymax
        //default hodnoty první point
        //iterace ymin, ymax
        //probíhat pro všechny hrany
        //  {
        //      1. zeptat se jestli existuje průsečík(v podmínce je zkrácení o jeden pixe)
        //      2. pokud existuje, spočítám ho a uložím do seznamu
        //  }

        //seřadím průsečíky
        //spojím lichý se sudým
        //obtáhnout hranici
    }
}
