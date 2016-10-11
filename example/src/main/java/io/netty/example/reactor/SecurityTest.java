package io.netty.example.reactor;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;

/****************************************
 * Created by daipengfei on 16/10/11.****
 ***************************************/
public class SecurityTest {
    public static void main(String[] args) throws IOException {

        System.out.println("SecurityManager: " + System.getSecurityManager());

        FileInputStream fis = new FileInputStream("/Users/daipengfei/dump.rdb");

        AccessController.doPrivileged(new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                System.out.println(System.getProperty("file.encoding"));
                return null;
            }
        });

        fis.close();

    }
}
