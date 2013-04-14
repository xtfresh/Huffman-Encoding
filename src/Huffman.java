import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class Huffman {

	/**
	 * Builds a frequency map of characters for the given string.
	 * 
	 * This should just be the count of each character.
	 * 
	 * @param s
	 * @return
	 */
	public static Map<Character, Integer> buildFrequencyMap(String s) {
		Map<Character, Integer> map = new HashMap<Character, Integer>();
		for(int i = 0; i < s.length(); i++){
			Integer frequency = map.get(s.charAt(i));
            map.put(s.charAt(i), (frequency == null) ? 1 : frequency + 1);
		}
		return map;
	}
	
	/**
	 * Build the Huffman tree using the frequencies given.
	 * 
	 * The frequency map will not necessarily come from the buildFrequencyMap() method.
	 * 
	 * @param freq
	 * @return
	 */
	public static Node buildHuffmanTree(Map<Character, Integer> freq) {
		PriorityQueue<Node> pq = new PriorityQueue<Node>();
		Set<Character> set = freq.keySet();
		for (Character i: set){
			pq.add(new Node(i, freq.get(i)));
		}
		while(pq.size() > 1){
			Node parent = new Node(pq.remove(), pq.remove());
			pq.add(parent);
		}
		return pq.peek();
	}
	
 	/**
 	 * Traverse the tree and extract the encoding for each character in the tree
 	 * 
 	 * The tree provided will be a valid huffman tree but may not come from the buildHuffmanTree() method.
 	 * 
 	 * @param tree
 	 * @return
 	 */
 	public static Map<Character, EncodedString> buildEncodingMap(Node tree) {
 		Map<Character, EncodedString> map = new HashMap<Character, EncodedString>();
 		EncodedString es = new EncodedString();
 		if(tree.left == null && tree.right == null){
 			es.zero();
 			map.put(tree.character, es);
 		}else
 			traverseTree(tree, es, map);
 		return map;
 	}
 	private static void traverseTree(Node tree, EncodedString es, Map<Character, EncodedString> map){
 		if(tree.left != null && tree.right != null){     
 			if(tree.left != null){
	        	es.zero();
	            traverseTree(tree.left, es, map);
	            es.remove();
	            
	        }
 			
	        if(tree.right != null){
	        	es.one();
	            traverseTree(tree.right,es, map);
	            es.remove();
	            
	        }
 		}else{
		        EncodedString s = new EncodedString();
		        s.list.addAll(es.list);
		 		map.put(tree.character, s);
	 		}
 
 	}
	
	/**
	 * Encode each character in the string using the map provided.
	 * 
	 * If a character in the string doesn't exist in the map ignore it.
	 * 
	 * The encoding map may not necessarily come from the buildEncodingMap() method, but will be correct
	 * for the tree given to decode() when decoding this method's output.
	 * 
	 * @param encodingMap
	 * @param s
	 * @return
	 */
	public static EncodedString encode(Map<Character, EncodedString> encodingMap, String s) {
		EncodedString str = new EncodedString();
        for(int i = 0; i < s.length(); i++){
            if(encodingMap.containsKey(s.charAt(i))){
                str.concat(encodingMap.get(s.charAt(i)));
            }
        }
        return str;
	}
	
	/**
	 * Decode the encoded string using the tree provided.
	 * 
	 * The encoded string may not necessarily come from encode, but will be a valid string for the given tree.
	 * 
	 * (tip: use StringBuilder to make this method faster -- concatenating strings is SLOW)
	 * 
	 * @param tree
	 * @param es
	 * @return
	 */
	public static String decode(Node tree, EncodedString es) {
		StringBuilder string = new StringBuilder(es.length());
		Node temp = tree;
		Iterator<Byte> it = es.iterator();
		for(int i = 0; i < es.length(); i++){
			if(es.length() > 1 )
				if(it.next() == 0){
					temp = temp.left;
				}else{
					temp = temp.right;
				}
			if(temp.left == null && temp.right == null){
				string.append(temp.character);
				temp = tree;
			} 
			
			
		}
		return string.toString();
	}
}
