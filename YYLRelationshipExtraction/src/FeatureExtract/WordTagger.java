package FeatureExtract;

import java.io.IOException;
import java.util.List;

import StaticConstant.ConstantParameter;
import edu.stanford.nlp.ling.CoreAnnotations.SentenceBeginAnnotation;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class WordTagger {
    public  int  WordPOS( String sentence)throws IOException,
    ClassNotFoundException {
    	// Initialize the tagger 
    	//try{
			//MaxentTagger tagger = new MaxentTagger("taggers/english-left3words-distsim.tagger");//这是相对路径，也可以写绝对路径  
		//        String tagged_sentence = tagger.tagString(Sentence);
			
			String tagged_sentence = ConstantParameter.tagger.tagString(sentence);
			//System.out.println("tagged_sentence:"+tagged_sentence);
			String tag = tagged_sentence.substring(tagged_sentence.lastIndexOf("_")+1,tagged_sentence.length()-1);
		    if(ConstantParameter.POSHash.containsKey(tag)){
		    	return ConstantParameter.POSHash.get(tag);
		    }else{
		    	return ConstantParameter.POSHash.get("");
		    }
		    //System.out.println("tag:"+tag);
    	//}catch(Exception e){
    	//	System.out.println(e);
    	//}
    }
}
