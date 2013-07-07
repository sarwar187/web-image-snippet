/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package imagesearcher;

import finalsearchengine.AllConstants.MyType;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 *
 * @author sarwar
 */
public class SearchById {
    
    public void search(){
        try {
            IndexReader reader = IndexReader.open(FSDirectory.open(new File(MyType.IMAGEINDEXDIR.toString())));
            IndexSearcher is = new IndexSearcher(reader);
            for(int i=0;i<is.maxDoc();i++){
                Document doc = is.doc(i);
                System.out.println(doc.get("fileUrl"));
                System.out.println(doc.get("webId"));
            }
        } catch (IOException ex) {
            Logger.getLogger(SearchById.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    public List<ImageSearchObject> search(String id,String queryStr){
        List<ImageSearchObject> list = new ArrayList<ImageSearchObject>();
        try {
            //IndexReader reader = IndexReader.open(FSDirectory.open(new File(MyType.HOME + "Index")));
            IndexReader reader = IndexReader.open(FSDirectory.open(new File(MyType.IMAGEINDEXDIR.toString())));
            
            IndexSearcher is = new IndexSearcher(reader);
            
            BooleanQuery booleanQuery = new BooleanQuery();
            /*StringTokenizer st = new StringTokenizer(queryStr);
            while(st.hasMoreTokens()){
                Query query = new TermQuery(new Term("text",st.nextToken()));
                //query.setBoost(10000);
                booleanQuery.add(query, BooleanClause.Occur.SHOULD);
            }*/
            QueryParser parser = new QueryParser(org.apache.lucene.util.Version.LUCENE_31,"webId",new StandardAnalyzer(Version.LUCENE_31));
            Query query = parser.parse(id);
            booleanQuery.add(new BooleanClause(query, BooleanClause.Occur.MUST));
            
            parser = new QueryParser(org.apache.lucene.util.Version.LUCENE_31,"stext",new StandardAnalyzer(Version.LUCENE_31));
            parser.setDefaultOperator(QueryParser.Operator.OR);
            query = parser.parse(queryStr);
            //Double stextd = Math.rrandom();
            
            query.setBoost(6f);
            booleanQuery.add(new BooleanClause(query, BooleanClause.Occur.SHOULD));
            parser = new QueryParser(org.apache.lucene.util.Version.LUCENE_31,"atext",new StandardAnalyzer(Version.LUCENE_31));
            parser.setDefaultOperator(QueryParser.Operator.OR);
            query = parser.parse(queryStr);
            query.setBoost(0.2f);
            booleanQuery.add(new BooleanClause(query, BooleanClause.Occur.SHOULD));
            parser = new QueryParser(org.apache.lucene.util.Version.LUCENE_31,"file",new StandardAnalyzer(Version.LUCENE_31));
            parser.setDefaultOperator(QueryParser.Operator.OR);
            query = parser.parse(queryStr);
            query.setBoost(0.0f);
            booleanQuery.add(new BooleanClause(query, BooleanClause.Occur.SHOULD));
            //booleanQuery.add(new BooleanClause(query, BooleanClause.Occur.SHOULD));
            /*parser = new QueryParser(org.apache.lucene.util.Version.LUCENE_31,"alt",new StandardAnalyzer(Version.LUCENE_31));
            query = parser.parse(queryStr);
            booleanQuery.add(new BooleanClause(query, BooleanClause.Occur.SHOULD));
            */
            //System.out.println(booleanQuery.toString());
            TopDocs h  = is.search(booleanQuery,40);
            // is.explain(query, doc)
            for(ScoreDoc scoreDoc : h.scoreDocs) {
                //SearchObject searchObject = new SearchObject();
                //System.out.println("nothing");
                ImageSearchObject im = new ImageSearchObject();
                Document doc = is.doc(scoreDoc.doc);
                //System.out.println(scoreDoc.score);
                //System.out.println(scoreDoc.shardIndex);
                im.webId = doc.get("webId");
                im.atext = doc.get("atext");
                im.file = doc.get("file");
                im.fileUrl = doc.get("fileUrl");
                im.max = doc.get("max");
                im.position = doc.get("position");
                im.region = doc.get("region");
                im.stext = doc.get("stext");
                im.text = doc.get("text");
                im.kurtosis = doc.get("kurtosis");
                im.score = scoreDoc.score;
                im.height = doc.get("height");
                im.width = doc.get("width");
                im.area = doc.get("area");
                im.areaNorm = doc.get("areanorm");
                //im.myscore = (double)is.maxDoc();
                //System.out.println(im.score); 
                list.add(im);
                //System.out.println(doc.get("webId"));
                //searchObject.title = doc.get("title");
                //searchObject.url = doc.get("filename");
                //list.add(searchObject);
                //System.out.println(doc.get("title"));       
            }
            
            
            //Document d = is.doc(0);
            //System.out.printlstn(d.get("fileUrl"));
        }catch(Exception e){
            System.out.println(e);
        }
        return list;
    }
}
