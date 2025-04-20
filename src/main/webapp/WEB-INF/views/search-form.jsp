<!-- searchForm.jsp -->
<!DOCTYPE html>
<html>
<head>
    <title>Recipe Search</title>
</head>
<body>
    <h1>Search for Recipes</h1>

    <!-- Form to submit an ingredient -->
    <form action="/recipe/search" method="get">
        <input type="text" name="ingredient" placeholder="Enter ingredient" required />
        <button type="submit">Search</button>
    </form>
</body>
</html>
