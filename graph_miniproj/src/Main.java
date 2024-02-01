import ds.graph.AdjacencyMapGraph;
import ds.graph.Edge;
import ds.graph.Graph;
import ds.graph.Vertex;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        GraphHandler gh = new GraphHandler();
        /*
        AdjacencyMapGraph<String, Integer> g = new AdjacencyMapGraph<>(false);
        g.insertVertex("a");
        g.insertVertex("b");
        g.insertVertex("c");
        g.insertVertex("d");
        Vertex<String> vert, vert1;
        for(Vertex<String> v: g.vertices()) {
            if(v.getElement().equals("a")) {
                vert = v;
            }
        }
        for(Vertex<String> v: g.vertices()) {
            if(v.getElement().equals("b")) {
                vert1 = v;
            }
        }
        g.insertEdge(vert, vert1);
        for(Vertex<String> v: g.vertices()) {
            if(v.getElement().equals("c")) {
                vert = v;
            }
        }
        for(Vertex<String> v: g.vertices()) {
            if(v.getElement().equals("d")) {
                vert = v;
            }
        }
         */

        /*
        gh.readFile();
        gh.insertConnections();
        ArrayList<User> users = gh.getSuggestion("3");
        int count = 0;
        for(int i=0; i<users.size() && count++<20; i++) {
            System.out.println(users.get(i).getId());
        }
         */
    }
}
