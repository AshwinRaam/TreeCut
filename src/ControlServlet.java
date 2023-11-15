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

@MultipartConfig
public class ControlServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO UserDAO = new UserDAO();
    private QuoteDAO QuoteDAO;
    private TreesDAO TreesDAO;
    private QuoteResponsesDAO QuoteResponsesDAO;
    private String currentUser;

    public ControlServlet() {

    }

    public void init() {
        UserDAO = new UserDAO();
        QuoteDAO = new QuoteDAO();
        TreesDAO = new TreesDAO();
        QuoteResponsesDAO = new QuoteResponsesDAO();
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
                        rootPage(request, response);
                        break;
                    case "/root":
                        rootPage(request, response);
                        break;
                    case "/quotes":
                        listQuotes(request, response, session);
                        break;
                    case "/orders":
                        request.getRequestDispatcher("OrderList.jsp").forward(request, response);
                        break;
                    case "/bills":
                        request.getRequestDispatcher("BillList.jsp").forward(request, response);
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
                    case "/createquoteresponse":
                        createQuoteResponse(request, response, session);
                        break;
                    case "/quoterespond":
                        System.out.println("Responding to quote...");
                        sendResponseToQuote(request, response);
                        break;
                    case "/images":
                        System.out.println("Serving image...");
                        serveImage(request, response);
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
            System.out.printf("Uploading tree%d ... ", treeNum);
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

            String size = getStringFromPart(pSize);String height = getStringFromPart(pHeight);
            String nearHouse = getStringFromPart(pNearHouse);
            String location = getStringFromPart(pLocation);
            String treePicURL1 = saveUploadedImage(treePic1Part);
            String treePicURL2 = saveUploadedImage(treePic2Part);
            String treePicURL3 = saveUploadedImage(treePic3Part);

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
        int quoteID = Integer.parseInt(request.getParameter("quoteID"));
        Quote quote = QuoteDAO.getQuote(quoteID);
        List<List<String>> treeImgUrls = TreesDAO.getTreeImages(quoteID);
        List<QuoteResponse> responses = QuoteResponsesDAO.GetResponses(quoteID);
        Collections.reverse(responses);

        request.setAttribute("quoteID", quoteID);
        request.setAttribute("quote", quote);
        request.setAttribute("picUrls", treeImgUrls);
        request.setAttribute("listResponses", responses);
        RequestDispatcher dispatcher = request.getRequestDispatcher("responseList.jsp");
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

    private void serveImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String filename = request.getServletPath().substring(
                request.getServletPath().lastIndexOf("/") + 1
        ); // get the filename from the URL
        ServletContext context = getServletContext();
        String path = context.getRealPath("/images/" + filename);

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
    private String saveUploadedImage(Part imagePart) throws IOException {
        ServletContext context = getServletContext();
        String path = context.getRealPath(File.separator + "images"); //for windows [wwwroot]/images/
        System.out.println(path);
        //String path = File.separator + "images";
        String fileName = getFileName(imagePart); //better hope there isnt a file of the same name
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

        return "/images/"+fileName;
    }

    private String getFileName(final Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}