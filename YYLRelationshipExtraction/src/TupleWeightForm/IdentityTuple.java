package TupleWeightForm;

import NameEntityRecognize.TrieDictionary;
import NameEntityRecognize.TrieNode;
import NameEntityRecognize.WordNetHelper;
import StaticConstant.NonChiSplit;

public class IdentityTuple {
//	//2016/11/29改动
//	String entity_start = "entity_start";
//	String entity_end = "entity_end";
//	
//	public int[] seg(String seq, String Entity)
//	{
//		//初始化一个字符串变量，保存派生的词组
//	    StringBuffer segBuffer_derivative = new StringBuffer();
//	    
//	    //将实体对的第一个和第二个实体分别保存
//	    String FirstEntity = Entity.substring(0,Entity.indexOf(','));
//	    String SecondEntity = Entity.substring(Entity.indexOf(',')+1,Entity.length());
//	    
//	    int i;
//	    char c;
//	    //保存从句子中读取的单词
//	    String word = "";
//	    //第一个第二个实体分别在句子中的位置
//	    int FirstEntityLocate = -1, SecondEntityLocate = -1;//实体在语句中的位置
//	    
//	    System.out.println("识别句子开始："+seq);
//	    
//	    for(i = 0; i < seq.length(); i++){
//	    	
//	    	 int start_index_first = -1,start_index_second = -1,end_index_first = -1, end_index_second = -1;
//			 //查找句子中出现的实体标记
//			 start_index_first = seq.indexOf(entity_start);
//			 end_index_first = seq.indexOf(entity_end);
//			   
//			 start_index_second = seq.indexOf(entity_start,end_index_first);
//			 end_index_second = seq.indexOf(entity_end,start_index_second);
//			 if(start_index_first != -1 && start_index_second != -1 && end_index_first != -1&& end_index_second != -1){
//			     for(int j = 0; )
//			 }
//	    	if(c == ' '||i == seq.length()-1||NonChiSplit.isCharSeperator(c)){
//		    	  //===============派生词用来匹配的过成
//	    	      String word_derivative = "";
//	    	      WordNetHelper getstem = new WordNetHelper();
//	    	      TrieNode pChild_derivative = null;
//	    	      boolean isLetter = false;      
//	    	      int isLetterJudge = 0;
//	    	      for(isLetterJudge = 0; isLetterJudge < word.length(); isLetterJudge++){
//    		    	  if(Character.isLetter(word.charAt(isLetterJudge))){
//    		    		  isLetter = true;
//    		    		  break;
//    		    	  }
//	    	      }
//	    	      
//	    	      if(isLetter == true){
//	    	         word_derivative = getstem.findStem(word);
//	    	      }else{
//	    	    	  word_derivative = word;
//	    	      }
//    			  //===如果当前节点的子节点为空，则有两种情况，一、找到一个词组的最大匹配；二、可能该词不在字典树中(因为该词从根节点起就没有出现)
//	    	      if((pChild_derivative = (TrieNode)p_derivative.childs.get(word_derivative)) == null) {
//	    	    	  
//	    	    	  //当前节点boud值为真，表示当前节点一直追溯到根节点为一个最大匹配的词组
//	    	    	  if(p_derivative.bound){
//	    	    		  String segBuffer = "";
//	    	    		  System.out.println("CAonimabi");
//	    	              
//	    	              if((segBuffer_derivative.toString()).charAt(segBuffer_derivative.length()-1) == ' '){
//	    	            	  segBuffer = segBuffer_derivative.toString().substring(0,segBuffer_derivative.length()-1);
//	    	              }else{
//	    	            	  segBuffer = segBuffer_derivative.toString();
//	    	              }
////	    	              System.out.println("word_derivative: "+word);
////	    	              System.out.println(segBuffer+"-"+FirstEntity);
////	    	              System.out.println(segBuffer+"-"+SecondEntity); 
//	    	    		  
//	    	    		 //当前节点的子节点为空,说明从根节点到当前节点已经是一个最大匹配；但是当前节点的子节点为空,并不表示子节点所代表的词没有出现在字典树中，必须重新将该词从根节点开始匹配，因此，指针回退一个词的长度
//	    	    		  i = i-word.length()-1;
//	    	    		 //在句子中匹配到了实体一或者实体二
//	    	    		  if(segBuffer.equals(FirstEntity)){
//	    	    			  FirstEntityLocate = i;
//	    	    		  }
//	    	    		  if(segBuffer.equals(SecondEntity)){
//	    	    			  SecondEntityLocate = i;
//	    	    		  }
//	    	    	  }
//	    	    	  //当前节点bound值为假，不做任何处理
//	    	    	  else{
//	    	    	      ;
//	    	    	  }
//	    	    	  //该词组查询已经到叶子节点，回退到字典树的根节点，下次查找从根节点开始匹配
//	    	    	  p_derivative = dict.getRootDerivative();
//	    	    	  //清空字符串
//	    	    	  segBuffer_derivative = new StringBuffer();
//	    	      }
//	    	      else{
//	    	    	  //已经达到句子末尾,不会有新词加入进来
//	    	    	  if(i == seq.length() - 1){
//	    	    		  //当前节点bound值为真，表示当前节点一直追溯到根节点为一个最大匹配的词组
//	    	    		  if(pChild_derivative.bound){
//		    	    		  //System.out.println("bound");
//		    	    		  segBuffer_derivative.append(word_derivative);
//		    	    		  
//		    	    		  System.out.println(segBuffer_derivative.toString()+"-"+FirstEntity);
//		    	              System.out.println(segBuffer_derivative.toString()+"-"+SecondEntity); 
//		    	              
//		    	    		  //在句子中匹配到了实体一或者实体二
//		    	    		  if((segBuffer_derivative.toString()).equals(FirstEntity)){
//		    	    			  FirstEntityLocate = i;
//		    	    		  }
//		    	    		  if((segBuffer_derivative.toString()).equals(SecondEntity)){
//		    	    			  SecondEntityLocate = i;
//		    	    		  }
//		    	    	  }
//		    	    	  //清空字符串
//		    	    	  segBuffer_derivative = new StringBuffer();
//	    	    	  }
//	    	    	  else{
//	    	    		  segBuffer_derivative.append(word_derivative+" ");
//	    	    	  }
//	    	    	  p_derivative = pChild_derivative;
//	    	      }
//		    	      
//	    		  word = "";
//	    	}
//	    	else{
//	    		word += c; 
//	    	}
//	    	
//	    	//判断实体是否出现在同一个句子中
//  	        if(FirstEntityLocate != -1 && SecondEntityLocate != -1){
//  	    	    break;  
//  	        }
//	    }
////	    System.out.println("FisrtEntityLocate: " + FirstEntityLocate);
////	    System.out.println("SecondEntityLocate: " + SecondEntityLocate);
//	    int locate[] = new int [3];
//	    locate[0] = FirstEntityLocate;
//	    locate[1] = SecondEntityLocate;
//	    return locate;
//	}
	//2016/11/29改动结束
	
