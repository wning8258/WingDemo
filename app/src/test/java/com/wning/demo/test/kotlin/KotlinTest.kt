package com.wning.demo.test.kotlin

/**
 * 定义一个class KotlinTest（含有两个成员变量，int型的liveType，可空String的roomName）
 *当我们将一个类标记为一个数据类（data class）时，您不必像在Java中那样实现或创建以下函数。
    hashCode()
    equals()
    toString()
    copy()
    编译器会自动在内部创建这些代码，因此也会产生干净的代码。
    虽然，数据类需要满足的需求很少:
    主构造函数需要至少有一个参数。
    所有主构造函数参数需要标记为val或var
    数据类不能是抽象的、开放的、密封的或内部的。
    因此，当您遇到这些情况时，请使用data class。
 */
data class KotlinTest1 (val liveType : Int , var roomName : String?){
    override fun toString(): String {
        return super.toString()
    }
}

data class KotlinTest2 (val liveType : Int){
}



class KotlinTest {


    fun test() {
        println("hello kotlin")
        val kotlinTest1 = KotlinTest1(1, null)
        KotlinTest1(1, "hello")

        println("kotlinTest 1 toString :" + kotlinTest1.toString())
        println("kotlinTest 1 toString :$kotlinTest1")  //等同于上一句

        val kotlinTest1Copy = kotlinTest1.copy();
        //kotlinTest1Copy.liveType = 2;  //编译报错 val不能重新赋值
        kotlinTest1Copy.roomName = "hahaha"
        println("kotlinTest 1 toString : " + kotlinTest1Copy)

        val str1: String = "str0"
        println("str1 length :" + str1.length)

        val str2: String? = "str0"
        //print("str2 length :" + str2?.length)  //编译报错，因为str2可能为空
        println("str2 length :" + str2?.length)


        val sum: (x: Int, y: Int) -> Int = { x, y -> x + y }  //—>指定返回值为int型
        println("sum : ${sum(1, 2)}");


        println("foo3 result :${foo3(1, 2, this::foo2)}");


    }

    // 表达式函数体
    fun foo2(x: Int, y: Int) = x + y

    // block 的类型就是函数类型(调用方法aaa方法,aaa 需要两个int参数，返回一个int)
    fun foo3(x: Int, y: Int, aaa: (Int, Int) -> Int): Int {
        return aaa.invoke(x, y)
    }
}

fun  main () {
    val kotlinTest = KotlinTest()
    kotlinTest.test()
}