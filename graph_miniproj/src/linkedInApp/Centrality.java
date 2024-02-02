package linkedInApp;

import ds.graph.AdjacencyMapGraph;
import ds.graph.Vertex;
import model.Connection;
import model.Node;
import model.User;

import java.util.*;

public class Centrality {

    // ............... Fields ....................

    private final AdjacencyMapGraph<User, Connection> graph;
    public HashMap<String, Double> centralizationDegreeComponents;
    public ArrayList<ArrayList<Node>> betweennessScores;
    public ArrayList<ArrayList<Node>> closenessScores;
    public ArrayList<ArrayList<Node>> katzScores;

    // ............... Constructor ....................
    public Centrality(AdjacencyMapGraph<User, Connection> graph) {
        this.graph = graph;
        // set dfs complete in this given graph
        graph.DFSComplete();
    }

    //------------------------------------------------------------------------------------------------------------------

    /*
      - Definition :
       Degree centrality assigns an importance score based simply on the number of links held by each node
      -------------------------------------------------------------------------------------------
       for finding very connected individuals, popular individuals ...

    */

    public ArrayList<ArrayList<Node>> degreeCentrality() {

        //store all result in degree array list
        ArrayList<ArrayList<Node>> degree = new ArrayList<>();
        centralizationDegreeComponents = new HashMap<>();

        // for each component
        for (String componentKey : graph.getComponents().keySet()) {
            Set<Vertex<User>> component = graph.getComponents().get(componentKey);
            ArrayList<Node> centralityValue = new ArrayList<>();

            // for each vertex of component find a degree and add new node contain this user and the degree
            for (Vertex<User> vertex : component) {
                int incidentNodes = graph.getEdgesOfGivenVertex(vertex).size();
                centralityValue.add(new Node( vertex.getElement() , incidentNodes));  // ???
            }

            // sorts the nodes according to its degree centrality
            ArrayList<Node> results = new ArrayList<>();
            centralityValue.sort(Collections.reverseOrder());

            // add centralityValue into result
            Iterator<Node> it = centralityValue.iterator();
            while (it.hasNext() && results.size() < 5) {
                results.add(it.next());
            }
            degree.add(results);
        }
        return degree;
    }

    //------------------------------------------------------------------------------------------------------------------

    /*
      - Definition : ( find influencers )
      calculates the shortest paths between all nodes,
      then assigns each node a score based on its sum of the shortest paths.
      -------------------------------------------------------------------------------------------
      is calculated by analyzing each component of the graph using bfs algorithm

    */

    public ArrayList<ArrayList<Node>> closenessCentrality() {

        // store all result in degree array list
        ArrayList<ArrayList<Node>> closeness = new ArrayList<>();

        // for each component
        for (Set<Vertex<User>> component : graph.getComponents().values()) {
            ArrayList<Node> centralityValue = new ArrayList<>();

            for (Vertex<User> source : component) {
                HashMap<Vertex<User>, Integer> distance = new HashMap<>();
                Queue<Vertex<User>> queue = new LinkedList<>();
                distance.put(source, 0);
                queue.add(source);

                while (!queue.isEmpty()) {
                    Vertex<User> u = queue.poll();
                    for (Vertex<User> v : graph.getEdgesOfGivenVertex(u).keySet()) {
                        if (!distance.containsKey(v) || distance.get(u) + 1 < distance.get(v)) {
                            distance.put(v, distance.get(u) + 1);
                            queue.add(v);
                        }
                    }
                }

                int totalDistance = 0;
                for (Integer nodeDistance : distance.values()) {
                    totalDistance += nodeDistance;
                }

                double CValue = Math.pow(component.size() - 1, 2) / graph.numVertices();
                CValue *= 1.0 / totalDistance;

                centralityValue.add(new Node( source.getElement(), CValue));
            }

            Collections.sort(centralityValue);
            closeness.add(centralityValue);

        }
        closenessScores = closeness;
        return closeness;

    }
    //------------------------------------------------------------------------------------------------------------------
    /*
       - Definition : ( with bfs )
        shows which nodes are ‘bridges’ between nodes in a network.It does this by identifying
        all the shortest paths and then counting how many times each node falls on one
       -------------------------------------------------------------------------------------------
       is calculated by finding the nodes with the highest betweenness centrality
       for each component of the graph

     */

