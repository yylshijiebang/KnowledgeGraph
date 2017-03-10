package SentenceParser;

import java.awt.geom.FlatteningPathIterator;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class GetNewEntity {
	 static String entity_start = "[entity_start]";
     static String entity_end = "[entity_end]";
     public void FindNewEntity(ArrayList<String> SentenceList){
    	 //调用stanford-parser进行句子解析
    	 SentenceParser sentenceParser = new SentenceParser();
    	 char c;
    	 String word = "",word2 = "";
    	 int FirstEntityStartLocate = -1, FirstEntityEndLocate = -1, SecondEntityStartLocate = -1, SecondEntityEndLocate = -1,space = -1;
    	 String FirstEntity = "", SecondEntity = ""; 
    	 String[] TempEntity = new String[]{"",""};
    	 int i = 0, flag = 0;
    	 
    	 //第一遍遍历找到相邻的实体，尝试合并
    	 for(i = SentenceList.size()-1; i >= 0; ){//从句尾到句首查找标注的实体
    		 word = SentenceList.get(i);
    		 if(word.equals(entity_end)){//找到第一个实体标记结束的标志
                 for(int j = i - 1; j >= 0; j--){
                	 word2 = SentenceList.get(j);
                	 if(word2.equals(entity_start)){//找第一个实体开始的标志
                		 FirstEntity = SubString(SentenceList,j+1,i-1);//将该实体暂存到变量中
                		 if(j-1 > 0){//如果没有到句首，则接着进行查询，找到实体前相邻的一个单词，或者一个实体
                			 word = SentenceList.get(j-1);
                			 if(word.equals(entity_end)){//如果实体前面相邻的是一个实体
                				 for(int t = j-2; t >= 0; t--){
                					 word2 = SentenceList.get(t);
                					 if(word2.equals(entity_start)){//找到紧前实体的开始标记
                						 SecondEntity = SubString(SentenceList,t+1,j-2);
                						 TempEntity[0] = SecondEntity;
                						 TempEntity[1] = FirstEntity;
                						 if(sentenceParser.GetSentenceParser(TempEntity)){
                							 SentenceList.set(j-1, "");//将SecondEntity结束标记质控
                                         	 SentenceList.set(j,"");//FirstEntity开始标记置空
                                         	 i = t-1;
                                         	 //flag = 1;
                                         	 break;
                						 }else{
                							 i = j-1;
                							 //flag = 1;
                                         	 break;
                						 }
                					 }
                				 }
                				 break;
                			 }else{
                				 i = j-1;
                				 break;
                			 }
                		 }else{//已经到句首第一个实体结束开始标记找到以后，已经到句首
                			 i = j-1;
                			 break;
                		 }
                	 }
                 }
    		 }else{
    			 i--;
    		 }
    	 }
    	 
    	 //第二遍遍历，找到实体前的第一个单词，看能否合并
//    	 for(i = SentenceList.size()-1; i >= 0; ){//从句尾到句首查找标注的实体
//    		 word = SentenceList.get(i);
//    		 if(word.equals(entity_end)){//找到第一个实体标记结束的标志
//                 for(int j = i - 1; j >= 0; j--){
//                	 word2 = SentenceList.get(j);
//                	 if(word2.equals(entity_start)){//找第一个实体开始的标志
//                		 FirstEntity = SubString(SentenceList,j+1,i-1);//将该实体暂存到变量中
//                		 if(j-1 > 0){//如果没有到句首，则接着进行查询，找到实体前相邻的一个单词，或者一个实体
//                			 word = SentenceList.get(j-1);
//                			 if(!word.equals(entity_end)){//如果实体前面相邻的是一个非实体单词
//                                TempEntity[0] = word; 
//                                TempEntity[1] = FirstEntity;
//                                if(sentenceParser.GetSentenceParser(TempEntity)){//如果判断是新实体
//                                	SentenceList.set(j-1, entity_start);//将单词所在位置用实体开始标记代替
//                                	SentenceList.set(j,word);//将实体开始标记所在位置用单词代替
//                                	i = j-2;
//                                	//flag = 1;
//                                	break;
//                                }else{
//                                	i = j-2;
//                                	//flag = 1;
//                                	break;
//                                }
//                			 }
//                			 else{//如果实体前面相邻的是一个实体
////                				 for(int t = j-2; t >= 0; t--){
////                					 word2 = SentenceList.get(t);
////                					 if(word2.equals(entity_start)){//找到紧前实体的开始标记
////                						 SecondEntity = SubString(SentenceList,t+1,j-2);
////                						 TempEntity[0] = SecondEntity;
////                						 TempEntity[1] = FirstEntity;
////                						 if(sentenceParser.GetSentenceParser(TempEntity)){
////                							 SentenceList.set(j-1, "");//将SecondEntity结束标记质控
////                                         	 SentenceList.set(j,"");//FirstEntity开始标记置空
////                                         	 i = t-1;
////                                         	 //flag = 1;
////                                         	 break;
////                						 }else{
////                							 i = j-1;
////                							 //flag = 1;
////                                         	 break;
////                						 }
////                					 }
////                				 }
//                				 i = j-1;
//                				 break;
//                			 }
//                		 }else{//已经到句首第一个实体结束开始标记找到以后，已经到句首
//                			 i = j-1;
//                			 break;
//                		 }
//                	 }
//                 }
//    		 }else{
//    			 i--;
//    		 }
//    	 }
    	 
    	 String MergeEntitySentence = "";
    	 int EntityNum= 0; //实体个数
    	 
    	 //将合并实体后的句子保存
    	 for(int j = 0; j < SentenceList.size()-1; j++){
    		 if(!"".equals(SentenceList.get(j)))
    		    MergeEntitySentence += SentenceList.get(j) + " ";
    		 if(SentenceList.get(j).equals(entity_start)){
    			 EntityNum ++;
    		 }
    	 }
    	 MergeEntitySentence += SentenceList.get(SentenceList.size()-1);
    	// System.out.println("MergeEntitySentence:"+MergeEntitySentence);
    	 if(EntityNum == 2){
	           WriteSentenceIntoFile(MergeEntitySentence);
	     }else{
	    		DivideSentence(MergeEntitySentence,EntityNum-1);
	     }
            
     }
     
     //2017/2/7划分句子
	  public void DivideSentence(String sentence, int i_label){
	    	 int i = 0;
	    	 String divide_sentence = "";
	    	 
	    	 int first_entity_start = 0, first_entity_end = 0;
	    	 int second_entity_start = 0, second_entity_end = 0;
	    	 int third_entity_start = 0, third_entity_end = 0;
	    	 int forth_entity_start = 0, forth_entity_end = 0;
	    	 while(i_label > 0){
	    		 first_entity_start = sentence.indexOf(entity_start,i);
	    		 first_entity_end = sentence.indexOf(entity_end,first_entity_start + entity_start.length()); 
	    		 
	    		 second_entity_start = sentence.indexOf(entity_start,first_entity_end + entity_end.length());
	    		 second_entity_end = sentence.indexOf(entity_end,second_entity_start + entity_start.length());
	    		 
	    		 third_entity_start = sentence.indexOf(entity_start,second_entity_end + entity_end.length());
	    		 third_entity_end = sentence.indexOf(entity_end,third_entity_start + entity_start.length());
	    		 
	             if(third_entity_start > 0){
	    		    divide_sentence = sentence.substring(i,third_entity_start-1);
	    		    WriteSentenceIntoFile(divide_sentence);
	             }else{
	            	divide_sentence = sentence.substring(i,sentence.length());
	            	WriteSentenceIntoFile(divide_sentence);
	             }
	    		 i = first_entity_end + entity_end.length() + 1;
	    		 i_label --;
	    	 }
	}
	//2017/2/7划分句子结束
     
     //将匹配到的单句子写入文件中
	  public static void WriteSentenceIntoFile(String glaucoma){
		    //写入文件
		    BufferedWriter bw = null;
		    FileWriter fileWriter=null;
			try{
			    fileWriter = new FileWriter("E:/北航文件/编程程序/disease_symptom_entity_recognize/recognize_entity_coprus/sentence_label.txt",true);
				bw = new BufferedWriter(fileWriter);
//				System.out.println(glaucoma);
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
     
     public String SubString(ArrayList<String> SentenceList,int start,int end){
    	 String TempEntity = "";
    	 for(int i = start; i < end; i++){
    		 TempEntity += SentenceList.get(i) + " ";
    	 }
    	 TempEntity += SentenceList.get(end);
    	 
    	 return TempEntity;
     }
}
