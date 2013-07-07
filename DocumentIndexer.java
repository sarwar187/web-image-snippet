/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/

package textsearchengine;
import finalsearchengine.AllConstants;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import java.io.*;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

public class DocumentIndexer {
    String strElementObj,metaTag,strPathNameObj;
    File indexDir = new File(AllConstants.MyType.TEXTINDEXDIR.toString()),file;
    IndexWriter writer;
    Document doc;
    IndexWriterConfig indexWriterConfig;
    Integer indexDocument;
    void setValues(String strElementObj,String metaTag){
        this.strElementObj  = strElementObj;
        this.metaTag = metaTag;
    }
    DocumentIndexer(String strPathNameObj,int indexDocument) throws IOException{
        indexWriterConfig = new IndexWriterConfig(Version.LUCENE_33, new StandardAnalyzer(Version.LUCENE_33));
        indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        this.strPathNameObj = strPathNameObj;
        this.indexDocument = indexDocument;
        writer = new IndexWriter(new SimpleFSDirectory(indexDir),indexWriterConfig);
        doc = new Document();
    }
    public void indexFile() throws CorruptIndexException, IOException{
        //System.out.println(strElementObj + " " + metaTag);
        if(strElementObj == null){
            strElementObj = "is";
        }
        if(metaTag.equals("title")){
            Field field = new Field(metaTag,strElementObj, Field.Store.YES,Field.Index.ANALYZED);
            doc.add(field);
        }
        else{
            doc.add(new Field(metaTag,strElementObj, Field.Store.NO,Field.Index.ANALYZED,Field.TermVector.NO));
        }
    }
    public void indexComplete(int numberOfImages)throws IOException, Exception{
        //System.out.println(strPathNameObj);
        strPathNameObj = strPathNameObj.substring(7);
        System.out.println(strPathNameObj);
        doc.add(new Field("filename",strPathNameObj,Field.Store.YES,Field.Index.NOT_ANALYZED));
        doc.add(new Field("id",indexDocument.toString(),Field.Store.YES,Field.Index.NOT_ANALYZED));
        doc.add(new Field("numberOfImages",indexDocument.toString(),Field.Store.YES,Field.Index.NOT_ANALYZED));
        doc.setBoost((float)Math.log(numberOfImages));
        writer.addDocument(doc);
        writer.optimize();
        writer.close();
    }
    public void getDocument(){
        System.out.println("check doc" + doc.get("title").toString());
    }
}


