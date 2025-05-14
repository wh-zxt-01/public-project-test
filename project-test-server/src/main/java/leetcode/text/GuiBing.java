package leetcode.text;

/**
 * @Author: wh
 * @Date: 2025/05/08/15:46
 * @Description:
 */
public class GuiBing {

    public static void main(String[] args) {
        int [] arr = {1,3,5,7,9,2,4,6,8,10};
        sort(arr,0,arr.length-1);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }
    public static void sort(int [] arr, int m,int n){
        if (m >= n){
            return;
        }
        int mid = (m + n) / 2;
        sort(arr,m,mid);
        sort(arr,mid+1,n);
        merge(arr,m,mid,n);
    }
    public static void merge(int [] arr, int m,int mid,int n){
        int [] temp = new int[n-m+1];
        int i = m;
        int j = mid+1;
        int k = 0;
        while(i<=mid && j<=n){
            if (arr[i]<arr[j]){
                temp[k++] = arr[i];
                i++;
            }else{
                temp[k++] = arr[j];
                j++;
            }
        }
        while (i<=mid){
            temp[k++] = arr[i++];
        }
        while (j<=n){
            temp[k++] = arr[j++];
        }
        for (int l = 0; l < temp.length; l++) {
            arr[m++] = temp[l];
        }
    }
}
