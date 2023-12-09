package schedules.hrrnScheduling;

import schedules.SchedulingTasksContainer;
import tasks.Task;

import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class HrrnTasksContainer implements  SchedulingTasksContainer<Task> {

    private final BlockingQueue<Task> taskExecutionQueue = new LinkedBlockingQueue<>();

    public void addTask(Task task) throws InterruptedException {
        this.taskExecutionQueue.put(task);
    }

    public boolean hasTasksAvailable() {
        return !taskExecutionQueue.isEmpty();
    }

    @Override
    public Iterator<Task> iterator() {
        return new HrrnIterator(this);
    }

    class HrrnIterator implements Iterator<Task> {

        private final HrrnTasksContainer tasks;

        public HrrnIterator(HrrnTasksContainer tasks) {
            this.tasks = tasks;
        }

        @Override
        public boolean hasNext() {
            return !this.tasks.taskExecutionQueue.isEmpty();
        }

        @Override
        public Task next() {
            return  tasks.taskExecutionQueue.poll();
        }
    }

    private double calculateResponseRatio(Task task, long currentTime) {
        long waitingTime = currentTime - task.getArrivalTime();
        return (waitingTime + task.getRemainingTime()) / (double) task.getRemainingTime();
    }

    public Task getNextTask() {
        long currentTime = System.currentTimeMillis();
        Task nextTask = null;
        double highestResponseRatio = Double.MIN_VALUE;

        for (Task task : taskExecutionQueue) {
            double responseRatio = calculateResponseRatio(task, currentTime);
            if (responseRatio > highestResponseRatio) {
                highestResponseRatio = responseRatio;
                nextTask = task;
            }
        }

        if (nextTask != null) {
            taskExecutionQueue.remove(nextTask);
        }

        return nextTask;
    }
}
