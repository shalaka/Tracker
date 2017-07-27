package pkg;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mysql.jdbc.PreparedStatement;

/**
 * Servlet implementation class DeleteDeviceServlet
 */
@WebServlet("/DeleteDeviceServlet")
public class DeleteDeviceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteDeviceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String did=request.getParameter("did");
		//String isDisabled=request.getParameter("isDisabled");
		String gotDiD;
		System.out.println(did);
		
		PrintWriter pw=response.getWriter();
		try{
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection cn= DriverManager.getConnection("jdbc:mysql://newdbinstance.ce6zjcay1pua.us-west-2.rds.amazonaws.com:3306/TrackerDB?useSSL=false","Shalaka","Shalaka123");
			
			String query="delete from Device where did='?'";
			
			Statement stmt=cn.createStatement();
			java.sql.PreparedStatement pst1=(PreparedStatement) cn.prepareStatement(query);
			pst1.executeQuery("select * from Device");
			ResultSet rs=pst1.getResultSet();
			WebResponse wr=new WebResponse();
				
			while(rs.next()) {
				gotDiD=rs.getString(2);
				String name=rs.getString(3);
				  
				if(did.equals(gotDiD))
                  {
                      pst1.executeUpdate("delete from Device where did='"+did+"'");
                      JsonObject jsonData = new JsonObject();
     				 jsonData.addProperty("Device_name",name );
     				 System.out.println("JSON data: "+jsonData.toString());
     					
     				wr.data=jsonData;
     				wr.result=true;
      				Gson g=new Gson();
					String str=g.toJson(wr);
					pw.println(str);
                      break;
                  }
				else {
					wr.result=false;
					System.out.println("failed");
					Gson g=new Gson();
					String str=g.toJson(wr);
					pw.println(str);
				}
            }
				
			cn.close();
			stmt.close();
			rs.close();
     
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
