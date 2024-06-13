
public class ArraySplitter {
    private static void printArray(int[] arr){
        for (int index : arr) {
            System.out.print(index + ",");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] array = {3, 7, 13, 21, 31, 43, 59, 79, 100, 121, 144, 169,196};
        printArray(array);
        int[] newArray = new int[array.length];
        for (int i = 1; i <= array.length; i++) {
            newArray[i-1] = array[i-1] - i*i-i-1;
        }
        printArray(newArray);
    }
}