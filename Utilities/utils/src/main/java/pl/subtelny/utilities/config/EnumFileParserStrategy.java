package pl.subtelny.utilities.config;

import pl.subtelny.utilities.ClassUtil;

import java.io.File;

public class EnumFileParserStrategy<T extends Enum<T>> extends AbstractFileParserStrategy<T> {

    public EnumFileParserStrategy(File file) {
        super(file);
    }

    @Override
    public T load(String path) {
        String rawEnum = new ObjectFileParserStrategy<String>(file).load(path);
        Enum anEnum = Enum.valueOf(getEnumType(), rawEnum.toUpperCase());
        return (T) anEnum;
    }

    @Override
    public Saveable set(String path, T value) {
        configuration.set(path, value.name());
        return this;
    }

    public Class<? extends Enum> getEnumType() {
        return (Class<? extends Enum>) ClassUtil.getTypeParameterType(this.getClass(), EnumFileParserStrategy.class, 0);
    }
}
