package PatternForm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

import StaticConstant.AdjustParameter;
import TupleWeightForm.TuplesStore;
import TupleWeightForm.WeightTuples;

public class GroupTuple {
	   double threhold = 0.6;//当聚类的时候大于阈值，说明才符合聚类的条件
       public void GetGroupTuple(String Entity, ArrayList<ArrayList> FiveTupleCollection){
    	     //String Entity = "";//实体关系对
    	   
    	     // Iterator it = tuples.weight.entrySet().iterator();
    	     int k = 0;//五元组分组后的索引
  			     
		     GroupTupleStore group_tuple = new GroupTupleStore();//保存聚好类的五元组
		     //对属于同一个实体关系对的五元组进行分组
		     for(int i = 0; i < FiveTupleCollection.size(); i++){
		    	 ArrayList<WeightTuples> five_tuple = new ArrayList<>();
		    	 five_tuple = FiveTupleCollection.get(i);
		       
		    	 //如果不存在已经分好类的元组
		    	 if(group_tuple.weight.size() == 0){
		    		
		    		ArrayList<ArrayList> Tuple = new ArrayList<>();
		    		Tuple.add(five_tuple);
		    		group_tuple.putWeightTuples(Entity+AdjustParameter.RecognizeFlag+String.valueOf(k),Tuple);
		    	 }
		    	 else{
		    		 String flag = "";
		    		 double Max = -1;
		    		 Iterator group = group_tuple.weight.entrySet().iterator();
		    		 //int t = 0;
		    		 while(group.hasNext()){
		    			  
		    			  Entry group_entry = (Entry)group.next();
		    			  //分组的索引
		    			  String group_k = (String)group_entry.getKey();
		    			  
		    			  //组内的五元组
		    			  ArrayList<ArrayList> GroupFiveTupleCollection = new ArrayList<>();
		  			      GroupFiveTupleCollection = (ArrayList<ArrayList>) group_entry.getValue();
		  			      double Average = 0.0;
		  			      MatchTuple match_tuple = new MatchTuple(); 
		  			      for(int j = 0; j < GroupFiveTupleCollection.size(); j++){
		  			    	  double match_degree = match_tuple.Match(five_tuple,GroupFiveTupleCollection.get(j));
		  			    	  if(Double.toString(match_degree).equals("0.0")){
		  			    		  Average = 0.0;
		  			    		  //System.out.println("SB");
		  			    		  break;
		  			    	  }
		  			    	  else{
		  			    		  Average += match_degree;
		  			    	  }
		  			      }
		  			      
		  			      //System.out.println(Average/GroupFiveTupleCollection.size());
		  			      
		  			      if(Average/GroupFiveTupleCollection.size() > threhold){
		  			    	  if(Average/GroupFiveTupleCollection.size() < Max){
		  			    		  ;
		  			    	  } 
		  			    	  else{
		  			    		  Max = Average/GroupFiveTupleCollection.size();
		  			    		  flag = group_k; 
		  			    	  }
		  			      }
		    		 }
		    		 
		    		 if(!"".equals(flag) && Max != -1){
		    			 group_tuple.getweightKey(flag).add(five_tuple);
		    		 }else{
		    			 k++;
		    			 ArrayList<ArrayList> Tuple = new ArrayList<>();
			    		 Tuple.add(five_tuple);
			    		 group_tuple.putWeightTuples(Entity+AdjustParameter.MidFactor+String.valueOf(k),Tuple);
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
