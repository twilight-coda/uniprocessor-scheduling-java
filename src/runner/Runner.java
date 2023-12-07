package runner;

import Threads.Task;

public class Runner {
    private int timeSlice;
    private boolean isTimeSliced = false;

    public Runner() {
        this.isTimeSliced = false;
    }

    public Runner(int timeSlice) {
        this.timeSlice = timeSlice;
        this.isTimeSliced = true;
    }

    public void runTask(Task task) {
        try {
            task.run();
            int runningTime = isTimeSliced ? Math.min(task.getRemainingTime(), timeSlice) : task.getRemainingTime();
            Thread.sleep(runningTime);
            task.setRemainingTime(task.getRemainingTime() - runningTime);
        } catch (InterruptedException ignored) {}
    }
}
