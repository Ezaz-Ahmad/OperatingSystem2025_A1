import java.util.*;

public class FCFSScheduler extends Scheduler {
    protected Deque<Process> ready;

    public FCFSScheduler(int disp, List<Process> allProcesses) {
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
        return p.remaining;
    }

    @Override
    protected void updateOnPreempt(Process p) {
        // No update for FCFS
    }
}