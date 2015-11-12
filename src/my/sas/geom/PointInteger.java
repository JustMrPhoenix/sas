package my.sas.geom;

public class PointInteger {
    private int x, y;

    public PointInteger(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public PointInteger add(Vector v) {
        return this.toPointDouble().add(v).toPointInteger();
    }

    public PointDouble toPointDouble() {
        return new PointDouble(this.x, this.y);
    }

    //region generated getters and setters

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    //endregion
}
