import java.time.LocalDateTime;

public class Quote {
	private int quoteID;
	private int userID;
	private double initialPrice;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private String status;
	private String note;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	public Quote() { }

	public Quote(int quoteID, int userID, double initialPrice, LocalDateTime startTime, LocalDateTime endTime,
			String status, String note, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.quoteID = quoteID;
		this.userID = userID;
		this.initialPrice = initialPrice;
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

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
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
