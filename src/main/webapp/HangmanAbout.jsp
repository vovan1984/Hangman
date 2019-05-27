<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>About author</title>
    <link rel="icon" type="image/png" href="resources/Logo.png">
</head>
<body>
    <jsp:include page="HangmanHeader.jsp">
        <jsp:param name="HeaderTitle" value="About me"/>
    </jsp:include> 
    
    <section>
        Game is written by Vladimir Igumnov. <br>
        It was created as a resume for Java Web Developer position.
    </section> 
           
    <jsp:include page="HangmanFooter.html"/>
</body>
</html>