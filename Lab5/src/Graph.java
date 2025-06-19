import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    private final Map<Integer, List<Integer>> graph;

    public Graph() {
        graph = new HashMap<>();
    }

    public void addEdge(int v, int w) {
        graph.computeIfAbsent(v, _ -> new ArrayList<>()).add(w);
        graph.computeIfAbsent(w, _ -> new ArrayList<>()).add(v);
    }

    public List<Integer> getNeighbors(int v) {
        return graph.get(v);
    }
}
