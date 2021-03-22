package com.jdiot.jeePro.servlets;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.jdiot.jeePro.beans.Fichier;
import com.jdiot.jeePro.forms.UploadForm;


public class Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public static final String ATT_FICHIER = "fichier";
    public static final String ATT_FORM    = "form";
    
	public static final String CHAMP_DESCRIPTION = "description";
	public static final String CHAMP_FICHIER = "fichier";
	public static final String CHEMIN = "chemin";
	public static final String VIEW_PATH = "/WEB-INF/views/upload.jsp";
	public static final int TAILLE_TAMPON = 10240; // 10 ko

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.getServletContext().getRequestDispatcher(VIEW_PATH).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			    
	    /*
         * Lecture du paramètre 'chemin' passé à la servlet via la déclaration
         * dans le web.xml
         */
        String chemin = this.getServletConfig().getInitParameter( CHEMIN );

        /* Préparation de l'objet formulaire */
        UploadForm form = new UploadForm();

        /* Traitement de la requête et récupération du bean en résultant */
        Fichier fichier = form.enregistrerFichier( request, chemin );

        /* Stockage du formulaire et du bean dans l'objet request */
        request.setAttribute( ATT_FORM, form );
        request.setAttribute( ATT_FICHIER, fichier );

        this.getServletContext().getRequestDispatcher( VIEW_PATH ).forward( request, response );

	}
}
