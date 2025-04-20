<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Login</title>
</head>
<body>
    <h2>Login</h2>
    
    <!-- Display error message if login fails -->
    <c:if test="${param.error != null}">
        <p style="color: red;">Invalid email or password</p>
    </c:if>
    
    <!-- Display logout message -->
    <c:if test="${param.logout != null}">
        <p style="color: green;">You have been logged out successfully</p>
    </c:if>

    <form action="/users/login" method="post">
    <input type="text" name="email" />
    <input type="password" name="password" />
    <button type="submit">Login</button>
</form>
	<a href="/users/register">Dont have an account!! Register</a>

</body>
</html>
