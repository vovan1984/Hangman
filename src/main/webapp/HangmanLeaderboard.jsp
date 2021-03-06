<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
    <jsp:include page="HangmanHead.jsp"/>
    <body>
        <jsp:include page="HangmanHeader.jsp">
            <jsp:param name="HeaderTitle" value="Board of Leaders"/>
        </jsp:include> 
    
        <table>
            <thead>
                <tr>
                    <th>Rank</th>
                    <th>Player</th>
                    <th>Score</th>
                    <th>Date</th>
                </tr>
            </thead>
        
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
                {
                    out.println("<tbody>");
                    for (int i=0; i<records.length; i++)
                    {
                        out.println("<tr>\n<td>" + (i + 1) + ".</td>");
                        for (int j=0; j<3; j++)
                           out.println("<td>" + records[i][j] + "</td>");
                        out.println("</tr>");
                    }
                    out.println("</tbody>");
                }
            %>
        </table>
    
        <% 
            if (records == null) // no stats avaialble 
        %>
                <h3 id="NoDataMsg">Nobody played yet!</h3>
    
    
    
        <jsp:include page="HangmanFooter.html"/>
    </body>
</html>