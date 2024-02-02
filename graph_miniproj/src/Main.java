import ds.graph.AdjacencyMapGraph;
import ds.graph.Edge;
import ds.graph.Graph;
import ds.graph.Vertex;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        GraphHandler gh = new GraphHandler();

        gh.readFile();
        gh.insertConnections();

        ArrayList<User> users = gh.getSuggestion(sc.next());

        System.out.println("* popular : ");
        int count = 0;
        for(int i=0; i<users.size() && count++<20; i++) {
            System.out.println(users.get(i).getId());
        }

        System.out.println("* suggestion : ");
        int count1 = 0;
        for(int i=0; i<users.size() && count1++<20; i++) {
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
