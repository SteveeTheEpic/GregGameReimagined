package de.stevee.API.Registry;

import java.util.Collection;

public interface Registry<T> {
    T register(T t);
    T get(String id);
    Collection<T> all();
}
