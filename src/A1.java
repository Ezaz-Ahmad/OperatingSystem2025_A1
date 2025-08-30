import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class A1 {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java A1 <inputfile>");
            System.exit(1);
        }
        String filename = args[0];
        int disp = 0;
        List<Process> processes = new ArrayList<>();
        try {
            Scanner sc = new Scanner(new File(filename));
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.startsWith("DISP:")) {
                    disp = Integer.parseInt(line.substring(5).trim());
                } else if (line.startsWith("PID:")) {
                    String id = line.substring(4).trim();
                    line = sc.nextLine().trim();
                    int arrTime = Integer.parseInt(line.substring(8).trim());
                    line = sc.nextLine().trim();
                    int srvTime = Integer.parseInt(line.substring(8).trim());
                    processes.add(new Process(id, arrTime, srvTime));
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filename);
            System.exit(1);
        }

        processes.sort(Comparator.comparingInt((Process p) -> p.arrival).thenComparingInt(Process::getIdNumber));

        String[] algorithms = {"FCFS", "RR", "SRR", "FB"};
        Map<String, Double> avgTurnarounds = new LinkedHashMap<>();
        Map<String, Double> avgWaitings = new LinkedHashMap<>();

        for (String algo : algorithms) {
            for (Process p : processes) {
                p.reset();
            }
            System.out.println(algo + ":");
            Scheduler scheduler = switch (algo) {
                case "FCFS" -> new FCFSScheduler(disp, processes);
                case "RR" -> new RRScheduler(disp, processes);
                case "SRR" -> new SRRScheduler(disp, processes);
                case "FB" -> new FBScheduler(disp, processes);
                default -> null;
            };
            scheduler.simulate();
            System.out.println("Process  Turnaround Time  Waiting Time");
            for (Process p : processes) {
                System.out.printf("%-8s %-15d %-15d%n", p.id, p.turnaround, p.waiting);
            }

            double sumTurn = 0.0;
            double sumWait = 0.0;
            for (Process p : processes) {
                sumTurn += p.turnaround;
                sumWait += p.waiting;
            }
            double avgTurn = sumTurn / processes.size();
            double avgWait = sumWait / processes.size();
            //System.out.printf("Average Turnaround time: %.2f%n", avgTurn);
            //System.out.printf("Average Waiting time: %.2f%n", avgWait);
            //System.out.printf(" ");
            System.out.printf(" ");
            avgTurnarounds.put(algo, avgTurn);
            avgWaitings.put(algo, avgWait);
            System.out.printf(" ");
        }

        System.out.println("Summary");
        System.out.println("Algorithm  Average Turnaround Time  Waiting Time");
        for (String algo : algorithms) {
            System.out.printf("%-10s %-22.2f %-15.2f%n", algo, avgTurnarounds.get(algo), avgWaitings.get(algo));
        }
    }
}