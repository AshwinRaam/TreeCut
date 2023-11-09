import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
    	String sql = "SELECT * FROM quotes WHERE quoteID = ?;";
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
    		LocalDateTime startTime = resultSet.getTimestamp("startTime").toLocalDateTime();
    		LocalDateTime endTime = resultSet.getTimestamp("endTime").toLocalDateTime();
    		String status = resultSet.getString("status");
    		String note = resultSet.getString("note");
    		LocalDateTime createdAt = resultSet.getTimestamp("createdAt").toLocalDateTime();
    		Timestamp UpdatedAt = resultSet.getTimestamp("updatedAt");
    		LocalDateTime updatedAt = null;
    		if (UpdatedAt != null)
    			updatedAt = resultSet.getTimestamp("updatedAt").toLocalDateTime();
    		
    		quote = new Quote(quoteID, clientID,contractorID, initialPrice,currentPrice,acceptedPrice, startTime, endTime, status, note, createdAt, updatedAt);
    	}
    	
    	System.out.println("--End getQuote()--");
    	return quote;
    }
}
