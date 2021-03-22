<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Connexion</title>
        <link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
    </head>
    <body>
        <form method="post" action="<c:url value="/signin"/>">
            <fieldset>
                <legend>Connexion</legend>
                <p>Vous pouvez vous connecter via ce formulaire.</p>
                <c:if test="${empty sessionScope.userSession && !empty requestScope.intervalleConnexions}">
                	<p class="info">(Vous ne vous êtes pas connecté(e) depuis ce navigateur depuis ${requestScope.intervalleConnexions})</p>
                </c:if>

                <label for="nom">Adresse email <span class="requis">*</span></label>
                <input type="text" id="email" name="email" value="<c:out value="${requestScope.user.email}"/>" size="20" maxlength="60" />
                <span class="erreur">${requestScope.form.erreurs['email']}</span>
                <br />

                <label for="motdepasse">Mot de passe <span class="requis">*</span></label>
                <input type="password" id="motdepasse" name="password" value="" size="20" maxlength="20" />
                <span class="erreur">${requestScope.form.erreurs['password']}</span>
                <br />
                
                <label for="memoire">Se souvenir de moi</label>
                <input type="checkbox" id="memoire" name="memoire" />
                <br />
                
                <input type="submit" value="Connexion" class="sansLabel" />
                <a href="<c:url value="/signout"/>">Deconnexion</a>
                <br />
                
                <p class="${empty form.erreurs ? 'succes' : 'erreur'}">${requestScope.form.resultat}</p>
                
                 <%-- Vérification de la présence d'un objet utilisateur en session --%>
                <c:if test="${!empty sessionScope.userSession}">
                    <%-- Si l'utilisateur existe en session, alors on affiche son adresse email. --%>
                    <p class="succes">Vous êtes connecté(e) avec l'adresse : ${sessionScope.userSession.email}</p>
                </c:if>
                
            </fieldset>
        </form>
    </body>
</html>