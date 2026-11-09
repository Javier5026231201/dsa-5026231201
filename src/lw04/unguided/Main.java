package lw04.unguided;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void selectionSort(int[] arr, int passes) {
        int n = arr.length;
        for (int i = 0; i < passes && i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            int temp = arr[i];
            arr[i] = arr[minIdx];
            arr[minIdx] = temp;
        }
    }

    public static int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        try {
            Scanner input = new Scanner(new File("src/lw04/unguided/cards.txt"));

            int n = input.nextInt();
            int[] cards = new int[n];
            for (int i = 0; i < n; i++) {
                cards[i] = input.nextInt();
            }

            int m = input.nextInt();
            int[] targets = new int[m];
            for (int i = 0; i < m; i++) {
                targets[i] = input.nextInt();
            }

            input.close();

            selectionSort(cards, 2);

            for (int i = 0; i < cards.length; i++) {
                System.out.print(cards[i]);
                if (i < cards.length - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();

            for (int i = 0; i < targets.length; i++) {
                int result = linearSearch(cards, targets[i]);
                if (result != -1) {
                    System.out.println(targets[i] + ": FOUND at index " + result);
                } else {
                    System.out.println(targets[i] + ": NOT FOUND");
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("File cards.txt tidak ditemukan.");
        }
    }
}