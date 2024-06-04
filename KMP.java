/**
 * A new KMP instance is created for every substring search performed. Both the
 * pattern and the text are passed to the constructor and the search method. You
 * could, for example, use the constructor to create the match table and the
 * search method to perform the search itself.
 */
public class KMP {

	/**
	 * Perform KMP substring search on the given text with the given pattern.
	 * 
	 * @param patternString - The pattern styring to search for.
	 * @param textString - The text string to search within.
	 * @return - The starting index of the first substring match if it exists, or -1 if it doesn't.
	 */
	public static int search(String patternString, String textString) {
		// Convert the pattern and text strings to character arrays
		char[] pattern = patternString.toCharArray(), text = textString.toCharArray();
		int[] matchTable = generateMatchTable(pattern);

		// 'k' is the start if the current match in the text
		// 'i' is the position of the current character in the pattern
		for (int k = 0, i = 0; k + i < text.length;) {
			// If characters matchy, increment i'
			if (pattern[i] == text[k + i]) {
				// If the entire pattern is found, return the start index 'k'
				if (++i == pattern.length) { return k; }
				continue;
			}

			// If characters do not match, udpate 'k' and 'i' based on the match table
			k = (matchTable[i] == -1) ? k + i + 1 : k + i - matchTable[i];
			i = (matchTable[i] == -1) ? 0 : matchTable[i];
		}

		// If no match is found, return -1
		return -1;
	}

	/**
	 * Generates the KMP "partial match" table for the given pattern.
	 * The partial match table is used to skip characters while matching.
	 * 
	 * @param pattern - The character array representing the pattern for which to create the match table.
	 * @return - The partial match table as an integer array.
	 */
	public static int[] generateMatchTable(char[] pattern) {
		int[] matchTable = new int[pattern.length];

		// Initialize the first two values of the match table
		matchTable[0] = -1;
		matchTable[1] = 0;

		// 'j' keeps track of the length of the previous longest prefix suffix
		int j = 0;

		// 'pos' is the current position in the pattern being processed
		int pos = 2;

		while (pos < pattern.length) {
			if (pattern[pos - 1] == pattern[j]) // Characters match, increment 'j' and set the match table at 'pos'
				matchTable[pos++] = ++j;
			else if (j > 0) // Characters do not match, fall back in the match table
				j = matchTable[j];
			else // No match found, set the match table at 'pos' to 0 and move to the next position
				matchTable[pos++] = 0;
		}
		
		return matchTable;
	}
}