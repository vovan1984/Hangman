<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <jsp:include page="resources/HangmanTitle.jsp"/>
<body>
    <jsp:include page="resources/HangmanHeader.jsp">
        <jsp:param name="HeaderTitle" value="Information about player"/>
    </jsp:include> 
    
    <section>
        <form action="HangmanWeb" method="post">
            First name: <input type="text" placeholder="FIRSTNAME" name="FirstName">
            Last name: <input type="text" placeholder="LASTNAME" name="LastName"><br/>
            <input type="submit" value="START!">
        </form>
    </section>
    
     <jsp:include page="resources/HangmanFooter.html"/>
</body>
</html>