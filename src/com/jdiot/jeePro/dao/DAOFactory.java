package com.jdiot.jeePro.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;


public class DAOFactory {
	private static final String FICHIER_PROPERTIES = "/com/jdiot/jeePro/dao/dao.properties";
    private static final String PROPERTY_URL = "url";
    private static final String PROPERTY_DRIVER = "driver";
    private static final String PROPERTY_NOM_UTILISATEUR = "nomutilisateur";
    private static final String PROPERTY_MOT_DE_PASSE = "motdepasse";

    private BasicDataSource connectionPool;

    DAOFactory( BasicDataSource connectionPool ) {
        this.connectionPool = connectionPool;
    }

    /*
     * Méthode chargée de récupérer les informations de connexion à la base de
     * données, charger le driver JDBC et retourner une instance de la Factory
     */
    public static DAOFactory getInstance() throws DAOConfigurationException {
        Properties properties = new Properties();
        String url;
        String driver;
        String nomUtilisateur;
        String motDePasse;
        BasicDataSource connectionPool;

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream fichierProperties = classLoader.getResourceAsStream( FICHIER_PROPERTIES );

        if ( fichierProperties == null ) {
            throw new DAOConfigurationException( "Le fichier properties " + FICHIER_PROPERTIES + " est introuvable." );
        }

        try {
            properties.load( fichierProperties );
            url = properties.getProperty( PROPERTY_URL );
            driver = properties.getProperty( PROPERTY_DRIVER );
            nomUtilisateur = properties.getProperty( PROPERTY_NOM_UTILISATEUR );
            motDePasse = properties.getProperty( PROPERTY_MOT_DE_PASSE );
        } catch ( IOException e ) {
            throw new DAOConfigurationException( "Impossible de charger le fichier properties " + FICHIER_PROPERTIES, e );
        }

        try {
        	connectionPool = new BasicDataSource();
        	connectionPool.setDriverClassName(driver);
			connectionPool.setUrl(url);
			connectionPool.setUsername(nomUtilisateur);
			connectionPool.setPassword(motDePasse);
			
			connectionPool.setMinIdle(5);
			connectionPool.setMaxIdle(10);
			connectionPool.setMaxOpenPreparedStatements(100);
			
		} catch (Exception e) {
            e.printStackTrace();
            throw new DAOConfigurationException( "Erreur de configuration du pool de connexions.", e );
		}
        

        DAOFactory instance = new DAOFactory( connectionPool );
        return instance;
    }

    /* Méthode chargée de fournir une connexion à la base de données */
     /* package */ 
    public Connection getConnection() throws SQLException {
    	
       return this.connectionPool.getConnection();
    }

    /*
     * Méthodes de récupération de l'implémentation des différents DAO (un seul
     * pour le moment)
     */
    public UtilisateurDao getUtilisateurDao() {
        return new UtilisateurDaoImpl( this );
    }
}
