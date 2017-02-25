package EvaluateResult;

import java.util.Random;

public class test {
    public static void main(String[] arg){
    	ExtractResult extractResult = new ExtractResult();
    	String FileName = "E:/北航文件/编程程序/YYLRelationshipExtraction/CandidateEntityTuple.txt";
    	
    	int i;
    	Random random = new Random();
    	i = random.nextInt(30)+1;
    	
    	System.out.println("随机生成的数字："+i);
    	
    	extractResult.ReadSentence(FileName,i);
    }
}
