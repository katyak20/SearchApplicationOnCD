package lucene_search;
import  java.io.*;


import java.util.Vector;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.queryParser.*;


import org.eclipse.swt.widgets.Shell;


import application.WarningMessageBox;

public class SearchPDFFiles {
/*public String escapeHTML(String s) {
  s = s.replaceAll("&", "&amp;");
  s = s.replaceAll("<", "&lt;");
  s = s.replaceAll(">", "&gt;");
  s = s.replaceAll("\"", "&quot;");
  s = s.replaceAll("'", "&apos;");
  return s;
}*/

       private boolean error = false;                  //used to control flow for error messages
       private String indexDir;     //local copy of the configuration variable
       private  IndexSearcher searcher = null;          //the searcher used to open/search the index
       private Query query = null;                     //the Query created by the QueryParser
  //     private TopDocCollector collector = null;                       //the search results
    //   private int startindex = 0;                     //the first index displayed on this page
    //   private int maxpage    = 50;                    //the maximum items displayed on this page
       private String queryString = null;              //the query entered in the previous page
    //   private String startVal    = null;              //string version of startindex
 //      private String maxresults  = null;              //string version of maxpage
   //    private int thispage = 0;                       //used for the for/next either maxpage or
                                                //hits.length() - startindex - whichever is
       private TopDocs topDocs;
       private int numberOfHits;
     //  private ScoreDoc[] hits;
       private StringBuffer message;
       private Vector<SearchResults> myResults;
      // private WarningMessageBox messageBox;
       private Shell shell;
      
       
      // private static int hitsPerPage = 10;
       
    public static void main(String[] argv)
    {/*SearchPDFFiles pdfsearcher = new SearchPDFFiles();
        pdfsearcher.search( );
*/
    }
        
