package my.sas.geom;

public class Circle {
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

    public Point getRandomPoint() {
        double randomAngle = Math.random();
        return Point.createFrom(randomAngle, this.getRandomRadiusUniform()).add(new Vector(center));
    }

    public double getRandomRadiusUniform() {
        double randomSum = radius * (Math.random() + Math.random());
        return randomSum > radius / 2 ? 2 * radius - randomSum : randomSum;
    }

    //region generated getters and setters

    public Point getCenter() {
        return center;
    }

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
