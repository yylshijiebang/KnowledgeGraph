package PatternForm;

import java.util.ArrayList;
import java.util.HashMap;

public class GroupTupleStore {
	public HashMap<String,ArrayList<ArrayList>> weight = new HashMap<>();//实体关系对带单词权重的五元组
	
	public HashMap<String,ArrayList<ArrayList>> temp_pattern = new HashMap<>();//实体关系对带单词权重的模板
	
	//存取带权重的五元组
    public void putWeightTuples(String key, ArrayList<ArrayList> Tuple){
   	     weight.put(key, Tuple);
    }
    public  ArrayList<ArrayList> getweightKey(String key){
   	     return weight.get(key);
    }
    
    //存取模式
    public void putPattern(String key, ArrayList<ArrayList> Tuple){
  	     temp_pattern.put(key, Tuple);
    }
    public  ArrayList<ArrayList> getPattern(String key){
  	     return temp_pattern.get(key);
    }
}
