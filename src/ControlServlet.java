import java.io.*;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@MultipartConfig
public class ControlServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO UserDAO = new UserDAO();
    private QuoteDAO QuoteDAO;
    private TreesDAO TreesDAO;
    private QuoteResponsesDAO QuoteResponsesDAO;
    private OrderDAO OrderDAO;
    private BillDAO BillDAO;
    private BillResponsesDAO BillResponsesDAO;
    private String currentUser;

    public ControlServlet() {

    }

    public void init() {
        UserDAO = new UserDAO();
        QuoteDAO = new QuoteDAO();
        TreesDAO = new TreesDAO();
        QuoteResponsesDAO = new QuoteResponsesDAO();
        OrderDAO = new OrderDAO();
        BillDAO = new BillDAO();
        BillResponsesDAO = new BillResponsesDAO();
        currentUser = "";
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        if (action.contains("images")) //handle image serving
            action = "/images";
        System.out.println(action);
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("username") != null) {
            try {
                switch (action) {
                    case "/login":
                        login(request, response);
                        break;
                    case "/register":
                        register(request, response);
                        break;
                    case "/initialize":
                        UserDAO.init();
                        System.out.println("Database successfully initialized!");
                        request.getRequestDispatcher("RootViews/Reset.jsp").forward(request, response);
                        break;
                    case "/root":
                        rootPage(request, response);
                        break;
                    case "/quotes":
                        listQuotes(request, response, session);
                        break;
                    case "/orders":
                        listOrders(request, response, session);
                        break;
                    case "/bills":
                        listBills(request, response, session);
                        break;
                    case "/logout":
                        logout(request, response);
                        break;
                    case "/new-quote":
                        createQuote(request, response);
                        break;
                    case "/submit-quote":
                        submitQuote(request, response, session);
                        break;
                    case "/showresponses":
                        listResponses(request, response);
                        break;
                    case "/view-tree":
                        viewTree(request, response);
                        break;
                    case "/createquoteresponse":
                        createQuoteResponse(request, response, session);
                        break;
                    case "/quoterespond":
                        System.out.println("Responding to quote...");
                        sendResponseToQuote(request, response);
                        break;
                    case "/accept-quote":
                        acceptQuote(request, response, session);
                        break;
                    case "/reject-quote":
                        rejectQuote(request, response, session);
                        break;
                    case "/images":
                        System.out.println("Serving image...");
                        serveImage(request, response);
                        break;
                    case "/complete-order":
                        completeOrder(request, response, session);
                        break;
                    case "/showbillresponses":
                        listBillResponses(request, response, session);
                        break;
                    case "/reset":
                        request.setAttribute("listUser", UserDAO.listAllUsers());
                        request.getRequestDispatcher("RootViews/Reset.jsp").forward(request, response);
                        break;
                    case "/createbillresponse":
                        createBillResponse(request, response, session);
                        break;
                    case "billrespond":
                        sendResponseToBill(request, response, session);
                        break;
                    case "/pay-bill":
                        payBill(request, response, session);
                        break;
                    case "/big-clients":
                        bigClients(request, response);
                        break;
                    case "/easy-clients":
                        easyClients(request, response);
                        break;
                    case "/prospective-clients":
                        prospectiveClients(request, response);
                        break;
                    case "/bad-clients":
                        badClients(request, response);
                        break;
                    case "/good-clients":
                        goodClients(request, response);
                        break;
                    case "/one-tree-quotes":
                        oneTreeQuotes(request, response);
                        break;
                    case "/highest-tree":
                        highestTree(request, response);
                        break;
                    case "/overdue-bills":
                        overdueBills(request, response);
                        break;

                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            try {
                if (action.equals("/login")) {
                    login(request, response);
                }
                RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
                dispatcher.forward(request, response);

            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    private void listQuotes(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws SQLException, ServletException, IOException {
        System.out.println("Sending to quotes page.");
        String username = (String) session.getAttribute("username");
        List<Quote> listQuote;
        User user = UserDAO.getUser(username);
        if (user.isClient())
            listQuote = QuoteDAO.listQuotesByUsername(username);
        else
            listQuote = QuoteDAO.listQuotesByContractor(user.userID);
        Collections.reverse(listQuote);
        request.setAttribute("listQuotes", listQuote);
        request.getRequestDispatcher("QuoteList.jsp").forward(request, response);
    }

    private void listOrders(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws SQLException, ServletException, IOException {
        System.out.println("Sending to orders page");
        String username = (String) session.getAttribute("username");
        User user = UserDAO.getUser(username);
        List<Order> listOrder;
        if (user.isClient())
            listOrder = OrderDAO.getOrders(user.userID);
        else
            listOrder = OrderDAO.getOrders();
        Collections.reverse(listOrder);
        request.setAttribute("listOrders", listOrder);
        request.setAttribute("user", user);
        request.getRequestDispatcher("OrderList.jsp").forward(request, response);
    }

    private void listBills(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws SQLException, ServletException, IOException {
        System.out.println("Sending to orders page");
        String username = (String) session.getAttribute("username");
        User user = UserDAO.getUser(username);
        List<Bill> listBill;
        if (user.isClient())
            listBill = BillDAO.getBills(user.userID);
        else
            listBill = BillDAO.getBills();
        Collections.reverse(listBill);
        request.setAttribute("listBills", listBill);
        request.setAttribute("user", user);
        request.getRequestDispatcher("BillList.jsp").forward(request, response);
    }

    private void rootPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        System.out.println("Root Dashboard");
        request.getRequestDispatcher("RootViews/RootDashboard.jsp").forward(request, response);
    }

    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("username") != null) {
            if(session.getAttribute("role").equals("Admin")){
                rootPage(request, response);
            }
            else if(session.getAttribute("role").equals("Contractor")) {
                request.getRequestDispatcher("ContractorDashboard.jsp").forward(request, response);
            }
            else if(session.getAttribute("role").equals("Client")) {
                request.getRequestDispatcher("ClientDashboard.jsp").forward(request, response);
            }
        } else {
            session = request.getSession(true);

            if ("root".equals(username) && "pass1234".equals(password)) {
                System.out.println("Login Successful! Redirecting to root");
                session.setAttribute("username", username);
                session.setAttribute("role", "Admin");
                rootPage(request, response);
            } else if (UserDAO.isValid(username, password)) {
                System.out.println("Login Successful! Redirecting");
                session.setAttribute("username", username);
                session.setAttribute("userID", UserDAO.getUserID(username));
                if (UserDAO.isClient(username)) {
                    session.setAttribute("role", "Client");
                    request.getRequestDispatcher("ClientDashboard.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("ContractorDashboard.jsp").forward(request, response);
                    session.setAttribute("role", "Contractor");
                }
            } else {
                request.setAttribute("loginFailedStr", "Login Failed: Please check your credentials.");
                session = null;
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return; //doesn't allow for the change in currentUser below
            }
        }

    }

    private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String retypePassword = request.getParameter("retypePassword");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String address = request.getParameter("address");
        String phoneNumber = request.getParameter("phoneNumber");
        String creditCardInfo = request.getParameter("creditCardInfo");
        String role = request.getParameter("role");

        if (!password.equals(retypePassword)) {
            System.out.println("Password and Retype Password do not match");
            request.setAttribute("errorOne", "Registration failed: Password and Retype Password do not match.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        if (UserDAO.checkEmail(email)) {
            System.out.println("Email taken, please enter a new email");
            request.setAttribute("errorTwo", "Registration failed: Email taken, please enter a new email.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        if (UserDAO.checkUsername(username)) {
            System.out.println("Username taken, please enter a new username");
            request.setAttribute("errorTwo", "Registration failed: Username taken, please enter a new username.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }


        System.out.println("Registration Successful! Added to database");
        User user = new User(username, password, role, firstName, lastName, address, phoneNumber, email, creditCardInfo,
                                role.equals("Client"));
        UserDAO.insert(user);
        String message = "Registration successful! Please login.";
        request.setAttribute("loginFailedStr", message);
        request.getRequestDispatcher("login.jsp").forward(request, response);

    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }
        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
            dispatcher.forward(request, response);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void createQuote(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("quoteRequest.jsp");
        dispatcher.forward(request, response);
    }

    private String getStringFromPart(Part part) throws IOException {
        InputStream partStream = part.getInputStream();
        BufferedReader partReader = new BufferedReader(new InputStreamReader(partStream));
        String stringPart = partReader.readLine();
        partReader.close();
        partStream.close();
        return stringPart;
    }

    private void submitQuote(HttpServletRequest request, HttpServletResponse response,  HttpSession session)
            throws SQLException, ServletException, IOException {
        String username = (String) session.getAttribute("username");
        User user = UserDAO.getUser(username);

        Part startTimePart = request.getPart("startTime");
        Part endTimePart = request.getPart("endTime");
        Part notePart = request.getPart("note");
        System.out.printf("Parts not null: %b, %b, %b\r\n", startTimePart != null, endTimePart != null, notePart != null);

        String sTimeStart = getStringFromPart(startTimePart);
        String sTimeEnd = getStringFromPart(endTimePart);
        System.out.println(sTimeStart + " " + sTimeEnd);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime lStartTime = LocalDateTime.parse(sTimeStart, formatter);
        LocalDateTime lEndTime = LocalDateTime.parse(sTimeEnd, formatter);
        java.sql.Timestamp startTime = Timestamp.valueOf(lStartTime);
        java.sql.Timestamp endTime = Timestamp.valueOf(lEndTime);
        String note = getStringFromPart(notePart);
        System.out.println(notePart);

        System.out.println("aaa");
        Quote quote = new Quote();
        quote.setClientID(user.userID);
        quote.setNote(note);
        quote.setStartTime(startTime);
        quote.setEndTime(endTime);

        System.out.println("aaa");

        int quoteID = QuoteDAO.insertQuote(quote);
        if (quoteID < 0){
            //handle error or smth
            return;
        }

        int treeNum = 1;
        while (true)
        {
            System.out.printf("Uploading tree%d ... \r\n", treeNum);
            if (treeNum > 1000) {
                System.out.println("Dawg you got too many trees.");
                break; //way too many trees.
            }

            Part pSize = request.getPart("size_tree" + treeNum);
            Part pHeight = request.getPart("height_tree" + treeNum);
            Part pNearHouse = request.getPart("near_house" + treeNum);
            Part pLocation = request.getPart("location" + treeNum);
            Part treePic1Part = request.getPart("tree" + treeNum + "pic1");
            Part treePic2Part = request.getPart("tree" + treeNum + "pic2");
            Part treePic3Part = request.getPart("tree" + treeNum + "pic3");
            if(pSize == null)
                break;

            String size = getStringFromPart(pSize);
            int height = Integer.parseInt(getStringFromPart(pHeight));
            String nearHouse = getStringFromPart(pNearHouse);
            String location = getStringFromPart(pLocation);
            String treePicURL1 = saveUploadedImage(treePic1Part, quoteID);
            String treePicURL2 = saveUploadedImage(treePic2Part, quoteID);
            String treePicURL3 = saveUploadedImage(treePic3Part, quoteID);

            Tree tree = new Tree();
            tree.setQuoteID(quoteID);
            tree.setSize(size);
            tree.setHeight(height);
            tree.setNearHouse(nearHouse);
            tree.setLocation(location);
            tree.setPictureURL1(treePicURL1);
            tree.setPictureURL2(treePicURL2);
            tree.setPictureURL3(treePicURL3);

            TreesDAO.insertTree(tree);

            treeNum++;
            System.out.println("Done.");
        }/**/

        response.sendRedirect("quotes");
    }

    private void listResponses(HttpServletRequest request, HttpServletResponse response) throws SQLException,
            ServletException, IOException {
        Quote quote;
        if (request.getParameter("billID") != null)
        {
            int billID = Integer.parseInt(request.getParameter("billID"));
            quote = QuoteDAO.getQuoteByBill(billID);
        }
        else {
            int quoteID = Integer.parseInt(request.getParameter("quoteID"));
            quote = QuoteDAO.getQuote(quoteID);
        }
        List<Tree> trees = TreesDAO.getTrees(quote.getQuoteID());
        List<QuoteResponse> responses = QuoteResponsesDAO.GetResponses(quote.getQuoteID());
        Collections.reverse(responses);
        System.out.println(quote.getQuoteID());
        System.out.println(trees.size());

        request.setAttribute("quoteID", quote.getQuoteID());
        request.setAttribute("quote", quote);
        request.setAttribute("trees", trees);
        request.setAttribute("listResponses", responses);
        request.setAttribute("isClient", UserDAO.isClient((String) request.getSession(false).getAttribute("username")));
        RequestDispatcher dispatcher = request.getRequestDispatcher("QuoteResponsesList.jsp");
        dispatcher.forward(request, response);
    }

    private void viewTree(HttpServletRequest request, HttpServletResponse response) throws SQLException,
            ServletException, IOException {
        int treeID = Integer.parseInt(request.getParameter("treeID"));
        Tree tree = TreesDAO.getTree(treeID);
        request.setAttribute("tree", tree);
        RequestDispatcher dispatcher = request.getRequestDispatcher("treeView.jsp");
        dispatcher.forward(request, response);
    }

    private void createQuoteResponse(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws SQLException, ServletException, IOException {
        String username = (String) session.getAttribute("username");
        User user = UserDAO.getUser(username);
        int quoteID = Integer.parseInt(request.getParameter("quoteID"));
        Quote quote = QuoteDAO.getQuote(quoteID);

        request.setAttribute("user", user);
        request.setAttribute("quote", quote);
        RequestDispatcher dispatcher = request.getRequestDispatcher("QuoteResponse.jsp");
        dispatcher.forward(request, response);
    }

    private void sendResponseToQuote(HttpServletRequest request, HttpServletResponse response) throws SQLException,
            IOException, ServletException {
        String username = request.getParameter("username");
        User user = UserDAO.getUser(username);
        int quoteID = Integer.parseInt(request.getParameter("quoteID"));
        Quote quote = QuoteDAO.getQuote(quoteID);

        //check to see if user is allowed to post
        if (user.isClient()) {
            if (quote.getClientID() != user.getUserID()) {
                System.out.printf("Error: User is not allowed to post on this. ID %d != %d%n",
                        quote.getClientID(), user.getUserID());
                System.out.println("Sending createquoteresponse.");
                request.setAttribute("errorOne", "Error: You are not allowed to post on this quote.");
                request.getRequestDispatcher("createquoteresponse").forward(request, response);
                return;
            }
            QuoteDAO.clientResponsed(quoteID);
        }
        else
        {
            QuoteDAO.contractorResponsed(quoteID);
            QuoteDAO.assignContractor(quoteID, user.userID);
        }

        //--start conversion--
        System.out.println("--Setting up converted parameters--");
        String sModPrice = request.getParameter("modifiedPrice");
        String sModStartTime = request.getParameter("modifiedStartTime");
        String sModEndTime = request.getParameter("modifiedEndTime");

        LocalDateTime modifiedStartTime = null, modifiedEndTime = null;
        double modifiedPrice = -1;

        if (!sModPrice.isEmpty()) {
            modifiedPrice = Double.parseDouble(sModPrice);
            QuoteDAO.updateCurrPrice(quoteID, modifiedPrice);
            if (!user.isClient() && quote.getInitialPrice() == 0) {
                QuoteDAO.setInitialPrice(quoteID, modifiedPrice);
            }
        }
        if (!sModStartTime.isEmpty()) {
            modifiedStartTime = LocalDateTime.parse(
                    request.getParameter("modifiedStartTime"), DateTimeFormatter.ISO_DATE_TIME);
            QuoteDAO.updateStartTime(quoteID, Timestamp.valueOf(modifiedStartTime));
        }
        if (!sModEndTime.isEmpty()) {
            modifiedEndTime = LocalDateTime.parse(
                    request.getParameter("modifiedEndTime"), DateTimeFormatter.ISO_DATE_TIME);
            QuoteDAO.updateStartTime(quoteID, Timestamp.valueOf(modifiedEndTime));
        }

        System.out.println("--finish setting up converted parameters--");
        //--end conversion--

        String note = request.getParameter("note");

        QuoteResponse qResponse = new QuoteResponse();
        qResponse.setQuoteID(quoteID);
        qResponse.setUserID(user.userID);
        qResponse.setModifiedPrice(modifiedPrice);
        qResponse.setModifiedStartTime(modifiedStartTime);
        qResponse.setModifiedEndTime(modifiedEndTime);
        qResponse.setNote(note);

        QuoteResponsesDAO.PostResponse(qResponse);
        request.setAttribute("quoteID",quoteID);
        request.getRequestDispatcher("showresponses").forward(request, response);
    }

    private void acceptQuote(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws SQLException, IOException {
        String username = (String) session.getAttribute("username");
        User user = UserDAO.getUser(username);
        int quoteID = Integer.parseInt(request.getParameter("quoteID"));
        Quote quote = QuoteDAO.getQuote(quoteID);

        if (user.userID != quote.getClientID()) {
            System.out.println("Unauthorized acceptance of quote");
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            response.sendRedirect("quotes");
            return;
        }

        boolean accepted = QuoteDAO.acceptQuote(quoteID);
        if (accepted) {
            QuoteResponse qResponse = new QuoteResponse();
            qResponse.setUserID(user.userID);
            qResponse.setQuoteID(quoteID);
            qResponse.setNote(user.getFirstName() + " " + user.getLastName() + " accepted the quote.");
            QuoteResponsesDAO.PostResponse(qResponse);

            Order order = new Order();
            order.setQuoteID(quoteID);
            OrderDAO.insert(order);
        }
        response.sendRedirect("quotes");
    }

    private void rejectQuote(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws SQLException, IOException {
        String username = (String) session.getAttribute("username");
        User user = UserDAO.getUser(username);
        int quoteID = Integer.parseInt(request.getParameter("quoteID"));
        Quote quote = QuoteDAO.getQuote(quoteID);

        if (!(user.userID == quote.getClientID() || user.userID == quote.getContractorID())) {
            System.out.println("Unauthorized rejection of quote");
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            response.sendRedirect("quotes");
            return;
        }

        QuoteDAO.rejectQuote(quoteID);
        QuoteResponse qResponse = new QuoteResponse();
        qResponse.setUserID(user.userID);
        qResponse.setQuoteID(quoteID);
        qResponse.setNote(user.getFirstName() + " " + user.getLastName() + " rejected the quote.");
        QuoteResponsesDAO.PostResponse(qResponse);
        response.sendRedirect("quotes");
    }

    private void completeOrder(HttpServletRequest request, HttpServletResponse response,  HttpSession session)
            throws SQLException, IOException {
        String username = (String) session.getAttribute("username");
        User user = UserDAO.getUser(username);
        if (!user.isClient())
        {
            int orderID = Integer.parseInt(request.getParameter("orderID"));
            Order order = OrderDAO.getOrder(orderID);
            OrderDAO.complete(orderID);

            Quote quote = QuoteDAO.getQuote(order.getQuoteID());
            Bill bill = new Bill();
            bill.setOrderID(orderID);
            bill.setAmount(quote.getAcceptedPrice());
            BillDAO.insert(bill);
        }

        response.sendRedirect("orders");
    }

    private void listBillResponses(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws SQLException, ServletException, IOException {
        String username = (String) session.getAttribute("username");
        int billID = Integer.parseInt(request.getParameter("billID"));
        Bill bill = BillDAO.getBill(billID);
        List<BillResponse> responses = BillResponsesDAO.getResponses(billID);
        Collections.reverse(responses);

        request.setAttribute("listResponses", responses);
        request.setAttribute("bill", bill);
        request.setAttribute("isClient", UserDAO.isClient(username));
        RequestDispatcher dispatcher = request.getRequestDispatcher("BillResponsesList.jsp");
        dispatcher.forward(request, response);
    }

    private void createBillResponse(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws SQLException, ServletException, IOException {
        String username = (String) session.getAttribute("username");
        int billID = Integer.parseInt(request.getParameter("billID"));
        Bill bill = BillDAO.getBill(billID);

        request.setAttribute("bill", bill);
        request.setAttribute("isClient", UserDAO.isClient(username));
        RequestDispatcher dispatcher = request.getRequestDispatcher("BillResponse.jsp");
        dispatcher.forward(request, response);
    }

    private void payBill(HttpServletRequest request, HttpServletResponse response,  HttpSession session)
            throws SQLException, IOException {
        String username = (String) session.getAttribute("username");
        User user = UserDAO.getUser(username);
        if (user.isClient())
        {
            int billID = Integer.parseInt(request.getParameter("billID"));
            BillResponse br = new BillResponse();
            br.setBillID(billID);
            br.setUserID(user.userID);
            br.setNote(user.getFirstName() + " " + user.getLastName() + " paid bill.");
            BillResponsesDAO.postResponse(br);

            //Update bill status
            BillDAO.setBillPaid(billID);
        }

        response.sendRedirect("bills");
    }

    private void sendResponseToBill(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws SQLException, IOException {
        String username = (String) session.getAttribute("username");
        int billID = Integer.parseInt(request.getParameter("billID"));

        //post response
        BillResponse br = new BillResponse();
        br.setBillID(billID);
        br.setUserID(UserDAO.getUserID(username));
        br.setNote(request.getParameter("note"));
        if (!UserDAO.isClient(username)){
            if (request.getParameter("paymentAmount") != null) {
                double newAmount = Double.parseDouble(request.getParameter("paymentAmount"));
                br.setNewAmount(newAmount);
            }
        }
        BillResponsesDAO.postResponse(br);

        //update status
        if (UserDAO.isClient(username)) {
            BillDAO.setBillDisputed(billID);
        }
        else {
            if (br.getNewAmount() > 0)
                BillDAO.setBillPending(billID, br.getNewAmount());
            else //contractor only responded with a note
                BillDAO.setBillPending(billID);
        }
    }

    private void bigClients(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        List<User> users = UserDAO.getUsersWithMostTrees();
        request.setAttribute("title", "Big Clients");
        request.setAttribute("listUser", users);
        request.getRequestDispatcher("RootViews/ClientsList.jsp").forward(request, response);
    }

    private void easyClients(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        List<User> users = UserDAO.getEasyClients();
        request.setAttribute("title", "East Clients");
        request.setAttribute("listUser", users);
        request.getRequestDispatcher("RootViews/ClientsList.jsp").forward(request, response);
    }

    private void prospectiveClients(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        List<User> users = UserDAO.getProspectiveClients();
        request.setAttribute("title", "Prospective Clients");
        request.setAttribute("listUser", users);
        request.getRequestDispatcher("RootViews/ClientsList.jsp").forward(request, response);
    }

    private void badClients(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        List<User> users = UserDAO.getWorstClients();
        request.setAttribute("title", "Bad Clients");
        request.setAttribute("listUser", users);
        request.getRequestDispatcher("RootViews/ClientsList.jsp").forward(request, response);
    }

    private void goodClients(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        List<User> users = UserDAO.getClientsWhoPayFast();
        request.setAttribute("title", "Good Clients");
        request.setAttribute("listUser", users);
        request.getRequestDispatcher("RootViews/ClientsList.jsp").forward(request, response);
    }

    private void oneTreeQuotes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        List<Quote> quotes = QuoteDAO.getOneTreeQuotes();
        request.setAttribute("listQuotes", quotes);
        request.getRequestDispatcher("RootViews/OneTreeQuotes.jsp").forward(request, response);
    }

    private void highestTree(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        List<Tree> trees = TreesDAO.getTallestTree();
        request.setAttribute("listTrees", trees);
        request.getRequestDispatcher("RootViews/HighestTree.jsp").forward(request, response);
    }

    private void overdueBills(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        List<Bill> listBill = BillDAO.getUnpaidBills();
        Collections.reverse(listBill);
        request.setAttribute("listBills", listBill);
        request.getRequestDispatcher("BillList.jsp").forward(request, response);
    }

    private void serveImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String filename = request.getServletPath().substring(
                request.getServletPath().lastIndexOf("/") + 1
        ); // get the filename from the URL
        ServletContext context = getServletContext();
        String path = context.getRealPath(request.getServletPath());

        File file = new File(path);
        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404
            return;
        }

        FileInputStream fStream = new FileInputStream(file);
        BufferedInputStream bStream = new BufferedInputStream(fStream);

        response.setContentType(getServletContext().getMimeType(filename));
        BufferedOutputStream output = new BufferedOutputStream(response.getOutputStream());

        for (int data; (data = bStream.read()) > -1;) {
            output.write(data);
        }

        output.close();
        bStream.close();
        fStream.close();
    }

    /**
     * Save the image of a tree to the server via the Part of the image.
     * @param imagePart
     * @return The String of the location the image is saved at.
     * @throws IOException
     */
    private String saveUploadedImage(Part imagePart, int quoteID) throws IOException {
        ServletContext context = getServletContext();
        String path = context.getRealPath(File.separator + "images" + File.separator + quoteID); //for windows [wwwroot]/images/[quoteID]/
        String extension = getFileExtension(imagePart);
        String fileName = getRandomString() + extension.toLowerCase();
        String fullPath = path + File.separator + fileName;

        File directory = new File(path);
        if (!directory.exists())
            directory.mkdirs();
        File file = new File(fullPath);
        OutputStream out = new FileOutputStream(new File(fullPath));
        InputStream fileContent = imagePart.getInputStream();
        int read = 0;
        final byte[] bytes = new byte[1024];

        while ((read = fileContent.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }
        System.out.println("File " + fileName + " created/updated at " + file.getCanonicalPath());
        out.close();
        fileContent.close();

        return "/images/" + quoteID + "/" +fileName;
    }

    private String getFileExtension(final Part part)
    {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('.') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    private String getRandomString(){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }
}