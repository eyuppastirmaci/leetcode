/**
 * Dynamic Programming
 * Time: O(n * m) - where n is length of string s, m is length of pattern p
 * Space: O(m) - only using two arrays of size m+1
 */
class Solution {
    public boolean isMatch(String s, String p) {
        final int n = s.length(), m = p.length();
        final char[] S = s.toCharArray(), P = p.toCharArray();

        boolean[] dpPrev = new boolean[m + 1];
        boolean[] dpCurr = new boolean[m + 1];

        dpPrev[0] = true;
        for (int j = 2; j <= m; j++) {
            if (P[j - 1] == '*') dpPrev[j] = dpPrev[j - 2];
        }

        for (int i = 1; i <= n; i++) {
            dpCurr[0] = false;

            for (int j = 1; j <= m; j++) {
                char pj = P[j - 1];

                if (pj != '*') {
                    boolean first = (S[i - 1] == pj) || (pj == '.');
                    dpCurr[j] = first && dpPrev[j - 1];
                } else {
                    boolean zeroRepeat = dpCurr[j - 2];
                    boolean oneOrMore = false;
                    char prev = P[j - 2];
                    if ((prev == '.') || (prev == S[i - 1])) {
                        oneOrMore = dpPrev[j];
                    }
                    dpCurr[j] = zeroRepeat || oneOrMore;
                }
            }

            boolean[] tmp = dpPrev;
            dpPrev = dpCurr;
            dpCurr = tmp;
        }

        return dpPrev[m];
    }
}