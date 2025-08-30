import java.util.*;

public class RRScheduler extends Scheduler {
    protected Deque<Process> ready;

    public RRScheduler(int disp, List<Process> allProcesses) {
        super(disp, allProcesses);
        ready = new LinkedList<>();
    }

    @Override
    protected void addToReady(Process p) {
        ready.addLast(p);
    }

    @Override
    protected Process pickNext() {
        return ready.pollFirst();
    }

    @Override
    protected boolean shouldPreempt() {
        return false;
    }

    @Override
    protected int getQuantum(Process p) {
        return 3;
    }

    @Override
    protected void updateOnPreempt(Process p) {
        // No update for RR
    }
}