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
 * Servlet implementation class DeleteUserServlet
 */
@WebServlet("/DeleteUserServlet")
public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email=request.getParameter("email");
		//String isDisabled=request.getParameter("isDisabled");
		String gotMail;
		System.out.println(email);
		
		PrintWriter pw=response.getWriter();
		try{
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection cn= DriverManager.getConnection("jdbc:mysql://newdbinstance.ce6zjcay1pua.us-west-2.rds.amazonaws.com:3306/TrackerDB?useSSL=false","Shalaka","Shalaka123");
			
			String q1="alter table User add constraint ";
			String query="delete from User where email='?'";
			
			Statement stmt=cn.createStatement();
			PreparedStatement pst1=(PreparedStatement) cn.prepareStatement(query);
			pst1.executeQuery("select * from User");
			ResultSet rs=pst1.getResultSet();
			WebResponse wr=new WebResponse();
				
			while(rs.next()) {
				gotMail=rs.getString(5);
				  
				if(email.equals(gotMail))
                  {
                      pst1.executeUpdate("delete from User where email='"+email+"'");
                      JsonObject jsonData = new JsonObject();
     				 jsonData.addProperty("Email", email);
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
