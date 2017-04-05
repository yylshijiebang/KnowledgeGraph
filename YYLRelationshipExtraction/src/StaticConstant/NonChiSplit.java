package StaticConstant;

public class NonChiSplit
{
 // private static final String C_E_SEPERATOR = "¡££¡£¿£º£»¡¢£¬£¨£©¡¶¡·¡¾¡¿{}¡°¡±¡®¡¯!?:;,()<>[]{}\"'\n\r\t ";

  public static boolean isCharSeperator(char c)
  {
     return (".¡££¡£¿£º£»¡¢£¬£¨£©¡¶¡·¡¾¡¿{}¡°¡±¡®¡¯!?:;,()<>{}\"\n\r\t ".indexOf(c) != -1);
  }
  public static boolean isSentence(char c){
	 return("!?.".indexOf(c) != -1);
  }
}