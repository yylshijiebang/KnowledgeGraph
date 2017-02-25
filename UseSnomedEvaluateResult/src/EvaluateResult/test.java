package EvaluateResult;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class test {
	    //将实体对保存到变量中
	    public static ArrayList<String> EntityList = new ArrayList<>();
	    public static HashMap<String, String> ConceptIdSet = new HashMap<>();
	    public static HashMap<Integer,ArrayList<String>> ConceptRelationShip = new HashMap<>();
	    public static HashMap<String, Integer> SameConceptRelationShip = new HashMap<>();
	    public static void GetSentence(String Sentence){
			//System.out.println(Sentence);
	    	
		    int i;
		    char c;
		    String word = "";
		    String word_derivative = "";
		    WordNetHelper getstem = new WordNetHelper();
		    //初始化一个字符串变量，保存派生的词组
		    StringBuffer segBuffer_derivative = new StringBuffer();
		    
		    for(i = 0; i < Sentence.length(); i++){
		    	c = Sentence.charAt(i);
		    	if(c == ' '||i == Sentence.length()-1||NonChiSplit.isCharSeperator(c)){
		    		 if(c == ' '){
			    		  word_derivative = getstem.findStem(word);
			    		  segBuffer_derivative.append(word_derivative+" ");
		    		 }else if(NonChiSplit.isCharSeperator(c)){
		    			  word_derivative = getstem.findStem(word);
			    		  segBuffer_derivative.append(word_derivative);
			    		 // System.out.println(segBuffer_derivative);
			    		  EntityList.add(segBuffer_derivative.toString());
				    	  segBuffer_derivative = new StringBuffer();
		    		 }else if(i == Sentence.length()-1){
		    			  word += c;
		    			  word_derivative = getstem.findStem(word);
		    			  segBuffer_derivative.append(word_derivative);
		    			  EntityList.add(segBuffer_derivative.toString());
		    			 // System.out.println(segBuffer_derivative);
				    	  segBuffer_derivative = new StringBuffer();
		    		 }
		    		 word = "";
		    		 
		    	}else{
		    		word += c; 
		    	}
		    }
		}
	    //程序的主入口
		public static void main(String[] args){
		
			//读入抽取出的实体对
	        try {
	        	String filepath = "E:/北航文件/编程程序/UseSnomedEvaluateResult/entity.txt";
				ReadFile readFile = new ReadFile();
				if(readFile.readfile(filepath)){
					System.out.println("entity finish");
				}
	        } catch (Exception e) {  
	        	System.out.println("fail");
	        	e.printStackTrace(); 
	        }  
	        //读取概念ID
	        System.out.println("读取概念ID");
	        GetConceptID getConceptID = new GetConceptID();
	        ConceptIdSet = getConceptID.ReadConcept();
	        //读取relation
	        System.out.println("读取relation");
	        GetRelation getRelation = new GetRelation();
	        ConceptRelationShip = getRelation.ReadRelaiton();
	        MatchEntityPairs();
//	        for(int i = 0; i < EntityList.size(); i = i + 2){
//	        	System.out.println(EntityList.size());
//	        }
	        
		}
		
		//匹配实体对，看实体对是否有关系
	    public static void  MatchEntityPairs(){
   	         int i;
   	         int certify = 0;
   	         for(i = 0; i < EntityList.size(); i = i+2){
   	        	 String conceptId1 = "";
   	        	 String conceptId2 = "";
   	        	 int flag = 0;
   	        	 String concept_entity1 = EntityList.get(i);
   	        	 String concept_entity2 = EntityList.get(i+1);
   	        	 ArrayList<String> arr_entity1 = valueGetKey(concept_entity1);
   	        	 ArrayList<String> arr_entity2 = valueGetKey(concept_entity2);
   	        	 if(!arr_entity1.isEmpty() && !arr_entity2.isEmpty()){
   	        		//certify ++;
   	        		for(int j = 0; j<arr_entity1.size(); j++) {  
    			        //System.out.println("关系名称"+arr.get(j));
    			        conceptId1 = arr_entity1.get(j);
    			        for(int t = 0; t < arr_entity2.size();t++){
    			        	conceptId2 = arr_entity2.get(t);
    			        	if(SameConceptRelationShip.get(conceptId1).equals(SameConceptRelationShip.get(conceptId2))){//如果两个concept有关系
      	        				 String relation = "";
      	        				 relation = ConceptRelationShip.get(SameConceptRelationShip.get(conceptId1)).get(1);
      	        				 flag = 1;
      	        				 certify ++;
//      	        				 //hashmap通过value反查key
//      	        				 ArrayList<String> arr = valueGetKey(relation);  
//      	        			     if(!arr.isEmpty()) {  
//      	        			        for(int j=0; j<arr.size(); j++) {  
//      	        			           //System.out.println("关系名称"+arr.get(j));
      	        			     WriteIntoFile("<"+EntityList.get(i)+">"+"<"+ConceptIdSet.get(relation)+">"+"<"+EntityList.get(i+1)+">");
      	        			     WriteIntoFile("<"+conceptId1+">"+"<"+relation+">"+"<"+conceptId2+">");
//      	        			        }   
      	        			 }
    			        	if(flag == 1){
    			        		break;
    			        	}
    			        }
    			        if(flag == 1){
			        		break;
			        	}
    			    }

   	        	 }
//   	        	 if(ConceptIdSet.get(EntityList.get(i)) != null){
//   	        		 conceptId1 = ConceptIdSet.get(EntityList.get(i));//第一个conceptId
//   	        		 if(ConceptIdSet.get(EntityList.get(i+1)) != null){
//   	        			 conceptId2 = ConceptIdSet.get(EntityList.get(i+1));//第二个conceptId
//   	        			 if(SameConceptRelationShip.get(conceptId1).equals(SameConceptRelationShip.get(conceptId2))){//如果两个concept有关系
//   	        				 String relation = "";
//   	        				 relation = ConceptRelationShip.get(SameConceptRelationShip.get(conceptId1)).get(1);
//   	        				 certify ++;
//   	        				 //hashmap通过value反查key
//   	        				 ArrayList<String> arr = valueGetKey(relation);  
//   	        			     if(!arr.isEmpty()) {  
//   	        			        for(int j=0; j<arr.size(); j++) {  
//   	        			           //System.out.println("关系名称"+arr.get(j));
//   	        			           WriteIntoFile("<"+EntityList.get(i)+">"+"<"+arr.get(j)+">"+"<"+EntityList.get(i+1)+">");
//   	        			           WriteIntoFile("<"+conceptId1+">"+"<"+relation+">"+"<"+conceptId2+">");
//   	        			        }  
//   	        			     }  
//   	        			 }
//   	        		 }
//   	        	 }
   	         }
   	         System.out.println("准确的实体对"+certify);
   	         WriteIntoFile(String.valueOf(certify));
        }
	    //通过value反查key
	    public static ArrayList<String> valueGetKey(String value) {  
	        Set set = ConceptIdSet.entrySet();  
	        ArrayList<String> arr = new ArrayList<>();  
	        Iterator it = set.iterator();  
	        while(it.hasNext()) {  
	            Map.Entry entry = (Map.Entry)it.next();  
	            if(entry.getValue().equals(value)) {  
	               String s = (String)entry.getKey();  
	               arr.add(s);  
	            }  
	        }  
	        return arr;  
	  }  
	    
	  //将匹配到的单句子写入文件中
	  public static void WriteIntoFile(String glaucoma){
		    //写入文件
		    BufferedWriter bw = null;
		    FileWriter fileWriter=null;
			try{
			    fileWriter = new FileWriter("E:/北航文件/编程程序/UseSnomedEvaluateResult/SnomedEntityRelation.txt",true);
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
}
