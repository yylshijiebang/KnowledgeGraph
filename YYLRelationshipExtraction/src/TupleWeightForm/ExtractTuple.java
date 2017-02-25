package TupleWeightForm;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.sql.SQLNonTransientConnectionException;
import java.util.ArrayList;
import java.util.HashMap;

import org.omg.CosNaming.NamingContextExtPackage.AddressHelper;

import NameEntityRecognize.TrieDictionary;
import NameEntityRecognize.WordNetHelper;
import StaticConstant.AdjustParameter;
import StaticConstant.NonChiSplit;


public class ExtractTuple {
	  //static ArrayList<ArrayList> PatternTuples = new ArrayList<>();
	  String entity_start = "[entity_start]";
	  String entity_end = "[entity_end]";
	  
	  
	  //将匹配到的单词写入文件中
	  public void WriteIntoFile(String glaucoma){
		    //写入文件
		    BufferedWriter bw = null;
		    FileWriter fileWriter=null;
			try{
				fileWriter = new FileWriter("E:/北航文件/编程程序/EnglishSegTest/SentenceTuple.txt",true);
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
	  
	  //抽取元组关系
	  public  ArrayList<HashMap<String, Integer>> ExtractTuples(String Sentence,String Entity, TuplesStore tuplesStore){
		  
			String FirstEntity = "",SecondEntity = "";
			//将实体对的第一个和第二个实体分别保存
		    FirstEntity = Entity.substring(0,Entity.indexOf(','));
		    SecondEntity = Entity.substring(Entity.indexOf(',')+1,Entity.length());
		    
//		    System.out.println(FirstEntity);
//		    System.out.println(SecondEntity);
		    
			//调用TrieDictionary建立字典树
//			NameEntityRecognize.TrieDictionary dict = null;
//	        dict = TrieDictionary.getInstance(Entity);
//	        
//	        //按照字典树保存的实体最长匹配单实体
//			IdentityTuple  mmsegger = new IdentityTuple();
//			int locate[] = new int [3];
//	        locate = mmsegger.seg(Sentence,dict,Entity);
//				
//	        //第一个第二个实体分别在句子中的位置
		    //2017/2/8使用全匹配的方法，识别实体，得到元组
		    int FirstEntityLocateStart = -1, FirstEntityLocateEnd = -1, SecondEntityLocateStart = -1, SecondEntityLocateEnd = -1;//实体在语句中的位置
		    FirstEntityLocateStart = Sentence.indexOf(entity_start);
		    FirstEntityLocateEnd = Sentence.indexOf(entity_end);
		    
		    SecondEntityLocateStart = Sentence.indexOf(entity_start,FirstEntityLocateEnd);
		    SecondEntityLocateEnd = Sentence.indexOf(entity_end,SecondEntityLocateStart);
		    
		    ArrayList<HashMap<String, Integer>> TempList = new ArrayList();
	    	HashMap<String, Integer>   prefix = new HashMap<>();
	        HashMap<String, Integer>   middle = new HashMap<>();
	        HashMap<String, Integer>   suffix = new HashMap<>();
	        String middle_string = "";
	        
	        //保存抽取的元组
	        ArrayList TempTuple = new ArrayList();
	        
		    //处理两个实体的左中右字符串
	        String FirstEntityLocate = "", SecondEntityLocate = "";
	        
	        FirstEntityLocate = Sentence.substring(FirstEntityLocateStart + entity_start.length() + 1,FirstEntityLocateEnd - 1);
	        SecondEntityLocate = Sentence.substring(SecondEntityLocateStart + entity_start.length() + 1,SecondEntityLocateEnd - 1);
	        
	        //System.out.println("句子中的实体：" + FirstEntityLocate+ " " + SecondEntityLocate);
	        //System.out.println("种子中的实体" + FirstEntity+ " " + SecondEntity);
	        
		    if(FirstEntityLocate.equals(FirstEntity) && SecondEntityLocate.equals(SecondEntity)){
		        
//			    	System.out.println(FirstEntityLocate+ " " + SecondEntityLocate);
//			    	System.out.println(Sentence);
		    	
		    		//得到原文单词的词干
//		    		WordNetHelper getstem = new WordNetHelper();
		    		//得到前缀的单词
		    		int i_first = 0;
		    		char c;
		    	    //保存从句子中读取的单词
		    	    StringBuffer word = new StringBuffer();
		    	    //前中后缀单词个数限制
		    	    int word_number = 0;
		    	    
		    	    //前缀单词统计
		    	    ArrayList<String> preWord = new ArrayList<>();
		    	    i_first = FirstEntityLocateStart-1;
		    	    
		    	    //处理掉第一个实体前的所有空格
		    	    while(true){
		    	    	if(i_first < 0){
		    	    		break;
		    	    	}
		    	    	else if(Character.isLetter(Sentence.charAt(i_first))){
		    	    		break;
		    	    	}
		    	    	i_first --;
		    	    }
		    		for(; i_first >= 0; i_first--){
		    		   	c = Sentence.charAt(i_first);
		    		   	
//		    		   	boolean isLetter = false;
//			    	    int isLetterJudge = 0;
		    		   	
		    		    if(word_number == AdjustParameter.PreWordNum)
		    		   		break;
		    		   	//找到一个单词
		    		   	if(c == ' '||i_first == 0||NonChiSplit.isCharSeperator(c)){
		    		   		String word_reverse = "";
		    		   		if(i_first == 0)
		    		   			word.append(c);
		    		   		//将反向字母转成正向，得到单词保存在变量中
		    		   		word_reverse = word.reverse().toString();
		    		   		
				    	    if(!prefix.containsKey(word_reverse)){//如果前缀哈希表中没有包含该单词
		    		   			if(!"".equals(word_reverse))//该单词不为空
		    		   		       prefix.put(word_reverse, 1);//将该该单词按照String,int形式保存起来,得到前缀中该单词的个数
		    		   		}
		    		   		else{//否则，该单词在之前的前缀中出现过，该单词所对应的频数加一
		    		   			prefix.put(word_reverse,prefix.get(word_reverse)+1);
		    		   		}
		    		   		word = new StringBuffer();
		    		   		word_number += 1;
		    		   		
		    		   		//改变量里保存的前缀单词是倒着存的，所以在提取的时候需要正着取，所以每次一个句子中的前缀单词先暂存在preword变量中，接下来在正向保存到TempTuple中，为计算元组的权重做准备
		    		   		if(!"".equals(word_reverse))
		    		   		   preWord.add(word_reverse);
		    		   	}
		    		   	else{
		    		   		word.append(c);
		    		   	}
		    		}
		    		
		    		//正向保存前缀的词
		    		int j = 0;
		    		//2016/12/13改动
		    		
	    			for(j = 0; j < AdjustParameter.PreWordNum - preWord.size(); j++){
	    				TempTuple.add("");
	    			}
	    			for(j = preWord.size()-1; j >= 0; j--){
	    				TempTuple.add(preWord.get(j));
	    			}
		    		//2016/12/13改动结束
		    		
		    		//前缀保存结束后，保存第一个实体
		    		TempTuple.add(FirstEntity);
		    		
		    		//中缀单词统计
		    		word = new StringBuffer();
		    		int i_end = 0;
		    		i_end =  SecondEntityLocateStart - 1;
			    	while(true){
			    		if(i_end < 0){
			    			break;
			    		}
			    		else if(!Character.isLetter(Sentence.charAt(i_end))){
			    	    	break;
			    	    }
			    	    i_end --;
			    	}
		    		for(i_first = FirstEntityLocateEnd + entity_end.length() + 1; i_first <= i_end; i_first++){
		    		   	c = Sentence.charAt(i_first);
		    		   	
		    		   	//找到一个单词
		    		   	if(c == ' '||i_first == i_end||NonChiSplit.isCharSeperator(c)){
		    		   		String word_middle = "";
		    		   		word_middle = word.toString();
		    		   		
		    		   		if(!middle.containsKey(word_middle)){
		    		   			if(!"".equals(word_middle))
		    		   		       middle.put(word_middle, 1);
		    		   		}
		    		   		else{
		    		   			middle.put(word_middle,middle.get(word_middle)+1);
		    		   		}
		    		   		word = new StringBuffer();
		    		   		
		    		   		if(!"".equals(word_middle))
		    		   		    TempTuple.add(word_middle);
		    		   		
		    		   	}
		    		   	else{
		    		   		word.append(c);
		    		   	}
		    		}
		    		
		    		//中缀保存结束后保存第二个实体
		    		TempTuple.add(SecondEntity);
		    		
		    		//后缀单词统计
		    		word = new StringBuffer();
		    		word_number = 0;
		    		ArrayList<String> suffWord = new ArrayList<>();
		    		for(i_first = SecondEntityLocateEnd + entity_end.length() + 1; i_first < Sentence.length(); i_first++){
		    		   	c = Sentence.charAt(i_first);
		    		   	
		    		   	if(word_number == AdjustParameter.SufWordNUm)
		    		   		break;
		    		   	
		    		   	//找到一个单词
		    		   	if(c == ' '||i_first == (Sentence.length()-1)||NonChiSplit.isCharSeperator(c)){   		
		    		   		
		    		   		String word_suffix = "";
		    		   		word_suffix = word.toString();
		    		   		
		    		   		if(!suffix.containsKey(word_suffix)){
		    		   			if(!"".equals(word_suffix))
		    		   		      suffix.put(word_suffix, 1);
		    		   		}
		    		   		else{
		    		   			suffix.put(word_suffix,suffix.get(word_suffix)+1);
		    		   		}
		    		   		word = new StringBuffer();
		    		   		word_number += 1;
		    		   		
		    		   		//SuffixWords += word_suffix + " ";
		    		   		if(!"".equals(word_suffix))
		    		   		   suffWord.add(word_suffix);
		    		   	}
		    		   	else{
		    		   		word.append(c);
		    		   	}
		    		}
		    		
		    		//保存后缀单词
		    		//2016/12/13改动
		    		for(j = 0; j < suffWord.size(); j++){
		    			TempTuple.add(suffWord.get(j));
		    		}
		    		for(j = suffWord.size(); j < AdjustParameter.SufWordNUm; j++){
		    			TempTuple.add("");
		    		}
		    		//2016/12/13改动结束
		    		
	    			TempList.add(prefix);
	    			TempList.add(suffix);
	    			TempList.add(middle);
	    			
//	    			System.out.println("TempTuple: " + TempTuple);
//	    			System.out.println("prefix: " + prefix);
//	    			System.out.println("middle: " + middle);
//	    			System.out.println("suffix: " + suffix);
	    			
	    			//保存五元组
	    			ArrayList<ArrayList> Tuple = new ArrayList<>();
	    			Tuple.add(TempTuple);
	    			if(tuplesStore.getKey(Entity) != null){
	    				tuplesStore.getKey(Entity).add(TempTuple);
	    			}else{
	    				tuplesStore.putTuples(Entity,Tuple);
	    			}
	    			
	    			//WriteIntoFile("1"+"$"+FirstEntity+"$"+SecondEntity+"$"+prefix+"$"+suffix+"$"+middle);
		    	
		    	//PatternTuples.add(TempList);
		    	
		    	//WriteIntoFile(Sentence);
		    	
		    }
		    return TempList;
	  }
}
