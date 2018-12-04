import javax.print.DocFlavor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class DijkstraWithoutPQ {
    public static void main(String[] args) {


        class Vertex implements Comparable<Vertex> {
            int indexOfnode;
            int cost;
            List<Vertex> adlist = new ArrayList<>();

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

            public Map<Integer, Integer> shortestPath() {

                HashMap<Integer, Integer> distance = new HashMap<>();

                HashMap<Integer, Boolean> visited = new HashMap<>();

                HashSet<Integer> notpr = new HashSet<>();


                for (int i = 1; i <= vertices; i++) {
                    distance.put(i, Integer.MAX_VALUE);
                    visited.put(i, false);
                    notpr.add(i);
                }


                distance.put(source, 0);


                while (!notpr.isEmpty()) {
                    int infinity = Integer.MAX_VALUE;
                    int current = -1;
                    for (int i : notpr) {
                        if (visited.get(i)) continue;
                        if (distance.get(i) <= infinity) {
                            current = i;
                            infinity = distance.get(current);

                        }
                    }

                    notpr.remove(current);

                    visited.put(current, true);

                    List<Vertex> list = graph.get(current);
                    if (list == null) continue;
                    for (Vertex v : list) {

                        if(visited.get(v.indexOfnode)) continue;
                        int newCost = distance.get(current) + v.cost;

                        if (newCost < distance.get(v.indexOfnode)) {
                            distance.put(v.indexOfnode, newCost);

                        }
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
            }

            final long startTime = System.nanoTime();
            Map<Integer, Integer> map = graph.shortestPath();

            final long duration = System.nanoTime() - startTime;

            // System.out.println(duration);
            Runtime runtime = Runtime.getRuntime();
            runtime.gc();
            long memory = runtime.totalMemory() - runtime.freeMemory();

            for(Integer i : map.keySet()) {
                if(i == graph.source)continue;
                System.out.print(map.get(i) + " ");
            }
            System.out.println();
            System.out.println("Time:" + " " + Math.toIntExact(duration) + "ns");
            System.out.println("Space:" + " " + Math.toIntExact(memory));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
