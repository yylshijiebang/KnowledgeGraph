package NameEntityRecognize;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;


import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.*;
import edu.mit.jwi.morph.WordnetStemmer;

/**
 * Created by liuyibo on 16-4-12.
 */

public class WordNetHelper {
    // file path of WordNet
    static String path = "D:/YYLSoftware/Program/javakit/WordNet-3.0/dict";
    //建立词典对象并打开它
    static IDictionary dict = new Dictionary(new File(path));
    // stemmer is the recognizer of words, eg. input "ate", output eat
    static WordnetStemmer stemmer = new WordnetStemmer(dict);
    static {
        try {
            dict.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * find the stem of the word
     * @param word
     * @return
     */
    public String findStem(String word) {
        for (String str : stemmer.findStems(word, POS.VERB)) {
            if (!str.equals(word)) {
                return str;
            }
        }
        for (String str : stemmer.findStems(word, POS.NOUN)) {
            if (!str.equals(word)) {
                return str;
            }
        }
        return word;
    }

    // for usage of finding words related to given word
    // if the word is a noun, return all words in the synnet
    // if the word is a verb, return all words in the synnet of words related to given word, eg. create -> creator
    public static class WordHandler {
        String word;
        boolean initialized = false;
        Set<String> listn = new HashSet<>();
        Set<String> listv = new HashSet<>();
        public WordHandler(String word) {
            this.word = word;
        }
        private void init() {
            for (String word0 : stemmer.findStems(word, POS.VERB)) {
                IIndexWord iw = dict.getIndexWord(word0, POS.VERB);
                if (iw == null) {
                    continue;
                }
                for (IWordID id : iw.getWordIDs()) {
                    addAllWordsInSynset(dict.getWord(id).getSynset(), listv);
                    for (IWordID id2 : dict.getWord(id).getRelatedWords()) {
                        addAllWordsInSynset(id2.getSynsetID(), listv);
                    }
                }

            }
            for (String word0 : stemmer.findStems(word, POS.NOUN)) {
                IIndexWord iw = dict.getIndexWord(word0, POS.NOUN);
                if (iw == null) {
                    continue;
                }
                for (IWordID id : iw.getWordIDs()) {
                    addAllWordsInSynset(dict.getWord(id).getSynset(), listn);
                }
            }
            initialized = true;
        }

        private void addAllWordsInSynset(ISynsetID synsetID, Collection<String> list) {
            if (synsetID == null) {
                return;
            }
            addAllWordsInSynset(dict.getSynset(synsetID), list);
        }

        private void addAllWordsInSynset(ISynset synset, Collection<String> list) {
            if (synset == null) {
                return;
            }
            for (IWord word : synset.getWords()) {
                list.add(word.getLemma());
            }
        }

        public int relationTo(String word, String pos) {
            if (!initialized) {
                init();
            }
            if (pos.equals("V")) {
                for (String stem : stemmer.findStems(word, POS.VERB)) {
                    if (listv.contains(stem)) {
                        return 1;
                    }
                }
            } else if (pos.equals("N")) {
                for (String stem : stemmer.findStems(word, POS.NOUN)) {
                    if (listn.contains(stem)) {
                        return 1;
                    }
                }
            }
            return 0;
        }

        /**
         * for debug only
         */
        public void print() {
            if (!initialized) {
                init();
            }
            List<String> l = new ArrayList<>();
            l.addAll(listn);
            System.out.println("[" + String.join("]  [", l) + "]");
        }
    }
}

