package ExtractEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import FeatureExtract.FeatureStore;

public class TempEntityStore {
	public static HashMap<String,ArrayList<FeatureStore>> temp_entity = new HashMap<>();//实体关系对带五元组句子和五元组向量
	public static ArrayList<String> entity_pairs = new ArrayList<>();//关系实体对
	public static ArrayList<String> temp_entity_pairs = new ArrayList<>();//临时保存产生的新实体对
	
	public static Set<String> string_tuple = new HashSet<>(); //五元组的字符串形式
	//存取模式
    public void putPattern(String key, ArrayList<FeatureStore> Tuple){
  	     temp_entity.put(key, Tuple);
    }
    public  ArrayList<FeatureStore> getPattern(String key){
  	     return temp_entity.get(key);
    }
}
