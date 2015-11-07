package my.sas.util;

import java.util.Collection;

public class CollectionUtil {
    public static <T, C extends Collection<T>> C requireNotNull(C collection) {
        if (collection == null) {
            throw (new NullPointerException());
        }
        return collection;
    }

    public static <T, C extends Collection<T>> C requireNotEmpty(C collection) {
        requireNotNull(collection);
        if (collection.isEmpty()) {
            throw (new IllegalStateException());
        }
        return collection;
    }
}
