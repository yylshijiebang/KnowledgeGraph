package NameEntityRecognize;

import java.util.HashMap;

/** 
 * 构建内存词典的Trie树结点 
 *  
 * @author single(宋乐) 
 * @version 1.01, 10/11/2009 
 */  

public class TrieNode {
	/**结点关键字，其值为英文词中的一个word*/  
    public String key = "";  
    /**如果该word在词语的末尾，则bound=true*/  
    public boolean bound=false;  
    /**指向下一个结点的指针结构，用来存放当前word所在词中的下一个word的位置*/  
    public HashMap<String,TrieNode> childs=new HashMap<String,TrieNode>();  
     
    //初始化构造函数
    public TrieNode(){  
    }  
    //初始化构造函数  
    public TrieNode(String k){  
        this.key=k;  
    }  
}
