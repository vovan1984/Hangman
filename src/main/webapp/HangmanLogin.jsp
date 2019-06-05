<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <jsp:include page="resources/HangmanTitle.jsp"/>   
    
    <body>
        <jsp:include page="resources/HangmanHeader.jsp">
            <jsp:param name="HeaderTitle" value="Information about player"/>
        </jsp:include> 
    
        <section id="login">
            <form action="HangmanWeb" method="post" onsubmit="return validate()">
        
                First name: <input type="text" placeholder="FIRSTNAME" id="FirstName" 
                              name="FirstName" autofocus>
                <label id="errfname" style="color:red; visibility:hidden;">
                    First name should not be blank!</label>
                <br><br>
            
                Last name: <input type="text" placeholder="LASTNAME" id="LastName" 
                              name="LastName">
                <label id="errlname" style="color:red; visibility:hidden;">
                    Last name should not be blank! </label>
                <br><br>
            
                <input type="submit" value="START!">
            </form>
        </section>
    
        <jsp:include page="resources/HangmanFooter.html"/>
    </body>

    <script type="application/javascript">
        function validate()
        {
            var firstname = document.getElementById("FirstName");
            var lastname = document.getElementById("LastName");
            
            if (firstname.value.trim() == "") 
            {
                firstname.classList.add("redborder");
                document.getElementById("errfname").style.visibility="visible";
            }
            else
            {
                firstname.classList.remove("redborder");
                document.getElementById("errfname").style.visibility="hidden";
            }
            
            if (lastname.value.trim() == "")
            {
                lastname.classList.add("redborder");
                document.getElementById("errlname").style.visibility="visible";
            }
            else
            {
                lastname.classList.remove("redborder");
                document.getElementById("errlname").style.visibility="hidden";
            }
            
            if (firstname.value.trim() != "" && lastname.value.trim() != "")
                return true;
            else
                return false;
        }
    </script>
</html>