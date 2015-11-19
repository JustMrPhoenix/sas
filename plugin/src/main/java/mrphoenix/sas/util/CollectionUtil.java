package mrphoenix.sas.util;

import java.util.Collection;

public class CollectionUtil {
    public static <T, C extends Collection<T>> C requireNotEmpty(C collection) {
        if (collection.isEmpty()) {
            throw (new IllegalStateException());
        }
        return collection;
    }
}
