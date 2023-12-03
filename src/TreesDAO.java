import java.sql.*;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;


import java.util.ArrayList;
import java.util.List;

@WebServlet("/treesDAO")
public class TreesDAO {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public TreesDAO() { }

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

    /**
     * Because I'm too lazy to keep copying and pasting code, send the result set here.
     * @param resultSet
     * @return A new tree object.
     * @throws SQLException
     */
    private Tree createTree(ResultSet resultSet) throws SQLException {
        int treeID = resultSet.getInt("treeID");
        int quoteID = resultSet.getInt("quoteID");
        String size = resultSet.getString("size");
        String height = resultSet.getString("height");
        String location = resultSet.getString("location");
        String nearHouse = resultSet.getString("nearHouse");
        String pictureURL1 = resultSet.getString("pictureURL1");
        String pictureURL2 = resultSet.getString("pictureURL2");
        String pictureURL3 = resultSet.getString("pictureURL3");
        Timestamp createdAt = resultSet.getTimestamp("createdAt");
        Timestamp updatedAt = resultSet.getTimestamp("updatedAt");

        return new Tree(
                treeID,
                quoteID,
                size,
                height,
                location,
                nearHouse,
                pictureURL1,
                pictureURL2,
                pictureURL3,
                createdAt,
                updatedAt
        );
    }

    public Tree getTree(int treeID) throws SQLException {
        String sql = "SELECT * FROM trees WHERE treeID = ?";

        connect_func();
        preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setInt(1, treeID);
        resultSet = preparedStatement.executeQuery();

        Tree tree = new Tree();
        while(resultSet.next()) {
            tree = createTree(resultSet);
        }

        return tree;
    }

    public List<Tree> getTrees(int quoteID) throws SQLException {
        String sql = "SELECT * FROM trees WHERE quoteID = ?";

        connect_func();
        preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setInt(1, quoteID);
        resultSet = preparedStatement.executeQuery();

        List<Tree> trees = new ArrayList<Tree>();
        while(resultSet.next())
        {
            trees.add(createTree(resultSet));
        }
        return trees;
    }

    public void insertTree(Tree tree) throws SQLException {
        String sql = "INSERT INTO trees (quoteID, size, height, location, " +
                "nearhouse, pictureURL1, pictureURL2, pictureURL3, createdAt) " +
                "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, NOW())";

        connect_func();
        preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setInt(1, tree.getQuoteID());
        preparedStatement.setString(2, tree.getSize());
        preparedStatement.setString(3, tree.getHeight());
        preparedStatement.setString(4, tree.getLocation());
        preparedStatement.setString(5, tree.getNearHouse());
        preparedStatement.setString(6, tree.getPictureURL1());
        preparedStatement.setString(7, tree.getPictureURL2());
        preparedStatement.setString(8, tree.getPictureURL3());
        preparedStatement.execute();
    }

    public void insertTrees(List<Tree> trees) throws SQLException {
        for(Tree tree : trees) {
            insertTree(tree);
        }
    }

    public List<List<String>> getTreeImages(int quoteID) throws SQLException {
        List<List<String>> treeImageUrls = new ArrayList<>();
        String sql = "SELECT pictureURL1, pictureURL2, pictureURL3 FROM trees WHERE quoteID = ?";

        connect_func();
        preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setInt(1, quoteID);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            String url1 = resultSet.getString(1);
            String url2 = resultSet.getString(2);
            String url3 = resultSet.getString(3);
            List<String> urls = new ArrayList<String>();
            urls.add(url1);
            urls.add(url2);
            urls.add(url3);

            treeImageUrls.add(urls);
        }

        return treeImageUrls;
    }

    /**
     * Get a list of all trees cut for a specific user.
     * @param userID
     * @return A list of all trees that have been cut for specified user.
     * @throws SQLException
     */
    public List<Tree> getAllTreesCut(int userID) throws SQLException {
        String sql = """
                SELECT * FROM trees t WHERE t.quoteID IN (
                    SELECT q.quoteID FROM quotes q
                        JOIN (
                        SELECT quoteID FROM orders WHERE status = 'Completed'
                        ) o ON q.quoteID = o.quoteID
                    WHERE q.clientID = ?
                    )""";

        connect_func();
        preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setInt(1, userID);
        resultSet = preparedStatement.executeQuery();

        List<Tree> trees = new ArrayList<>();
        while(resultSet.next()) {
            Tree tree = createTree(resultSet);
            trees.add(tree);
        }

        return trees;
    }
}
