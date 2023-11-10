import java.time.LocalDateTime;

public class QuoteResponse {
	private int responseID;
	private int quoteID;
	private int userID;
	private String fullName;
	private double modifiedPrice;
	private LocalDateTime modifiedStartTime;
	private LocalDateTime modifiedEndTime;
	private String note;
	private LocalDateTime createdAt;
	
	public QuoteResponse() { }
	
	public QuoteResponse(int responseID, int quoteID, int userID, String username, double modifiedPrice, LocalDateTime modifiedStartTime,
			LocalDateTime modifiedEndTime, String note, LocalDateTime createdAt) {
		this.responseID = responseID;
		this.quoteID = quoteID;
		this.userID = userID;
		this.fullName = username;
		this.modifiedPrice = modifiedPrice;
		this.modifiedStartTime = modifiedStartTime;
		this.modifiedEndTime = modifiedEndTime;
		this.note = note;
		this.createdAt = createdAt;
	}

	public int getResponseID() {
		return responseID;
	}

	public void setResponseID(int responseID) {
		this.responseID = responseID;
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

	public String getFullName() { return fullName; }

	public void setFullName(String fullName) { this.fullName = fullName; }

	public double getModifiedPrice() {
		return modifiedPrice;
	}

	public void setModifiedPrice(double modifiedPrice) {
		this.modifiedPrice = modifiedPrice;
	}

	public LocalDateTime getModifiedStartTime() {
		return modifiedStartTime;
	}

	public void setModifiedStartTime(LocalDateTime modifiedStartTime) {
		this.modifiedStartTime = modifiedStartTime;
	}

	public LocalDateTime getModifiedEndTime() {
		return modifiedEndTime;
	}

	public void setModifiedEndTime(LocalDateTime modifiedEndTime) {
		this.modifiedEndTime = modifiedEndTime;
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
}
