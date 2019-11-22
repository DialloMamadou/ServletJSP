<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: o2140482
  Date: 19/01/18
  Time: 11:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Questionnaire à faire</title>
    <jsp:useBean id="questionnaires" type="java.util.Collection" scope="request"/>
</head>
<body>
Questionnaire à faire

<form action="/controleur">
    <input type="hidden" name="action" value="gotoQuestion">
    <select name="choixQuestion">
        <c:forEach items="${questionnaires}" var="questionnaire">
            <option value="${questionnaire.idQuestionnaire}">${questionnaire.libelleQuestionnaire}</option>
        </c:forEach>
    </select>
    <input type="submit"/>
</form>
</body>
</html>
