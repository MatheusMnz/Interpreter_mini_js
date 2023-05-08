package interpreter.value;

public class NumberValue extends Value<Double> {

    private Double value;

    public NumberValue(Double value) {
        this.value = value;
    }

    @Override
    public Double value() {
        return this.value;
    }

    @Override
    public boolean eval() {
        return !this.value.isNaN() && this.value != 0.0;
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof NumberValue) {
            return this.value.doubleValue() == ((NumberValue) obj).value.doubleValue();
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        long tmp = this.value.longValue();
        return this.value.doubleValue() == ((double) tmp) ?
            Long.toString(tmp) : this.value.toString();
    }

    public static double convert(Value<?> value) {

        if (value instanceof BoolValue) 
            return ((BoolValue) value).value() ? 1.0 : 0.0;
        else if (value instanceof NumberValue)
            return ((NumberValue) value).value();
        else if (value instanceof TextValue){

            try {
                return Double.parseDouble(((TextValue) value).value());
            } catch (Exception e) {
                return 0.0;
            }
            
        }
        else 
            return 0.0;
    }
}
