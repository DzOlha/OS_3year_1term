package os.lab_1.compfunc;

import java.io.IOException;
import java.io.PipedOutputStream;

public class FThread implements Runnable {
    private PipedOutputStream fOutput;
    private int value;
    public FThread(PipedOutputStream out, int x){
        this.fOutput = out;
        this.value = x;
        new Thread(this, "F_function").start();
    }
    public int getInputValue(){
        return value;
    }
    public void setInputValue(int inputValue){
        value = inputValue;
    }
    @Override
    public void run() {
        FunctionClient f = new FunctionClient("F", fOutput, value);
        f.compute();
        System.out.println("finished");
    }
}
