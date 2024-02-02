import ds.graph.Vertex;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        GraphHandler gh = new GraphHandler();

        User currentUser;

        // read json and insert connection
        gh.readFile();
        gh.insertConnections();

        while(true) {
            System.out.println("> Enter your id ... ");
            String id = sc.next();
            Vertex<User> vertex = gh.getUserVert(id);
            // check this user exist
            if( vertex != null) {
                // set current
                currentUser = vertex.getElement();
                System.out.println( "* Welcome " + currentUser.getName() );
                System.out.println();
                System.out.println("* Your Info : ");
                System.out.println(currentUser);
                System.out.println();


                boolean signIn = true;

                // menu
                while (signIn) {
                    printUserMenu();
                    int command = sc.nextInt();
                    switch (command) {
                        case 1 -> {
                            System.out.println("* popular : ");
                            ArrayList<Vertex<User>> popularUsers = gh.getPopularUsers();
                            for (Vertex<User> popularUser : popularUsers) {
                                System.out.println(popularUser.getElement());
                            }
                        }
                        case 2 -> {
                            System.out.println("* suggestion : ");
                            ArrayList<User> users = gh.getSuggestion(currentUser.getId());
                            int count1 = 0;
                            for (int i = 0; i < users.size() && count1++ < 20; i++) {
                                System.out.println(users.get(i));
                            }
                        }
                        case 3 -> {
                            System.out.println("* Influencer : ");
                            ArrayList<Vertex<User>> influenceUsers = gh.getInfluenceUsers();
                            for (Vertex<User> influenceUser : influenceUsers) {
                                System.out.println(influenceUser.getElement());
                            }
                        }
                        case 4 -> {
                            signIn = false;
                        }
                    }
                }
            }
        }

    }

    public static void printUserMenu(){
        System.out.println("""
                    --- Menu ----
                    [1] popular
                    [2] suggestion
                    [3] influencer
                    [4] EXIT
                    """);
    }
}
