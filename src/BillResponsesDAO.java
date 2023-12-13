import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillResponsesDAO {
    private static final long serialVersionUID = 1L;
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public BillResponsesDAO() { }

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

    public List<BillResponse> getResponses(int billID) throws SQLException {
        String sql = "SELECT * FROM billresponses WHERE billID = ?";

        connect_func();
        preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setInt(1, billID);
        resultSet = preparedStatement.executeQuery();

        List<BillResponse> responses = new ArrayList<>();
        UserDAO UserDAO = new UserDAO();
        while (resultSet.next()) {
            int responseID = resultSet.getInt("responseID");
            int userID = resultSet.getInt("userID");
            String note = resultSet.getString("note");
            double newPrice = resultSet.getDouble("newAmount");
            Timestamp createdAt = resultSet.getTimestamp("createdAt");

            User user = UserDAO.getUser(userID);
            String fullName = user.getFirstName() + " " + user.getLastName();

            BillResponse br = new BillResponse(responseID, billID, userID, fullName, note, newPrice, createdAt);
            /* yes, this could be smaller, and more efficient, but getUser(userID) seems more versatile */
            responses.add(br);
        }

        resultSet.close();
        preparedStatement.close();
        connect.close();

        return responses;
    }

    public void postResponse(BillResponse response) throws SQLException {
        String sql = "INSERT INTO billresponses (billID, userID, note, newAmount, createdAt) VALUES (?, ?, ?, ?, NOW())";

        connect_func();
        preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setInt(1, response.getBillID());
        preparedStatement.setInt(2, response.getUserID());
        preparedStatement.setString(3, response.getNote());
        if (response.getNewAmount() > 0)
            preparedStatement.setDouble(4, response.getNewAmount());
        else
            preparedStatement.setNull(4, Types.DOUBLE);
        int result = preparedStatement.executeUpdate();

        if (result < 1) {
            System.out.println("Error in inserting bill response");
            return;
        }

        System.out.println("Posting finished");
    }
}
