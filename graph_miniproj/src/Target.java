public class Target {
    private User trg;
    private int dst;
    public Target(User trg, int dst) {
        this.trg = trg;
        this.dst = dst;
    }

    public User getTrg() {
        return trg;
    }

    public int getDst() {
        return dst;
    }
}
