package StaticConstant;

import java.util.ArrayList;
import java.util.HashMap;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class ConstantParameter {
    public static HashMap<String, Integer> POSHash = new HashMap<>();//保存词性表
    public static HashMap<String, Integer> DisHash = new HashMap<>();//保存单词到实体距离表
    public static HashMap<String, ArrayList<Float>> WordVecHash = new HashMap<>();//保存词向量
    public static MaxentTagger tagger = new MaxentTagger("taggers/english-left3words-distsim.tagger");//这是相对路径，也可以写绝对路径  
    //public static int test_num = 0;
}
