<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Player Details</title>
    <link rel="icon" type="image/png" href="resources/Logo.png">
</head>
<body>
    <jsp:include page="HangmanHeader.jsp">
        <jsp:param name="HeaderTitle" value="Information about player"/>
    </jsp:include> 
    
    <section>
        <form action="HangmanWeb" method="post">
            First name: <input type="text" placeholder="FIRSTNAME" name="FirstName">
            Last name: <input type="text" placeholder="LASTNAME" name="LastName"><br/>
            <input type="submit" value="START!">
        </form>
    </section>
    
     <jsp:include page="HangmanFooter.html"/>
</body>
</html>