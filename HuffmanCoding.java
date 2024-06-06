import java.util.*;

public class HuffmanCoding {
	// Tree node class
	private static class Node {
		char symbol;
		int frequency;
		Node left, right;

		Node(char symbol, int frequency) {
			this.symbol = symbol;
			this.frequency = frequency;
		}

		Node(Node left, Node right) {
			this.symbol = '\0';
			this.frequency = left.frequency + right.frequency;
			this.left = left;
			this.right = right;
		}
	}

	private Node root;
	private Map<Character, String> charToCode;

	/**
	 * Constructs the Huffman tree and maps for encoding and decoding.
	 * 
	 * @param text - The input text to be encoded or decoded.
	 */
	public HuffmanCoding(String text) {
		Map<Character, Integer> frequencyMap = buildFrequencyMap(text);
		root = buildHuffmanTree(frequencyMap);

		// Initialize maps for character to code and code to character
		charToCode = new HashMap<>();

		buildCodes(root, "");
	}

	/**
	 * Builds a frequency map for each character in the given text.
	 * 
	 * @param text - The input text for which to build the frequency map.
	 * @return - A map with characters as keys and their frequencies as values.
	 */
	private Map<Character, Integer> buildFrequencyMap(String text) {
		Map<Character, Integer> frequencyMap = new HashMap<>();
		for (char c : text.toCharArray())
			frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
		
		return frequencyMap;
	}

	/**
	 * Builds the Huffman tree using the character frequency map.
	 * 
	 * @param frequencyMap - A map with characters as keys and their frquencies as values.
	 * @return - The root node of the constructed huffman tree.
	 */
	private Node buildHuffmanTree(Map<Character, Integer> frequencyMap) {
		// Create priority queue to sort by frequency and symbol
		PriorityQueue<Node> priorityQueue = new PriorityQueue<>(
			(n1, n2) -> {
				int i = Integer.compare(n1.frequency, n2.frequency);
				return (i != 0) ? i : Character.compare(n1.symbol, n2.symbol);
			}
		);

		// Construct a leaf node for each symbol and add it to the priority queue
		for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet())
			priorityQueue.add(new Node(entry.getKey(), entry.getValue()));

		// Iterate until there is more than one node in the queue
		while (priorityQueue.size() > 1) {
			Node left = priorityQueue.poll();
			Node right = priorityQueue.poll();
			priorityQueue.add(new Node(left, right));
		}
		
		// Final node is the root of the Huffman tree
		return priorityQueue.poll();
	}

	/**
	 * Recursively builds Huffman codes by traversing the tree.
	 * 
	 * @param node - The current node in the Huffman tree.
	 * @param code - The code built so far for the current node.
	 */
	private void buildCodes(Node node, String code) {
		if (node == null) { return; }

		// if leaf node, store the code for the symbol
		if (node.left == null && node.right == null) {
			charToCode.put(node.symbol, code);
		} else {
			// Traverse left and right children
			buildCodes(node.left, code + "0");
			buildCodes(node.right, code + "1");
		}
	}


	/**
	 * Encodes the given text using the stored Huffman tree.
	 * 
	 * @param text - The input text to encode.
	 * @return - The encoded binary string.
	 */
	public String encode(String text) {
		StringBuilder encoded = new StringBuilder();
		for (char c : text.toCharArray())
			encoded.append(charToCode.get(c));
	
		return encoded.toString();
	}

	/**
	 * Decodes the given binary string using the stored Huffman tree.
	 * 
	 * @param encoded - The encoded binary string.
	 * @return - The decoded text.
	 */
	public String decode(String encoded) {
		StringBuilder decoded = new StringBuilder();
		Node currentNode = root;
		for (char bit : encoded.toCharArray()) {
			currentNode = (bit == '0') ? currentNode.left : currentNode.right;
			if (currentNode.left == null && currentNode.right == null) {
				decoded.append(currentNode.symbol);
				currentNode = root;
			}
		}
		return decoded.toString();
	}

	/**
	 * The getInformation method is here for your convenience, you don't need to
	 * fill it in if you don't wan to. It is called on every run and its return
	 * value is displayed on-screen. You could use this, for example, to print
	 * out the encoding tree.
	 */
	public String getInformation() {
		return "";
	}
}
