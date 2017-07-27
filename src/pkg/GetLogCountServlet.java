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
 * Servlet implementation class GetLogCountServlet
 */
@WebServlet("/GetLogCountServlet")
public class GetLogCountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetLogCountServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String count=null;
		String log_device_id=request.getParameter("log_device_id");
		
		System.out.println(count);
		
		PrintWriter pw=response.getWriter();
		try{
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection cn= DriverManager.getConnection("jdbc:mysql://newdbinstance.ce6zjcay1pua.us-west-2.rds.amazonaws.com:3306/TrackerDB?useSSL=false","Shalaka","Shalaka123");
			
			
			//String query="select did from Device where owner_id=?";
			//select count from Log where log_device_id=(select device_id from Device where did=?)
			
			String query="select count from Log where log_device_id=(select device_id from Device where did=?)";
			java.sql.PreparedStatement pst = null;
			pst = cn.prepareStatement(query);
			//pst.setString(1,count);
			pst.setString(1, log_device_id);
			
			ResultSet rs = pst.executeQuery();
			System.out.println(rs);
			
			WebResponse wr=new WebResponse();
			
			while(rs.next())
			 {
			 count=rs.getString("count");
			 String time=rs.getString("timestamp");
			 if(count!=null) {
				 
				 
				 JsonObject jsonData = new JsonObject();
				    jsonData.addProperty("Log Count", count);
				    jsonData.addProperty("TimeStamp", time);
				    System.out.println("JSON data: "+jsonData.toString());
					
					wr.data=jsonData;
				
				 wr.result=true;
				Gson g=new Gson();
				String str=g.toJson(wr);
				pw.println(str);
			}else {
				wr.result=false;
				System.out.println("failed");
				Gson g=new Gson();
				String str=g.toJson(wr);
				}
			}
			
			cn.close();
			pst.close();
			rs.close();
		
			if(count!=null)
			System.out.println(" Success"+count);
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
