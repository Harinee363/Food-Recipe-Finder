<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head><title>Recipe List</title></head>
<body>
<h1>Saved Recipes</h1>

<table>
    <thead>
        <tr>
            <th>Title</th>
            <th>Ready in Minutes</th>
            <th>Image</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="recipe" items="${savedRecipes}">
            <tr>
                <td>${recipe.title}</td>
                <td>${recipe.readyInMinutes}</td>
                <td><img src="${recipe.imageUrl}" alt="${recipe.title}" width="100" /></td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<a href="/search-form">Search for Recipes</a>
</body>
</html>
