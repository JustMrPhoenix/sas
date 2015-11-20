package refruity.math.geom.shape;

import java.util.Random;

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

    @Override
    public Point getRandomPoint() {
        return null;
    }

    @Override
    public Point getRandomPoint(Random random) {
        Angle randomAngle = Angle.createRandom();
        return Point.createFrom(randomAngle, this.getRandomRadiusUniform(random)).add(new Vector(center));
    }

    public double getRandomRadiusUniform() {
        return this.getRandomRadiusUniform(new Random());
    }

    public double getRandomRadiusUniform(Random random) {
        double randomSum = radius * (random.nextDouble() + random.nextDouble());
        return randomSum > radius ? 2 * radius - randomSum : randomSum;
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
