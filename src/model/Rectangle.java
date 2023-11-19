package model;

import java.util.ArrayList;

public class Rectangle extends Polygon{
    public Rectangle(Point p1, Point p3)
    {
        addPoint(p1);
        addPoint(new Point(p1.x, p3.y));
        addPoint(p3);
        addPoint(new Point(p3.x, p1.y));
    }
    public Point returnCenterPointsForElipse()
    {
        int centerX = (this.points.get(0).x + this.points.get(2).x) / 2;
        int centerY = (this.points.get(0).y + this.points.get(2).y) / 2;
        return new Point(centerX,centerY);
    }

    public ArrayList<Integer> returnRadiusOfElipse(){
        int width = (this.points.get(0).x - this.points.get(3).x);
        int height = (this.points.get(0).y - this.points.get(1).y);
        ArrayList<Integer> dimensions = new ArrayList<Integer>();
        dimensions.add(width);
        dimensions.add(height);
        return dimensions;
    }
}

