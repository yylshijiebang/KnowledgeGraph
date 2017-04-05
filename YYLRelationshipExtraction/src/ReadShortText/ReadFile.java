package ReadShortText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import FeatureExtract.ExtractWordFeature;

public class ReadFile {
	public void ReadSentence(String FileName,int flag){
		 try{
		     //Scanner reader = new Scanner(new File(FileName),"UTF-8");
		     int i = 0;
		     Reader reader = null;
	         //读取txt文件中的内容
	         System.out.println("以字符为单位读取文件内容，一次读一个字节：");  
	         
	         // IndentitySentence indentitySentence = new IndentitySentence();
	         DictionaryTable dictionaryTable = new DictionaryTable();
	         //抽取句子特征向量
	         ExtractWordFeature extractWordFeature = new ExtractWordFeature();
	         // 一次读一个字符  
	         reader = new InputStreamReader(new FileInputStream(FileName));  
	         String Sentence = ""; 
	         int tempchar = -1;  
	         while ((tempchar = reader.read()) != -1) {  
	             // 对于windows下，\r\n这两个字符在一起时，表示一个换行。  
	             if((char) tempchar == '\n'){
	            	 Sentence = Sentence.substring(0,Sentence.length()-1);
	            	 //System.out.println(Sentence);
	            	 if(flag == 3){//读取到sentence_lable句子
	            		 //2017.3.24修改去掉一半没有关系的实体句子
//	            		 if(i == 12314)
//	            			 break;
	            		 //2017.3.24修改结束
	            		 extractWordFeature.GetWordVector(Sentence);
	            	 }else{
	            	     dictionaryTable.GetTable(Sentence,i,flag);
	            	 }
	            	 i++;
	            	 //indentitySentence.GetSentence(Sentence);
	            	 Sentence = "";
	              }else{
	                  Sentence = Sentence + ((char) tempchar);
	              } 
	         }  
	         reader.close();
	         System.out.println(i); 
	        // System.exit(0);
	
		 }catch(Exception e){
			 e.printStackTrace();
		 }
	}
}
