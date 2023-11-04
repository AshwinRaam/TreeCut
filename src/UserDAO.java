import java.io.FileNotFoundException;
import java.io.IOException;
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
import java.sql.PreparedStatement;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 * Servlet implementation class Connect
 */
@WebServlet("/userDAO")
public class UserDAO 
{
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public UserDAO(){}
	
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
            connect = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/TreeCut?serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false&user=john&password=pass1234");
            System.out.println(connect);
        }
    }
    
    public boolean database_login(String email, String password) throws SQLException{
    	try {
    		connect_func("root","pass1234");
    		String sql = "select * from user where email = ?";
    		preparedStatement = connect.prepareStatement(sql);
    		preparedStatement.setString(1, email);
    		ResultSet rs = preparedStatement.executeQuery();
    		return rs.next();
    	}
    	catch(SQLException e) {
    		System.out.println("failed login");
    		return false;
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
  			      .getConnection("jdbc:mysql://127.0.0.1:3306/userdb?"
  			          + "useSSL=false&user=" + username + "&password=" + password);
            System.out.println(connect);
        }
    }
    
    public List<User> listAllUsers() throws SQLException {
        List<User> listUser = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        connect_func();
        statement = connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            int userID = resultSet.getInt("userID");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            String role = resultSet.getString("role");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String address = resultSet.getString("address");
            String phoneNumber = resultSet.getString("phoneNumber");
            String email = resultSet.getString("email");
            String createdAt = resultSet.getString("createdAt");
            String updatedAt = resultSet.getString("updatedAt");

            User user = new User(username, password, role, firstName, lastName, address, phoneNumber, email);
            user.setUserID(userID);
            user.setCreatedAt(createdAt);
            user.setUpdatedAt(updatedAt);
            listUser.add(user);
        }

        resultSet.close();
        statement.close();
        disconnect();

        return listUser;
    }
    
    protected void disconnect() throws SQLException {
        if (connect != null && !connect.isClosed()) {
        	connect.close();
        }
    }
    
    public void insert(User user) throws SQLException {
        connect_func("root", "pass1234");
        
        String sql = "INSERT INTO Users (username, password, role, firstName, lastName, address, phoneNumber, email, creditCardInfo,createdAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";
        preparedStatement = connect.prepareStatement(sql);
        
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getRole());
        preparedStatement.setString(4, user.getFirstName());
        preparedStatement.setString(5, user.getLastName());
        preparedStatement.setString(6, user.getAddress());
        preparedStatement.setString(7, user.getPhoneNumber());
        preparedStatement.setString(8, user.getEmail());
        preparedStatement.setString(9, user.getCreditCardInfo());
