package refruity.math.geom.algo;

import refruity.math.geom.shape.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class UniformDistributionAlgorithm extends DistributionAlgorithm {
    @Override
    public Point getRandomPoint(Shape shape) {
        if (shape instanceof Circle) {
            return getRandomCirclePoint((Circle)shape);
        } else if (shape instanceof Disk) {
            return getRandomDiskPoint((Disk)shape);
        } else {
            throw new NotImplementedException();
        }
    }

    private Point getRandomCirclePoint(Circle circle) {
        double radius = circle.getRadius();
        double randomSum = radius * (randomGenerator.nextDouble() + randomGenerator.nextDouble());
        double uniformRadius = randomSum > radius ? 2 * radius - randomSum : randomSum;

        Angle randomAngle = Angle.createRandom(randomGenerator);

        return Point.createFrom(randomAngle, uniformRadius).add(new Vector(circle.getCenter()));
    }

    private Point getRandomDiskPoint(Disk disk) {
        Point center = disk.getCenter();
        double outerRadius = disk.getOuterRadius();
        double innerRadius = disk.getInnerRadius();

        double radiusDelta = outerRadius - innerRadius;
        double randomSum = radiusDelta * (randomGenerator.nextDouble() + randomGenerator.nextDouble());
        double uniformRadius = randomSum > radiusDelta ? 2 * radiusDelta - randomSum : randomSum;

        Angle randomAngle = Angle.createRandom(randomGenerator);

        return Point.createFrom(randomAngle, uniformRadius + innerRadius).add(new Vector(center));
    }
}
