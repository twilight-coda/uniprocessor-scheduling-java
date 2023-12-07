package schedules.feedbackScheduling;

import tasks.Task;

import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FeedbackTasksContainer implements Iterable<Task> {

    @Override
    public Iterator<Task> iterator() {
        return new FeedbackIterator(this);
    }

    class FeedbackIterator implements Iterator<Task> {
        private final FeedbackTasksContainer taskContainer;

        public FeedbackIterator(FeedbackTasksContainer taskContainer) {
            this.taskContainer = taskContainer;
        }

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Task next() {
            return null;
        }
    }
}
