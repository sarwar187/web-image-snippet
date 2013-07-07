<%-- 
    Document   : index
    Created on : Jan 4, 2012, 9:56:03 PM
    Author     : User
--%>

<%@page import="finalsearchengine.SearchObject"%>
<%@page import="java.util.List"%>
<%@page import="finalsearchengine.Searcher"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html> 
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Engine Visualization Research By Sheikh Muhammad Sarwar and Dr. Md. Mustafizur Rahman</title>
    </head>
    <body>
        <h1 align="center">This search engine is on development as a part of the research work done by Sheikh Muhammad Sarwar</h1>
        <h3>Master's Student, Department of Computer Science and Engineering (January, 2012)</h3>
        <h3>University of Dhaka</h3>
        <form name="frm" method="get" action="search_result.jsp">        Query &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="query"><br>
        Score Importance&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="score" value="20"><br>
        Kurtosis Importance&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="kurtosis" value="20"><br>
        Region Importance&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="region" value="20"><br>
        Position Importance&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="position" value="20"><br>
        Area Importance&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="area" value="20"><br><br>
        <input type="submit" name="submit" value="Submit">
        </form>
        <br>

         <font color="green" size="5px">The search engine provides image snippets and thus aids in search result visualization and re-finding. Images based on <b>quality</b>, <b>position in the web page</b>, <b>similarity of surrounding text with query</b> and <b>area taken in the web page</b> are the parameters for choosing image snippets i.e. image based summaries. The relative importance of the parameters can be set but putting importance factors in the text boxes and it must be a number. Relative importance will be calculated in percentage (so you can put any value; just assign higher value for the parameter you prefer). For better quality images kurtosis and region values should be high. For choosing a dominant image in the web page area and position parameters should be given high value. Finally, for finding snippets for which surrounding texts are similar to users search intention the score importance value should be higher. This is ultimately a machine learning problem and we are trying to build a learning framework. We are trying to improve the system so that it takes feedback from user and adjusts the parameter values itself. As we do not have a larger index please restrict your queries to sports and computer related subjects to obtain better results. :) </font><br><br>

<h3>Example queries</h3>
 <font color="#610B38" size="4px">Query: Wembly Stadium &nbsp;&nbsp;&nbsp;&nbsp; Score Importance: 20 &nbsp;&nbsp;&nbsp;&nbsp; Kurtosis Importance: 20 &nbsp;&nbsp;&nbsp;&nbsp; Region Importance: 20 &nbsp;&nbsp;&nbsp;&nbsp; Position Importance:  20 &nbsp;&nbsp;&nbsp;&nbsp; Area Importance: 20
<br>
Query: Wembly Stadium &nbsp;&nbsp;&nbsp;&nbsp; Score Importance: 60 &nbsp;&nbsp;&nbsp;&nbsp; Kurtosis Importance: 10 &nbsp;&nbsp;&nbsp;&nbsp; Region Importance: 10 &nbsp;&nbsp;&nbsp;&nbsp; Position Importance:  10 &nbsp;&nbsp;&nbsp;&nbsp; Area Importance: 10
<br><br>
<b>Please note that, whenever equal importance is given to the parameters, the image of wembley staidum do not come up in the search result page as a snippet of document with ID 685. But, when importance factor 60 is assigned to Score Importance, the image of wembley stadium comes up as a snippet of the document with ID 685. Play with other queries but restrict them to sports and computer related area for obtaining better results.</b>
<br><br>
Query: Games and Sports &nbsp;&nbsp;&nbsp;&nbsp; Score Importance: 100 &nbsp;&nbsp;&nbsp;&nbsp; Kurtosis Importance: 0 &nbsp;&nbsp;&nbsp;&nbsp; Region Importance: 0 &nbsp;&nbsp;&nbsp;&nbsp; Position Importance: 0 &nbsp;&nbsp;&nbsp;&nbsp; Area Importance: 0 <br>

Query: Games and Sports &nbsp;&nbsp;&nbsp;&nbsp; Score Importance: 20 &nbsp;&nbsp;&nbsp;&nbsp; Kurtosis Importance: 40 &nbsp;&nbsp;&nbsp;&nbsp; Region Importance: 40 &nbsp;&nbsp;&nbsp;&nbsp; Position Importance: 0 &nbsp;&nbsp;&nbsp;&nbsp; Area Importance: 0<br>

Query: Games and Sports &nbsp;&nbsp;&nbsp;&nbsp; Score Importance: 20 &nbsp;&nbsp;&nbsp;&nbsp; Kurtosis Importance: 0 &nbsp;&nbsp;&nbsp;&nbsp; Region Importance: 0 &nbsp;&nbsp;&nbsp;&nbsp; Position Importance:  40 &nbsp;&nbsp;&nbsp;&nbsp; Area Importance: 40  </font>
<br><br>

<font color="blue" size="5px"> You can also see <a href="21683-79751-1-PB.pdf"> the paper </a> for a detailed description.         
<br>You can contact me at sheikh.sarwar at ulab dot edu dot bd<br> Find details about me at <a href="http://sarwar.bgdomain.com">this link.</a> </font>
 
            <%--<h1>Hello World!</h1> --%>
    </body>
</html>
