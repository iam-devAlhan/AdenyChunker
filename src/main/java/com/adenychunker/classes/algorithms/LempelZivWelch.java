package com.adenychunker.classes.algorithms;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import javax.swing.JOptionPane;



public class LempelZivWelch {
    private static List<Integer> result=new ArrayList<>();
    private static long inputSize;
    private static long compressedSize;
    public LempelZivWelch(){
        this.result=result;
    }
    
    
    public static void encode(String filepath){
        String text=readFileToString(filepath);//read source file and convert to string
        int dictSize=256;
        Map<String, Integer> dictionary=new HashMap<>();
        for(int i=0; i<dictSize; i++){
            dictionary.put(String.valueOf((char)i), i);
        }//Populates Dictionary with all 256 ASCII characters as keys and assign them values from 0 to 255
        String foundChars="";
        
        for(char character : text.toCharArray()){//loops over each character of input text
            String charsToAdd=foundChars+character;
            if(dictionary.containsKey(charsToAdd)){
                foundChars=charsToAdd;
            }//if string charsToAdd exist in dictionary then save it as foundChars
            else{//if string charsToAdd does not exist
                result.add(dictionary.get(foundChars));//Add foundChars(longest known sequence) to the list
                dictionary.put(charsToAdd, dictSize++);//Add the new string in the Dictionary
                foundChars=String.valueOf(character);//Set foundChars to current character
            }
        }
        if(!foundChars.isEmpty()){
                result.add(dictionary.get(foundChars));
        }//lastly, if foundChars is not empty then add it to the list, to complete the encoding
        System.out.println(result);
        writeCompressedDataToFile(filepath);// store the list of codes in a compressed file
    }
    
    public static void decode(String sourcePath, String destinationPath){
        List<Integer> encodedText=readCompressedDataFromFile(sourcePath);
        int dictsize=256;
        Map<Integer, String> dictionary=new HashMap<>();
        for(int i=0; i<dictsize; i++){
            dictionary.put(i, String.valueOf((char)i));
        }//Populate Dictionary with all 256 ASCII characters as values and assign them keys from 0 to 255
        String characters = String.valueOf((char) encodedText.remove(0).intValue());
        //remove first code from encodedText and store to Characters
        StringBuilder result=new StringBuilder(characters);
        for(int code: encodedText){//loop over each code in encodedText
            System.out.println(code);
            String entry;
            if (dictionary.containsKey(code)) {
                entry = dictionary.get(code); 
        // If the code exists in the dictionary, store the corresponding string to entry 
            } else {
                entry = characters + characters.charAt(0);
                System.out.println("entry ");
        // If the code doesn't exist, store first the character of the text to entry
            }
            result.append(entry);//append entry to list 
            dictionary.put(dictsize++, characters + entry.charAt(0));
        //add a new value in dictionary by appending string from last iteration+first character of entry
            characters=entry;//set the value of entry to character for next iteration
            System.out.println(result);
        }
        
        writeStringToFile(destinationPath, result.toString());//write result string to the file
    }
    
    
    
    public static String readFileToString(String filePath) {// .txt file input
        String content = "";

        try {
            // Read all bytes from the file and convert them to a string
            content = new String(Files.readAllBytes(Paths.get(filePath)));
            //read the file from path as byte array, convert into string
            inputSize=Files.size(Paths.get(filePath));//store source file size
            if(inputSize>1000000){
                System.out.println("Input File size: "+(float)inputSize/1048576+" MB");
            }else if(inputSize>1000){
                System.out.println("Input File size: "+(float)inputSize/1024+" KB");
            }else if(inputSize<1000){
                System.out.println("Input File size: "+(float)inputSize+" Bytes");
            }//prints source file size
        } catch (IOException e) {
            // Handle the exception (e.g., file not found, permission issues)
            System.err.println("Error reading the file: " + e.getMessage());
        }
        
        return content;
    }
        
    public static void writeCompressedDataToFile(String filePath) {
        int lastDotIndex = filePath.lastIndexOf('.');
        String nameWithoutExtension = filePath.substring(0, lastDotIndex);
        String extension = filePath.substring(lastDotIndex+1);
        filePath= nameWithoutExtension + ".l" + extension;
        //to add L in the file name e.g: .lcsv .ltxt .ljson
    try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath))) {//creates a file at filepath and write in binary mode
        for (int code : result) {
            // Write the code as a variable-length byte sequence
            while (code >= 128) {
                bos.write((code & 127) | 128); // Write 7 bits and set the 8th bit to indicate more bytes
                code >>>= 7; // Shift right by 7 bits
            }
            bos.write(code); // Write the remaining bits
            
        }
        long compressedSize=Files.size(Paths.get(filePath));//to store compressed file size
        if(compressedSize>1000000){
            System.out.println("Compressed File size: "+(float)compressedSize/1048576+" MB");
        }else if(compressedSize>1000){
            System.out.println("Compressed File size: "+(float)compressedSize/1024+" KB");
        }else if(inputSize<1000){
                System.out.println("Input File size: "+(float)inputSize+" Bytes");
        }
        System.out.println("Compression Rate: "+(((float)inputSize/compressedSize)*100)+"%");
        JOptionPane.showMessageDialog(null, "File Compressed Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
    } catch (IOException e) {
        System.err.println("Error writing to the file: " + e.getMessage());
        JOptionPane.showMessageDialog(null, "Error Compressing file", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    
        public static List<Integer> readCompressedDataFromFile(String filePath) {
        List<Integer> compressedData = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath)) {//read file from filepath
            int code = 0; // Stores the reconstructed code
            int shift = 0; // Tracks the number of bits shifted
            int b; // Stores the current byte read from the file

            // Read the file byte by byte
            while ((b = fis.read()) != -1) {
                // Add the 7 bits of the current byte to the code
                code |= (b & 127) << shift;

                // Check if the 8th bit is set (indicates more bytes for this code)
                if ((b & 128) == 0) {
                    // If the 8th bit is not set, this is the last byte for the current code
                    compressedData.add(code); // Add the reconstructed code to the list
                    code = 0; // Reset the code for the next integer
                    shift = 0; // Reset the shift counter
                } else {
                    // If the 8th bit is set, prepare to read the next 7 bits
                    shift += 7;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from the file: " + e.getMessage());
        }

        return compressedData;
    }
        
        public static void writeStringToFile(String filePath, String code) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {//create a file at given path
            fileWriter.write(code);
            JOptionPane.showMessageDialog(null, "File Decompressed Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error Compressing file", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


