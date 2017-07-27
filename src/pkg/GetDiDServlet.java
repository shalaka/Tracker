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
 * Servlet implementation class GetDiDServlet
 */
@WebServlet("/GetDiDServlet")
public class GetDiDServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetDiDServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String did=request.getParameter("did");
		String owner_id=request.getParameter("owner_id");
		
		System.out.println(did+""+owner_id);
		
		PrintWriter pw=response.getWriter();
		try{
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection cn= DriverManager.getConnection("jdbc:mysql://newdbinstance.ce6zjcay1pua.us-west-2.rds.amazonaws.com:3306/TrackerDB?useSSL=false","Shalaka","Shalaka123");
			
			
			String query="select did from Device where owner_id=?";
			
			java.sql.PreparedStatement pst = null;
			pst = cn.prepareStatement(query);
			pst.setString(1,owner_id);
			
			ResultSet rs = pst.executeQuery();
			
			
			System.out.println(rs);
			
			WebResponse wr=new WebResponse();
			
			while(rs.next())
			 {
			 did=rs.getString("did");
			}

			if(did!=null)
			System.out.println(" Success"+did);
			else
			System.out.println("Login Failed");
			
			
				query ="select max(user_id) as maxuid from User";
				pst=(PreparedStatement) cn.prepareStatement(query);
				ResultSet rst=pst.executeQuery();
				
				if(rst.next())
				{
					int maxid=rst.getInt(1);
					
					 JsonObject jsonData = new JsonObject();
					    jsonData.addProperty("Returned DId", did);
					    System.out.println("JSON data: "+jsonData.toString());
						
						wr.data=jsonData;
						wr.result=true;
					
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
