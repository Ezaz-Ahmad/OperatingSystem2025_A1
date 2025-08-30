import java.util.*;

public abstract class Scheduler {
    protected int disp;
    protected List<Process> allProcesses;
    protected Process currentProcess;
    protected int currentTime;
    protected int processIndex;
    protected int timeSliceEnd;

    public Scheduler(int disp, List<Process> allProcesses) {
        this.disp = disp;
        this.allProcesses = allProcesses;
    }

    abstract protected void addToReady(Process p);

    abstract protected Process pickNext();

    abstract protected boolean shouldPreempt();

    abstract protected int getQuantum(Process p);

    abstract protected void updateOnPreempt(Process p);

    public void simulate() {
        currentProcess = null;
        currentTime = 0;
        processIndex = 0;
        timeSliceEnd = 0;

        while (!allDone()) {
            int nextTime = Integer.MAX_VALUE;
            if (processIndex < allProcesses.size()) {
                nextTime = Math.min(nextTime, allProcesses.get(processIndex).arrival);
            }
            if (currentProcess != null) {
                nextTime = Math.min(nextTime, timeSliceEnd);
            }

            if (nextTime > currentTime) {
                if (currentProcess == null) {
                    currentTime = nextTime;
                } else {
                    int advance = Math.min(nextTime - currentTime, currentProcess.remaining);
                    currentTime += advance;
                    currentProcess.remaining -= advance;
                    if (currentProcess.remaining == 0) {
                        currentProcess.finish = currentTime;
                        currentProcess.turnaround = currentProcess.finish - currentProcess.arrival;
                        currentProcess.waiting = currentProcess.turnaround - currentProcess.service;
                        currentTime += disp; // Add DISP after completion
                        currentProcess = null;
                    }
                }
            } else {
                currentTime = nextTime;
            }

            while (processIndex < allProcesses.size() && allProcesses.get(processIndex).arrival <= currentTime) {
                addToReady(allProcesses.get(processIndex++));
            }

            if (currentProcess != null && shouldPreempt()) {
                addToReady(currentProcess);
                currentProcess = null;
            }

            if (currentProcess != null && timeSliceEnd == currentTime) {
                updateOnPreempt(currentProcess);
                addToReady(currentProcess);
                currentTime += disp; // Add DISP after quantum expiry
                currentProcess = null;
            }

            if (currentProcess == null) {
                int dispatchStart = currentTime;
                Process next = pickNext();
                if (next == null) continue;
                currentTime += disp; // Add DISP for dispatch
                while (processIndex < allProcesses.size() && allProcesses.get(processIndex).arrival <= currentTime
                        && allProcesses.get(processIndex).arrival > dispatchStart) {
                    addToReady(allProcesses.get(processIndex++));
                }
                currentProcess = next;
                System.out.println("T" + currentTime + ": " + currentProcess.id);
                int quantum = getQuantum(currentProcess);
                timeSliceEnd = currentTime + quantum;
            }
        }
    }

    private boolean allDone() {
        for (Process p : allProcesses) {
            if (p.remaining > 0) return false;
        }
        return true;
    }
}