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
import com.mysql.jdbc.PreparedStatement;

/**
 * Servlet implementation class DeviceServlet
 */
@WebServlet("/DeviceServlet")
public class DeviceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeviceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String device_name=request.getParameter("device_name");
		String did=request.getParameter("did");
		String isDisabled=request.getParameter("isDisabled");
		String owner_id=request.getParameter("owner_id");
		
		System.out.println(device_name+""+did+""+isDisabled+""+owner_id);
		
		PrintWriter pw=response.getWriter();
		try{
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection cn= DriverManager.getConnection("jdbc:mysql://newdbinstance.ce6zjcay1pua.us-west-2.rds.amazonaws.com:3306/TrackerDB?useSSL=false","Shalaka","Shalaka123");
			
		
			String query="insert into Device(did,device_name,isDisabled,owner_id)values(?,?,?,(select user_id from User where email=?))";
			String query1="update Device set device_name=? where did=?";
					

			java.sql.PreparedStatement pst=cn.prepareStatement(query);	
			
			pst.setString(1, did);
			pst.setString(2, device_name);
			pst.setString(3, isDisabled);
			pst.setString(4, owner_id);
			System.out.println("hello");
			
			String device_id=null;
		
			int r= pst.executeUpdate();
			
			
			WebResponse wr=new WebResponse();
			
				query ="select max(device_id) as maxdid from Device";
				pst=(PreparedStatement) cn.prepareStatement(query);
				ResultSet rst=pst.executeQuery();
				
				if(rst.next())
				{
					int maxid=rst.getInt(1);
					
				wr.data=maxid+"added";
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
