package UndoStringBuilder;

import java.util.ArrayList;
import java.util.List;

public class UndoStringBuilder {
    private final StringBuilder sb;
    private final List<Integer> appendIndices;

    public UndoStringBuilder() {
        sb = new StringBuilder();
        appendIndices = new ArrayList<>();
    }

    public void append(Object obj) {
        appendIndices.add(sb.length());
        sb.append(obj);
    }

    public void undo() {
        if (appendIndices.isEmpty()) {
            return;
        }

        sb.delete(appendIndices.getLast(), sb.length());
        appendIndices.removeLast();
    }

    public String toString() {
        return sb.toString();
    }
}
