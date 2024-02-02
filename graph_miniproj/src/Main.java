import ds.graph.AdjacencyMapGraph;
import ds.graph.Edge;
import ds.graph.Graph;
import ds.graph.Vertex;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        GraphHandler gh = new GraphHandler();

//        AdjacencyMapGraph<String, Integer> g = new AdjacencyMapGraph<>(false);
//        Vertex<String> a = g.insertVertex("a");
//        Vertex<String> b =g.insertVertex("b");
//        Vertex<String> c =g.insertVertex("c");
//        Vertex<String> d =g.insertVertex("d");
//        Vertex<String> e =g.insertVertex("e");
//        g.insertEdge(a , b , 1);
//        g.insertEdge(a , c , 2);
//        //g.insertEdge(b , d , 3);
//        g.insertEdge(d , e , 4);
//
//        Map<Map<Vertex<String>, Edge<Integer>>, Integer> map = g.BFS(a , 0 , 5);
//        g.DFSComplete();
//        Map<String, Set<Vertex<String>>> ff = g.getComponents();
//
//        System.out.println("....");



        gh.readFile();
        gh.insertConnections();
        int b = 0;
        for(Edge<Connection> c : gh.getG().edges()){
            b++;
        }
        System.out.println(b);
        ArrayList<User> users = gh.getSuggestion("3");

        System.out.println("* suggestion : ");
        int count = 0;
        for(int i=0; i<users.size() && count++<20; i++) {
            System.out.println(users.get(i).getId());
        }

        System.out.println();
        System.out.println("* Influencer : ");
        ArrayList<Vertex<User>> influenceUsers = gh.getInfluenceUsers();
        for(int i=0; i<influenceUsers.size(); i++) {
            System.out.println(influenceUsers.get(i).getElement().getId());
        }

    }
}
