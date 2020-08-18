package com.example.array;

/**
 * java给定一个有序数组，删除其中重复元素，只保留一个，并返回新数组的长度
 * [0,0,1,1,1,2,2,3,3,4],输出5
 */
public class removeDuplicatesTest {



    public static void main(String[] args){
//        int[] array={1,2,2,2,2,3,3};
        int array[]={0,0,1,1,1,2,2,3,3,4};
        int count=removeDuplicates(array);
        System.out.println(count);
    }

    // 有序数组去重，返回新数组长度
   public static int removeDuplicates(int A[]) {
        int i = 0;    // 第一个指针
        int j;        // 第二个指针
        int n = A.length;
        if (n <=1 ) return n;
        // 遍历数组
        for (j = 1; j < n; j++) {
            if (A[j] != A[i]) { // 若两个指针所指元素不同，则i+1位置保存j所指元素的值
                A[++i] = A[j];
            }
        }
        return i+1;    // 返回新数组的长度
    }
}
