import java.io.*;
import javax.swing.*;
import java.util.*;

public class ThreshTool {
    static void plot() throws Exception {
        ProcessBuilder builder = new ProcessBuilder(
            "ThresholdGraph","[-1,1,-1,0,5,-6]","[1,2,3,4,5,6]","0");
        builder.redirectErrorStream(false);
        Process p = builder.start();
    }
}