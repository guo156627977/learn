package com.gzq;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-08-08 16:56.
 */
public class Address {

    public static void main(String[] args) throws UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();
        String hostName = addr.getHostName();
        System.out.println("hostName = " + hostName);
        String hostAddress = addr.getHostAddress();
        System.out.println("hostAddress = " + hostAddress);
    }
}
