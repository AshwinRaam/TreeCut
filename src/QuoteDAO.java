import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QuoteDAO {
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public QuoteDAO() {}
	
	/** 
	 * @see HttpServlet#HttpServlet()
     */
    protected void connect_func() throws SQLException {
    	//uses default connection to the database
        if (connect == null || connect.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            connect = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/treecut?serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false&user=john&password=pass1234");
            System.out.println(connect);
        }
    }
    
	//connect to the database 
    public void connect_func(String username, String password) throws SQLException {
        if (connect == null || connect.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            connect = (Connection) DriverManager
  			      .getConnection("jdbc:mysql://127.0.0.1:3306/treecut?"
  			          + "useSSL=false&user=" + username + "&password=" + password);
            System.out.println(connect);
        }
    }
    
    public Quote getQuote(int quoteID) throws SQLException {
    	System.out.println(String.format("--getQuote(%d) invoked--", quoteID));
    	String sql = "SELECT * FROM Quotes WHERE quoteID = ?;";
    	connect_func();
    	preparedStatement = connect.prepareStatement(sql);
    	preparedStatement.setInt(1, quoteID);
    	resultSet = preparedStatement.executeQuery();
    	
    	Quote quote = new Quote();
    	if(resultSet.next()) {
    		System.out.println("Found quote...");
    		int clientID = resultSet.getInt("clientID");
			int contractorID = resultSet.getInt("contractorID");
    		double initialPrice = resultSet.getDouble("initialPrice");
			double currentPrice = resultSet.getDouble("currentPrice");
			double acceptedPrice = resultSet.getDouble("acceptedPrice");
    		Timestamp startTime = resultSet.getTimestamp("startTime");
    		Timestamp endTime = resultSet.getTimestamp("endTime");
    		String status = resultSet.getString("status");
    		String note = resultSet.getString("note");
    		Timestamp createdAt = resultSet.getTimestamp("createdAt");
    		Timestamp UpdatedAt = resultSet.getTimestamp("updatedAt");
    		Timestamp updatedAt = null;
    		if (UpdatedAt != null)
    			updatedAt = resultSet.getTimestamp("updatedAt");
    		
    		quote = new Quote(quoteID, clientID,contractorID, initialPrice,currentPrice,acceptedPrice, startTime, endTime, status, note, createdAt, updatedAt);
    	}
    	
    	System.out.println("--End getQuote()--");
    	return quote;
    }

	public List<Quote> listQuotesByUsername(String username) throws SQLException {
		List<Quote> listQuotes = new ArrayList<>();

		// SQL query to get user ID from username
		String userIdSql = "SELECT userID FROM Users WHERE username = ?";

		// SQL query to get quotes for that user ID
		String quotesSql = "SELECT * FROM Quotes WHERE clientID = ?";

		connect_func(); // Establish database connection

		int userId = -1;
		// Prepare statement to fetch user ID
		PreparedStatement userIdStatement = connect.prepareStatement(userIdSql);
		userIdStatement.setString(1, username);
		ResultSet userIdResult = userIdStatement.executeQuery();

		if (userIdResult.next()) {
			userId = userIdResult.getInt("userID");
		}
		userIdResult.close();
		userIdStatement.close();

		if (userId != -1) {
			// Prepare statement to fetch quotes using the fetched user ID
			PreparedStatement quotesStatement = connect.prepareStatement(quotesSql);
			quotesStatement.setInt(1, userId);
			ResultSet quotesResultSet = quotesStatement.executeQuery();

			while (quotesResultSet.next()) {
				Quote quote = new Quote();
				quote.setQuoteID(quotesResultSet.getInt("quoteID"));
				quote.setClientID(quotesResultSet.getInt("clientID"));
				quote.setContractorID(quotesResultSet.getInt("contractorID"));
				quote.setInitialPrice(quotesResultSet.getDouble("initialPrice"));
				quote.setCurrentPrice(quotesResultSet.getDouble("currentPrice"));
				quote.setAcceptedPrice(quotesResultSet.getDouble("acceptedPrice"));
				quote.setStartTime(quotesResultSet.getTimestamp("startTime"));
				quote.setEndTime(quotesResultSet.getTimestamp("endTime"));
				quote.setStatus(quotesResultSet.getString("status"));
				quote.setNote(quotesResultSet.getString("note"));
				quote.setCreatedAt(quotesResultSet.getTimestamp("createdAt"));
				quote.setUpdatedAt(quotesResultSet.getTimestamp("updatedAt"));
				listQuotes.add(quote);
			}


			quotesResultSet.close();
			quotesStatement.close();
		}

		return listQuotes;
	}

	/**
	 * Insert a brand new quote into the quotes table.
	 * @param quote
	 * @return The new quote's ID if inserted, -1 if it was not inserted.
	 * @throws SQLException
	 */
	public int insertQuote(Quote quote) throws SQLException {
		String sql = "INSERT IGNORE INTO quotes (clientID, startTime, endTime, status, note, createdAt) " +
				"VALUES (?, ?, ?, ?, ?, NOW())";

		connect_func();
		preparedStatement = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setInt(1, quote.getClientID());
		preparedStatement.setTimestamp(2, quote.getStartTime());
		preparedStatement.setTimestamp(3, quote.getEndTime());
		System.out.println("aaaaa");
		preparedStatement.setString(4, "Requested");
		preparedStatement.setString(5, quote.getNote());
		preparedStatement.execute();

		resultSet = preparedStatement.getGeneratedKeys();
		if (resultSet.next())
			return resultSet.getInt(1);
		else
			return -1;
	}
	public void acceptQuote(int quoteID) throws SQLException {
		updateStatus(quoteID, "Accepted");
	}

	public void rejectQuote(int quoteID) throws SQLException {
		updateStatus(quoteID, "Rejected");
	}

	private void updateStatus(int quoteID, String status) throws SQLException {
		String sql = "UPDATE quotes SET status=?, updatedAt=NOW() WHERE quoteID = ?";

		connect_func();
		preparedStatement = connect.prepareStatement(sql);
		preparedStatement.setString(1, status);
		preparedStatement.setInt(2, quoteID);
		preparedStatement.executeUpdate();
	}
}
