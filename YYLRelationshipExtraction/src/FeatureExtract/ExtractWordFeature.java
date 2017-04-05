package FeatureExtract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ForkJoinPool;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.omg.PortableServer.AdapterActivator;

import StaticConstant.AdjustParameter;
import StaticConstant.ConstantParameter;
import StaticConstant.Distant;
import StaticConstant.POSMatrix;
import edu.stanford.nlp.io.EncodingPrintWriter.out;

public class ExtractWordFeature {
    
    
    static String entity_start = "[entity_start]";
    static String entity_end = "[entity_end]";
    
	//得到word特征
	public void GetWordVector(String Sentence){
		int i,j;
		String word = "";
		char c;
		
		//是否符合词向量标记
		int flag = 0;
		
		//定义带句子的特征向量变量
		FeatureStore featureStore = new FeatureStore();
		featureStore.sentence = Sentence;//句子保存到变量中
		
		FeatureStore SentenceFeatureVec = new FeatureStore();//将句子和句子的特征向量保存到Feature类中的静态变量中
		
		//得到句子中每个实体的位置
	    int FirstEntityStartLocate = -1;
	    int FirstEntityEndLocate = -1;
	    
	    int SecondEntityStartLocate = -1;
	    int SecondEntityEndLocate = -1;
	    
	    int word_num = 0;
	    
	    for(j = 0; j < Sentence.length(); j++){
	    	c = Sentence.charAt(j);
	    	//如果该字符c是空格，则是一个单词
	    	if(c == ' '|| j == Sentence.length()-1){
	    		if(j == Sentence.length()-1){
	    			word += c;
	    		}
//	    		SentenceHash.put(word,word_num);
	    	    //保存实体位置
	    		if(word.equals(entity_start)){
	    			if(FirstEntityStartLocate == -1){
	    				FirstEntityStartLocate = word_num;
	    			}else{
	    				SecondEntityStartLocate = word_num;
	    			}
	    		}else if(word.equals(entity_end)){
	    			if(FirstEntityEndLocate == -1){
	    				FirstEntityEndLocate = word_num;
	    			}else{
	    				SecondEntityEndLocate = word_num;
	    			}
	    		}
	    		word_num ++;
	    		word = "";
	    	}else{
	    		word += c; 
	    	} 
	    }
	    
	   // System.out.println("Sentence:"+Sentence);
	   // System.out.println("EntityLocate:"+ FirstEntityStartLocate + ":" + FirstEntityEndLocate + ":" + SecondEntityStartLocate + ":" + SecondEntityEndLocate);
	    
	    
	    word_num = 0;
	    word = "";
		//得到句子中每个单词的词向量和pos向量
		for(j = 0; j < Sentence.length(); j++){
	    	c = Sentence.charAt(j);
	    	//如果该字符c是空格，则是一个单词
	    	if(c == ' '|| j == Sentence.length()-1){
	    		if(j == Sentence.length()-1){
	    			word += c;
	    		}
	    		int p1 = -1;//单词到第一个实体的距离
	    	    int p2 = -1;//单词到第二个实体的距离
	    	    flag = 0;
	    		if(!word.equals(entity_start) && !word.equals(entity_end)){//如果单词不是实体标志
		    		if(word_num < FirstEntityStartLocate){//第一个实体前的单词
		    			p1 = 0 - (FirstEntityStartLocate - word_num); 
		    			p2 = 0 - (SecondEntityStartLocate - word_num);
		    			if((0-p1) <= AdjustParameter.PreWordNum){//如果属于第一个实体前3个单词之内
		    				flag = 1;
		    			}
//		    		}else if(word_num > FirstEntityStartLocate && word_num < FirstEntityEndLocate){//第一个实体
//		    			p1 = 0;
//		    			p2 = FirstEntityEndLocate - SecondEntityStartLocate;
		    		}else if(word_num > FirstEntityEndLocate && word_num < SecondEntityStartLocate){//第一个实体和第二个实体之间的单词
						p1 = word_num - FirstEntityEndLocate;
						p2 = 0 - (SecondEntityStartLocate - word_num);
						flag = 1;
//					}else if(word_num > SecondEntityStartLocate && word_num < SecondEntityEndLocate){//第二个实体
//						p1 = FirstEntityEndLocate - SecondEntityStartLocate;
//						p2 = 0;
					}else if(word_num > SecondEntityEndLocate){//第二个实体后的单词
						p1 = word_num - FirstEntityEndLocate;
						p2 = word_num - SecondEntityEndLocate;
						if(p2 <= AdjustParameter.SufWordNUm){//如果属于第二个实体后三个单词之内
							flag = 1;
						}
					}
		    		
		    		//System.out.println("word:"+ word + ":"+"distant:"+p1+":"+p2);
		    		
		    	    //得到单词的向量
		    		if(flag == 1){//如果满足构建词向量的条件
			    		if(ConstantParameter.WordVecHash.containsKey(word)){//如果单词在词向量中存在
	
	//		    			System.out.println("word:"+word);
	//		    			System.out.println(ConstantParameter.WordVecHash.get(word));
			    			ArrayList<Float> word_vec = new ArrayList<>();
			    			for(int k = 0; k < ConstantParameter.WordVecHash.get(word).size(); k++){//得到单词的词向量
		    				     word_vec.add(ConstantParameter.WordVecHash.get(word).get(k));
		    			    }
			    			//word_vec = ConstantParameter.WordVecHash.get(word);
			    			
	//		    			System.out.println("词向量长度WordVecHash："+ConstantParameter.WordVecHash.get(word).size());
	//		    			System.out.println("词向量长度："+word_vec.size());
			    			//System.out.println("WordVecHash:"+ word_vec);
			                
			    		
			    			
			    			word_vec = LinkDistantVec(word_vec,p1,p2);//调用函数拼接dis向量
			    			word_vec = LinkPosVec(word_vec,word);//调用函数拼接pos向量
			    			
			    			
			    			//System.out.println("WordVecHash:"+ word_vec);
			    			
			    			//System.out.println("句向量长度："+word_vec.size());
			    			//System.out.println("word_vec:"+ word_vec);
			    			//System.out.println("featureStore.vector+word_vec:"+ featureStore.vector.);
			    			if(featureStore.vector.size() == 0){
			    				for(int t = 0; t < word_vec.size(); t++){
			    				    featureStore.vector.add(word_vec.get(t));
			    				}
			    				//System.out.println("featureStore.vector+word_vec:"+ featureStore.vector);
			    			}else{
			    				for(int t = 0; t < featureStore.vector.size(); t++){
			    					featureStore.vector.set(t,featureStore.vector.get(t)+word_vec.get(t));
			    				}
			    			}
			    		}
		    		}
	    		}
	    		
	    		word_num ++;
	    		word = "";
	    	}else{
	    		word += c; 
	    	} 
	    }
		//System.out.println("featureStore.sentence:"+ featureStore.sentence);
		//System.out.println(featureStore.vector.size());
		//System.out.println("featureStore.vector:"+ featureStore.vector);
		
//		ClusterSentence clusterSentence = new ClusterSentence();//调用句子聚类函数进行聚类
//		clusterSentence.SentenceCluster(featureStore);
		if(featureStore.vector.size() != 0)
		   SentenceFeatureVec.SentenceVector.add(featureStore);//将带特征向量的句子保存到静态变量SentenceVector中
		
    }
	
