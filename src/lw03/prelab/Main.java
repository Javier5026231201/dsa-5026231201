package lw03.prelab;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        // ==========================================
        // Problem 1: Playlist Management (List)
        // ==========================================
        System.out.println("===== Problem 1 =====");
        List<String> playlist = new ArrayList<>();
        
        try (Scanner sc1 = new Scanner(new File("playlist.txt"))) {
            while (sc1.hasNextLine()) {
                String line = sc1.nextLine().trim();
                if (line.isEmpty()) continue;
                
                if (line.startsWith("ADD ")) {
                    String song = line.substring(4).trim();
                    playlist.add(song); // Menambahkan ke akhir playlist
                } else if (line.startsWith("INSERT ")) {
                    String[] parts = line.split(" ", 3);
                    int index = Integer.parseInt(parts[1]);
                    String song = parts[2];
                    playlist.add(index, song); // Menyisipkan pada indeks tertentu
                } else if (line.startsWith("REMOVE ")) {
                    String song = line.substring(7).trim();
                    playlist.remove(song); // Menghapus kemunculan pertama lagu
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File playlist.txt tidak ditemukan.");
        }
        
        // Output Problem 1
        System.out.println("Total songs: " + playlist.size());
        for (int i = 0; i < playlist.size(); i++) {
            System.out.println((i + 1) + ": " + playlist.get(i));
        }

        // ==========================================
        // Problem 2: Workshop Participants (Set)
        // ==========================================
        System.out.println("\n===== Problem 2 =====");
        // Menggunakan LinkedHashSet untuk mempertahankan urutan kemunculan pertama
        Set<String> participants = new LinkedHashSet<>();
        int duplicateCount = 0;
        
        try (Scanner sc2 = new Scanner(new File("participants.txt"))) {
            while (sc2.hasNextLine()) {
                String name = sc2.nextLine().trim();
                if (name.isEmpty()) continue;
                
                // Memeriksa keberadaan elemen sebelum ditambahkan
                if (participants.contains(name)) {
                    duplicateCount++;
                } else {
                    participants.add(name);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File participants.txt tidak ditemukan.");
        }
        
        // Output Problem 2
        System.out.println("Unique participants: " + participants.size());
        int rank = 1;
        for (String name : participants) {
            System.out.println(rank + ". " + name);
            rank++;
        }
        System.out.println("Duplicate registrations: " + duplicateCount);

        // ==========================================
        // Problem 3: Product Inventory (Map)
        // ==========================================
        System.out.println("\n===== Problem 3 =====");
        // Menggunakan LinkedHashMap untuk mempertahankan urutan kemunculan pertama produk
        Map<String, Integer> inventory = new LinkedHashMap<>();
        int failedSales = 0;
        
        try (Scanner sc3 = new Scanner(new File("inventory.txt"))) {
            while (sc3.hasNextLine()) {
                String line = sc3.nextLine().trim();
                if (line.isEmpty()) continue;
                
                String[] parts = line.split("\\s+");
                if (parts.length < 3) continue;
                
                String type = parts[0];
                String product = parts[1];
                int quantity = Integer.parseInt(parts[2]);
                
                if (type.equalsIgnoreCase("ADD")) {
                    if (inventory.containsKey(product)) {
                        inventory.put(product, inventory.get(product) + quantity);
                    } else {
                        inventory.put(product, quantity);
                    }
                } else if (type.equalsIgnoreCase("SELL")) {
                    if (inventory.containsKey(product) && inventory.get(product) >= quantity) {
                        inventory.put(product, inventory.get(product) - quantity);
                    } else {
                        failedSales++;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File inventory.txt tidak ditemukan.");
        }
        
        // Output Problem 3
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("Failed sales: " + failedSales);
    }
}