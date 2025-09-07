class Solution {
    public boolean isPalindrome(int x) {
        if (x < 0 || (x != 0 && x % 10 == 0)) return false;
        if (x < 10) return true;

        int original = x;
        int reversed = 0;

        final int MAX_DIV_10 = Integer.MAX_VALUE / 10;

        while (x > 0) {
            int digit = x % 10;

            if (reversed > MAX_DIV_10) return false;
            if (reversed == MAX_DIV_10 && digit > 7) return false;

            reversed = reversed * 10 + digit;
            x /= 10;
        }

        return original == reversed;
    }
}

class Solution {
    public boolean isPalindrome(int x) {
        if (x < 0 || (x != 0 && x % 10 == 0)) return false;
        if (x < 10) return true;

        int leftHalf = x;
        int rightHalf = 0;

        while (leftHalf > rightHalf) {
            rightHalf = (rightHalf * 10) + (leftHalf % 10);
            leftHalf /= 10;
        }

        return leftHalf == rightHalf || leftHalf == (rightHalf / 10);
    }
}