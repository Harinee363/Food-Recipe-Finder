<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Recipe Results</title></head>
<body>
<h2>Search Results for: ${ingredient}</h2>

<p style="font-size:18px; color:blue;">
  <a href="/recipe/favorites">👉 View Favorites</a>
</p>

<c:forEach var="recipe" items="${recipes}">
    <div style="border: 1px solid gray; margin: 10px; padding: 10px;">
        <h2>${recipe.title}</h2>
        <img src="${recipe.image}" alt="${recipe.title}" width="200"/>

        <p><strong>Ready in:</strong> ${recipe.readyInMinutes} minutes</p>
        
        <a href="details?id=${recipe.id}">View Details</a>

        <form action="/recipe/saveFavorites" method="post">
            <input type="hidden" name="recipeId" value="${recipe.id}" />
            <input type="hidden" name="title" value="${recipe.title}" />
            <input type="hidden" name="image" value="${recipe.image}" />
            <input type="hidden" name="readyInMinutes" value="${recipe.readyInMinutes}" />
            <button type="submit">💾 Save to Favorites</button>
        </form>
    </div>
</c:forEach>

<!-- Link to view favorites -->
<a href="/recipe/favorites">View Favorites</a>

</body>
</html>
