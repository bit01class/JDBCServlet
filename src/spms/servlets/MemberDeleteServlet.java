package spms.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/member/delete")
public class MemberDeleteServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try{
			ServletContext sc = this.getServletContext();
			Class.forName(sc.getInitParameter("driver"));
			conn = DriverManager.getConnection(sc.getInitParameter("url"), sc.getInitParameter("username"), sc.getInitParameter("password"));
			pstmt = conn.prepareStatement("delete from members where mno = ?");
			pstmt.setInt(1, Integer.parseInt(req.getParameter("no")));
			pstmt.executeUpdate();
			
			resp.sendRedirect("list");
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{ if(pstmt != null){ pstmt.close(); }}catch(Exception e){}
			try{ if(conn != null){ conn.close(); }}catch(Exception e){}
		}
	}

}