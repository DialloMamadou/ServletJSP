<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: o2140482
  Date: 19/01/18
  Time: 10:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Connexion</title>
    <jsp:useBean id="erreur" class="java.lang.String" scope="request"/>
</head>
<body>

<c:if test="${erreur.length() > 0}">
    <span style="color: #ff8c00;">${erreur}</span>
</c:if>

    <form action="/controleur" method="post">
        <input type="hidden" name="action" value="connexion">
        Login : <input type="text" name="login"><br />
        Mot de passe : <input type="password" name="pwd"><br />
        <input type="submit" value="Valider">
    </form>
</body>
</html>
