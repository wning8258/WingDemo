package com.wning.demo.test;

import java.util.Arrays;

public class Sort {
    public static void main(String[] args){

        int a[]={6,4,8,7,1,5,3,2};
//        int a[]={6,1,2,7,9};
       // int b[]={1,2,3,4,5,6,7,8};

        Sort sort=new Sort();
       //  sort.bubbleSort(a);
//       sort.quickSort(a,0,a.length-1);
        sort.choiceSort(a);

        //System.out.println("result :"+ Arrays.toString(a));
        //int pos=sort.binarySearch(b,8);
//        System.out.println("pos :"+pos);
    }

    /**
     * 冒泡排序（两次遍历，比较相邻两个数的大小，把较大的数往右移）
     * @param a
     */
    public void bubbleSort(int[] a){
        int temp;
        for (int i=0;i<a.length-1;i++){
            for(int j=0;j<a.length-1-i;j++){
                if(a[j]>a[j+1]){
                    temp=a[j];
                    a[j]=a[j+1];
                    a[j+1]=temp;
                }
            }
            System.out.println("i :"+i+" , "+Arrays.toString(a));
        }

    }

    /**
     * 选择排序（两次遍历，从左到右，每次比较第个元素 和后续元素的大小，并记录位置，结束后把第i小的元素移动到最左边）
     * @param a
     */
    public void choiceSort(int[] a){
        int pos,temp;
       for(int i=0;i<=a.length-1;i++){
           pos=i;
           for(int j=i+1;j<=a.length-1;j++){
               if(a[j]<a[pos]){
                   pos=j;
               }
           }
           temp=a[i];
           a[i]=a[pos];
           a[pos]=temp;
           System.out.println("i :"+i+" , "+Arrays.toString(a));
       }
    }


    /**
     * 快速排序，（选择一个目标数，两个指针，一个从右向左，一个从左往右）
     * @param a
     * @param left
     * @param right
     */
    public void quickSort(int[] a,int left,int right){
        int temp,t,i,j;
        if(left>=right){//这行必须有，而且是大于等于
            return;
        }

        temp=a[left];

        i=left;
        j=right;

        while(i!=j){
            while(a[j]>=temp&&i<j){
                j--;
            }
            while(a[i]<=temp&&i<j){
                i++;
            }
            if(i<j){  //a[i]+a[j]交换位置
                t=a[i];
                a[i]=a[j];
                a[j]=t;
            }

        }
        a[left]=a[i]; //最后定位到的a[i]和基准点a[left]交换
        a[i]=temp;
        System.out.println(i+" :"+j+" ,"+ Arrays.toString(a));

        quickSort(a,left,i-1);
        quickSort(a,i+1,right);
    }

    public int binarySearch(int[] a, int key){
        int start,end,mid;
        start=0;
        end=a.length-1;
        while(start<=end){  //必须是<=(否则key是最后一个的话，找不到)
            mid=start+(end-start)/2;
            if(key<a[mid]){
                end=mid-1;
            }else if(key>a[mid]){
                start=mid+1;
            }else{
                return mid;
            }
        }
        return -1;
    }
}
