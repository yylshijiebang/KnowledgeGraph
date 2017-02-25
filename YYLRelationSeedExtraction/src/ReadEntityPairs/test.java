package ReadEntityPairs;

public class test {
	public  static void main(String[] arg){
		ExtractSeeds extractSeeds = new ExtractSeeds();
		String FileName = "E:/北航文件/编程程序/YYLRelationSeedExtraction/sentence_label.txt";
		extractSeeds.ReadSentenceLabel(FileName);
	}
}
