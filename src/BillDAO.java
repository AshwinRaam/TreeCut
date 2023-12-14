import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {
    private static final long serialVersionUID = 1L;
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public BillDAO() { }

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

    public Bill getBill(int billID) throws SQLException {
        String sql = "SELECT * FROM bills WHERE billID = ?;";

        connect_func();
        preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setInt(1, billID);
        resultSet = preparedStatement.executeQuery();

        Bill bill = new Bill();
        if(resultSet.next()){
            bill = createBill(resultSet);
        }

        resultSet.close();
        preparedStatement.close();
        connect.close();

        return bill;
    }

    public List<Bill> getBills() throws SQLException {
        String sql = "SELECT * FROM bills;";

        connect_func();
        statement = connect.createStatement();
        resultSet = statement.executeQuery(sql);

        List<Bill> bills = new ArrayList<>();
        while (resultSet.next()){
            Bill bill = createBill(resultSet);
            bills.add(bill);
        }

        resultSet.close();
        connect.close();

        return bills;
    }

    /**
     *
     * @param userID The userID of the user you'd like to find the bills for.
     * @return A list of all bills for a specified user.
     * @throws SQLException
     */
    public List<Bill> getBills(int userID) throws SQLException {
        String sql = """
                SELECT b.*
                FROM bills b
                         JOIN (SELECT orderID
                               FROM orders o
                                        JOIN (SELECT quoteID
                                              FROM quotes
                                              WHERE clientID = ?) q ON o.quoteID = q.quoteID) o ON b.orderID = o.orderID;""";
        connect_func();
        preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setInt(1, userID);
        resultSet = preparedStatement.executeQuery();

        List<Bill> bills = new ArrayList<>();
        while (resultSet.next()){
            Bill bill = createBill(resultSet);
            bills.add(bill);
        }

        resultSet.close();
        preparedStatement.close();
        connect.close();

        return bills;
    }

    public void insert(Bill bill) throws SQLException {
        String sql = "INSERT INTO bills (orderID, amount, status, createdAt) VALUES (?, ?, 'Pending', NOW());";

        connect_func();
        preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setInt(1, bill.getOrderID());
        preparedStatement.setDouble(2, bill.getAmount());
        int result = preparedStatement.executeUpdate();

        if (result < 1) {
            System.out.println("Error in inserting bill");
        }

        preparedStatement.close();
        connect.close();
    }

    public void setBillPaid(int billID) throws SQLException {
        String sql = "UPDATE bills SET status = 'Paid' WHERE billID = ?;";

        connect_func();
        preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setInt(1, billID);
        preparedStatement.executeUpdate();

        preparedStatement.close();
        connect.close();
    }

    public void setBillPending(int billID) throws SQLException {
        String sql = "UPDATE bills SET status = 'Pending' WHERE billID = ?;";

        connect_func();
        preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setInt(1, billID);
        preparedStatement.executeUpdate();

        preparedStatement.close();
        connect.close();
    }

    public void setBillPending(int billID, double newAmount) throws SQLException {
        String sql = "UPDATE bills SET status = 'Pending', amount = ? WHERE billID = ?;";

        connect_func();
        preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setInt(1, billID);
        preparedStatement.setDouble(2, newAmount);
        preparedStatement.executeUpdate();

        preparedStatement.close();
        connect.close();
    }

    /**
     * For use when there is no new amount set.
     * @param billID
     * @throws SQLException
     */
    public void setBillDisputed(int billID) throws SQLException {
        String sql = "UPDATE bills SET status = 'Disputed' WHERE billID = ?;";

        connect_func();
        preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setInt(1, billID);
        preparedStatement.executeUpdate();

        preparedStatement.close();
        connect.close();
    }

    /**
     * Get all pending or disputed bills older than one week.
     * @return A list of all unpaid bills.
     * @throws SQLException
     */
    public List<Bill> getUnpaidBills() throws SQLException {
        String sql = "SELECT * FROM bills b WHERE status != 'Paid' AND createdAt < NOW() - INTERVAL 1 WEEK;";
        connect_func();
        statement = connect.createStatement();
        resultSet = statement.executeQuery(sql);

        List<Bill> bills = new ArrayList<>();
        while(resultSet.next()){
            Bill bill = createBill(resultSet);
            bills.add(bill);
        }
        resultSet.close();
        connect.close();
        return bills;
    }

    /**
     * Get the total amount due per user account.
     * @param userID
     * @return
     * @throws SQLException
     */
    public double getTotalAmountDue(int userID) throws SQLException {
        String sql = """
                SELECT SUM(b.amount) as amountDue FROM bills b
                JOIN (
                    SELECT orderID FROM orders o
                                   JOIN (
                                       SELECT quoteID FROM quotes WHERE clientID = ?
                    ) q ON o.quoteID = q.quoteID
                ) o ON b.orderID = o.orderID
                WHERE b.status = 'Pending';""";
        connect_func();
        preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setInt(1, userID);
        resultSet = preparedStatement.executeQuery();

        double amountDue = 0;
        if (resultSet.next())
            amountDue =  resultSet.getDouble("amountDue");

        resultSet.close();
        preparedStatement.close();
        connect.close();

        return amountDue;
    }

    public double getTotalAmountPaid(int userID) throws SQLException {
        String sql = """
                SELECT SUM(b.amount) as amountPaid FROM bills b
                JOIN (
                    SELECT orderID FROM orders o
                                   JOIN (
                                       SELECT quoteID FROM quotes WHERE clientID = ?
                    ) q on o.quoteID = q.quoteID
                ) o on b.orderID = o.orderID
                WHERE b.status = 'Paid';""";
        connect_func();
        preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setInt(1, userID);
        resultSet = preparedStatement.executeQuery();

        double amountPaid = 0;
        if (resultSet.next())
            amountPaid = resultSet.getDouble("amountPaid");

        resultSet.close();
        preparedStatement.close();
        connect.close();

        return amountPaid;
    }

    /**
     * Too lazy to keep copying and pasting this code.
     * @param resultSet
     * @return A new bill object from a "SELECT * FROM bills (...)" query.
     * @throws SQLException
     */
    private static Bill createBill(ResultSet resultSet) throws SQLException {
        int billID = resultSet.getInt("billID");
        int orderID = resultSet.getInt("orderID");
        String status = resultSet.getString("status");
        double amount = resultSet.getDouble("Amount");
        Timestamp createdAt = resultSet.getTimestamp("createdAt");
        return new Bill(billID, orderID, amount, status, createdAt);
    }
}
