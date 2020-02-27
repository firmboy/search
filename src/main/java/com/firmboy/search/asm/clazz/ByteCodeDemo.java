package com.firmboy.search.asm.clazz;

/**
 * @author playboy
 * @create 2020-02-26 9:52 上午
 * @description
 * @serviceLogic
 **/
public class ByteCodeDemo {

        private static final String name = "xiaoming";

        private int age;

        public ByteCodeDemo(int age) {
            this.age = age;
        }

        public int getAge() {
            return age;
        }

        public static void main(String[] args) {
            ByteCodeDemo byteCodeDeomo = new ByteCodeDemo(12);
            System.out.println("name:" + name + "age:" + byteCodeDeomo.getAge());
        }
}
