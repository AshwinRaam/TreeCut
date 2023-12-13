import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private static final long serialVersionUID = 1L;
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public OrderDAO() { }

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

    public Order getOrder(int orderID) throws SQLException {
        String sql = "SELECT * FROM orders WHERE orderID = ?;";

        connect_func();
        preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setInt(1, orderID);
        resultSet = preparedStatement.executeQuery();

        Order order = new Order();
        if(resultSet.next()){
            order = createOrder(resultSet);
        }

        resultSet.close();
        preparedStatement.close();
        connect.close();

        return order;
    }

    /**
     *
     * @return All orders in the orders table.
     */
    public List<Order> getOrders() throws SQLException {
        String sql = "SELECT * FROM orders;";
        connect_func();
        statement = connect.createStatement();
        resultSet = statement.executeQuery(sql);

        List<Order> orders = new ArrayList<>();
        while (resultSet.next()) {
            Order order = createOrder(resultSet);
            orders.add(order);
        }

        resultSet.close();
        connect.close();

        return orders;
    }

    /**
     *
     * @param userID The userID of the user you'd like to find order for.
     * @return All orders pertaining to a specific user.
     */
    public List<Order> getOrders(int userID) throws SQLException {
        String sql = """
                SELECT *
                FROM orders o
                WHERE quoteID = (
                    SELECT quoteID
                    FROM quotes
                    WHERE quoteID = o.quoteID AND clientID = ?
                );""";
        connect_func();
        preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setInt(1, userID);
        resultSet = preparedStatement.executeQuery();

        List<Order> orders = new ArrayList<>();
        while(resultSet.next()){
            Order order = createOrder(resultSet);
            orders.add(order);
        }

        resultSet.close();
        preparedStatement.close();
        connect.close();

        return orders;
    }

    public void insert(Order order) throws SQLException {
        String sql = "INSERT INTO orders (quoteID, status, createdAt) VALUES (?, 'Pending', NOW());";

        connect_func();
        preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setInt(1, order.getQuoteID());
        int result = preparedStatement.executeUpdate();

        if (result < 1) {
            System.out.println("Error in inserting order");
        }

        preparedStatement.close();
        connect.close();
    }

    public void complete(int orderID) throws SQLException {
        String sql = "UPDATE orders SET status = 'Completed', workDate = NOW() WHERE orderID = ?;";

        connect_func();
        preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setInt(1, orderID);
        preparedStatement.executeUpdate(); //not checking update because it doesn't matter all that much
    }

    /**
     * I'm too lazy to keep copying and pasting code.
     * @param resultSet ResultSet from a "SELECT * FROM orders (...)" query.
     * @return A new Order object with the return of a resultSet.
     * @throws SQLException
     */
    private Order createOrder(ResultSet resultSet) throws SQLException {
        Order order = new Order(); //could use one of the constructors, but there's an unused
        order.setOrderID(resultSet.getInt("orderID"));
        order.setQuoteID(resultSet.getInt("quoteID"));
        order.setStatus(resultSet.getString("status"));
        order.setWorkDate(resultSet.getTimestamp("workDate"));
        order.setCreatedAt(resultSet.getTimestamp("createdAt"));
        return order;
    }
}