	public int[] seg(String seq,TrieDictionary dict,String Entity)
	{
	    //初始化一个字符串变量，保存派生的词组
	    StringBuffer segBuffer_derivative = new StringBuffer();
	    //得到派生字典的根节点
	    TrieNode p_derivative = dict.getRootDerivative();
	    
	    //将实体对的第一个和第二个实体分别保存
	    String FirstEntity = Entity.substring(0,Entity.indexOf(','));
	    String SecondEntity = Entity.substring(Entity.indexOf(',')+1,Entity.length());
	    
	    int i;
	    char c;
	    //保存从句子中读取的单词
	    String word = "";
	    //第一个第二个实体分别在句子中的位置
	    int FirstEntityLocate = -1, SecondEntityLocate = -1;//实体在语句中的位置
	    
	    System.out.println("识别句子开始："+seq);
	    
	    for(i = 0; i < seq.length(); i++){
	    	c = seq.charAt(i);
	    	if(c == ' '||i == seq.length()-1||NonChiSplit.isCharSeperator(c)){
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
	    	      }else{
	    	    	  word_derivative = word;
	    	      }
    			  //===如果当前节点的子节点为空，则有两种情况，一、找到一个词组的最大匹配；二、可能该词不在字典树中(因为该词从根节点起就没有出现)
	    	      if((pChild_derivative = (TrieNode)p_derivative.childs.get(word_derivative)) == null) {
	    	    	  
	    	    	  //当前节点boud值为真，表示当前节点一直追溯到根节点为一个最大匹配的词组
	    	    	  if(p_derivative.bound){
	    	    		  String segBuffer = "";
	    	    		  System.out.println("CAonimabi");
	    	              
	    	              if((segBuffer_derivative.toString()).charAt(segBuffer_derivative.length()-1) == ' '){
	    	            	  segBuffer = segBuffer_derivative.toString().substring(0,segBuffer_derivative.length()-1);
	    	              }else{
	    	            	  segBuffer = segBuffer_derivative.toString();
	    	              }
//	    	              System.out.println("word_derivative: "+word);
//	    	              System.out.println(segBuffer+"-"+FirstEntity);
//	    	              System.out.println(segBuffer+"-"+SecondEntity); 
	    	    		  
	    	    		 //当前节点的子节点为空,说明从根节点到当前节点已经是一个最大匹配；但是当前节点的子节点为空,并不表示子节点所代表的词没有出现在字典树中，必须重新将该词从根节点开始匹配，因此，指针回退一个词的长度
	    	    		  i = i-word.length()-1;
	    	    		 //在句子中匹配到了实体一或者实体二
	    	    		  if(segBuffer.equals(FirstEntity)){
	    	    			  FirstEntityLocate = i;
	    	    		  }
	    	    		  if(segBuffer.equals(SecondEntity)){
	    	    			  SecondEntityLocate = i;
	    	    		  }
	    	    	  }
	    	    	  //当前节点bound值为假，不做任何处理
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
		    	    		  segBuffer_derivative.append(word_derivative);
		    	    		  
		    	    		  System.out.println(segBuffer_derivative.toString()+"-"+FirstEntity);
		    	              System.out.println(segBuffer_derivative.toString()+"-"+SecondEntity); 
		    	              
		    	    		  //在句子中匹配到了实体一或者实体二
		    	    		  if((segBuffer_derivative.toString()).equals(FirstEntity)){
		    	    			  FirstEntityLocate = i;
		    	    		  }
		    	    		  if((segBuffer_derivative.toString()).equals(SecondEntity)){
		    	    			  SecondEntityLocate = i;
		    	    		  }
		    	    	  }
		    	    	  //清空字符串
		    	    	  segBuffer_derivative = new StringBuffer();
	    	    	  }
	    	    	  else{
	    	    		  segBuffer_derivative.append(word_derivative+" ");
	    	    	  }
	    	    	  p_derivative = pChild_derivative;
	    	      }
		    	      
	    		  word = "";
	    	}
	    	else{
	    		word += c; 
	    	}
	    	
	    	//判断实体是否出现在同一个句子中
  	        if(FirstEntityLocate != -1 && SecondEntityLocate != -1){
  	    	    break;  
  	        }
	    }
//	    System.out.println("FisrtEntityLocate: " + FirstEntityLocate);
//	    System.out.println("SecondEntityLocate: " + SecondEntityLocate);
	    int locate[] = new int [3];
	    locate[0] = FirstEntityLocate;
	    locate[1] = SecondEntityLocate;
	    return locate;
	 }
}
