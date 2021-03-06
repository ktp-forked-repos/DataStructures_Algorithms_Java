package darkRealm.CTCI.LinkedLists;

import ADT.LLNode;
import ADT.LinkedList;

import java.util.Stack;

/**
 * Created by Jayam on 2/6/2017.
 */
public class Linked_List {

  /*  [138] Copy List with Random Pointer
  * A linked list is given such that each node contains an additional random pointer which could point to any node in
   * the list or null. Return a deep copy of the list.
  * */
  public static RandomList deepCopyOfRandomList(RandomList list) {
    RandomNode trav = list.head;
    // First Insert newly created deep copy of nodes in to the list
    while (trav != null) {
      RandomNode deepNode = new RandomNode(trav.data);
      deepNode.next = trav.next;
      trav.next = deepNode;
      trav = deepNode.next;
    }

    //Now set the random pointer using the new next operations avalable after insertion of new nodes
    trav = list.head;
    while (trav != null) {
      trav.next.randomPointer = trav.randomPointer;
      trav = trav.next.next;
    }
    // now lets seperate the the newly deeply copied list in to a seprate list
    trav = list.head;
    RandomNode newHead = trav.next;
    trav = trav.next.next;
    RandomNode newTrav = newHead;
    while (trav != null) {
      newTrav.next = trav.next;
      trav = trav.next.next;
      newTrav = newTrav.next;
    }
    return new RandomList(newHead);
  }

  public static class RandomList extends LinkedList {
    public RandomNode head;

    public RandomList(RandomNode head) {
      this.head = head;
    }
  }

  public static class RandomNode {
    public RandomNode randomPointer;
    public RandomNode next;
    public int data;

    public RandomNode(int d) {
      data = d;
    }
  }

  /*  [Prob 2.7] Intersection
  *   Q) Given 2 linked list that meet each other at some node , find the node at which these two linked list intersect
  *   A) we traverse both list while measuring their lengths and if we end up from both list at the last node, then there
   *   is a intersection. Now we take diff of lengths, this is crucial in deciding where the intersecting node will be
   *   the list which is longer, we send a pointer in that list which is ahead by the diff of lengths. Now we traverse
    *   with this pointers onw that is skipped in longer & one from the start of the shorter list, The point at which
    *   they meet at a same node, is the intersection LLNode.
  * */
  public static LLNode intersection(LinkedList l1, LinkedList l2) {
    LLNode trav1, trav2;
    trav1 = l1.head;
    trav2 = l2.head;
    int len1, len2;
    len1 = len2 = 0;
    while (trav1.next != null) {
      trav1 = trav1.next;
      len1++;
    }
    while (trav2.next != null) {
      trav2 = trav2.next;
      len2++;
    }
    if (trav1 == trav2) {// they both intersect
      // get the longer list
      LLNode longer, shorter;
      if (len1 > len2) {
        longer = l1.head;
        shorter = l2.head;
      } else {
        longer = l2.head;
        shorter = l1.head;
      }
      int diff = Math.abs(len1 - len2);
      LLNode skipped = longer;
      // skip the longer list, this is done in order to bring both pointers at same dist from end.
      while (diff != 0) {
        skipped = skipped.next;
        diff--;
      }
      LLNode second = shorter;
      while (skipped != second) {
        skipped = skipped.next;
        second = second.next;
      }
      return skipped;
    }
    return null;
  }

  /*  [Prob 2.5]  Sum List
  *   Q) we have two numbers represented by link list
  *   ( 7 -> 1 -> 6 )+ ( 5 -> + 9 -> 2) i.e 617+295 = 912
  *   (2 -> 1 -> 9)
  * */
  public static LinkedList sumList(LinkedList a, LinkedList b) {
    if (a == null || b == null) {
      return null;
    }
    int carry = 0;
    LLNode sumList = null;
    LLNode sumTrav = null;
    LLNode atrav, btrav;
    atrav = a.head;
    btrav = b.head;
    int sum;
    while (atrav != null && btrav != null) {
      int aNo = atrav != null ? atrav.data : 0;
      int bNo = btrav != null ? btrav.data : 0;
      sum = aNo + bNo + carry;
      carry = sum < 10 ? 0 : 1;
      sum = sum % 10;
      if (sumList == null) {
        sumList = new LLNode(sum);
        sumTrav = sumList;
      } else {
        sumTrav.next = new LLNode(sum);
        sumTrav = sumTrav.next;
      }
      atrav = atrav.next;
      btrav = btrav.next;
    }

    LLNode nextPart = null;
    if (atrav == null && btrav != null) {
      nextPart = btrav;
    } else if (btrav == null && atrav != null) {
      nextPart = atrav;
    }
    while (nextPart != null) {
      sum = nextPart.data + carry;
      carry = sum < 10 ? 0 : 1;
      sum = sum % 10;
      sumTrav.next = new LLNode(sum);
      sumTrav = sumTrav.next;
      nextPart = nextPart.next;
    }
    if (carry == 1) {
      sumTrav.next = new LLNode(carry);
    }
    LinkedList sumRes = new LinkedList();
    sumRes.head = sumList;
    return sumRes;
  }

