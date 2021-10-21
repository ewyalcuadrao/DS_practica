import java.util.*;
import java.time.*;
import javax.swing.*;

public class Main {

    public static void main(String[] args)  {
        simpleTest();
    }
    private static void simpleTest()
    {
        //instance clocktimer
        Project p0 = new Project("p1");
        Task t0 = new Task("study",p0);
        t0.startWorking();
        System.out.println("Task in progress...");
        t0.stopWorking();
        t0.startWorking();
        t0.stopWorking();
        System.out.println("Task in progress...");


        System.out.println(t0.init);
        System.out.println(t0.end);
        System.out.println(t0.totalTime);
        //hay que revisar todo esto

    }
}
