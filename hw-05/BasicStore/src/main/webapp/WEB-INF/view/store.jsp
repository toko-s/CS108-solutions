<%--
  Created by IntelliJ IDEA.
  User: Drama
  Date: 6/28/2021
  Time: 2:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>store</title>
</head>
<body>
    <h1>Student Store</h1>
    <p>Items available:</p>
    <ul>
        <c:forEach var="item" items="${data}">
            <li>
                <a href="/product?id=${item.id}">${item.name}</a>
            </li>
        </c:forEach>
    </ul>
</body>
</html>
