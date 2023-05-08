
package interpreter.value;

public class BoolValue extends Value<Boolean> {

    private Boolean value;

    public BoolValue(Boolean value) {
        this.value = value;
    }

    @Override
    public Boolean value() {
        return this.value;
    }

    @Override
    public boolean eval() {
        return this.value.booleanValue();
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof BoolValue) {
            return this.value.booleanValue() == ((BoolValue) obj).value.booleanValue();
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

    public static boolean convert(Value<?> value) {
        if (value instanceof BoolValue) 
            return ((BoolValue) value).value();
        else if (value instanceof NumberValue) 
            return ((NumberValue) value).value() != 0.0;
        else if (value instanceof TextValue)
            return !((TextValue) value).value().isEmpty();
        else if (value instanceof ListValue)
            return !((ListValue) value).value().isEmpty();
        else if (value instanceof ObjectValue)
            return !((ObjectValue) value).value().isEmpty();
        else if (value instanceof FunctionValue)
            return true;
        else
            return false;
    }
}
