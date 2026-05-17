/**
 * Time: we first pair each car's position with its speed, which takes O(n). then we sort the cars by position in
 * descending order, which takes O(n log n). after sorting, we make one pass over the cars and do O(1) work for each car.
 * so the total time complexity is O(n log n), dominated by sorting.
 *
 * Space: we create a cars list of size n, so that takes O(n) space. in the worst case, no car catches up to the fleet
 * ahead, so the stack can also hold up to n arrival times. therefore, the auxiliary space complexity is O(n).
 */
class Solution {
    fun carFleet(target: Int, position: IntArray, speed: IntArray): Int {

        val stack = ArrayDeque<Double>()

        // sort cars closest to target first, so we process them front to back. a car behind that needs more time to
        // reach target than the car ahead can never catch up -> it forms a new fleet.
        val cars = position.zip(speed).sortedByDescending { it.first }

        for ((curPosition, curSpeed) in cars) {
            val timeToTarget = (target - curPosition).toDouble() / curSpeed

            if (stack.isEmpty() || timeToTarget > stack.last()) {
                stack.addLast(timeToTarget)
            }
        }

        return stack.size
    }
}

/**
 * Time: we build an index array of size n in O(n), then sort it by position in descending order in O(n log n).
 * after sorting, we make one pass over the indices and do O(1) work per car. so the total time complexity is O(n log n),
 * dominated by sorting.
 *
 * Space: we allocate an indices array of size n, so that takes O(n) space. unlike the stack solution, we only keep
 * fleetCount and the (distance, speed) of the last fleet. therefore, excluding the indices array, the extra tracking
 * space is O(1). the total auxiliary space complexity is O(n) because of the indices array.
 *
 * Note: instead of computing time as a Double (distance / speed), we keep (distance, speed) as Longs and compare via
 * cross-multiplication. this avoids floating point precision issues entirely. since target, position, and speed can
 * each be up to 10^6, the product can reach 10^12, which overflows Int but fits comfortably in Long.
 */
class Solution {
    fun carFleet(target: Int, position: IntArray, speed: IntArray): Int {

        // we need to sort indices by their position, but IntArray doesn't support sorting by a custom key, so we fall
        // back to a boxed Array<Int>.
        val indices = Array(position.size) { it }
        indices.sortByDescending { position[it] }

        var fleetCount = 0
        var lastFleetDistance = 0L
        // sentinel speed of 1 makes the first car always pass the check, since currentDistance is guaranteed positive.
        var lastFleetSpeed = 1L

        for (i in indices) {
            val currentDistance = (target - position[i]).toLong()
            val currentSpeed = speed[i].toLong()

            // if the current car takes longer to reach target than the fleet ahead, it can never catch up and forms a
            // new fleet. we check this by cross-multiplying the two times instead of dividing, which keeps everything
            // in integer arithmetic and avoids any floating point precision issues.
            if (currentDistance * lastFleetSpeed > lastFleetDistance * currentSpeed) {
                fleetCount++
                lastFleetDistance = currentDistance
                lastFleetSpeed = currentSpeed
            }
        }

        return fleetCount
    }
}