<%-- 
    Document   : index
    Created on : Feb 21, 2021, 7:59:51 PM
    Author     : RehMan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>BMI calculation</title>
    </head>
    <body>
        <form method="GET" action="NewServlet">
            Name : <input type="text" name="u"/><br/>
            Weight : <input type="text" name="p"/><br/>
            Height : <input type="text" name="s"/><br/>
            <input type="submit" value="Calculate BMI"/>



        </form>
    </body>
</html>
