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
import com.mysql.jdbc.PreparedStatement;

/**
 * Servlet implementation class User_DeviceServlet
 */
@WebServlet("/User_DeviceServlet")
public class User_DeviceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public User_DeviceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String user_id=request.getParameter("user_id");
		String device_id=request.getParameter("device_id");
		String isDisabled=request.getParameter("isDisabled");
		//String owner_id=request.getParameter("owner_id");
		
		System.out.println(device_id+""+user_id+""+isDisabled);
		
		PrintWriter pw=response.getWriter();
		try{
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection cn= DriverManager.getConnection("jdbc:mysql://newdbinstance.ce6zjcay1pua.us-west-2.rds.amazonaws.com:3306/TrackerDB?useSSL=false","Shalaka","Shalaka123");
			
			String query="insert into User_Device(user_id,device_id,isDisabled)values((select user_id from User u where u.email=? ),(select device_id from Device where did=?),(select isDisabled from Device where did=?))";
			java.sql.PreparedStatement pst=cn.prepareStatement(query);		
			
			pst.setString(1, user_id);
			pst.setString(2, device_id);
			pst.setString(3, isDisabled);
			System.out.println("hello");
			int r= pst.executeUpdate();
			
			WebResponse wr=new WebResponse();
			
			query ="select max(ud_id) as maxudid from User_Device";
			pst=(PreparedStatement) cn.prepareStatement(query);
			ResultSet rst=pst.executeQuery();
			
			if(rst.next())
			{
				int maxid=rst.getInt(1);
				
				 JsonObject jsonData = new JsonObject();
				 
				 jsonData.addProperty("id", maxid);
				 System.out.println("JSON data: "+jsonData.toString());
					
				wr.data=jsonData;
				wr.result=true;
			Gson g=new Gson();
			String str=g.toJson(wr);
			pw.println(str);
				
			}
			rst.close();
			cn.close();
			pst.close();	

		Gson g=new Gson();
		String str=g.toJson(wr);
		pw.println(str);
		
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
