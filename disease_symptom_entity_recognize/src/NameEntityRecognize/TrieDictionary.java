package NameEntityRecognize;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Scanner;




public class TrieDictionary
{
	  private static TrieDictionary dict = null;
	 
	  //定义原型词根节点
	  private TrieNode root = new TrieNode();
	  
	  //定义派生词根节点
	  private TrieNode root_derivative = new TrieNode();
	  
	  //调用importDict函数导入字典
	  private TrieDictionary(String fn) {
	    importDict(fn);
	  }
	  
	  //定义TrieDictionary类型的函数，实例化一个文件名
	  public static TrieDictionary getInstance(String fileName) {
		System.out.println("nigedasb");
	    if (dict == null)
	      dict = new TrieDictionary(fileName);
	      
	    return dict;
	  }
	
	  //读入字典文件中的内容
	  private boolean importDict(String fileName)
	  
	  {
	    try
	    {
	      System.out.println("fileName:"+fileName);
	      //读入词典中的内容
	      Scanner in = new Scanner(new File(fileName),"UTF-8"); 
	      
	      String word = "";
	      int i = 0;
	      while (in.hasNextLine()) {
              word = in.nextLine();
              //System.out.println(i+":"+word);
  	    	  i ++;
  	    	  
  	    	  //添加字典到内存
  	          add(word);
//  	          System.out.println("i: " + i);
          } 
          in.close();
          System.out.println("i: " + i);
//          System.out.println("word: " + word);
          System.out.println("Construct Dict Finish!");
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    return true;
	  }
	  
	  //构造字典树
	  public void add(String term)
	  {
	    TrieNode p = this.root;
	    TrieNode p_derivative = this.root_derivative;
	    int i;
	    String word = "";
	    String word_derivative = "";
	    WordNetHelper getstem = new WordNetHelper();
	    for (i = 0; i < term.length(); ++i) {
		      char c = term.charAt(i);
		      if(c == ' '||i == (term.length()-1)||NonChiSplit.isCharSeperator(c)){
		    	 if(word.length() > 0){
			    	  //如果c是空格，则已经得到一个word，则按照字典树的方法进行字典树的构造
			    	  if(c == ' '){
			    		  word_derivative = getstem.findStem(word);
			    		  word_derivative = word_derivative.toLowerCase();//将所有大写转换成小写字母
			    		  TrieNode pChild =  null;
			    		  TrieNode pChild_derivative = null;
//			    		  TrieNode pChild = new TrieNode();
//			    		  TrieNode pChild_derivative = new TrieNode();
			    		  //如果该word不存在，则将该word加入到字典树中
			    	      if((pChild = (TrieNode)p.childs.get(word)) == null) {
			    	          pChild = new TrieNode(word);
			    	          //System.out.println("pChild:" + pChild.key);
			    	          p.childs.put(word, pChild);
			    	      }
			    	      
			    	      //如果派生词不存在，则将派生词加入到派生词字典树
			    	      if((pChild_derivative = (TrieNode)p_derivative.childs.get(word_derivative)) == null) {
			    	          pChild_derivative = new TrieNode(word_derivative);
			    	          //System.out.println("pChild:" + pChild.key);
			    	          p_derivative.childs.put(word_derivative, pChild_derivative);
			    	      }
			    	      
			    	      //指针指向该word
			    	      p = pChild;
			    	      p_derivative = pChild_derivative;
			    	  }
			    	  //如果是标点符号
			    	  else if(NonChiSplit.isCharSeperator(c)){
			    		  word_derivative = getstem.findStem(word);
			    		  word_derivative = word_derivative.toLowerCase();//将所有大写转换成小写字母
			    		  TrieNode pChild = null;
			    		  TrieNode pChild_derivative = null;
//			    		  TrieNode pChild = new TrieNode();
//			    		  TrieNode pChild_derivative = new TrieNode();
			    		  //如果该word不存在，则将该word加入到字典树中
			    	      if((pChild = (TrieNode)p.childs.get(word)) == null) {
			    	          pChild = new TrieNode(word);
			    	          //System.out.println("pChild:" + pChild.key);
			    	          p.childs.put(word, pChild);
			    	      }
			    	      
			    	      //如果派生词不存在，则将派生词加入到派生词字典树
			    	      if((pChild_derivative = (TrieNode)p_derivative.childs.get(word_derivative)) == null) {
			    	          pChild_derivative = new TrieNode(word_derivative);
			    	          //System.out.println("pChild:" + pChild.key);
			    	          p_derivative.childs.put(word_derivative, pChild_derivative);
			    	      }
			    	      
			    	      //指针指向该word
			    	      p = pChild;
			    	      p_derivative = pChild_derivative;
			    	  }
			    	  //如果是该短语的最后一个字母，则是最后一个单词
			    	  else if(i == (term.length()-1)){
			    		  if(!NonChiSplit.isCharSeperator(c)){
			    				word += c;
			    		  }
			    		  //word += c; 
			    		  word_derivative = getstem.findStem(word);
			    		  word_derivative = word_derivative.toLowerCase();//将所有大写转换成小写字母
			    		  
			    		  TrieNode pChild = null;
			    		  TrieNode pChild_derivative = null;
//			    		  TrieNode pChild = new TrieNode();
//			    		  TrieNode pChild_derivative = new TrieNode();
			    		  
			    	      if ((pChild = (TrieNode)p.childs.get(word)) == null) {
			    	          pChild = new TrieNode(word);
			    	          //System.out.println("pChild:" + pChild.key);
			    	          p.childs.put(word, pChild);
			    	      }
			    	      
			    	      //如果派生词不存在，则将派生词加入到派生词字典树
			    	      if((pChild_derivative = (TrieNode)p_derivative.childs.get(word_derivative)) == null) {
			    	          pChild_derivative = new TrieNode(word_derivative);
			    	          //System.out.println("pChild:" + pChild.key);
			    	          p_derivative.childs.put(word_derivative, pChild_derivative);
			    	      }
			    	      
			    	      p = pChild;
			    	      p_derivative = pChild_derivative;
			    	  }
		    	  }
		    	  //System.out.println(word);
		    	  word = "";
		      }
		      else{
		    	  word += c; 
		      }
	    }
	    p.bound = true;
	    p_derivative.bound = true;
	   // System.out.println("pChild:" + p.key +" "+"p.bound:"+" "+p.bound);
	   // System.out.println("pChild_derivative:" + p_derivative.key +" "+"p_derivative.bound:"+" "+p_derivative.bound);
	  }
	
//	  //查询字典树
//	  public boolean contains(String term)
//	  {
//		    TrieNode p = this.root;
//		    int i;
//		    String word = "";
//		    for (i = 0; i < term.length(); ++i) {
//			      char c = term.charAt(i);
////			      TrieNode pChild = null;
////			      if ((pChild = (TrieNode)p.childs.get(Character.valueOf(c))) == null) break;
////			      p = pChild;
//			      if(c == ' '||i == (term.length()-1)){
//			    	  //如果c是空格，则已经得到一个word，则按照字典树的方法进行字典树的构造
//			    	  if(c== ' '){
//			    		  TrieNode pChild = null;
//			    		  //如果该word不存在，则将该word加入到字典树中
//			    	      if((pChild = (TrieNode)p.childs.get(word)) == null) {
//			    	          break;
//			    	      }
//			    	      //指针指向该word
//			    	      p = pChild;
//			    	  }
//			    	  //如果是该短语的最后一个字母，则是最后一个单词
//			    	  else if(i == (term.length()-1)){
//			    		  word += c; 
//			    		  TrieNode pChild = null;
//			    	      if ((pChild = (TrieNode)p.childs.get(word)) == null) {
//			    	         break;
//			    	      }
//			    	      p = pChild;
//			    	  }
//			    	  System.out.println("contains:"+word);
//			    	  word = "";
//			      }
//			      else{
//			    	  word += c; 
//			      }
//		    }
//		
//		    return ((i == word.length()) && (p.bound));//查找到最大的匹配
//	  }
//	
	  public TrieNode getRoot()
	  {
	     return this.root;
	  }
	  public TrieNode getRootDerivative()
	  {
		  return this.root_derivative;
	  }
}
