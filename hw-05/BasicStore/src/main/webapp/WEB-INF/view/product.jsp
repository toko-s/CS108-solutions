<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Drama
  Date: 6/28/2021
  Time: 2:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>product</title>
</head>
<body>
    <h1>${item.name}</h1>
    <img src='/store-images/${item.image}' alt="${item.image}">
    <form method="post" action="/cart?id=${item.id}">
        ${item.price}$
        <button type="submit">Add to Cart</button>
    </form>
</body>
</html>
