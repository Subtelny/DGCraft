package pl.subtelny.repository;

public interface ComputeCache<KEY, VALUE> {

    VALUE compute(KEY key);

}
