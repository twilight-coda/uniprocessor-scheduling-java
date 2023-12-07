package schedules.feedbackScheduling;

import schedules.AbstractScheduler;
import tasks.Task;

import java.util.concurrent.BlockingQueue;

public class FeedbackScheduler extends AbstractScheduler {
    public FeedbackScheduler(BlockingQueue<Task> taskArrivalQueue) {
        super(taskArrivalQueue);
    }

    @Override
    public void run() {

    }

    @Override
    public void runSchedule() {

    }
}