	//拼接单词和实体位置的向量
	public ArrayList<Float> LinkDistantVec(ArrayList<Float> word_vec, int p1, int p2){
		
		int locate1 = -1, locate2 = -1;
		if(ConstantParameter.DisHash.containsKey(String.valueOf(p1))){//得到第一个p1
			locate1 = ConstantParameter.DisHash.get(String.valueOf(p1));
		}else{
			locate1 = 0;
		}
		
		//System.out.println("locate1:"+locate1);
		for(int i = 0; i < Distant.row; i++){
			word_vec.add(Distant.DisMatrix[i][locate1]);
			//System.out.println("Distant.DisMatrix:"+Distant.DisMatrix[i][locate1]);
		}
		
		
		if(ConstantParameter.DisHash.containsKey(String.valueOf(p2))){//得到第二个p2
			locate2 = ConstantParameter.DisHash.get(String.valueOf(p2));
		}else{
			locate2 = 0;
		}
		
		//System.out.println("locate2:"+locate2);
		for(int i = 0; i < Distant.row; i++){
			word_vec.add(Distant.DisMatrix[i][locate2]);
			//System.out.println("Distant.DisMatrix:"+Distant.DisMatrix[i][locate2]);
		}
		
		return word_vec;
	}
	
	//拼接pos向量
	public ArrayList<Float> LinkPosVec(ArrayList<Float> word_vec,String word){
		WordTagger wordTagger = new WordTagger();//得到单词的对应词性在0-1向量中的位置
		try{
		    int locate = wordTagger.WordPOS(word);
		    
		    //System.out.println("locate:"+locate);
		    
		    for(int i = 0; i < POSMatrix.row; i++){
		    	word_vec.add(POSMatrix.PosMatrix[i][locate]);
		    	
		    	//System.out.println(POSMatrix.PosMatrix[i][locate]);
		    }
		}catch(Exception e){
		    System.out.println(e);
		}
		return word_vec;
	}
	
}
