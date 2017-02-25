package PatternForm;

import java.util.ArrayList;

import StaticConstant.AdjustParameter;
import TupleWeightForm.WeightTuples;

//计算元组之间的匹配度
public class MatchTuple {
	 double match_threhold = 0.6;
     public double Match(ArrayList<WeightTuples> tuple1, ArrayList<WeightTuples> tuple2){
    	 double match_degree = 0.0;
    	 double prefix = 0.0;
    	 double middle = 0.0;
    	 double suffix = 0.0;
    	 
//    	 System.out.println("test weight");
    	 for(int i = 0; i < AdjustParameter.PreWordNum; i++){
    		 for(int j = 0; j < AdjustParameter.PreWordNum; j++){
    			 prefix +=  tuple1.get(i).weight * tuple2.get(j).weight;
//    			 System.out.println(tuple1.get(i).weight);
//    			 System.out.println(tuple2.get(j).weight);
    		 }
    	 }
    	 
    	// System.out.println(prefix);
    	 
    	 for(int i = AdjustParameter.PreWordNum; i < tuple1.size()-AdjustParameter.SufWordNUm; i++){
    		 for(int j = AdjustParameter.PreWordNum; j < tuple2.size()-AdjustParameter.SufWordNUm; j++){
    			 middle += tuple1.get(i).weight * tuple2.get(j).weight;
    		 }
    	 }
    	 
    	// System.out.println(middle);
    	 
    	 for(int i = tuple1.size() - AdjustParameter.SufWordNUm; i < tuple1.size(); i++){
    		 for(int j = tuple2.size() - AdjustParameter.SufWordNUm; j < tuple2.size(); j++){
    			suffix += tuple1.get(i).weight * tuple2.get(j).weight;
    		 }
    	 }
    	 
    	 //System.out.println(suffix);
    	 
    	 match_degree = prefix + middle + suffix;
    	 if(match_degree < 0.5){
    		 return 0.0;
    	 }
    	 return match_degree;
     }
}
