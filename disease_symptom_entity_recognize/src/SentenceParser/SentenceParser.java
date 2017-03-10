package SentenceParser;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import StaticConstant.StaticParameter;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.SentenceUtils;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreePrint;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;

public class SentenceParser {
	 
	 ArrayList<String> NPvalue = new ArrayList<>();//暂存可能是新实体的短语
	 int flag;
	 String TempString1 = "",TempString2 = "";
	 
     
	 public boolean GetSentenceParser(String[] TempEntity){
    	
    	// LexicalizedParser lp = LexicalizedParser.loadModel(StaticParameter.parserModel);
    	 TempString2 = TempEntity[0] +" "+ TempEntity[1];
    	 
    	 flag = 0;//识别出临时实体是否是一个真实实体
    	 
    	 // This option shows parsing a list of correctly tokenized words
    	 
    	// Lastly, the mainstay of treatment of pediatric non-alcoholic [entity_start] fatty liver [entity_end] [entity_start] disease [entity_end] (NAFLD) has been largely through [entity_start] lifestyle [entity_end] interventions, namely, dieting and exercise [entity_start] Nonalcoholic fatty liver [entity_end] [entity_start] disease [entity_end] (NAFLD), tightly linked to the metabolic syndrome (MS), has emerged as a leading cause of chronic liver disease worldwide.
    	// String[] sent = { "This", "is", "an", "easy ball", "sentence", "." };
    	// String[] sent = {"non-alcoholic", "fatty liver"};
    	 List<CoreLabel> rawWords = SentenceUtils.toCoreLabelList(TempEntity);
    	 Tree parse = StaticParameter.lp.apply(rawWords);
    	 
    	 Process(parse);
    	 
    	 //parse.pennPrint();
    	 //System.out.println();
    	 
    	 if(flag == 1){
    		 return true;
    	 }else{
    		 return false;
    	 }

    	 // This option shows loading and using an explicit tokenizer
//    	 TokenizerFactory<CoreLabel> tokenizerFactory = PTBTokenizer.factory(new CoreLabelTokenFactory(), "");
//    	 Tokenizer<CoreLabel> tok = tokenizerFactory.getTokenizer(new StringReader(Sentence));
//    	 List<CoreLabel> rawWords2 = tok.tokenize();
//    	 parse = lp.apply(rawWords2);
//    	 
//    	 Process(parse);

//    	 TreebankLanguagePack tlp = lp.treebankLanguagePack(); // PennTreebankLanguagePack for English
//    	 GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
//    	 GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
//    	 List<TypedDependency> tdl = gs.typedDependenciesCCprocessed();
//    	 System.out.println(tdl);
//    	 System.out.println();
//
//    	 // You can also use a TreePrint object to print trees and dependencies
//    	 TreePrint tp = new TreePrint("penn,typedDependenciesCollapsed");
//    	 tp.printTree(parse);
    	 
//		 if(NPvalue.size() > 0){
//			 if(NPvalue.get(0).equals("NP")){
//				 int i;
//				 System.out.println("名词短语对");
//				 for(i = 0; i < NPvalue.size(); i++){
//					 System.out.println(NPvalue.get(i));
//				 }
//			 }
//	     }
     }
     
     public void Process(Tree tree){
    	 if(tree.isLeaf()){
//    		 System.out.println("child.value():"+tree.value());
//    		 System.out.println("child.nodeString():"+tree.nodeString());
    		 if(NPvalue.size() > 0){
    			 if(NPvalue.get(0).equals("NP")){
    				// int i;
    				 NPvalue.add(tree.value());
//    				 System.out.println("名词短语对");
//    				 for(i = 0; i < NPvalue.size(); i++){
//    					 System.out.println(NPvalue.get(i));
//    				 }
    			 }
    		 }
    		 return ;
    	 }
    	 
    	 for (Tree child : tree.children()) {
//    		 System.out.println("child.value():"+child.toString());
//    		 System.out.println("child.value():"+child.value());
    		 if(child.value().equals("NP")){
    			 NPvalue.add("NP");
    			 Process(child);
    			
    			 if(NPvalue.size() > 0){
    				 if(NPvalue.get(0).equals("NP")){
    					 int i;
    					// System.out.println("名词短语对2");
    					 for(i = 1; i < NPvalue.size() - 1 ; i++){
    						 TempString1 += NPvalue.get(i) + " ";
    						 //System.out.println(NPvalue.get(i));
    					 }
    					 //System.out.println(NPvalue.get(i));
    					 TempString1 += NPvalue.get(i);
    					
    					 //System.out.println("TempString1:"+TempString1);
    					 //System.out.println("TempString2:"+TempString2);
    					 
    					 if(TempString1.equals(TempString2)){
    						 flag = 1;	 
    					 }
    					 TempString1 ="";
    					
    				 }
    		     }
    			 
    			 NPvalue = new ArrayList<>();
    			// NPvalue.add(child.value());
    		 }
//    		 System.out.println("child.nodeString():"+child.nodeString());
    		 else{
    			 Process(child);
    		 }
         }
    	 
    	 
    	// NPvalue = new ArrayList<>();
    	 
     }
}
