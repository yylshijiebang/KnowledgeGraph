package SentenceParser;

import java.util.ArrayList;

public class GetSentenceList {
     public void GetSentenceList(String Sentence){
    	//初始化一个字符串变量 ,保存处理掉乱码符号的
  	    String CleanSentence = "";
  	    ArrayList<String> SentenceList = new ArrayList<>();
  	   
  	    int i;
  	    char c;
  	    //保存单词
  	    String word = "";
  	    for(i = 0; i < Sentence.length(); i++){
  	    	c = Sentence.charAt(i);
  	    	//如果该字符c是空格，是句子的最后一个字符（最后一个字符是标点符号）,则是一个单词
  	    	if(c == ' '||i == Sentence.length()-1){
  	    		if(word.length() > 0){
  		    		if(c == ' '){
  		    			SentenceList.add(word);
  		    		}else if(i == Sentence.length()-1){
  		    			word += c;
  		    			SentenceList.add(word);
  		    		}
  	    		}
  	    		word = "";
  	    	}else{
  	    		word += c; 
  	    	} 
  	    }
  	    
  	    //System.out.println("CleanSentence:"+CleanSentence);
  	    //调用函数完成句子的解析和新实体发现
  	    GetNewEntity getNewEntity = new GetNewEntity();
  	    getNewEntity.FindNewEntity(SentenceList);
     }
}
