import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    static Process[] p;
    static Clock clock = new Clock();

    public static void main(String[] args) {
        try {
            readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SJF();
    }

    public static void readFile() throws IOException{
        Scanner s = new Scanner(new File("D:\\Documents\\School\\192\\ICS 431\\HW4\\QB3\\sample_input.txt"));

        // Count the total number of processes.
        int countLines = 0;
        while (s.hasNextLine()){
            countLines++;
            s.nextLine();
        }

        s.close();
        s = new Scanner(new File("D:\\Documents\\School\\192\\ICS 431\\HW4\\QB3\\sample_input.txt"));

        p = new Process[countLines-1];

        // Initialize each process in the array from the file.
        for (int i = 0; i < p.length; i++) {
            p[i] = new Process();
            p[i].pid = s.next();
            p[i].arrival = s.nextInt();
            p[i].execTime = s.nextDouble();
            p[i].sysCalls = s.nextDouble();
            p[i].ioTime = s.nextDouble();
            p[i].priority = s.nextInt();
        }

        for (Process i : p) {
            System.out.println(i);
        }

        System.out.println();

        s.close();
    }

    public static void FCFS(){
        // A queue with processes arranged according to arrival time.
        Process[] queue = p;

        // Sort queue.
        for (int i = 0; i < queue.length; i++) {
            Process tmp = queue[i];
            int j = i - 1;

            /* Move elements of queue[0..i-1], that are
               greater than tmp, to one position ahead
               of their current position */
            while (j >= 0 && queue[j].arrival > tmp.arrival) {
                queue[j + 1] = queue[j];
                j = j - 1;
            }
            queue[j + 1] = tmp;
        }

        /*for (Process i : queue) {
            System.out.println(i);
        }*/

        // Compute turnaround and wait for each process.
        for (int i = 0; i < queue.length; i++) {
            for (int j = 0; j < p.length; j++) {
                if(i == 0 && queue[i].pid.equals(p[j].pid)){
                    p[j].waitTime = 0;
                    p[j].turnaroundTime = p[j].waitTime + p[j].execTime + p[j].sysCalls + p[j].ioTime;
                } else if(queue[i].pid.equals(p[j].pid)){
                    p[j].waitTime = queue[i-1].execTime - p[j].arrival;
                    p[j].turnaroundTime = p[j].waitTime + p[j].execTime + p[j].sysCalls + p[j].ioTime;
                }
            }
        }

        for (Process i : p) {
            System.out.println(i.result());
        }

        // Average and standard deviation

    }

    public static void SJF(){
        int processesNotFinished = p.length;
        Process[] queue = new Process[p.length];
        int queueSize = 0;

        while(processesNotFinished > 0){

            // Add newly arrived processes.
            for (int i = 0; i < p.length; i++) {
                if (p[i].arrival >= clock.time && !contains(queue, p[i])){
                    queue[queueSize] = p[i];
                    queueSize++;
                    queue = SJFSort(queue);
                }
            }

            if(queueSize > 0){
                // Add to waiting time of processes still in queue.
                for (int i = 1; i < queueSize; i++) {
                    for (int j = 0; j < p.length; j++) {
                        if(queue[i].pid.equals(p[j].pid)){
                            p[j].waitTime += 0.1;
                        }
                    }
                }

                queue[0].execTime -= 0.1; // Process is executing
                if(queue[0].execTime <= 0){ // Process is finished
                    processesNotFinished--;

                    for (int i = 0; i < p.length; i++) {
                        if(queue[0].pid.equals(p[i].pid)){
                            p[i].turnaroundTime = clock.time + p[i].sysCalls + p[i].ioTime - p[i].arrival;    // Compute turnaround time.
                        }
                    }

                    queue = SJFSort(queue);
                    queueSize--;
                }
            }

            clock.tick();
        }

        for (Process i : p) {
            System.out.println(i.result());
        }

        // Average and standard deviation

    }

    // Sort queue according to execution time.
    public static Process[] SJFSort(Process[] queue){
        if(queue[0].execTime <= 0){
            for (int i = 1; i < queue.length; i++) {
                queue[i-1] = queue[i];
            }
            return queue;
        }

        for (int i = 0; i < queue.length; i++) {
            Process tmp = queue[i];
            int j = i - 1;

            /* Move elements of queue[0..i-1], that are
               greater than tmp, to one position ahead
               of their current position */
            while ( j >= 0 && tmp != null && queue[j] != null && queue[j].execTime > tmp.execTime) {
                queue[j + 1] = queue[j];
                j = j - 1;
            }
            queue[j + 1] = tmp;
        }
        return queue;
    }

    public static boolean contains(Process[] arr, Process p){

        for (int i = 0; i < arr.length; i++) {
            if(p != null && arr[i] != null && p.pid.equals(arr[i].pid))
                return true;
        }

        return false;
    }

    public static void RR(int qTime){

    }
}
