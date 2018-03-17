package com.gzq.demo.test;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-03-14 13:24.
 */
public class JavassistTest {

    @Test
    public void updateMethod() throws Exception{
        ClassPool pool = new ClassPool();
        pool.appendSystemPath();
        //得到插入目标class
        CtClass ctClass = pool.get("com.gzq.demo.test.UserServiceImpl");
        //得到目标方法
        CtMethod method = ctClass.getDeclaredMethod("getUser");

        //插入代码块
        method.insertBefore("System.out.println(\"abc\");");

        //添加属性
        CtField field = new CtField(pool.get(String.class.getName()), "abc", ctClass);
        ctClass.addField(field);

        File file = new File(System.getProperty("user.dir") + "/target/UserServiceImpl.class");
        file.createNewFile();
        Files.write(file.toPath(), ctClass.toBytecode());
    }

    @Test
    public void update() throws Exception {
        ClassPool pool = new ClassPool();
        pool.appendSystemPath();
        CtClass ctClass = pool.get("com.gzq.demo.test.UserServiceImpl");
        CtMethod method = ctClass.getDeclaredMethod("addUser");

        method.insertBefore("System.out.println($0);");
        method.insertBefore("System.out.println($1);");
        method.insertBefore("System.out.println($2);");
        method.insertBefore("addUser2($$);");
        ctClass.toClass();
        new UserServiceImpl().addUser("gzq","man");

    }

}
