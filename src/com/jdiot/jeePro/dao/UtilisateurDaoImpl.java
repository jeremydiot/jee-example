package com.jdiot.jeePro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jdiot.jeePro.beans.Utilisateur;

public class UtilisateurDaoImpl implements UtilisateurDao {

	private static final String SQL_SELECT_PAR_EMAIL = "SELECT id, email, nom, mot_de_passe, date_inscription FROM Utilisateur WHERE email = ?";
	private static final String SQL_INSERT = "INSERT INTO Utilisateur (email, mot_de_passe, nom, date_inscription) VALUES (?, ?, ?, NOW())";
	
	
	private DAOFactory daoFactory;
	
	public UtilisateurDaoImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	/* Implémentation de la méthode définie dans l'interface UtilisateurDao */
	@Override
	public void creer( Utilisateur utilisateur ) throws DAOException {
	    Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet valeursAutoGenerees = null;

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        preparedStatement = DAOUtilitaire.initialisationRequetePreparee( connexion, SQL_INSERT, true, utilisateur.getEmail(), utilisateur.getPassword(), utilisateur.getUsername());
	        int statut = preparedStatement.executeUpdate();
	        /* Analyse du statut retourné par la requête d'insertion */
	        if ( statut == 0 ) {
	            throw new DAOException( "Échec de la création de l'utilisateur, aucune ligne ajoutée dans la table." );
	        }
	        /* Récupération de l'id auto-généré par la requête d'insertion */
	        valeursAutoGenerees = preparedStatement.getGeneratedKeys();
	        if ( valeursAutoGenerees.next() ) {
	            /* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
	            utilisateur.setId( valeursAutoGenerees.getLong( 1 ) );
	        } else {
	            throw new DAOException( "Échec de la création de l'utilisateur en base, aucun ID auto-généré retourné." );
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	    	DAOUtilitaire.fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
	    }
	}

	/* Implémentation de la méthode définie dans l'interface UtilisateurDao */
	@Override
	public Utilisateur trouver( String email ) throws DAOException {
	    Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Utilisateur utilisateur = null;

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        preparedStatement = DAOUtilitaire.initialisationRequetePreparee( connexion, SQL_SELECT_PAR_EMAIL, false, email );
	        resultSet = preparedStatement.executeQuery();
	        /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
	        if ( resultSet.next() ) {
	            utilisateur = map( resultSet );
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	    	DAOUtilitaire.fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	    }

	    return utilisateur;
	}
	
	/*
	 * Simple méthode utilitaire permettant de faire la correspondance (le
	 * mapping) entre une ligne issue de la table des utilisateurs (un
	 * ResultSet) et un bean Utilisateur.
	 */
	private static Utilisateur map( ResultSet resultSet ) throws SQLException {
	    Utilisateur utilisateur = new Utilisateur();
	    utilisateur.setId( resultSet.getLong( "id" ) );
	    utilisateur.setEmail( resultSet.getString( "email" ) );
	    utilisateur.setPassword( resultSet.getString( "mot_de_passe" ) );
	    utilisateur.setUsername( resultSet.getString( "nom" ) );
	    utilisateur.setDateInscription( resultSet.getTimestamp( "date_inscription" ) );
	    return utilisateur;
	}

}