        public SearchPDFFiles(Shell s) {
        	shell = s;
        	myResults =new Vector<SearchResults>();  
        	
        }
        //less
public void search(String  param) {
	 message = new StringBuffer("SEARCH");
	 queryString = param;
	// this.getIndexDirectory();
	 SourceReader sr = new SourceReader();
	 indexDir = sr.ReadFile(); 
        try {
        	System.out.println("Otladka " + indexDir);
          searcher = new IndexSearcher(indexDir);      //create an indexSearcher for our page
                                                        //NOTE: this operation is slow for large
                                                        //indices (much slower than the search itself)
                                                        //so you might want to keep an IndexSearcher 
                                                        //open
                                                        
        } catch (IOException e) { 
        	message.append("\n" + "ERROR opening the Index - contact sysadmin!" + e);
        	System.out.println("ERROR opening the Index - contact sysadmin!" + e);     
            error=true;
        }
        

       if (error == false) {                                       //did we open the index?
              
    	   System.out.println("QueryString" +queryString);

                if (queryString.equals(""))
                {  //Shell child = new Shell(shell);
                	String msg = "No query specified"; 
                	WarningMessageBox messageBox = new WarningMessageBox(shell, msg);
               // messageBox.setMessage("No query specified.");
               
               // int rc =messageBox.open();
               // System.out.println(rc == SWT.OK);
               // child.open();
                        System.out.println("no query "+       //if you don't have a query then
                                                   "specified");      //you probably played on the 
                }     
                else {System.out.println("QueryString equals TRUE/FALSE "+ queryString.equals(""));
                	//query string so you get the 
                }
                                                                      //treatment

                Analyzer analyzer = new StandardAnalyzer();           //construct our usual analyzer
                try {
                        QueryParser qp = new QueryParser("contents", analyzer);
                        query = qp.parse(queryString); //parse the 
                } catch (ParseException e) {                          //query and construct the Query
                                                                      //object
                                                                      //if it's just "operator error"
                                                                      //send them a nice error HTML
                	    message.append("\n" + "Error while parsing query");
                        System.out.println("Error while parsing query");

                        error = true;                                 //don't bother with the rest of
                                                                      //the page
                }
        }


        if (error == false && searcher != null) {                     // if we've had no errors
                                                                      // searcher != null was to handle
                                                                      // a weird compilation bug 
                                             // default last element to maxpage
                try {
                	//collector = new TopDocCollector(hitsPerPage);
                	topDocs = searcher.search(query, 20);
                	numberOfHits=topDocs.totalHits;
                	 for (int i = 0; i < topDocs.totalHits; i++) {  
                		            ScoreDoc match = topDocs.scoreDocs[i];  
                		             Explanation explanation = searcher.explain(query, match.doc); //#1  
                		            System.out.println("----------");  
                		            Document doc = searcher.doc(match.doc);  
                		            System.out.println("Title" + doc.get("title")+ doc.get("path"));  
                		            System.out.println(explanation.toString()); //#2  
                		        }  

                }
                catch (IOException e)
                {   message.append("\n" + "Trouble opening file IO Ex" + e);
                	System.out.println("Trouble opening file IO Ex" + e);}
                if (numberOfHits == 0) {                             // if we got no results tell the user
                	message.append("\n" + " I'm sorry I couldn't find what you were looking for. ");
     System.out.println(" I'm sorry I couldn't find what you were looking for. ");
                error = true;                                        // don't bother with the rest of the
                                                                     // page
                }
        }

        if (error == false && searcher != null) {                   

              /*  if ((startindex + maxpage) > hits.length) {
                  //      thispage = numberOfHits - startindex;      // set the max index to maxpage or last
                }                                                   // actual search result whichever is less
*/
                for (int i = 0; i < numberOfHits; i++) {  // for each element
                     try {
                    	 ScoreDoc match = topDocs.scoreDocs[i];  
    		           //  Explanation explanation = searcher.explain(query, match.doc); //#1  
    		          // System.out.println("----------");  
    		            Document doc = searcher.doc(match.doc);                   //get the next document 
                        String doctitle = doc.get("title");            //get its title
                        String url = doc.get("path");      //get its path field
                        float score = match.score*100;
                        int intScore = Math.round(score);
                        String stringScore = Integer.toString(intScore);
                     
                        if (url != null && url.startsWith("../webapps/")) { // strip off ../webapps prefix if present
                                url = url.substring(10);
                        }
                        if ((doctitle == null) || doctitle.equals("")) //use the path if it has no title
                                doctitle = url;
                                                                       //then output!
                       
                        message.append("\n" + url );
                        message.append("\n" +"SUMMARY :"+ "\n" +doc.get("summary"));
                        System.out.println(url+doctitle + " score:  " + score);
                        
                        /***************/
                        SearchResults searchResult = new SearchResults();
                        searchResult.setName(doctitle);
                        searchResult.setAbsolutePath(url);
                        searchResult.setScore(stringScore);
                        searchResult.setId(doc.get("id"));
                        searchResult.setCreationDate(doc.get("CreationDate"));
                        searchResult.setModificationDate(doc.get("ModificationDate"));
                        searchResult.setSummary(doc.get("summary"));
                       
                        /***************/
                        if (doctitle != null)
                        	{myResults.addElement(searchResult);
                        }
                        
                     }
                     catch (CorruptIndexException e) { message.append("\n" + e);
                                                       System.out.println(e);}
                     catch (IOException e) { 
                         message.append("\n" + e);
                    	 System.out.println(e);}
                     
                }
          /*      if ( (startindex + maxpage) < hits.length()) {   //if there are more results...display 
                                                                   //the more link

                        String moreurl="results.jsp?query=" + 
                                       URLEncoder.encode(queryString) +  //construct the "more" link
                                       "&amp;maxresults=" + maxpage + 
                                       "&amp;startat=" + (startindex + maxpage);
%>
                <tr>
                        <td></td><td><a href="<%=moreurl%>">More Results>></a></td>
                </tr>
<%
                }
%>
                </table>
*/
       }                                            //then include our footer.
         if (searcher != null)
               try{ searcher.close();}
               catch (IOException e) {
                   message.append("\n" + "IOException on closing the searcher" + e);
            	   System.out.println("IOException on closing the searcher" + e);}
               
     
}

   public Vector<SearchResults> getSearchResults() {
	   return myResults;
   }
   public StringBuffer getMessage() {
	   return message;
   }
   private void getIndexDirectory() {
	   indexDir = new SourceReader().ReadFile(); 
   }

}
