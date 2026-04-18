// Time: in terms of time complexity, it will be O(n * k) since we have to iterate through n strings, and for each
// string, we process its characters which takes k time on average
// Space: it's O(n * k) because we are storing all the original strings inside the map, grouped by their frequency keys
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {

        var map = new HashMap<String, List<String>>();

        for (String str : strs) {
            var countArray = new int[26];

            for (char c : str.toCharArray()) {
                countArray[c - 'a']++;
            }

            String key = Arrays.toString(countArray);

            map.computeIfAbsent(key, k -> new ArrayList<>()).add(str);
        }

        return new ArrayList<>(map.values());
    }
}