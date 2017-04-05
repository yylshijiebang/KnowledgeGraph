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
import ExtractEntity.TempEntityStore;
import FeatureExtract.FeatureStore;
import StaticConstant.AdjustParameter;

public class FormPattern {
	 float threhold = 0.79f;
	 //加一个调整因子，每次调整阈值
	// float deltathrehold = 0f;
	 //将抽取的候选实体保存到文本中
	 public void WriteIntoFile(String glaucoma){
		    //写入文件
		    BufferedWriter bw = null;
		    FileWriter fileWriter=null;
			try{
				fileWriter = new FileWriter("D:/YYLSoftware/Program/YYLRelationshipExtraction/CandidateEntityPattern.txt",true);
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
		 TempEntityStore tempEntityStore = new TempEntityStore();
		 
		 //System.out.println("FormPatternTempPattern:" + group_pattern.temp_pattern.size());
		 //System.out.println("FormPatternClusterSentenceVector:" + group_pattern.ClusterSentenceVector.toString());
		 
		 Iterator it = group_tuple.ClusterSentenceVector.entrySet().iterator();
		 String Entity = "";
 		 while(it.hasNext()){
 			 Entry entry = (Entry)it.next();
 			 
 			 Entity = (String)entry.getKey();
 			 
 			 //System.out.println("FromPatternEntity:"+Entity);
 			 
 			 ArrayList<FeatureStore> GroupFiveTupleCollection = new ArrayList<>();
 			 GroupFiveTupleCollection = (ArrayList<FeatureStore>) entry.getValue();
 			 
 			 //System.out.println("FromPatternGroupFiveTupleCollection:"+GroupFiveTupleCollection.toString());
 			 
			 //保存中心向量
			 ArrayList<Float> center_vector = new ArrayList<>();
			 center_vector.clear();
			 String center_sentence = "";
			 
			 //读取一个聚类中的向量，求出中心向量
 			 for(int j = 0; j < GroupFiveTupleCollection.size(); j++){
 				 //ArrayList<Float> temp_vector
 				 if(center_vector.isEmpty()){
 					 for(int t = 0; t < GroupFiveTupleCollection.get(j).vector.size(); t++)
 					    center_vector.add(GroupFiveTupleCollection.get(j).vector.get(t));
 				 }else{
	 				 for(int t = 0; t < GroupFiveTupleCollection.get(j).vector.size(); t++){
	 					 center_vector.set(t, center_vector.get(t) + GroupFiveTupleCollection.get(j).vector.get(t));
	 				 }
 				 }
 			 }
 			 
 			 for(int j = 0; j < center_vector.size(); j++){//求得中心向量
 				center_vector.set(j, center_vector.get(j)/(float)GroupFiveTupleCollection.size());
 			 }
 			 
 			 //每个该组中每个向量和中心向量比较，求出最相似的向量，保存其句子五院组
 			 MatchVectorSim matchVectorSim = new MatchVectorSim();
 			 float SimValue = 0f, Max = 0f;
 			 int flag = 0;
 			 for(int j = 0; j < GroupFiveTupleCollection.size(); j++){
 				SimValue = matchVectorSim.VectorSim(center_vector, GroupFiveTupleCollection.get(j).vector);
 				if(SimValue > Max){
 					Max = SimValue;
 					flag = j;
 				}
 			 }
 			 center_sentence = GroupFiveTupleCollection.get(flag).sentence;
 			 
 			 //2017.3.22修改本次迭代产生模板和上一次迭代产生的模板匹配，如果满足阈值条件，则用此次产生的模板进行实体抽取
 			 FeatureStore temp_pattern = new FeatureStore();//临时模板
		     temp_pattern.sentence = center_sentence;
		     temp_pattern.vector = center_vector;//这里本应该一个一个添加，直接赋值可能会出问题
			 ArrayList<FeatureStore> tuple = new ArrayList<>();
 			 if(tempEntityStore.getPattern(Entity.substring(0, Entity.indexOf(AdjustParameter.RecognizeFlag))) != null){//找到上一次迭代中产生这个实体的模板
 				 ArrayList<FeatureStore> last_pattern = new ArrayList<>();
 				 last_pattern.clear();
 				 last_pattern = TempEntityStore.temp_entity.get(Entity.substring(0, Entity.indexOf(AdjustParameter.RecognizeFlag)));
 			     int judge = 0;
 				 for(int g = 0; g < last_pattern.size(); g++){
 					if(matchVectorSim.VectorSim(last_pattern.get(g).vector, temp_pattern.vector) > threhold){//如果本次实体产生的模板和上一次迭代产生该实体的模板只要有一个模板向量相似度超过阈值
 						judge ++;
 						break;
 					}
 				 }
 				 
 				 //System.out.println("judge");
 				 
 				 if(judge == last_pattern.size()){
 					 
 					 //System.out.println("相等");
					 
 					 if(group_pattern.temp_pattern.get(Entity) != null){
					    group_pattern.temp_pattern.get(Entity).add(temp_pattern);			    	 
				     }else{
				    	 tuple.add(temp_pattern);
				    	 group_pattern.temp_pattern.put(Entity,tuple);
				     }
 				 }
 			 }else{
 				 if(group_pattern.temp_pattern.get(Entity) != null){
				    group_pattern.temp_pattern.get(Entity).add(temp_pattern);			    	 
			     }else{
			    	 tuple.add(temp_pattern);
			    	 group_pattern.temp_pattern.put(Entity,tuple);
			     }
 			 }
 			 //2017.3.22修改结束
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
