package os.lab_1.cancellation;

import os.lab_1.compfunc.FThread;
import os.lab_1.compfunc.GThread;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.function.BiFunction;

public class Manager implements Runnable {
    private PipedInputStream fInput;
    private PipedInputStream gInput;
    private PipedOutputStream fOutput;
    private PipedOutputStream gOutput;
    private int value;
    private String reasonFail = "";
    public Manager(int inputValue){
        fInput = new PipedInputStream();
        gInput = new PipedInputStream();
        fOutput = new PipedOutputStream();
        gOutput = new PipedOutputStream();

        try {
            fInput.connect(fOutput);
            gInput.connect(gOutput);
        } catch (IOException e){
            System.out.println("Error CONNECTING pipes!");
        }
        value = inputValue;
        new Thread(this, "Manager").start();
    }
    public int getInputValue(){
        return value;
    }
    public void setInputValue(int inputValue){
        value = inputValue;
    }
    @Override
    public void run() {
        new FThread(fOutput, value);
        new GThread(gOutput, value);
        try {
            int x = fInput.read();
            checkFail(x, "F");
            int y = gInput.read();
            checkFail(y, "G");

            if(reasonFail.isEmpty()) {
                System.out.println("\nResult: " + applyBiOperation(x, y));
            } else {
                System.out.println("\nResult can not be computed because of: " + reasonFail);
            }
        }catch (IOException ex){}
    }
    private int applyBiOperation(int x, int y) {
        return x+y;
    }
    private void checkFail(int x, String func){
        if(x == 72) {
            reasonFail = func + " = HARD FAIL!";
        }
        else {
            if (x == 83) {
                reasonFail = func + " = SOFT FAIL!";
            }
        }
    }
}
