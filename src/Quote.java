import java.time.LocalDateTime;

public class Quote {
    private int quoteID;
    private int clientID;
    private int contractorID;
    private double initialPrice;

    private double currentPrice;
    private double acceptedPrice;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    private String note;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

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

    public Quote(int quoteID, int clientID, int contractorID,
                 String status, String note, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.quoteID = quoteID;
        this.clientID = clientID;
        this.contractorID = contractorID;
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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }


}
