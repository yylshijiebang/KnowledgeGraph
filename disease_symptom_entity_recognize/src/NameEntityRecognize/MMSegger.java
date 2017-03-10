package NameEntityRecognize;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import SentenceParser.GetSentenceList;

public class MMSegger 
{
      String entity_start = "[entity_start] ";
      String entity_end = " [entity_end]";
	 
//	  //将匹配到的单词写入文件中
//	  public static void WriteIntoFile(String glaucoma,int flag){
//		    //写入文件
//		    BufferedWriter bw = null;
//		    FileWriter fileWriter=null;
//			try{
//				if(flag == 1)
//					fileWriter = new FileWriter("E:/北航文件/编程程序/disease_symptom_entity_recognize/term.txt",true);
//				else if(flag == 2)
//				    fileWriter = new FileWriter("E:/北航文件/编程程序/disease_symptom_entity_recognize/term_derivative.txt",true);
//				bw = new BufferedWriter(fileWriter);
//			    bw.write(glaucoma);
//			    bw.newLine();  
//			
//			}catch (IOException e) {
//				e.printStackTrace();
//			}finally{
//			    try {
//			        bw.close();
//			    }catch (IOException e) {
//			        e.printStackTrace();
//			    }
//			}
//	  }	
//	  
//	  //将匹配到的单句子写入文件中
//	  public static void WriteSentenceIntoFile(String glaucoma){
//		    //写入文件
//		    BufferedWriter bw = null;
//		    FileWriter fileWriter=null;
//			try{
//			    fileWriter = new FileWriter("E:/北航文件/编程程序/disease_symptom_entity_recognize/recognize_entity_coprus/sentence_label.txt",true);
//				bw = new BufferedWriter(fileWriter);
////				System.out.println(glaucoma);
//			    bw.write(glaucoma);
//			    bw.newLine();  
//			
//			}catch (IOException e) {
//				e.printStackTrace();
//			}finally{
//			    try {
//			        bw.close();
//			    }catch (IOException e) {
//			        e.printStackTrace();
//			    }
//			}
//	  }	
	  

