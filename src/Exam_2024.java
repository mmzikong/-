import java.util.ArrayList;
import java.util.List;

public class Exam_2024 {
    //构造有4m+2项的数组
    private static int[] creatArray(int m){
        int[] arr = new int[m*4+2];
        for(int i = 0; i < m*4+2; i++){
            arr[i] = i+1;
        }
        return arr;
    }


    //判断是否为等差数组
    private static boolean isArithmeticalSequence(int[] arr){
        //公差d
        int d = arr[1] - arr[0];
        for(int i = 1; i < arr.length-1; i++){
            if(d != arr[i+1] - arr[i]){
                return false;
            }
        }
        return true;
    }

    //对数组arr去掉第i,j两项
    private static int[] removeTwo(int[] arr, int i, int j){
        int[] newArr = new int[arr.length-2];
        for(int k = 0,l = 0; k < newArr.length; k++,l++){
            while (l==i-1 || l==j-1){
                l++;
            }
            newArr[k] = arr[l];
        }
        return newArr;
    }

    private static int[] removeFour(int[] arr, int i, int j,int m, int n){
        int[] newArr = new int[arr.length-4];
        for(int k = 0,l = 0; k < newArr.length; k++,l++){
            while (l==i || l==j || l==n || l==m){
                l++;
            }
            newArr[k] = arr[l];
        }
        return newArr;
    }

    private static void printArray(int[] arr){
        for (int index : arr) {
            System.out.print(index + ",");
        }
        System.out.println();
    }

    private static List<int[]> splitArray(int[] arr) {
        List<int[]> splitArrays = new ArrayList<>();

        int length = arr.length;
        //共可分为数组长度/4的组数，即原题中的m,也是每组的最大公差
        int numGroups = length / 4;
        int[] a = new int[numGroups];
        int[] b = new int[numGroups];
        int[] c = new int[numGroups];
        int[] d = new int[numGroups];
        int[] oldArr = arr;
        //所有的公差组合，每组公差可以为[1,1,1...1]或[1,1,1...2]或...或[m,m,m...m]共m*m种组合
        int newLength = arr.length;

        int i = 0;
        outerLoopFirst:
        for (int j = 1; j < newLength - 2; j++) {
            outerLoopThird:
            for (int k = j + 1; k < newLength - 1; k++) {
                for (int l = k + 1; l < newLength; l++) {
                    int[] group = new int[4];
                    group[0] = arr[0];
                    group[1] = arr[j];
                    group[2] = arr[k];
                    group[3] = arr[l];
                    if (group[3] - group[2] > group[1] - group[0]) {
                        if (j == newLength - 3 && k == newLength - 2 && l == newLength - 1) {
                            splitArrays.clear();
                            break outerLoopFirst;
                        }
                        break;
                    }
                    if (group[2] - group[1] > group[1] - group[0]) {
                        if (j == newLength - 3 && k == newLength - 2 && l == newLength - 1) {
                            if (!splitArrays.isEmpty()){
                                splitArrays.removeLast();
                                arr = oldArr;
                                i --;
                                j = b[i];
                                k = c[i];
                                l = d[i];
                                for (int f=0; f<i; f++){
                                    arr = removeFour(arr,0,b[f],c[f],d[f]);
                                }
                                newLength = arr.length;
                                break outerLoopThird;
                            }
                            break outerLoopFirst;
                        }
                        break outerLoopThird;
                    }
                    if (group[1] - group[0] > numGroups) {
                        if (!splitArrays.isEmpty()){
                            splitArrays.removeLast();
                            arr = oldArr;
                            i --;
                            j = b[i];
                            k = c[i];
                            l = d[i];
                            for (int f=0; f<i; f++){
                                arr = removeFour(arr,0,b[f],c[f],d[f]);
                            }
                            newLength = arr.length;
                            break outerLoopThird;
                        }
                        else{
                            break outerLoopFirst;
                        }
                    }
                    if (isArithmeticalSequence(group)) {
                        arr = removeFour(arr, 0, j, k, l);
                        if (arr.length + (i + 1) * 4 == length) {
                            b[i] = j;
                            c[i] = k;
                            d[i] = l;
                            i++;
                        }
                        newLength = arr.length;
                        splitArrays.add(group);
                        j = 1;
                        k = 2;
                        l = 2;
                        if (newLength == 0) {
                            break outerLoopFirst;
                        }
                    } else {
                        if (j == newLength - 3 && k == newLength - 2 && l == newLength - 1) {
                            splitArrays.removeLast();
                            arr = oldArr;
                            i --;
                            j = b[i];
                            k = c[i];
                            l = d[i];
                            if (!splitArrays.isEmpty()){
                                for (int f=0; f<i; f++){
                                    arr = removeFour(arr,0,b[f],c[f],d[f]);
                                }
                            }
                            newLength = arr.length;
                        }
                    }
                }
            }
        }
        return splitArrays;
    }


    private static void isDivisible(int m){
        int[] arr = creatArray(m);
        printArray(arr);
        int count = 0;
        for (int i = 0; i < arr.length -1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                int[] newArr = removeTwo(arr,i+1,j+1);
                List<int[]> splitArrays = splitArray(newArr);
                if (!splitArrays.isEmpty()){
                    count++;
                    System.out.println("是("+(i+1)+","+(j+1)+")的一一可分数列");
                }
            }
        }
        int total = (2*m+1)*(4*m+1);
        float p = (float)count/total;
        System.out.println("总数为："+count);
        System.out.println("答案应为：" + (m*m+m+1));
        System.out.println("概率Pm为：" + p);
        System.out.println("1/8为：" + (float)1/8);
    }

    public static void main(String[] args) {
//        int[] arr = creatArray(4);
//        printArray(arr);
//        arr = removeTwo(arr,2,13);
//        printArray(arr);
//        List<int[]>splitArrays = splitArray(arr);
//        for (int[] splitArray : splitArrays) {
//            for (int element : splitArray) {
//                System.out.print(element + " ");
//            }
//            System.out.println();
//        }
        isDivisible(9);
    }
}