package refruity.math.geom.shape;

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