  /*
   [Prob 2.5]  Sum List Natural, numbers are not in reverse order
  * */
  public static LinkedList sumListNatural(LinkedList a, LinkedList b) {
    Stack<Integer> list1 = new Stack<>();
    Stack<Integer> list2 = new Stack<>();

    LLNode trav = a.head;
    while (trav != null) {
      list1.push(trav.data);
      trav = trav.next;
    }
    trav = b.head;
    while (trav != null) {
      list2.push(trav.data);
      trav = trav.next;
    }
    int x, y, sum, carry;
    carry = sum = 0;
    LLNode sumList = null;
    while (list1.size() != 0 && list2.size() != 0) {
      x = list1.pop();
      y = list2.pop();
      sum = x + y + carry;
      carry = sum > 9 ? 1 : 0;
      sum = sum % 10;
      LLNode temp = new LLNode(sum);
      temp.next = sumList;
      sumList = temp;
    }
    Stack<Integer> stack = list1.size() == 0 ? list2 : list1;
    while (stack.size() > 0) {
      x = stack.pop();
      sum = x + carry;
      carry = sum > 9 ? 1 : 0;
      sum = sum % 10;
      LLNode temp = new LLNode(sum);
      temp.next = sumList;
      sumList = temp;
    }
    if (carry == 1) {
      LLNode temp = new LLNode(carry);
      temp.next = sumList;
      sumList = temp;
    }
    LinkedList sumRes = new LinkedList();
    sumRes.head = sumList;
    return sumRes;
  }

  /*  [Prob] reverse from mid
  * 1 -> 2 -> 3 -> 4 -> 5
  * 1 -> 2 -> 5 -> 4 -> 3
  * */
  public static LinkedList reverseFromMid(LinkedList list) {
    if (list == null || list.head.next == null) return list;
    LLNode head = list.head;
    LLNode slowPrev = null;
    LLNode slow;
    LLNode fast;
    slow = fast = list.head;
    while (fast != null && fast.next != null) {
      slowPrev = slow;
      slow = slow.next;
      fast = fast.next.next;
    }
    LLNode temp;
    LLNode prev = null;
    LLNode trav = slow;
    while (trav != null) {
      temp = trav.next;
      trav.next = prev;
      prev = trav;
      trav = temp;
    }
    slowPrev.next = prev;

    LinkedList res = new LinkedList();
    res.head = head;
    return res;
  }

  /* [Prob 21] Merge Sorted Lists
  * Merge two sorted linked lists and return it as a new list. The new list should be made by splicing together
  * the nodes of the first two lists.
  * Recursive Solution is very compact (in linkedList Class), but has a tradeoff for stack
  * */
  public static LLNode mergeSortedListIterative(LLNode L1, LLNode L2) {
    if (L1 == null && L2 == null) return null;
    else if (L1 == null) return L2;
    else if (L2 == null) return L1;

    LLNode newHead = null;
    LLNode trav = null;
    if (L1.data < L2.data) {
      newHead = L1;
      L1 = L1.next;
    } else {
      newHead = L2;
      L2 = L2.next;
    }
    trav = newHead;
    while (L1 != null && L2 != null) {
      if (L1.data < L2.data) {
        trav.next = L1;
        L1 = L1.next;
      } else {
        trav.next = L2;
        L2 = L2.next;
      }
      trav = trav.next;
    }
    while (L1 != null) {
      trav.next = L1;
      L1 = L1.next;
      trav = trav.next;
    }
    while (L2 != null) {
      trav.next = L2;
      L2 = L2.next;
      trav = trav.next;
    }
    return newHead;
  }
  
