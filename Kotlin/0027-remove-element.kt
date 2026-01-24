/**
 * Functional Filter
 * Time: O(N) - The array is traversed twice (filter + copy).
 * Space: O(N) - Creates a new filtered list.
 */
class Solution {
    fun removeElement(nums: IntArray, `val`: Int): Int {

        val filtered = nums.filter { it != `val` }

        filtered.forEachIndexed { i, num -> nums[i] = num }

        return filtered.size
    }
}

/**
 * Two Pointers (Swap)
 * Time: O(N) - The array is traversed at most once.
 * Space: O(1) - Mutates the input array in-place without allocating a new array.
 */
class Solution {
    fun removeElement(nums: IntArray, `val`: Int): Int {

        var left = 0
        var right = nums.lastIndex

        while (left <= right) {

            if (nums[left] == `val`) {

                nums[left] = nums[right]
                right--

            } else {

                left++

            }

        }

        return left
    }
}

/**
 * Two Pointers (Overwrite)
 * Time: O(N) - The array is traversed exactly once.
 * Space: O(1) - Mutates the input array in-place without allocating a new array.
 */
class Solution {
    fun removeElement(nums: IntArray, `val`: Int): Int {

        var k = 0

        for (num in nums) {

            if (num !=  `val`) {
                nums[k] = num
                k++
            }

        }

        return k
    }
}