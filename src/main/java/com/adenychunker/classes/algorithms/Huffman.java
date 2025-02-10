package com.adenychunker.classes.algorithms;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.PriorityQueue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

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

    public Huffman(String source) {
        getCharacterFrequency(source);
        createHuffmanTree();
    }
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

    public String encoder(String source) {
        String answer = "";
        for (int i = 0; i < source.length(); i++) {
            answer = answer + encoder.get(source.charAt(i));
        }
        return answer;
    }
    public String decoder(String codedString) {
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

    public void saveToFile(String filename, String encodedText) throws IOException {
        // Writes Data in ctxt format, This format is intended for saving encoded data into a file
        try (FileWriter fw = new FileWriter(filename + ".ctxt");) {
            for (Map.Entry<Character,Integer> entry: fmap.entrySet()) {
                fw.write(entry.getKey() + ":" + entry.getValue() + "\n");
            }
            fw.write("=====\n");
            fw.write(encodedText);
            System.out.println("File Written and saved successfully!! ");
            JOptionPane.showMessageDialog(null, "File Written Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            fw.close();
        }
        catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error writing file", "Error", JOptionPane.ERROR_MESSAGE);
        }
         
    }
    public String decodeEncodedFile(String encodedFile) throws IOException {
        // Decode the file
        boolean isEncoding = false;
        FileReader freader = new FileReader(encodedFile);
        try (BufferedReader buffReader = new BufferedReader(freader)) {
            fmap.clear();
            String line;
            StringBuilder encoded = new StringBuilder();
            while ((line = buffReader.readLine()) != null) {
                if (line.equals("=====")) {
                    isEncoding = true;
                    continue;
                }
                if (isEncoding) {
                    encoded.append(line);
                }
                else {
                    String[] parts = line.split(":");
                    fmap.put(parts[0].charAt(0), Integer.parseInt(parts[1]));
                }
            }
            createHuffmanTree();
            return decoder(encoded.toString());
        }
        catch (IOException er) {
            JOptionPane.showMessageDialog(null, er, "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
} 
