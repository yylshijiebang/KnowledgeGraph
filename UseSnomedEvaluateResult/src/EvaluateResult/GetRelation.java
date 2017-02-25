package EvaluateResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class GetRelation {
	 public HashMap<Integer,ArrayList<String>> ConceptRelation = new HashMap<>();
	 public HashMap<Integer,ArrayList<String>> ReadRelaiton(){
    	 try { 
	            Scanner in = new Scanner(new File("E:/北航文件/编程程序/UseSnomedEvaluateResult/Relationships.txt"),"UTF-8"); 
             
	            int l = 0; 
	            //读取Concepts.txt
	            while (in.hasNextLine()) {
	                String glaucoma = in.nextLine();
	                l++;
	                if(l != 1)
	                    GetGlaucomaParameters(glaucoma,l);
//	                l++;
//	                if(l == 9){
//	                	break;
//	                }
	            } 
	            in.close();
	        } catch (FileNotFoundException e) { 
	            e.printStackTrace(); 
	        } 
    	 System.out.println("关系读取结束");
    	 return ConceptRelation;
     }
     
     public void GetGlaucomaParameters(String glaucoma,int l){
//		   System.out.println("glaucoma: "+glaucoma);
		   //读取出关系ID
		   String concept1 = ""; //概念1
		   String relation = ""; //关系
		   String concept2 = "";//概念2
		   int SpaceFirst = glaucoma.indexOf("\t");
		   int SpaceSecond = glaucoma.indexOf("\t",SpaceFirst+1);
		   int SpaceThird = glaucoma.indexOf("\t",SpaceSecond+1);
		   int SpaceForth = glaucoma.indexOf("\t",SpaceThird+1);
		   
		   ArrayList<String> teStrings = new ArrayList<>();
		   
		   //System.out.println(SpaceFirst+" "+SpaceSecond);
		   concept1 = glaucoma.substring(SpaceFirst+1,SpaceSecond);
		   teStrings.add(concept1);
		   test.SameConceptRelationShip.put(concept1, l);
		   
		   relation = glaucoma.substring(SpaceSecond+1,SpaceThird);
		   teStrings.add(relation);
		   test.SameConceptRelationShip.put(relation, l);
		   
		   concept2 = glaucoma.substring(SpaceThird+1,SpaceForth);
		   teStrings.add(concept2);
		   test.SameConceptRelationShip.put(concept2, l);
		   
		   ConceptRelation.put(l,teStrings);
		   //System.out.println(concept1+" "+relation+" "+concept2);
		  

	  }
     
}