	/* [Prob 23] Merge k Sorted Lists
	*	Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.
  * The MergeSortLike method (mergeKListsFinal) is the fasteest as compared to iterative & recursive approach
  */

  public static LLNode mergeKLists(LLNode[] lists) {
//    return mergeKRecur(lists);
    return mergeKIterative(lists);
  }

  private static LLNode mergeKRecur(LLNode[] lists) {
    int min = Integer.MAX_VALUE;
    LLNode trav = null;
    int minIndex = 0;
    for (int i = 0; i < lists.length; i++)
      if (lists[i] != null && lists[i].data < min) {
        min = lists[i].data;
        minIndex = i;
        trav = lists[i];
      }
    if (lists[minIndex] != null) {
      lists[minIndex] = lists[minIndex].next;
      trav.next = mergeKRecur(lists);
    }
    return trav;
  }

  private static LLNode mergeKIterative(LLNode[] lists) {
    if (lists == null || lists.length < 1) return null;
    LLNode head, prev;
    head = prev = null;
    int min;
    int minIndex = 0;
    int exhausted = 0;
    while (exhausted != lists.length) {
      exhausted = 0;
      min = Integer.MAX_VALUE;
      for (int i = 0; i < lists.length; i++) {
        if (lists[i] != null && lists[i].data < min) {
          min = lists[i].data;
          minIndex = i;
        }
        if (lists[i] == null) exhausted++;
      }
      if (lists[minIndex] != null) {
        if (head == null) {
          head = lists[minIndex];
          prev = head;
        } else {
          prev.next = lists[minIndex];
          prev = prev.next;
        }
        lists[minIndex] = lists[minIndex].next;
      }
    }
    prev.next = null;
    return head;
  }

  /* [Prob 23] Merge k Sorted Lists
	*	Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.
  * The MergeSortLike method is the fasteest as compared to iterative & recursive approach
  */
  public static LLNode mergeKListsFinal(LLNode[] lists) {
    if (lists == null || lists.length < 1) return null;
    return mergeList(lists, 0, lists.length - 1);
  }

  private static LLNode mergeList(LLNode[] lists, int low, int high) {
    if (low > high) return null;
    if (low == high) return lists[low];
    LLNode head = new LLNode(0);
    LLNode trav = head;
    LLNode a, b;
    int mid = low + (high - low) / 2;

    a = mergeList(lists, low, mid);
    b = mergeList(lists, mid + 1, high);
    while (a != null && b != null) {
      if (a.data < b.data) {
        trav.next = a;
        a = a.next;
      } else {
        trav.next = b;
        b = b.next;
      }
      trav = trav.next;
    }
    trav.next = a != null ? a : b;
    return head.next;
  }

  /*  [Prob 445] Add Two Numbers II
   *  You are given two non-empty linked lists representing two non-negative integers. The most significant digit comes
   *  first and each of their nodes contain a single digit. Add the two numbers and return it as a linked list.
   *  You may assume the two numbers do not contain any leading zero, except the number 0 itself.
   *  Follow up:
   *  What if you cannot modify the input lists? In other words, reversing the lists is not allowed.
   *  Example:
   *  Input: (7 -> 2 -> 4 -> 3) + (5 -> 6 -> 4)
   *  Output: 7 -> 8 -> 0 -> 7
   *  * */
  public static LinkedList addTwoNumbers(LinkedList l1, LinkedList l2) {
    if (l1 == null || l2 == null) return null;
    Stack<LLNode> stack1 = new Stack<>();
    Stack<LLNode> stack2 = new Stack<>();
    LLNode trav = l1.head;
    while (trav != null) {
      stack1.push(trav);
      trav = trav.next;
    }
    trav = l2.head;
    while (trav != null) {
      stack2.push(trav);
      trav = trav.next;
    }
    int sum = 0;
    int carry = 0;
    LinkedList res = new LinkedList();
    while (!stack1.isEmpty() || !stack2.isEmpty()) {
      if(!stack1.isEmpty()) sum += stack1.pop().data ;
      if(!stack2.isEmpty()) sum += stack2.pop().data ;
      sum +=carry;
      carry = sum / 10;
      sum = sum % 10;
      if (res.head == null) res.head = new LLNode(sum);
      else {
        trav = new LLNode(sum);
        trav.next = res.head;
        res.head = trav;
      }
    }

    if(carry>0){
      trav = new LLNode(carry);
      trav.next = res.head;
      res.head = trav;
    }
    return res;
  }
}