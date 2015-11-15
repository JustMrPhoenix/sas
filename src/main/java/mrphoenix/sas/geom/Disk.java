package mrphoenix.sas.geom;

import java.util.Random;

public class Disk implements Shape {
    private Point center;

    private double innerRadius, outerRadius;

    public Disk(Point center, double innerRadius, double outerRadius) {
        this.center = center;
        this.innerRadius = innerRadius;
        this.outerRadius = outerRadius;
    }

    @Override
    public Point getCenter() {
        return center;
    }

    @Override
    public Point getRandomPoint() {
        return this.getRandomPoint(new Random());
    }

    @Override
    public Point getRandomPoint(Random random) {
        Circle circle = new Circle(center, outerRadius - innerRadius);
        Angle randomAngle = Angle.createRandom();
        return Point.createFrom(randomAngle, circle.getRandomRadiusUniform() + innerRadius).add(new Vector(center));
    }

    //region generated getters and setters

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
