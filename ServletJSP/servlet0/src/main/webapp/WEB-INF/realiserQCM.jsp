<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: o2140482
  Date: 19/01/18
  Time: 11:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <jsp:useBean id="idQuestionnaire" type="java.lang.Integer" scope="request"/>
    <jsp:useBean id="questionCourante" type="modele.QuestionReponse" scope="request"/>
</head>
<body>
<form action="/controleur">
    <input type="hidden" name="action" value="gotoQuestion">
    <input type="hidden" name="choixQuestion" value="${idQuestionnaire}">
    <input type="hidden" name="question" value="${questionCourante.getIdQuestion()}">
    <label>${questionCourante.getQuestion()}</label>
    <select name="choixReponse">
        <c:forEach items="${questionCourante.getReponsesPossibles()}" var="reponse">
            <option value="${reponse}">${reponse}</option>
        </c:forEach>
    </select>
    <input type="submit"/>
</form>
</body>
</html>
