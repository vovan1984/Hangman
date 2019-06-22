<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <jsp:include page="resources/HangmanTitle.jsp"/>
<body>
    <jsp:include page="resources/HangmanHeader.jsp">
        <jsp:param name="HeaderTitle" value="About me"/>
    </jsp:include> 
    
    <section>
        <p>Game was created by <strong>Vladimir Igumnov</strong>.<br>
        It was developed as a resume for Java Web Developer position.<p>
        
        <p>Source code for all variations of the game (console, GUI, WEB) can be
        found at the <a href="https://github.com/vovan1984/Hangman" target="_blank">GitHub</a>
        repository.</p>
    </section> 
           
    <jsp:include page="resources/HangmanFooter.html"/>
</body>
</html>