package Threads;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class SimpleThreadFactory {
    private static final Random random = new Random();
    private static int threadId = 1;

    public static List<Task> createTasks(int numberOfTasks) {
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < numberOfTasks; i++) {
            tasks.add(new SimpleTask(threadId++, generateRandomDuration()));
        }
        return tasks;
    }

    private static int generateRandomDuration() {
        int[] shortTerm = {500, 1000}; // 500ms to 1000ms
        int[] mediumTerm = {5000, 6000, 7000}; // 5s, 6s, 7s
        int[] longTerm = {13000, 14000, 15000}; // 13s, 14s, 15s

        // Randomly select a term
        return switch (random.nextInt(3)) {
            case 0 -> // Short term
                    random.nextInt(shortTerm[1] - shortTerm[0] + 1) + shortTerm[0];
            case 1 -> // Medium term
                    mediumTerm[random.nextInt(mediumTerm.length)];
            case 2 -> // Long term
                    longTerm[random.nextInt(longTerm.length)];
            default -> throw new IllegalStateException("Unexpected value");
        };
    }
}
