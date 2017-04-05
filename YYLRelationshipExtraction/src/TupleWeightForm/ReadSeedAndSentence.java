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
import FeatureExtract.FeatureStore;

import java.util.Scanner;

import PatternForm.GroupTuple;
import PatternForm.GroupTupleStore;
import PatternForm.MatchVectorSim;
import StaticConstant.AdjustParameter;
import StaticConstant.NonChiSplit;


public class ReadSeedAndSentence {
	 //保存种子实体关系对
	 TempEntityStore SeedList = new TempEntityStore();
	 
	//将抽取的候选实体保存到文本中
	 public void WriteIntoFile(String glaucoma){
		    //写入文件
		    BufferedWriter bw = null;
		    FileWriter fileWriter=null;
			try{
				fileWriter = new FileWriter("D:/YYLSoftware/Program/YYLRelationshipExtraction/CandidateEntityTuple.txt",true);
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
	 public void WriteIntoFile2(String glaucoma,int iterator_flag){
		    //写入文件
		    BufferedWriter bw = null;
		    FileWriter fileWriter=null;
			try{
				fileWriter = new FileWriter("D:/YYLSoftware/Program/YYLRelationshipExtraction/IteratorResult/CandidateEntityTuple"+String.valueOf(iterator_flag)+".txt",true);
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
	 public void GetSeed(String SeedFileName){
		  try{
			 //读取种子对到内存
		     Scanner EntityPairs = new Scanner(new File(SeedFileName),"UTF-8");
		     while (EntityPairs.hasNextLine()){
		    	 String Entity = EntityPairs.nextLine();
		    	 SeedList.entity_pairs.add(Entity);//将实体对保存到统一的静态变量中
		    	 SeedList.temp_entity_pairs.add(Entity);//临时保存每次新产生的实体对
		     }
		  }catch(Exception e){
			  e.printStackTrace();
		  }
		  System.out.println("种子对个数"+SeedList.entity_pairs.size());
		  
		  GetSentence();
	 }
	 
	 
     //读取内存中的句子，并抽取出符合条件的五元组
	 public void GetSentence(){
	   	 String Sentence = "";
         int i,j;
         int iterator_flag = 0;
         //程序进行迭代，抽取实体和模板
		 while(true){
			 //迭代次数
			 iterator_flag ++;
			 //2017/2/12修改，每次迭代保存本次迭代产生的五元组句子和五元组向量
			 TuplesStore TheTupleStore = new TuplesStore();
			 
			 //System.out.println("ReadSeedAndSentenceTuples:"+TheTupleStore.Tuples.size());
			 
			 //每次迭代时，初始化抽取元组的类
			 ExtractTuple extract_tuple = new ExtractTuple();
			 //2017/2/12修改结束
			
			 
			 //迭代抽取开始
		   	 for(i = 0; i < SeedList.temp_entity_pairs.size();i++){
		   		 for(j = 0; j < FeatureStore.SentenceVector.size(); j++){
		   			//抽取出实体对对应的五元组向量和句子
		   		    extract_tuple.ExtractTuples(j,SeedList.temp_entity_pairs.get(i).toString(),TheTupleStore);
		   	     }
		   	 }
		   	 
		   	 //System.out.println("ReadSeedAndSentence_temp_entity_pairs:"+ SeedList.temp_entity_pairs.toString());
		   	 
		   	 SeedList.temp_entity_pairs.clear();//在使用临时实体对完成五院组向量抽取后将其清空
		   	 
		   	 //System.out.println("ReadSeedAndSentence_temp_entity_pairs:"+ SeedList.temp_entity_pairs.size());
		   	 
		   	 
		     WaitForGroup(TheTupleStore);
		    
			 System.out.println("第几次迭代："+iterator_flag);
			
			//当迭代次数等于2的时候先保存一次，看看结果怎么样
            //if(iterator_flag == 2){
            	TempEntityStore entityStore2 = new TempEntityStore();
    			Iterator it2 = entityStore2.temp_entity.entrySet().iterator();
            	while(it2.hasNext()){
					 Entry entry = (Entry)it2.next();
					
					 String Entity = (String)entry.getKey();
					 //System.out.println(Entity);
					 
					 WriteIntoFile2(Entity,iterator_flag);
					 
					 
					 ArrayList<FeatureStore> GroupFiveTupleCollection = new ArrayList<>();
					 GroupFiveTupleCollection = (ArrayList<FeatureStore>) entry.getValue();
					// System.out.println(GroupFiveTupleCollection);
					 
					 for(j = 0; j < GroupFiveTupleCollection.size(); j++){
						FeatureStore test = new FeatureStore();
						test =	GroupFiveTupleCollection.get(j);
						String test_string = "";//五元组句子
						ArrayList<Float> test_vector = new ArrayList<>();//五元组向量
						test_string  = test.sentence;
						test_vector = test.vector;
						
						WriteIntoFile2("{" + test_string + "}" + "\n" + test_vector.toString(),iterator_flag);
					 }
			    }
            	System.out.println("迭代次数等于"+iterator_flag+"保存结束！");
		   // }
            //当迭代次数等于2的时候保存结束
			
			//如果迭代结束后实体对没有增加，则迭代结束 
            if(SeedList.temp_entity_pairs.size() == 0){
				//迭代结束
			    System.out.println("迭代次数："+iterator_flag);
			    
			    
//			    TempEntityStore entityStore2 = new TempEntityStore();
//    			Iterator it2 = entityStore2.temp_entity.entrySet().iterator();
//            	while(it2.hasNext()){
//					 Entry entry = (Entry)it2.next();
//					
//					 String Entity = (String)entry.getKey();
//					 //System.out.println(Entity);
//					 
//					 WriteIntoFile(Entity);
//					 
//					 
//					 ArrayList<FeatureStore> GroupFiveTupleCollection = new ArrayList<>();
//					 GroupFiveTupleCollection = (ArrayList<FeatureStore>) entry.getValue();
//					// System.out.println(GroupFiveTupleCollection);
//					 
//					 for(j = 0; j < GroupFiveTupleCollection.size(); j++){
//						FeatureStore test = new FeatureStore();
//						test =	GroupFiveTupleCollection.get(j);
//						String test_string = "";//五元组句子
//						ArrayList<Float> test_vector = new ArrayList<>();//五元组向量
//						test_string  = test.sentence;
//						test_vector = test.vector;
//						
//						WriteIntoFile("{" + test_string + "}" + "\n" + test_vector.toString());
//					 }
//					 
////					 for(j = 0; j < GroupFiveTupleCollection.size()-1; j++){
////						 for(int yyl = j+1; yyl < GroupFiveTupleCollection.size(); yyl ++){
////							MatchVectorSim YYlmatchVectorSim = new MatchVectorSim();
////							System.out.println(YYlmatchVectorSim.VectorSim(GroupFiveTupleCollection.get(j).vector, GroupFiveTupleCollection.get(yyl).vector));
////							
////							//WriteIntoFile(test_string +"\n"+ test_vector.toString());
////						 }
////					 }
//					 
//			    }
            	System.out.println("迭代结束，保存结束！");
				System.out.println("extract entity finish");
				break;
			}
            
//            SeedList.temp_entity_pairs.clear();//清楚临时变量中的
		}
	 }
	 
	 //准备调用元组分类程序进行分类
	 public  void WaitForGroup(TuplesStore tuplesStore){
		 int i,j;
		 //实体对
		 String Entity = "";
		 Iterator it = tuplesStore.Tuples.entrySet().iterator();
		// TuplesStore get_tuple_weight = new TuplesStore();
		 
		 //System.out.println("ReadSeedAndSentenceWaitForGroup:" +  tuplesStore.Tuples.size());
		 //System.out.println("ReadSeedAndSentenceWaitForGroup:" +  tuplesStore.Tuples.toString());
		 
		 GroupTuple group_tuple = new GroupTuple(); 
		 while(it.hasNext()){
			 Entry entry = (Entry)it.next();
			
			 Entity = (String)entry.getKey();
			 //System.out.println("ReadSeedAndSentenceEntity:"+Entity);
			 
			 ArrayList<FeatureStore> FiveTupleCollection = new ArrayList<>();
			 FiveTupleCollection = (ArrayList<FeatureStore>) entry.getValue();
			 //System.out.println(FiveTupleCollection);
			 //System.out.println("ReadSeedAndSentenceFiveTupleCollection:" +  FiveTupleCollection.toString());
			 
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
