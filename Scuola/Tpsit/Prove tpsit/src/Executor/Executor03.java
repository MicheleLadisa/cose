package Executor;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.Random;

public class Executor03
{

    public static void main(String[] args)
    {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        for (int i = 0; i <= 5; i++)
        {
            Task task = new Task("Task n." + i);
            System.out.println("A new task has been added : " + task.getName());
            executor.execute(task);
        }
        executor.shutdown();
    }
}

class Task implements Runnable
{

    private static final Random r = new Random(); // use nanotime for seed

    private String name;

    public Task(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public void run()
    {
        try
        {
            int duration = 1 + r.nextInt(9);
            System.out.println("Doing a task during : " + name);
            System.out.println(Thread.currentThread().getName());
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
