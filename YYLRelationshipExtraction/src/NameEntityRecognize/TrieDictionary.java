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
	  //定义派生词根节点
	  private TrieNode root_derivative = new TrieNode();
	  
	  //调用importDict函数导入字典
	  private TrieDictionary(String fn) {
	    importDict(fn);
	  }
	  
	  //定义TrieDictionary类型的函数，实例化一个文件名
	  public static TrieDictionary getInstance(String Entity) {
		TrieDictionary dict = null;   
		System.out.println("对实体对创建字典树");
	    if (dict == null)
	      dict = new TrieDictionary(Entity);
	      
	    return dict;
	  }
	
	  //读入字典文件中的内容
	  private boolean importDict(String Entity)
	  
	  {
	      System.out.println("Entity:"+Entity);
	      String FirstEntity = Entity.substring(0,Entity.indexOf(','));
	      String SecondEntity = Entity.substring(Entity.indexOf(',')+1,Entity.length());
	      add(FirstEntity); 
	      add(SecondEntity);
	     
          System.out.println("Construct Dict Finish!");
	      return true;
	  }
	  
	  //构造字典树
	  public void add(String term)
	  {
	    TrieNode p_derivative = this.root_derivative;
	    int i;
	    String word = "";
	    String word_derivative = "";
	    WordNetHelper getstem = new WordNetHelper();
	    for (i = 0; i < term.length(); ++i) {
		      char c = term.charAt(i);
		      if(c == ' '||i == (term.length()-1)){
		    	 
		    	  //如果c是空格，则已经得到一个word，则按照字典树的方法进行字典树的构造
		    	  if(c== ' '){
		    		  word_derivative = getstem.findStem(word);
		    		  TrieNode pChild_derivative = null;
		    	      
		    	      //如果派生词不存在，则将派生词加入到派生词字典树
		    	      if((pChild_derivative = (TrieNode)p_derivative.childs.get(word_derivative)) == null) {
		    	          pChild_derivative = new TrieNode(word_derivative);
		    	          //System.out.println("pChild:" + pChild.key);
		    	          p_derivative.childs.put(word_derivative, pChild_derivative);
		    	      }
		    	      
		    	      //指针指向该word
		    	      p_derivative = pChild_derivative;
		    	  }
		    	  
		    	  //如果是该短语的最后一个字母，则是最后一个单词
		    	  else if(i == (term.length()-1)){
		    		  word += c; 
		    		  word_derivative = getstem.findStem(word);
		    		  
		    		  TrieNode pChild_derivative = null;
		    	      
		    	      //如果派生词不存在，则将派生词加入到派生词字典树
		    	      if((pChild_derivative = (TrieNode)p_derivative.childs.get(word_derivative)) == null) {
		    	          pChild_derivative = new TrieNode(word_derivative);
		    	          //System.out.println("pChild:" + pChild.key);
		    	          p_derivative.childs.put(word_derivative, pChild_derivative);
		    	      }
		    	      
		    	      p_derivative = pChild_derivative;
		    	  }
		    	  //System.out.println(word);
		    	  word = "";
		      }
		      else{
		    	  word += c; 
		      }
	    }
	    p_derivative.bound = true;
	   // System.out.println("pChild:" + p.key +" "+"p.bound:"+" "+p.bound);
	   // System.out.println("pChild_derivative:" + p_derivative.key +" "+"p_derivative.bound:"+" "+p_derivative.bound);
	  }

	  public TrieNode getRootDerivative()
	  {
		  return this.root_derivative;
	  }
}
