# 一.创建线程

## 基本创建

+ 继承Thread

```
public class JavaThread extends Thread {
    @Override
    public void run() {
        System.out.println("JavaThread extends thread");
    }
    public static void main(String[] args) {
        new JavaThread().start();
    }
}
```

+ 实现Runnable

```
public class JavaThread2 implements Runnable {
    @Override
    public void run() {
        System.out.println("JavaThread2 implement runnable ");
    }
    public static void main(String[] args) {
        new Thread(new JavaThread()).start();
    }
}
```

+ 实现Callable, FutureTask ,FutureTask可以等待线程的返回值

```
public class JavaThread3 implements Callable<Integer> {
    private static final Integer VALUE = 0;
    @Override
    public Integer call() {
        System.out.println(Thread.currentThread().getName() + ":JavaThread3 implement Callable ");
        return VALUE + 1;
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<>(new JavaThread3());
        new Thread(futureTask, "有返回值的线程").start();
        System.out.println(Thread.currentThread().getName() + futureTask.get());
    }
}
```

## 线程池创建（Executor Executors ExecutorService ThreadPoolExecutor）

> Executor是线程池(ExecutorService)的顶级接口，但是Executor严格来说并无线程池功能，其提供的方法execute()只是一个线程执行的工具。真正的线程池接口是ExecutorService
> Executors是java提供的默认线程池实现类，其提供了4重方法
> 使用ThreadPoolExecutor自定义线程池

### Executors 4种方式

+ 第一种：固定线程池 ExecutorService(ThreadPoolExecutor) newFixedThreadPool(int nThreads)

> 1.创建一个（可重用）固定数量线程的线程池，以共享的无界（其实是Integer.MAX_VALUE大小的）队列方式来运行这些线程。  
> 2.该线程池的线程数量不会扩展，线程的最大活跃数即为nThreads；创建的线程也不会被回收，线程数量将一直为nThreads个。  
> 3.当所有线程处于活跃状态（run）时提交了任务，则这些任务将处于队列中，直接有可用的线程。   
> 4.如果任何线程在关闭前的执行过程中因失败而终止（线程执行过程中如果抛出异常，则该线程将被终止），则在需要执行后续任务时，将有一个新线程取代它；
> 上述意思是：如果线程在执行过程中抛出异常，则该线程将被终止，线程池将会生成一个新的线程来替代它，执行后序的任务。  
> 5.池中的线程将一直存在，直到它被明确shutdown  
> 6.场景：使用在并发量不大，任务执行周期长的场景下  
> 7.弊端：使用无届队列，在任务执行时间过长情况下，可能会造成队列过大儿OOM

```
ExecutorService executorService = Executors.newFixedThreadPool(2);
for (int i = 0; i < 5; i++) {
    executorService.execute(() -> {
        System.out.println(Thread.currentThread().getName() + " is running .." + Calendar.getInstance().getTime());
    });
}
```

+ 第二种 缓存线程池 ExecutorService(ThreadPoolExecutor) newCachedThreadPool()

> 1.优化使用线程池的闲置的线程，（提高线程的复用率）如果没有，将会重新构造一个新的线程  
> 2.线程在线程池中被保留的时间是60s，核心线程数是0，这意味着一旦长时间没有任务，线程池中的线程可能会被清零。  
> 3.最大线程数为Integer.MAX_VALUE ， 这可能会造成线程数创建过多，而服务器资源不足的情况。  
> 4.场景：用于处理 大并发，周期短的任务。

+ 第三种 单一线程池 ExecutorService newSingleThreadExecutor() == newFixedThreadPool(1)

> 1.场景：在异步情况下，多个任务需要操作同一个数据的情况。比如：在一次api调用中，程序最后需要执行一个周期很长的存储过程，这时该过程使用异步执行，
> 程序直接返回结果，但是如果每次请求都从新开一个线程去执行存储过程，会造成数据死锁。在该场景下，我们就希望在过程是一个线性执行的情况。

+ 第四种 周期性线程池 ScheduledExecutorService newScheduledThreadPool()

