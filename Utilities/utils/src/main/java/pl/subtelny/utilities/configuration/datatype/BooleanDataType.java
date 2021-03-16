package pl.subtelny.utilities.configuration.datatype;

public class BooleanDataType implements DataType<Boolean> {

    public static final BooleanDataType TYPE = new BooleanDataType();

    private BooleanDataType() {
    }

    @Override
    public Boolean convertToType(String value) {
        return Boolean.parseBoolean(value);
    }

    @Override
    public String convertToString(Boolean value) {
        return value.toString();
    }

    @Override
    public boolean isDefault(String value) {
        return "false".equals(value);
    }

    @Override
    public Boolean getDefault() {
        return false;
    }

}
