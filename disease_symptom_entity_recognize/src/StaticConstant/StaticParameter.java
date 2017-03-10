package StaticConstant;

import edu.stanford.nlp.parser.lexparser.LexicalizedParser;

public class StaticParameter {
	 public static LexicalizedParser lp = LexicalizedParser.loadModel("edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz");
}
