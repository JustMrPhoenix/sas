package refruity.math.geom.shape;

import java.util.Random;

public interface Shape {
    public Point getCenter();

    public Point getRandomPoint();

    public Point getRandomPoint(Random random);
}
