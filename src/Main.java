import schedules.feedbackScheduling.FeedbackScheduler;
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
        List<Task> tasks = SimpleTaskFactory.createTasks(10);

        IntervalTaskInjector injector = new IntervalTaskInjector(taskArrivalQueue, tasks);
        Thread producerThread = new Thread(injector);

//        System.out.println("Creating SPN scheduler");
//        SpnScheduler spnScheduler = new SpnScheduler(taskArrivalQueue);
//        System.out.println("Starting SPN scheduler thread");
//        Thread scheduler = new Thread(spnScheduler);

        FeedbackScheduler feedbackScheduler = new FeedbackScheduler(taskArrivalQueue);
        Thread scheduler = new Thread(feedbackScheduler);

        scheduler.start();
        System.out.println("Starting injector");
        producerThread.start();
        try {
            producerThread.join();
            scheduler.join();
        } catch (InterruptedException ignored) {}

    }
}
