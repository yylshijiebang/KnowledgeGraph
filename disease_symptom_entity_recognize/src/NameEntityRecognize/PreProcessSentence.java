package NameEntityRecognize;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PreProcessSentence {
    public void CleanSentence(String Sentence){
    	//初始化一个字符串变量 ,保存处理掉乱码符号的
	    String CleanSentence = "";
	    IndentitySentence indentitySentence = new IndentitySentence();
	    int i;
	    char c;
	    //保存单词
	    String word = "";
	    for(i = 0; i < Sentence.length(); i++){
	    	c = Sentence.charAt(i);
	    	//如果该字符c是空格，是句子的最后一个字符（最后一个字符是标点符号），或者是一个标点符号，则是一个单词
	    	if(c == ' '||i == Sentence.length()-1||NonChiSplit.isCharSeperator(c)){
	    		if(word.length() > 0){
		    		if(c == ' '){
		    			CleanSentence += word + " ";//将该单词保存到句子中
		    		}else if(NonChiSplit.isCharSeperator(c)){
		    			if(i == Sentence.length()-1)
		    			    CleanSentence += word;
		    			else
		    				CleanSentence += word+ " ";
		    		}else if(i == Sentence.length()-1){
		    			if(!NonChiSplit.isCharSeperator(c)){
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
	    //测试清理句子后的结果
	    WriteSentenceIntoFile(CleanSentence);
	    
	    indentitySentence.GetSentence(CleanSentence);
    }
    
     //将匹配到的单句子写入文件中
	  public static void WriteSentenceIntoFile(String glaucoma){
		    //写入文件
		    BufferedWriter bw = null;
		    FileWriter fileWriter=null;
			try{
			    fileWriter = new FileWriter("E:/北航文件/编程程序/disease_symptom_entity_recognize/CleanSentence.txt",true);
				bw = new BufferedWriter(fileWriter);
//				System.out.println(glaucoma);
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
}
