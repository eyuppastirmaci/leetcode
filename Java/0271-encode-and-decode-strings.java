/*
 * Total Time Complexity will be O(N). This is calculated based on the overall number of
 * characters across all strings to encode and decode. We will do 1 complete pass through all input
 * characters while encoding, when we append them to the StringBuilder, as well as doing 1 pass to
 * decode and extract all of the separated strings. Amortized linear complexity to the total number
 * of characters in the encoded string applies in the decode method because the inner and outer
 * pointers together visit each character a constant number of times: the inner pointer scans
 * length-prefix digits up to the separator, and the outer pointer then advances past the payload
 * via substring extraction. Neither pointer ever revisits a character it has already processed.
 *
 * Overall Total Space Complexity will be O(N). The overall space required for the encoded
 * string and for the list of decoded strings that are the final output of the decode method will
 * be O(N). The amount of auxiliary storage (in addition to the required output structures)
 * will remain constant O(1).
 */
public class Codec {

    private static final char WORD_SEPARATOR = '#';

    // Encodes a list of strings to a single string.
    public String encode(List<String> strs) {
        StringBuilder sb = new StringBuilder();

        for (String str : strs) {
            sb.append(str.length()).append(WORD_SEPARATOR).append(str);
        }

        return sb.toString();
    }

    // Decodes a single string to a list of strings.
    public List<String> decode(String s) {

        List<String> strs = new ArrayList<>();

        int i = 0;
        while (i < s.length()) {

            int j = i;
            while (j < s.length() && s.charAt(j) != WORD_SEPARATOR) {
                j++;
            }

            if (j == s.length()) {
                throw new IllegalArgumentException(
                        "Malformed input: separator not found at position " + i
                );
            }

            int len = Integer.parseInt(s.substring(i, j));

            if (j + 1 + len > s.length()) {
                throw new IllegalArgumentException(
                        "Malformed input: declared length " + len + " exceeds remaining input"
                );
            }

            strs.add(s.substring(j + 1, j + 1 + len));

            i = j + 1 + len;
        }

        return strs;
    }
}

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.decode(codec.encode(strs));