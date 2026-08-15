package org.example;

import java.util.Set;
import java.util.Stack;
import javax.print.DocFlavor;

public class Day5
{

    private Set<Character> openingBrackets = Set.of('(', '{', '[' );
    private Set<Character> closingBrackets = Set.of(')', '}', ']' );


    public boolean isBalancedBrackets(String word){
        Stack<Character> opened = new Stack<>();

        for (Character character:word.toCharArray())
        {

            if(openingBrackets.contains(character)){
                opened.push(character);
            }

            if(closingBrackets.contains(character)){
                if(opened.empty()) return false;

                Character latestOpening = opened.pop();

               if(!isClosingIt(latestOpening, character)) return false;

            }

        }

        return true;
    }


    private boolean isClosingIt(Character a, Character b){
        return  (a.equals('(') && b.equals(')')) ||
                (a.equals('{') && b.equals('}')) ||
                (a.equals('[') && b.equals(']'));
    }
}
