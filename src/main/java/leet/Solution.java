package leet;

import com.sun.org.apache.xml.internal.security.keys.content.KeyValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

class ListNode {
    int val;
    ListNode next;
    ListNode(int x) {
        val = x;
        next = null;
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
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

  public int divide(int dividend, int divisor) {
    if (dividend == Integer.MIN_VALUE && divisor==-1) {
      return Integer.MAX_VALUE;
    }
    if (divisor == Integer.MIN_VALUE && dividend == Integer.MIN_VALUE) {
      return 1;
    }
    if (divisor == Integer.MIN_VALUE) {
      return 0;
    }
    int res = 0;
    boolean b = (dividend<0) ^ (divisor<0);
    boolean b1 = dividend == Integer.MIN_VALUE;
    if (b1) {
      dividend = Integer.MAX_VALUE;
    }
    dividend = Math.abs(dividend);
    divisor = Math.abs(divisor);
    int offset = 0;
    while (offset != -1) {
      offset = maxDiv(dividend, divisor);
      if (offset!= -1) {
        dividend -= divisor << offset;
        res += (1 << offset);
      }
    }
    if (b1) {
      offset = maxDiv(dividend+1, divisor);
      if (offset!= -1) {
      res+=(1>>offset);
      }
    }
    return b?-res:res;
  }
  public static int maxDiv(long dividend, long divisor) {
    if (dividend < divisor) {
      return -1;
    }
    int offset = 0;
    while (dividend >= divisor<<offset && offset<30) {
      offset++;
    }
    return offset-1;
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

  public List<String> letterCombinations(String digits) {
    if (digits.length() == 0) {
      return Arrays.asList();
    }
    List<String> strings = new ArrayList<>();
    String letters = getLetters(digits.charAt(0) - '0');
    if (digits.length() == 1) {
      for (int i = 0; i < letters.length(); i ++) {
        strings.add(letters.charAt(i)+"");
      }
      return strings;
    }
    for (String str : letterCombinations(digits.substring(1))) {
      for (int i = 0; i < letters.length(); i ++) {
        strings.add(letters.charAt(i)+str);
      }
    }
    return strings;
  }

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
    return "";
  }

  public int lengthOfLongestSubstring(String s) {
    int maxLength = 0;
    String subStr = "";
    int index = -1;
    for (int i = 0; i < s.length();i++) {
      if ((index = subStr.indexOf(s.charAt(i))) != -1) {
        subStr = (subStr + s.charAt(i)).substring(index + 1);
      } else {
        subStr += s.charAt(i);
      }
      maxLength = Math.max(maxLength, subStr.length());
    }
    return maxLength;
  }

//  public double findMedianSortedArrays(int[] nums1, int[] nums2) {
//
//  }

  public String longestPalindrome1(String s) {
    if (s.length() == 0) {
      return s;
    }
    boolean[][] palindrome = new boolean[s.length()][s.length()];
    int start=0, end=0;
    for (int i = 0; i < s.length(); i++) {
      for (int j = 0; j < s.length(); j++) {
        if (i == j) {
          palindrome[i][j] = true;
        } else if (Math.abs(i-j) == 1) {
          palindrome[i][j] = s.charAt(i) == s.charAt(j);
        } else {
          boolean f = false;
          if (i > j) {
            f = palindrome[i-1][j+1];
          } else {
            f = palindrome[i+1][j-1];
          }
          palindrome[i][j] = f && s.charAt(i) == s.charAt(j);
        }
        if (palindrome[i][j] && Math.abs(i-j) > (end-start)) {
          start = Math.min(i,j);
          end = Math.max(i,j);
        }
      }
    }
    return s.substring(start, end+1);
  }

  public String longestPalindrome(String s) {
    if (s.length() == 0) {
      return s;
    }
    boolean[][] palindrome = new boolean[s.length()][s.length()];
    int start=0, end=0;
    for (int j = 0; j < s.length(); j++) {
      for (int i = 0; i <= j; i++) {
        if (s.charAt(i) != s.charAt(j)) {
          palindrome[i][j] = false;
        } else {
          if (j - i < 2) {
            palindrome[i][j] = true;
          } else {
            palindrome[i][j] = palindrome[i+1][j-1];
          }
        }
        if (palindrome[i][j] && j-i > end-start) {
          end = j;
          start = i;
        }
      }
    }
    return s.substring(start, end+1);
  }

  public int reverse(int x) {
    long num = Math.abs(x);
    long result = 0;
    while (num != 0) {
      result = result * 10 + num%10;
      num = num/10;
    }
    result = x > 0 ? result:0-result;
    if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
      return 0;
    }
    return (int)result;
  }

