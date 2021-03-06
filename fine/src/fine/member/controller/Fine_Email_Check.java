package fine.member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fine.member.service.Service;

@WebServlet("/EmailCheck")
public class Fine_Email_Check extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public Fine_Email_Check() {
        super();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		excute(request, response);
	}	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		excute(request, response);
	}
	protected void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		String email = request.getParameter("email");
		PrintWriter out = response.getWriter();
		System.out.println("이메일체크옴");
		try {
			Service services = new Service();
			boolean result = services.idCheck(email);
			if (result == true)
				out.println("이미 사용중인 email 입니다");
			else
				out.println("사용가능한 email 입니다");
		} catch (SQLException e) {
			e.printStackTrace();
			out.println("입력범위를 벗어났습니다. 다시 입력해주세요.");
		} catch (Exception e) {
			out.println("조회하는 도중에 오류가 발생했습니다.");
		}
		out.flush();
		out.close();
	}
}