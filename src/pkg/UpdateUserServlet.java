package pkg;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class UpdateUserServlet
 */
@WebServlet("/UpdateUserServlet")
public class UpdateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user_name=request.getParameter("user_name");
		String password=request.getParameter("password");
		String email=request.getParameter("email");
		//String owner_id=request.getParameter("owner_id");
		
		System.out.println(user_name+""+password+""+email);
		
		PrintWriter pw=response.getWriter();
		try{
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection cn= DriverManager.getConnection("jdbc:mysql://newdbinstance.ce6zjcay1pua.us-west-2.rds.amazonaws.com:3306/TrackerDB?useSSL=false","Shalaka","Shalaka123");
			
			String query="update User set user_name=?,password=? where email=?";
					
			java.sql.PreparedStatement pst=cn.prepareStatement(query);
			
			pst.setString(1, user_name);
			pst.setString(2, password);
			pst.setString(3, email);
//			pst.setString(4, owner_id);
//			pst.setString(5, did);
			
			System.out.println("hello");
			
			
			WebResponse wr=new WebResponse();
			int r= pst.executeUpdate();
			
			if(r==1)
			{
				System.out.println("success");
				
				 JsonObject jsonData = new JsonObject();
				 jsonData.addProperty("User_Name", user_name);
				 jsonData.addProperty("password", password);
				 jsonData.addProperty("Email", email);
				 System.out.println("JSON data: "+jsonData.toString());
					
				wr.data=jsonData;
				wr.result=true;
					
				cn.close();
				pst.close();	
				
				Gson g=new Gson();
				String str=g.toJson(wr);
				pw.println(str);
			}
			else {
				wr.result=false;
				
				cn.close();
				pst.close();	
			Gson g=new Gson();
			String str=g.toJson(wr);
			pw.println(str);
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
