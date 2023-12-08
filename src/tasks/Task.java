package tasks;

import java.time.Duration;

public interface Task {
    void run();

    int getRemainingTime();

    void setRemainingTime(int duration);

    int getId();

    int getTotalTime();

    void setStartTime();

    void setArrivalTime();

    void setFinishTime();

    Duration getResponseTime();

    Duration getTurnAroundTime();

    Duration getWaitTime();
}
