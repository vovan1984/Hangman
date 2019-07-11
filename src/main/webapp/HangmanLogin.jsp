<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <jsp:include page="HangmanHead.jsp"/>   
    
    <body>
        <jsp:include page="HangmanHeader.jsp">
            <jsp:param name="HeaderTitle" value="Information about player"/>
        </jsp:include> 
    
        <section id="login">
            <form action="HangmanWeb" method="post" onsubmit="return validate()">
        
                First name: <input type="text" placeholder="FIRSTNAME" id="FirstName" 
                              name="FirstName" autofocus>
                <br><br>
            
                Last name: <input type="text" placeholder="LASTNAME" id="LastName" 
                              name="LastName">
                <br><br>
            
                <input type="submit" value="START!">
            </form>
        </section>
    
        <jsp:include page="HangmanFooter.html"/>
    </body>

    <script type="application/javascript">
    
        function addErrAfter(node, labelId, hiddenInd)
        {
            node.classList.add("redborder");
            
            if (document.getElementById(labelId) == null)
            {
        	    var label = document.createElement("label");      	    
        	    label.setAttribute("id", labelId);
        	    node.parentNode.insertBefore(label, node.nextSibling);
            }
           
            document.getElementById(labelId).style.visibility = hiddenInd;
            
            /* remove red border if error should be hidden */ 
            if (hiddenInd == "hidden")
            	node.classList.remove("redborder");
        }
       
        function validate()
        {
            var firstname = document.getElementById("FirstName");
            var lastname = document.getElementById("LastName");
            
            var firstNameWrong = (firstname.value.trim() == "");
            var lastNameWrong = (lastname.value.trim() == "");
            
            /* If both names are wrong - display error message for both */
            if (firstNameWrong && lastNameWrong) 
            {
                addErrAfter(firstname, "errfname", "visible");
                addErrAfter(lastname, "errlname", "visible");
            }
            /* If only first name is wrong, then message for last name should
               not be displayed, but should be present for alignment purposes */
            else  if (firstNameWrong)
            {
                addErrAfter(firstname, "errfname", "visible");
                addErrAfter(lastname, "errlname", "hidden");           	
            }
            else if (lastNameWrong)
            {
                addErrAfter(firstname, "errfname", "hidden");
                addErrAfter(lastname, "errlname", "visible");             	
            }
            
            // If both names are valid => validation passed           
            return ! (firstNameWrong || lastNameWrong);
        }
    </script>
</html>