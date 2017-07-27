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
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import jdk.nashorn.api.scripting.JSObject;
import pojo.Device;

/**
 * Servlet implementation class getDeviceInfoFromU_DServlet
 */
@WebServlet("/getDeviceInfoFromU_DServlet")
public class getDeviceInfoFromU_DServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getDeviceInfoFromU_DServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String device_id=request.getParameter("device_id");

			System.out.println(device_id);
			
			PrintWriter pw=response.getWriter();
			try{
				
				Class.forName("com.mysql.jdbc.Driver");
				Connection cn= DriverManager.getConnection("jdbc:mysql://newdbinstance.ce6zjcay1pua.us-west-2.rds.amazonaws.com:3306/TrackerDB?useSSL=false","Shalaka","Shalaka123");
				//String query="select * from User_Device where user_id=(select user_id from User where email=?)";//and device_id=(select device_id from Device where did=?)
				//String q="select * from Device where device_id IN (select device_id from User_Device where user_id IN (select user_id from User where email=?))";
					
				String q="select * from Device d inner join User_Device ud on d.device_id=ud.device_id inner join User u on ud.user_id=u.user_id where u.email=?";
				
				java.sql.PreparedStatement pst = null;
				pst = cn.prepareStatement(q);

				pst.setString(1, device_id);
						
				ResultSet rs = pst.executeQuery();
				System.out.println(rs);
				
				WebResponse wr=new WebResponse();
				
				JsonObject mainObj=new JsonObject();
				JsonObject valueObj=new JsonObject();
				JsonArray list = new JsonArray();
				
				
				while(rs.next())
				 {
					JsonArray array=new JsonArray();
				
					  	int dev_id = rs.getInt("device_id");
				        String did = rs.getString("did");
				        String device_name = rs.getString("device_name");
				        String isDisabled = rs.getString("isDisabled");
				        int owner=rs.getInt("owner_id");
				        
				        System.out.println(dev_id+did+device_name+isDisabled);
				 
					
				   
						Device obj=new Device(dev_id, did, device_name, isDisabled, owner);
						valueObj.addProperty("Device_id", dev_id);
						valueObj.addProperty("DID", did );
						valueObj.addProperty("Device_name", device_name);
						valueObj.addProperty("isDisabled", isDisabled);
						valueObj.addProperty("Owner", owner);
						list.add(valueObj);
						
						System.out.println("JSON data: "+mainObj.toString());
						
				 }
				
				if(list!=null) {
					wr.data=list;
					 wr.result=true;
					}else {
					wr.result=false;
					System.out.println("not found");
					}
				
				Gson g=new Gson();
				String str=g.toJson(wr);
				pw.println(str);
				
				cn.close();
				pst.close();
				rs.close();
			
				if(device_id!=null)
				System.out.println(" Success"+device_id);
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
