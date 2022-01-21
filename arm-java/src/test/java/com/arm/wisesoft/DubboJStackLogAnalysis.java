package com.arm.wisesoft;

import javax.xml.transform.Source;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DubboJStackLogAnalysis {

    public static Map<String, Integer> analysis(File input, String filter) throws IOException {
        Map<String, Integer> map = new HashMap<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(input)));
        String a = "";
        while ((a = reader.readLine()) != null) {
            if (a.contains(filter)) {
                map.merge(a, 1, Integer::sum);
            }
        }
        return map;
    }

    public static void main(String[] args) throws IOException {
        Map<String, Integer> analysis = analysis(new File("/Users/zhaolangjing/Downloads/Dubbo_JStack.log.2021-12-17_10_57_39"), "com.wisesoft.archives.impl.dao");
        System.out.println(analysis);
        System.out.println("success");
    }
}
