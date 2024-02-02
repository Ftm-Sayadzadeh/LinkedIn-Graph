import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        GraphHandler gh = new GraphHandler();

        /*
        ArrayList<User> us = new ArrayList<>();
        User u1 = new User("2", "hana", "1212/10/3", "LH", "AA", "re", new ArrayList<>(), new HashSet<>());
        User u2 = new User("3", "hana1", "1212/10/3", "LH", "BB", "re", new ArrayList<>(), new HashSet<>());
        User t = new User("4", "hana1", "1212/10/3", "LH", "BB", "re", new ArrayList<>(), new HashSet<>());
        us.add(u1);
        u1.setTarget(new Target(t, 2));
        us.add(u2);
        u2.setTarget(new Target(t, 2));
        Collections.sort(us);
        for(User u: us) {
            System.out.println(u.getId());
        }
         */
        /*
        AdjacencyMapGraph<String, Integer> g = new AdjacencyMapGraph<>(false);
        ArrayList<Vertex<String>> vertices = new ArrayList<>();

        Vertex<String> a = g.insertVertex("a");
        Vertex<String> b = g.insertVertex("b");
        Vertex<String> c = g.insertVertex("c");
        Vertex<String> d = g.insertVertex("d");
        Vertex<String> e = g.insertVertex("e");
        Vertex<String> f = g.insertVertex("f");
        Vertex<String> g1 = g.insertVertex("g");
        Vertex<String> h = g.insertVertex("h");
        Vertex<String> i = g.insertVertex("i");
        Vertex<String> j = g.insertVertex("j");
        Vertex<String> k = g.insertVertex("k");
        Vertex<String> l = g.insertVertex("l");

        g.insertEdge(a, b, 1);
        g.insertEdge(a, c, 2);
        g.insertEdge(b, d, 3);
        g.insertEdge(b, e, 4);
        g.insertEdge(d, f, 5);
        g.insertEdge(e, g1, 6);
        g.insertEdge(e, h, 7);
        g.insertEdge(g1, i, 8);
        g.insertEdge(g1, j, 9);
        g.insertEdge(h, k, 10);
        g.insertEdge(i, l, 11);
        g.insertEdge(j, l, 12);

        Map<Map<Vertex<String>, Edge<Integer>>, Integer> s = g.BFS(a);
        for(Map.Entry<Map<Vertex<String>, Edge<Integer>>, Integer> s1: s.entrySet()) {
            for(Map.Entry<Vertex<String>, Edge<Integer>> s2: s1.getKey().entrySet()) {
                System.out.println(s2.getKey().getElement() + " " + s1.getValue());
            }
        }
         */

        // {"id":"2","name":"aryan Li","dateOfBirth":"1370/10/17","universityLocation":"CD","field":"BB","workplace":"Digikala","specialties":["Java","Fast Type"],"connectionId":["783","344","885","678","627"]},{"id":"3","name":"pujman Ho","dateOfBirth":"1373/4/30","universityLocation":"GH","field":"AA","workplace":"Google","specialties":["C#","Python"],"connectionId":["231","672","302","502","42"]},{"id":"4","name":"pareechehr Davies","dateOfBirth":"1370/12/2","universityLocation":"EF","field":"BB","workplace":"Intel","specialties":["Java","C++"],"connectionId":["796","721","667","459","294"]},{"id":"5","name":"aref Davis","dateOfBirth":"1367/4/20","universityLocation":"EF","field":"DD","workplace":"Intel","specialties":["Data","Machine Learning"],"connectionId":["479","700"]}
        gh.readFile();
        gh.insertConnections();
        ArrayList<User> users = gh.getSuggestion("2");
        int count = 0;
        for(int i=0; i<users.size() && count++<20; i++) {
            System.out.println(users.get(i));
        }
    }
}
