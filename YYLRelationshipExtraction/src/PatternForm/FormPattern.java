package PatternForm;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.print.attribute.Size2DSyntax;

import ExtractEntity.ExtractCandidateEntity;
import StaticConstant.AdjustParameter;
import TupleWeightForm.WeightTuples;

public class FormPattern {
	
	 //将抽取的候选实体保存到文本中
	 public void WriteIntoFile(String glaucoma){
		    //写入文件
		    BufferedWriter bw = null;
		    FileWriter fileWriter=null;
			try{
				fileWriter = new FileWriter("E:/北航文件/编程程序/BuaaSnowball/CandidateEntityPattern.txt",true);
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
	
    //模板形成
	public void GetPattern(GroupTupleStore group_tuple){
		 
		 GroupTupleStore group_pattern = new GroupTupleStore();//保存聚好类的模板
		 Iterator it = group_tuple.weight.entrySet().iterator();
		 String Entity = "";
 		 while(it.hasNext()){
 			 Entry entry = (Entry)it.next();
 			 
 			 Entity = (String)entry.getKey();
 			 //System.out.println("FromPattern:"+Entity);
 			 
 			 ArrayList<ArrayList> GroupFiveTupleCollection = new ArrayList<>();
 			 GroupFiveTupleCollection = (ArrayList<ArrayList>) entry.getValue();
 			 
 			 double prefix_centroid = 0.0;
			 double middle_centroid = 0.0;
			 double suffix_centroid = 0.0;
			 
			 ArrayList<Double> prefix = new ArrayList<>();
			 ArrayList<Double> middle = new ArrayList<>();
			 ArrayList<Double> suffix = new ArrayList<>();
			 
 			 for(int j = 0; j < GroupFiveTupleCollection.size(); j++){
 				 
 				 double prefix_temp = 0.0;
 				 double middle_temp = 0.0;
 				 double suffix_temp = 0.0;
 				 
 				 ArrayList<WeightTuples> five_tuple = GroupFiveTupleCollection.get(j);
 				 //System.out.println("=====");
 				 
 				 for(int t = 0; t < AdjustParameter.PreWordNum; t++){
 					 prefix_temp += five_tuple.get(t).weight;
 				     prefix_centroid += five_tuple.get(t).weight;  	 
 				 }
 				 
 				 for(int t = AdjustParameter.PreWordNum; t < five_tuple.size()-AdjustParameter.SufWordNUm; t++){
 					 middle_temp +=five_tuple.get(t).weight;
 				     middle_centroid += five_tuple.get(t).weight;	 
 				 }
 				 
 				 for(int t = five_tuple.size()-AdjustParameter.SufWordNUm; t < five_tuple.size(); t++){
 					 suffix_temp += five_tuple.get(t).weight;
 					 suffix_centroid += five_tuple.get(t).weight;
 				 }
 				 
 				 prefix.add(prefix_temp);
 				 middle.add(middle_temp);
 				 suffix.add(suffix_temp);
 			 }
 			 
 			 prefix_centroid = prefix_centroid/prefix.size();
 			 middle_centroid = middle_centroid/middle.size();
 			 suffix_centroid = suffix_centroid/suffix.size();
 			 
 			
 			 ArrayList<WeightTuples> temp_pattern = new ArrayList<>();//临时模板
 			 ArrayList<ArrayList> tuple = new ArrayList<>();
 			 
 			 //前缀的中心向量
 			 double min = 1.0;
 			 int flag= -1;
 			 for(int j = 0; j < prefix.size(); j++){
 				 if(Math.abs(prefix.get(j)-prefix_centroid) < min){
 					 flag = j;
 					 min = Math.abs(prefix.get(j)-prefix_centroid);
 				 }
 			 }
 			 
 			 for(int j = 0; j < AdjustParameter.PreWordNum; j++){
 				 temp_pattern.add((WeightTuples)GroupFiveTupleCollection.get(flag).get(j));
 			 }
 			 
 			 //中缀的中心向量
 			 min = 1.0;
			 flag= -1;
			 for(int j = 0; j < middle.size(); j++){
				 if(Math.abs(middle.get(j)-middle_centroid) < min){
					 flag = j;
					 min = Math.abs(middle.get(j)-middle_centroid);
				 }
			 }
			 
			 for(int j = AdjustParameter.PreWordNum; j < GroupFiveTupleCollection.get(flag).size() - AdjustParameter.SufWordNUm; j++){
				 temp_pattern.add((WeightTuples)GroupFiveTupleCollection.get(flag).get(j));
			 }
 			 
 			 //后缀的中心向量
			 min = 1.0;
			 flag= -1;
			 for(int j = 0; j < suffix.size(); j++){
				 if(Math.abs(suffix.get(j)-suffix_centroid) < min){
					 flag = j;
					 min = Math.abs(suffix.get(j)-suffix_centroid);
				 }
			 }
			 
			 for(int j = GroupFiveTupleCollection.get(flag).size() - AdjustParameter.SufWordNUm; j < GroupFiveTupleCollection.get(flag).size(); j++){
				 temp_pattern.add((WeightTuples)GroupFiveTupleCollection.get(flag).get(j));
			 }
 			 
			 
 			 if(group_pattern.getPattern(Entity) != null){
   			    group_pattern.getPattern(Entity).add(temp_pattern);			    	 
		     }else{
		    	 tuple.add(temp_pattern);
		    	 group_pattern.putPattern(Entity,tuple);
		     }
 			 
 		 }
    	 
 		 //测试数据
 		  //测试数据
//		   System.out.println("pattern finish");
//		   it = group_pattern.temp_pattern.entrySet().iterator();
//		 while(it.hasNext()){
//			 Entry entry = (Entry)it.next();
//			
//			 Entity = (String)entry.getKey();
//			 System.out.println(Entity);
//			 WriteIntoFile(Entity);
//			 
//			 ArrayList<ArrayList> GroupFiveTupleCollection = new ArrayList<>();
//			 GroupFiveTupleCollection = (ArrayList<ArrayList>) entry.getValue();
//			 System.out.println(GroupFiveTupleCollection);
//			 
//			 for(int j = 0; j < GroupFiveTupleCollection.size(); j++){
//				ArrayList<WeightTuples> test = GroupFiveTupleCollection.get(j);
//				String test_string = "";
//				System.out.println("=====");
//				test_string +="{";
//				for(int t = 0; t < test.size(); t++){
//					test_string += "<"+test.get(t).word+","+test.get(t).weight+">"+",";
//					System.out.println(test.get(t).word);
//					System.out.println(test.get(t).weight);
//				}
//				test_string +="}";
//				//将模板写入文件中
//				WriteIntoFile(test_string);
//			 }
//			 
//		 }
		 //测试数据结束
 		 
		 //调用函数抽取种子
 		 ExtractCandidateEntity extractCandidateEntity = new ExtractCandidateEntity();
 		 extractCandidateEntity.GetCandidateEntity(group_pattern);
		 
    }
}
