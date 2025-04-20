<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Recipe Details</title></head>
<body>

<c:if test="${not empty recipe}">
    <h1>${recipe.title}</h1>
    <img src="${recipe.image}" alt="${recipe.title}" width="300"/>

    <p><strong>Ready in:</strong> ${recipe.readyInMinutes} minutes</p>

    <h3>Ingredients:</h3>
    <ul>
        <c:forEach var="ing" items="${recipe.ingredients}">
            <li>${ing.original}</li>
        </c:forEach>
    </ul>
</c:if>

<c:if test="${not empty error}">
    <p style="color:red;">${error}</p>
</c:if>

<a href="form">Back to Search</a>

</body>
</html>
