/**
 * Sliding Window with Frequency Map
 *
 * Time: O(n * k)
 * Iterates through k starting offsets, and for each offset it slides a window across the string in O(n/k) steps, yielding O(n) work per offset in total.
 *
 * Space: O(m)
 * where m is the number of words in the input array. Holds at most m unique words in the target frequency map as well as the window frequency map.
 */
class Solution {
    fun findSubstring(s: String, words: Array<String>): List<Int> {

        val result = mutableListOf<Int>()

        if (words.isEmpty() || s.isEmpty()) {
            return result
        }

        val wordLength = words[0].length
        val totalWords = words.size
        val totalLength = wordLength * totalWords

        if (s.length < totalLength) {
            return result
        }

        val wordFreqMap = HashMap<String, Int>()

        for (word in words) {

            wordFreqMap.merge(word, 1, Int::plus)

        }

        for (startPos in 0 until wordLength) {
            var left = startPos
            var right = startPos
            val windowFreqMap = HashMap<String, Int>()

            while (right + wordLength <= s.length) {

                val currentWord = s.substring(right, right + wordLength)
                right += wordLength

                if (currentWord !in wordFreqMap) {

                    windowFreqMap.clear()
                    left = right

                } else {

                    windowFreqMap.merge(currentWord, 1, Int::plus)

                    val targetCount = wordFreqMap.getOrDefault(currentWord, 0)

                    while (windowFreqMap.getOrDefault(currentWord, 0) > targetCount) {

                        val leftWord = s.substring(left, left + wordLength)
                        val newCount = windowFreqMap.getOrDefault(leftWord, 0) - 1

                        if (newCount == 0) {
                            windowFreqMap.remove(leftWord)
                        } else {
                            windowFreqMap[leftWord] = newCount
                        }

                        left += wordLength;
                    }

                    if (right - left == totalLength) {
                        result.add(left)
                    }

                }
            }
        }

        return result
    }
}