<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<html>
<head>
    <title>Register</title>
</head>
<body>

<h1>${head}</h1>
<p>${status}</p>

<form method="POST">
    <%--@declare id="username"--%>
    <%--@declare id="password"--%>
    <label for="username">User Name: </label>
    <input type="text" name="username"/><br/>
    <label for="password">Password: </label>
    <input type="password" name="password" /><br/>
    <button type="submit">Register</button>
</form>

</body>
</html>
