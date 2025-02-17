package com.adenychunker.classes.algorithms;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.PriorityQueue;
import java.io.*;
import javax.swing.*;


public class Huffman {
    class Node implements Comparable<Node> {
        Character data;
        int freq;
        Node left;
        Node right;

        public Node(char data, int freq) {
            this.data = data;
            this.freq = freq;
            this.left = null;
            this.right = null;
        }
        @Override
        public int compareTo(Node other) {
            return this.freq - other.freq;
        }
    }
    private PriorityQueue<Node> queue = new PriorityQueue<>(); 
    private Map<Character, Integer> fmap = new HashMap<>();
    private HashMap<Character, String> encoder;
    private HashMap<String, Character> decoder;

   
    private void getCharacterFrequency(String feeder) {
        for (int i = 0; i < feeder.length(); i++) {
            char cc = feeder.charAt(i);
            if (fmap.containsKey(cc)) {
                int ov = fmap.get(cc);
                ov += 1;
                fmap.put(cc, ov);
            }
            else {
                fmap.put(cc, 1);
            }
        }
        createHuffmanTree();
    }

    private void createHuffmanTree() {
        Set<Map.Entry<Character, Integer>> freqSet = fmap.entrySet();
        for (Map.Entry<Character, Integer> entry: freqSet) {
            Node node = new Node(entry.getKey(), entry.getValue());
            queue.add(node);
        }
        while (queue.size() != 1) {
            Node first = queue.poll();
            Node second = queue.poll();
            Node combined = new Node('\0', first.freq + second.freq);
            combined.left = first;
            combined.right = second;
            queue.add(combined);
        }
        Node tree = queue.poll();
        this.encoder = new HashMap<>();
        this.decoder = new HashMap<>();
        this.initializeEncoderandDecoder(tree, "");
    }

    private void initializeEncoderandDecoder(Node curr, String output) {
        if (curr == null)
            return;

        if (curr.left == null && curr.right == null) {
            this.encoder.put(curr.data, output);
            this.decoder.put(output, curr.data);
            
        }
        initializeEncoderandDecoder(curr.left, output+"0");
        initializeEncoderandDecoder(curr.right, output+"1");
    }

    private String encoder(String source) {
        String answer = "";
        for (int i = 0; i < source.length(); i++) {
            answer = answer + encoder.get(source.charAt(i));
        }
        return answer;
    }
    
    private String decoder(String codedString) {
        String ans = "";
        String key = "";
        for (int i = 0; i < codedString.length(); i++) {
            key = key + codedString.charAt(i);
            if (decoder.containsKey(key)) {
                ans = ans + decoder.get(key);
                key = "";
            }
        }
        return ans;
    }
    
    public void encodeFromFile(String fileName, String outputFile) throws IOException {
    try (BufferedReader bf = new BufferedReader(new FileReader(fileName))) {
        String line;
        StringBuilder content = new StringBuilder();
        while ((line = bf.readLine()) != null) {
            content.append(line).append("\n");
        }

        getCharacterFrequency(content.toString().trim());
        String encoded = encoder(content.toString().trim());

        ByteArrayOutputStream byteArrOut = new ByteArrayOutputStream();
        int extraBits = 0; // Keep track of padding bits

        for (int i = 0; i < encoded.length(); i += 8) {
            String byteString = encoded.substring(i, Math.min(i + 8, encoded.length()));
            extraBits = 8 - byteString.length(); // Track padding bits if last byte
            byteString += "0".repeat(extraBits); // Ensure full 8 bits
            byte byteValue = (byte) Integer.parseInt(byteString, 2);
            byteArrOut.write(byteValue & 0xFF);
        }

        try (FileOutputStream fs = new FileOutputStream(outputFile);
             DataOutputStream oos = new DataOutputStream(fs)) {

            oos.writeInt(fmap.size());
            for (Map.Entry<Character, Integer> entry : fmap.entrySet()) {
                oos.writeByte(entry.getKey());  // 1 byte for character
                oos.writeShort(entry.getValue()); // 2 bytes for frequency
            }

            oos.writeInt(encoded.length()); // Original bit-length for exact decoding
            oos.writeInt(extraBits); // Store padding information
            oos.write(byteArrOut.toByteArray()); // Store compressed data
            JOptionPane.showMessageDialog(null, "Successfully written to: " + outputFile, "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Failed to encode file. Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

public void decodeToFile(String encodedFile, String outputFile) throws IOException {
    try (FileInputStream fs = new FileInputStream(encodedFile);
         DataInputStream dis = new DataInputStream(fs)) {

        int freqSize = dis.readInt();
        fmap.clear();

        for (int i = 0; i < freqSize; i++) {
            char key = (char) dis.readByte(); // Read character as a byte
            int value = dis.readShort(); // Read frequency count as short
            fmap.put(key, value);
        }

        createHuffmanTree();
        int encodedLength = dis.readInt();
        int extraBits = dis.readInt(); // Read padding information

        byte[] encodedBytes = dis.readAllBytes();
        StringBuilder binaryString = new StringBuilder();

        for (byte b : encodedBytes) {
            binaryString.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
        }

        // Remove padding
        if (extraBits > 0) {
            binaryString.setLength(binaryString.length() - extraBits);
        }

        String decodedText = decoder(binaryString.toString());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write(decodedText);
        }

        JOptionPane.showMessageDialog(null, "File successfully decoded to: " + outputFile, "Success", JOptionPane.INFORMATION_MESSAGE);

    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Failed to decode file. Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

}
    
    

