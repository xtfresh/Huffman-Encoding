import java.util.ArrayList;
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
		System.out.println(s);
		System.out.println(map.toString());
		System.out.println("^^^^^ Freq Map ^^^^^^");
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
		
		System.out.println(pq.toString());
		while(pq.size() > 1){
			Node parent = new Node(pq.remove(), pq.remove());
			pq.add(parent);
		}
		System.out.println("^^^^^ Huff tree ^^^^^^");
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
 		System.out.println(map.get('a').list.toString());
 		//System.out.println(map.get('b').list.toString());
 		System.out.println("^^^^^ enc map ^^^^^^");
 		return map;
 	}
 	private static void traverseTree(Node tree, EncodedString es, Map<Character, EncodedString> map){
 		if(tree.left != null && tree.right != null){
 			es.zero();
 			traverseTree(tree.left, es, map);
 			es.one();
 			traverseTree(tree.right, es, map);
 			
 		}
 		else{
 			map.put(tree.character, es);
 			//es.remove();

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
		System.out.println(encodingMap.get(s.charAt(0)).list.toString());
		EncodedString str = new EncodedString();
		System.out.println(s);
        for(int i = 0; i < s.length(); i++){
            if(encodingMap.containsKey(s.charAt(i))){
                str.concat(encodingMap.get(s.charAt(i)));
                System.out.println(encodingMap.get(s.charAt(i)).list.toString());
            }
        }
       // System.out.println(str.list.toString());
        System.out.println("^^^^^ encode ^^^^^^");
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
			if(temp.left == null && temp.right == null){
				string.append(temp.character);
				temp = tree;
			}else 
				if(it.next() == 0){
					temp = temp.left;
				}else
					temp = temp.right;
			
		}
		System.out.println(string.toString());
		return string.toString();
	}
}
