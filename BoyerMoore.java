import java.util.*;

public class BoyerMoore {

	/**
	 * Preprocesses the pattern for the bad character rule in the Boyer-Moore algorithm.
	 * 
	 * @param pattern - The pattern to preprocess.
	 * @return - An array representing the bad character shift values.
	 */
    private static int[] badCharacterRule(String pattern) {
        int[] badCharShift = new int[256]; // Assuming ASCII character set
        Arrays.fill(badCharShift, -1);

		// Fill the bad character shift array with the rightmost position of each character in the pattern
        for (int i = 0; i < pattern.length(); i++)
            badCharShift[(int) pattern.charAt(i)] = i;

        return badCharShift;
    }

	/**
	 * Preprocesses the pattern for the good suffix rule in the Boyer-Moore algorithm.
	 * 
	 * @param pattern - The pattern to preprocess.
	 * @return - An array representing the good suffix shift values.
	 */
    private static int[] goodSuffixRule(String pattern) {
        int m = pattern.length();
        int[] goodSuffixShift = new int[m + 1];
        int[] suffixes = new int[m + 1];

        // Compute the suffix lengths array
        suffixes[m] = m + 1;
        int g = m + 1, f = m;
        for (int i = m - 1; i >= 0; --i) {
			// Compute the length of the suffix
            if (i > g && suffixes[i + m - f - 1] < i - g) {
                suffixes[i] = suffixes[i + m - f - 1];
				continue;
            }
			g = i;
			f = g;
			// Extend the current suffix
			while (g >= 0 && pattern.charAt(g) == pattern.charAt(g + m - f - 1)) { --g; }
			suffixes[i] = f - g;
        }

        // Compute the good suffix shift array
        Arrays.fill(goodSuffixShift, m);
        for (int i = m - 1; i >= 0; --i) {
            if (suffixes[i] == i + 1) {
				// Suffix matches a prefix
                for (int j = 0; j < m - 1 - i; ++j) {
                    if (goodSuffixShift[j] == m)
                        goodSuffixShift[j] = m - 1 - i;
                }
            }
        }
		// Suffix matches a substring
        for (int i = 0; i <= m - 2; ++i)
            goodSuffixShift[m - 1 - suffixes[i]] = m - 1 - i;

        return goodSuffixShift;
    }

	/**
	 * Search for the pattern within the text using the Boyer-Moore algorithm.
	 * 
	 * @param pattern - The pattern to search for.
	 * @param text - The text to search within.
	 * @return - The index of the first occurrence of the pattern in the text, or -1 if not found.
	 */
    public static int search(String pattern, String text) {
        int m = pattern.length(), n = text.length();

		// Check for empty pattern or text
        if (m == 0 || n == 0) { return -1; }

		// Preprocess bad character and good suffix rules
        int[] badCharShift = badCharacterRule(pattern), goodSuffixShift = goodSuffixRule(pattern);

		// Start searching for the pattern within the text
        int s = 0; // s is the shift position of the pattern with respect to the text
        while (s <= (n - m)) {
            int j = m - 1;

			// Compare pattern characters with corresponding text characters
            while (j >= 0 && pattern.charAt(j) == text.charAt(s + j)) { j--; }

            if (j < 0) // If pattern is found, return the index
				return s;
            else // Use the bad character and good suffix rules to calculate the next shift
                s += Math.max(goodSuffixShift[j], j - badCharShift[text.charAt(s + j)]);
        }

        return -1; // Pattern not found
    }
}