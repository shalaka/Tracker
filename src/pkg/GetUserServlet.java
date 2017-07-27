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
 * Servlet implementation class GetUserServlet
 */
@WebServlet("/GetUserServlet")
public class GetUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	
		String email=request.getParameter("email");
		
		System.out.println(email);
		
		PrintWriter pw=response.getWriter();
		try{
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection cn= DriverManager.getConnection("jdbc:mysql://newdbinstance.ce6zjcay1pua.us-west-2.rds.amazonaws.com:3306/TrackerDB?useSSL=false","Shalaka","Shalaka123");
			String query="select * from User where email=?";
			java.sql.PreparedStatement pst = null;
			pst = cn.prepareStatement(query);

			pst.setString(1, email);
			
			ResultSet rs = pst.executeQuery();
			System.out.println(rs);
			
			WebResponse wr=new WebResponse();
			
			while(rs.next())
			 {
			 
				  	int id = rs.getInt("user_id");
			        String userName = rs.getString("user_name");
			        String password = rs.getString("password");
			        String mobile = rs.getString("mobile");
			        String email1=rs.getString("email");
			        	String isDisabled=rs.getString("isDisabled");
			        
			        System.out.println(id+userName+password+mobile+email1+isDisabled);
			 
				if(email!=null) {
				    JsonObject jsonData = new JsonObject();
				    jsonData.addProperty("User_Id", id);
				    jsonData.addProperty("User_Name", userName );
				    jsonData.addProperty("Password", password);
				    jsonData.addProperty("mobile", mobile);
				    jsonData.addProperty("isDisabled", isDisabled);
				    System.out.println("JSON data: "+jsonData.toString());
					
					wr.data=jsonData;
			  //   wr.data=id+","+userName+","+password+","+mobile+","+email1+","+isDisabled;
				 wr.result=true;
				Gson g=new Gson();
				String str=g.toJson(wr);
				pw.println(str);

				}else {
				wr.result=false;
				System.out.println("not found");
				Gson g=new Gson();
				String str=g.toJson(wr);
				//pw.println(str);
				}
			}
			
			cn.close();
			pst.close();
			rs.close();
		
			if(email!=null)
			System.out.println(" Success"+email);
			else
			System.out.println("Login Failed");
			
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
