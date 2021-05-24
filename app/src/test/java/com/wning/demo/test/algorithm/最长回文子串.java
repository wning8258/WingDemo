package com.wning.demo.test.algorithm;

public class 最长回文子串 {
    public static void main(String[] args) {
        最长回文子串 instance =new 最长回文子串();
        String s = "babad";
        int result = instance.longestPalindrome(s);
        System.out.println(s + "-->" +result);
    }

    public int longestPalindrome(String s) {
        int i =0, j= s.length()-1;
        int result= 0;
        while( i != j) {
            if (s.charAt(i) != s.charAt(j)) {
                i++;
                j--;
                result = 0;
            }
            result =Math.max(result, j - i + 1);
        }
        return result;
    }
}
