<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: o2140482
  Date: 19/01/18
  Time: 11:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Correction</title>
    <jsp:useBean id="questionnaire" type="modele.Questionnaire" scope="request"/>
</head>
<body>

Correction
<p>${questionnaire.getLibelleQuestionnaire()}</p>
    <c:forEach items="${questionnaire.getQuestionsReponses()}" var="question">
        <ul>${question.getQuestion()}</ul>
            <li>Réponse correcte : ${question.getReponseCorrecte()}</li>
    </c:forEach>

<a href="/controleur?action=menu"><li>Menu</li></a>
<a href="/controleur?action=deconnexion"><li>Quitter</li></a>

</body>
</html>
