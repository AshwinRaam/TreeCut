import java.sql.Timestamp;

public class Order {
    private int orderID;
    private int quoteID;
    private String status;
    private Timestamp workDate;
    private Timestamp createdAt;

    public Order() { }

    public Order(int orderID, int quoteID, String status, Timestamp workDate, Timestamp createdAt) {
        this.orderID = orderID;
        this.quoteID = quoteID;
        this.status = status;
        this.workDate = workDate;
        this.createdAt = createdAt;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getQuoteID() {
        return quoteID;
    }

    public void setQuoteID(int quoteID) {
        this.quoteID = quoteID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Timestamp workDate) {
        this.workDate = workDate;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
