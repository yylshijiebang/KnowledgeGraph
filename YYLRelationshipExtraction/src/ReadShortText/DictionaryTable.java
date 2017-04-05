package ReadShortText;

import java.util.ArrayList;

import StaticConstant.ConstantParameter;

public class DictionaryTable {
    public void GetTable(String key,int i,int flag){
    	if(flag == 1){
    	   ConstantParameter.DisHash.put(key, i);
    	   //System.out.println(key+":"+i);
    	}else if(flag == 0){
    		ConstantParameter.POSHash.put(key,i);
    		//System.out.println(key+":"+i);
    	}else if(flag == 2){
			if(i >= 1){
				ArrayList<Float> wordVec = new ArrayList<>();
				int j;
				int WordNum = 0;
				char c;
			    //保存单词
			    String word = "";
			    String word_str = "";
			   
			    
			    for(j = 0; j < key.length(); j++){
			    	c = key.charAt(j);
			    	//如果该字符c是空格，则是一个单词
			    	if(c == ' '|| j == key.length()-1){
			    		if(j == key.length()-1){
			    			word += c;
			    		}
			    		
			    		if(WordNum == 0){
			    			word_str = word;
			    			//word_str = word_str.toLowerCase();//同一转化成小写字母进行处理
			    		}else{
			    			wordVec.add(Float.parseFloat(word));
			    		}
			    		WordNum ++;
			    		word = "";
			    	}else{
			    		word += c; 
			    	} 
			    }
			    ConstantParameter.WordVecHash.put(word_str, wordVec);
//			    if(ConstantParameter.WordVecHash.get(word_str).size() == 50){
//			    	ConstantParameter.test_num ++;
//			    }
			    //System.out.println("读取出词向量长度："+wordVec.size());
//			    if(i >= 9703){
//				    System.out.println("词向量");
//				    System.out.println(key);
//				    System.out.println(word_str);
//				    for(j = 0; j < wordVec.size();j++){
//				    	System.out.println(wordVec.get(j));
//				    }
//			    }
			    //System.exit(0);
			}
		}
    }
}
