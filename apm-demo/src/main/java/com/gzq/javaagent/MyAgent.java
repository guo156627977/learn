package com.gzq.javaagent;

import javassist.*;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-03-13 11:26.
 */
public class MyAgent {
    public static void premain(String arg, Instrumentation instrumentation) {
        System.out.println("装载成功，方法premain的 参数" + arg);
        instrumentation.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain
                    protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

                String cname = "com.gzq.demo.test.UserServiceImpl";
                if (className.replaceAll("/", ".").equals(cname)) {
                    ClassPool pool = new ClassPool();
                    pool.insertClassPath(new LoaderClassPath(loader));
                    try {
                        CtClass ctClass = pool.get(cname);
                        CtMethod method = ctClass.getDeclaredMethod("getUser");
                        method.insertBefore(" System.out.println(System.currentTimeMillis());");

                        return ctClass.toBytecode();
                    } catch (NotFoundException e) {
                        e.printStackTrace();
                    } catch (CannotCompileException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }

                return null;
            }
        });

    }
}
