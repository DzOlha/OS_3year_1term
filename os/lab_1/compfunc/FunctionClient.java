package os.lab_1.compfunc;

import os.lab_1.compfunc.advanced.IntOps;

import java.io.IOException;
import java.io.PipedOutputStream;
import java.util.Optional;
import java.util.function.Function;

public class FunctionClient {
    private final String type;
    private PipedOutputStream out;
    private Function<Integer, Optional<Optional<Integer>>> function;
    private Integer value;
    private String result;
    private int resultIsNumeric;

    public FunctionClient(String t, PipedOutputStream outputStream, Integer x) {
        type = t;
        out = outputStream;
        value = x;
        assign(type);
    }
    private void assign(String type){
        if(type == "F"){
            function = IntOps::trialF;
        }
        if(type == "G"){
            function = IntOps::trialG;
        }
    }

    public void compute(){
        System.out.println(Thread.currentThread().getName() + ": Computation of the value...");
        Optional<Optional<Integer>> packed_result = function.apply(value);

        if(!packed_result.isPresent()){
            result = "hard fail";
            resultIsNumeric = 72;
        } else if (!packed_result.get().isPresent()){
            result = "soft fail";
            resultIsNumeric = 83;
        } else {
            resultIsNumeric = packed_result.get().get();
            result = "computed value = " + resultIsNumeric;
        }
        System.out.println("\n" + type + " - " + result);
        passResultToPipedOutputStream(resultIsNumeric);
    }

    private void passResultToPipedOutputStream(int res) {
        System.out.println(Thread.currentThread().getName() + ": Passing of the result...");
        try {
            out.write(res);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
