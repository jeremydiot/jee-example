package com.jdiot.jeePro.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class SignOut extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static final String URL_REDIRECT = "/signin";
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		session.invalidate();
		
		resp.sendRedirect(req.getContextPath()+URL_REDIRECT);
	}

}
