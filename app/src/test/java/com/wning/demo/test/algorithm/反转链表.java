package com.wning.demo.test.algorithm;

/**
 * https://leetcode-cn.com/problems/reverse-linked-list/
 * 给你单链表的头节点 head ，请你反转链表，并返回反转后的链表。
 * 输入：head = [1,2,3,4,5]
 * 输出：[5,4,3,2,1]
 *
 * 输入：head = [1,2]
 * 输出：[2,1]
 *
 * 输入：head = []
 * 输出：[]
 */
public class 反转链表 {
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        ListNode next;
        while (curr != null) {
            next = curr.next;
            //反转链表，当前的下一个指向空
            curr.next = prev;
            //prev往下移动，指向cur
            prev = curr;
            //curr往下移动
            curr = next;
        }
        return prev;
    }
}
