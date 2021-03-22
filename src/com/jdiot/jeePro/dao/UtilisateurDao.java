package com.jdiot.jeePro.dao;

import com.jdiot.jeePro.beans.Utilisateur;

public interface UtilisateurDao {
    void creer( Utilisateur utilisateur ) throws DAOException;

    Utilisateur trouver( String email ) throws DAOException;
}
