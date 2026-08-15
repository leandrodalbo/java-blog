package org.example;

public class Arrays
{
    /**
     * Time: O(n + m)
     *
     * You're creating a new array containing every element.
     *
     * Space: O(n + m)
     * @param arr0
     * @param arr1
     * @return
     */
    public int[] merge(int[] arr0, int[] arr1){

        int[] merged = new int[arr0.length + arr1.length];

        int a = 0; int b = 0; int z = 0;

        while (a < arr0.length && b < arr1.length)
        {
            if(arr0[a] < arr1[b]){
                merged[z] = arr0[a];
                a++;
            }
            else
            {
                merged[z]=arr1[b];
                b++;
            }
            z++;

        }


        if(a < arr0.length){
            while (a < arr0.length){
                merged[z] = arr0[a];
                a++;
                z++;
            }
        }

        if(b < arr1.length){
            while (b < arr1.length){
                merged[z] = arr1[b];
                b++;
                z++;
            }
        }

        return merged;
    }
}
