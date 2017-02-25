package NameEntityRecognize;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;


public class IndentitySentence {
	//调用TrieDictionary建立字典树
	public static TrieDictionary dict = null;
	public static MMSegger mmsegger = new MMSegger();
	public static int[] SentenceIndex = new int[2];//指示句子序号
	//public static int SentenceIndexDerivative = 0;//指示句子序号，派生
	
	static {
	    dict = TrieDictionary.getInstance("E:/北航文件/编程程序/unify_entity_recognize/GlaucomaConcept.txt");
	}
	
	 //将匹配到的语句写入文件中
	 public static void WriteIntoFile(String glaucoma, String glaucoma2 ,int[] flag){
		    //写入文件
		    BufferedWriter bw1 = null;
		    BufferedWriter bw2 = null;
		    FileWriter fileWriter1 = null;
		    FileWriter fileWriter2 = null;
			try{
				if(flag[0] == 1){
					SentenceIndex[0] += 1;
			        fileWriter1 = new FileWriter("E:/北航文件/编程程序/unify_entity_recognize/sentence.txt",true);
			        bw1 = new BufferedWriter(fileWriter1);
					bw1.write(glaucoma);
					bw1.newLine(); 
				}
				
				if(flag[1] == 1){
					 SentenceIndex[1] += 1;
					 fileWriter2 = new FileWriter("E:/北航文件/编程程序/unify_entity_recognize/sentence_derivative.txt",true);
					 bw2 = new BufferedWriter(fileWriter2);
					 bw2.write(glaucoma2);
					 bw2.newLine();  
				}
			
			}catch (IOException e) {
				e.printStackTrace();
			}finally{
			    try {
			    	if(flag[0] == 1)
			            bw1.close();
			    	if(flag[1] == 1)
			            bw2.close();
			    }catch (IOException e) {
			        e.printStackTrace();
			    }
			}
	}	
	
	public static void GetSentence(String Sentence){
		//System.out.println(Sentence);
		int[] flag  = mmsegger.seg(Sentence,dict,SentenceIndex);
		//System.out.println("flag[0]"+flag[0]);
		//System.out.println("flag[1]"+flag[1]);
		String Sentence1 = "";
		String Sentence2 = "";
		if(flag[0] == 1||flag[1] == 1){
			if(flag[0] == 1)
			   Sentence1 = String.valueOf(SentenceIndex[0]) + " " +Sentence;
			if(flag[1] == 1)
				Sentence2 = String.valueOf(SentenceIndex[1]) + " " +Sentence;
			WriteIntoFile(Sentence1, Sentence2 ,flag);
		}
		
		//System.out.println(mmsegger.seg(Sentence,dict));
	}
	
	
	//程序的主入口
	public static void main(String[] args){
		
		//待切分短语文件
//		String FileName = "E:/北航文件/编程程序/unify_entity_recognize/character_new.txt";
//		String Sentence = "";
//		Reader reader = null;  
        try {
        	String filepath = "E:/北航文件/编程程序/unify_entity_recognize/medline coprus";
			ReadFile readFile = new ReadFile();
			if(readFile.readfile(filepath)){
				System.out.println("All finish");
			}
//            System.out.println("以字符为单位读取文件内容，一次读一个字节：");  
//            // 一次读一个字符  
//            reader = new InputStreamReader(new FileInputStream(FileName));  
//            int tempchar = -1;  
//            while ((tempchar = reader.read()) != -1) {  
//                // 对于windows下，\r\n这两个字符在一起时，表示一个换行。  
//                // 但如果这两个字符分开显示时，会换两次行。  
//                // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。  
//                if (((char) tempchar) != '\r'&&((char) tempchar) != '\n') {  
//                	//if(((char) tempchar) == '.') {
//                	if(NonChiSplit.isSentence((char)tempchar)){
//                		Sentence = Sentence + ((char) tempchar);
//                		GetSentence(Sentence);  
//                		Sentence = "";
//                	}
//                	else{
//                		Sentence = Sentence + ((char) tempchar);
//                	}
//                }  
//            }  
//            reader.close();
//            System.out.println("All Finish!");  
        } catch (Exception e) {  
        	System.out.println("fail");
        	e.printStackTrace(); 
        }  
	}
}
