package Filter;

public class NumbersApplier implements Filter {
    @Override
    public boolean apply(Object o) {
        return o instanceof Number;
    }
}
