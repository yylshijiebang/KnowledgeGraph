package EvaluateResult;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class ExtractResult {
	 int EntityPairsNum = 100;
	 //读取文本中的句子
	 public void ReadSentence(String FileName, int random){
		 try{
		     //Scanner reader = new Scanner(new File(FileName),"UTF-8");
		     int i = 0,t = 0;
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
                	 if(Sentence.indexOf("{",0) < 0){
                		 t++;
                	 }
                	 if(t >= random){
                		 GetRandomResult(Sentence);
                	 }
                	 if(t - random == EntityPairsNum){
                		 System.out.println("读了多少个实体对：" + t);
                		 break;
                	 }
                	 Sentence = "";
	              }else{
	                  Sentence = Sentence + ((char) tempchar);
	              } 
             }  
             reader.close();
             System.out.println("总共读了多少行："+i); 
            // System.exit(0);

		 }catch(Exception e){
			 e.printStackTrace();
		 }
	 }
	 
	 //随机统计出其中若干个数据
     public void GetRandomResult(String Sentence){
    	 if(Sentence.indexOf("{",0) >= 0){
    		 if(Sentence.indexOf("}",Sentence.length()-1) < 0){
    			 WriteRelationIntoFile(Sentence);
    		 }
    	 }else{
    		 WriteEntityIntoFile(Sentence);
    	 }
     }
     
     //将实体对保存到文本中
   	 public void WriteEntityIntoFile(String glaucoma){
   		    //写入文件
   		    BufferedWriter bw = null;
   		    FileWriter fileWriter=null;
   			try{
   				fileWriter = new FileWriter("E:/北航文件/编程程序/EvaluateExtractResult/entity.txt",true);
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
   			WriteRelationIntoFile(glaucoma);
   	  }
   	 
   	 //将实体间关系写入文本
   	 public void WriteRelationIntoFile(String glaucoma){
		    //写入文件
		    BufferedWriter bw = null;
		    FileWriter fileWriter=null;
			try{
				fileWriter = new FileWriter("E:/北航文件/编程程序/EvaluateExtractResult/relation.txt",true);
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
