package main.java.linked_list_cycle_II;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

class ListNode {
    int val;
    ListNode next;
    ListNode(int x) {
        val = x;
        next = null;
    }
}

public class Solution {
  public ListNode detectCycle(ListNode head) {
    ListNode a = head;
    ListNode b = head;
    int step = 0;
    while (a != b || step == 0) {
      if (b.next == null || b.next.next == null) {
        return new ListNode(-1);
      }
      b = b.next.next;
      a = a.next;
      step ++;
    }
    b = head;
    int index = 0;
    while (a != b) {
      a = a.next;
      b = b.next;
      index ++;
    }
    return new ListNode(index);
  }

  public boolean hasCycle(ListNode head) {
    ListNode cur = head;
    ListNode next;
    while (cur != null) {
      if (cur == cur.next) {
        return true;
      }
      next = cur.next;
      cur.next = cur;
      cur = next;
    }
    return false;
  }

  public int[] twoSum(int[] nums, int target) {
    HashMap<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
      if (map.containsKey(target - nums[i])) {
        return new int[]{map.get(target-nums[i]), i};
      }
      map.put(nums[i], i);
    }
    return new int[0];
  }

  public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    int add = (l1.val + l2.val) / 10;
    ListNode head = new ListNode((l1.val + l2.val) % 10);
    ListNode point = head;
    while (l1.next != null || l2.next != null) {
      if (l1.next == null) {
        point.next = new ListNode((l2.next.val + add) % 10);
        add = (l2.next.val + add) / 10;
        l2 = l2.next;
        point = point.next;
      } else if (l2.next == null) {
        point.next = new ListNode((l1.next.val + add) % 10);
        add = (l1.next.val + add) / 10;
        l1 = l1.next;
        point = point.next;
      } else {
        point.next = new ListNode((l1 .next.val + l2.next.val + add) % 10);
        add = (l1.next.val + l2.next.val + add) / 10;
        l2 = l2.next;
        l1 = l1.next;
        point = point.next;
      }
    }
    if (add != 0) {
      point.next = new ListNode(add);
    }
    return head;
  }

  public String multiply(String a, String b) {
    if (a.equals("0")||b.equals("0")) {
      return "0";
    }
    StringBuilder sb = new StringBuilder();
    int res[] = new int[a.length() + b.length()];
    for (int i = a.length()-1; i >=0;i--) {
      for (int j = b.length()-1; j >=0; j--) {
        int num = (a.charAt(i) - '0')*(b.charAt(j) - '0');
        num += res[i+j+1];
        res[i+j] += num / 10;
        res[i+j+1] = num %10;
      }
    }
    int i = 0;
    while (res[i] == 0){
      i++;
    }
    for (; i < res.length; i++) {
      sb.append(res[i]);
    }
    return sb.toString();
  }

  public int[] plusOne(int[] digits) {
    if (digits.length == 0) {
      return new int[0];
    }
    int add = 1;
    int i = digits.length -1;
    while (i>=0 && add > 0) {
      int num = digits[i] + add;
      digits[i] = num%10;
      add = num/10;
      i--;
    }
    if (add == 0) {
      return digits;
    }
    int[] res = new int[digits.length + 1];
    res[0] = add;
    for (int j = 0; j< digits.length; j++) {
      res[j+1] = digits[j];
    }
    return res;
  }

  public String addBinary(String a, String b) {
    StringBuilder res = new StringBuilder();
    char jw = '0';
    StringBuilder aa = new StringBuilder(a).reverse();
    StringBuilder bb = new StringBuilder(b).reverse();
    for (int i =0; i < Math.max(aa.length(), bb.length()); i++) {
      String num = addBinary(i<aa.length()?aa.charAt(i):'0',i<bb.length()?bb.charAt(i):'0', jw);
      res.append(num.charAt(1));
      jw = num.charAt(0);
    }
    if (jw == '1') {
      res.append('1');
    }
    return res.reverse().toString();
  }

  public String addBinary(char a, char b, char jw) {
    int num = (a-'0') + (b-'0') + (jw-'0');
    switch (num) {
      case 0:
        return "00";
      case 1:
        return "01";
      case 2:
        return "10";
      case 3:
        return "11";
    }
    return null;
  }


  public StringBuilder multiply(String num1, char c1, int jw) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0;i< jw;i++) {
      sb.append('0');
    }
    int add = 0;
    for (int i = 0; i < num1.length(); i++) {
      int num = multiply(num1.charAt(num1.length() - 1 -i), c1, add);
      sb.append(num % 10);
      add = num/10;
    }
    if (add != 0) {
      sb.append(add);
    }
    return sb;
  }

  public int multiply(char c1, char c2, int add) {
    return (c1 - '0') * (c2 - '0') + add;
  }

  public StringBuilder add(StringBuilder a, StringBuilder b) {
    StringBuilder res = new StringBuilder();
    if (a == null) {
      return b;
    }
    int add = 0;
    for (int i = 0; i < Math.max(a.length(), b.length()); i++) {
      int n1 = i < a.length() ? a.charAt(i) - '0':0;
      int n2 = i < b.length() ? b.charAt(i) - '0':0;
      res.append((n1+n2 + add) % 10);
      add = (n1+n2 + add) /10;
    }
    if (add != 0) {
      res.append(add);
    }
    return res;
  }

  public List<Integer> addToArrayForm(int[] A, int K) {
    int resSize = Math.max(A.length, String.valueOf(K).length()) + 1;
    List<Integer> res = Arrays.asList(new Integer[resSize]);
    for (int i = A.length-1; i >= 0; i--) {
      res.set(resSize - A.length + i, (A[i] + K) % 10);
      K = (A[i] + K) / 10;
    }
    for (int i = resSize - A.length - 1; i >= 0 ; i--) {
      res.set(i, K%10);
      K=K/10;
    }
    if (res.get(0) == 0) {
      return res.subList(1, resSize);
    }
    return res;
  }

  public String addStrings(String num1, String num2) {
    StringBuilder sb = new StringBuilder();
    int i = num1.length()-1;
    int j = num2.length()-1;
    int jinwei = 0;
    while (i>=0 || j>=0) {
      int sum ;
      if (i < 0) {
        sum = num2.charAt(j) - '0' + jinwei;
      } else if (j < 0) {
        sum = num1.charAt(i) - '0' + jinwei;
      } else {
        sum = num1.charAt(i) - '0' + num2.charAt(j) - '0' + jinwei;
      }
      sb.append(sum%10);
      jinwei = sum/10;
      i--;
      j--;
    }
    if (jinwei > 0) {
      sb.append(jinwei);
    }
    return sb.reverse().toString();
  }

  public List<String> letterCombinations1(String digits) {
    List<String> letters = Arrays.asList("","", "abc", "def", "ghi","jkl","mno","pqrs","tuv","whyz");
    List<String> res = new ArrayList<>();
    for (int i = 0; i < digits.length(); i++) {
      int num = digits.charAt(i) - '0';
      if (num >9 || num < 0) {
        return null;
      }
      if (num <= 1) {
        continue;
      }
      String letter = letters.get(num);
      if (res.size() == 0) {
        for (int j = 0; j < letter.length(); j++) {
          res.add(letter.charAt(j) + "");
        }

      } else {
        List<String> tmp = new ArrayList<>();
        for (String s : res) {
          for (int j = 0; j < letter.length(); j++) {
            tmp.add(s + letter.charAt(j));
          }
        }
        res = tmp;
      }
    }
    return res;
  }

//  public List<String> letterCombinations(String digits) {
//    List<String> letters = Arrays.asList("","", "abc", "def", "ghi","jkl","mno","pqrs","tuv","whyz");
//    if (digits.length() == 0) {
//      return new ArrayList<>();
//    }
//    if (digits.length() == 1) {
//
//    }
//  }

  public String getLetters(int i) {
    switch (i) {
      case 2:
        return "abc";
      case 3:
        return "def";
      case 4:
        return "ghi";
      case 5:
        return "jkl";
      case 6:
        return "mno";
      case 7:
        return "pqrs";
      case 8:
        return "tuv";
      case 9:
        return "wxyz";
    }
  }

  public static void main(String[] args) {
    System.out.println(new Solution().letterCombinations("23"));
  }

  public static String toString(ListNode node) {
    StringBuilder sb = new StringBuilder();
    while (node != null) {
      sb.append(node.val + ",");
      node = node.next;
    }
    return sb.toString();
  }

  public static ListNode arrayToListNode(int[] s) {
    ListNode head = new ListNode(s[0]);
    ListNode point = head;
    for (int i = 1;i< s.length;i++) {
      point.next = new ListNode(s[i]);
      point = point.next;
    }
    return head;
  }
}