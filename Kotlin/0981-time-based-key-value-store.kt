/**
 * In this problem, multiple values can be stored for each key, and each value is associated with a timestamp.
 * Therefore, we use a HashMap as the data structure; the HashMap’s key is a String, and on the value side, we store the
 * list of TimeEntry objects associated with that key. Each TimeEntry contains timestamp and value information.
 * Since it is guaranteed that timestamp values are in ascending order in set calls, we only need to add the
 * new TimeEntry object to the end of the relevant key’s ArrayList. This ensures that the list associated with each key
 * is automatically maintained in ascending order by timestamp, eliminating the need for additional sorting.
 *
 * In a get call, we first access the list associated with the relevant key via the HashMap in approximately O(1) time.
 * If the key does not exist, we return an empty string. If the key exists, since the list associated with that key is
 * sorted by timestamp, we can perform a binary search. Here, we are not simply looking for the record with a timestamp
 * equal to the given one. What is required is the value with the largest timestamp that is less than or equal to the
 * given timestamp. Therefore, during the binary search, as we find a valid candidate, we update the result and look to
 * the right.
 *
 * We don’t need to use a TreeMap because the advantage of a TreeMap is that it keeps keys sorted. In this problem, since
 * the timestamps are already in ascending order, the sorting is naturally preserved within the ArrayList.
 * Therefore, the HashMap + ArrayList + binary search solution is lighter and more efficient.
 *
 * Time: The set operation has an average time complexity of O(1). This is because we first find the list associated with the
 * key in the HashMap and then append to the end of the ArrayList. The get operation has a time complexity of O(log m).
 * This is because we first access the list associated with the key in the HashMap in an average of O(1) time, and then
 * perform a binary search only on that list. Here, m represents the number of records associated with the key.
 * Therefore, the total complexity is O(log m).
 *
 * Space: The space complexity is O(n). Here, n represents the total number of set calls. This is because we store a
 * TimeEntry for each set call. A HashMap stores keys, and under each key there is a list of TimeEntries associated with
 * that key.
 *
 */
class TimeMap() {

    private val storage = HashMap<String, ArrayList<TimeEntry>>()

    fun set(key: String, value: String, timestamp: Int) {
        val entries = storage.getOrPut(key) { ArrayList() }
        entries.add(TimeEntry(timestamp, value))
    }

    fun get(key: String, timestamp: Int): String {
        val entries = storage[key] ?: return ""
        return findValueAtOrBefore(entries, timestamp)
    }

    private fun findValueAtOrBefore(entries: List<TimeEntry>, timestamp: Int): String {

        var left = 0
        var right = entries.size - 1
        var result = ""

        while (left <= right) {

            val mid = left + (right - left) / 2
            val entry = entries[mid]

            if (entry.timestamp <= timestamp) {
                result = entry.value
                left = mid + 1
            } else {
                right = mid - 1
            }
        }

        return result
    }

    private data class TimeEntry(
        val timestamp: Int,
        val value: String
    )

}