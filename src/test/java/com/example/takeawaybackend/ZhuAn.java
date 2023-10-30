//package com.example.takeawaybackend;
//
//class Animals{
//    public void eat1(){
//        System.out.println("几乎都是肉食动物");
//    }
//}
//class NiuMa extends Animals{
//    public void eat2(){
//        System.out.println("杂食动物");
//    }
//    public void sleep(){
//        System.out.println("都要睡觉。");
//    }
//}
//class A{
//    public void func1(Animals animals){
//        animals.eat1();
//        System.out.println("父类形参");
//    }
//    public void func3(NiuMa niuMa){
//        niuMa.sleep();
//        System.out.println("子类形参");
//    }
//
//    public NiuMa func2(){
//        System.out.println("此时返回值类型与public后的引用类型一致");
//        return new NiuMa();
//    }
//    public Animals func4(){
//        System.out.println("此时返回值类型与public后的引用类型不一致！");
//        return new NiuMa();
//    }
//
//    public Animals func5(){
//        System.out.println("此时返回值类型与public后的引用类型一致");
//        return new Animals();
//    }
//    public NiuMa func6(){
//        System.out.println("此时返回值类型与public后的引用类型不一致！");
//        return (NiuMa)new Animals();
//    }
//
//}
//public class ZhuAn {
//    public static void main4(String[] args) {//主要为向下转型。
//        //返回值类型法向下转型：
//
//        //利用func5():public后面为父类，返回值类型为父类。
//        A a1 = new A();
//        //用引用类型直接接收：
//        NiuMa niuma1 = (NiuMa) a1.func5();//发生了向下转型
//        Animals animals1 = a1.func5();//正常接收
//        //new对象进行接收：
//        NiuMa niuma2 = new NiuMa();
//        Animals animals2 = new Animals();
//        niuma2 = (NiuMa) a1.func5();//发生了向下转型
//        animals2 = a1.func5();//正常接收
//
//        //利用func6():public后面为子类，返回值类型为父类。
//        A a2 = new A();
//        //用引用类型直接接收：
//        NiuMa niuma3 = a2.func6();//正常接收
//        Animals animals3 = a2.func6();//相当于向上转型
//        //new对象进行接收：
//        NiuMa niuma4 = new NiuMa();
//        Animals animals4 = new Animals();
//        niuma4 = a2.func6(); //正常接收
//        animals4 = a2.func6();//相当于向上转型
//
//        //小结：当public后面为子类，返回值类型为父类的时候，返回值类型为子类。
//    }
//    public static void main3(String[] args) {//主要为向上转型：
//        //返回值类型法向上转型：
//
//        //利用func2():public后面为子类，返回值类型为子类
//        A a1 = new A();
//        //用引用类型直接接收：
//        NiuMa niuma1 = a1.func2();//正常接收。
//        Animals animals1 = a1.func2();//发生了向上转型
//        //new对象进行接收：
//        NiuMa niuma2 = new NiuMa();
//        Animals animals2 = new Animals();
//        niuma2 = a1.func2(); //正常接收
//        animals2 = a1.func2();//发生了向上转型
//
//        //利用finc4():public后面为父类，返回值类型为子类
//        A a2 = new A();
//        //用引用类型直接接收：
//        NiuMa niuma3 = (NiuMa) a2.func4();//相当于发生了一次向下转型
//        Animals animals3 = a2.func4();//正常接收。
//        //new对象进行接收：
//        NiuMa niuma4 = new NiuMa();
//        Animals animals4 = new Animals();
//        niuma4 = (NiuMa) a2.func4(); //相当于又发生了一次向下转型。
//        animals4 = a2.func4();//正常接收
//
//        //结论：当public后面为父类，返回值为子类时，实际返回值为父类类型。
//
//    }
//
//    public static void main2(String[] args) {
//        //传参法：在方法内实现向上转型，在方法内调用方法。
//
//        //向上转型：
//        NiuMa niuma1 = new NiuMa();
//        A a1 = new A();
//        a1.func1(niuma1);
//
//        //向下转型：
//        Animals animals1 = new NiuMa();//先发生强制类型转换
//        A a2 = new A();
//        a2.func3((NiuMa) animals1);
//
//        //⭐错误代码：
//        //Animals people = new Animals();
//        //A a = new A();
//        //a.func3(people);
//        //形参只能向下兼容，不能向上兼容。向下兼容的时候传入子类则会发生向上转型。
//    }
//
//    public static void main1(String[] args) {
//        //直接赋值法
//
//        //向上转型
//        Animals people1 = new NiuMa();
//        people1.eat1();//调用继承后公共部分的方法，没重写调用没重写的，重写了调用重写后的。
//
//        //向下转型
//        Animals people2 = new NiuMa();//要先发生一次向上转型
//        NiuMa people3 = (NiuMa) people2; //将people2强制类型转换成NiuMa类，用people3接收
//        people1.eat1();
//        people3.sleep();
//    }
//}