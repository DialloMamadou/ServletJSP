<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: o2140482
  Date: 19/01/18
  Time: 11:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Resultat</title>
    <jsp:useBean id="score" type="java.lang.Double" scope="request"/>
</head>
<body>
<p>Vous avez obtenu <b>${Integer(score)}</b> % de bonnes réponses.</p>
<a href="/controleur?action=menu"><li>Menu</li></a>
<a href="/controleur?action=deconnexion"><li>Quitter</li></a>

</body>
</html>
