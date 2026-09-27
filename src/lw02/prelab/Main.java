package lw02.prelab;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        // 1. LinkedList untuk menyimpan seluruh transaksi awal
        LinkedList<String[]> rawTransactions = new LinkedList<>();

        // 2. LinkedList untuk menyimpan data nasabah [Nama, Saldo]
        LinkedList<String[]> customers = new LinkedList<>();

        // Pembacaan file transactions.txt
        File file = new File("src/lw02/prelab/transactions.txt");
        if (!file.exists()) {
            file = new File("transactions.txt"); // Fallback jalur
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String name = scanner.next();
                String type = scanner.next();
                String amount = scanner.next();

                // Simpan transaksi
                rawTransactions.add(new String[]{name, type, amount});

                // Tambahkan nasabah jika pertama kali muncul (Order of First Appearance)
                boolean exists = false;
                for (String[] customer : customers) {
                    if (customer[0].equals(name)) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    customers.add(new String[]{name, "0"});
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File transactions.txt tidak ditemukan!");
            return;
        }

        // 3. Pindahkan transaksi dari LinkedList ke Queue (FIFO)
        Queue<String[]> transactionQueue = new LinkedList<>();
        for (String[] tx : rawTransactions) {
            transactionQueue.add(tx);
        }

        // 4. Stack untuk menyimpan transaksi gagal (LIFO)
        Stack<String[]> failedStack = new Stack<>();

        // Proses transaksi berurutan dari Queue
        while (!transactionQueue.isEmpty()) {
            String[] tx = transactionQueue.poll(); // FIFO
            String name = tx[0];
            String type = tx[1];
            int amount = Integer.parseInt(tx[2]);

            // Cari data nasabah terkait
            String[] currentCustomer = null;
            for (String[] customer : customers) {
                if (customer[0].equals(name)) {
                    currentCustomer = customer;
                    break;
                }
            }

            if (currentCustomer != null) {
                int currentBalance = Integer.parseInt(currentCustomer[1]);

                if (type.equalsIgnoreCase("DEPOSIT")) {
                    currentBalance += amount;
                    currentCustomer[1] = String.valueOf(currentBalance);
                } else if (type.equalsIgnoreCase("WITHDRAW")) {
                    if (amount > currentBalance) {
                        // Penarikan gagal -> Push ke Stack
                        failedStack.push(tx);
                    } else {
                        currentBalance -= amount;
                        currentCustomer[1] = String.valueOf(currentBalance);
                    }
                }
            }
        }

        // 5. Cetak Output
        System.out.println("=== Final Balances ===");
        for (String[] customer : customers) {
            System.out.println(customer[0] + ": " + customer[1]);
        }

        System.out.println("=== Failed Transactions ===");
        while (!failedStack.isEmpty()) {
            String[] tx = failedStack.pop(); // LIFO
            System.out.println(tx[0] + " " + tx[1] + " " + tx[2]);
        }
    }
}