package leetcode.text;

/**
 * @author wh
 * @version 1.0
 * @description: TODO
 * @date 2025/5/13 09:39
 */
public class KuaiSu {

    public static void main(String[] args) {
        int [] arr = {1,3,5,7,9,2};
        quickSort(arr,0,arr.length-1);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i] + " ");
        }
    }

    public static void quickSort(int [] arr, int start,int end){
        if (start >= end) {
            return;
        }
        int pIndex = partition(arr, start, end);
        quickSort(arr, start, pIndex - 1);
        quickSort(arr, pIndex + 1, end);
    }
    public static int partition(int[] arr, int start, int end) {
        int i = start;
        int partition = arr[end];
        for (int j = start; j < end - 1; j++) {
            if (arr[j] < partition) {
                if (i == j) {
                    i++;
                } else {
                    int temp = arr[i];
                    arr[i++] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        int tmp = arr[i];
        arr[i] = arr[end];
        arr[end] = tmp;
        return i;
    }
}
