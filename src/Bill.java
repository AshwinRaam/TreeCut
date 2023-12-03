import java.sql.Timestamp;

public class Bill {
    int billID;
    int orderID;
    double amount;
    String status;
    Timestamp createdAt;

    public Bill() {
    }

    public Bill(int billID, int orderID, double amount, String status, Timestamp createdAt) {
        this.billID = billID;
        this.orderID = orderID;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getBillID() {
        return billID;
    }

    public void setBillID(int billID) {
        this.billID = billID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
