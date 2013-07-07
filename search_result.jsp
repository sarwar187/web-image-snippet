<%-- 
    Document   : index
    Created on : Jan 4, 2012, 9:56:03 PM
    Author     : User
--%>

<%@page import="java.io.BufferedWriter"%>
<%@page import="java.io.File"%>
<%@page import="java.io.FileWriter"%>
<%@page import="java.io.IOException"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.util.Comparator"%>
<%@page import="java.util.Collections"%>
<%@page import="imagesearcher.ImageSearchObject"%>
<%@page import="imagesearcher.SearchById"%>
<%@page import="java.lang.System"%>
<%@page import="finalsearchengine.SearchObject"%>
<%@page import="java.util.List"%>
<%@page import="finalsearchengine.Searcher"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
String query=request.getParameter("query");
String sscore=request.getParameter("sscore");
String sregion=request.getParameter("region");
String skurtosis=request.getParameter("kurtosis");
String sposition=request.getParameter("position");
String sarea = request.getParameter("area");
Double fscore;
if(sscore==null||sscore.length()==0)fscore = 0.55; 
else fscore = Double.parseDouble(sscore);
Double fregion;
if(sregion==null||sregion.length()==0)fregion = 0.55; 
else fregion = Double.parseDouble(sregion);
Double fkurtosis;
if(skurtosis==null||skurtosis.length()==0)fkurtosis = 0.55; 
else fkurtosis = Double.parseDouble(skurtosis);
Double fposition;
if(sposition==null||sposition.length()==0)fposition = 0.55; 
else fposition = Double.parseDouble(sposition);
Double farea;
if(sarea==null||sarea.length()==0)farea = 0.55; 
else farea = Double.parseDouble(sarea);
Double sum = fscore + fregion + fkurtosis + fposition + farea; 
fscore = (fscore/sum)*100;
fregion = (fregion/sum)*100;
fkurtosis = (fkurtosis/sum)*100;
fposition = (fposition/sum)*100;
farea = (farea/sum)*100;

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Result Page with Image Snippets</title>
    </head>
    <body>
        Your Query is <b> <%out.print(query);%> </b><br><br>
        <%--<h1>Hello World!</h1> --%>
        
        <table>
            
        <%  //String str = "print me";
            //always give the path from root. This way it almost always works.
       //String nameOfTextFile = "D:\\execution_time.txt";
       String nameOfTextFile = "/home/icekite/execution_time.txt";
         
       //String learnData = "D:\\learn_data.txt";
       String learnData = "/home/icekite/learn_data.txt";
       
       int maxDoc;
       try {   
            FileWriter fileWritter = new FileWriter(nameOfTextFile,true);
          BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            String result="";
            String eTime="";
            Searcher s = new Searcher(); 
            SearchById searchById = new SearchById();
            long timeInMillis = System.currentTimeMillis();
            List<SearchObject> list = s.Search(query); 
            out.print("<tr>Time taken to execute the text query");
            Double time = (System.currentTimeMillis()-timeInMillis)/1000d;
            eTime+=time.toString() + "\t";
            out.print(time + " second</tr><br><br>");
            if(list.size()==0){
                out.print("no result found");
                throw new IOException();
            }
            maxDoc = list.get(0).getMaxDoc();
            out.print("<tr>Total number of documents in the system ");
            out.print(maxDoc + "</tr>");
            timeInMillis = System.currentTimeMillis();
            int listSize=0;
            if(list.size()>10)listSize = 10;
            else listSize = list.size();
            for(int i=0;i<listSize;i++){
              out.print("<tr>"); 
              SearchObject searchObject = list.get(i);
              //out.print("result " + i + "<br><br>");
              List<ImageSearchObject> listImage = searchById.search(searchObject.getId(), query);
              Double a=5d,b=2d,c=3d;
              for(int j=0;j<listImage.size();j++){
                  Double region = Double.parseDouble(listImage.get(j).region);
                  Integer area = Integer.parseInt(listImage.get(j).area);
                  if(region<800 || area<3000){listImage.get(j).myscore=0d;continue;}
                  Double max = Double.parseDouble(listImage.get(j).max);
                  Double position = Double.parseDouble(listImage.get(j).position);
                  Float score = listImage.get(j).score*10;
                  Double kurtosis = Double.parseDouble(listImage.get(j).kurtosis);
                  Double areaNorm = Double.parseDouble(listImage.get(j).areaNorm);
                  Double finalScore = fscore*score + fregion*(region/max) + fposition*(1/(Math.log(position)+1)) + fkurtosis*(1-kurtosis) + farea*areaNorm;
                  //out.print("val  " + finalScore +"\n");
                  listImage.get(j).myscore = finalScore;
                  //out.print("val  " + listImage.get(j).myscore +"<br>");
              }
              Collections.sort(listImage, new Comparator(){
                     public int compare(Object o1, Object o2) {
                        ImageSearchObject im1 = (ImageSearchObject)o1;
                        ImageSearchObject im2 = (ImageSearchObject)o2;
                        return im2.myscore.compareTo(im1.myscore);
                    }
                });
              //out.print("val " + listImage.get(0)); 
              //out.print("<br><br>val " + listImage.get(0)+"<br><br>"); 
              //out.print("val " + listImage.get(0));    
              //out.print("<img src=" + listImage.get(0)+ "/><br><br>"); 
              out.print("<td width='600'>");  
              out.print("ID of the document " + searchObject.getId() + "<br>");
              out.print("Title <a href=" + "http://" + searchObject.getUrl()+">" +searchObject.getTitle() +"</a><br>"); 
              out.print("URL <a href=" + "http://" + searchObject.getUrl()+">" +searchObject.getUrl() +"</a>"); 
              out.print("</td>");     
              try{
              if(listImage.get(0).myscore != 0){    
              out.print("<td align='center'>");     
              Double width = (Double.parseDouble(listImage.get(0).width)/Double.parseDouble(listImage.get(0).height))*180d;
              out.print("<a href=" + listImage.get(0).fileUrl + ">" + "<img src='" + listImage.get(0).fileUrl+ "' width='" + String.valueOf(width.intValue()) + "'height='180'/>" + "</a>"); 
              //out.println(listImage.get(0).area);
              out.print("</td>");
                             }
                           else{
                                out.print("<td align='center'><br><br><br><br>No Image Available<br><br><br><br><br></td>"); 
                           }
              out.print("<td>");     
                //out.print("<a href=" + listImage.get(1).fileUrl + ">" + "<img src='" + listImage.get(1).fileUrl+ "' width='100' height='60'/>" + "</a>"); 
                out.print("<br>");
                //out.print("<a href=" + listImage.get(2).fileUrl + ">" + "<img src='" + listImage.get(2).fileUrl+ "' width='100' height='60'/>" + "</a>"); 
              out.print("</td>");     
           
              out.print("<td>");
                result = result + listImage.get(0).atext + "\t";
                result = result + listImage.get(0).file + "\t";
                result = result + listImage.get(0).fileUrl + "\t";
                result = result + listImage.get(0).max + "\t";
                result = result + listImage.get(0).position + "\t";
                result = result + listImage.get(0).region + "\t";
                result = result + listImage.get(0).stext + "\t";
                result = result + listImage.get(0).text + "\t";
                result = result + listImage.get(0).webId + "\t";
                result = result + listImage.get(0).myscore + "\t";
                
                //out.print("alt text " + listImage.get(0).atext + "<br>"); 
                //out.print("file " + listImage.get(0).file + "<br>"); 
                //out.print("fileUrl " + listImage.get(0).fileUrl + "<br>"); 
                //out.print("max " + listImage.get(0).max + "<br>"); 
                //out.print("position " + listImage.get(0).position + "<br>"); 
                //out.print("region " + listImage.get(0).region + "<br>"); 
                //out.print("source text " + listImage.get(0).stext + "<br>"); 
                //out.print("text " + listImage.get(0).text + "<br>"); 
                //out.print("web id " + listImage.get(0).webId + "<br>");
                //out.print("kurtosis " + listImage.get(0).kurtosis+ "<br>");    
                //out.print("snippet score " + listImage.get(0).myscore.doubleValue()+ "<br>");                                
              out.print("</td>");     
              
              }catch(Exception e){
              }
              //out.print("image link  <a href=" + listImage.get(0)+">" +listImage.get(0) +"</a><br>"); 
              //out.print("image link  <a href=" + listImage.get(1)+">" +listImage.get(1) +"</a><br>"); 
              
              
              out.print("</tr>");
            }%>
        </table>
            <br><br>Time taken to find the snippet&nbsp;<%
            time = (System.currentTimeMillis()-timeInMillis)/1000d;
            out.print(time + " second<br><br>");
            eTime+=time.toString();
            eTime= eTime + "\t" + maxDoc;
            bufferWritter.write(eTime.toString(),0,eTime.length());
            bufferWritter.newLine();
            bufferWritter.close();
            fileWritter = new FileWriter(learnData,true);
    	    bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write(result, 0, result.length());
            bufferWritter.newLine();
            bufferWritter.close();
       } catch(IOException e) {
           
                out.println("<br><br>Exception caught");
                out.println(e.getMessage());
       }
        
        %>
    </body>
</html>
