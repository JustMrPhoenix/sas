package my.sas.geom;

import my.sas.geom.PointInteger;

public class Disk {
    private PointInteger center;

    private double innerRadius;

    private double outerRadius;

    public Disk(PointInteger center, double innerRadius, double outerRadius) {
        this.center = center;
        this.innerRadius = innerRadius;
        this.outerRadius = outerRadius;
    }

    public PointInteger getRandomPointInteger() {
        double angle = Math.random();
        return null;
    }

    //region generated getters and setters

    public PointInteger getCenter() {
        return center;
    }

    public void setCenter(PointInteger center) {
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
