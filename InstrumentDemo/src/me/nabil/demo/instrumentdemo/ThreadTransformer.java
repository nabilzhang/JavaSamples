package me.nabil.demo.instrumentdemo;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.NotFoundException;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

/**
 * ThreadTransformer
 *
 * @author nabil
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

        System.out.println("[Agent] Transforming class " + className);
        try {
            ClassPool cp = ClassPool.getDefault();
            CtClass cc = cp.get(targetClassName);
            CtConstructor[] constructors = cc.getDeclaredConstructors();
            for (CtConstructor c : constructors) {
//                System.out.println(c.getLongName());
                StringBuilder endBlock = new StringBuilder();
                endBlock.append("Thread current = currentThread();");
                endBlock.append("StringBuilder stack = new StringBuilder();\n");
                endBlock.append("for(int i = current.getStackTrace().length-1; i>=0; i--) {");
                endBlock.append("   stack.append(current.getStackTrace()[i] + \" | \");\n");
                endBlock.append("}\n");

                endBlock.append(
                        "System.out.println(\"[THREAD-LOG] [\" + this.name + \"] " +
                                "created by [\" + current.getName() + \"] \"" +
                                "+ \"stack [\" + stack + \"]\");");
//                System.out.println("[Agent] Transforming class content:\n" + endBlock);
                c.insertAfter(endBlock.toString());
            }
            cc.writeFile("D:/data/tmp");
            byteCode = cc.toBytecode();
            cc.detach();
        } catch (NotFoundException | CannotCompileException | IOException e) {
            e.printStackTrace();
            System.out.println("Exception" + e.getMessage());
        }

        return byteCode;
    }
}
