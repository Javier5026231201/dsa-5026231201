package lw02.prelab;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        Queue<String[]> transactionQueue = new LinkedList<>();
        LinkedList<String[]> customers = new LinkedList<>();
        Stack<String[]> failedStack = new Stack<>();

        File file = new File("src/lw02/prelab/transactions.txt");
        if (!file.exists()) {
            file = new File("transactions.txt");
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String name = scanner.next();
                String type = scanner.next();
                String amount = scanner.next();

                transactionQueue.add(new String[]{name, type, amount});

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

        while (!transactionQueue.isEmpty()) {
            String[] tx = transactionQueue.poll();
            String name = tx[0];
            String type = tx[1];
            int amount = Integer.parseInt(tx[2]);

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
                        failedStack.push(tx);
                    } else {
                        currentBalance -= amount;
                        currentCustomer[1] = String.valueOf(currentBalance);
                    }
                }
            }
        }

        System.out.println("=== Final Balances ===");
        for (String[] customer : customers) {
            System.out.println(customer[0] + ": " + customer[1]);
        }

        System.out.println("=== Failed Transactions ===");
        while (!failedStack.isEmpty()) {
            String[] tx = failedStack.pop();
            System.out.println(tx[0] + " " + tx[1] + " " + tx[2]);
        }
    }
}