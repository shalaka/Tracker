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
 * Servlet implementation class ShowLogCountServlet
 */
@WebServlet("/ShowLogCountServlet")
public class ShowLogCountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowLogCountServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String log_device_id=request.getParameter("log_device_id");
		//String isDisabled=request.getParameter("isDisabled");
		String count=request.getParameter("count");
		
		System.out.println(log_device_id+""+count);
		
		PrintWriter pw=response.getWriter();
		try{
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection cn= DriverManager.getConnection("jdbc:mysql://newdbinstance.ce6zjcay1pua.us-west-2.rds.amazonaws.com:3306/TrackerDB?useSSL=false","Shalaka","Shalaka123");
			
			
			String query="select count from Log where log_device_id=?";
			
			java.sql.PreparedStatement pst = null;
			pst = cn.prepareStatement(query);
			pst.setString(1,log_device_id);
			
			ResultSet rs = pst.executeQuery();
			
			
			//ResultSet r=.executeQuery(query);
			System.out.println(rs);
			
			WebResponse wr=new WebResponse();
			
			while(rs.next())
			 {
				
			 count=rs.getString("count");
			 }

			if(log_device_id!=null)
			{
			System.out.println(" Success"+count);
			 JsonObject jsonData = new JsonObject();
			  jsonData.addProperty("Log count", count);
			  System.out.println("JSON data: "+jsonData.toString());
				
			wr.data=jsonData;
			wr.result=true;		
			Gson g=new Gson();
			String str=g.toJson(wr);
			pw.println(str);

			}
			else {
			System.out.println(" Failed");
			}
				pst=(PreparedStatement) cn.prepareStatement(query);
				ResultSet rst=pst.executeQuery();
				
				if(rst.next())
				{
					int maxid=rst.getInt(1);
					
				wr.data=maxid+"";
				wr.result=true;
					
				}
				rst.close();
				cn.close();
				pst.close();	

				wr.result=false;
			
			Gson g=new Gson();
			String str=g.toJson(wr);
			pw.println(str);
			//}
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
