package org.example;

public class URLify
{

    public String urlSafeString(String word){
        char[] charArray = word.trim().toCharArray();
        char[] charArrayResult = new char[charArray.length + (totalSpaces(word.trim()) * 2)];

        int index = charArrayResult.length;

        for (int i = charArray.length - 1; i >= 0; i--){
            if(Character.isWhitespace(charArray[i])){
                charArrayResult[--index]='0';
                charArrayResult[--index]='2';
                charArrayResult[--index]='%';
            }
            else {
                charArrayResult[--index]=charArray[i];
            }

        }

        return new String(charArrayResult);
    }

    private int totalSpaces(String word){
        int result = 0;

        for(int i = 0; i<word.length(); i++){

            if(Character.isWhitespace(word.charAt(i)))
                result++;

        }
        return result;
    }
}
