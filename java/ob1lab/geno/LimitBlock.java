package ob1lab.geno;

public class LimitBlock {
    public final String id;
    public final String viewName;
    public final int limit;

    public LimitBlock(String id, String viewName, int amount) {
        this.id = id;
        this.viewName = viewName;
        this.limit = amount;
    }
}
