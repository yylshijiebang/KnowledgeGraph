package TupleWeightForm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import ExtractEntity.TempEntityStore;

import java.util.Scanner;

import PatternForm.GroupTuple;
import PatternForm.GroupTupleStore;
import PatternForm.MatchTuple;
import StaticConstant.AdjustParameter;
import StaticConstant.NonChiSplit;


public class ReadSeedAndSentence {
	 //没词迭代需要保存前中后缀
	 HashMap<String, Integer>   prefix = new HashMap<>();//前缀中每个单词对应的频数
     HashMap<String, Integer>   middle = new HashMap<>();//中缀中每个单词对应的频数
     HashMap<String, Integer>   suffix = new HashMap<>();//后缀中每个单词对应的频数
     int prefix_total_word = 0;
     int middle_total_word = 0;
     int suffix_total_word = 0;
	 //保存种子实体关系对
	 TempEntityStore SeedList = new TempEntityStore();
	 
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
	 
	  //迭代次数等于2的时候保存一部分看看结果
	 public void WriteIntoFile2(String glaucoma){
		    //写入文件
		    BufferedWriter bw = null;
		    FileWriter fileWriter=null;
			try{
				fileWriter = new FileWriter("E:/北航文件/编程程序/YYLRelationshipExtraction/CandidateEntityTuple2.txt",true);
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
	 
     //读取文本中的种子关系对
	 public void GetSeed(String SeedFileName, String SentenceFileName){
		  try{
			 //读取种子对到内存
		     Scanner EntityPairs = new Scanner(new File(SeedFileName),"UTF-8");
		     while (EntityPairs.hasNextLine()){
		    	 String Entity = EntityPairs.nextLine();
		    	 SeedList.entity_pairs.add(Entity);
		     }
		  }catch(Exception e){
			  e.printStackTrace();
		  }
		  //读取句子保存到内存
		  System.out.println("种子对个数"+SeedList.entity_pairs.size());
		  ReadSentence(SentenceFileName);
	 }
	 
	 //读取句子到内存
	 public void ReadSentence(String FileName){
		 //保存读取的句子
		 TuplesStore text_sentence = new TuplesStore();
		
//		 int i;
//		 try{
//		     Scanner reader = new Scanner(new File(FileName),"UTF-8");
//		     while (reader.hasNextLine()){
//		    	 String Sentence = reader.nextLine();
//		         //保存已经读取的文本句子，为迭代准备
//             	 text_sentence.putSentence(Sentence);
//		     }
//
//		 }catch(Exception e){
//			 e.printStackTrace();
//		 }
		 
		 try{
		     //Scanner reader = new Scanner(new File(FileName),"UTF-8");
		     int i = 0;
		     Reader reader = null;
             //读取txt文件中的内容
             System.out.println("以字符为单位读取文件内容，一次读一个字节：");  
             // 一次读一个字符  
             reader = new InputStreamReader(new FileInputStream(FileName));  
             String Sentence = ""; 
             int tempchar = -1;  
             while ((tempchar = reader.read()) != -1) {  
                 // 对于windows下，\r\n这两个字符在一起时，表示一个换行。  
                 if((char) tempchar == '\n'){
                	 i++;
                	 Sentence = Sentence.substring(0,Sentence.length()-1);
                	 //System.out.println(Sentence); 
                	 //保存已经读取的文本句子，为迭代准备
                	 text_sentence.putSentence(Sentence); 
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
		 
		 GetSentence();
	 }
	 
     //读取内存中的句子，并抽取出符合条件的五元组
	 public void GetSentence(){
	   	 String Sentence = "";
	   	 TuplesStore text_sentence = new TuplesStore();
         int i,j;
         int iterator_flag = 0;
         //程序进行迭代，抽取实体和模板
		 while(true){
			 //迭代抽取结束判断条件
			 int judge_start = SeedList.entity_pairs.size();
			 //迭代次数
			 iterator_flag ++;
			 //2017/2/12修改，每次迭代保存本次迭代产生的五元组
			 TuplesStore TheTupleStore = new TuplesStore();
			 //每次迭代时，初始化抽取元组的类
			 ExtractTuple extract_tuple = new ExtractTuple();
			 //2017/2/12修改结束
			 
			 
			 //迭代抽取开始
		   	 for(i = 0; i < SeedList.entity_pairs.size();i++){
		   		 for(j = 0; j < text_sentence.Text_sentence.size(); j++){
		   		    Sentence = text_sentence.Text_sentence.get(j);
		   			ArrayList<HashMap<String, Integer>> TempList = new ArrayList();
		      		
//		   			ExtractTuple extract_tuple = new ExtractTuple();
		      		TempList = extract_tuple.ExtractTuples(Sentence,SeedList.entity_pairs.get(i).toString(),TheTupleStore);
		//          		System.exit(0);
		      		
		      		String word = "";
		      		int word_number = 0;
		      	
		      		if(TempList.size() > 0){
		          		//统计前缀的单词个数
		          		Iterator it = TempList.get(0).entrySet().iterator();  
		          		while(it.hasNext()){  
		          		     Entry entry = (Entry)it.next();  
		          		     word = (String)entry.getKey(); //返回与此项对应的键  
		          		     word_number = (int)entry.getValue(); // 返回与此项对应的值 
		          		     
		          		     //如果前缀已经包含该单词，则增加单词的所对应的频数
		          		     if(prefix.containsKey(word)){
		          		    	 prefix.put(word, prefix.get(word) + word_number);
		          		     }else{//否则，则添加该单词和其在前缀中的频数
		          		    	 prefix.put(word,word_number);
		          		     }
		          		     //System.out.println(entry.getValue());
		          		     prefix_total_word += word_number; 
		          		     
		//              		     System.out.println("prefix");
		//              		     System.out.println(word);
		//              		     System.out.println(prefix.get(word));
		          		}  
		          		
		          		
		          		//统计中缀的单词个数
		          		it = TempList.get(2).entrySet().iterator();
		          		while(it.hasNext()){  
		          		     Entry entry = (Entry)it.next();  
		          		     word = (String)entry.getKey(); //返回与此项对应的键  
		          		     word_number = (int)entry.getValue(); // 返回与此项对应的值 
		          		     
		          		     //如果前缀已经包含该单词，则增加单词的所对应的频数
		          		     if(middle.containsKey(word)){
		          		    	 middle.put(word, middle.get(word) + word_number);
		          		     }else{//否则，则添加该单词和其在前缀中的频数
		          		    	 middle.put(word,word_number);
		          		     }
		          		     //System.out.println(entry.getValue()); 
		          		     middle_total_word += word_number;
		          		     
		//              		     System.out.println("middle");
		//              		     System.out.println(word);
		//              		     System.out.println(middle.get(word));
		          		}
		          		
		          		//统计后缀的单词个数
		          		it = TempList.get(1).entrySet().iterator();
		          		while(it.hasNext()){  
		          		     Entry entry = (Entry)it.next();  
		          		     word = (String)entry.getKey(); //返回与此项对应的键  
		          		     word_number = (int)entry.getValue(); // 返回与此项对应的值 
		          		     
		          		     //如果前缀已经包含该单词，则增加单词的所对应的频数
		          		     if(suffix.containsKey(word)){
		          		    	 suffix.put(word, suffix.get(word) + word_number);
		          		     }else{//否则，则添加该单词和其在前缀中的频数
		          		    	 suffix.put(word,word_number);
		          		     }
		          		     //System.out.println(entry.getValue());
		          		     suffix_total_word += word_number;
		          		     
		//              		     System.out.println("suffix");
		//              		     System.out.println(word);
		//              		     System.out.println(suffix.get(word));
		          		}
		   		    }
		   	    }
		   	 
		   	 }
		   	 //迭代抽取结束
//		   	System.out.println("ReadSeed: "+prefix_total_word);
//		    System.out.println("ReadSeed: "+prefix);
//		    System.out.println("ReadSeed: "+middle_total_word);
//		    System.out.println("ReadSeed: "+middle);
//		    System.out.println("ReadSeed: "+suffix_total_word);
//		    System.out.println("ReadSeed: "+suffix);
//		    System.out.println("ReadSeed: "+TheTupleStore.Tuples);
		    
		    CalculateTupleWeight(TheTupleStore);
		    
		    //2017/2/12修改，每次迭代结束，对前中后缀频数进行初始化
		    prefix = new HashMap<>();//前缀中每个单词对应的频数
		    middle = new HashMap<>();//中缀中每个单词对应的频数
		    suffix = new HashMap<>();//后缀中每个单词对应的频数
		    prefix_total_word = 0;
		    middle_total_word = 0;
		    suffix_total_word = 0;
		    //2017/2/12修改结束
		    
		    //迭代抽取结束判断条件
			int judge_end = SeedList.entity_pairs.size();
			System.out.println("第几次迭代："+iterator_flag);
			
			//当迭代次数等于2的时候先保存一次，看看结果怎么样
            if(iterator_flag == 2){
            	TempEntityStore entityStore2 = new TempEntityStore();
    			Iterator it2 = entityStore2.temp_entity.entrySet().iterator();
            	while(it2.hasNext()){
					 Entry entry = (Entry)it2.next();
					
					 String Entity = (String)entry.getKey();
					 //System.out.println(Entity);
					 
					 WriteIntoFile2(Entity);
					 
					 
					 ArrayList<ArrayList> GroupFiveTupleCollection = new ArrayList<>();
					 GroupFiveTupleCollection = (ArrayList<ArrayList>) entry.getValue();
					// System.out.println(GroupFiveTupleCollection);
					 
					 for(j = 0; j < GroupFiveTupleCollection.size(); j++){
						ArrayList<WeightTuples> test = GroupFiveTupleCollection.get(j);
						String test_string = "";//带权重的元组关系
						String test_relationship = "";//带实体关系
						//System.out.println("=====");
						test_string +="{";
						for(int t = 0; t < test.size(); t++){
							test_string += "<"+test.get(t).word+","+test.get(t).weight+">"+",";
							//System.out.println(test.get(t).word);
							//System.out.println(test.get(t).weight);
						}
						test_string +="}";
						WriteIntoFile2(test_string);
						
						test_relationship += "{<"+Entity.substring(0,Entity.indexOf(","))+">,<";
						for(int t = AdjustParameter.PreWordNum; t < test.size() - AdjustParameter.SufWordNUm; t++){
							test_relationship += test.get(t).word + " ";
						}
						test_relationship += ">,<" + Entity.substring(Entity.indexOf(",")+1,Entity.length()) + ">";
						WriteIntoFile2(test_relationship);
					 }
			    }
            	System.out.println("迭代次数等于2的时候，保存结束！");
		    }
            //当迭代次数等于2的时候保存结束
			
			//如果迭代结束后实体对没有增加，则迭代结束 
			if(judge_start == judge_end){
				//迭代结束
				
			    System.out.println("迭代次数："+iterator_flag);
			    
			    
				TempEntityStore entityStore = new TempEntityStore();
				
//				for(int k = 0; k < entityStore.entity_pairs.size(); k++){
//					System.out.println(entityStore.entity_pairs.get(k));
//				}
				
				Iterator it = entityStore.temp_entity.entrySet().iterator();
				
                
				while(it.hasNext()){
						 Entry entry = (Entry)it.next();
						
						 String Entity = (String)entry.getKey();
						 //System.out.println(Entity);
						 
						 WriteIntoFile(Entity);
						 
						 
						 ArrayList<ArrayList> GroupFiveTupleCollection = new ArrayList<>();
						 GroupFiveTupleCollection = (ArrayList<ArrayList>) entry.getValue();
						// System.out.println(GroupFiveTupleCollection);
						 
						 for(j = 0; j < GroupFiveTupleCollection.size(); j++){
							ArrayList<WeightTuples> test = GroupFiveTupleCollection.get(j);
							String test_string = "";//带权重的元组关系
							String test_relationship = "";//带实体关系
							//System.out.println("=====");
							test_string +="{";
							for(int t = 0; t < test.size(); t++){
								test_string += "<"+test.get(t).word+","+test.get(t).weight+">"+",";
								//System.out.println(test.get(t).word);
								//System.out.println(test.get(t).weight);
							}
							test_string +="}";
							WriteIntoFile(test_string);
							
							test_relationship += "{<"+Entity.substring(0,Entity.indexOf(","))+">,<";
							for(int t = AdjustParameter.PreWordNum; t < test.size() - AdjustParameter.SufWordNUm; t++){
								test_relationship += test.get(t).word + " ";
							}
							test_relationship += ">,<" + Entity.substring(Entity.indexOf(",")+1,Entity.length()) + ">";
							WriteIntoFile(test_relationship);
						 }
					   
					   //
				}
				System.out.println("extract entity finish");
				break;
			}
		}
	 }
	 
	 //计算元组的权重
	 public  void CalculateTupleWeight(TuplesStore tuplesStore){
		 int i,j;
		 //实体对
		 String Entity = "";
		 Iterator it = tuplesStore.Tuples.entrySet().iterator();
		 TuplesStore get_tuple_weight = new TuplesStore();
		 while(it.hasNext()){
			 Entry entry = (Entry)it.next();
			 //返回与此项对应的键  
			 Entity = (String)entry.getKey(); 
			 String FirstEntity = "", SecondEntity = "";
			 FirstEntity = Entity.substring(0,Entity.indexOf(','));
			 SecondEntity = Entity.substring(Entity.indexOf(',')+1,Entity.length());
			 
			// System.out.println(Entity);
  		     
			 //返回与键对应的value
			 ArrayList<ArrayList> FiveTupleCollection = new ArrayList<>();
			 FiveTupleCollection = (ArrayList<ArrayList>) entry.getValue();
			 
			// System.out.println(FiveTupleCollection);
			
			 //逐步解析出五元组，进行权重值得计算
			 ArrayList<String> FiveTuple = new ArrayList<>();
			 
			 for(i = 0; i < FiveTupleCollection.size(); i++){
			     FiveTuple = FiveTupleCollection.get(i);
			     
			     //System.out.println(FiveTuple); 
			     //计算每个单词的权重
			     //前缀权重计算  
			     ArrayList<WeightTuples> weight_tuples = new ArrayList<>();
			     ArrayList<String> weight_tuples_test = new ArrayList<>();
			     
			     for(j = 0; j <= AdjustParameter.PreWordNum-1; j++){
			    	 WeightTuples word_weight = new WeightTuples();
			         word_weight.word = FiveTuple.get(j);
			         
			         //System.out.println(word_weight.word);
			         
			         if(prefix.get(word_weight.word) == null)
			        	 word_weight.weight = 0.0;
			         else
			             word_weight.weight = AdjustParameter.PreFactor*(prefix.get(word_weight.word).doubleValue()/(double)prefix_total_word);
			         
			         weight_tuples.add(word_weight);
			     }
			    
//			     System.out.println(weight_tuples.get(0).word);
			     
			     //中缀权重计算
			     for(j = AdjustParameter.PreWordNum+1; j < FiveTuple.size()-AdjustParameter.SufWordNUm-1; j++){
			    	 WeightTuples word_weight = new WeightTuples();
			    	 word_weight.word = FiveTuple.get(j);
//			    	 System.out.println(word_weight.word);
			    	 
			    	 if(middle.get(word_weight.word) == null)
			    		 word_weight.weight = 0.0;
			    	 else
			             word_weight.weight = AdjustParameter.MidFactor*(middle.get(word_weight.word).doubleValue()/(double)middle_total_word);
			         weight_tuples.add(word_weight);
			         
			     }
			     
			     //后缀权重计算
			     for(j = FiveTuple.size()-AdjustParameter.SufWordNUm; j <= FiveTuple.size()-1; j++){
			    	 WeightTuples word_weight = new WeightTuples();
			    	 word_weight.word = FiveTuple.get(j);
			    	 
//			    	 System.out.println(word_weight.word);
			    	 
			    	 if(suffix.get(word_weight.word) == null)
			    		 word_weight.weight = 0.0;
			    	 else
			    		 word_weight.weight = AdjustParameter.SuffFactor*(suffix.get(word_weight.word).doubleValue()/(double)suffix_total_word);
			    	 weight_tuples.add(word_weight);
			    	 
//			    	 System.out.println(word_weight.weight);
//			    	 
//			    	 System.out.println("caonima"+weight_tuples.get(0).word);
//			         System.out.println("caonima"+weight_tuples.get(0).weight);
			     }
			     
			     //在这里考虑是否加入上次迭代时使用的模板到实体对中
			     
			     //保存计算完成的权重五元组
			     ArrayList<ArrayList> Tuple = new ArrayList<>();
	    		 Tuple.add(weight_tuples);
	    		 
	    		 
	    		 
//	    		 for(int t = 0; t < weight_tuples.size(); t++){
//	    		    System.out.println(weight_tuples.get(t).word);
//	    		    System.out.println(weight_tuples.get(t).weight);
//	    		 }
//	    		 System.out.println(Tuple);
			     
	    		 
	    		 
	    		 if(get_tuple_weight.getweightKey(Entity) != null){
	    			 get_tuple_weight.getweightKey(Entity).add(weight_tuples);			    	 
			     }else{
			    	 get_tuple_weight.putWeightTuples(Entity,Tuple);
			     }
			 }
			 
		 }
		 
		 GroupTuple group_tuple = new GroupTuple(); 
		 
		 
		// System.out.println("权重向量");
		 
		 it = get_tuple_weight.weight.entrySet().iterator();
		 while(it.hasNext()){
			 Entry entry = (Entry)it.next();
			
			 Entity = (String)entry.getKey();
			 //System.out.println(Entity);
			 
			 ArrayList<ArrayList> FiveTupleCollection = new ArrayList<>();
			 FiveTupleCollection = (ArrayList<ArrayList>) entry.getValue();
			 //System.out.println(FiveTupleCollection);
			 
			 //2016/11/29改动
			 //if(Entity.equals("arthur Conan Doyle,adventure of sherlock holme")){
			 //2016/11/29改动结束
		     group_tuple.GetGroupTuple(Entity,FiveTupleCollection);
			 //2016/11/29改动
		     //}
			 //2016/11/29改动结束
		 }
	     
		
		 
	 }
		   
}
