package refruity.math.geom.algo;

import refruity.math.geom.shape.Point;
import refruity.math.geom.shape.Shape;

import java.util.Random;

public abstract class DistributionAlgorithm {
    protected Random randomGenerator = new Random();

    public abstract Point getRandomPoint(Shape shape);

    //region generated getters and setters

    public Random getRandomGenerator() {
        return randomGenerator;
    }

    public void setRandomGenerator(Random randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    //endregion
}
