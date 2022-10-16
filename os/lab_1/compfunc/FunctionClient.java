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
        if(type == "f"){
            function = IntOps::trialF;
        }
        if(type == "g"){
            function = IntOps::trialG;
        }
    }

    public void compute(){
        System.out.println(Thread.currentThread().getName() + ": Computation of the value...");
        Optional<Optional<Integer>> packed_result = function.apply(value);

        if(!packed_result.isPresent()){
            result = type + " - hard fail";
            resultIsNumeric = 0;
        } else if (!packed_result.get().isPresent()){
            result = type + " - soft fail";
            resultIsNumeric = 1;
        } else {
            result = type + " " + packed_result.get().get();
            resultIsNumeric = packed_result.get().get();
        }
        System.out.println(result);
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
