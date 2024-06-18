import java.util.Scanner;

/**
 * @author zhaoshenghao
 * @date  2024-06-13 16:28:19
 */
public class ReturnTypeArray {


    //回型数组实现
    public static  int[][] returnTypeArray(int length) {
        //设定四个边界值
        int right = length-1;
        int down = length-1;
        int left = 0;
        int top = 0;
        int[][] array = new int[length][length];
        int index = 1;
        //若左右边界相等则说明数组已经填到中间，结束循环
        while (left <= right) {
            //从左边界到有边界
            for (int i = left; i <= right; i++) {
                array[top][i] = index++;
            }
            //当填完一边则上边界下降，即top+1，四边同理
            top++;
            //从上边界到下边界
            for (int i = top; i <= down; i++) {
                array[i][right] = index++;
            }
            right--;
            //从右边界到左边界
            for (int i = right; i >= left; i--) {
                array[down][i] = index++;
            }
            down--;
            //从下边界到上边界
            for (int i = down; i >= top; i--) {
                array[i][left] = index++;
            }
            left++;
        }
        return array;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("输入一个数字: ");
        int length = scanner.nextInt();
        int[][] array = returnTypeArray(length);
        for (int[] ints : array) {
            for (int anInt : ints) {
                System.out.print(anInt + "\t");
            }
            System.out.println();
        }
    }
}
