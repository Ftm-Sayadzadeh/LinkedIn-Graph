package model;

public class Node implements Comparable<Node> {
    // fields
    private User user;
    private double value;

    // cons
    public Node(User user, double value) {
        this.user = user;
        this.value = value;
    }

    // get & set
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public int compareTo(Node node) {
        return Double.compare(value, node.value);
    }
}
