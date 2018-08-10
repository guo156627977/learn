package com.gzq;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;
import org.jboss.netty.util.internal.ConcurrentHashMap;

import java.util.HashSet;
import java.util.List;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-08-07 9:48.
 */
public class ZkClientTest {

    private static String zkAddress = "guo001:2181";
    private static String path = "/test";

    private static ZkClient zkClient;

    private static ConcurrentHashMap<String, Object> localRatioCache = new ConcurrentHashMap<String, Object>();

    private static HashSet hashSet = new HashSet();
    static double radio = 0.1d;

    // static {
    //     zkClient = new ZkClient(zkAddress);
    //     // zkClient = new ZkClient(new IZkConnection() {
    //     // });
    // }


    public static void main(String[] args) throws InterruptedException {
        zkClient = new ZkClient(zkAddress);
        String ip1 = "/127.0.0.1";
        String ip2 = "/0.0.0.0";
        // zkClient.deleteRecursive(path);
        zkClient.createPersistent(path, true);
        zkClient.writeData(path, 0.01);
        zkClient = new ZkClient(new ZkConnection(zkAddress));
        final double a = 0d;
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {

                zkClient.subscribeDataChanges(path, new IZkDataListener() {
                    @Override
                    public void handleDataChange(String dataPath, Object data) throws Exception {
                        System.out.println("节点" + dataPath + "下变更为" + data.toString());
                        radio = Double.parseDouble(data.toString());
                    }

                    @Override
                    public void handleDataDeleted(String dataPath) throws Exception {
                        System.out.println(dataPath + "被删除了");
                    }
                });
                zkClient.subscribeChildChanges(path, new IZkChildListener() {

                    @Override
                    public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                        System.out.println("父节点: " + parentPath + ",子节点:" + currentChilds + "\r\n");
                        // localRatioCache.clear();
                        HashSet allChild = getAllChild(parentPath);
                        System.out.println("arrayList = " + allChild);
                        // for (String currentChild : currentChilds) {
                        // String path = parentPath + "/" + currentChild;
                        // List<String> children = zkClient.getChildren(path);
                        // // Stat stat = new Stat();
                        // // Object o = zkClient.readData(path, stat);
                        // // zkClient.
                        // // Object o = zkClient.readData(path);
                        // System.out.println("o = " + o.toString());
                        // localRatioCache.put(path, o);
                        // }
                    }
                });


            }
        });
        thread1.start();

        Thread.sleep(100);
        zkClient.createPersistent(path + ip1 + "/testChild", true);
        zkClient.writeData(path + ip1 + "/testChild", 0.01);
        zkClient.createPersistent(path + ip1 + "/testChild2", true);
        zkClient.writeData(path + ip1 + "/testChild2", 0.02);
        ZkClientTest.zkClient.writeData(path, 0.02);
        Thread.sleep(100);
        zkClient.createPersistent(path + ip1 + "/testChild3", true);
        zkClient.writeData(path + ip1 + "/testChild3", 0.03);

        zkClient.createPersistent(path + ip2 + "/testChild", true);
        zkClient.writeData(path + ip2 + "/testChild", 0.01);
        zkClient.createPersistent(path + ip2 + "/testChild2", true);
        zkClient.writeData(path + ip2 + "/testChild2", 0.02);
        zkClient.createPersistent(path + ip2 + "/testChild3", true);
        zkClient.writeData(path + ip2 + "/testChild3", 0.03);
        System.out.println("radio=" + radio);
        // zkClient.delete(path + "/testChild3");
        // zkClient.writeData(path + "/testChild3", 0.06);
        zkClient.writeData(path, 0.5);
        Thread.sleep(100);
        System.out.println("radio=" + radio);
        Thread.sleep(2000);

        // Iterator<Map.Entry<String, Object>> iterator = localRatioCache.entrySet().iterator();
        // while (iterator.hasNext()) {
        //     Map.Entry<String, Object> next = iterator.next();
        //     System.out.println("key=" + next.getKey() + ",value=" + next.getValue());
        // }
        Thread.sleep(Integer.MAX_VALUE);

    }

    public static HashSet getAllChild(String path) {

        List<String> children = zkClient.getChildren(path);
        if (children != null&& children.size()!=0) {
            for (String child : children) {
                getAllChild(path + "/" + child);
            }
        } else {
            hashSet.add(path);
        }
        return hashSet;

    }

    ;


}
