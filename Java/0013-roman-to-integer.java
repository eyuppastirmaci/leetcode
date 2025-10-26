/**
 * ASCII Array Lookup Approach
 * Time: O(n) where n is the length of the input string
 * Space: O(1) constant space with fixed 128 size array
 *
 * Key optimization: Uses static ASCII array for O(1) character to value lookup
 * instead of HashMap. Static initializer ensures one time setup.
 *
 * Algorithm: Iterate through string, subtract current value if next value is greater,
 * otherwise add current value. This handles subtraction cases like IV, IX, XL, XC, CD, CM.
 */
class Solution {
    private static final int[] VAL = new int[128];

    static {
        VAL['I']=1;
        VAL['V']=5;
        VAL['X']=10;
        VAL['L']=50;
        VAL['C']=100;
        VAL['D']=500;
        VAL['M']=1000;
    }

    public int romanToInt(String s) {
        int res = 0, n = s.length();

        for (int i = 0; i < n; i++) {
            int v = VAL[s.charAt(i)];
            if (i + 1 < n && v < VAL[s.charAt(i + 1)]) res -= v;
            else res += v;
        }

        return res;
    }
}
