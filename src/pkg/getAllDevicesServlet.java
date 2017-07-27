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
 * Servlet implementation class getAllDevicesServlet
 */
@WebServlet("/getAllDevicesServlet")
public class getAllDevicesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getAllDevicesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		String owner_id=request.getParameter("owner_id");
		
		System.out.println(owner_id);
		
		PrintWriter pw=response.getWriter();
		try{
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection cn= DriverManager.getConnection("jdbc:mysql://newdbinstance.ce6zjcay1pua.us-west-2.rds.amazonaws.com:3306/TrackerDB?useSSL=false","Shalaka","Shalaka123");
			String query="select * from Device where owner_id=(select user_id from User where email=?)";//
			java.sql.PreparedStatement pst = null;
			pst = cn.prepareStatement(query);

			pst.setString(1, owner_id);
			
			ResultSet rs = pst.executeQuery();
			System.out.println(rs);
			
			WebResponse wr=new WebResponse();
			
			while(rs.next())
			 {
			 
				  	int id = rs.getInt("device_id");
			        String deviceName = rs.getString("device_name");
			        String did = rs.getString("did");
			        String owner_id1 = rs.getString("owner_id");
			        	String isDisabled=rs.getString("isDisabled");
			        
			        System.out.println(id+deviceName+did+owner_id1+isDisabled);
			 
				if(owner_id!=null) {
				    JsonObject jsonData = new JsonObject();
				    jsonData.addProperty("Device_id", id);
				    jsonData.addProperty("Device_Name", deviceName );
				    jsonData.addProperty("Did", did);
				    jsonData.addProperty("Owner_id", owner_id1);
				    jsonData.addProperty("isDisabled", isDisabled);
				    System.out.println("JSON data: "+jsonData.toString());
					
				 wr.data=jsonData;
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
		
			if(owner_id!=null)
			System.out.println(" Success"+owner_id);
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
