package lw02.unguided;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        LinkedList<String[]> rawRequests = new LinkedList<>();
        LinkedList<String[]> books = new LinkedList<>();
        LinkedList<String[]> members = new LinkedList<>();
        LinkedList<String[]> successfulRequests = new LinkedList<>();
        Queue<String[]> requestQueue = new LinkedList<>();
        Stack<String[]> failedStack = new Stack<>();

        books.add(new String[]{"Kalkulus", "2"});
        books.add(new String[]{"Fisika", "1"});
        books.add(new String[]{"Statistika", "2"});

        try (Scanner scanner = new Scanner(new File("src/lw02/unguided/borrowing.txt"))) {
            while (scanner.hasNext()) {
                String memberName = scanner.next();
                String bookTitle = scanner.next();
                rawRequests.add(new String[]{memberName, bookTitle});

                boolean memberExists = false;
                for (String[] member : members) {
                    if (member[0].equals(memberName)) {
                        memberExists = true;
                        break;
                    }
                }
                if (!memberExists) {
                    members.add(new String[]{memberName, "0"});
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File borrowing.txt tidak ditemukan!");
            return;
        }

        for (String[] request : rawRequests) {
            requestQueue.add(request);
        }

        while (!requestQueue.isEmpty()) {
            String[] request = requestQueue.poll();
            String memberName = request[0];
            String bookTitle = request[1];

            String[] currentBook = null;
            for (String[] book : books) {
                if (book[0].equals(bookTitle)) {
                    currentBook = book;
                    break;
                }
            }

            String[] currentMember = null;
            for (String[] member : members) {
                if (member[0].equals(memberName)) {
                    currentMember = member;
                    break;
                }
            }

            if (currentBook != null && currentMember != null) {
                int stock = Integer.parseInt(currentBook[1]);
                int borrowedCount = Integer.parseInt(currentMember[1]);

                if (stock > 0 && borrowedCount < 2) {
                    currentBook[1] = String.valueOf(stock - 1);
                    currentMember[1] = String.valueOf(borrowedCount + 1);
                    successfulRequests.add(new String[]{memberName, bookTitle});
                } else {
                    failedStack.push(new String[]{memberName, bookTitle});
                }
            }
        }

        System.out.println("=== Successfully Processed Requests ===");
        for (String[] request : successfulRequests) {
            System.out.println(request[0] + " " + request[1]);
        }

        System.out.println("=== Remaining Book Stock ===");
        for (String[] book : books) {
            System.out.println(book[0] + ": " + book[1]);
        }

        System.out.println("=== Failed Requests ===");
        while (!failedStack.isEmpty()) {
            String[] request = failedStack.pop();
            System.out.println(request[0] + " " + request[1]);
        }
    }
}