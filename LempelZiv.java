import java.util.*;

public class LempelZiv {
    /**
     * Take input as a text string, compress it, and return it as a text string.
     * 
     * @param text - The text to compress.
     * @return - The compressed text.
     */
    public static String compress(String text) {
        StringBuilder compressed = new StringBuilder();
        int cursor = 0;
        int windowSize = 100;
        int lookaheadSize = 8;

        while (cursor < text.length()) {
            int length = 1;
            int prevMatchIndex = -1;

            // Find the longest match in the search window
            while (cursor + length <= text.length() && length <= lookaheadSize) {
                String currentSubstring = text.substring(cursor, cursor + length);
                int start = Math.max(0, cursor - windowSize);
                String searchWindow = text.substring(start, cursor);

                int matchPos = searchWindow.indexOf(currentSubstring);
                if (matchPos != -1) {
                    prevMatchIndex = start + matchPos;
                    length++;
                } else {
                    break;
                }
            }

            // If a match is found, record it with the offset and length, plus the next character
            if (prevMatchIndex != -1 && length > 1) {
                length--;
                int offset = cursor - prevMatchIndex;

                compressed.append("[").append(offset).append("|").append(length).append("|");
                compressed.append(cursor + length < text.length() ? text.charAt(cursor + length) : text.charAt(text.length() - 1));
                compressed.append("]");

                cursor += length + 1;
            } else {
                // If no match, record the current character with no offset and length
                compressed.append("[0|0|").append(text.charAt(cursor)).append("]");
                cursor++;
            }
        }

        return compressed.toString();
    }

    /**
     * Decompresses a text string that was compressed using the compress method.
     * 
     * @param compressed - The compressed text to decompress.
     * @return - The decompressed text.
     */
    public static String decompress(String compressed) {
        StringBuilder decompressed = new StringBuilder();
        int cursor = 0;

        // Split the compressed string into tokens based on the delimiter pattern
        for (String token : compressed.split("(?<=])")) {
            // Remove the square brackets and split the token into its components
            String[] parts = token.substring(1, token.length() - 1).split("\\|");

            int offset = Integer.parseInt(parts[0]);
            int length = Integer.parseInt(parts[1]);
            char ch = parts[2].charAt(0);

            // If offset and length are specified, append the matching substring
            if (offset != 0 || length != 0) { 
                for (int j = 0; j < length; j++)
                    decompressed.append(decompressed.charAt(cursor - offset + j));
            }
            decompressed.append(ch);
            cursor = decompressed.length();
        }

        return decompressed.toString();
    }

    public String getInformation() {
        return "";
    }

    public static void main(String[] args) {
        String text1 = "ABABABA,,,...";
        String compressedText1 = LempelZiv.compress(text1);
        String decompressedText1 = LempelZiv.decompress(compressedText1);

        System.out.println("Original text: " + text1);
        System.out.println("Compressed text: " + compressedText1);
        System.out.println("Decompressed text: " + decompressedText1);
    }
}