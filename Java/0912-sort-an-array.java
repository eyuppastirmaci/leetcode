/*
 *
 * Merge Sort
 *
 * n: number of elements
 *
 * Time complexity: O(n log n) in the average and worst case
 *                  log n levels of recursion, each level merges n elements in total
 *                  O(n) in the best case, already sorted input skips every merge
 *
 * Space complexity: O(n)
 *                   O(n) for the shared temp array, allocated once
 *                   O(log n) for the recursion stack
 *
 */
class Solution {
    public int[] sortArray(int[] nums) {
        mergeSort(nums, new int[nums.length], 0, nums.length - 1);
        return nums;
    }

    private void mergeSort(int[] nums, int[] temp, int left, int right) {
        if (left >= right) {
            return;
        }

        int mid = left + (right - left) / 2;

        // sort both halves recursively
        mergeSort(nums, temp, left, mid);
        mergeSort(nums, temp, mid + 1, right);

        // largest of the left half <= smallest of the right half, so the range is already sorted
        if (nums[mid] <= nums[mid + 1]) {
            return;
        }

        merge(nums, temp, left, mid, right);
    }

    private void merge(int[] nums, int[] temp, int left, int mid, int right) {
        int i = left;
        int j = mid + 1;
        int k = left;

        // take the smaller element from the two sorted halves
        while (i <= mid && j <= right) {
            if (nums[i] <= nums[j]) {
                temp[k++] = nums[i++];
            } else {
                temp[k++] = nums[j++];
            }
        }

        // copy any remaining elements
        while (i <= mid) {
            temp[k++] = nums[i++];
        }
        while (j <= right) {
            temp[k++] = nums[j++];
        }

        // copy the merged result back into the original array
        for (k = left; k <= right; k++) {
            nums[k] = temp[k];
        }
    }
}

/*
 *
 * Heap Sort
 *
 * n: number of elements
 *
 * Time complexity: O(n log n)
 *                  building the heap bottom-up is O(n)
 *                  n - 1 extractions, each sift down is O(log n)
 *
 * Space complexity: O(1)
 *                   sorts in place and siftDown is iterative, so no recursion stack
 *
 * Trade-off: not stable, equal elements may change their relative order
 *
 */
class Solution {
    public int[] sortArray(int[] nums) {
        int n = nums.length;

        // build a max-heap in place
        for (int i = n / 2 - 1; i >= 0; i--) {
            siftDown(nums, i, n);
        }

        // move the largest element to the end, then restore the heap on the remaining part
        for (int end = n - 1; end > 0; end--) {
            swap(nums, 0, end);
            siftDown(nums, 0, end);
        }

        return nums;
    }

    private void siftDown(int[] nums, int index, int size) {
        while (true) {
            int left = 2 * index + 1;
            if (left >= size) {
                return;
            }

            int right = left + 1;
            int larger = left;
            if (right < size && nums[right] > nums[left]) {
                larger = right;
            }

            if (nums[index] >= nums[larger]) {
                return;
            }

            swap(nums, index, larger);
            index = larger;
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}