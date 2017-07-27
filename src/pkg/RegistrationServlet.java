package pkg;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;


@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public RegistrationServlet() {
        super();
       
    }
    
    /**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}
	
	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
//	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		super.service(request, response);
//	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	
		response.setContentType("text/html;charset=UTF-8");
		//doGet(request, response);
		String user_name=request.getParameter("user_name");
		String password=request.getParameter("password");
		String mobile=request.getParameter("mobile");
		String email=request.getParameter("email");
		String isDisabled=request.getParameter("isDisabled");

		System.out.println(user_name+""+password+""+mobile+""+email);
		
		PrintWriter pw=response.getWriter();
		try{
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection cn= DriverManager.getConnection("jdbc:mysql://newdbinstance.ce6zjcay1pua.us-west-2.rds.amazonaws.com:3306/TrackerDB?useSSL=false","Shalaka","Shalaka123");
			
			String query="insert into User(password,email,mobile,user_name,isDisabled)values(?,?,?,?,?)";
			
			java.sql.PreparedStatement pst=cn.prepareStatement(query);		
			
			pst.setString(1, password);
			pst.setString(2, email);
			pst.setString(3, mobile);
			pst.setString(4, user_name);
			pst.setString(5, isDisabled);
			System.out.println("hello");
			int r= pst.executeUpdate();
			
			WebResponse wr=new WebResponse();
			
			if(r==1)
			{
				
//				String username=rst.getString("user_name");
//				String passwd=rst.getString("password");
//				String emailid=rst.getString("email");
//				String phone=rst.getString("mobile");


				JsonObject jsonData = new JsonObject();
					jsonData.addProperty("User_name", user_name);
					jsonData.addProperty("User_ID", password );
					jsonData.addProperty("Email", email);
					jsonData.addProperty("Mobile", mobile);
					System.out.println("JSON data: "+jsonData.toString());
						
				 wr.data=jsonData;
				 wr.result=true;
				 cn.close();
				 pst.close();	
				 Gson g=new Gson();
				String str=g.toJson(wr);
				pw.println(str);
				}
			else {
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

	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("in dopost");
		response.setContentType("text/html;charset=UTF-8");
		//doGet(request, response);
//		String user_name=request.getParameter("txtname");
//		String password=request.getParameter("txtpass");
//		String mobile=request.getParameter("txtmob");
//		String email=request.getParameter("txtemail");
//		String isDisabled=request.getParameter("txtDis");
		
		String user_name=request.getParameter("user_name");
		String password=request.getParameter("password");
		String mobile=request.getParameter("mobile");
		String email=request.getParameter("email");
		String isDisabled=request.getParameter("isDisabled");

		System.out.println(user_name+""+password+""+mobile+""+email);
		
		PrintWriter pw=response.getWriter();
		try{
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection cn= DriverManager.getConnection("jdbc:mysql://newdbinstance.ce6zjcay1pua.us-west-2.rds.amazonaws.com:3306/TrackerDB?useSSL=false","Shalaka","Shalaka123");
			
			String query="insert into User(password,email,mobile,user_name,isDisabled)values(?,?,?,?,?)";
			
			java.sql.PreparedStatement pst=cn.prepareStatement(query);		
			
			pst.setString(1, password);
			pst.setString(2, email);
			pst.setString(3, mobile);
			pst.setString(4, user_name);
			pst.setString(5, isDisabled);
			System.out.println("hello");
			int r= pst.executeUpdate();
			System.out.println("r value"+r);
			WebResponse wr=new WebResponse();
			wr.data="success";
			wr.result=true;
			
			
			if(r==1)
			{
				System.out.println("success");
				 JsonObject jsonData = new JsonObject();
				 jsonData.addProperty("User_Name", user_name);
				 jsonData.addProperty("email", email );
				 jsonData.addProperty("Moblie",mobile);
				 jsonData.addProperty("isDisabled", isDisabled);
				 System.out.println("JSON data: "+jsonData.toString());
					
				wr.data=jsonData;
				wr.result=true;

				Gson g=new Gson();
				String str=g.toJson(wr);
				pw.println(str);
				query ="select max(user_id) as maxuid from User";
				pst=cn.prepareStatement(query);
				ResultSet rst=pst.executeQuery();
				
				if(rst.next())
				{
					int maxid=rst.getInt(1);
					
				wr.data=maxid+"";
				wr.result=true;
				Gson g1=new Gson();
				String str1=g.toJson(wr);
				pw.println(str1);
					
				}
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

}
