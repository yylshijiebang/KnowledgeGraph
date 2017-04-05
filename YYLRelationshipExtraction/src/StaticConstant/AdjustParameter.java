package StaticConstant;

import java.sql.SQLNonTransientConnectionException;
import java.util.HashSet;
import java.util.Set;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class AdjustParameter {
	 //前缀中缀后缀单词的限制个数
     public static int PreWordNum = 0;
     public static int MidWordNUm = 6;
     public static int SufWordNUm = 0;
     
     //前缀中缀后缀权重计算的调整因子
//     public static double PreFactor = 0.2;
//     public static double MidFactor = 0.6;
//     public static double SuffFactor = 0.2;
     
     //对元组聚类时，实体对和聚类索引之间的划分标记
     public static String RecognizeFlag = "###";
     
     //词性标注
     //public static MaxentTagger tagger = new MaxentTagger("taggers/english-left3words-distsim.tagger");//这是相对路径，也可以写绝对路径  
     
     //匹配这几个动词形式2017.2.27
//     public boolean isVerb(String c)
//     {
//    	 Set<String> verb = new HashSet<>();
//    	 verb.add("VB");
//    	 verb.add("VBD");
//    	 verb.add("VBG");
//    	 verb.add("VBN");
//    	 verb.add("VBP");
//    	 verb.add("VBZ");
//    	 verb.add("VBD");
//    	 verb.add("RB");
//    	 verb.add("RBR");
//    	 verb.add("RBS");
//    	 verb.add("WRB");
//    	 if(verb.contains(c))
//    	     return  true;
//    	 else
//    		 return false;
//     }
}
