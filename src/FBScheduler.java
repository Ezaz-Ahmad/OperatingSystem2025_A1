import java.util.*;

public class FBScheduler extends Scheduler {
    protected Deque<Process>[] fbQueues;

    public FBScheduler(int disp, List<Process> allProcesses) {
        super(disp, allProcesses);
        fbQueues = new Deque[4];
        for (int i = 0; i < 4; i++) {
            fbQueues[i] = new LinkedList<>();
        }
    }

    @Override
    protected void addToReady(Process p) {
        fbQueues[p.priority].addLast(p);
    }

    @Override
    protected Process pickNext() {
        for (int i = 0; i < 4; i++) {
            if (!fbQueues[i].isEmpty()) {
                return fbQueues[i].pollFirst();
            }
        }
        return null;
    }

    @Override
    protected boolean shouldPreempt() {
        if (currentProcess == null) return false;
        for (int i = 0; i < currentProcess.priority; i++) {
            if (!fbQueues[i].isEmpty()) return true;
        }
        return false;
    }

    @Override
    protected int getQuantum(Process p) {
        return 3;
    }

    @Override
    protected void updateOnPreempt(Process p) {
        if (p.priority < 3) p.priority++;
    }
}