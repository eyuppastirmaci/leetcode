class Solution {
    fun twoSum(nums: IntArray, target: Int): IntArray {

        val valueIndexMap = mutableMapOf<Int, Int>()

        for ((index, num) in nums.withIndex()) {

            val complement = target - num
            valueIndexMap[complement]?.let {
                return intArrayOf(it, index)
            }

            valueIndexMap[num] = index
        }

        throw IllegalArgumentException()
    }
}