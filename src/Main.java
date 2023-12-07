import tasks.IntervalTaskInjector;
import tasks.Task;
import tasks.SimpleTaskFactory;
import schedules.spnScheduling.SpnScheduler;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static void main(String[] args) {
        BlockingQueue<Task> taskArrivalQueue = new LinkedBlockingQueue<>();

        System.out.println("Creating SPN scheduler");
        SpnScheduler spnScheduler = new SpnScheduler(taskArrivalQueue);
        System.out.println("Starting SPN scheduler thread");
        Thread scheduler = new Thread(spnScheduler);
        scheduler.start();

        System.out.println("Creating tasks");
        List<Task> tasks = SimpleTaskFactory.createTasks(10);

        System.out.println("Creating task injector to push tasks to scheduler");
        IntervalTaskInjector injector = new IntervalTaskInjector(taskArrivalQueue, tasks);

        System.out.println("Starting injector");
        Thread producerThread = new Thread(injector);
        producerThread.start();
        try {
            producerThread.join();
            scheduler.join();
        } catch (InterruptedException ignored) {}

    }
}
