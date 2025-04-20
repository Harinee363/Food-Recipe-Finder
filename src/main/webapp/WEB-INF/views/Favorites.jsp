<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Your Favorites</title></head>
<body>

<h2>Your Saved Recipes</h2>

<c:forEach var="recipe" items="${savedRecipes}">
    <div style="border: 1px solid gray; margin: 10px; padding: 10px;">
    <h2>${recipe.title}</h2>
    <img src="${recipe.imageUrl}" alt="${recipe.title}" width="200"/>
    <p><strong>Ready in:</strong> ${recipe.readyInMinutes} minutes</p>
    </div>
</c:forEach>

</body>
</html>
