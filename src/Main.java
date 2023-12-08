import schedules.feedbackScheduling.FeedbackScheduler;
import tasks.IntervalTaskInjector;
import tasks.Task;
import tasks.SimpleTaskFactory;
import schedules.spnScheduling.SpnScheduler;
import util.PerfCalculator;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static void main(String[] args) {
        String selectedSchedule = args.length > 0 ? args[0] : "";
        switch (selectedSchedule) {
            case "1":
                runSpnSchedule();
                break;
            case "2":
                runFeedbackSchedule();
                break;
            default:
                System.out.println("Running all schedules");
                runSpnSchedule();
                runFeedbackSchedule();
        }
    }

    private static void runSpnSchedule() {
        System.out.println("-----------------SPN Scheduling-----------------");
        PreparedSchedulingItems preparedSchedulingItems = prepareSchedule();

        SpnScheduler spnScheduler = new SpnScheduler(preparedSchedulingItems.taskArrivalQueue);
        Thread scheduler = new Thread(spnScheduler);

        runAlgorithm(scheduler, preparedSchedulingItems.producerThread);

        summarize(preparedSchedulingItems.tasks);
        System.out.println("-----------------END-----------------");
    }

    private static void runFeedbackSchedule() {
        System.out.println("-----------------Feedback Scheduling-----------------");
        PreparedSchedulingItems preparedSchedulingItems = prepareSchedule();

        FeedbackScheduler feedbackScheduler = new FeedbackScheduler(preparedSchedulingItems.taskArrivalQueue);
        Thread scheduler = new Thread(feedbackScheduler);

        runAlgorithm(scheduler, preparedSchedulingItems.producerThread);

        summarize(preparedSchedulingItems.tasks);
        System.out.println("-----------------END-----------------");
    }

    private static void summarize(List<Task> tasks) {
        System.out.println("-----------------SUMMARY-----------------");
        for (Task task : tasks) {
            System.out.println(task);
        }
        System.out.println("Average Response Time " + PerfCalculator.getAverageResponseTime(tasks) + " ms");
        System.out.println("Total Execution Time " + PerfCalculator.getTotalExecutionTime(tasks) + " ms");
        System.out.println("Average Turn-around Time " + PerfCalculator.getAverageTurnAroundTime(tasks) + " ms");
        System.out.println("Average Wait Time " + PerfCalculator.getAverageWaitTime(tasks) + " ms");
    }

    private static PreparedSchedulingItems prepareSchedule() {
        List<Task> tasks = SimpleTaskFactory.createTasks(10);
        BlockingQueue<Task> taskArrivalQueue = new LinkedBlockingQueue<>();
        IntervalTaskInjector injector = new IntervalTaskInjector(taskArrivalQueue, tasks);
        Thread producerThread = new Thread(injector);
        return new PreparedSchedulingItems(tasks, taskArrivalQueue, producerThread);
    }

    private static void runAlgorithm(Thread scheduler, Thread producerThread) {
        scheduler.start();
        System.out.println("Starting injector");
        producerThread.start();
        try {
            producerThread.join();
            scheduler.join();
        } catch (InterruptedException ignored) {}
    }

    private record PreparedSchedulingItems(List<Task> tasks, BlockingQueue<Task> taskArrivalQueue, Thread producerThread) {}

}
