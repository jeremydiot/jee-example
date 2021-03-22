package com.jdiot.jeePro.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jdiot.jeePro.beans.Utilisateur;
import com.jdiot.jeePro.dao.DAOFactory;
import com.jdiot.jeePro.dao.UtilisateurDao;
import com.jdiot.jeePro.forms.SignupForm;

public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String VIEW_PATH = "/WEB-INF/views/signUp.jsp";
	public static final String ATT_FORM = "form";
	public static final String ATT_USER = "user";
    public static final String CONF_DAO_FACTORY = "daofactory";
	
    private UtilisateurDao utilisateurDao;

    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.utilisateurDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getUtilisateurDao();
    }
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		this.getServletContext().getRequestDispatcher(VIEW_PATH).forward( req, resp );
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		SignupForm form = new SignupForm(utilisateurDao);
		
		Utilisateur user = form.inscrireUtilisateur(req);
	
		req.setAttribute(ATT_FORM, form);
		req.setAttribute(ATT_USER, user);
		
		this.getServletContext().getRequestDispatcher(VIEW_PATH).forward( req, resp );
	}
}
