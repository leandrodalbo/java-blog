package org.example;

public class BestProfit
{
    public int maxProfit(int[] values) {
        int minPrice = values[0];
        int maxProfit = 0;

        for (int i = 1; i < values.length; i++) {
            maxProfit = Math.max(maxProfit, values[i] - minPrice);
            minPrice = Math.min(minPrice, values[i]);
        }

        return maxProfit;
    }
}
