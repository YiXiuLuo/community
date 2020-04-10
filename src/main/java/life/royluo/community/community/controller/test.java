package life.royluo.community.community.controller;

public class test {

    public static void main(String[] args) {
        int [] arr= {24,11,65,9,55};
        bubbleSort(arr);
    }
    public static void bubbleSort(int [] arr) {
        for(int j=0;j<arr.length-1;j++) {
            for(int i=0;i<arr.length-1-j;i++) {
                if(arr[i]>arr[i+1]) {
                    int temp =arr[i];
                    arr[i]=arr[i+1];
                    arr[i+1]=temp;
                }
            }
        }
        for(int i=0;i<arr.length;i++) {
            System.out.print(arr[i]+",");
        }


    }



}
