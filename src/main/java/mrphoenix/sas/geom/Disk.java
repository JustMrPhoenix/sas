package mrphoenix.sas.geom;

public class Disk {
    private Point center;

    private double innerRadius, outerRadius;

    public Disk(Point center, double innerRadius, double outerRadius) {
        this.center = center;
        this.innerRadius = innerRadius;
        this.outerRadius = outerRadius;
    }

    public Point getRandomPoint() {
        Circle circle = new Circle(center, outerRadius - innerRadius);
        double randomAngle = Math.random();
        return Point.createFrom(randomAngle, circle.getRandomRadiusUniform() + innerRadius).add(new Vector(center));
    }

    //region generated getters and setters

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public double getInnerRadius() {
        return innerRadius;
    }

    public void setInnerRadius(double innerRadius) {
        this.innerRadius = innerRadius;
    }

    public double getOuterRadius() {
        return outerRadius;
    }

    public void setOuterRadius(double outerRadius) {
        this.outerRadius = outerRadius;
    }

    //endregion
}
