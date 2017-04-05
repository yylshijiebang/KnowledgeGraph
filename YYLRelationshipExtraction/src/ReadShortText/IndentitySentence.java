package ReadShortText;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;


public class IndentitySentence {
	
	//清理掉标点符号的句子写入文件
	public static void WriteIntoFile(String glaucoma){
		    //写入文件
		    BufferedWriter bw = null;
		    FileWriter fileWriter=null;
			try{
				fileWriter = new FileWriter("D:/YYLSoftware/Program/YYLRelationshipExtraction/CleanSentence.txt",true);
				bw = new BufferedWriter(fileWriter);
			    bw.write(glaucoma);
			    bw.newLine();  
			
			}catch (IOException e) {
				e.printStackTrace();
			}finally{
			    try {
			        bw.close();
			    }catch (IOException e) {
			        e.printStackTrace();
			    }
			}
	}	

	public static void GetSentence(String Sentence){
		//初始化一个字符串变量 ,保存处理掉乱码符号的
	    String CleanSentence = "";
	    int i;
	    char c;
	    //保存单词
	    String word = "";
	    for(i = 0; i < Sentence.length(); i++){
	    	c = Sentence.charAt(i);
	    	//如果该字符c是空格，是句子的最后一个字符（最后一个字符是标点符号），或者是一个标点符号，则是一个单词
	    	if(c == ' '||i == Sentence.length()-1||StaticConstant.NonChiSplit.isCharSeperator(c)){
	    		if(word.length() > 0){
		    		if(c == ' '){
		    			CleanSentence += word + " ";//将该单词保存到句子中
		    		}else if(StaticConstant.NonChiSplit.isCharSeperator(c)){
		    			if(i == Sentence.length()-1)
		    			    CleanSentence += word;
		    			else
		    				CleanSentence += word+ " ";
		    		}else if(i == Sentence.length()-1){
		    			if(!StaticConstant.NonChiSplit.isCharSeperator(c)){
		    				word += c;
		    			}
		    			CleanSentence += word;
		    		}
	    		}
	    		word = "";
	    	}else{
	    		word += c; 
	    	} 
	    }
	    WriteIntoFile(CleanSentence);
	}
	
}
