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

    //删除数组arr中第i,j,m,n项，由于每组4个元素，每存放一组数组就从原数组中将该数组的元素去除
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

    //对数组进行分割，使每一组都为等差数组，将每一组等差数组都放入队列splitArrays中,若找到一种全为等差数组的队列即为可分数组，返回队列
    private static List<int[]> splitArray(int[] arr) {
        //用于存放数组的队列
        List<int[]> splitArrays = new ArrayList<>();

        int length = arr.length;
        //共可分为数组长度/4的组数，即原题中的m,同时要理解每组的最大公差，即每一组的步长最大不超过m
        //所有的公差组合，每组公差可以为[1,1,1...1]或[1,1,1...2]或...或[m,m,m...m]共m*m种组合
        int numGroups = length / 4;
        //数组b,c,d用于存放每次取值后原数组的下标
        int[] b = new int[numGroups];
        int[] c = new int[numGroups];
        int[] d = new int[numGroups];
        //备份原数组
        int[] oldArr = arr;
        //存放每次更新数组后的长度
        int newLength = arr.length;
        //记录已经找到几组的等差数列
        int i = 0;
        outerLoopFirst:
        for (int j = 1; j < newLength - 2; j++) {
            outerLoopThird:
            for (int k = j + 1; k < newLength - 1; k++) {
                for (int l = k + 1; l < newLength; l++) {
                    //用于存放找到的数组
                    int[] group = new int[4];
                    //由于每次更新原数组，所以每次取值的第一个数都是新数组的第一个元素
                    group[0] = arr[0];
                    group[1] = arr[j];
                    group[2] = arr[k];
                    group[3] = arr[l];
                    //公差即为第二个元素与第一个元素的差值，若后面差值大于公差即本次循环之后的元素都不可能构成等差数列
                    if (group[3] - group[2] > group[1] - group[0]) {
                        //判断是否已经遍历了该数组的所有情况
                        if (j == newLength - 3 && k == newLength - 2 && l == newLength - 1) {
                            if (!splitArrays.isEmpty()){
                                //返回上一次已经构成的等差数列，重新选值
                                //删除上一次的选值
                                splitArrays.removeLast();
                                arr = oldArr;
                                i --;
                                //j,k,l返回上一次的下标
                                j = b[i];
                                k = c[i];
                                l = d[i];
                                //将数组变为上一次选值前的情况
                                for (int f=0; f<i; f++){
                                    arr = removeFour(arr,0,b[f],c[f],d[f]);
                                }
                                newLength = arr.length;
                                break outerLoopThird;
                            }else
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
                            }else
                                break outerLoopFirst;
                        }
                        break outerLoopThird;
                    }
                    //步长大于最大公差，本次循环下都不可能
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
                    //判断选中的数组group是否为等差数组
                    if (isArithmeticalSequence(group)) {
                        //若为等差数组就从原数组arr中将已选的数删除
                        arr = removeFour(arr, 0, j, k, l);
                        //核查已选数组数列与i是否相等
                        if (arr.length + (i + 1) * 4 == length) {
                            b[i] = j;
                            c[i] = k;
                            d[i] = l;
                            i++;
                        }
                        newLength = arr.length;
                        //将该等差数组加入队列中
                        splitArrays.add(group);
                        //由于数组被更改，将j,k,l进行重置，由于首先返回到l的循环中，l会自增，所以l为2
                        j = 1;
                        k = 2;
                        l = 2;
                        //若新数组的长度为0，即所以数以及选择完毕，手动结束循环
                        if (newLength == 0) {
                            break outerLoopFirst;
                        }
                    }
                    //选中的数组不是等差数组
                    else {
                        //是否已经遍历了所有情况，返回上一次的选值
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
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < arr.length -1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                int[] newArr = removeTwo(arr,i+1,j+1);
                List<int[]> splitArrays = splitArray(newArr);
                if (!splitArrays.isEmpty()){
                    count++;
                    System.out.print("("+(i+1)+","+(j+1)+")的可分数列,分组为：");
                    for (int[] splitArray : splitArrays) {
                        System.out.print("(");
                        for (int element : splitArray) {
                            System.out.print( element + " ");
                        }
                        System.out.print(")");
                    }
                    System.out.println();
                }
            }
        }
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("执行时间："+elapsedTime+"毫秒");
        int total = (2*m+1)*(4*m+1);
        float p = (float)count/total;
        System.out.println("总数为："+count);
        System.out.println("答案应为：" + (m*m+m+1));
        System.out.println("概率Pm为：" + p);
        System.out.println("1/8为：" + (float)1/8);
    }

    public static void main(String[] args) {
//        int[] arr = creatArray(7);
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
        isDivisible(7);
//        for (int i = 1; i <= 8; i++) {
//            System.out.println("---------------------- m="+ i +"----------------------");
//            isDivisible(i);
//        }
    }
}