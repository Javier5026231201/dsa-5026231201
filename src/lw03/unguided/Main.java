package lw03.unguided;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Map<String, Integer> enrollmentMap = new HashMap<>();
        
        List<String> courseOrder = new ArrayList<>();
        
        List<String> checkResults = new ArrayList<>();
        
        int rejectedCount = 0;

        try {
            File file = new File("enrollment.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNext()) {
                String command = scanner.next();

                if (command.equals("REGISTER")) {
                    String courseCode = scanner.next();
                    int count = scanner.nextInt();

                    if (count <= 0) {
                        rejectedCount++;
                    } else {
                        if (enrollmentMap.containsKey(courseCode)) {
                            int currentCount = enrollmentMap.get(courseCode);
                            enrollmentMap.put(courseCode, currentCount + count);
                        } else {
                            enrollmentMap.put(courseCode, count);
                            courseOrder.add(courseCode);
                        }
                    }
                } else if (command.equals("WITHDRAW")) {
                    String courseCode = scanner.next();
                    int count = scanner.nextInt();

                    if (count <= 0) {
                        rejectedCount++;
                    } else if (enrollmentMap.containsKey(courseCode)) {
                        int currentCount = enrollmentMap.get(courseCode);
                        if (currentCount >= count) {
                            enrollmentMap.put(courseCode, currentCount - count);
                        } else {
                            rejectedCount++; 
                        }
                    } else {
                        rejectedCount++;
                    }
                } else if (command.equals("CHECK")) {
                    String courseCode = scanner.next();

                    if (enrollmentMap.containsKey(courseCode)) {
                        int currentCount = enrollmentMap.get(courseCode);
                        checkResults.add(courseCode + ": " + currentCount + " students");
                    } else {
                        checkResults.add(courseCode + ": Not found");
                    }
                }
            }
            scanner.close();

            System.out.println("===== Enrollment Checks =====");
            for (int i = 0; i < checkResults.size(); i++) {
                System.out.println(checkResults.get(i));
            }

            System.out.println("===== Final Enrollment =====");
            for (int i = 0; i < courseOrder.size(); i++) {
                String courseCode = courseOrder.get(i);
                int count = enrollmentMap.get(courseCode);
                System.out.println(courseCode + ": " + count + " students");
            }

            System.out.println("Rejected operations: " + rejectedCount);

        } catch (FileNotFoundException e) {
            System.out.println("File enrollment.txt tidak ditemukan.");
        }
    }
}