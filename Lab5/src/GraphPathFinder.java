import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class GraphPathFinder {
    private final Graph graph;
    private final int startNode;
    private final int endNode;
    private final Queue<Integer> queue;
    private final ConcurrentHashMap.KeySetView<Integer, Boolean> visited;
    private final AtomicBoolean isPathFound = new AtomicBoolean(false);
    private final ConcurrentHashMap<Integer, Integer> parentMap;
    private final ExecutorService executorService;
    private final int countThreads;

    public GraphPathFinder(Graph graph, int startNode, int endNode) {
        this.graph = graph;
        this.startNode = startNode;
        this.endNode = endNode;
        this.queue = new ConcurrentLinkedQueue<>();
        this.visited = ConcurrentHashMap.newKeySet();
        this.parentMap = new ConcurrentHashMap<>();
        this.countThreads = Runtime.getRuntime().availableProcessors() - 1;
        this.executorService = Executors.newFixedThreadPool(countThreads);
    }

    public List<Integer> findPath() {
        return BFSFindPathParallel();
    }

    private List<Integer> BFSFindPathParallel() {
        if (startNode == endNode) {
            return new ArrayList<>(startNode);
        }

        queue.offer(startNode);
        visited.add(startNode);

        for (int i = 0; i < countThreads; i++) {
            this.executorService.submit(this::BFSFindPathTask);
        }

        try {
            while (!isPathFound.get()) {
                if (queue.isEmpty()) {
                    break;
                }
                TimeUnit.MILLISECONDS.sleep(100);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Основной поток прерван во время ожидания завершения поиска пути.");
        } finally {
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                    System.err.println("ExecutorService не завершился в срок. Принудительное завершение.");
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
                Thread.currentThread().interrupt();
                System.err.println("Основной поток прерван во время ожидания завершения ExecutorService.");
            }
        }

        if (isPathFound.get()) {
            return recreatePath();
        }

        System.out.println("Поиск завершен, но путь не был найден.");
        return Collections.emptyList();

    }

    private List<Integer> recreatePath() {
        List<Integer> path = new ArrayList<>();
        Integer currentNode = endNode;
        while (currentNode != null) {
            path.add(currentNode);
            currentNode = parentMap.get(currentNode);
        }
        return path;
    }

    private void BFSFindPathTask() {
        try {
            while (!this.isPathFound.get()) {
                Integer currentNode = queue.poll();
                if (currentNode == null) {
                    if (!this.isPathFound.get()) {
                        break;
                    }
                    TimeUnit.MILLISECONDS.sleep(10);
                    continue;
                }

                if (currentNode == endNode) {
                    isPathFound.set(true);
                    break;
                }

                for (Integer neighbor : graph.getNeighbors(currentNode)) {
                    if (visited.add(neighbor)) {
                        parentMap.put(neighbor, currentNode);
                        queue.offer(neighbor);
                    }
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(Thread.currentThread().getName() + " был прерван.");
        } catch (Exception e) {
            System.err.println(Thread.currentThread().getName() + " столкнулся с ошибкой: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 4);
        graph.addEdge(3, 5);
        graph.addEdge(4, 5);
        graph.addEdge(5, 6);
        graph.addEdge(6, 7);
        graph.addEdge(7, 8);
        graph.addEdge(8, 9);
        graph.addEdge(2, 7);

        int startNode = 0;
        int endNode = 9;

        System.out.println("Parallel find of path from " + startNode + " в " + endNode);

        GraphPathFinder pathFinder = new GraphPathFinder(graph, startNode, endNode);
        List<Integer> path = pathFinder.findPath();

        if (!path.isEmpty()) {
            System.out.println("\nПуть найден: " + path);
        } else {
            System.out.println("\nПуть не найден из " + startNode + " в " + endNode);
        }
    }
}
