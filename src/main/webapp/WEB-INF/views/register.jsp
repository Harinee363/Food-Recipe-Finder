<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head><title>Register</title></head>
<body>
    <h2>Register</h2>
    <form:form action="/users/register" modelAttribute="user" method="post">
        Email: <form:input path="email" /><br/>
        Password: <form:password path="password" /><br/>
        <button type="submit">Register</button>
    </form:form>
</body>
</html>
