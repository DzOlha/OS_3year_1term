package os.lab_1.compfunc;

import java.io.IOException;
import java.io.PipedOutputStream;

public class GThread implements Runnable {
    private PipedOutputStream gOutput;
    private int value;
    public GThread(PipedOutputStream out, int x){
        this.gOutput = out;
        this.value = x;
        new Thread(this, "G_function").start();
    }
    public int getInputValue(){
        return value;
    }
    public void setInputValue(int inputValue){
        value = inputValue;
    }
    @Override
    public void run() {
        FunctionClient g = new FunctionClient("G", gOutput, value);
        g.compute();
        System.out.println("finished");
    }
}
