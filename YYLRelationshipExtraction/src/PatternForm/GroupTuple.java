package PatternForm;

import java.awt.datatransfer.FlavorEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

import ExtractEntity.TempEntityStore;
import FeatureExtract.FeatureStore;
import StaticConstant.AdjustParameter;
import TupleWeightForm.TuplesStore;


public class GroupTuple {
	   float threhold = 0.77f;//当聚类的时候大于阈值，说明才符合聚类的条件
       public void GetGroupTuple(String Entity, ArrayList<FeatureStore> FiveTupleCollection){
    	   
    	     // Iterator it = tuples.weight.entrySet().iterator();
    	     int k = 0;//五元组分组后的索引
  			     
		     GroupTupleStore group_tuple = new GroupTupleStore();//保存聚好类的五元组
		     
		     //System.out.println("GroupTupleClusterSentenceVector:"+group_tuple.ClusterSentenceVector.size());
		     //System.out.println("GroupTupleTempPattern:"+group_tuple.temp_pattern.size());

		     //对属于同一个实体关系对的五元组进行分组
		     for(int j = 0; j < FiveTupleCollection.size(); j++){
		    	 //读取元组向量
		    	 FeatureStore featureStore = new FeatureStore();
		    	 featureStore = FiveTupleCollection.get(j);
		    	 
		    	 if(group_tuple.ClusterSentenceVector.isEmpty()){//不存在向量分类
		         	ArrayList<FeatureStore> SenVecList = new ArrayList<>(); 
		         	SenVecList.add(featureStore);
		         	group_tuple.ClusterSentenceVector.put(Entity+AdjustParameter.RecognizeFlag+String.valueOf(k),SenVecList);
		         }else{
		         	String flag = "";
		         	float Max = -1f;
		         	Iterator iterator = group_tuple.ClusterSentenceVector.entrySet().iterator();
		         	
		         	while(iterator.hasNext()){//遍历每个类
		         		Entry group_entry = (Entry)iterator.next();
		   			    //分组的索引
		   			    String group_k = (String)group_entry.getKey();
		   			    
		   			    //每一组包含：带句子的特征向量
		   			    ArrayList<FeatureStore> group_senvec = (ArrayList<FeatureStore>)group_entry.getValue();
		   			    
		   			    //System.out.println("GroupTupleGroup_K:"+group_k+":"+group_senvec.size());
		   			    
		   			    MatchVectorSim match_tuple = new MatchVectorSim();
		   			    float Average = 0.0f;
		   			    for(int i = 0; i < group_senvec.size(); i++){
		   			    	float match_degree = match_tuple.VectorSim(featureStore.vector,group_senvec.get(i).vector);
		   			    	
		   			    	//System.out.println("GroupTuplematch_degree:" + match_degree);
		   			    	
		   			    	if(match_degree < threhold){//如果新来的句子特征向量和该类中某一个句子特征向量相似度低于阈值
		   			    		Average = 0.0f;
		   			    		break;
		   			    	}else{
		   			    		Average += match_degree;
		   			    	}
		   			    }
		   			   // System.out.println("GroupTuple平均值:" + Average/(float)group_senvec.size());
		   			    if(Average/(float)group_senvec.size() > threhold){
		   			    	if(Average/(float)group_senvec.size() < Max){
		   			    		;
		   			    	}else{
		   			    		 Max = Average/group_senvec.size();
		  			    		 flag = group_k; 
		   			    	}
		   			    }
		         	}
		         	
		         	if(!"".equals(flag) && !String.valueOf(Max).equals(String.valueOf(-1f))){//如果属于已经存在的类
		         		group_tuple.ClusterSentenceVector.get(flag).add(featureStore);
		    		}else{//划分到新的类
		    		    	k ++;//产生新的类
		    			    ArrayList<FeatureStore> Tuple = new ArrayList<>();
		 	    		    Tuple.add(featureStore);
		 	    		    group_tuple.ClusterSentenceVector.put(Entity+AdjustParameter.RecognizeFlag+String.valueOf(k),Tuple);
		    		}
		         }
		    	 
		    	 
		     }
		
  		   //}
		     
		    
    	   
		     //测试数据
//  		   System.out.println("group finish");
//  		 Iterator it = group_tuple.weight.entrySet().iterator();
//  		 while(it.hasNext()){
//  			 Entry entry = (Entry)it.next();
//  			
//  			 Entity = (String)entry.getKey();
//  			 System.out.println(Entity);
//  			 
//  			 ArrayList<ArrayList> GroupFiveTupleCollection = new ArrayList<>();
//  			 GroupFiveTupleCollection = (ArrayList<ArrayList>) entry.getValue();
//  			 System.out.println(GroupFiveTupleCollection);
//  			 
//  			 for(int j = 0; j < GroupFiveTupleCollection.size(); j++){
//  				ArrayList<WeightTuples> test = GroupFiveTupleCollection.get(j);
//  				System.out.println("=====");
//  				for(int t = 0; t < test.size(); t++){
//  					System.out.println(test.get(t).word);
//					System.out.println(test.get(t).weight);
//  				}
//  			 }
//  			 
//  		 }
  		 
  		 FormPattern formPattern = new FormPattern();
	     formPattern.GetPattern(group_tuple);
  		   
       }
}
