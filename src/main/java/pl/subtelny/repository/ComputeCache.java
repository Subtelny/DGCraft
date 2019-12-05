package pl.subtelny.repository;

import java.util.Map;

public interface ComputeCache<KEY, VALUE> {

    VALUE compute(KEY key);

    Map<KEY, VALUE> compute(Iterable<? extends KEY> key);

}
