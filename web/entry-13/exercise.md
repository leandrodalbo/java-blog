# Best Profit

The classic "buy low, sell high" problem, solved in a single pass.

## Problem

Given a non-empty array of daily stock prices, find the maximum profit from buying on one day and selling on a later day. You can only hold one share at a time, and you must buy before you sell.

```
prices: [7, 1, 5, 3, 6, 4]

buy at 1 (day 2), sell at 6 (day 5) -> profit = 5
```

## Implementation

```java
public int maxProfit(int[] values) {
    int minPrice = values[0];
    int maxProfit = 0;

    for (int i = 1; i < values.length; i++) {
        maxProfit = Math.max(maxProfit, values[i] - minPrice);
        minPrice = Math.min(minPrice, values[i]);
    }

    return maxProfit;
}
```

One pass, two things tracked as it goes:

- `minPrice`: the lowest price seen *so far*, the best possible buy day up to this point.
- `maxProfit`: the best `price - minPrice` seen so far, i.e. selling today against the cheapest earlier day.

Both update every iteration. Profit is checked *before* the minimum
is updated, so `minPrice` always reflects a day strictly earlier than
today, matching the rule that you must buy before you sell.

## Complexity

Time: O(n), one pass over the array.
Space: O(1), just the two running values.
