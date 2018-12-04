import javax.print.DocFlavor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class BellmanFord {
    public static void main(String[] args) {

        class Edge {
            int source;
            int dest;
            int cost;

            public Edge(int source, int dest, int cost) {
                this.source = source;
                this.dest = dest;
                this.cost = cost;
            }

            @Override
            public String toString() {
                return "Edge{" +
                        "source=" + source +
                        ", dest=" + dest +
                        ", cost=" + cost +
                        '}';
            }
        }


        class Vertex implements Comparable<Vertex> {
            int indexOfnode;
            int cost;
            boolean visited;

            public Vertex(int index, int cost) {
                this.indexOfnode = index;
                this.cost = cost;
            }


            @Override
            public String toString() {
                return "Vertex{" +
                        "indexOfnode=" + indexOfnode +
                        ", cost=" + cost +
                        '}';
            }

            @Override
            public int compareTo(Vertex vertex) {
                return this.cost - vertex.cost;
            }
        }

        class Graph {
            public int vertices;
            public int edges;
            public int source;
            public List<Edge> edgesList = new ArrayList<>();


            public HashMap<Integer, List<Vertex>> graph = new HashMap<Integer, List<Vertex>>();

            Graph(int vertices, int edges, int source) {
                this.vertices = vertices;
                this.edges = edges;
                this.source = source;
            }

            public void putEdge(int source, int node, int cost) {
                Vertex v = new Vertex(node, cost);
                if (!graph.containsKey(source)) {
                    List<Vertex> adList = new ArrayList<>();
                    adList.add(v);
                    graph.put(source, adList);
                } else {
                    List<Vertex> adlist = graph.get(source);
                    adlist.add(v);
                }
            }

            public void makeEdge(int source, int dest, int cost) {
                Edge edge = new Edge(source, dest, cost);
                edgesList.add(edge);
            }


            public Map<Integer, Integer> shortestPath() {

                HashMap<Integer, Integer> distance = new HashMap<>();
                HashMap<Integer, Integer> predecessor = new HashMap<>();

                for (int i = 1; i <= vertices; i++) {
                    distance.put(i, Integer.MAX_VALUE);
                    predecessor.put(i, null);
                }

                //source vertex
                distance.put(1, 0);

                //relax edges
                for (int j = 1; j < vertices; j++) {
                    for (Edge edge : edgesList) {
                        if (edge.cost + distance.get(edge.source) < distance.get(edge.dest)) {
                            distance.put(edge.dest, edge.cost + distance.get(edge.source));
                            predecessor.put(edge.dest, edge.source);
                        }
                    }
                }

                for (Edge edge : edgesList) {
                    if (distance.get(edge.source) + edge.cost < distance.get(edge.dest)) {
                        System.out.println("Graph contains a neg weight cycle");
                        return null;
                    }
                }

                return distance;

            }

            @Override
            public String toString() {
                return "Graph{" +
                        "vertices=" + vertices +
                        ", edges=" + edges +
                        ", source=" + source +
                        ", graph=" + graph +
                        '}';
            }
        }


        try {
            FileInputStream text = new FileInputStream(args[0]);
            Scanner in = new Scanner(text);
            int numberVertices = in.nextInt();
            int numberEdges = in.nextInt();
            int sourceVertex = in.nextInt();
            Graph graph = new Graph(numberVertices, numberEdges, sourceVertex);

            while (in.hasNextLine()) {
                int source = in.nextInt();
                int node = in.nextInt();
                int cost = in.nextInt();
                graph.putEdge(source, node, cost);
                graph.makeEdge(source, node, cost);
            }


            final long startTime = System.nanoTime();
            Map<Integer, Integer> map = graph.shortestPath();

            final long duration = System.nanoTime() - startTime;


            Runtime runtime = Runtime.getRuntime();
            runtime.gc();
            long memory = runtime.totalMemory() - runtime.freeMemory();


            if (map != null) {
                for (Integer i : map.keySet()) {
                    if (i == graph.source) continue;
                    System.out.print(map.get(i) + " ");
                }
            }
            System.out.println();
            System.out.println("Time:" + " " + Math.toIntExact(duration) + " ns");
            System.out.println("Space:" + " " + Math.toIntExact(memory));


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
