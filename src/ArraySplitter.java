import java.util.ArrayList;
import java.util.List;

public class ArraySplitter {
    public static List<int[]> splitArray(int[] array) {
        List<int[]> splitArrays = new ArrayList<>();
        int length = array.length;
        int numGroups = length / 4;
        int remainder = length % 4;

        for (int i = 0; i < numGroups; i++) {
            int[] group = new int[4];
            System.arraycopy(array, i * 4, group, 0, 4);
            splitArrays.add(group);
        }

        if (remainder > 0) {
            int[] group = new int[remainder];
            System.arraycopy(array, numGroups * 4, group, 0, remainder);
            splitArrays.add(group);
        }

        return splitArrays;
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,13};
        List<int[]> splitArrays = splitArray(array);

        for (int[] splitArray : splitArrays) {
            for (int element : splitArray) {
                System.out.print(element + " ");
            }
            System.out.println();
        }
    }
}