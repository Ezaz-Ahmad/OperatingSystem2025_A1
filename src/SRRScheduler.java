import java.util.*;

public class SRRScheduler extends Scheduler {
    protected Deque<Process> ready;

    public SRRScheduler(int disp, List<Process> allProcesses) {
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
        return p.quantum;
    }

    @Override
    protected void updateOnPreempt(Process p) {
        p.quantum = Math.min(p.quantum + 1, 6);
    }
}