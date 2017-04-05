package FeatureExtract;

import java.util.ArrayList;

public class FeatureStore {
    public String sentence = "";
    public ArrayList<Float> vector = new ArrayList<>();
    public FeatureStore(){//构造函数,初始化变量
    	this.sentence = "";
    	this.vector.clear();
    }
    public static ArrayList<FeatureStore> SentenceVector = new ArrayList<>();//带句子的句子特征向量
    
}