//        preparedStatement.setString(9, user.getCreatedAt());
//        preparedStatement.setString(10, user.getUpdatedAt());

        preparedStatement.executeUpdate();
        preparedStatement.close();
        connect.close();
    }
    
    public boolean delete(String email) throws SQLException {
        String sql = "DELETE FROM Users WHERE email = ?";        
        connect_func();
         
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, email);
         
        boolean rowDeleted = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
        return rowDeleted;     
    }
     
    public boolean update(User user) throws SQLException {
        String sql = "UPDATE Users SET username=?, password=?, role=?, firstName=?, lastName=?, address=?, phoneNumber=?, email=?, createdAt=?, updatedAt=? WHERE userID=?";
        connect_func();

        preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getRole());
        preparedStatement.setString(4, user.getFirstName());
        preparedStatement.setString(5, user.getLastName());
        preparedStatement.setString(6, user.getAddress());
        preparedStatement.setString(7, user.getPhoneNumber());
        preparedStatement.setString(8, user.getEmail());
        preparedStatement.setString(9, user.getCreatedAt());
        preparedStatement.setString(10, user.getUpdatedAt());
        preparedStatement.setInt(11, user.getUserID());

        boolean rowUpdated = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
        connect.close();
        return rowUpdated;
    }
    
    public User getUser(String username) throws SQLException {
        User user = null;
        String sql = "SELECT * FROM Users WHERE username = ?";

        connect_func();

        preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setString(1, username);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            int userID = resultSet.getInt("userID");
            //String username = resultSet.getString("username");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
            String role = resultSet.getString("role");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String address = resultSet.getString("address");
            String phoneNumber = resultSet.getString("phoneNumber");
            String createdAt = resultSet.getString("createdAt");
            String updatedAt = resultSet.getString("updatedAt");

            user = new User(username, password, role, firstName, lastName, address, phoneNumber, email);
            user.setUserID(userID);
            user.setCreatedAt(createdAt);
            user.setUpdatedAt(updatedAt);
        }

        resultSet.close();
        preparedStatement.close();
        connect.close();

        return user;
    }
    
    public boolean checkEmail(String email) throws SQLException {
    	boolean checks = false;
    	String sql = "SELECT * FROM Users WHERE email = ?";
    	connect_func();
    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
        	checks = true;
        }
        
    	return checks;
    }
    
    public boolean checkUsername(String username) throws SQLException {
    	boolean checks = false;
    	String sql = "SELECT * FROM Users WHERE username = ?";
    	connect_func();
    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
        	checks = true;
        }
        
    	return checks;
    }
    
    public boolean checkPassword(String password) throws SQLException {
    	boolean checks = false;
    	String sql = "SELECT * FROM Users WHERE password = ?";
    	connect_func();
    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        
        System.out.println(checks);	
        
        if (resultSet.next()) {
        	checks = true;
        }
        
        System.out.println(checks);
       	return checks;
    }
    
    public boolean isValid(String username, String password) throws SQLException
    {
    	String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
    	connect_func();
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();

        return (resultSet.next());
    }
    
    public boolean isClient(String username) throws SQLException {
        String sql = "SELECT role FROM Users WHERE username = ?";
        connect_func();

        try (PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String role = resultSet.getString("role");
                    return "Client".equals(role);
                }
            }
        } finally {
            if (connect != null && !connect.isClosed()) {
                connect.close();
            }
        }

        return false;
    }
    
    public void init() throws SQLException, FileNotFoundException, IOException{
    	connect_func();
        statement =  (Statement) connect.createStatement();
        
         String[] INITIAL_QUERIES = {
                "DROP DATABASE IF EXISTS TreeCut",
                "CREATE DATABASE TreeCut",
                "USE TreeCut",
                "CREATE TABLE Users (userID INT AUTO_INCREMENT PRIMARY KEY, username VARCHAR(255) UNIQUE, password VARCHAR(255) NOT NULL, role ENUM('Client', 'Admin', 'Contractor') NOT NULL, firstName VARCHAR(255) NOT NULL, lastName VARCHAR(255) NOT NULL, address VARCHAR(255) NOT NULL, phoneNumber VARCHAR(15) NOT NULL, email VARCHAR(255) NOT NULL, creditCardInfo VARCHAR(255), createdAt DATETIME NOT NULL, updatedAt DATETIME)",
                "CREATE TABLE Quotes (quoteID INT AUTO_INCREMENT PRIMARY KEY, userID INT, initialPrice DECIMAL(10, 2), startTime DATETIME, endTime DATETIME, status ENUM('Requested', 'Rejected', 'Quoted' ,'Accepted') NOT NULL, note TEXT, createdAt DATETIME NOT NULL, updatedAt DATETIME, FOREIGN KEY (userID) REFERENCES TreeCut.Users(userID))",
                "CREATE TABLE Trees (treeID INT AUTO_INCREMENT PRIMARY KEY, quoteID INT, size VARCHAR(50), height VARCHAR(50), location TEXT NOT NULL, nearHouse VARCHAR(50) NOT NULL, pictureURL1 TEXT NOT NULL, pictureURL2 TEXT NOT NULL, pictureURL3 TEXT NOT NULL, createdAt DATETIME NOT NULL, updatedAt DATETIME, FOREIGN KEY (quoteID) REFERENCES TreeCut.Quotes(quoteID))",
                "CREATE TABLE QuoteResponses (responseID INT AUTO_INCREMENT PRIMARY KEY, quoteID INT, userID INT, modifiedPrice DECIMAL(10, 2), modifiedStartTime DATETIME, modifiedEndTime DATETIME, note TEXT, createdAt DATETIME NOT NULL, FOREIGN KEY (quoteID) REFERENCES TreeCut.Quotes(quoteID), FOREIGN KEY (userID) REFERENCES TreeCut.Users(userID))",
                "CREATE TABLE Orders (orderID INT AUTO_INCREMENT PRIMARY KEY, quoteID INT, status ENUM('Pending', 'Completed') NOT NULL, createdAt DATETIME NOT NULL, FOREIGN KEY (quoteID) REFERENCES TreeCut.Quotes(quoteID))",
                "CREATE TABLE Bills (billID INT AUTO_INCREMENT PRIMARY KEY, orderID INT, amount DECIMAL(10, 2), status ENUM('Pending', 'Paid', 'Disputed') NOT NULL, createdAt DATETIME NOT NULL, FOREIGN KEY (orderID) REFERENCES TreeCut.Orders(orderID))",
                "CREATE TABLE BillResponses (responseID INT AUTO_INCREMENT PRIMARY KEY, billID INT, userID INT, note TEXT, newAmount DECIMAL(10, 2), createdAt DATETIME NOT NULL, FOREIGN KEY (billID) REFERENCES TreeCut.Bills(billID), FOREIGN KEY (userID) REFERENCES TreeCut.Users(userID))"
            };
         
         for (String query : INITIAL_QUERIES) {
             statement.execute(query);
         }
         
         System.out.println("Inital Database and tables are created");
         
         String[] usersQueries = {
        		    "INSERT INTO Users (username, password, role, firstName, lastName, address, phoneNumber, email, creditCardInfo, createdAt) VALUES ('root', 'pass1234', 'Admin', 'Sean', 'Ash', '123 Main St', '123-456-7890', 'john.doe@email.com', '4111111111111111', NOW())",
        		    "INSERT INTO Users (username, password, role, firstName, lastName, address, phoneNumber, email, creditCardInfo, createdAt) VALUES ('DavidSmith', 'securePassword', 'Contractor', 'David', 'Smith', '456 Oak St', '987-654-3210', 'david.smith@email.com', '4222222222222222', NOW())",
        		    "INSERT INTO Users (username, password, role, firstName, lastName, address, phoneNumber, email, creditCardInfo, createdAt) VALUES ('mikeSmith', 'mike1234', 'Client', 'Mike', 'Smith', '789 Pine St', '321-654-9870', 'mike.smith@email.com', '4333333333333333', NOW())",
        		    "INSERT INTO Users (username, password, role, firstName, lastName, address, phoneNumber, email, creditCardInfo, createdAt) VALUES ('sarahJohnson', 'sarahsPass', 'Client', 'Sarah', 'Johnson', '159 Maple St', '123-789-4560', 'sarah.johnson@email.com', '4444444444444444', NOW())",
        		    "INSERT INTO Users (username, password, role, firstName, lastName, address, phoneNumber, email, creditCardInfo, createdAt) VALUES ('carolJohnson', 'carolJ2023', 'Client', 'Carol', 'Johnson', '975 Oak St', '987-987-9879', 'carol.johnson@email.com', '4555555555555555', NOW())",
        		    "INSERT INTO Users (username, password, role, firstName, lastName, address, phoneNumber, email, creditCardInfo, createdAt) VALUES ('jakeWhite', 'jakeWPass', 'Client', 'Jake', 'White', '789 Birch St', '321-321-3219', 'jake.white@email.com', '4666666666666666', NOW())",
        		    "INSERT INTO Users (username, password, role, firstName, lastName, address, phoneNumber, email, creditCardInfo, createdAt) VALUES ('emilyBrown', 'emilysPass', 'Client', 'Emily', 'Brown', '159 Oak St', '123-456-7899', 'emily.brown@email.com', '4777777777777777', NOW())",
        		    "INSERT INTO Users (username, password, role, firstName, lastName, address, phoneNumber, email, creditCardInfo, createdAt) VALUES ('johnDoe', 'johnsPass', 'Client', 'John', 'Doe', '456 Main St', '987-654-3219', 'john.doe@email.com', '4888888888888888', NOW())",
        		    "INSERT INTO Users (username, password, role, firstName, lastName, address, phoneNumber, email, creditCardInfo, createdAt) VALUES ('janeDoe', 'janesPass', 'Client', 'Jane', 'Doe', '789 Oak St', '321-654-9871', 'jane.doe@email.com', '4999999999999999', NOW())",
        		    "INSERT INTO Users (username, password, role, firstName, lastName, address, phoneNumber, email, creditCardInfo, createdAt) VALUES ('brianGreen', 'brianG2023', 'Client', 'Brian', 'Green', '159 Pine St', '987-123-4560', 'brian.green@email.com', '5000000000000000', NOW())",
        		    "INSERT INTO Users (username, password, role, firstName, lastName, address, phoneNumber, email, creditCardInfo, createdAt) VALUES ('lisaWhite', 'lisaWPass', 'Client', 'Lisa', 'White', '456 Birch St', '321-321-6543', 'lisa.white@email.com', '5111111111111111', NOW())",
        		    "INSERT INTO Users (username, password, role, firstName, lastName, address, phoneNumber, email, creditCardInfo, createdAt) VALUES ('kevinBrown', 'kevinB2023', 'Client', 'Kevin', 'Brown', '159 Maple St', '123-789-3210', 'kevin.brown@email.com', '5222222222222222', NOW())",
        		    "INSERT INTO Users (username, password, role, firstName, lastName, address, phoneNumber, email, creditCardInfo, createdAt) VALUES ('susanClark', 'susanCPass', 'Client', 'Susan', 'Clark', '456 Oak St', '987-654-9872', 'susan.clark@email.com', '5333333333333333', NOW())",
        		    "INSERT INTO Users (username, password, role, firstName, lastName, address, phoneNumber, email, creditCardInfo, createdAt) VALUES ('frankMiller', 'frankMPass', 'Client', 'Frank', 'Miller', '789 Maple St', '321-321-3210', 'frank.miller@email.com', '5444444444444444', NOW())",
        		    "INSERT INTO Users (username, password, role, firstName, lastName, address, phoneNumber, email, creditCardInfo, createdAt) VALUES ('karenWhite', 'karenW2023', 'Client', 'Karen', 'White', '159 Birch St', '123-456-3214', 'karen.white@email.com', '5555555555555555', NOW())",
        		    "INSERT INTO Users (username, password, role, firstName, lastName, address, phoneNumber, email, creditCardInfo, createdAt) VALUES ('jamesJohnson', 'jamesJPass', 'Client', 'James', 'Johnson', '456 Pine St', '987-654-1234', 'james.johnson@email.com', '5666666666666666', NOW())",
        		    "INSERT INTO Users (username, password, role, firstName, lastName, address, phoneNumber, email, creditCardInfo, createdAt) VALUES ('patriciaBrown', 'patriciaB2023', 'Client', 'Patricia', 'Brown', '789 Oak St', '321-321-9876', 'patricia.brown@email.com', '5777777777777777', NOW())",
        		    "INSERT INTO Users (username, password, role, firstName, lastName, address, phoneNumber, email, creditCardInfo, createdAt) VALUES ('robertClark', 'robertCPass', 'Client', 'Robert', 'Clark', '159 Maple St', '123-789-3211', 'robert.clark@email.com', '5888888888888888', NOW())",
        		    "INSERT INTO Users (username, password, role, firstName, lastName, address, phoneNumber, email, creditCardInfo, createdAt) VALUES ('jenniferMiller', 'jenniferMPass', 'Client', 'Jennifer', 'Miller', '456 Birch St', '987-654-3211', 'jennifer.miller@email.com', '5999999999999999', NOW())",
        		    "INSERT INTO Users (username, password, role, firstName, lastName, address, phoneNumber, email, creditCardInfo, createdAt) VALUES ('williamJohnson', 'williamJ2023', 'Client', 'William', 'Johnson', '789 Pine St', '321-321-6544', 'william.johnson@email.com', '6000000000000000', NOW())"
        		};
         
         for (String query : usersQueries) {
             statement.execute(query);
         }
         
         System.out.println("Users table is inserted with initial values.");
         
         String[] quotesQueries = {
        			"INSERT INTO Quotes (userID, initialPrice, startTime, endTime, status, note, createdAt) VALUES (1, 100.00, '2023-10-26 10:00:00', '2023-10-26 12:00:00', 'Requested', 'Note1', NOW())",
        			"INSERT INTO Quotes (userID, initialPrice, startTime, endTime, status, note, createdAt) VALUES (2, 200.00, '2023-10-26 11:00:00', '2023-10-26 13:00:00', 'Requested', 'Note2', NOW())",
        			"INSERT INTO Quotes (userID, initialPrice, startTime, endTime, status, note, createdAt) VALUES (3, 300.00, '2023-10-26 12:00:00', '2023-10-26 14:00:00', 'Rejected', 'Note3', NOW())",
        			"INSERT INTO Quotes (userID, initialPrice, startTime, endTime, status, note, createdAt) VALUES (4, 150.50, '2023-10-26 13:00:00', '2023-10-26 15:00:00', 'Quoted', 'Note4', NOW())",
        			"INSERT INTO Quotes (userID, initialPrice, startTime, endTime, status, note, createdAt) VALUES (5, 250.75, '2023-10-26 14:00:00', '2023-10-26 16:00:00', 'Accepted', 'Note5', NOW())",
        			"INSERT INTO Quotes (userID, initialPrice, startTime, endTime, status, note, createdAt) VALUES (6, 350.25, '2023-10-26 15:00:00', '2023-10-26 17:00:00', 'Requested', 'Note6', NOW())",
        			"INSERT INTO Quotes (userID, initialPrice, startTime, endTime, status, note, createdAt) VALUES (7, 175.00, '2023-10-26 16:00:00', '2023-10-26 18:00:00', 'Rejected', 'Note7', NOW())",
        			"INSERT INTO Quotes (userID, initialPrice, startTime, endTime, status, note, createdAt) VALUES (8, 275.50, '2023-10-26 17:00:00', '2023-10-26 19:00:00', 'Quoted', 'Note8', NOW())",
        			"INSERT INTO Quotes (userID, initialPrice, startTime, endTime, status, note, createdAt) VALUES (9, 375.75, '2023-10-26 18:00:00', '2023-10-26 20:00:00', 'Accepted', 'Note9', NOW())",
        			"INSERT INTO Quotes (userID, initialPrice, startTime, endTime, status, note, createdAt) VALUES (10, 200.00, '2023-10-26 19:00:00', '2023-10-26 21:00:00', 'Requested', 'Note10', NOW())",
        			"INSERT INTO Quotes (userID, initialPrice, startTime, endTime, status, note, createdAt) VALUES (11, 300.50, '2023-10-26 20:00:00', '2023-10-26 22:00:00', 'Rejected', 'Note11', NOW())",
        			"INSERT INTO Quotes (userID, initialPrice, startTime, endTime, status, note, createdAt) VALUES (12, 400.75, '2023-10-26 21:00:00', '2023-10-26 23:00:00', 'Quoted', 'Note12', NOW())",
        			"INSERT INTO Quotes (userID, initialPrice, startTime, endTime, status, note, createdAt) VALUES (13, 225.00, '2023-10-26 22:00:00', '2023-10-27 00:00:00', 'Accepted', 'Note13', NOW())",
        			"INSERT INTO Quotes (userID, initialPrice, startTime, endTime, status, note, createdAt) VALUES (14, 325.50, '2023-10-27 10:00:00', '2023-10-27 12:00:00', 'Requested', 'Note14', NOW())",
        			"INSERT INTO Quotes (userID, initialPrice, startTime, endTime, status, note, createdAt) VALUES (15, 425.75, '2023-10-27 11:00:00', '2023-10-27 13:00:00', 'Rejected', 'Note15', NOW())",
        			"INSERT INTO Quotes (userID, initialPrice, startTime, endTime, status, note, createdAt) VALUES (16, 250.00, '2023-10-27 12:00:00', '2023-10-27 14:00:00', 'Quoted', 'Note16', NOW())",
        			"INSERT INTO Quotes (userID, initialPrice, startTime, endTime, status, note, createdAt) VALUES (17, 350.50, '2023-10-27 13:00:00', '2023-10-27 15:00:00', 'Accepted', 'Note17', NOW())",
        			"INSERT INTO Quotes (userID, initialPrice, startTime, endTime, status, note, createdAt) VALUES (18, 450.75, '2023-10-27 14:00:00', '2023-10-27 16:00:00', 'Requested', 'Note18', NOW())",
        			"INSERT INTO Quotes (userID, initialPrice, startTime, endTime, status, note, createdAt) VALUES (19, 275.00, '2023-10-27 15:00:00', '2023-10-27 17:00:00', 'Rejected', 'Note19', NOW())",
        			"INSERT INTO Quotes (userID, initialPrice, startTime, endTime, status, note, createdAt) VALUES (20, 375.50, '2023-10-27 16:00:00', '2023-10-27 18:00:00', 'Quoted', 'Note20', NOW())"
        		};


         for (String query : quotesQueries) {
             statement.execute(query);
         }
         
         System.out.println("Quotes table is inserted with initial values.");

         String[] treesQueries = {
        		    "INSERT INTO Trees (quoteID, size, height, location, nearHouse, pictureURL1, pictureURL2, pictureURL3, createdAt) VALUES (1, 'Medium', 'Tall', 'Backyard', 'Yes', 'url1', 'url2', 'url3', NOW())",
        		    "INSERT INTO Trees (quoteID, size, height, location, nearHouse, pictureURL1, pictureURL2, pictureURL3, createdAt) VALUES (2, 'Large', 'Short', 'Front Yard', 'No', 'url4', 'url5', 'url6', NOW())",
        		    "INSERT INTO Trees (quoteID, size, height, location, nearHouse, pictureURL1, pictureURL2, pictureURL3, createdAt) VALUES (3, 'Small', 'Tall', 'Garden', 'Yes', 'url7', 'url8', 'url9', NOW())",
        		    "INSERT INTO Trees (quoteID, size, height, location, nearHouse, pictureURL1, pictureURL2, pictureURL3, createdAt) VALUES (4, 'Medium', 'Short', 'Backyard', 'No', 'url10', 'url11', 'url12', NOW())",
        		    "INSERT INTO Trees (quoteID, size, height, location, nearHouse, pictureURL1, pictureURL2, pictureURL3, createdAt) VALUES (5, 'Large', 'Tall', 'Front Yard', 'Yes', 'url13', 'url14', 'url15', NOW())",
        		    "INSERT INTO Trees (quoteID, size, height, location, nearHouse, pictureURL1, pictureURL2, pictureURL3, createdAt) VALUES (6, 'Small', 'Short', 'Garden', 'No', 'url16', 'url17', 'url18', NOW())",
        		    "INSERT INTO Trees (quoteID, size, height, location, nearHouse, pictureURL1, pictureURL2, pictureURL3, createdAt) VALUES (7, 'Medium', 'Tall', 'Backyard', 'Yes', 'url19', 'url20', 'url21', NOW())",
        		    "INSERT INTO Trees (quoteID, size, height, location, nearHouse, pictureURL1, pictureURL2, pictureURL3, createdAt) VALUES (8, 'Large', 'Short', 'Front Yard', 'No', 'url22', 'url23', 'url24', NOW())",
        		    "INSERT INTO Trees (quoteID, size, height, location, nearHouse, pictureURL1, pictureURL2, pictureURL3, createdAt) VALUES (9, 'Small', 'Tall', 'Garden', 'Yes', 'url25', 'url26', 'url27', NOW())",
        		    "INSERT INTO Trees (quoteID, size, height, location, nearHouse, pictureURL1, pictureURL2, pictureURL3, createdAt) VALUES (10, 'Medium', 'Short', 'Backyard', 'No', 'url28', 'url29', 'url30', NOW())",
        		    "INSERT INTO Trees (quoteID, size, height, location, nearHouse, pictureURL1, pictureURL2, pictureURL3, createdAt) VALUES (11, 'Large', 'Tall', 'Front Yard', 'Yes', 'url31', 'url32', 'url33', NOW())",
        		    "INSERT INTO Trees (quoteID, size, height, location, nearHouse, pictureURL1, pictureURL2, pictureURL3, createdAt) VALUES (12, 'Small', 'Short', 'Garden', 'No', 'url34', 'url35', 'url36', NOW())",
        		    "INSERT INTO Trees (quoteID, size, height, location, nearHouse, pictureURL1, pictureURL2, pictureURL3, createdAt) VALUES (13, 'Medium', 'Tall', 'Backyard', 'Yes', 'url37', 'url38', 'url39', NOW())",
        		    "INSERT INTO Trees (quoteID, size, height, location, nearHouse, pictureURL1, pictureURL2, pictureURL3, createdAt) VALUES (14, 'Large', 'Short', 'Front Yard', 'No', 'url40', 'url41', 'url42', NOW())",
        		    "INSERT INTO Trees (quoteID, size, height, location, nearHouse, pictureURL1, pictureURL2, pictureURL3, createdAt) VALUES (15, 'Small', 'Tall', 'Garden', 'Yes', 'url43', 'url44', 'url45', NOW())",
        		    "INSERT INTO Trees (quoteID, size, height, location, nearHouse, pictureURL1, pictureURL2, pictureURL3, createdAt) VALUES (16, 'Medium', 'Short', 'Backyard', 'No', 'url46', 'url47', 'url48', NOW())",
        		    "INSERT INTO Trees (quoteID, size, height, location, nearHouse, pictureURL1, pictureURL2, pictureURL3, createdAt) VALUES (17, 'Large', 'Tall', 'Front Yard', 'Yes', 'url49', 'url50', 'url51', NOW())",
        		    "INSERT INTO Trees (quoteID, size, height, location, nearHouse, pictureURL1, pictureURL2, pictureURL3, createdAt) VALUES (18, 'Small', 'Short', 'Garden', 'No', 'url52', 'url53', 'url54', NOW())",
        		    "INSERT INTO Trees (quoteID, size, height, location, nearHouse, pictureURL1, pictureURL2, pictureURL3, createdAt) VALUES (19, 'Medium', 'Tall', 'Backyard', 'Yes', 'url55', 'url56', 'url57', NOW())",
        		    "INSERT INTO Trees (quoteID, size, height, location, nearHouse, pictureURL1, pictureURL2, pictureURL3, createdAt) VALUES (20, 'Large', 'Short', 'Front Yard', 'No', 'url58', 'url59', 'url60', NOW())"
        		};
         
         
         for (String query : treesQueries) {
             statement.execute(query);
         }
         
         System.out.println("Trees table is inserted with initial values.");
         
         String[] quoteResponsesQueries = {
        		    "INSERT INTO QuoteResponses (quoteID, userID, modifiedPrice, modifiedStartTime, modifiedEndTime, note, createdAt) VALUES (1, 2, 90.00, NOW(), NOW(), 'Response Note1', NOW())",
        		    "INSERT INTO QuoteResponses (quoteID, userID, modifiedPrice, modifiedStartTime, modifiedEndTime, note, createdAt) VALUES (2, 3, 95.00, NOW(), NOW(), 'Response Note2', NOW())",
        		    "INSERT INTO QuoteResponses (quoteID, userID, modifiedPrice, modifiedStartTime, modifiedEndTime, note, createdAt) VALUES (3, 4, 85.00, NOW(), NOW(), 'Response Note3', NOW())",
        		    "INSERT INTO QuoteResponses (quoteID, userID, modifiedPrice, modifiedStartTime, modifiedEndTime, note, createdAt) VALUES (4, 5, 100.00, NOW(), NOW(), 'Response Note4', NOW())",
        		    "INSERT INTO QuoteResponses (quoteID, userID, modifiedPrice, modifiedStartTime, modifiedEndTime, note, createdAt) VALUES (5, 6, 110.00, NOW(), NOW(), 'Response Note5', NOW())",
        		    "INSERT INTO QuoteResponses (quoteID, userID, modifiedPrice, modifiedStartTime, modifiedEndTime, note, createdAt) VALUES (6, 7, 120.00, NOW(), NOW(), 'Response Note6', NOW())",
        		    "INSERT INTO QuoteResponses (quoteID, userID, modifiedPrice, modifiedStartTime, modifiedEndTime, note, createdAt) VALUES (7, 8, 130.00, NOW(), NOW(), 'Response Note7', NOW())",
        		    "INSERT INTO QuoteResponses (quoteID, userID, modifiedPrice, modifiedStartTime, modifiedEndTime, note, createdAt) VALUES (8, 9, 140.00, NOW(), NOW(), 'Response Note8', NOW())",
        		    "INSERT INTO QuoteResponses (quoteID, userID, modifiedPrice, modifiedStartTime, modifiedEndTime, note, createdAt) VALUES (9, 10, 150.00, NOW(), NOW(), 'Response Note9', NOW())",
        		    "INSERT INTO QuoteResponses (quoteID, userID, modifiedPrice, modifiedStartTime, modifiedEndTime, note, createdAt) VALUES (10, 11, 160.00, NOW(), NOW(), 'Response Note10', NOW())",
        		    "INSERT INTO QuoteResponses (quoteID, userID, modifiedPrice, modifiedStartTime, modifiedEndTime, note, createdAt) VALUES (11, 12, 170.00, NOW(), NOW(), 'Response Note11', NOW())",
        		    "INSERT INTO QuoteResponses (quoteID, userID, modifiedPrice, modifiedStartTime, modifiedEndTime, note, createdAt) VALUES (12, 13, 180.00, NOW(), NOW(), 'Response Note12', NOW())",
        		    "INSERT INTO QuoteResponses (quoteID, userID, modifiedPrice, modifiedStartTime, modifiedEndTime, note, createdAt) VALUES (13, 14, 190.00, NOW(), NOW(), 'Response Note13', NOW())",
        		    "INSERT INTO QuoteResponses (quoteID, userID, modifiedPrice, modifiedStartTime, modifiedEndTime, note, createdAt) VALUES (14, 15, 200.00, NOW(), NOW(), 'Response Note14', NOW())",
        		    "INSERT INTO QuoteResponses (quoteID, userID, modifiedPrice, modifiedStartTime, modifiedEndTime, note, createdAt) VALUES (15, 16, 210.00, NOW(), NOW(), 'Response Note15', NOW())",
        		    "INSERT INTO QuoteResponses (quoteID, userID, modifiedPrice, modifiedStartTime, modifiedEndTime, note, createdAt) VALUES (16, 17, 220.00, NOW(), NOW(), 'Response Note16', NOW())",
        		    "INSERT INTO QuoteResponses (quoteID, userID, modifiedPrice, modifiedStartTime, modifiedEndTime, note, createdAt) VALUES (17, 18, 230.00, NOW(), NOW(), 'Response Note17', NOW())",
        		    "INSERT INTO QuoteResponses (quoteID, userID, modifiedPrice, modifiedStartTime, modifiedEndTime, note, createdAt) VALUES (18, 19, 240.00, NOW(), NOW(), 'Response Note18', NOW())",
        		    "INSERT INTO QuoteResponses (quoteID, userID, modifiedPrice, modifiedStartTime, modifiedEndTime, note, createdAt) VALUES (19, 20, 250.00, NOW(), NOW(), 'Response Note19', NOW())",
        		    "INSERT INTO QuoteResponses (quoteID, userID, modifiedPrice, modifiedStartTime, modifiedEndTime, note, createdAt) VALUES (20, 1, 260.00, NOW(), NOW(), 'Response Note20', NOW())"
        		};

         
         for (String query : quoteResponsesQueries) {
             statement.execute(query);
         }
         
         System.out.println("QuoteResponses table is inserted with initial values.");
         
         String[] ordersQueries = {
        		    "INSERT INTO Orders (quoteID, status, createdAt) VALUES (1, 'Pending', NOW())",
        		    "INSERT INTO Orders (quoteID, status, createdAt) VALUES (2, 'Completed', NOW())",
        		    "INSERT INTO Orders (quoteID, status, createdAt) VALUES (3, 'Pending', NOW())",
        		    "INSERT INTO Orders (quoteID, status, createdAt) VALUES (4, 'Completed', NOW())",
        		    "INSERT INTO Orders (quoteID, status, createdAt) VALUES (5, 'Pending', NOW())",
        		    "INSERT INTO Orders (quoteID, status, createdAt) VALUES (6, 'Completed', NOW())",
        		    "INSERT INTO Orders (quoteID, status, createdAt) VALUES (7, 'Pending', NOW())",
        		    "INSERT INTO Orders (quoteID, status, createdAt) VALUES (8, 'Completed', NOW())",
        		    "INSERT INTO Orders (quoteID, status, createdAt) VALUES (9, 'Pending', NOW())",
        		    "INSERT INTO Orders (quoteID, status, createdAt) VALUES (10, 'Completed', NOW())",
        		    "INSERT INTO Orders (quoteID, status, createdAt) VALUES (11, 'Pending', NOW())",
        		    "INSERT INTO Orders (quoteID, status, createdAt) VALUES (12, 'Completed', NOW())",
        		    "INSERT INTO Orders (quoteID, status, createdAt) VALUES (13, 'Pending', NOW())",
        		    "INSERT INTO Orders (quoteID, status, createdAt) VALUES (14, 'Completed', NOW())",
        		    "INSERT INTO Orders (quoteID, status, createdAt) VALUES (15, 'Pending', NOW())",
        		    "INSERT INTO Orders (quoteID, status, createdAt) VALUES (16, 'Completed', NOW())",
        		    "INSERT INTO Orders (quoteID, status, createdAt) VALUES (17, 'Pending', NOW())",
        		    "INSERT INTO Orders (quoteID, status, createdAt) VALUES (18, 'Completed', NOW())",
        		    "INSERT INTO Orders (quoteID, status, createdAt) VALUES (19, 'Pending', NOW())",
        		    "INSERT INTO Orders (quoteID, status, createdAt) VALUES (20, 'Completed', NOW())"
        		};


         
         for (String query : ordersQueries) {
             statement.execute(query);
         }
         
         System.out.println("Orders table is inserted with initial values.");
         
         String[] billsQueries = {
        		    "INSERT INTO Bills (orderID, amount, status, createdAt) VALUES (1, 100.00, 'Pending', NOW())",
        		    "INSERT INTO Bills (orderID, amount, status, createdAt) VALUES (2, 200.00, 'Paid', NOW())",
        		    "INSERT INTO Bills (orderID, amount, status, createdAt) VALUES (3, 150.00, 'Pending', NOW())",
        		    "INSERT INTO Bills (orderID, amount, status, createdAt) VALUES (4, 250.00, 'Paid', NOW())",
        		    "INSERT INTO Bills (orderID, amount, status, createdAt) VALUES (5, 300.00, 'Pending', NOW())",
        		    "INSERT INTO Bills (orderID, amount, status, createdAt) VALUES (6, 350.00, 'Paid', NOW())",
        		    "INSERT INTO Bills (orderID, amount, status, createdAt) VALUES (7, 400.00, 'Pending', NOW())",
        		    "INSERT INTO Bills (orderID, amount, status, createdAt) VALUES (8, 450.00, 'Paid', NOW())",
        		    "INSERT INTO Bills (orderID, amount, status, createdAt) VALUES (9, 500.00, 'Pending', NOW())",
        		    "INSERT INTO Bills (orderID, amount, status, createdAt) VALUES (10, 550.00, 'Paid', NOW())",
        		    "INSERT INTO Bills (orderID, amount, status, createdAt) VALUES (11, 600.00, 'Pending', NOW())",
        		    "INSERT INTO Bills (orderID, amount, status, createdAt) VALUES (12, 650.00, 'Paid', NOW())",
        		    "INSERT INTO Bills (orderID, amount, status, createdAt) VALUES (13, 700.00, 'Pending', NOW())",
        		    "INSERT INTO Bills (orderID, amount, status, createdAt) VALUES (14, 750.00, 'Paid', NOW())",
        		    "INSERT INTO Bills (orderID, amount, status, createdAt) VALUES (15, 800.00, 'Pending', NOW())",
        		    "INSERT INTO Bills (orderID, amount, status, createdAt) VALUES (16, 850.00, 'Paid', NOW())",
        		    "INSERT INTO Bills (orderID, amount, status, createdAt) VALUES (17, 900.00, 'Pending', NOW())",
        		    "INSERT INTO Bills (orderID, amount, status, createdAt) VALUES (18, 950.00, 'Paid', NOW())",
        		    "INSERT INTO Bills (orderID, amount, status, createdAt) VALUES (19, 1000.00, 'Pending', NOW())",
        		    "INSERT INTO Bills (orderID, amount, status, createdAt) VALUES (20, 1050.00, 'Paid', NOW())"
        		};

         
         for (String query : billsQueries) {
             statement.execute(query);
         }
         
         System.out.println("Bills table is inserted with initial values.");

         String[] billResponsesQueries = {
        		    "INSERT INTO BillResponses (billID, userID, note, newAmount, createdAt) VALUES (1, 1, 'Bill Response Note1', 90.00, NOW())",
        		    "INSERT INTO BillResponses (billID, userID, note, newAmount, createdAt) VALUES (2, 2, 'Bill Response Note2', 190.00, NOW())",
        		    "INSERT INTO BillResponses (billID, userID, note, newAmount, createdAt) VALUES (3, 3, 'Bill Response Note3', 140.00, NOW())",
        		    "INSERT INTO BillResponses (billID, userID, note, newAmount, createdAt) VALUES (4, 4, 'Bill Response Note4', 240.00, NOW())",
        		    "INSERT INTO BillResponses (billID, userID, note, newAmount, createdAt) VALUES (5, 5, 'Bill Response Note5', 290.00, NOW())",
        		    "INSERT INTO BillResponses (billID, userID, note, newAmount, createdAt) VALUES (6, 6, 'Bill Response Note6', 340.00, NOW())",
        		    "INSERT INTO BillResponses (billID, userID, note, newAmount, createdAt) VALUES (7, 7, 'Bill Response Note7', 390.00, NOW())",
        		    "INSERT INTO BillResponses (billID, userID, note, newAmount, createdAt) VALUES (8, 8, 'Bill Response Note8', 440.00, NOW())",
        		    "INSERT INTO BillResponses (billID, userID, note, newAmount, createdAt) VALUES (9, 9, 'Bill Response Note9', 490.00, NOW())",
        		    "INSERT INTO BillResponses (billID, userID, note, newAmount, createdAt) VALUES (10, 10, 'Bill Response Note10', 540.00, NOW())",
        		    "INSERT INTO BillResponses (billID, userID, note, newAmount, createdAt) VALUES (11, 11, 'Bill Response Note11', 590.00, NOW())",
        		    "INSERT INTO BillResponses (billID, userID, note, newAmount, createdAt) VALUES (12, 12, 'Bill Response Note12', 640.00, NOW())",
        		    "INSERT INTO BillResponses (billID, userID, note, newAmount, createdAt) VALUES (13, 13, 'Bill Response Note13', 690.00, NOW())",
        		    "INSERT INTO BillResponses (billID, userID, note, newAmount, createdAt) VALUES (14, 14, 'Bill Response Note14', 740.00, NOW())",
        		    "INSERT INTO BillResponses (billID, userID, note, newAmount, createdAt) VALUES (15, 15, 'Bill Response Note15', 790.00, NOW())",
        		    "INSERT INTO BillResponses (billID, userID, note, newAmount, createdAt) VALUES (16, 16, 'Bill Response Note16', 840.00, NOW())",
        		    "INSERT INTO BillResponses (billID, userID, note, newAmount, createdAt) VALUES (17, 17, 'Bill Response Note17', 890.00, NOW())",
        		    "INSERT INTO BillResponses (billID, userID, note, newAmount, createdAt) VALUES (18, 18, 'Bill Response Note18', 940.00, NOW())",
        		    "INSERT INTO BillResponses (billID, userID, note, newAmount, createdAt) VALUES (19, 19, 'Bill Response Note19', 990.00, NOW())",
        		    "INSERT INTO BillResponses (billID, userID, note, newAmount, createdAt) VALUES (20, 20, 'Bill Response Note20', 1040.00, NOW())"
        		};

         
         for (String query : billResponsesQueries) {
             statement.execute(query);
         }
        
         System.out.println("BillResponses table is inserted with initial values."); 
         
        disconnect();
    }
}
