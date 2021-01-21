package com.lambda;

public class Lambda {
    //静态内部类
    static class XiaoMing2 implements LearnLambda {
        @Override
        public void likeLambda() {
            System.out.println("小明学习Lambda2");
        }
    }
    public static void main(String[] args) {
        LearnLambda xiaoMing = new XiaoMing();
        xiaoMing.likeLambda();

        xiaoMing = new XiaoMing2();
        xiaoMing.likeLambda();

        //局部内部类
        class XiaoMing3 implements LearnLambda {
            @Override
            public void likeLambda() {
                System.out.println("小明学习Lambda3");
            }
        }

        xiaoMing = new XiaoMing3();
        xiaoMing.likeLambda();

        //匿名内部类
        xiaoMing = new LearnLambda(){
            @Override
            public void likeLambda() {
                System.out.println("小明学习Lambda4");
            }
        };
        xiaoMing.likeLambda();

        xiaoMing =()->System.out.println("小明学习Lambda5");
        xiaoMing.likeLambda();

    }
}

//定义一个函数接口
interface LearnLambda {
    void likeLambda();
}

//实现函数接口
class XiaoMing implements LearnLambda {
    @Override
    public void likeLambda() {
        System.out.println("小明学习Lambda");
    }
}