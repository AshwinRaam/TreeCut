import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
 
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;


public class ControlServlet extends HttpServlet {
	    private static final long serialVersionUID = 1L;
	    private UserDAO UserDAO = new UserDAO();
	    private QuoteDAO QuoteDAO;
	    private String currentUser;
	    private HttpSession session=null;
	    
	    public ControlServlet()
	    {
	    	
	    }
	    
	    public void init()
	    {
	    	UserDAO = new UserDAO();
	    	QuoteDAO = new QuoteDAO();
	    	currentUser= "";
	    }
	    
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        doGet(request, response);
	    }
	    
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        String action = request.getServletPath();
	        System.out.println(action);
	    
	    try {
        	switch(action) {  
        	case "/login":
        		login(request,response);
        		break;
        	case "/register":
        		register(request, response);
        		break;
        	case "/initialize":
        		UserDAO.init();
        		System.out.println("Database successfully initialized!");
        		rootPage(request,response,"");
        		break;
        	case "/root":
        		rootPage(request,response, "");
        		break;
        	case "/logout":
        		logout(request,response);
        		break;
        	 case "/list": 
                 System.out.println("The action is: list");
                 listUser(request, response);           	
                 break;
        	 case "/createquoteresponse":
        		 System.out.println("Sending to quote response page.");
        		 createQuoteResponse(request, response);
        		 break;
	    	}
	    }
	    catch(Exception ex) {
        	System.out.println(ex.getMessage());
	    	}
	    }
        	
	    private void listUser(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException, ServletException {
	        System.out.println("listUser started: 00000000000000000000000000000000000");

	     
	        List<User> listUser = UserDAO.listAllUsers();
	        request.setAttribute("listUser", listUser);       
	        RequestDispatcher dispatcher = request.getRequestDispatcher("UserList.jsp");       
	        dispatcher.forward(request, response);
	     
	        System.out.println("listPeople finished: 111111111111111111111111111111111111");
	    }
	    	        
	    private void rootPage(HttpServletRequest request, HttpServletResponse response, String view) throws ServletException, IOException, SQLException{
	    	System.out.println("root view");
			request.setAttribute("listUser", UserDAO.listAllUsers());
	    	request.getRequestDispatcher("rootView.jsp").forward(request, response);
	    }
	    
	    
	    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	        String username = request.getParameter("username");
	        String password = request.getParameter("password");
	        
	        HttpSession session = request.getSession();
	        
	        if ("root".equals(username) && "pass1234".equals(password)) {
	            System.out.println("Login Successful! Redirecting to root");
	            session.setAttribute("Username", username);
	            rootPage(request, response, "");
	        } else if (UserDAO.isValid(username, password)) {
	            System.out.println("Login Successful! Redirecting");
	            session.setAttribute("Username", username);
	            if (UserDAO.isClient(username)) {
	            	request.getRequestDispatcher("clientDashboard.jsp").forward(request, response);
	            }
	            else {
	            	request.getRequestDispatcher("contractorDashboard.jsp").forward(request, response);
	            }
	        } else {
	            request.setAttribute("loginFailedStr","Login Failed: Please check your credentials.");
	            request.getRequestDispatcher("login.jsp").forward(request, response);
	            return; //doesn't allow for the change in currentUser below
	        }
	        
            currentUser = username;
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
	        response.sendRedirect("login.jsp");
	    }
 
	    
	    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    	currentUser = "";
        		response.sendRedirect("login.jsp");
        	}

	    private void createQuoteResponse(HttpServletRequest request, HttpServletResponse response) 
	    		throws SQLException, ServletException, IOException {
	    	User user = UserDAO.getUser(currentUser);
	    	//User user = UserDAO.getUser("sarahJohnson");
	    	int quoteID = Integer.parseInt(request.getParameter("quoteID"));
	    	Quote quote = QuoteDAO.getQuote(quoteID);
	    	
	    	request.setAttribute("user", user);
	    	request.setAttribute("quote", quote);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("quoteResponse.jsp");       
	        dispatcher.forward(request, response);
	    }
	    
}
	        
	        
	    
	        
	        
	        
	    


