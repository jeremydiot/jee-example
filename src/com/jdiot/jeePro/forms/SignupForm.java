package com.jdiot.jeePro.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import com.jdiot.jeePro.beans.Utilisateur;
import com.jdiot.jeePro.dao.DAOException;
import com.jdiot.jeePro.dao.UtilisateurDao;

public class SignupForm {

	private static final String ALGO_CHIFFREMENT = "SHA-256";
	
	public static final String FIELD_EMAIL = "email";
	public static final String FIELD_PSWD1 = "password1";
	public static final String FIELD_PSWD2 = "password2";
	public static final String FIELD_USERNAME = "username";
	
	private String result;
	private Map<String,String> errors = new HashMap<String, String>();
	
	private UtilisateurDao      utilisateurDao;

	public SignupForm( UtilisateurDao utilisateurDao ) {
	    this.utilisateurDao = utilisateurDao;
	}
	
	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @return the errors
	 */
	public Map<String, String> getErrors() {
		return errors;
	}

	public Utilisateur inscrireUtilisateur( HttpServletRequest request ) {
	    String email = getValeurChamp( request, FIELD_EMAIL );
	    String password1 = getValeurChamp( request, FIELD_PSWD1 );
	    String password2 = getValeurChamp( request, FIELD_PSWD2 );
	    String username = getValeurChamp( request, FIELD_USERNAME );

	    Utilisateur utilisateur = new Utilisateur();

	    try {
	        traiterEmail( email, utilisateur );
	        traiterMotsDePasse( password1, password2, utilisateur );
	        traiterNom( username, utilisateur );

	        if ( errors.isEmpty() ) {
	            utilisateurDao.creer( utilisateur );
	            result = "Succès de l'inscription.";
	        } else {
	            result = "Échec de l'inscription.";
	        }
	    } catch ( DAOException e ) {
	        result = "Échec de l'inscription : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
	        e.printStackTrace();
	    }
	    
	    return utilisateur;
	}
	
	
	
	/*
	 * Appel à la validation de l'adresse email reçue et initialisation de la
	 * propriété email du bean
	 */
	private void traiterEmail( String email, Utilisateur utilisateur ) {
	    try {
	        validationEmail(email);
	    } catch ( FormValidationException e ) {
	        setErreur( FIELD_EMAIL, e.getMessage() );
	    }
	    utilisateur.setEmail( email );
	}

	private void traiterNom( String nom, Utilisateur utilisateur ) {
	    try {
	        validationNom(nom);
	    } catch ( FormValidationException e ) {
	        setErreur( FIELD_USERNAME, e.getMessage() );
	    }
	    utilisateur.setUsername(nom);
	}

	
	/*
	 * Appel à la validation des mots de passe reçus, chiffrement du mot de
	 * passe et initialisation de la propriété motDePasse du bean
	 */
	private void traiterMotsDePasse( String motDePasse, String confirmation, Utilisateur utilisateur ) {
	    try {
	        validationMotsDePasse( motDePasse, confirmation );
	    } catch ( FormValidationException e ) {
	        setErreur( FIELD_PSWD1, e.getMessage() );
	        setErreur( FIELD_PSWD2, null );
	    }

	    /*
	     * Utilisation de la bibliothèque Jasypt pour chiffrer le mot de passe
	     * efficacement.
	     * 
	     * L'algorithme SHA-256 est ici utilisé, avec par défaut un salage
	     * aléatoire et un grand nombre d'itérations de la fonction de hashage.
	     * 
	     * La String retournée est de longueur 56 et contient le hash en Base64.
	     */
	    ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
	    passwordEncryptor.setAlgorithm( ALGO_CHIFFREMENT );
	    passwordEncryptor.setPlainDigest( false );
	    String motDePasseChiffre = passwordEncryptor.encryptPassword( motDePasse );

	    utilisateur.setPassword( motDePasseChiffre );
	}

	/* Validation de l'adresse email */
	private void validationEmail( String email ) throws FormValidationException {
	    if ( email != null ) {
	        if ( !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
	            throw new FormValidationException( "Merci de saisir une adresse mail valide." );
	        } else if ( utilisateurDao.trouver( email ) != null ) {
	            throw new FormValidationException( "Cette adresse email est déjà utilisée, merci d'en choisir une autre." );
	        }
	    } else {
	        throw new FormValidationException( "Merci de saisir une adresse mail." );
	    }
	}

	private void validationMotsDePasse( String motDePasse, String confirmation ) throws FormValidationException {
	    if ( motDePasse != null && confirmation != null ) {
	        if ( !motDePasse.equals( confirmation ) ) {
	            throw new FormValidationException( "Les mots de passe entrés sont différents, merci de les saisir à nouveau." );
	        } else if ( motDePasse.length() < 3 ) {
	            throw new FormValidationException( "Les mots de passe doivent contenir au moins 3 caractères." );
	        }
	    } else {
	        throw new FormValidationException( "Merci de saisir et confirmer votre mot de passe." );
	    }
	}

	private void validationNom( String nom ) throws FormValidationException {
	    if ( nom != null && nom.length() < 3 ) {
	        throw new FormValidationException( "Le nom d'utilisateur doit contenir au moins 3 caractères." );
	    }
	}

	/*
	 * Ajoute un message correspondant au champ spécifié à la map des erreurs.
	 */
	private void setErreur( String champ, String message ) {
		errors.put( champ, message );
	}

	/*
	 * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
	 * sinon.
	 */
	private static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
	    String valeur = request.getParameter( nomChamp );
	    if ( valeur == null || valeur.trim().length() == 0 ) {
	        return null;
	    } else {
	        return valeur.trim();
	    }
	}
}
