package com.arm.dds.core.core;

import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public abstract class AbstractDynamicDataSource implements DynamicDataSource {

    /**
     * 最大可创建的数据源管理池对象。
     */
    protected volatile int maxActive = DEFAULT_MAX_ACTIVE_SIZE;

    /**
     * 回收时触发次数
     */
    protected volatile int reclaimTries = DEFAULT_RECLAIM_TRIES_SIZE;

    /**
     * 线程回收数据源资源时 间隔多少时间执行一次
     */
    protected volatile long timeBetweenEvictionRunsMillis = DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS;

    protected volatile boolean close;
    protected volatile boolean init;

    private static final int DEFAULT_DATASOURCE_SIZE = 16;
    /**
     * 数据源的缓存
     */
    protected ConcurrentHashMap<String, DataSource> dataSourceMap = new ConcurrentHashMap<>(DEFAULT_DATASOURCE_SIZE);

    /**
     * 数据源类型的缓存
     */
    //protected ConcurrentHashMap<String, String> dataSourceTypeMap = new ConcurrentHashMap<>();

    /**
     * 回收dataSource的线程
     */
    protected DestroyDataSourceThread destroyDataSourceThread;


    private Lock lock = new ReentrantLock(false); // 非公平锁


    public AbstractDynamicDataSource() {
        init(null, null, null);
    }

    public AbstractDynamicDataSource(Integer maxActive, Integer reclaimTries, Integer timeBetweenEvictionRunsMillis) {
        init(maxActive, reclaimTries, timeBetweenEvictionRunsMillis);
    }

    /*@Override
    public String getDataSourceType(String dataSourceKey) {
        return dataSourceTypeMap.get(dataSourceKey); // 线程安全，单一操作，无需加锁
    }*/

    @Override
    public DataSource getDataSource(String dataSourceKey) {
        if (close) {
            return null;
        }
        return dataSourceMap.get(dataSourceKey);
    }

    @Override
    public DataSource setDataSource(DataSourceProperties properties) throws DynamicDataSourceException {
        try {
            if (close) {
                return null;
            }
            lock.lock();
            if (log.isDebugEnabled()) {
                log.debug("Start creating the data source :{}", properties);
            }
            // 触发回收资源
            if (dataSourceMap.size() == maxActive) {
                reclaimDataSource();
            }
            if (dataSourceMap.size() == maxActive) {
                throw new DynamicDataSourceException("Failed to reclaim data source while creating data source : {}" + properties);
            }
            DataSource dataSource;
            if ((dataSource = dataSourceMap.get(properties.getUrl())) == null) {
                dataSource = initDataSource(properties);
                if (dataSource != null) {
                    dataSourceMap.put(properties.getUrl(), dataSource);
                    if (log.isDebugEnabled()) {
                        log.debug(" creat the data source success:{}", dataSource);
                    }
                }
            }
            return dataSource;
        } finally {
            lock.unlock();
        }
    }


    @Override
    public boolean closeDataSource(String dataSourceKey) throws DynamicDataSourceException {
        if (close) {
            return true;
        }
        try {
            lock.lock();
            DataSource dataSource;
            if (!dataSourceMap.isEmpty() && (dataSource = dataSourceMap.get(dataSourceKey)) != null) {
                if (closeDataSource(dataSource)) {
                    dataSourceMap.remove(dataSourceKey);
                    if (log.isDebugEnabled()) {
                        log.debug(" close the data source success:{}", dataSource);
                    }
                }
            }
        } finally {
            lock.unlock();
        }
        return true;
    }

    @Override
    public boolean close() {
        if (close) {
            return true;
        }
        if (!init) {
            return true;
        }
        try {
            lock.lock();
            log.info("stop the Dynamic Data Source Manager ... " + this);
            if (destroyDataSourceThread != null) {
                destroyDataSourceThread.interrupt();
            }
            boolean closed = closeAllDataSource();
            if (closed) {
                dataSourceMap.clear();
                close = true;
                log.info("stop the Dynamic Data Source Manager success ... " + this);
            } else {
                log.info("stop the Dynamic Data Source Manager fail ... " + this);
            }
        } finally {
            lock.unlock();
        }
        return false;
    }

    @Override
    public boolean isClose() {
        return close;
    }


    /**
     * 初始化数据源，由不同的数据源实现去实现该操作
     *
     * @param properties
     * @return
     */
    protected abstract DataSource initDataSource(DataSourceProperties properties);

    /**
     * 回收资源
     *
     * @return
     */
    protected abstract int tryReclaimDataSource();

    protected abstract boolean closeDataSource(DataSource dataSource);

    protected abstract boolean closeAllDataSource();

    private int reclaimDataSource() {
        int reclaimNums = 0;
        int tries = 0;
        // 如果回收时，没有被回收掉 如果集合中的数据源存储的ds操过了集合的3/4大小才回收
        while (reclaimNums == 0 && reclaimTries >= 0 && tries <= reclaimTries && dataSourceMap.size() > DEFAULT_DATASOURCE_SIZE * 0.75) {
            reclaimNums = tryReclaimDataSource();
            tries++;
        }
        return reclaimNums;
    }


    /**
     * 初始化程序
     */
    private void init(Integer maxActive, Integer reclaimTries, Integer timeBetweenEvictionRunsMillis) {
        log.info("start initialize Dynamic Data Source Manager ... {}", this);

        if (maxActive != null) {
            this.maxActive = maxActive;
        }
        if (reclaimTries != null) {
            this.reclaimTries = reclaimTries;
        }
        if (timeBetweenEvictionRunsMillis != null) {
            this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
        }

        String deadThreadName = "DynamicDataSource-Destroy-" + getHash();
        destroyDataSourceThread = new DestroyDataSourceThread(deadThreadName);
        destroyDataSourceThread.start();
        this.init = true;
        log.info("start initialize Dynamic Data Source Manager over ... {}", this);
    }

    private int getHash() {
        return System.identityHashCode(this); // 返回当前对象的固定hash
    }

    public class DestroyDataSourceThread extends Thread {

        public DestroyDataSourceThread(String name) {
            super(name);
            this.setDaemon(true);
        }

        public void run() {
            for (; ; ) {
                // 从前面开始删除
                try {
                    if (close) {
                        break;
                    }
                    if (timeBetweenEvictionRunsMillis > 0) {
                        Thread.sleep(timeBetweenEvictionRunsMillis);
                    } else {
                        Thread.sleep(1000); //
                    }
                    // 线程被中断
                    if (Thread.interrupted()) {
                        break;
                    }

                    try {
                        lock.lock();
                        int reclaimNum = reclaimDataSource();
                        if (log.isDebugEnabled()) {
                            log.debug("reclaim un-useful datasource number: {}", reclaimNum);
                        }
                    } finally {
                        lock.unlock();
                    }

                } catch (InterruptedException e) {
                    break;
                }
            }
        }

    }
}
