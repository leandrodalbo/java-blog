package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The problem: given a list of strings,
 * group the ones that are anagrams of each other, same letters, different order,
 * into buckets.
 * ["eat", "tea", "tan", "ate", "nat", "bat"]
 * ->
 * [["eat", "tea", "ate"], ["tan", "nat"], ["bat"]
 */
public class GroupAnagrams
{

    /**
     * n = number of words
     * k = max word length
     * <p>
     * per word: toCharArray O(k), Arrays.sort O(k log k), new String O(k)
     * <p>
     * n words => O(n * k log k) time
     * <p>
     * O(n * k) space (every word ends up stored once, in some group)
     */
    public Map<String, List<String>> groupAnagramsSortedKeys(List<String> anagrams)
    {
        Map<String, List<String>> groupedAnagrams = new HashMap<>();

        for (String anagram: anagrams){

            char[] chars = anagram.toCharArray();
            Arrays.sort(chars);
            String freqKey = new String(chars);

            if(groupedAnagrams.containsKey(freqKey)){
                groupedAnagrams.get(freqKey).add(anagram);
            }
            else{
                groupedAnagrams.put(freqKey, new ArrayList<>(List.of(anagram)));
            }

        }

        return groupedAnagrams;

    }


    /**
     * n = number of words
     * k = max word length (charFrequencies always allocates a fixed 128-slot array,
     * so it's O(k) to fill, not O(1))
     * <p>
     * per word: charFrequencies O(k), Arrays.toString on a fixed 128-length array O(1)
     * <p>
     * n words => O(n * k) time
     * <p>
     * O(n * k) space (every word ends up stored once, in some group)
     */
    public Map<String, List<String>> groupAnagrams(List<String> anagrams)
    {
        Map<String, List<String>> groupedAnagrams = new HashMap<>();

        for (String anagram: anagrams){

            String freqKey = Arrays.toString(charFrequencies(anagram));

            if(groupedAnagrams.containsKey(freqKey)){
                groupedAnagrams.get(freqKey).add(anagram);
            }
            else{
                groupedAnagrams.put(freqKey, new ArrayList<>(List.of(anagram)));
            }

        }

        return groupedAnagrams;

    }

    private int[] charFrequencies(String word){
        int[] chars = new int[128];

        for(int i = 0; i< word.length(); i++)
        {
            chars[word.charAt(i)]++;
        }

        return chars;
    }

}
