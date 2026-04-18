// Time: O(n) one pass, each HashSet add is amortized O(1).
//       worst case O(n log n) when many keys collide into the same bucket
//       (after Java 8 treeifies buckets with 8 or more than entries once the table capacity
//       reaches 64, turning bucket lookups into O(log n) with red-black tree).
// Space: O(n) in the worst case, as we may add every element of the array to the set.
class Solution {
    public boolean containsDuplicate(int[] nums) {

        var set = new HashSet<Integer>();

        for (int num: nums)
            if (!set.add(num))
                return true;

        return false;

        // or we can just use stream api
        //return Arrays.stream(nums).distinct().count() < nums.length;
    }
}