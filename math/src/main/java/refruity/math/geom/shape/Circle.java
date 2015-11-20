package refruity.math.geom.shape;

public class Circle implements Shape {
    private Point center;

    private double radius;

    public Circle() {
        this.center = new Point();
        this.radius = 1;
    }

    public Circle(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    @Override
    public Point getCenter() {
        return center;
    }

    //region generated getters and setters

    public void setCenter(Point center) {
        this.center = center;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    //endregion
}
