package my.sas.geom;

public class Disk {
    private PointDouble center;

    private double innerRadius;

    private double outerRadius;

    public Disk(PointDouble center, double innerRadius, double outerRadius) {
        this.center = center;
        this.innerRadius = innerRadius;
        this.outerRadius = outerRadius;
    }

    public PointInteger getRandomPointInteger() {
        double angle = Math.random();
        double radius = Math.random();
        Vector radiusVector;
        return null;
    }

    //region generated getters and setters

    public PointDouble getCenter() {
        return center;
    }

    public void setCenter(PointDouble center) {
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
