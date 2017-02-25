package ExtractEntity;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import PatternForm.GroupTupleStore;
import StaticConstant.AdjustParameter;
import StaticConstant.NonChiSplit;
import TupleWeightForm.TuplesStore;
import TupleWeightForm.WeightTuples;

public class ExtractCandidateEntity {
	
	 String entity_start = "[entity_start]";
	 String entity_end = "[entity_end]";
	 //将抽取的候选实体保存到文本中
	 public void WriteIntoFile(String glaucoma){
		    //写入文件
		    BufferedWriter bw = null;
		    FileWriter fileWriter=null;
			try{
				fileWriter = new FileWriter("E:/北航文件/编程程序/YYLRelationshipExtraction/CandidateEntityTuple.txt",true);
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
	 
	 //抽取候选实体
     public void GetCandidateEntity(GroupTupleStore group_pattern){
		   // GroupTupleStore group_pattern = new GroupTupleStore();
		   TuplesStore text_sentence = new TuplesStore();
		   //System.out.println(text_sentence.Text_sentence);
		  
		   Iterator it = group_pattern.temp_pattern.entrySet().iterator();
		   //Iterator it = group_pattern.entrySet().iterator();
		   String Entity = "";
		   
		   TempEntityStore entityStore = new TempEntityStore();
		   
		   //System.out.println(text_sentence.Text_sentence.size());
		   
		   //从文本中抽取实体对
		   while(it.hasNext()){
			   Entry entry = (Entry)it.next();
				
			   Entity = (String)entry.getKey();
			  // System.out.println("生成模板的实体对："+Entity.substring(0, Entity.indexOf(AdjustParameter.RecognizeFlag)));
				 
			   ArrayList<ArrayList> group_pattern_collection = new ArrayList<>();
			   group_pattern_collection = (ArrayList<ArrayList>) entry.getValue();
			   
			   
			   for(int j = 0; j < group_pattern_collection.size(); j++){
				   
				   ArrayList<WeightTuples> pattern = group_pattern_collection.get(j);
				   //System.out.println("每个实体对对应的模板："+ pattern);
				   ArrayList<ArrayList> temp_tuple = new ArrayList<>();
				   //主要是完成对上一轮实体和实体产生的模板进行保存
				   //五元组的字符串形式
                   String string_pattern = "";
                   for(int g = 0; g < pattern.size(); g++){
                      string_pattern += "<"+pattern.get(g).word+","+pattern.get(g).weight+">"+",";
       			   }
                   string_pattern +="}";
       			   //五元组字符串形式结束
                   if(Entity.indexOf(AdjustParameter.RecognizeFlag) > 0){
					   if(entityStore.getPattern(Entity.substring(0, Entity.indexOf(AdjustParameter.RecognizeFlag))) != null){
						   if(!entityStore.string_tuple.contains(string_pattern)){
	                           entityStore.getPattern(Entity.substring(0, Entity.indexOf(AdjustParameter.RecognizeFlag))).add(pattern);	
	                           entityStore.string_tuple.add(string_pattern);
						   }
	          		   }else{
	          			   
	          			   temp_tuple.add(pattern);
	          		       entityStore.putPattern(Entity.substring(0, Entity.indexOf(AdjustParameter.RecognizeFlag)),temp_tuple);
	          		       entityStore.string_tuple.add(string_pattern);
	          		       //2017/2/13修改开始
	          		       if(!entityStore.entity_pairs.contains(Entity.substring(0, Entity.indexOf(AdjustParameter.RecognizeFlag))))
	          		          entityStore.entity_pairs.add(Entity.substring(0, Entity.indexOf(AdjustParameter.RecognizeFlag)));//将新的实体保存到变量中
	          		      //2017/2/13修改结束
	          		   }
                   }
				   
				   for(int i = 0; i < text_sentence.Text_sentence.size(); i++){
					   String sentence = text_sentence.Text_sentence.get(i);
					   
					   //查找句子中出现的实体标记
					   int FirstEntityLocateStart = -1, FirstEntityLocateEnd = -1, SecondEntityLocateStart = -1, SecondEntityLocateEnd = -1;//实体在语句中的位置
					   FirstEntityLocateStart =sentence.indexOf(entity_start);
					   FirstEntityLocateEnd = sentence.indexOf(entity_end);
					    
					   SecondEntityLocateStart = sentence.indexOf(entity_start,FirstEntityLocateEnd);
					   SecondEntityLocateEnd = sentence.indexOf(entity_end,SecondEntityLocateStart);
					   
					   if(FirstEntityLocateStart != -1 && FirstEntityLocateEnd != -1 && SecondEntityLocateStart != -1&& SecondEntityLocateEnd != -1){
						   String temp_prefix = "";
						   String temp_middle = "";
						   String temp_suffix = "";
						   
						   //得到pattern的前缀单词,前缀匹配
						   String prefix = "";		   
						   int flag = 0;
						   for(int t = 0; t < AdjustParameter.PreWordNum-1; t++){
							   if(!"".equals(pattern.get(t).word)){ 
								   prefix += pattern.get(t).word;
								   prefix += " ";
							   }
						   }
						   prefix += pattern.get(AdjustParameter.PreWordNum-1).word;
						   
						   //得到句子中前缀的单词
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
				    	    	else if(Character.isLetter(sentence.charAt(i_first))){
				    	    		break;
				    	    	}
				    	    	i_first --;
				    	   }
				    	   //保存单词
				    	   for(; i_first >= 0; i_first--){
				    		   	c = sentence.charAt(i_first);
				    		   	
				    		    if(word_number == AdjustParameter.PreWordNum)
				    		   		break;
				    		   	//找到一个单词
				    		   	if(c == ' '||i_first == 0||NonChiSplit.isCharSeperator(c)){
				    		   		String word_reverse = "";
				    		   		if(i_first == 0)
				    		   			word.append(c);
				    		   		//将反向字母转成正向，得到单词保存在变量中
				    		   		word_reverse = word.reverse().toString();
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
				    		int k = 0;
			    			for(k = preWord.size()-1; k > 0; k--){
			    				temp_prefix += preWord.get(k) + " ";
			    			}
			    			if(!preWord.isEmpty())
						       temp_prefix += preWord.get(0);
						    
						    //如果模板中的前缀和句子中的前缀不匹配，则后续的操作么比较进行
						    if(!temp_prefix.equals(prefix)){
						    	continue;
						    }
						   
						    //得到pattern的中缀单词，中缀匹配
						    String middle = "";
						    for(int t = AdjustParameter.PreWordNum; t < pattern.size()-AdjustParameter.SufWordNUm; t++){
							   if(!"".equals(pattern.get(t).word)){
							      middle += pattern.get(t).word + " ";
							   }
						    }
						    if(middle.length() != 0)
						       middle = middle.substring(0,middle.length()-1);
						   
						    //得到句子中中缀单词
						    word = new StringBuffer();
				    	    int i_end = 0;
				    	    i_end =  SecondEntityLocateStart - 1;
					        while(true){
					    		if(i_end < 0){
					    			break;
					    		}
					    		else if(!Character.isLetter(sentence.charAt(i_end))){
					    	    	break;
					    	    }
					    	    i_end --;
					        }
				    	    for(i_first = FirstEntityLocateEnd + entity_end.length() + 1; i_first <= i_end; i_first++){
				    		   	c = sentence.charAt(i_first);
				    		   	
				    		   	//找到一个单词
				    		   	if(c == ' '||i_first == i_end||NonChiSplit.isCharSeperator(c)){
				    		   		String word_middle = "";
				    		   		word_middle = word.toString();
				    		   		word = new StringBuffer();
				    		   		
				    		   		if(!"".equals(word_middle))
				    		   		    temp_middle += word_middle + " ";
				    		   		
				    		   	}
				    		   	else{
				    		   		word.append(c);
				    		   	}
				    	   }
				    	   if(temp_middle.length() != 0)
						      temp_middle = temp_middle.substring(0,temp_middle.length()-1);
						  
						   //如果模板中的中缀和句子中的中缀不匹配，则后续的操作不进行
						   if(!temp_middle.equals(middle)){
							   continue;
						   }
						   
						   
						   //得到pattern的后缀单词，后缀匹配
						   String suffix = "";
						   for(int t = pattern.size()-AdjustParameter.SufWordNUm; t < pattern.size() - 1; t++){
							   if(!"".equals(pattern.get(t).word)){
								   suffix += pattern.get(t).word;
								   suffix += " ";
							   }   
						   }
						   suffix += pattern.get(pattern.size()-1).word;
						   //得到句子中的后缀单词
						   word = new StringBuffer();
				    	   word_number = 0;
				    	   for(i_first = SecondEntityLocateEnd + entity_end.length() + 1; i_first < sentence.length(); i_first++){
				    		   	c = sentence.charAt(i_first);
				    		   	
				    		   	if(word_number == AdjustParameter.SufWordNUm)
				    		   		break;
				    		   	
				    		   	//找到一个单词
				    		   	if(c == ' '||i_first == (sentence.length()-1)||NonChiSplit.isCharSeperator(c)){   		
				    		   		
				    		   		String word_suffix = "";
				    		   		word_suffix = word.toString();
				    		   		word = new StringBuffer();
				    		   		word_number += 1;
				    		   		
				    		   		//SuffixWords += word_suffix + " ";
				    		   		if(!"".equals(word_suffix)){
				    		   		   temp_suffix += word_suffix + " ";
				    		   		}
				    		   	}
				    		   	else{
				    		   		word.append(c);
				    		   	}
				    		}
				    	    if(temp_suffix.length() != 0)
				    		   temp_suffix = temp_suffix.substring(0,temp_suffix.length()-1);
				    		
				    		//如果模板中的后缀和句子中的后缀不匹配，则后续的操作不进行
							if(!temp_suffix.equals(suffix)){
								continue;
							}

							if(prefix.equals(temp_prefix) && middle.equals(temp_middle) && suffix.equals(temp_suffix)){
	                             ArrayList<ArrayList> tuple = new ArrayList<>();
	                             String NewEntity = "";
	                             NewEntity = sentence.substring(FirstEntityLocateStart + entity_start.length() + 1,FirstEntityLocateEnd - 1) +"," + sentence.substring(SecondEntityLocateStart + entity_start.length() + 1,SecondEntityLocateEnd - 1);
//	                             System.out.println("产生新实体："+NewEntity);
//	                             System.out.println("产生新实体的模板："+pattern);
	                             //五元组的字符串形式
	                             String pattern_string = "";
                                 for(int g = 0; g < pattern.size(); g++){
                                     pattern_string += "<"+pattern.get(g).word+","+pattern.get(g).weight+">"+",";
                     			 }
                     			 pattern_string +="}";
                     			 //五元组字符串形式结束	 
	                             if(entityStore.getPattern(NewEntity) != null){
	                            	if(!entityStore.string_tuple.contains(pattern_string)){
	                            		
	                            		entityStore.getPattern(NewEntity).add(pattern);
	                            		entityStore.string_tuple.add(pattern_string);
	                            	}
	                    		 }else{
	                    		     tuple.add(pattern);
	                    		     entityStore.putPattern(NewEntity,tuple);
	                    		     //元组的字符串形式
	                    		     entityStore.string_tuple.add(pattern_string);
	                    		     //元组字符串形式结
	                    		     //2017/2/12修改开始
	   	                             if(!entityStore.entity_pairs.contains(NewEntity))
	                    		        entityStore.entity_pairs.add(NewEntity);//将新的实体保存到变量中
	                    		     //2017/2/12修改结束
	                    		 }
							}
							
							FirstEntityLocateStart = -1;
							FirstEntityLocateEnd = -1;
							    
							SecondEntityLocateStart = -1;
							SecondEntityLocateEnd = -1;
					   } 
					  
					   
				   }
			   }
		   }
		   
		   //测试数据
//		   System.out.println("extract entity finish");
//		   it = entityStore.temp_entity.entrySet().iterator();
//		   while(it.hasNext()){
//				 Entry entry = (Entry)it.next();
//				
//				 Entity = (String)entry.getKey();
//				 System.out.println(Entity);
//				 
//				 WriteIntoFile(Entity);
//				 
//				 ArrayList<ArrayList> GroupFiveTupleCollection = new ArrayList<>();
//				 GroupFiveTupleCollection = (ArrayList<ArrayList>) entry.getValue();
//				 System.out.println(GroupFiveTupleCollection);
//				 
//				 
//				 
//				 for(int j = 0; j < GroupFiveTupleCollection.size(); j++){
//					ArrayList<WeightTuples> test = GroupFiveTupleCollection.get(j);
//					String test_string = "";
//					System.out.println("=====");
//					test_string +="{";
//					for(int t = 0; t < test.size(); t++){
//						test_string += "<"+test.get(t).word+","+test.get(t).weight+">"+",";
//						System.out.println(test.get(t).word);
//						System.out.println(test.get(t).weight);
//					}
//					test_string +="}";
//					WriteIntoFile(test_string);
//				 }
//			   
//			   //
//		  }
	 }
}
