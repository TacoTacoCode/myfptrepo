<%-- 
    Document   : create
    Created on : Oct 28, 2021, 9:07:26 AM
    Author     : Taco
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
    <body>
        <h1>Add Cake with Dom</h1>
        <form action="CreateCakeController" method="POST">
            ID: <input type="text" name="txtID"><br/>
            <c:if test="${requestScope.ERROR != null}">
                <c:out value="${requestScope.ERROR}"/><br/>
            </c:if>
            
            Is Available: <select name="isAvailable">
                <option value="0">False</option>
                <option value="1">True</option>
            </select><br/>
            Cooking Time: <input type="number" name="txtTime" min="0" value="0"/><br/>
            Name: <input type="text" name="txtName"><br/>
            Description: <input type="text" name="txtDes"><br/>
            Quantity: <input type="number" name="txtQuantity" min="0" value="0"/><br/>
            Price: <input type="number" name="txtPrice" min="0"/><br/>
            Chef:<br/>
            Gender: <select name="cboSex">
                <option value="0">Female</option>
                <option value="1">Male</option>
            </select><br/>
            Firstname: <input type="text" name="txtFirst"/><br/>
            Lastname: <input type="text" name="txtLast"/><br/>
            <input type="submit" value="Create"/>
        </form>
    </body>
</body>
</html>
