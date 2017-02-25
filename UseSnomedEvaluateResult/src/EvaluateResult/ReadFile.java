package EvaluateResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class ReadFile {
	public static boolean readfile(String filepath) throws FileNotFoundException, IOException {
        try {
        	   
    		    test indentitySentence = new test();
    		    //读取文件夹中的文件
                File file = new File(filepath);
                if (!file.isDirectory()) {
//                        System.out.println("文件");
                        System.out.println("path=" + file.getPath());
                        System.out.println("absolutepath=" + file.getAbsolutePath());
                	 //待切分短语文件
        		        String FileName = "";
        		        String Sentence = "";
        		        Reader reader = null;
//                        System.out.println("name=" + file.getName());
                        //读取txt文件中的内容
                        FileName = file.getAbsolutePath();
                        System.out.println("以字符为单位读取文件内容，一次读一个字节：");  
                        // 一次读一个字符  
                        reader = new InputStreamReader(new FileInputStream(FileName));  
                        int tempchar = -1;  
                        while ((tempchar = reader.read()) != -1) {  
                            // 对于windows下，\r\n这两个字符在一起时，表示一个换行。  
                            // 但如果这两个字符分开显示时，会换两次行。  
                            // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。  
                        	if((char) tempchar == '\n'){
                          	     Sentence = Sentence.substring(0,Sentence.length()-1);
                          	     indentitySentence.GetSentence(Sentence); 
                          	     Sentence = "";
          	                 }else{
          	                     Sentence = Sentence + ((char) tempchar);
          	                 } 
                        }  
                        reader.close();
//                        System.out.println("All Finish!");  

                } else if (file.isDirectory()) {
                        System.out.println("文件夹");
                        String[] filelist = file.list();
                        for (int i = 0; i < filelist.length; i++) {
                                File readfile = new File(filepath + "\\" + filelist[i]);
                                if (!readfile.isDirectory()) {
//                                        System.out.println("path=" + readfile.getPath());
                                       System.out.println("absolutepath="
                                                        + readfile.getAbsolutePath());
                                	    //待切分短语文件
                        		        String FileName = "";
                        		        String Sentence = "";
                        		        Reader reader = null;
                                        //System.out.println("name=" + readfile.getName());
                                        //读取txt文件中的内容
                                        FileName = readfile.getAbsolutePath();
                                        System.out.println("以字符为单位读取文件内容，一次读一个字节：");  
                                        // 一次读一个字符  
                                        reader = new InputStreamReader(new FileInputStream(FileName));  
                                        int tempchar = -1;  
                                        while ((tempchar = reader.read()) != -1) {  
                                            // 对于windows下，\r\n这两个字符在一起时，表示一个换行。  
                                            // 但如果这两个字符分开显示时，会换两次行。  
                                            // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。  
                                        	 // 对于windows下，\r\n这两个字符在一起时，表示一个换行。  
                                            if((char) tempchar == '\n'){
                                           	     Sentence = Sentence.substring(0,Sentence.length()-1);
                                           	     indentitySentence.GetSentence(Sentence); 
                                           	     Sentence = "";
                           	                }else{
                           	                     Sentence = Sentence + ((char) tempchar);
                           	                } 
                                        }  
                                        reader.close();
//                                        System.out.println("All Finish!");

                                } else if (readfile.isDirectory()) {
                                        readfile(filepath + "\\" + filelist[i]);
                                }
                        }

                }

        } catch (FileNotFoundException e) {
                System.out.println("readfile()   Exception:" + e.getMessage());
        }
        return true;
   }
}
