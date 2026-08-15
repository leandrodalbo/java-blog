package org.example;

import java.util.Arrays;

public class Day4
{

    public static void main(String[] args){


        int[] data = new int[]{1, 2, 3, 4, 5, 6, 7};
        int k = 3;


        Arrays.stream(data).forEach(System.out::println);

    }


    /**
     * reversal trick: reverse whole array, then reverse [0, k-1], then reverse [k, n-1]
     * each element is touched a constant number of times across the three passes, in place
     * <p>
     * O(n) time, O(1) space
     */
    public static void rotateArray(int[] arr, int k){
        rotateSubarray(arr, 0, arr.length - 1);
        rotateSubarray(arr, 0, k - 1);
        rotateSubarray(arr, k, arr.length - 1);
    }

    public static void rotateSubarray(int[] arr, int start, int end){
        while (start < end){

            int aux = arr[start];
            arr[start] = arr[end];
            arr[end] = aux;

            start++;
            end--;
        }
    }

    public static class ListNode{
        int val;
        ListNode next;

        ListNode(int val){
            this.val = val;
        }
    }

    /**
     * reverse iteratively
     * O(N) time, O(1) space
     */
    public static ListNode reverseIterative(ListNode head){
       ListNode prev = null;

        while (head != null){
            ListNode next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }

        return prev;
    }

}
