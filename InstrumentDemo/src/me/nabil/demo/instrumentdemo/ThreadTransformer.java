package me.nabil.demo.instrumentdemo;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * TODO:类描述
 *
 * @author yuanshan
 * @date 2019/1/4
 */
public class ThreadTransformer implements ClassFileTransformer {

    private String targetClassName;
    private ClassLoader targetClassLoader;

    public ThreadTransformer(String name, ClassLoader classLoader) {
        this.targetClassLoader = classLoader;
        this.targetClassName = name;
    }

    @Override
    public byte[] transform(
            ClassLoader loader,
            String className,
            Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain,
            byte[] classfileBuffer) {
        byte[] byteCode = classfileBuffer;
        String finalTargetClassName = this.targetClassName
                .replaceAll("\\.", "/");
        if (!className.equals(finalTargetClassName)) {
            return byteCode;
        }

        System.out.println("[Agent] Transforming class Thread");
        try {
            ClassPool cp = ClassPool.getDefault();
            CtClass cc = cp.get(targetClassName);
            CtMethod m = cc.getDeclaredMethod(
                    "start");
            m.addLocalVariable(
                    "startTime", CtClass.longType);
            m.insertBefore(
                    "startTime = System.currentTimeMillis();");

            StringBuilder endBlock = new StringBuilder();

            m.addLocalVariable("endTime", CtClass.longType);
            m.addLocalVariable("opTime", CtClass.longType);
            endBlock.append(
                    "endTime = System.currentTimeMillis();");
            endBlock.append(
                    "opTime = (endTime-startTime)/1000;");
            endBlock.append(
                    "System.out.println(\"[Application] Withdrawal operation completed in:" +
                            "\" + opTime + \" seconds!\");");
//
//            endBlock.append(
//                    "System.out.println(java.util.stream.Stream.of(this.getStackTrace()).map(StackTraceElement::toString).collect(java.util.stream.Collectors.toList()).toString());");
            endBlock.append(
                    "System.out.println(this.getStackTrace()[0]);");

//            System.out.println(java.util.stream.Stream.of(this.getStackTrace()).map(StackTraceElement::toString).collect(java.util.stream.Collectors.toList()).toString());
            m.insertAfter(endBlock.toString());

            byteCode = cc.toBytecode();
            cc.detach();
        } catch (NotFoundException | CannotCompileException | IOException e) {
            System.out.println("Exception" + e.getLocalizedMessage());
        }

        return byteCode;
    }
}
