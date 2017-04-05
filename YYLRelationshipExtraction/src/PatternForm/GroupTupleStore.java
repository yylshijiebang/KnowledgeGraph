package PatternForm;

import java.util.ArrayList;
import java.util.HashMap;

import FeatureExtract.FeatureStore;

public class GroupTupleStore {
	//public HashMap<String,ArrayList<ArrayList>> weight = new HashMap<>();//实体关系对带单词权重的五元组
	 
	public HashMap<String,ArrayList<FeatureStore>> ClusterSentenceVector = new HashMap<>();//实体关系对，聚好类的五元组向量保存
	public HashMap<String,ArrayList<FeatureStore>> temp_pattern = new HashMap<>();//实体关系对，五元组模板
	
//	class GroupTupleStore{
//		
//	}
	//存取带权重的五元组
   
    
}
