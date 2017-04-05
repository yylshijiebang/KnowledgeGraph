package TupleWeightForm;

import java.util.ArrayList;
import java.util.HashMap;

import FeatureExtract.FeatureStore;

public class TuplesStore {
     public HashMap<String,ArrayList<FeatureStore>> Tuples  = new HashMap<>();//按照实体关系对，保存携带句子和五元组向量
     
     //存取五元组
     public void putTuples(String key, ArrayList<FeatureStore> Tuple){
    	 Tuples.put(key, Tuple);
     }
     public ArrayList<FeatureStore> getKey(String key){
    	 return Tuples.get(key);
     }
}
