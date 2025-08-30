public class Process {
    String id;
    int arrival;
    int service;
    int remaining;
    int finish;
    int turnaround;
    int waiting;
    int quantum;
    int priority;

    public Process(String id, int arrival, int service) {
        this.id = id;
        this.arrival = arrival;
        this.service = service;
        reset();
    }

    public void reset() {
        remaining = service;
        finish = 0;
        turnaround = 0;
        waiting = 0;
        quantum = 3;
        priority = 0;
    }

    public int getIdNumber() {
        return Integer.parseInt(id.substring(1));
    }
}