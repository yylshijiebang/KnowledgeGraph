package ReadShortText;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import FeatureExtract.FeatureStore;
import StaticConstant.ConstantParameter;
import StaticConstant.Distant;
import StaticConstant.POSMatrix;
import TupleWeightForm.ReadSeedAndSentence;
import edu.stanford.nlp.maxent.Feature;

public class test {
	//程序的主入口
		public static void main(String[] args){
			 
			 
			//待切分短语文件 
	        try { 
	        	String filepathDistant = "D:/YYLSoftware/Program/YYLRelationshipExtraction/Distant_Table.txt";
	        	String filepathPos = "D:/YYLSoftware/Program/YYLRelationshipExtraction/POS_Table.txt";
	        	String filepathWordVec = "D:/YYLSoftware/Program/YYLRelationshipExtraction/SentenceLableVectors.txt";
	        	//String filepathWordVec = "D:/YYLSoftware/Program/YYLRelationshipExtraction/test_vectors.txt";
	        	String filepathSentence = "D:/YYLSoftware/Program/YYLRelationshipExtraction/sentence_label.txt";
	        	//String filepathSentence = "D:/YYLSoftware/Program/YYLRelationshipExtraction/test_sentence.txt";
	        	//String filepathSentence = "D:/YYLSoftware/Program/YYLRelationshipExtraction/test.txt";
				ReadFile readFile = new ReadFile();
	
				readFile.ReadSentence(filepathDistant,1);//实体间距离表
				System.out.println("Distant");
				
				readFile.ReadSentence(filepathPos,0);//词性表
				System.out.println("POS");
				
				readFile.ReadSentence(filepathWordVec,2);//词向量表
				System.out.println("wordvec");
				
				Distant.CaculateMatrix();//随机产生单词距离实体距离的矩阵表
				POSMatrix.CaculateMatrix();//随机产生词性矩阵表表
				
				readFile.ReadSentence(filepathSentence, 3);//读取句子,并构建不包含实体的前中后缀组合的向量
				System.out.println("FeatureStore.SentenceVector:"+FeatureStore.SentenceVector.size());
				
				ReadSeedAndSentence readSeedAndSentence = new ReadSeedAndSentence();//读取种子实体对
				String SeedFileName = "D:/YYLSoftware/Program/YYLRelationshipExtraction/SeedRelation.txt";
				//String SeedFileName = "D:/YYLSoftware/Program/YYLRelationshipExtraction/test_seed.txt";
				
                readSeedAndSentence.GetSeed(SeedFileName);
				
				System.out.println("All Finish!");
				
//				for(int i = 0; i < FeatureStore.SentenceVector.size(); i++){
//					System.out.println(FeatureStore.SentenceVector.get(i).sentence);
//					System.out.println(FeatureStore.SentenceVector.get(i).vector);
//				}
				
	        } catch (Exception e) {  
	        	System.out.println("fail");
	        	e.printStackTrace(); 
	        }  
			
		}	
		
}
