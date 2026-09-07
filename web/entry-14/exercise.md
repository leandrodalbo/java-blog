# Group Anagrams

Two ways to group words that are anagrams of each other, both hashing on a shared key.

## Problem

Given a list of strings, group the ones that are anagrams of each other (same letters, any order) into buckets.

```
input:  ["eat", "tea", "tan", "ate", "nat", "bat"]
output: [["eat", "tea", "ate"], ["tan", "nat"], ["bat"]]
```

## Tests (RED)

```java
private GroupAnagrams underTest = new GroupAnagrams();

@Test
public void shouldGroupAnagrams() {
    assertThat(underTest.groupAnagrams(List.of("eat", "tea", "tan", "ate", "nat", "bat")).values())
            .containsExactlyInAnyOrder(
                    List.of("eat", "tea", "ate"),
                    List.of("tan", "nat"),
                    List.of("bat")
            );
}

@Test
public void shouldGroupAnagramsUsingSort() {
    assertThat(underTest.groupAnagramsSortedKeys(List.of("eat", "tea", "tan", "ate", "nat", "bat")).values())
            .containsExactlyInAnyOrder(
                    List.of("eat", "tea", "ate"),
                    List.of("tan", "nat"),
                    List.of("bat")
            );
}
```

## Implementation (GREEN)

Both implementations group words by computing a key that is identical for every anagram of the same word, and different otherwise.

```java
public Map<String, List<String>> groupAnagramsSortedKeys(List<String> anagrams) {
    Map<String, List<String>> groupedAnagrams = new HashMap<>();

    for (String anagram : anagrams) {
        char[] chars = anagram.toCharArray();
        Arrays.sort(chars);
        String freqKey = new String(chars);

        if (groupedAnagrams.containsKey(freqKey)) {
            groupedAnagrams.get(freqKey).add(anagram);
        } else {
            groupedAnagrams.put(freqKey, new ArrayList<>(List.of(anagram)));
        }
    }

    return groupedAnagrams;
}
```

Sorting a word's characters gives a key that is identical for every anagram of it: `"eat"`, `"tea"` and `"ate"` all sort to `"aet"`.

```java
public Map<String, List<String>> groupAnagrams(List<String> anagrams) {
    Map<String, List<String>> groupedAnagrams = new HashMap<>();

    for (String anagram : anagrams) {
        String freqKey = Arrays.toString(charFrequencies(anagram));

        if (groupedAnagrams.containsKey(freqKey)) {
            groupedAnagrams.get(freqKey).add(anagram);
        } else {
            groupedAnagrams.put(freqKey, new ArrayList<>(List.of(anagram)));
        }
    }

    return groupedAnagrams;
}

private int[] charFrequencies(String word) {
    int[] chars = new int[128];

    for (int i = 0; i < word.length(); i++) {
        chars[word.charAt(i)]++;
    }

    return chars;
}
```

The second version skips sorting: a 128 slot array counting how many times each character appears is just as good a key, since anagrams always produce the same counts.

## Complexity

Let `n` be the number of words and `k` the length of the longest one.

- `groupAnagramsSortedKeys`: sorting each word costs O(k log k), so O(n * k log k) time overall.
- `groupAnagrams`: counting characters is O(k) per word, so O(n * k) time overall, faster since it avoids the sort.

Both are O(n * k) space: every word ends up stored once, in some group.
