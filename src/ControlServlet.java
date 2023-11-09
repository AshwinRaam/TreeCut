import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class ControlServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO UserDAO = new UserDAO();
    private QuoteDAO QuoteDAO;
    private QuoteResponsesDAO QuoteResponsesDAO;
    private String currentUser;

    public ControlServlet() {

    }

    public void init() {
        UserDAO = new UserDAO();
        QuoteDAO = new QuoteDAO();
        QuoteResponsesDAO = new QuoteResponsesDAO();
        currentUser = "";
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
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
                        rootPage(request, response);
                        break;
                    case "/root":
                        rootPage(request, response);
                        break;
                    case "/quotes":
                        listQuotes(request, response, session);
                        break;
                    case "/orders":
                        System.out.println("Sending to orders page.");
                        request.getRequestDispatcher("OrderList.jsp").forward(request, response);
                        break;
                    case "/bills":
                        System.out.println("Sending to bills page.");
                        request.getRequestDispatcher("BillList.jsp").forward(request, response);
                        break;
                    case "/logout":
                        logout(request, response);
                        break;
                    case "/createquoteresponse":
                        System.out.println("Sending to quote response page.");
                        createQuoteResponse(request, response);
                        break;
                    case "/quoterespond":
                        System.out.println("Responding to quote...");
                        sendResponseToQuote(request, response);
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

    private void listQuotes(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws SQLException, ServletException, IOException {
        System.out.println("Sending to quotes page.");
        String username = (String) session.getAttribute("username");
        List<Quote> listQuote = QuoteDAO.listQuotesByUsername(username);
        System.out.println("listQuote size: " + listQuote.size());
        request.setAttribute("listQuotes", listQuote);
        request.getRequestDispatcher("QuoteList.jsp").forward(request, response);
    }

    private void rootPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        System.out.println("root view");
        request.setAttribute("listUser", UserDAO.listAllUsers());
        request.getRequestDispatcher("rootView.jsp").forward(request, response);
    }

    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("username") != null) {
            request.getRequestDispatcher("clientDashboard.jsp").forward(request, response);
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
                    request.getRequestDispatcher("clientDashboard.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("contractorDashboard.jsp").forward(request, response);
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
        User user = new User(username, password, role, firstName, lastName, address, phoneNumber, email, creditCardInfo);
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

    private void createQuoteResponse(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        User user = UserDAO.getUser(currentUser);
        int quoteID = Integer.parseInt(request.getParameter("quoteID"));
        Quote quote = QuoteDAO.getQuote(quoteID);

        request.setAttribute("user", user);
        request.setAttribute("quote", quote);
        RequestDispatcher dispatcher = request.getRequestDispatcher("quoteResponse.jsp");
        dispatcher.forward(request, response);
    }

    private void sendResponseToQuote(HttpServletRequest request, HttpServletResponse response) throws SQLException,
            IOException, ServletException {
        String username = request.getParameter("username");
        User user = UserDAO.getUser(username);
        int quoteID = Integer.parseInt(request.getParameter("quoteID"));
        Quote quote = QuoteDAO.getQuote(quoteID);

        //check to see if user is allowed to post
        if (UserDAO.isClient(username)) {
            if (quote.getClientID() != user.getUserID()) {
                System.out.printf("Error: User is not allowed to post on this. ID %d != %d%n",
                        quote.getClientID(), user.getUserID());
                System.out.println("Sending createquoteresponse.");
                request.setAttribute("errorOne", "Error: You are not allowed to post on this quote.");
                request.getRequestDispatcher("createquoteresponse").forward(request, response);
                return;
            }
        }

        //--start conversion--
        System.out.println("--Setting up converted parameters--");
        String sModPrice = request.getParameter("modifiedPrice");
        String sModStartTime = request.getParameter("modifiedStartTime");
        String sModEndTime = request.getParameter("modifiedEndTime");

        LocalDateTime modifiedStartTime = null, modifiedEndTime = null;
        double modifiedPrice = -1;

        if (!sModPrice.isEmpty())
            modifiedPrice = Double.parseDouble(sModPrice);
        if (!sModStartTime.isEmpty())
            modifiedStartTime = LocalDateTime.parse(
                    request.getParameter("modifiedStartTime"), DateTimeFormatter.ISO_DATE_TIME);
        if (!sModEndTime.isEmpty())
            modifiedEndTime = LocalDateTime.parse(
                    request.getParameter("modifiedEndTime"), DateTimeFormatter.ISO_DATE_TIME);

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

        QuoteResponsesDAO.PostResposne(qResponse);
        if (UserDAO.isClient(username)) {
            System.out.println("Finished. sending to client dashboard.");
            response.sendRedirect("clientDashboard.jsp");
            return; //technically not needed, just here in case.
        } else {
            System.out.println("Finished. sending to contractor dashboard.");
            response.sendRedirect("contractorDashboard.jsp");
            return; //technically not needed, just here in case.
        }
    }
}