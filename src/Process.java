public class Process {
    String pid;
    int arrival;
    double execTime;
    double sysCalls;
    double ioTime;
    int priority;

    double turnaroundTime = 0;
    double waitTime = 0;

    @Override
    public String toString() {
        return "Process{" +
                "pid='" + pid + '\'' +
                ", arrival=" + arrival +
                ", execTime=" + execTime +
                ", sysCalls=" + sysCalls +
                ", ioTime=" + ioTime +
                ", priority=" + priority +
                '}';
    }

    // needs formatting
    public String result(){
        return  "pid='" + pid + '\'' +
                ", turnaround=" + turnaroundTime +
                ", total wait time=" + waitTime;
    }
}
