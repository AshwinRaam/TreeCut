import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Quote {
    private int quoteID;
    private int clientID;
    private int contractorID;
    private double initialPrice;

    private double currentPrice;
    private double acceptedPrice;
    private Timestamp startTime;
    private Timestamp endTime;
    private String status;
    private String note;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Quote() {
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public int getContractorID() {
        return contractorID;
    }

    public void setContractorID(int contractorID) {
        this.contractorID = contractorID;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getAcceptedPrice() {
        return acceptedPrice;
    }

    public void setAcceptedPrice(double acceptedPrice) {
        this.acceptedPrice = acceptedPrice;
    }

    public Quote(int quoteID, int clientID, int contractorID, double initialPrice, double currentPrice, double acceptedPrice, Timestamp startTime, Timestamp endTime,
                 String status, String note, Timestamp createdAt, Timestamp updatedAt) {
        this.quoteID = quoteID;
        this.clientID = clientID;
        this.contractorID = contractorID;
        this.initialPrice = initialPrice;
        this.currentPrice = currentPrice;
        this.acceptedPrice = acceptedPrice;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.note = note;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getQuoteID() {
        return quoteID;
    }

    public void setQuoteID(int quoteID) {
        this.quoteID = quoteID;
    }

    public double getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(double initialPrice) {
        this.initialPrice = initialPrice;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }


}