	  public void seg(String seq,TrieDictionary dict,int[] SentenceIndex)
	  {
		//初始化一个字符串变量  
	    StringBuffer segBuffer = new StringBuffer();
	    //初始化一个字符串变量，保存派生的词组
	    StringBuffer segBuffer_derivative = new StringBuffer();
	    
	    //2016/11/29插入开始
	    StringBuffer seqBuffer = new StringBuffer(seq);
	   // StringBuffer sentenceBuffer = new StringBuffer(seq);
	    //2016/11/29插入结束
	    
	    int[] GlaucomaFlag = new int[2];//判断语句中是否含有snomed中的英文词组
	    GlaucomaFlag[0] = 0;
	    GlaucomaFlag[1] = 0;
	    //得到字典的根节点
	    TrieNode p = dict.getRoot();
	    //得到派生字典的根节点
	    TrieNode p_derivative = dict.getRootDerivative();
	    
	    //定义得到句子list列表
	    GetSentenceList getSentenceList = new GetSentenceList();
	    
	    
	    int i;
	    int i_label = 0;
	    char c;
	    String word = "";
	    //System.out.println("CleanSentence:"+seq);
	    for(i = 0; i < seq.length(); i++){
	    	c = seq.charAt(i);
	    	if(c == ' '||i == seq.length()-1){
	    		if(word.length() > 0){
	    			  if(i == seq.length()-1){
	    				  word += c;
	    			  }
//	    			  TrieNode pChild = null;
		    		  //System.out.println("word:"+(TrieNode)p.childs.get(word));
	    			  //===如果当前节点的子节点为空，则有两种情况，一、找到一个词组的最大匹配；二、可能该词不在字典树中(因为该词从根节点起就没有出现)
//		    	      if((pChild = (TrieNode)p.childs.get(word)) == null) {
//		    	    	  //当前节点boud值为真，表示当前节点一直追溯到根节点为一个最大匹配的词组
//		    	    	  if(p.bound){
//		    	    		  //System.out.println("bound");
//		    	    		 // segBuffer.append("-");
//		    	    		  GlaucomaFlag[0] = 1;
//		    	    		  WriteIntoFile(new String(String.valueOf(SentenceIndex[0])+" "+segBuffer),1);
//		    	    		  //当前节点的子节点为空，并不表示该词没有出现在字典树中，必须重新将该词从根节点开始匹配，因此，指针回退一个词的长度
//		    	    		//  i = i-word.length()-1;
//		    	    	  }
//		    	    	  //当前节点bound值为假，不做任何处理
//		    	    	  else{
//		    	    	      ;
//		    	    	  }
//		    	    	  //该词组查询已经到叶子节点，回退到字典树的根节点，下次查找从根节点开始匹配
//		    	    	  p = dict.getRoot();
//		    	    	  //清空字符串
//		    	    	  segBuffer = new StringBuffer();
//		    	      }
//		    	      else{
//		    	    	  //已经达到句子末尾,不会有新词加入进来
//		    	    	  if(i == seq.length() - 1){
//		    	    		  //当前节点bound值为真，表示当前节点一直追溯到根节点为一个最大匹配的词组
//		    	    		  if(pChild.bound){
//			    	    		  //System.out.println("bound");
//			    	    		  segBuffer.append(word);
//			    	    		  GlaucomaFlag[0] = 1;
//			    	    		  WriteIntoFile(new String(String.valueOf(SentenceIndex[0])+" "+segBuffer),1);
//			    	    		  
//			    	    	  }
//			    	    	  //清空字符串
//			    	    	  segBuffer = new StringBuffer();
//		    	    	  }
//		    	    	  else{
//		    	    		  segBuffer.append(word+" ");
//		    	    	  }
//		    	    	  p = pChild;
//		    	      }
		    	      
		    	      //===============派生词用来匹配的过成
		    	      String word_derivative = "";
		    	      WordNetHelper getstem = new WordNetHelper();
		    	      TrieNode pChild_derivative = null;
		    	      boolean isLetter = false;
		    	      int isLetterJudge = 0;
		    	      for(isLetterJudge = 0; isLetterJudge < word.length(); isLetterJudge++){
		    		    	 if(Character.isLetter(word.charAt(isLetterJudge))){
		    		    		 isLetter = true;
		    		    		 break;
		    		    	 }
		    	      }
		    	      
		    	      if(isLetter == true){
		    	         word_derivative = getstem.findStem(word);
		    	      }
		    	      word_derivative = word_derivative.toLowerCase();//将所有大写转换成小写字母
		    	      
//		    	      System.out.println("word:"+word);
//		    	      System.out.println("word_derivative:"+word_derivative);
		    	      
		    		  //System.out.println("word:"+(TrieNode)p.childs.get(word));
	    			  //===如果当前节点的子节点为空，则有两种情况，一、找到一个词组的最大匹配；二、可能该词不在字典树中(因为该词从根节点起就没有出现)
		    	      if((pChild_derivative = (TrieNode)p_derivative.childs.get(word_derivative)) == null) {
		    	    	  //当前节点boud值为真，表示当前节点一直追溯到根节点为一个最大匹配的词组
		    	    	  if(p_derivative.bound){
		    	    		  //System.out.println("bound");
		    	    		 // segBuffer.append("-");
//		    	    		  GlaucomaFlag[1] =  1;
//		    	    		  WriteIntoFile(new String(String.valueOf(SentenceIndex[1])+" "+segBuffer_derivative),2);
//		    	    		  
//		    	    		  System.out.println("segBuffer_derivative1:" + segBuffer_derivative);
		    	    		  
		    	    		  //2016/11/29添加内容
//		    	    		  if(i-segBuffer_derivative.length() - word.length()+(entity_end.length()+entity_start.length())*i_label < 0)
//		    	    			  seqBuffer.insert(0, entity_start);
//		    	    		  else
//		    	    		  System.out.println("i:"+i);
//		    	    		  System.out.println("segBuffer_derivative:"+segBuffer_derivative+":"+segBuffer_derivative.length());
//		    	    		  System.out.println("word:"+word+":"+word.length());
//		    	    		  System.out.println("i_label:"+i_label);
//		    	    		  System.out.println("插入位置:"+(i-segBuffer_derivative.length() - word.length()+(entity_end.length()+entity_start.length())*i_label));
		    	    		  
		    	    		  if(i == seq.length()-1){//如果到了句子末尾
		    	    			  //2017/3/10添加内容
			    	    		  seqBuffer = seqBuffer.insert(i-segBuffer_derivative.length() + 1 - word.length() +(entity_end.length()+entity_start.length())*i_label,entity_start);
			    	    		  seqBuffer = seqBuffer.insert(i+(entity_end.length()+entity_start.length())*i_label+entity_start.length() - word.length(), entity_end);
			    	    		  i_label++;
			    	    		  //2017/3/10添加内容结束
		    	    		  }else{//如果没到
		    	    		      seqBuffer = seqBuffer.insert(i-segBuffer_derivative.length() - word.length()+(entity_end.length()+entity_start.length())*i_label,entity_start);
		    	    		      seqBuffer = seqBuffer.insert(i+(entity_end.length()+entity_start.length())*i_label+entity_start.length() - word.length()-1, entity_end);
		    	    		      i_label++;
		    	    		  }
		    	    		  //2016/11/29添加内容结束
		    	    		  
		    	    		  //当前节点的子节点为空，并不表示该词没有出现在字典树中，必须重新将该词从根节点开始匹配，因此，指针回退一个词的长度
		    	    		  i = i-word.length()-1;
		    	    	  }
		    	    	  //当前节点bound值为假，说明该词组不在字典树
		    	    	  else{
		    	    		  ;
		    	    	  }
		    	    	  //该词组查询已经到叶子节点，回退到字典树的根节点，下次查找从根节点开始匹配
		    	    	  p_derivative = dict.getRootDerivative();
		    	    	  //清空字符串
		    	    	  segBuffer_derivative = new StringBuffer();
		    	      }
		    	      else{
		    	    	  //已经达到句子末尾,不会有新词加入进来
		    	    	  if(i == seq.length() - 1){
		    	    		  //当前节点bound值为真，表示当前节点一直追溯到根节点为一个最大匹配的词组
		    	    		  if(pChild_derivative.bound){
			    	    		  //System.out.println("bound");
			    	    		  segBuffer_derivative.append(word);
//			    	    		  System.out.println("segBuffer_derivative2:" + segBuffer_derivative);
//			    	    		  GlaucomaFlag[1] =  1;
//			    	    		  WriteIntoFile(new String(String.valueOf(SentenceIndex[1])+" "+segBuffer_derivative),2);
			    	    		  
			    	    		  //2016/11/29添加内容
			    	    		  seqBuffer = seqBuffer.insert(i-segBuffer_derivative.length() + 1 +(entity_end.length()+entity_start.length())*i_label,entity_start);
			    	    		  seqBuffer = seqBuffer.insert(i+(entity_end.length()+entity_start.length())*i_label+entity_start.length() + 1, entity_end);
			    	    		  i_label++;
			    	    		  //2016/11/29添加内容结束
			    	    		  
			    	    	  }
			    	    	  //清空字符串
			    	    	  segBuffer_derivative = new StringBuffer();
		    	    	  }
		    	    	  else{
		    	    		  segBuffer_derivative.append(word+" ");
		    	    		 // System.out.println("segBuffer_derivative3:" + segBuffer_derivative);
		    	    	  }
		    	    	  p_derivative = pChild_derivative;
		    	      }
		    	      
	    		}
	    		word = "";
	    	}
	    	else{
	    		word += c; 
	    	}
	    }
	    
	    //2016/11/29
	    if(i_label >= 2){
	    	//2017/2/7开始
//	    	System.out.println("i_label: " + i_label);
//	    	System.out.println(seqBuffer);
//	    	if(i_label == 2){
//	           WriteSentenceIntoFile(new String(seqBuffer));
//	    	}else{
//	    		DivideSentence(new String(seqBuffer),i_label-1);
//	    	}
	        //2017/2/7结束
	    	
	    	//2017.3.9修改
	    	getSentenceList.GetSentenceList(new String(seqBuffer));
	    	//2017.3.9修改结束
	    }
	    //2016/11/29
		
	    // return GlaucomaFlag;
	  }
	  
//	  //2017/2/7划分句子
//	  public void DivideSentence(String sentence, int i_label){
//	    	 int i = 0;
//	    	 String divide_sentence = "";
//	    	 
//	    	 int first_entity_start = 0, first_entity_end = 0;
//	    	 int second_entity_start = 0, second_entity_end = 0;
//	    	 int third_entity_start = 0, third_entity_end = 0;
//	    	 int forth_entity_start = 0, forth_entity_end = 0;
//	    	 while(i_label > 0){
//	    		 first_entity_start = sentence.indexOf(entity_start,i);
//	    		 first_entity_end = sentence.indexOf(entity_end,first_entity_start + entity_start.length()); 
//	    		 
//	    		 second_entity_start = sentence.indexOf(entity_start,first_entity_end + entity_end.length());
//	    		 second_entity_end = sentence.indexOf(entity_end,second_entity_start + entity_start.length());
//	    		 
//	    		 third_entity_start = sentence.indexOf(entity_start,second_entity_end + entity_end.length());
//	    		 third_entity_end = sentence.indexOf(entity_end,third_entity_start + entity_start.length());
//	    		 
//	             if(third_entity_start > 0){
//	    		    divide_sentence = sentence.substring(i,third_entity_start-1);
//	    		    WriteSentenceIntoFile(divide_sentence);
//	             }else{
//	            	divide_sentence = sentence.substring(i,sentence.length());
//	            	WriteSentenceIntoFile(divide_sentence);
//	             }
//	    		 i = first_entity_end + entity_end.length() + 1;
//	    		 i_label --;
//	    	 }
//	}
//	//2017/2/7划分句子结束
//  public static void main(String[] args) throws IOException
//  {
//	    MMSegger mmsegger = new MMSegger();
//	    System.out.println(mmsegger.seg("I buy the Snomed Product,you Glaucoma treat yout eyes rather than Glaucoma treat。"));
//  }
}
