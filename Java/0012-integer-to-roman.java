/**
 * Greedy Approach
 * Time: O(1) - constant time since input is bounded and max iterations is fixed
 * Space: O(1) - StringBuilder size is bounded by max Roman numeral length
 */
class Solution {
    private record RomanValuePair(int value, String symbol) {}

    private static final RomanValuePair[] ROMAN_PAIRS = {
            new RomanValuePair(1000, "M"),
            new RomanValuePair(900, "CM"),
            new RomanValuePair(500, "D"),
            new RomanValuePair(400, "CD"),
            new RomanValuePair(100, "C"),
            new RomanValuePair(90, "XC"),
            new RomanValuePair(50, "L"),
            new RomanValuePair(40, "XL"),
            new RomanValuePair(10, "X"),
            new RomanValuePair(9, "IX"),
            new RomanValuePair(5, "V"),
            new RomanValuePair(4, "IV"),
            new RomanValuePair(1, "I")
    };

    public String intToRoman(int num) {
        if (num < 1 || num > 3999) return "";

        StringBuilder sb = new StringBuilder();
        for (RomanValuePair pair : ROMAN_PAIRS) {
            while (num >= pair.value()) {
                sb.append(pair.symbol());
                num -= pair.value();
            }
        }
        return sb.toString();
    }
}