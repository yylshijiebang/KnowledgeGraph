package ReadEntityPairs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class ExtractSeeds {
	String entity_start = "[entity_start]";
	String entity_end = "[entity_end]";
	
	//将匹配到的单词写入文件中
	public void WriteIntoFile(String glaucoma){
		    //写入文件
		    BufferedWriter bw = null;
		    FileWriter fileWriter=null;
			try{
				fileWriter = new FileWriter("E:/北航文件/编程程序/YYLRelationSeedExtraction/SeedRelation.txt",true);
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
	
	//读取带标签句子中的实体对，得到种子
    public void ReadSentenceLabel(String FileName){
    	 HashMap<String, Integer>   EntityPairs = new HashMap<>();//每个实体对对应的频数
    	 try{
		     //Scanner reader = new Scanner(new File(FileName),"UTF-8");
		     int i = 0;
		     Reader reader = null;
             //读取txt文件中的内容
             System.out.println("以字符为单位读取文件内容，一次读一个字节：");  
             // 一次读一个字符  
             reader = new InputStreamReader(new FileInputStream(FileName));  
             String Sentence = ""; 
             int tempchar = -1;  
             while ((tempchar = reader.read()) != -1) {  
                 // 对于windows下，\r\n这两个字符在一起时，表示一个换行。  
                 if((char) tempchar == '\n'){
                	 i++;
                	 Sentence = Sentence.substring(0,Sentence.length()-1);
                	 //System.out.println(Sentence);
                	 //找到句子中的实体位置
	   		    	 int FirstEntityLocateStart = -1, FirstEntityLocateEnd = -1, SecondEntityLocateStart = -1, SecondEntityLocateEnd = -1;//实体在语句中的位置
	   				 FirstEntityLocateStart = Sentence.indexOf(entity_start);
	   				 FirstEntityLocateEnd = Sentence.indexOf(entity_end);
	   				    
	   				 SecondEntityLocateStart = Sentence.indexOf(entity_start,FirstEntityLocateEnd);
	   				 SecondEntityLocateEnd = Sentence.indexOf(entity_end,SecondEntityLocateStart);
	   				 
	   				 //抽取出两个实体
	   			     String FirstEntity = "", SecondEntity = "";
	   			        
	   			     FirstEntity = Sentence.substring(FirstEntityLocateStart + entity_start.length() + 1,FirstEntityLocateEnd - 1);
	   			     SecondEntity = Sentence.substring(SecondEntityLocateStart + entity_start.length() + 1,SecondEntityLocateEnd - 1);
	   			     
	   			     //如果实体对已经存在，则增加单词的所对应的频数
	         		 if(EntityPairs.containsKey(FirstEntity+","+SecondEntity)){
	         		    	EntityPairs.put(FirstEntity+","+SecondEntity, EntityPairs.get(FirstEntity+","+SecondEntity) + 1);
	         		 }else{//否则，则添加该实体对和其频数
	         		    	EntityPairs.put(FirstEntity+","+SecondEntity,1);
	         		 }
	                 Sentence = "";
	              }else{
	                  Sentence = Sentence + ((char) tempchar);
	              } 
             }  
             reader.close();
             System.out.println(i); 

		 }catch(Exception e){
			 e.printStackTrace();
		 }
    	 
    	 //调用函数对实体对进行排序
    	 SortEntityPairs(EntityPairs);
    }
    
    public void SortEntityPairs(HashMap<String, Integer>   EntityPairs){//按照value值从高到低排序
    	List<Map.Entry<String, Integer>> EntitySeed = new ArrayList<Map.Entry<String, Integer>>(EntityPairs.entrySet());
    	
    	//排序
    	Collections.sort(EntitySeed, new Comparator<Map.Entry<String, Integer>>() {   
    	    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {      
    	        return (o2.getValue() - o1.getValue()); 
    	        //return (o1.getKey()).toString().compareTo(o2.getKey());
    	    }
    	}); 

    	//排序后
    	for (int i = 0; i < EntitySeed.size(); i++) {
    	    String Entity = EntitySeed.get(i).toString();
    	    System.out.println(Entity);
    	    Entity = Entity.substring(0,Entity.indexOf("="));
    	    WriteIntoFile(Entity);
    	    if(i == 10){
    	    	break;
    	    }
    	    	
    	}
    }
}
