import java.sql.Timestamp;

public class BillResponse {
    private int responseID;
    private int billID;
    private int userID;
    private String note;
    private double newAmount;
    private Timestamp createdAt;

    public BillResponse() { }

    public BillResponse(int responseID, int billID, int userID, String note, double newAmount, Timestamp createdAt) {
        this.responseID = responseID;
        this.billID = billID;
        this.userID = userID;
        this.note = note;
        this.newAmount = newAmount;
        this.createdAt = createdAt;
    }

    public int getResponseID() {
        return responseID;
    }

    public void setResponseID(int responseID) {
        this.responseID = responseID;
    }

    public int getBillID() {
        return billID;
    }

    public void setBillID(int billID) {
        this.billID = billID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public double getNewAmount() {
        return newAmount;
    }

    public void setNewAmount(double newAmount) {
        this.newAmount = newAmount;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
