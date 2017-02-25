package TupleWeightForm;

public class test {
	public static void main(String[] arg){
		 String SeedFileName = "E:/北航文件/编程程序/YYLRelationshipExtraction/SeedRelation.txt";
		 String SentenceFileName = "E:/北航文件/编程程序/YYLRelationshipExtraction/sentence_label.txt";
		 ReadSeedAndSentence get_seed = new ReadSeedAndSentence();
		 get_seed.GetSeed(SeedFileName,SentenceFileName);
		 //添加注册注释
	 }
}
