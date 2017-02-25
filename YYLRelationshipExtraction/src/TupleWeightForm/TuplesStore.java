package TupleWeightForm;

import java.util.ArrayList;
import java.util.HashMap;

public class TuplesStore {
     public HashMap<String, ArrayList<ArrayList>> Tuples  = new HashMap<>();//按照实体关系对，保存五元组
     
     public HashMap<String,ArrayList<ArrayList>> weight = new HashMap<>();//实体关系对带单词权重的五元组
     
     static public ArrayList<String> Text_sentence = new ArrayList<>();//保存文本句子
     
     //存取五元组
     public void putTuples(String key, ArrayList<ArrayList> Tuple){
    	 Tuples.put(key, Tuple);
     }
     public ArrayList<ArrayList> getKey(String key){
    	 return Tuples.get(key);
     }
     
     //存取带权重的五元组
     public void putWeightTuples(String key, ArrayList<ArrayList> Tuple){
    	 weight.put(key, Tuple);
     }
     public ArrayList<ArrayList> getweightKey(String key){
    	 return weight.get(key);
     }
     
     //存取文本句子
     public void putSentence(String sentence){
		 Text_sentence.add(sentence);
	 }
//     public String getSentence(String sentence){
//    	 return Text_sentence.get(index)
//     }
}
