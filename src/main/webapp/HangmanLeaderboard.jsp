<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <jsp:include page="resources/HangmanTitle.jsp"/>
<body>
    <jsp:include page="resources/HangmanHeader.jsp">
        <jsp:param name="HeaderTitle" value="Board of Leaders"/>
    </jsp:include> 
    
    <table>
        <tr>
            <th>Rank</th>
            <th>Player</th>
            <th>Score</th><th>Date</th>
        </tr>
        <% 
            hangman.HangmanStats storage;
        
            if (session.getAttribute("HangmanStats") == null)
            {
                storage = new hangman.utils.HangmanFileStats();
                session.setAttribute("HangmanStats", storage);
            }
            else 
                storage = (hangman.HangmanStats)session.getAttribute("HangmanStats");
        
            String[][] records = storage.getResults();
            
            if (records!=null)
                for (int i=0; i<records.length; i++)
                {
                    out.println("<tr>\n<td>" + (i + 1) + ".</td>");
                    for (int j=0; j<3; j++)
                       out.println("<td>" + records[i][j] + "</td>");
                    out.println("</tr>");
                }
        %>
    </table>
    
    <jsp:include page="resources/HangmanFooter.html"/>
</body>
</html>