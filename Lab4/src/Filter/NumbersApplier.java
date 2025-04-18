package Filter;

public class NumbersApplier implements Filter {
    @Override
    public boolean apply(Object o) {
        return o instanceof Integer || o instanceof Double || o instanceof Float ||
                o instanceof Short || o instanceof Byte;
    }
}
