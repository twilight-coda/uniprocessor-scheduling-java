package Threads;

public interface Task extends Runnable {
    int getRemainingTime();

    void setRemainingTime(int duration);

    int getId();
}
