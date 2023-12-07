package Threads;

public interface SchedulingTask extends Runnable {
    int getRemainingTime();

    void setRemainingTime(int duration);

    int getId();
}
