package darkRealm.LeetCode;

import darkRealm.CTCI.LinkedLists.LinkedList;
import darkRealm.CTCI.LinkedLists.Node;

/**
 * Created by Jayam on 1/27/2017.
 */
public class LC_Prob_1_50 {

  /*  [Prob 2] : Add Two Numbers
  * You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse
  * order and each of their nodes contain a single digit. Add the two numbers and return it as a linked list.
  * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
  * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
  * Output: 7 -> 0 -> 8
  * */
  public static Node AddTwoNumbers(LinkedList n1, LinkedList n2) {
    int sum = addTwoNodes(n1.head, n2.head, 1);
    LinkedList resList = new LinkedList();
//    while (sum != 0) {
//      resList.add(sum % 10);
//      sum = sum / 10;
//    }

    Node trav = null;
    Node head = null;
    while (sum != 0) {

      if (head == null) {
        head = new Node(sum % 10);
        trav = head;
      } else {
        trav.next = new Node(sum % 10);
        trav = trav.next;
      }

      sum = sum / 10;
    }
//    return resList;
    return head;
  }

  private static int addTwoNodes(Node a, Node b, int powerTen) {
    if (a == null && b == null) {
      return 0;
    }

    int first = a == null ? 0 : a.data;
    int second = b == null ? 0 : b.data;

    int prev = addTwoNodes(a.next, b.next, powerTen * 10);
    first *= powerTen;
    second *= powerTen;
    int sum = prev + first + second;
    return sum;
  }

  /*  [Prob 3] : Longest Substring wihtout repeating chars
  * Given a string, find the length of the longest substring without repeating characters.
  * Examples:
  * Given "abcabcbb", the answer is "abc", which the length is 3.
  * Given "bbbbb", the answer is "b", with the length of 1.
  * Given "pwwkew", the answer is "wke", with the length of 3. Note that the answer must be a substring,
  * "pwke" is a subsequence and not a substring.
  * */
  public static int lengthOfLongestSubstring(String s) {
    int len;
    int maxLen;
    len = maxLen = 0;
    StringBuilder part = new StringBuilder();
    for (int i = 0; i < s.length(); i++) {
      String ch = new String(new char[]{s.charAt(i)});
      int lastFound = part.lastIndexOf(ch);
      if (lastFound > -1) {
        String newPart = part.substring(lastFound + 1);
        part = new StringBuilder(newPart);
        len = part.length();
      }
      part.append(ch);
      len++;
      if (len > maxLen) {
        maxLen = len;
      }
    }
    return maxLen;
  }

  /*  [Prob 5] Longest Palindromic Substring
  *   Q) Given a string s, find the longest palindromic substring in s. You may assume that the maximum length of s is 1000.
  *   Example:
  *   Input: "babad"
  *   Output: "bab"
  *   Note: "aba" is also a valid answer.
  *   Example:
  *   Input: "cbbd"
  *   Output: "bb"
  * *********NOT A GOOD SOLUTION DONOT PAY ATTENTION ITS A WORK IN PROGRESS*********
  * */
  public static String longestPalindrome(String s) {
    int beg = 0;
    int end = s.length() - 1;
    while (beg < end) {
      if (s.charAt(beg) != s.charAt(end)) {
        break;
      }
      beg++;
      end--;
    }
    if (beg >= end) {
      return s;
    }

    String maxPalindromeString = "";
    int maxPalindromeLength = 0;
    int palinStart, palinEnd;
    palinStart = palinEnd = 0;
    int len = 0;
    char leftChar, rightChar;
    char[] arr = s.toCharArray();
    for (int i = 0; i < s.length(); i++) {
      // considering for odd length palindrome
      int left = i == 0 ? i : i - 1;
      int right = arr[i ] == arr[left] ? i  : Integer.MAX_VALUE;
      len = 0;
      while (left >= 0 && right < s.length()) {
        leftChar = arr[left];
        rightChar = arr[right];

        if (leftChar == rightChar) {
          len = right - left;
          if (len > maxPalindromeLength) {
            maxPalindromeLength = len;
            palinStart = left;
            palinEnd = right;
          }
        } else {
          break;
        }
        left--;
        right++;
      }

      left = i == 0 ? i : i - 1;
      right = i<arr.length-1?arr[i +1] == arr[left] ? i+1  : Integer.MAX_VALUE:i;
      len = 0;
      while (left >= 0 && right < s.length()) {
        leftChar = arr[left];
        rightChar = arr[right];

        if (leftChar == rightChar) {
          len = right - left;
          if (len > maxPalindromeLength) {
            maxPalindromeLength = len;
            palinStart = left;
            palinEnd = right;
          }
        } else {
          break;
        }
        left--;
        right++;
      }
    }
    maxPalindromeString = s.substring(palinStart, palinEnd + 1);
    return maxPalindromeString;
  }
}