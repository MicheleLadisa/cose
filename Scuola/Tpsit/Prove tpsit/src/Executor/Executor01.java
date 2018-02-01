package Executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Executor01
{

    public static void main(String[] args)
    {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() ->
        {
            String threadName = Thread.currentThread().getName();
            System.out.println("Hello1 " + threadName);
        });
        executor.submit(() ->
        {
            String threadName = Thread.currentThread().getName();
            System.out.println("Hello2 " + threadName);
        });

    }
}
