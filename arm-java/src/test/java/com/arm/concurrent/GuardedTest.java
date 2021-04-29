package com.arm.concurrent;


import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 使用wait notify实现保护模式 保护模式设置
 *
 * @author zhaolangjing
 * @since 2021-3-11 22:28
 */
@Slf4j
public class GuardedTest {
    public static void main(String[] args) {
        GuardedObject guardedObject = new GuardedObject();
        new Thread( () -> {
            log.debug( "等待结果！" );
            log.debug( "结果是：{}", ((List<String>) guardedObject.get()).stream().collect( Collectors.joining() ) );
        }, "t1" ).start();

        new Thread( () -> {
            log.debug( "执行下载！" );
            try {
                guardedObject.set( Downloader.downloader() );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "t2" ).start();
    }
}


class GuardedObject {
    private Object response;

    // 可以思考下待时间的等待如何设计? 和join()方法类似
    public Object get() {
        synchronized (this) {
            while (response == null) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return response;
        }
    }

    public void set(Object response) {
        synchronized (this) {
            this.response = response;
            this.notifyAll();
        }
    }
}

class Downloader {
    public static List<String> downloader() throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL( "http://www.baidu.com" ).openConnection();
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader( conn.getInputStream(), StandardCharsets.UTF_8 ) )) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add( line );
            }
            return lines;
        }
    }
}