  public int myAtoi(String str) {
    str = str.trim();
    if (str.length() == 0) {
      return 0;
    }
    if (str.charAt(0) != '-' && str.charAt(0) != '+' && (str.charAt(0) < '0' || str.charAt(0) > '9')) {
      return 0;
    }
    boolean fu = str.charAt(0) == '-';
    long result = 0;
    if (str.charAt(0) != '-' && str.charAt(0) != '+') {
      result = str.charAt(0) - '0';
    }
    for (int i = 1 ; i < str.length(); i ++) {
      if (str.charAt(i)< '0' || str.charAt(i) > '9') {
        break;
      } else {
        result = result * 10 + str.charAt(i) - '0';
      }
      if (fu && (0-result) < Integer.MIN_VALUE) {
        return Integer.MIN_VALUE;
      }
      if (!fu && result>Integer.MAX_VALUE) {
        return Integer.MAX_VALUE;
      }
    }
    if (fu) {
      return (int)(-result);
    }
    return (int)result;
  }

  public ListNode removeNthFromEnd(ListNode head, int n) {
    ListNode start = new ListNode(0);
    start.next = head;
    ListNode n1 = start;
    ListNode n2 = start;
    for (int i = 0; i < n; i++) {
      n1 = n1.next;
      if (n1 == null) {
        return head;
      }
    }
    while (n1.next != null) {
      n2 = n2.next;
      n1 = n1.next;
    }
    n2.next = n2.next.next;
    return start.next;
  }

  public int maxArea(int[] height) {
    if (height.length <= 1) {
      return 0;
    }
    int maxArea = 0;
    int start = 0;
    int end = height.length - 1;

    while (start < end) {
      maxArea = Math.max(maxArea, Math.min(height[start], height[end]) * (end-start));
      if (height[start] < height[end]) {
        start ++;
      } else if (height[start] >= height[end]) {
        end--;
      }
    }
    return maxArea;
  }

  public int trap(int[] height) {
    int left = 0;
    int area = 0;
    int res = 0;
    for (int i = 1; i < height.length; i++) {
      if (height[i]>=height[left]) {
        res += (i-left-1) * height[left] - area;
        left = i;
        area = 0;
      } else {
        area += height[i];
      }
    }
    int right = height.length - 1;
    area = 0;
    for (int i = right-1; i >= left; i--) {
      if (height[i]>=height[right]) {
        res += (right - i -1) * height[right] - area;
        right = i;
        area = 0;
      } else {
        area += height[i];
      }
    }
    return res;
  }

  public int[] productExceptSelf(int[] nums) {
    int[] res = new int[nums.length];
    int left = 1;
    int right = 1;
    for (int i = 0; i < nums.length; i++) {
      res[i] = left;
      left *= nums[i];
    }
    for (int j = nums.length-1; j >= 0; j--) {
      res[j] *=right;
      right *=nums[j];
    }
    return res;
  }

  public boolean isSameTree(TreeNode p, TreeNode q) {
    if (p == null && q == null) {
      return true;
    }
    if (p == null || q == null) {
      return false;
    }
    return p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
  }

