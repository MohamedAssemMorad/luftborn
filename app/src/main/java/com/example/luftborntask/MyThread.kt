package com.example.luftborntask

import android.os.Handler

class MyThread internal constructor(
    private val myName: String,
    private val count: Int,
    private val timeSleep: Long
) :
    Runnable {
    val mHandler = Handler()
    override fun run() {
        // TODO Auto-generated method stub
        var sum = 0
        for (i in 1..count) {
            sum += i
        }
        println(
            myName + " thread has sum = " + sum +
                    " and is going to sleep for " + timeSleep
        )
        try {
            Thread.sleep(timeSleep)
        } catch (e: InterruptedException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        mHandler.post {
            Runnable {
                run {
                    print("Finished succ")
                }
            }

        }
    }

}