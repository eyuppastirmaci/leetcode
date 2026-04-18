// time is O(n), where n is the length of the strings we go through s once to
// build the map, then through t once to decrement countse each HashMap operation is O(1)
// on average, so the total work scales linearly with input size
//
// space is O(1) thanks to the problem constraints since the input only has
// lowercase English letters, the map can hold at most 26 entries regardless of how long
// the strings are if we drop that constraint, a more general answer would be O(k), where
// k is the alphabet size.
class Solution {
    public boolean isAnagram(String s, String t) {

        if (s.length() != t.length()) {
            return false;
        }

        var map = new HashMap<Character, Integer>();

        for (char c : s.toCharArray()) {
            map.merge(c, 1, Integer::sum);
        }

        for (char c : t.toCharArray()) {
            Integer count = map.get(c);
            if (count == null || count == 0) {
                return false;
            }
            map.put(c, count - 1);
        }

        return true;
    }
}

// time is O(n), two sequential passes: one to tally s, one to drain t
// each step does constant work: an index lookup plus an increment,
// or a decrement-and-compare
//
// space is O(1), the counts array is always 26 slots regardless of input
// this is a stricter O(1) than the HashMap version, it holds by design,
// not because of a problem constraint
//
// no final sweep is needed, s and t are the same length, so total increments
// equal total decrements, if no slot went negative during the second pass,
// every slot must end at zero
//
// splitting the passes matters: with a single interleaved loop, a slot can
// dip negative mid-way even for a true anagram (when t[i] hasn't been seen
// on the s side yet), so early exit would produce false negatives, after s
// is fully tallied, any negative during the t pass is a real mismatch
//
// caveat: this only works for lowercase English letters, for Unicode input
// we would fall back to the HashMap approach
class Solution {
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        var counts = new int[26];

        for (int i = 0; i < s.length(); i++) {
            counts[s.charAt(i) - 'a']++;
            if (--counts[t.charAt(i) - 'a'] < 0) {
                return false;
            }
        }

        return true;
    }
}