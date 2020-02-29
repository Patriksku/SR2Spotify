package Lyrics.Functions;

import java.util.HashMap;
import java.util.Map;

/**
 * class to handle weird symbols in songs and artist to fit an URL
 * @author Racquel
 */

public class StringSimplifier {

   HashMap<String, String> map = new HashMap<String, String>()
   {
       { put("ä", "a"); }
       { put("å", "a"); }
       { put("ö", "o"); }
       { put("ë", "e"); }
       { put("\\[", ""); }
       { put("\\]", ""); }
       { put("\\(", ""); }
       { put("\\)", ""); }
       { put("\\;", ""); }
       { put("\\:", ""); }
       { put("\\!", ""); }
       { put("\\?", ""); }
       { put("\\=", ""); }
       { put("\\_", ""); }
       { put("\\-", ""); }
       { put("\\,", "+"); }
       { put("\\'", ""); }
       { put("\\&", ""); }
       { put("\\.", ""); }
       { put(" ", "+"); }

   };

   public String simplyString(String t){
       String textToSimplify = t.toLowerCase();
       for(Map.Entry<String, String> entry : map.entrySet()) {
           textToSimplify = textToSimplify.replaceAll(entry.getKey(), entry.getValue());
       }
       return textToSimplify;
   }
}
