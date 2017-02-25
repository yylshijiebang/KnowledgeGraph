package ExtractEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class TempEntityStore {
	public static HashMap<String,ArrayList<ArrayList>> temp_entity = new HashMap<>();//实体关系对带单词权重的五元组
	public static ArrayList<String> entity_pairs = new ArrayList<>();//关系实体对，不带单词权重的五元组
	
	public static Set<String> string_tuple = new HashSet<>(); //五元组的字符串形式
	//存取模式
    public void putPattern(String key, ArrayList<ArrayList> Tuple){
  	     temp_entity.put(key, Tuple);
    }
    public  ArrayList<ArrayList> getPattern(String key){
  	     return temp_entity.get(key);
    }
}
