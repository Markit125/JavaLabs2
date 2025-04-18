package Filter;

public class FilterApplier implements Filter {
    @Override
    public boolean apply(Object o) {
        return o instanceof Filter;
    }
}
