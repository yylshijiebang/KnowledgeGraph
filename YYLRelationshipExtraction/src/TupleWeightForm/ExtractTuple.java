package TupleWeightForm;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.sql.SQLNonTransientConnectionException;
import java.util.ArrayList;
import java.util.HashMap;

import org.omg.CosNaming.NamingContextExtPackage.AddressHelper;

import FeatureExtract.FeatureStore;
import NameEntityRecognize.TrieDictionary;
import NameEntityRecognize.WordNetHelper;
import StaticConstant.AdjustParameter;
import StaticConstant.ConstantParameter;
import StaticConstant.NonChiSplit;


public class ExtractTuple {
	  String entity_start = "[entity_start]";
	  String entity_end = "[entity_end]";
	  
	  
	  //将匹配到的单词写入文件中
	  public void WriteIntoFile(String glaucoma){
		    //写入文件
		    BufferedWriter bw = null;
		    FileWriter fileWriter=null;
			try{
				fileWriter = new FileWriter("D:/YYLSoftware/Program/YYLRelationshipExtraction/SentenceTuple.txt",true);
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
	  
	  //抽取五元组向量和五元组组句子
	  public void ExtractTuples(int k,String Entity, TuplesStore tuplesStore){
		  
		    int i,j;
			String word = "";
			char c;
			
			//是否符合词向量标记
			int flag = 0;
			
			//保存句子和五元组向量
			String Sentence = FeatureStore.SentenceVector.get(k).sentence;
			ArrayList<Float> five_tuple_vector = new ArrayList<>();
			for(int g = 0; g < FeatureStore.SentenceVector.get(k).vector.size(); g++)
			    five_tuple_vector.add(FeatureStore.SentenceVector.get(k).vector.get(g)); 
			
			//System.out.println("ExtractTuplesSentence:"+ Sentence);
			
			//种子实体对中实体的保存
			String FirstEntity = "",SecondEntity = "";
		    FirstEntity = Entity.substring(0,Entity.indexOf(','));
		    SecondEntity = Entity.substring(Entity.indexOf(',')+1,Entity.length());
		    
		    //System.out.println("ExtractTuplesEntity:"+ FirstEntity + ":" + SecondEntity);
			
			//得到句子中每个实体的位置
		    int FirstEntityStartLocate = -1;
		    int FirstEntityEndLocate = -1;
		    
		    int SecondEntityStartLocate = -1;
		    int SecondEntityEndLocate = -1;
		    
		    //句子中两个实体的保存
	        String SentenceFirstEntity = "", SentenceSecondEntity = "";
	       
	        FirstEntityStartLocate = Sentence.indexOf(entity_start);
	        FirstEntityEndLocate = Sentence.indexOf(entity_end);
	        SecondEntityStartLocate = Sentence.indexOf(entity_start,FirstEntityEndLocate);
	        SecondEntityEndLocate = Sentence.indexOf(entity_end,SecondEntityStartLocate);
	        if(FirstEntityStartLocate + entity_start.length() + 1 < FirstEntityEndLocate - 1 && SecondEntityStartLocate + entity_start.length() + 1< SecondEntityEndLocate - 1){
	           SentenceFirstEntity = Sentence.substring(FirstEntityStartLocate + entity_start.length() + 1,FirstEntityEndLocate - 1);
	           SentenceSecondEntity = Sentence.substring(SecondEntityStartLocate + entity_start.length() + 1,SecondEntityEndLocate - 1);
	        }
	        
	        //System.out.println("ExtractTuplesSentenceEntity:"+ SentenceFirstEntity + ":" + SentenceSecondEntity);
	        
	        //重新初始化句子中实体位置变量
	        FirstEntityStartLocate = -1;
		    FirstEntityEndLocate = -1;
		    
		    SecondEntityStartLocate = -1;
		    SecondEntityEndLocate = -1;
		   
		    if(SentenceFirstEntity.equals(FirstEntity) && SentenceSecondEntity.equals(SecondEntity)){//如果种子中的实体和句子中的实体匹配
				    int word_num = 0;
				    String five_tuple = "";//保存五元组
				    for(j = 0; j < Sentence.length(); j++){
				    	c = Sentence.charAt(j);
				    	//如果该字符c是空格，则是一个单词
				    	if(c == ' '|| j == Sentence.length()-1){
				    		if(j == Sentence.length()-1){
				    			word += c;
				    		}
		//		    		SentenceHash.put(word,word_num);
				    	    //保存实体位置
				    		if(word.equals(entity_start)){
				    			if(FirstEntityStartLocate == -1){
				    				FirstEntityStartLocate = word_num;
				    			}else{
				    				SecondEntityStartLocate = word_num;
				    			}
				    		}else if(word.equals(entity_end)){
				    			if(FirstEntityEndLocate == -1){
				    				FirstEntityEndLocate = word_num;
				    			}else{
				    				SecondEntityEndLocate = word_num;
				    			}
				    		}
				    		word_num ++;
				    		word = "";
				    	}else{
				    		word += c; 
				    	} 
				    }
				    
				   // System.out.println("Sentence:"+Sentence);
				   // System.out.println("EntityLocate:"+ FirstEntityStartLocate + ":" + FirstEntityEndLocate + ":" + SecondEntityStartLocate + ":" + SecondEntityEndLocate);
				    
				    
				    word_num = 0;
				    word = "";
					//得到句子中前中后缀单词
					for(j = 0; j < Sentence.length(); j++){
				    	c = Sentence.charAt(j);
				    	//如果该字符c是空格，则是一个单词
				    	if(c == ' '|| j == Sentence.length()-1){
				    		if(j == Sentence.length()-1){
				    			word += c;
				    		}
				    		int p1 = -1;//单词到第一个实体的距离
				    	    int p2 = -1;//单词到第二个实体的距离
				    	    flag = 0;
				    		if(!word.equals(entity_start) && !word.equals(entity_end)){//如果单词不是实体标志
					    		if(word_num < FirstEntityStartLocate){//第一个实体前的单词
					    			p1 = 0 - (FirstEntityStartLocate - word_num); 
					    			p2 = 0 - (SecondEntityStartLocate - word_num);
					    			if((0-p1) <= AdjustParameter.PreWordNum){//如果属于第一个实体前3个单词之内
					    				flag = 1;
					    			}
//					    		}else if(word_num > FirstEntityStartLocate && word_num < FirstEntityEndLocate){//第一个实体
//					    			p1 = 0;
//					    			p2 = FirstEntityEndLocate - SecondEntityStartLocate;
//					    			flag = 2;
					    		}else if(word_num > FirstEntityEndLocate && word_num < SecondEntityStartLocate){//第一个实体和第二个实体之间的单词
									p1 = word_num - FirstEntityEndLocate;
									p2 = 0 - (SecondEntityStartLocate - word_num);
									flag = 1;
//								}else if(word_num > SecondEntityStartLocate && word_num < SecondEntityEndLocate){//第二个实体
//									p1 = FirstEntityEndLocate - SecondEntityStartLocate;
//									p2 = 0;
//									flag = 2;
								}else if(word_num > SecondEntityEndLocate){//第二个实体后的单词
									p1 = word_num - FirstEntityEndLocate;
									p2 = word_num - SecondEntityEndLocate;
									if(p2 <= AdjustParameter.SufWordNUm){//如果属于第二个实体后三个单词之内
										flag = 1;
									}
								}
					    		
					    		//System.out.println("word:"+ word + ":"+"distant:"+p1+":"+p2);
					    		
					    	    //得到前中后缀单词
					    		if(flag == 1){
						    		five_tuple += word + " ";
//					    		}else if(flag == 2){
//					    			five_tuple += word + " ";
					    		}
				    		}else if(!word.equals(entity_start)){
				    		     five_tuple += "| "; 
				    		}
				    		word_num ++;
				    		word = "";
				    	}else{
				    		word += c; 
				    	} 
				    }
					//保存实体对和其五元组向量和五元组句子
					FeatureStore featureStore = new FeatureStore();
					featureStore.sentence = five_tuple;
					for(int g = 0; g < five_tuple_vector.size(); g++){
					     featureStore.vector.add(five_tuple_vector.get(g));
					}
				    
					//System.out.println("ExtractTuplesFeatureStore:"+ featureStore.sentence + ":" + featureStore.vector);
				
	    		    ArrayList<FeatureStore> Tuple = new ArrayList<>();
	    			Tuple.add(featureStore);
	    			if(tuplesStore.getKey(Entity) != null){
	    				tuplesStore.getKey(Entity).add(featureStore);
	    			}else{
	    				tuplesStore.putTuples(Entity,Tuple);
	    			}
		    }
	  }
}
