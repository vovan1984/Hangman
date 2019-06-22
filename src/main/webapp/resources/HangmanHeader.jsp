    <!-- Header to be shown on every page of the Hangman game -->
    <header>
        <h1 id="HeaderTitle"><%= request.getParameter("HeaderTitle")%></h1>
        <nav>
            <a href="index.jsp">Description</a>
            <a href="HangmanLeaderboard.jsp">Statistics</a>
            <a href="HangmanWeb">Game</a>
            <a rel="author" href="HangmanAbout.jsp">About</a>
        </nav> 
    </header>