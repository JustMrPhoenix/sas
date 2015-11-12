package my.sas.util;

import my.sas.geom.PointDouble;
import my.sas.geom.PointInteger;

// TODO: Remove if not necessary
public class GeomUtil {
    public static PointInteger toPointInteger(PointDouble p) {
        return new PointInteger((int)p.getX(), (int)p.getY());
    }

    public static PointDouble toPoint(PointInteger pint) {
        return new PointDouble(pint.getX(), pint.getY());
    }
}
