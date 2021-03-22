<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Inscription</title>
        <link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
    </head>
    <body>
        <form method="post" action="<c:url value="/signup"/>">
            <fieldset>
                <legend>Inscription</legend>
                <p>Vous pouvez vous inscrire via ce formulaire.</p>

                <label for="email">Adresse email <span class="requis">*</span></label>
                <input type="text" id="email" name="email" value="<c:out value="${requestScope.user.email}"/>" size="20" maxlength="60" />
                <span class="erreur">${requestScope.form.errors['email']}</span>
                <br />

                <label for="motdepasse">Mot de passe <span class="requis">*</span></label>
                <input type="password" id="motdepasse" name="password1" value="" size="20" maxlength="20" />
                <span class="erreur">${requestScope.form.errors['password1']}</span>
                <br />

                <label for="confirmation">Confirmation du mot de passe <span class="requis">*</span></label>
                <input type="password" id="confirmation" name="password2" value="" size="20" maxlength="20" />
                <span class="erreur">${requestScope.form.errors['password2']}</span>
                <br />

                <label for="nom">Nom d'utilisateur</label>
                <input type="text" id="nom" name="username" value="<c:out value="${requestScope.user.username}"/>" size="20" maxlength="20" />
                <span class="erreur">${requestScope.form.errors['username']}</span>
                <br />

                <input type="submit" value="Inscription" class="sansLabel" />
                <br />
                
                <p class="${empty requestScope.form.errors ? 'succes' : 'erreur'}">${requestScope.form.result}</p>
            </fieldset>
        </form>
    </body>
</html>