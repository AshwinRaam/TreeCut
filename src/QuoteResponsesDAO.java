import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QuoteResponsesDAO {
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public QuoteResponsesDAO() { }
	
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
    
    public List<QuoteResponse> GetResponses(int quoteID) throws SQLException {
    	List<QuoteResponse> responses = new ArrayList<QuoteResponse>();
    	
    	String sql = "SELECT * FROM quoteresponses WHERE quoteID = ?;";
    	connect_func();
    	preparedStatement = connect.prepareStatement(sql);
    	preparedStatement.setInt(1, quoteID);
    	resultSet = preparedStatement.executeQuery();

		UserDAO UserDAO = new UserDAO();
    	while(resultSet.next()) {
    		int responseID = resultSet.getInt("responseID");
    		//int quoteID; //already in method parameter
    		int userID = resultSet.getInt("userID");
			User user = UserDAO.getUser(userID);
			String fullName = user.getFirstName() + " " + user.getLastName();
			/* yes, this could be smaller, and more efficient, but getUser(userID) seems more versatile */
    		double modifiedPrice = resultSet.getDouble("modifiedPrice");

			LocalDateTime modifiedStartTime = null, modifiedEndTime = null;
			Timestamp tModifiedStartTime = resultSet.getTimestamp("modifiedStartTime");
			if (tModifiedStartTime != null) {
				modifiedStartTime = tModifiedStartTime.toLocalDateTime();
			}
			Timestamp tModifiedEndTime = resultSet.getTimestamp("modifiedEndTime");
			if (tModifiedEndTime != null) {
				modifiedEndTime = tModifiedEndTime.toLocalDateTime();
			}

			String note = resultSet.getString("note");
    		LocalDateTime createdAt = resultSet.getTimestamp("createdAt").toLocalDateTime();
    		
    		QuoteResponse response = new QuoteResponse(responseID, quoteID, userID, fullName, modifiedPrice,
					modifiedStartTime, modifiedEndTime, note, createdAt);
    		responses.add(response);
    	}
    	resultSet.close();
    	
    	return responses;
    }
    
    public void PostResponse(QuoteResponse response) throws SQLException {
    	System.out.println("Posting quote response...");
    	Timestamp tModStartTime = null, tModEndTime = null;
    	if (response.getModifiedStartTime() != null)
    		tModStartTime = Timestamp.valueOf(response.getModifiedStartTime());
    	if (response.getModifiedStartTime() != null)
    		tModEndTime = Timestamp.valueOf(response.getModifiedEndTime());
    	
    	String sql = "INSERT INTO quoteresponses (quoteID, userID, modifiedPrice, modifiedStartTime, modifiedEndTime, " +
				"note, createdAt) VALUES (?, ?, ?, ?, ?, ?, NOW());";
    	connect_func();
    	preparedStatement = connect.prepareStatement(sql);
    	preparedStatement.setInt(1, response.getQuoteID());
    	preparedStatement.setInt(2, response.getUserID());
    	if (response.getModifiedPrice() > 0)
    		preparedStatement.setDouble(3, response.getModifiedPrice());
    	else
    		preparedStatement.setNull(3, Types.INTEGER);
    	preparedStatement.setTimestamp(4, tModStartTime);
    	preparedStatement.setTimestamp(5, tModEndTime);
    	preparedStatement.setString(6, response.getNote());
    	int result = preparedStatement.executeUpdate();
    	
    	if (result < 1) {
    		System.out.println("Error in inserting quote response");
    		return;
    	}
    	
    	System.out.println("Posting finished");
    }
}
