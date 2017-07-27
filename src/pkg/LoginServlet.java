package pkg;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

	public void init(ServletConfig config) throws ServletException {
	}

	
	public void destroy() {
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String user_name=request.getParameter("user_name");
		String password=request.getParameter("password");
		System.out.println("uname"+user_name+"\n"+"password"+password);
		
		PrintWriter pw=response.getWriter();
		System.out.println("hello");
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection cn=DriverManager.getConnection("jdbc:mysql://newdbinstance.ce6zjcay1pua.us-west-2.rds.amazonaws.com:3306/TrackerDB?useSSL=false","Shalaka","Shalaka123");
			
			WebResponse wr=new WebResponse();

			String query="select * from User where user_name=? and password=?";
			
			java.sql.PreparedStatement pst1=cn.prepareStatement(query);
			pst1.setString(1, user_name);
			pst1.setString(2, password);
			
			ResultSet rst=pst1.executeQuery();
			
			if(rst.next()) {

				int uid=rst.getInt(1);
				String uname=rst.getString(2);
				String pass=rst.getString(3);
				String mobile=rst.getString(4);
				String email=rst.getString(5);
				String isDisable=rst.getString(6);
				
				System.out.println(uid);
				
				 JsonObject jsonData = new JsonObject();
				 jsonData.addProperty("User_Id", uid);
				 jsonData.addProperty("User_Name", user_name );
				 jsonData.addProperty("Password", pass);
				 jsonData.addProperty("Mobile", mobile);
				 jsonData.addProperty("Email", email);
				 jsonData.addProperty("isDisabled", isDisable);
				 System.out.println("JSON data: "+jsonData.toString());
					
				wr.data=jsonData;
				wr.result=true;
				
			}
			
			Gson g=new Gson();
			String str=g.toJson(wr);
			pw.println(str);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
