public class Clock {
    public double time;

    public Clock(){
        time = 0.0;
    }

    public double tick(){
        time += 0.1;
        return time;
    }

    public void reset(){
        time = 0;
    }
}
