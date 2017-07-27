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
 * Servlet implementation class LogCountServlet
 */
@WebServlet("/LogCountServlet")
public class LogCountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogCountServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String log_device_id=request.getParameter("log_device_id");
		String timestamp=request.getParameter("timestamp");
		String count=request.getParameter("count");
		
		
		PrintWriter pw=response.getWriter();
		try{
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection cn= DriverManager.getConnection("jdbc:mysql://newdbinstance.ce6zjcay1pua.us-west-2.rds.amazonaws.com:3306/TrackerDB?useSSL=false","Shalaka","Shalaka123");
			
			String query="insert into Log(log_device_id,timestamp,count)values(?,?,?)";
					
			java.sql.PreparedStatement pst=cn.prepareStatement(query);	
			
			pst.setString(1, log_device_id);
			pst.setString(2, timestamp);
			pst.setString(3, count);
			int r= pst.executeUpdate();
			
			WebResponse wr=new WebResponse();
			
			if(r==1)
			{
				System.out.println("success");
				//wr.data="success";
				
				 JsonObject jsonData = new JsonObject();
				    jsonData.addProperty("log_Device_id", log_device_id);
				    jsonData.addProperty("timestamp", timestamp );
				    jsonData.addProperty("count", count);
				    System.out.println("JSON data: "+jsonData.toString());
					
					wr.data=jsonData;
				wr.result=true;
				Gson g=new Gson();
				String str=g.toJson(wr);
				pw.println(str);
				query ="select max(log_id) as maxuid from Log";
				pst=cn.prepareStatement(query);
				ResultSet rst=pst.executeQuery();
				
				
				rst.close();
				cn.close();
				pst.close();	
			}
			else {
				System.out.println("failed");

				wr.result=false;
			
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
