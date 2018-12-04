import java.io.*;
import java.nio.file.Path;
import java.util.*;

public class Dijkstra {
    public static void main(String[] args) {


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

            public PriorityQueue<Vertex> makePrqueue() {
                PriorityQueue<Vertex> pr = new PriorityQueue<>();
                for (Integer in : graph.keySet()) {
                    for (Vertex v : graph.get(in)) {
                        v.visited = false;
                        pr.add(v);
                    }
                }

                return pr;
            }


            public Map<Integer, Integer> shortestPath() {

                //making of pr queue
                // add source node to it

                PriorityQueue<Vertex> pr = new PriorityQueue<>();
                Vertex sourceNode = new Vertex(source, 0);
                sourceNode.visited = false;
                pr.add(sourceNode);


                //stores distance from root to every vertex
                Map<Integer, Integer> distance = new HashMap<>();
                for(int i = 1 ; i <= vertices; i++) {
                    distance.put(i, Integer.MAX_VALUE);
                }

                //stores the parent of vertex
                Map<Vertex, Vertex> root = new HashMap<>();

                //put root in distance
                distance.put(1, 0);

                //mark it s parent as null
                root.put(sourceNode, null);


                while (!pr.isEmpty()) {
                    Vertex curVertex;
                    Vertex tmpVertex = pr.peek();
                    if (!tmpVertex.visited) {
                        curVertex = pr.poll();
                        curVertex.visited = true;
                    } else continue;

                    List<Vertex> list = graph.get(curVertex.indexOfnode);
                    if(list == null) continue;
                    for (Vertex neighbour : list) {

//                        if (neighbour.visited) {
//                            continue;
//                        }

                        if (neighbour.cost + distance.get(curVertex.indexOfnode) < distance.get(neighbour.indexOfnode)) {

                            if (neighbour.visited) {
                                continue;
                            }

                            neighbour.visited = true;

                            int cost = neighbour.cost + distance.get(curVertex.indexOfnode);
                            Vertex newNode = new Vertex(neighbour.indexOfnode, cost);
                            pr.remove(neighbour);
                            pr.add(newNode);
                            distance.put(neighbour.indexOfnode, newNode.cost);
                            root.put(newNode, curVertex);

                        }

                    }

                }

                //System.out.println(distance.toString());

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