> 1.创建一个线程池，可以安排命令在给定延迟后运行，或定期执行 。（延迟执行，周期执行任务）

```
public void threadPoolExecutorTest2() {
    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
    for (int i = 0; i < 3; i++) {
        scheduledExecutorService.schedule(() -> {
            // 打印日志， 程序启动10s后开始执行任务
            System.out.println(Thread.currentThread().getName() + " is running .." + Calendar.getInstance().getTime());
        }, 10, TimeUnit.SECONDS);
    }
    while (true) { }
}
```

### 自定义线程池（ThreadPoolExecutor）

![四个构造函数](../../data/img/thread/t2.png)

+ 参数说明

| #   | 名称         | 类型    | 名称        |
| --- | ------------ | ------ | -----------|
| 1   | corePoolSize | int    |核心线程池大小|
| 2   | maximumPoolSize | int    |最大线程池大小|
| 3   | keepAliveTime | long    |线程最大空闲时间|
| 4   | unit | TimeUnit    |时间单位|
| 5   | workQueue | BlockingQueue<Runnable>  |线程等待队列 [介绍](#BlockingQueue（阻塞队列）介绍)  |
| 6   | threadFactory | ThreadFactory    |线程创建工厂,可自定义如何创构建线程|
| 7   | handler | RejectedExecutionHandler    |拒绝策略 [介绍](#RejectedExecutionHandler(拒接策略)介绍)|

```
public class CustomThreadPoll {
    public static void main(String[] args) throws InterruptedException, IOException {
        int corePoolSize = 2;
        int maximumPoolSize = 4;
        long keepAliveTime = 10;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(2);
        ThreadFactory threadFactory = new NameTreadFactory();
        RejectedExecutionHandler handler = new MyIgnorePolicy();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit,
                workQueue, threadFactory, handler);
        executor.prestartAllCoreThreads(); // 预启动所有核心线程

        for (int i = 1; i <= 10; i++) {
            MyTask task = new MyTask(String.valueOf(i));
            executor.execute(task);
        }

        System.in.read(); //阻塞主线程
    }

    static class NameTreadFactory implements ThreadFactory {
        private final AtomicInteger mThreadNum = new AtomicInteger(1);
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "my-thread-" + mThreadNum.getAndIncrement());
            System.out.println(t.getName() + " has been created");
            return t;
        }
    }

    static class MyIgnorePolicy implements RejectedExecutionHandler {

        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            doLog(r, e);
        }

        private void doLog(Runnable r, ThreadPoolExecutor e) {
            // 可做日志记录等
            System.err.println(r.toString() + " rejected");
        }
    }

    static class MyTask implements Runnable {
        private String name;

        public MyTask(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                System.out.println(this.toString() + " is running!");
                Thread.sleep(3000); //让任务执行慢点
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "MyTask [name=" + name + "]";
        }
    }
}
```

# 二.队列和决绝策略

## BlockingQueue（阻塞队列）介绍

> 特性：一个阻塞的，线程安全的队列。  
> 阻塞队列让我们无需关系生产者，消费者 何时阻塞线程，何时唤醒线程，减轻了我们维护的难度

+ 方法介绍

  | #   | 抛出异常      | 返回特殊值（null或false）| 无限期阻塞，直到操作成功 | 阻塞给定时间，然后放弃 |
          | --- | ------------ | ------ | -----------|  -----------|
  | 添加   | add(e) | offer(e)   |put(e)|offer(e, time, unit)|
  | 移除  | remove() | poll()  |take()|poll(time, unit)|
  | 检查  | element() | peek()    |无|无|

1.放入数据

（1）offer(anObject):表示如果可能的话,将anObject加到BlockingQueue里,即如果BlockingQueue可以容纳,则返回true,否则返回false.（本方法不阻塞当前执行方法

的线程）； （2）offer(E o, long timeout, TimeUnit unit)：可以设定等待的时间，如果在指定的时间内，还不能往队列中加入BlockingQueue，则返回失败。

（3）put(anObject):把anObject加到BlockingQueue里,如果BlockQueue没有空间,则调用此方法的线程被阻断直到BlockingQueue里面有空间再继续.

2. 获取数据

（1）poll(time):取走BlockingQueue里排在首位的对象,若不能立即取出,则可以等time参数规定的时间,取不到时返回null;

（2）poll(long timeout, TimeUnit unit)：从BlockingQueue取出一个队首的对象，如果在指定时间内，队列一旦有数据可取，则立即返回队列中的数据。否则知道时间

超时还没有数据可取，返回失败。

（3）take():取走BlockingQueue里排在首位的对象,若BlockingQueue为空,阻断进入等待状态直到BlockingQueue有新的数据被加入;

（4）drainTo():一次性从BlockingQueue获取所有可用的数据对象（还可以指定获取数据的个数），通过该方法，可以提升获取数据效率；不需要多次分批加锁或释放锁。

+ 实现类

| #   | 类      | 特性 阻塞给定时间，然后放弃 |
| --- |   -----------|    -----------|
|    | ArrayBlockingQueue| 1.先进先出 2.有界队列，队列大小在创建时已经确定，无法更改 |
|    | LinkedBlockingQueue| 1.先进先出 2.有界队列，创建没有指定大小，其容量为（Integer.MAX_VALUE），存在OOM的风险 |
|    | DelayQueue| 元素只有当其指定的延迟时间到了，才能够从队列中获取到该元素。DelayQueue是一个没有大小限制的队列，因此往队列中插入数据的操作（生产者）永远不会被阻塞，而只有获取数据的操作（消费者）才会被阻塞 |
|    | PriorityBlockingQueue|  基于优先级的无界(OOM)阻塞队列 |
|    | SynchronousQueue| 无缓冲队列，同步队列，内部容量为0，每个put操作必须等待一个take操作，反之亦然。类似余生产者与消费者直接交易 |

## RejectedExecutionHandler(拒接策略)介绍

> 当没有更多线程或队列插槽可用时,ThreadPoolExecutor将执行拒接策略。

+ 默认策略

| #   | 名称         | 特性    | 
| --- | ------------ | ------ | 
| 1   | AbortPolicy |  处理程序遭到拒绝将抛出运行时RejectedExecutionException;    |
| 2   | CallerRunsPolicy | 由调度线程从新执行该任务   |
| 3   | DiscardPolicy | 丢弃任务（不执行任何操作），但是不抛出异常    |
| 4   | DiscardOldestPolicy | 丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）    |

# 三.executor簇介绍
## 结构
```
Executor
  ｜-- ExecutorService
     ｜-- AbstractExecutorService
        ｜-- ThreadPoolExecutor    
          ｜-- ScheduledThreadPoolExecutor 
     ｜-- ScheduledExecutorService
       ｜-- ScheduledThreadPoolExecutor            
```


## Executor接口
> Executor 提供了方法：void execute(Runnable command);  根据Executor实现的判断，该命令可以在新线程、池线程或调用线程中执行

## AbstractExecutorService抽象类
> 其提供了几类方法，下面一一说明：
> 1. protected newTaskFor 创建一个可用的RunnableFuture任务，以供线程调度执行。  
> 2. submit 提交任务并返回结果  
> 3. List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) 批量执行任务并返回结果集。  
> 4. invokeAny 执行并返回成功的值，任一任务完成任务执行立即返回结果。  
> 5. execute 执行任务无返回结果  
> 6. shutdown 安全关闭，已有任务正常执行，但不会接收新任务。  
> 7. shutdownNow 不安全关闭，尝试停止所有正在执行的任务，停止等待任务的处理，并返回等待执行的任务列表。  
> 8. isShutdown 不安全关闭，尝试停止所有正在执行的任务，停止等待任务的处理，并返回等待执行的任务列表。  
> 9. isTerminated 如果关闭后所有任务都已完成，则返回true 。 请注意，除非先调用shutdown或shutdownNow ，否则isTerminated永远不会为true 。 
> 10. awaitTermination 