  public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> res = new ArrayList<>();
    Queue<TreeNode> linkedList = new LinkedList<>();
    linkedList.add(root);
    linkedList.add(null);
    List<Integer> list = new ArrayList<>();
    while (!linkedList.isEmpty()) {
      TreeNode node = linkedList.poll();
      if (node == null) {
        if (!list.isEmpty()) {
          res.add(list);
        }
        list = new ArrayList<>();
        if (linkedList.peek() != null) {
          linkedList.add(null);
        }
      } else {
        list.add(node.val);
        if (node.left != null) {
          linkedList.add(node.left);
        }
        if (node.right != null) {
          linkedList.add(node.right);
        }
      }
    }
    return res;
  }

  public List<List<Integer>> levelOrderBottom(TreeNode root) {
      LinkedList<List<Integer>> res = new LinkedList<>();
      Queue<TreeNode> linkedList = new LinkedList<>();
      linkedList.add(root);
      linkedList.add(null);
      List<Integer> list = new ArrayList<>();
      while (!linkedList.isEmpty()) {
        TreeNode node = linkedList.poll();
        if (node == null) {
          if (!list.isEmpty()) {
            res.addFirst(list);
          }
          list = new ArrayList<>();
          if (linkedList.peek() != null) {
            linkedList.add(null);
          }
        } else {
          list.add(node.val);
          if (node.left != null) {
            linkedList.add(node.left);
          }
          if (node.right != null) {
            linkedList.add(node.right);
          }
        }
      }
      return res;
  }

  public List<Double> averageOfLevels(TreeNode root) {
    List<Double> res = new ArrayList<>();
    Queue<TreeNode> linkedList = new LinkedList<>();
    linkedList.add(root);
    linkedList.add(null);
    double sum = 0.0;
    int size = 0;
    while (!linkedList.isEmpty()) {
      TreeNode node = linkedList.poll();
      if (node == null) {
        res.add(sum/size);
        sum = 0.0;
        size = 0;
        if (linkedList.peek() != null) {
          linkedList.add(null);
        }
      } else {
        sum += node.val;
        size++;
        if (node.left != null) {
          linkedList.add(node.left);
        }
        if (node.right != null) {
          linkedList.add(node.right);
        }
      }
    }
    return res;
  }

  public List<List<Integer>> threeSum(int[] nums) {
    List<List<Integer>> lists = new ArrayList<>();
    Arrays.sort(nums);
    for (int i = 0; i < nums.length-2; i++) {
      int start = i+1;
      int end = nums.length-1;
      while (start < end) {
        int sum = nums[i] + nums[start] + nums[end];
        if (sum > 0) {
          end--;
        } else if (sum < 0) {
          start ++;
        } else {
          lists.add(Arrays.asList(nums[i], nums[start], nums[end]));
          start++;
          while (start<end && nums[start-1] == nums[start]) {
            start++;
          }
        }
      }
      while (i < nums.length-2 && nums[i+1] == nums[i]) {
        i++;
      }
    }
    return lists;
  }

  public int threeSumClosest(int[] nums, int target) {
    Arrays.sort(nums);
    int closest = Integer.MAX_VALUE;
    int res = 0;
    for (int i = 0; i < nums.length-2; i++) {
      int start = i+1;
      int end = nums.length-1;
      while (start < end) {
        int sum = nums[i] + nums[start] + nums[end];
        if (Math.abs(sum-target) < closest) {
          closest = Math.abs(sum - target);
          res = sum;
        }
        if (sum > target) {
          end--;
        } else if (sum < target) {
          start ++;
        } else {
          return sum;
        }
      }
    }
    return res;
  }

  public List<Integer> inorderTraversal1(TreeNode root) {
    List<Integer> list = new ArrayList<>();
    if (root == null) {
      return list;
    }
    list.addAll(inorderTraversal1(root.left));
    list.add(root.val);
    list.addAll(inorderTraversal1(root.right));
    return list;
  }

  public List<Integer> inorderTraversal(TreeNode root) {
    List<Integer> list = new ArrayList<>();
    Stack<TreeNode> stack = new Stack<>();
    if (root == null) {
      return list;
    }
    stack.push(root);
    while (!stack.empty()) {
      TreeNode treeNode = stack.pop();
      if (treeNode.left == null && treeNode.right == null) {
        list.add(treeNode.val);
        continue;
      }
      if (treeNode.right != null) {
        stack.push(treeNode.right);
      }
      stack.push(new TreeNode(treeNode.val));
      if (treeNode.left != null) {
        stack.push(treeNode.left);
      }
    }
    return list;
  }

  public static String toString(List<Integer> integers) {
    if (integers.size() == 0) {
      return "";
    }
    StringBuilder stringBuilder = new StringBuilder();
    for (Integer in : integers) {
      stringBuilder.append(",").append(in);
    }
    return stringBuilder.substring(1);
  }

  public boolean isValidBST(TreeNode root) {
    if (root == null) {
      return true;
    }
    List<Integer> list = new ArrayList<>();
    Stack<TreeNode> queue = new Stack<>();
    queue.push(root);
    while (!queue.isEmpty()) {
      TreeNode treeNode = queue.pop();
      if (treeNode.left == null && treeNode.right == null) {
        if (list.size() == 0 || treeNode.val > list.get(list.size()-1)) {
          list.add(treeNode.val);
          continue;
        } else {
          return false;
        }
      }
      if (treeNode.right != null) {
        queue.add(treeNode.right);
      }
      queue.add(new TreeNode(treeNode.val));
      if (treeNode.left != null) {
        queue.add(treeNode.left);
      }
    }
    return true;
  }

  public int[] findMode(TreeNode root) {
    if (root == null) {
      return new int[0];
    }
    Map<Integer, Integer> list = new HashMap<>();
    Stack<TreeNode> queue = new Stack<>();
    queue.push(root);
    int max = 0;
    Set<Integer> list1 = new HashSet<>();
    while (!queue.isEmpty()) {
      TreeNode treeNode = queue.pop();
      if (treeNode.left == null && treeNode.right == null) {
        list.compute(treeNode.val, (k,v) -> v==null?1:v+1);
        if (list.get(treeNode.val) > max) {
          list1 = new HashSet<>();
          list1.add(treeNode.val);
          max = treeNode.val;
        } else if (list.get(treeNode.val) == max) {
          list1.add(treeNode.val);
        }
        continue;
      }
      if (treeNode.right != null) {
        queue.add(treeNode.right);
      }
      queue.add(new TreeNode(treeNode.val));
      if (treeNode.left != null) {
        queue.add(treeNode.left);
      }
    }
    int[] res = new int[list1.size()];
    int i = 0;
    for (Integer in : list1) {
      res[i] = in;
      i++;
    }
    return res;
  }

  public boolean isSymmetric(TreeNode root) {
    return root == null || isSymmetricTree(root.left, root.right);
  }

  public boolean isSymmetricTree(TreeNode left, TreeNode right) {
    return left == null
        && right == null
        || left != null
        && right != null
        && left.val == right.val
        && isSymmetricTree(left.left, right.right)
        && isSymmetricTree(left.right, right.left);
  }

  public TreeNode buildTree(int[] preorder, int[] inorder) {
    return buildTree1(preorder, inorder, 0,preorder.length, 0, preorder.length);
//    return buildTree1(preorder, inorder, 0, )
  }

  public TreeNode buildTree(int[] preorder, int[] inorder, int pStart, int pEnd, int iStart, int iEnd) {
    if (pEnd == pStart || pEnd+iStart != pStart+iEnd) {
      return null;
    }
    int rootV = preorder[pEnd];
    TreeNode treeNode = new TreeNode(rootV);
    for (int i = 0; i < iEnd-iStart; i++) {
      if (inorder[i + iStart] == rootV) {
        treeNode.left = buildTree(preorder, inorder, pStart+1, pStart+i+1, iStart, iStart+i);
        treeNode.right = buildTree(preorder, inorder, pStart+i+1, pEnd, iStart+i+1, iEnd);
      }
    }
    return treeNode;
  }

  public TreeNode buildTree1(int[] inorder, int[] postorder, int iStart, int iEnd, int pStart, int pEnd) {
    if (pEnd == pStart || pEnd+iStart != pStart+iEnd) {
      return null;
    }
    int rootV = postorder[pEnd-1];
    TreeNode treeNode = new TreeNode(rootV);
    for (int i = 0; i < iEnd-iStart; i++) {
      if (inorder[i + iStart] == rootV) {
        treeNode.left = buildTree1(inorder, postorder,iStart, iStart+i, pStart, pStart+i);
        treeNode.right = buildTree1(inorder, postorder, iStart+i+1, iEnd,pStart+i, pEnd-1);
      }
    }
    return treeNode;
  }

  public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
    if (t1 == null && t2 == null) {
      return null;
    }
    if (t1 != null && t2 == null) {
      return t1;
    }
    if (t1 == null) {
      return t2;
    }
    TreeNode res = new TreeNode(t1.val + t2.val);
    res.left = mergeTrees(t1.left, t2.left);
    res.right = mergeTrees(t1.right, t2.right);
    return res;
  }

  public int maxPathSum1(TreeNode root) {
    if (root == null) {
      return 0;
    }
    maxValueSum(root);
    int max = Integer.MIN_VALUE;
    Queue<TreeNode> queue = new LinkedList<>();
    queue.add(root);
    while (!queue.isEmpty()) {
      TreeNode poi = queue.poll();
      int value = poi.val;
      if (poi.left != null && poi.right != null) {
        value += Math.max(0,Math.min(poi.left.val, poi.right.val));
      }
      if (value > max) {
        max = value;
      }
      if (poi.left != null) {
        queue.add(poi.left);
      }
      if (poi.right != null) {
        queue.add(poi.right);
      }
    }
    return max;
  }

  public ListNode mergeKLists(ListNode[] lists) {
    ListNode point = null;
    ListNode head = null;
    int index = 0;
    while (index != -1) {
      index = -1;
      for (int i = 0; i < lists.length;i++) {
        if (lists[i] == null) {
          continue;
        }
        if (index==-1 || lists[index].val > lists[i].val) {
          index = i;
        }
      }
      if (index != -1) {
        if (point == null) {
          point = lists[index];
        } else {
          point.next = lists[index];
          point = point.next;
        }
        lists[index] = lists[index].next;
        if (head == null) {
          head = point;
        }
      }
    }
    return head;
  }

  public int romanToInt(String s) {
    if (s.length() == 1) {
      return romanValue(s.charAt(0));
    }
    int res = 0;
    for (int i =0; i<s.length()-1;i++) {
      if (romanValue(s.charAt(i)) < romanValue(s.charAt(i+1))) {
        res-=romanValue(s.charAt(i));
      }else {
        res+=romanValue(s.charAt(i));
      }
    }
    res+=romanValue(s.charAt(s.length()-1));
    return res;
  }

  public static int romanValue(char c) {
    switch (c) {
      case 'I':return 1;
      case 'V':return 5;
      case 'X':return 10;
      case 'L':return 50;
      case 'C':return 100;
      case 'D':return 500;
      case 'M':return 1000;
      default:return -1;
    }
  }

  public static String intValueRoman(int i) {
    switch (i) {
      case 1:return "I";
      case 5:return "V";
      case 10:return "X";
      case 50:return "L";
      case 100:return "C";
      case 500:return "D";
      case 1000:return "M";
      default:return "";
    }
  }

  public static String intValueRoman(int i, int base) {
    String one = intValueRoman(base);
    String two = intValueRoman(base*5);
    String three = intValueRoman(base*10);
    switch (i) {
      case 1: return one;
      case 2: return one+one;
      case 3: return one+one+one;
      case 4: return one+two;
      case 5: return two;
      case 6: return two+one;
      case 7: return two+one+one;
      case 8: return two+one+one+one;
      case 9: return one+three;
    }
    return "";
  }

  public String intToRoman(int num) {
    int base = 1;
    String res = "";
    while (num > 0) {
      res = intValueRoman(num%10, base).concat(res);
      num/=10;
      base = base*10;
    }
    return res;
  }

  public int maxPathSum(TreeNode root) {
    if (root == null) {
      return Integer.MIN_VALUE;
    }
    int left = maxPathSum(root.left);
    int right = maxPathSum(root.right);
    int max = Math.max(left, right);
    int min = Math.min(left, right);
    if (min > 0) {
      return Math.max(max, max + min + root.val);
    } else if (max > 0) {
      return Math.max(max, max + root.val);
    } else {
      return Math.max(max, root.val);
    }
  }

  public int maxValueSum(TreeNode root) {
    if (root == null) {
      return 0;
    }
    root.val = root.val + Math.max(0,Math.max(maxValueSum(root.left), maxValueSum(root.right)));
    return root.val;
  }

  public int[][] merge(int[][] intervals) {
    int[] num = new int[2*intervals.length];
    HashMap<Integer,Integer> start = new HashMap<>();
    HashMap<Integer,Integer> end = new HashMap<>();
    for(int i = 0; i< intervals.length;i++) {
      num[2*i] = intervals[i][0];
      num[2*i+1] = intervals[i][1];
      start.compute(intervals[i][0], (k,v) -> v==null?1:v+1);
      end.compute(intervals[i][1], (k,v) -> v==null?1:v+1);
    }
    Arrays.sort(num);
    List<int[]> res = new ArrayList<>();
    int startIndex = -1;
    int ennIndex = -1;
    int startNum = 0;
    for (int i = 0; i < num.length;i++) {
      if (start.containsKey(num[i])) {
        if (startNum == 0) {
          startIndex = i;
        }
        startNum++;
        start.computeIfPresent(num[i], (k,v)->v-1);
        if (start.get(num[i]) == 0) {
          start.remove(num[i]);
        }
        continue;
      }
      if (end.containsKey(num[i])) {
          startNum--;
          if (startNum == 0) {
            ennIndex = i;
            res.add(new int[]{num[startIndex], num[ennIndex]});
          }
        end.computeIfAbsent(num[i], v->v-1);
        if (end.get(num[i]) == 0) {
          end.remove(num[i]);
        }
      }
    }
    int[][] result = new int[res.size()][2];
    for (int i = 0; i < res.size();i++) {
      result[i] = res.get(i);
    }
    return result;
  }

  public int minPathSum(int[][] grid) {
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        if (i!= 0 && j != 0) {
          grid[i][j] = grid[i][j] + Math.min(grid[i-1][j], grid[i][j-1]);
        }
        if (i==0 && j != 0) {
          grid[i][j] = grid[i][j] + grid[i][j-1];
        }
        if (j==0 && i != 0) {
          grid[i][j] = grid[i][j] + grid[i-1][j];
        }
      }
    }
    return grid[grid.length-1][grid[grid.length-1].length-1];
  }

  public boolean isPalindrome(int x) {
    if (x < 0) {
      return false;
    }
    if (x <10) {
      return true;
    }
    if (x %10 ==0) {
      return false;
    }
    int a = 0;
    while (a < x) {
      a = a*10 + x%10;
      x = x/10;
      if (a == x || a == x/10) {
        return true;
      }
    }
    return false;
  }

  public boolean isMatch(String s, String p) {
    boolean[][] res = new boolean[s.length()+1][p.length()+1];
    for (int i = s.length(); i>=0;i-- ){
      for (int j = p.length(); j>=0; j--) {
        if (j==p.length()) {
          res[i][j] = i == s.length();
          continue;
        }
        if (i == s.length()) {
          res[i][j] = (j+1) < p.length() && p.charAt(j+1) == '*' && res[i][j+2];
          continue;
        }
        boolean firstMatch = i<s.length() && (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.');
        if (j + 1 < p.length() && p.charAt(j+1) == '*') {
          res[i][j] = res[i][j+2] || (firstMatch && res[i+1][j]);
        } else {
          res[i][j] = firstMatch && res[i+1][j+1];
        }
      }
    }
    return res[0][0];
  }


  public List<String> generateParenthesis(int n) {
    if(n==0) {
      return new ArrayList<>();
    }
    Set<String> set = new HashSet<>();
    process(0, "", 2*n,n, set);
    return new ArrayList<>(set);
  }

  public void process (int num, String str, int size, int n, Set<String> strings) {
    if(num == 0 && str.length() == size) {
      strings.add(str);
      return;
    }
    if (num == 0 && n > 0) {
      process(num+1, str+"(",size,n-1, strings);
    } else if (num>0 && n>0) {
      process(num+1, str+"(", size, n-1,strings);
      process(num-1, str+")", size, n,strings);
    } else if (n==0) {
      process(num-1, str+")", size, n,strings);
    }
  }

  public ListNode swapPairs(ListNode head) {
    if (head == null || head.next == null) {
      return head;
    }
    ListNode pre = null;
    ListNode a = head;
    ListNode b = head.next;
    head = b;
    while (a!= null && b != null) {
      a.next = b.next;
      b.next = a;
      if (pre != null) {
        pre.next = b;
      }
      pre = a;
      a = a.next;
      if (a != null) {
        b = a.next;
      }
    }
    return head;
  }

  public int removeElement(int[] nums, int val) {
    int num = nums.length;
    int newIndex = 0;
    for (int i = 0; i < nums.length; i++) {
      if (nums[i] != val) {
        nums[newIndex] = nums[i];
        newIndex++;
      } else {
        num--;
      }
    }
    return num;
  }

  public int strStr(String haystack, String needle) {
    if (needle.length() == 0) {
      return 0;
    }
    for (int i = 0; i < haystack.length()-needle.length() + 1;i++) {
      if (haystack.substring(i,i+needle.length()).equals(needle)) {
        return i;
      }
    }
    return -1;
  }

  public List<Integer> findSubstring(String s, String[] words) {
    List<Integer> list = new ArrayList<>();
    if (words.length == 0) {
      return list;
    }
    if (words.length == 1) {
      for (int i = 0; i <s.length()-words[0].length()+1;i++) {
        if (s.substring(i,i+words[0].length()).equals(words[0])) {
          list.add(i);
        }
      }
    } else {

    }

    return list;
  }

  public int climbStairs(int n) {
    if (n <= 1) {
      return 1;
    }
    int[] num = new int[n+1];
    num[1] = 1;
    num[0] = 1;
    for (int i = 2; i<= n; i++) {
      num[i] = num[i-1] + num[i-2];
    }
    return num[n];
  }

  public int maxDepth(TreeNode root) {
    if(root == null) {
      return 0;
    }
    return 1+ Math.max( maxDepth(root.left), maxDepth(root.right));
  }

  public int maxSubArray(int[] nums) {
    int[]max = new int[nums.length];
    max[0] = nums[0];
    int m = max[0];
    for (int i = 1; i < nums.length; i++) {
      max[i] = Math.max(nums[i], nums[i] + max[i-1]);
      if (m < max[i]) {
        m = max[i];
      }
    }
    return m;
  }

  public ListNode reverseKGroup(ListNode head, int k) {
    if (head == null || k ==1) {
      return head;
    }
    List<ListNode> listNodes = new ArrayList<>();
    listNodes.add(head);
    for (int i = 1; i < k; i++) {
      listNodes.add(listNodes.get(i-1) == null? null:listNodes.get(i-1).next);
    }
    if (listNodes.get(k-1) == null) {
      return head;
    }
    head = listNodes.get(k-1);
    ListNode pre = null;
    while (listNodes.get(k-1) != null) {
      ListNode next = listNodes.get(k-1).next;
      if (pre != null) {
        pre.next = listNodes.get(k-1);
      }
      for (int i = k-1; i>=1; i--) {
        listNodes.get(i).next = listNodes.get(i-1);
      }
      listNodes.get(0).next = next;
      pre = listNodes.get(0);
      listNodes.set(0, next);
      for (int i = 1; i < k; i++) {
        listNodes.set(i, listNodes.get(i-1) == null? null:listNodes.get(i-1).next);
      }
    }
    return head;
  }

  public void nextPermutation(int[] nums) {
    if (nums.length <= 1) {
      return;
    }
    int indexA = nums.length-2;
    int indexB = nums.length-1;
    while (indexA>=0 && nums[indexA+1] <= nums[indexA]) {
      indexA--;
    }
    if (indexA >=0) {
      while (indexB >= 0 && nums[indexB] <= nums[indexA]) {
        indexB--;
      }
      int p = nums[indexA];
      nums[indexA] = nums[indexB];
      nums[indexB] = p;
      reverse(nums, indexA+1);
      return;
    }
    reverse(nums, 0);
  }

  public void reverse(int[]nums, int start) {
    Stack<Integer> stack = new Stack<>();
    for (int i = start; i < nums.length; i++) {
      stack.push(nums[i]);
    }
    for (int j = start; j < nums.length; j++) {
      nums[j] = stack.pop();
    }
  }

  public int longestValidParentheses(String s) {
    int maxLength = 0;
    int num = 0;
    int start = 0;
    for (int i = 0; i < s.length()-1; i++) {
      if (s.charAt(i) == '(') {
        num++;
      } else if (s.charAt(i) == ')') {
        num--;
      }
      if (num == 0) {
        maxLength = Math.max(maxLength, i-start+1);
      } else if (num < 0) {
        num = 0;
        start = i+1;
      }
    }
    start = s.length()-1;
    num=0;
    for (int i = s.length()-1; i >=0; i--) {
      if (s.charAt(i) == '(') {
        num++;
      } else if (s.charAt(i) == ')') {
        num--;
      }
      if (num == 0) {
        maxLength = Math.max(maxLength, start-i+1);
      } else if (num > 0) {
        num = 0;
        start = i-1;
      }
    }
    return maxLength;
  }


  public static void main(String[] args) {
    TreeNode treeNode = toTree("1,-2,-3,1,3,-2,null,-1");
//    System.out.println(toString(new Solution().mergeKLists(new ListNode[]{
//        arrayToListNode(new int[]{1,4,5}),
//        arrayToListNode(new int[]{1,3,4}),
//        arrayToListNode(new int[]{2,6}),
//    })));
//    System.out.println(new Solution().romanToInt("MCMXCIV"));
//    System.out.println(new Solution().convert("A",1));
//    System.out.println(new Solution().minPathSum(new int[][]{
//        {1,3,1},
//        {1,5,1},
//        {4,2,1}
//    }));
//    System.out.println(new Solution().isPalindrome(10));
//    System.out.println(new Solution().nextPermutation(new int[]{1,2,3,4,5,6,7,8}));
    System.out.println(new Solution().longestValidParentheses("(()))(()()())"));
  }


  public String longestCommonPrefix(String[] strs) {
    StringBuilder builder = new StringBuilder("");
    int index  = 0;
    while (true) {
      char a = '.';
      for (int i = 0 ; i < strs.length;i++) {
        if (index >= strs[i].length()) {
          return builder.toString();
        }
        if (a=='.') {
          a = strs[i].charAt(index);
        }
        if (a != strs[i].charAt(index)) {
          return builder.toString();
        }
      }
      if (a == '.') {
        return builder.toString();
      }
      builder.append(a);
      index++;
    }
  }

  public boolean isValid(String s) {
    Map<Character, Character> map = new HashMap<Character, Character>();
    map.put(')', '(');
    map.put('}', '{');
    map.put(']', '[');
    Stack<Character> characters = new Stack<>();
    for (int i =0; i < s.length(); i++) {
      char a = s.charAt(i);
      if (!map.containsKey(a)) {
        characters.push(a);
      } else {
        if (characters.empty() || map.get(a) != characters.pop()) {
          return false;
        }
      }
    }
    return characters.empty();
  }

  public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    ListNode head = null;
    if (l1 == null) {
      return l2;
    }
    if (l2 == null) {
      return l1;
    }
    if (l1.val > l2.val) {
      head = l2;
      l2 = l2.next;
    } else {
      head = l1;
      l1 = l1.next;
    }
    ListNode point = head;
    while (true) {
      if (l1 == null) {
        point.next = l2;
        return head;
      }
      if (l2 == null) {
        point.next = l1;
        return head;
      }
      if (l1.val < l2.val) {
        point.next = l1;
        l1 = l1.next;
      } else {
        point.next = l2;
        l2 = l2.next;
      }
      point = point.next;
    }
  }


  public List<List<Integer>> fourSum(int[] nums, int target) {
    if (nums.length < 4) {
      return new ArrayList<>();
    }
    Arrays.sort(nums);
    List<List<Integer>> list = new ArrayList<>();
    for (int i = 0; i < nums.length-3;) {
      for (int j = i+1 ; j < nums.length-2;) {
        int start = j+1;
        int end = nums.length-1;
        while (end > start) {
          int sum = nums[i] + nums[j] + nums[start] + nums[end];
          if (sum > target) {
            end--;
          } else if (sum < target) {
            start++;
          }
          if (sum == target) {
            list.add(Arrays.asList(nums[i], nums[j], nums[start], nums[end]));
            start++;
            while (start < nums.length && nums[start] == nums[start-1]) {
              start++;
            }
          }
        }
        j++;
        while (j < nums.length-2 && nums[j] == nums[j-1]) {
          j++;
        }
      }
      i++;
      while (i < nums.length-3 && nums[i] == nums[i-1]) {
        i++;
      }
    }
    return list;
  }

  public int removeDuplicates(int[] nums) {
    if (nums.length <1) {
      return nums.length;
    }
    int newIndex = 0;
    int oldIndex = 1;
    for (int i = 0; i<nums.length-1;i++) {
      if (nums[newIndex] == nums[oldIndex]) {
        oldIndex++;
      } else {
        newIndex++;
        nums[newIndex] = nums[oldIndex];
        oldIndex++;
      }
    }
    return newIndex+1;
  }

  public String convert(String s, int numRows) {
    if (numRows==1) {
      return s;
    }
    String[] res = new String[numRows];
    for (int i = 0; i< res.length;i++) {
      res[i] = "";
    }
    for (int i = 0; i < s.length(); i+=2*(numRows-1)) {
      res[0] += s.charAt(i)+"";
      if (i+numRows-1 < s.length()) {
        res[numRows - 1] += s.charAt(i + numRows - 1) + "";
      }
      for (int j = 1; j < numRows-1;j++) {
        if (i+j<s.length()) {
          res[j] = res[j] + s.charAt(i + j);
        }
        if (i+2*(numRows-1)-j < s.length()) {
          res[j] = res[j] + s.charAt(i+2*(numRows-1) -j);
        }
      }
    }
    return String.join("", res);
  }


  public static String toString(ListNode node) {
    StringBuilder sb = new StringBuilder();
    while (node != null) {
      sb.append(node.val + ",");
      node = node.next;
    }
    return sb.toString();
  }

  public static TreeNode toTree(String str) {
    String[] arr = str.split(",");
    List<TreeNode> treeNodes = new ArrayList<>();
    treeNodes.add(new TreeNode(Integer.parseInt(arr[0])));
    for (int i = 1; i < arr.length; i++) {
      if (arr[i].equals("null")) {
        treeNodes.add(new TreeNode(0));
        continue;
      }
      treeNodes.add(new TreeNode(Integer.parseInt(arr[i])));
      TreeNode parent = treeNodes.get((i-1)/2);
      if (i%2==0) {
        parent.right = treeNodes.get(i);
      } else {
        parent.left = treeNodes.get(i);
      }
    }
    return treeNodes.get(0);
  }

  public static String toString(int[] t) {
    if (t.length == 0) {
      return "";
    }
    StringBuilder sb = new StringBuilder();
    sb.append(t[0]);
    for (int i = 1; i < t.length; i++) {
      sb.append(","+t[i]);
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
