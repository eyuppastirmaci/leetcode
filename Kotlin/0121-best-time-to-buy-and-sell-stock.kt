/**
 * Time: we iterate through the prices array provided as input exactly once using a single for loop for each element
 * and we perform only the constant-time minOf and maxOf operations therefore the time complexity increases linearly
 * with the input size and is O(n).
 *
 * Space: we use only two additional variables: minPrice and maxProfit no matter how large the input array becomes
 * the amount of extra memory we use remains constant so the space complexity is O(1).
 */
class Solution {
    fun maxProfit(prices: IntArray): Int {

        var minPrice = Int.MAX_VALUE
        var maxProfit = 0

        for (price in prices) {
            minPrice = minOf(minPrice, price)
            maxProfit = maxOf(maxProfit, price - minPrice)
        }

        return maxProfit
    }
}