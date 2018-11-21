
public final class MaxSum {
    // globala, lagrar start och slut pÃ¥ sekvensen
    // jag orkade inte skriva getters fÃ¶r dom men det borde man kanske
    public static int seqStart = 0;
    public static int seqEnd = -1;

    /**
     * contiguous subsequence sum algorithm. seqStart and seqEnd represent the
     * actual best sequence. Version 1
     */
    // Version 1
    public static int maxSubSum1(int[] a) {
        int maxSum = 0; // 1 op
        for (int i = 0; i < a.length; i++) // Do n times (Does not count index calculations)
            for (int j = i; j < a.length; j++) {// Do n-i times (Does not count index calculations)
                int thisSum = 0; // 1 op
                for (int k = i; k <= j; k++) { // Do j-i+1 times (Does not count index calculations)
                    thisSum += a[k]; // 2 op
                }
                if (thisSum > maxSum) { // 1 op
                    maxSum = thisSum; // 1 op
                    seqStart = i; // 1 op
                    seqEnd = j; // 1 op
                }
            }
        return maxSum;
    }

    // Version 2
    public static int maxSubSum2(int[] a) {
        int maxSum = 0;
        for (int i = 0; i < a.length; i++) {
            int thisSum = 0;
            for (int j = i; j < a.length; j++) {
                thisSum += a[j];
                if (thisSum > maxSum) {
                    maxSum = thisSum;
                    seqStart = i;
                    seqEnd = j;
                }
            }
        }
        return maxSum;
    }

    // Version 3
    public static int maxSubSum3(int[] a) {
        int maxSum = 0;
        int thisSum = 0;
        for (int i = 0, j = 0; j < a.length; j++) {
            thisSum += a[j];
            if (thisSum > maxSum) {
                maxSum = thisSum;
                seqStart = i;
                seqEnd = j;
            } else if (thisSum < 0) {
                i = j + 1;
                thisSum = 0;
            }
        }
        return maxSum;
    }
}