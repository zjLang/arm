package com.arm.mac.clean;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.unit.DataSizeUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author z-ewa
 */
public class Clean {

    public static final String TARGET_DIRECTORY = "target";
    public static final String TARGET_SEARCH_DIRECTORY = "/Users/zhaolangjing/code/";

    public static final String REPOSITORY_SEARCH_DIRECTORY = "/Users/zhaolangjing/repository";

    public static final Map<String, Long> target_file = new HashMap<>();

    public static void main(String[] args) {
        //cleanTarget();
        //cleanWar();
    }


    private static void cleanWar() {
        long start = System.currentTimeMillis();
        File[] files = new File(REPOSITORY_SEARCH_DIRECTORY).listFiles();
        repositoryCalculation(files);
        Long allSize = 0L;
        for (Long value : target_file.values()) {
            allSize += value;
        }
        System.out.println(String.format("查找目录文件的总大小为：%s", DataSizeUtil.format(allSize)));
        /*target_file.forEach((file, size) -> {
            FileUtil.del(file);
        });*/
        System.out.println(String.format("执行成功，总执行时间：%s s ", (System.currentTimeMillis() - start) / 1000));
    }

    private static void repositoryCalculation(File[] files) {
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isFile() && (FileUtil.extName(file).equals("war") || file.getName().contains("-main"))) {
                    long size = FileUtil.size(file);
                    target_file.put(file.getAbsolutePath(), size);
                    System.out.println(String.format("查找到文件【%s】其大小为【%s】", file.getAbsolutePath(), size));
                } else if (file.isDirectory()) {
                    repositoryCalculation(file.listFiles());
                }
            }
        }
    }


    private static void cleanTarget() {
        long start = System.currentTimeMillis();
        //File userHomeDir = FileUtil.getUserHomeDir();
        File[] files = new File(TARGET_SEARCH_DIRECTORY).listFiles();
        calculation(files);
        AtomicLong allSize = new AtomicLong(0L);
        target_file.forEach((file, size) -> {
            allSize.addAndGet(size);
        });
        System.out.println(String.format("查找目录文件的总大小为：%s", DataSizeUtil.format(allSize.get())));
        target_file.forEach((file, size) -> {
            FileUtil.del(file);
        });
        System.out.println(String.format("执行成功，总执行时间：%s s ", (System.currentTimeMillis() - start) / 1000));
    }

    private static void calculation(File[] files) {
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isDirectory() && TARGET_DIRECTORY.equals(file.getName())) {
                    long size = FileUtil.size(file);
                    target_file.put(file.getAbsolutePath(), size);
                    System.out.println(String.format("查找到文件【%s】其大小为【%s】", file.getAbsolutePath(), size));
                } else if (file.isDirectory()) {
                    calculation(file.listFiles());
                }
            }
        }
    }

    private static String getMyPath() {
        String osName = System.getProperty("os.name");
        if (osName.startsWith("Mac OS")) {
            String home = System.getenv("HOME");
            return home;
        } else {
            throw new RuntimeException("home 目录不存在.");
        }
    }
}
