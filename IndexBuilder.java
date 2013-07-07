/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package imageindexer;
import finalsearchengine.AllConstants.*;
import imageinfogenerator.ImageInfo;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 *
 * @author sarwar
 */
public class IndexBuilder {
    
    private String[] testFiles; //= new String[]{"britney.JPG", "actress.JPG","misc.JPG", "obama.JPG", "advertisement.JPG"};
    private String testFilesPath = MyType.HOME.toString();
    private String indexPath = MyType.IMAGEINDEXDIR.toString();
    private Integer webId;
    public IndexBuilder(Integer webId){
        //DirectoryBrowser directoryBrowser = new DirectoryBrowser(testFilesPath);
        //testFiles = directoryBrowser.getAllFiles();
        this.webId = webId;
    }
    public void testCreateIndex(List<ImageInfo> imageInfoList) throws IOException {
        // Create an appropriate DocumentBuilder
        //DocumentBuilder builder = DocumentBuilderFactory.getCEDDDocumentBuilder();
        // That's the way it is done with Lucene 3.0 - supported with LIRe v0.8
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_33, new StandardAnalyzer(Version.LUCENE_33));
        indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        IndexWriter iw = new IndexWriter(FSDirectory.open(new File(indexPath)),indexWriterConfig);
        
        //IndexWriter iw = new IndexWriter(FSDirectory.open(new File(indexPath)), new SimpleAnalyzer(), true, IndexWriter.MaxFieldLength.UNLIMITED);
        //Integer val=0;
        Integer maxRegion=0;
        Double maxKurtosis=0d;
        Integer maxArea=0;
        for(ImageInfo identifier : imageInfoList){
            if(identifier.getRegionNumber()>maxRegion)
                maxRegion = identifier.getRegionNumber();
            if(identifier.getKurtosis()>maxKurtosis)
                maxKurtosis = identifier.getKurtosis();
            if(identifier.getArea()>maxArea)
                maxArea = identifier.getArea();
        }
        for(ImageInfo identifier : imageInfoList){
            //Build the Lucene Documents
            
            if(identifier.getFileUrl().contains(".jpg")||identifier.getFileUrl().contains(".JPG")||identifier.getFileUrl().contains(".jpeg")||identifier.getFileUrl().contains(".png")||identifier.getFileUrl().contains(".gif"))
            {
                //File f = new File(testFilesPath + identifier);
                //System.out.println(testFilesPath + identifier);
                //BufferedImage bufferedImage = identifier.getBufferedImage();
                //ColorModel c = bufferedImage.getColorModel();
                //ColorSpace cs = c.getColorSpace();
                //if(cs.isCS_sRGB()==false)continue;
                /*BufferedImage firstHalf = bufferedImage.getSubimage(0,0,bufferedImage.getWidth()/2,bufferedImage.getHeight());
                BufferedImage secondHalf = bufferedImage.getSubimage((bufferedImage.getWidth()/2),0,bufferedImage.getWidth()/2,bufferedImage.getHeight());
                ImageConversion imageConversionFirst = new ImageConversion(firstHalf);
                imageConversionFirst.createIntensityArray();
                Integer val1 = imageConversionFirst.findRegion(firstHalf);
                System.out.println(val1);
                ImageConversion imageConversionSecond = new ImageConversion(secondHalf);
                imageConversionSecond.createIntensityArray();
                Integer val2 = imageConversionSecond.findRegion(secondHalf);
                System.out.println(val2);
                System.out.println(val1+val2);
                Document doc = builder.createDocument(new FileInputStream(testFilesPath + identifier), identifier);
                doc.add(new Field("id",val.toString(),Field.Store.YES,Field.Index.NOT_ANALYZED));
                doc.add(new Field("first",val1.toString(),Field.Store.YES,Field.Index.NOT_ANALYZED));
                doc.add(new Field("second",val2.toString(),Field.Store.YES,Field.Index.NOT_ANALYZED));
                doc.add(new Field("shade",val2.toString(),Field.Store.YES,Field.Index.NOT_ANALYZED));
                val2=val1+val2;
                doc.add(new Field("total",val2.toString(),Field.Store.YES,Field.Index.NOT_ANALYZED));
                System.out.println(doc.get("id"));
                */
                //bufferedImage = bufferedImage.getSubimage(0,0,bufferedImage.getWidth(),bufferedImage.getHeight());
                try {
                    //Document doc = builder.createDocument(bufferedImage, identifier.getFileUrl());
                    Document doc = new Document();
                    //ImageConversion imageConversion = new ImageConversion(bufferedImage);
                    //imageConversion.createIntensityArray();
                    //Integer total = imageConversion.findRegion(bufferedImage);
                    //Long shade = imageConversion.getNumberOfShades();
                    //doc.add(new Field("id",val.toString(),Field.Store.YES,Field.Index.NOT_ANALYZED));
                    //doc.add(new Field("total",total.toString(),Field.Store.YES,Field.Index.NOT_ANALYZED));
                    //doc.add(new Field("shade",shade.toString(),Field.Store.YES,Field.Index.NOT_ANALYZED));
                    //System.out.println(doc.get("id"));
                    //if(total>3000)
                    //doc.add(new Field("id", val.toString(), Field.Store.YES, Field.Index.NOT_ANALYZED));
                    Integer reg = identifier.getRegionNumber();
                    //if(reg<3000)continue;
                    //reg=reg/100;
                    //if(reg<5000)continue;
                    String str="";
                    str = identifier.getFileUrl();
                    //System.out.println(str);
                    str = str.substring(str.lastIndexOf('/')+1);
                    //System.out.println(str);
                    
                    Field text = new Field("text", identifier.getSourText()+identifier.getAltText(), Field.Store.YES, Field.Index.ANALYZED);
                    //text.setBoost(1000);
                    Field f = null;
                    Integer serial = identifier.serial;
                    doc.add(text);
                    //identifier.get
                    Double areanorm  = identifier.getArea()/(double)maxArea;
                    doc.add(new Field("webId", webId.toString(), Field.Store.YES, Field.Index.NOT_ANALYZED));
                    doc.add(new Field("fileUrl", identifier.getFileUrl(), Field.Store.YES, Field.Index.NOT_ANALYZED));
                    doc.add(new Field("width", String.valueOf(identifier.getWidth()), Field.Store.YES, Field.Index.NOT_ANALYZED));
                    doc.add(new Field("height", String.valueOf(identifier.getHeight()), Field.Store.YES, Field.Index.NOT_ANALYZED));
                    doc.add(new Field("area", String.valueOf(identifier.getArea()), Field.Store.YES, Field.Index.NOT_ANALYZED));
                    doc.add(new Field("areanorm", areanorm.toString(), Field.Store.YES, Field.Index.NOT_ANALYZED));
                    doc.add(new Field("region", reg.toString(), Field.Store.YES, Field.Index.NOT_ANALYZED));
                    doc.add(new Field("file", str, Field.Store.YES, Field.Index.NOT_ANALYZED));
                    doc.add(new Field("max", maxRegion.toString(), Field.Store.YES, Field.Index.NOT_ANALYZED));
                    doc.add(new Field("position", serial.toString(), Field.Store.YES, Field.Index.NOT_ANALYZED));
                    Double kurtosis = identifier.getKurtosis()/maxKurtosis;
                    doc.add(new Field("kurtosis", kurtosis.toString(), Field.Store.YES, Field.Index.NOT_ANALYZED));
                    if(identifier.getSourText()==null){
                        f = new Field("stext","no text", Field.Store.YES, Field.Index.ANALYZED);
                        f.setBoost(0);doc.add(f);
                    }else{
                        doc.add(new Field("stext", identifier.getSourText(), Field.Store.YES, Field.Index.ANALYZED));
                    }
                    if(identifier.getAltText()==null){
                        f = new Field("atext","no text", Field.Store.YES, Field.Index.ANALYZED);
                        f.setBoost(0);doc.add(f);
                    }else{
                        doc.add(new Field("atext", identifier.getAltText(), Field.Store.YES, Field.Index.ANALYZED));
                    }
                    //System.out.println(reg + "  " + maxRegion + "  " + reg/max);
                    //Float score = (float)(10*(reg/maxRegion) + 10*(1/identifier.serial));
                    //score.
                    //doc.setBoost(score*10);
                    //if(reg<3000)
                    //System.out.println((((reg*100000)/maxRegion)/identifier.serial));
                    float val = (0.5f*(reg/maxRegion)) + (0.5f*(1/identifier.serial));
                    //System.out.println("val " + val);
                    //doc.setBoost((0.5f*(reg/maxRegion)) + (0.5f*(1/identifier.serial)));
                    //doc.setBoost(reg);
                    //System.out.println("id all " + doc.get("id"));
                    //System.out.println(identifier.getFileUrl());
                    //System.out.println(identifier.getSourText());
                    
                    //System.out.println("total number of regions : " + total);

                    //System.out.println("total intensity values : " + shade);

                    //System.out.println("shade vector : " + shade);
                    //System.out.println("maximum shade : " + imageConversion.getmaxShade());
                    //System.out.println("average intensity : " + imageConversion.getAverageIntensity());
                    //val++;
                    iw.addDocument(doc);
                } catch(Exception ex){
                    ex.printStackTrace();
                }
                
            }
        }
        iw.optimize();
        iw.close();
    }
}
