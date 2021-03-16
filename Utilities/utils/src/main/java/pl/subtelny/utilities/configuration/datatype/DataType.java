package pl.subtelny.utilities.configuration.datatype;

public interface DataType<T> {

    T convertToType(String value);

    String convertToString(T value);

    boolean isDefault(String value);

    T getDefault();

}