    public ArrayList<ArrayList<Node>> betweennessCentrality() {
        ArrayList<ArrayList<Node>> betweenness = new ArrayList<>();

        for (Set<Vertex<User>> component : graph.getComponents().values()) {

            // stores the betweenness linkedInApp.Centrality value for each node
            HashMap<Vertex<User>, Double> centrality = new HashMap<>();

            for (Vertex<User> source : component) {

                Stack<Vertex<User>> stack = new Stack<>();
                Queue<Vertex<User>> queue = new LinkedList<>();
                HashMap<Vertex<User>, ArrayList<Vertex<User>>> precedingNode = new HashMap<>();
                HashMap<Vertex<User>, Integer> distance = new HashMap<>();
                HashMap<Vertex<User>, Integer> paths = new HashMap<>();

                queue.add(source);
                paths.put(source, 1);
                distance.put(source, 0);

                while (!queue.isEmpty()) {
                    Vertex<User> current = queue.poll();

                    // used for the dependency accumulation algorithm later
                    stack.push(current);

                    for (Vertex<User> node : graph.getEdgesOfGivenVertex(current).keySet()) {

                        // node is founded for the first time
                        if (!distance.containsKey(node)) {
                            queue.add(node);
                            distance.put(node, distance.get(current) + 1);
                            paths.put(node, paths.get(current));
                            ArrayList<Vertex<User>> parent = new ArrayList<>();
                            parent.add(current);
                            precedingNode.put(node, parent);

                            // found another SP for existing node
                        } else if (distance.get(node) == distance.get(current) + 1) {
                            paths.put(node, (paths.get(node) + paths.get(current)));
                            ArrayList<Vertex<User>> precede = precedingNode.get(node);
                            precede.add(current);
                            precedingNode.put(node, precede);
                        }
                    }
                }
                /*
                  Runs Brandes' dependency accumulation algorithm to compute
                  the betweenness centrality for each node.
                 */
                HashMap<Vertex<User>, Double> dependency = new HashMap<>();
                for (Vertex<User> node : component) {
                    dependency.put(node, 0.0);
                }
                while (!stack.isEmpty()) {
                    Vertex<User> current = stack.pop();
                    if (current != source) {
                        for (Vertex<User> node : precedingNode.get(current)) {
                            double result = ((double) paths.get(node)
                                    / paths.get(current)) * (1 + dependency.get(current));
                            dependency.put(node, dependency.get(node) + result);
                        }
                        if (!centrality.containsKey(current)) {
                            // divided by 2 due to the graph's undirected nature
                            centrality.put(current, dependency.get(current) / 2);
                        } else {
                            centrality.put(current, centrality.get(current)
                                    + dependency.get(current) / 2);
                        }
                    }
                }
            }


            ArrayList<Node> centralityValue = new ArrayList<>();
            for (Vertex<User> node : centrality.keySet()) {
                centralityValue.add(new Node( node.getElement(), centrality.get(node)));
            }
            centralityValue.sort(Collections.reverseOrder());

            betweenness.add(centralityValue);
        }

        betweennessScores = betweenness;
        return betweenness;
    }
    //------------------------------------------------------------------------------------------------------------------

    /*
       - Definition : ( with bfs )
       the relative influence of a node within a network by measuring the number of
       the immediate neighbors (first degree nodes) and also all other nodes in the network
       that connect to the node under consideration through these immediate neighbors .
       -------------------------------------------------------------------------------------------
       Each node is given a theoretical weight which can calculated by α ^ k where α is an
       attenuation factor between 0 and 1, and k is the level of the node from a given source node.

     */
    public ArrayList<ArrayList<Node>> katzCentrality(double alpha) {

        ArrayList<ArrayList<Node>> katz = new ArrayList<>();

        // for each component
        for (Set<Vertex<User>> component : graph.getComponents().values()) {
            ArrayList<Node> centralityValue = new ArrayList<>();

            // for each vertex in this component
            for (Vertex<User> source : component) {

                HashMap<Vertex<User>, Integer> level = new HashMap<>();

                // put in queue
                Queue<Vertex<User>> queue = new LinkedList<>();
                level.put(source, 0);

                // stores the Katz centrality value of the source node
                double centrality = 0;
                Vertex<User> current = source;

                // for each component calculate alpha ^ ( level of this vertex + 1 )
                while (level.size() != component.size()) {
                    for (Vertex<User> node : graph.getEdgesOfGivenVertex(current).keySet()) {
                        if (!level.containsKey(node)) {
                            int newLevel = level.get(current) + 1;
                            centrality += Math.pow(alpha, newLevel);
                            // put current node in queue and put it in level HashMap with newLevel value
                            level.put(node, newLevel);
                            queue.add(node);
                        }
                    }
                    if (!queue.isEmpty()) {
                        current = queue.poll();
                    }

                }
                centralityValue.add(new Node( source.getElement() , centrality));

            }
            centralityValue.sort(Collections.reverseOrder());
            katz.add(centralityValue);
        }

        katzScores = katz;
        return katz;
    }
}
