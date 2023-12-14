public class ClientStat {
    private String username;
    private int totalTrees;
    private double totalDueAmount;
    private double totalPaidAmount;

    public ClientStat() {
    }

    public ClientStat(String username, int totalTrees, double totalDueAmount, double totalPaidAmount) {
        this.username = username;
        this.totalTrees = totalTrees;
        this.totalDueAmount = totalDueAmount;
        this.totalPaidAmount = totalPaidAmount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTotalTrees() {
        return totalTrees;
    }

    public void setTotalTrees(int totalTrees) {
        this.totalTrees = totalTrees;
    }

    public double getTotalDueAmount() {
        return totalDueAmount;
    }

    public void setTotalDueAmount(double totalDueAmount) {
        this.totalDueAmount = totalDueAmount;
    }

    public double getTotalPaidAmount() {
        return totalPaidAmount;
    }

    public void setTotalPaidAmount(double totalPaidAmount) {
        this.totalPaidAmount = totalPaidAmount;
    }
}
