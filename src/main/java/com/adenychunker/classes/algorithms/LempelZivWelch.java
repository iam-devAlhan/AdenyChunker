package lzw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LempelZivWelch {
    public static List<Integer> encode(String text){
        int dictSize=256;
        Map<String, Integer> dictionary=new HashMap<>();
        for(int i=0; i<dictSize; i++){
            dictionary.put(String.valueOf((char)i), i);
        }
        String foundChars="";
        List<Integer> result=new ArrayList<>();
        for(char character : text.toCharArray()){
            String charsToAdd=foundChars+character;
            if(dictionary.containsKey(charsToAdd)){
                foundChars=charsToAdd;
            }
            else{
                result.add(dictionary.get(foundChars));
                dictionary.put(charsToAdd, dictSize++);
                foundChars=String.valueOf(character);
            }
        }
        if(!foundChars.isEmpty()){
                result.add(dictionary.get(foundChars));
        }
        return result;
    }
    
    public static String decode(List<Integer> encodedText){
        int dictsize=256;
        Map<Integer, String> dictionary=new HashMap<>();
        for(int i=0; i<dictsize; i++){
            dictionary.put(i, String.valueOf((char)i));
        }
        String characters = String.valueOf((char) encodedText.remove(0).intValue());
        StringBuilder result=new StringBuilder(characters);
        for(int code: encodedText){
            String entry;
            if (dictionary.containsKey(code)) {
                entry = dictionary.get(code); // If the code exists in the dictionary, use the corresponding value
            } else {
                entry = characters + characters.charAt(0); // If the code doesn't exist, create a new string
            }
            result.append(entry);
            dictionary.put(dictsize++, characters + entry.charAt(0));
            characters=entry;
        }
        return result.toString();
    }
}
