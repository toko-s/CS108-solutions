<%@ page import="manager.UserCart" %>
<%@ page import="manager.Item" %><%--
  Created by IntelliJ IDEA.
  User: Drama
  Date: 6/28/2021
  Time: 2:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>cart</title>
</head>
<body>
    <h1>Shopping Cart</h1>
    <form method="get" action="/cart">
    <ul>
        <c:forEach var="item" items='<%=((UserCart) session.getAttribute("user-cart")).getItems()%>'>
            <li>
                <input type="text" name="${item.name}" value='<%=((UserCart) session.getAttribute("user-cart")).getValue((Item) pageContext.getAttribute("item"))%>'/>
                ${item.name}, ${item.price}$
            </li>
        </c:forEach>
    </ul>
    Total: $<%=((UserCart) session.getAttribute("user-cart")).getTotalPrice()%>
        <button type="submit">Update Cart</button>
    </form>
    <a href="/">Continue Shopping</a>
</body>
</html>
