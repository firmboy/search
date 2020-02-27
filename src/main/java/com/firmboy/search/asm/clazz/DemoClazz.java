package com.firmboy.search.asm.clazz;

import org.objectweb.asm.*;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.FileInputStream;

/**
 * @author playboy
 * @create 2020-02-26 9:26 上午
 * @description
 * @serviceLogic
 **/
public class DemoClazz {

    public static void main(String[] args) throws Exception {
        test1();
    }

    public static void test() throws Exception{
        FileInputStream fileInputStream = new FileInputStream("/Users/playboy/dev/code/czsb/search/target/classes/com/firmboy/search/asm/clazz/ByteCodeDemo.class");
        ClassReader classReader = new ClassReader(fileInputStream);
        ClassWriter cw = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);

        //Java8选择ASM5，
        ClassVisitor classVisitor = new ClassVisitor(Opcodes.ASM5, cw) {
            @Override
            public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
                System.out.println("field:" + name);
                return super.visitField(access, name, desc, signature, value);
            }

            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                System.out.println("方法" + name);
                return super.visitMethod(access, name, desc, signature, exceptions);
            }
        };
        //忽略调试信息
        classReader.accept(classVisitor, ClassReader.SKIP_DEBUG);
    }

    public static void test1() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("/Users/playboy/dev/code/czsb/search/target/classes/com/firmboy/search/asm/clazz/ByteCodeDemo.class");
        ClassReader classReader = new ClassReader(fileInputStream);
        ClassWriter cw = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
        //忽略调试信息
        ClassNode classNode = new ClassNode(org.objectweb.asm.Opcodes.ASM5);

        classReader.accept(classNode, ClassReader.SKIP_DEBUG);
        for (Object obj:classNode.methods) {
            MethodNode methodNode = (MethodNode)obj;
            System.out.println(methodNode.name);
        }
        classNode.accept(cw);
    }


}
