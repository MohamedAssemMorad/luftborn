package com.example.luftborntask

import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.*

class MainActivity : AppCompatActivity() {
    var service: ExecutorService? = null

    var taskOne: Runnable? = null
    var taskTwo: Runnable? = null
    var taskThree: Runnable? = null
    var taskFour: Runnable? = null

    var futureOne: Future<Any>? = null
    var futureTwo: Future<Any>? = null
    var futureThree: Future<Any>? = null
    var futureFour: Future<Any>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        service = Executors.newFixedThreadPool(2)

        val task1: TextView = findViewById(R.id.tvTask1)
        val task2: TextView = findViewById(R.id.tvTask2)
        val task3: TextView = findViewById(R.id.tvTask3)
        val task4: TextView = findViewById(R.id.tvTask4)

        val task1Des: TextView = findViewById(R.id.tvTask1Des)
        val task2Des: TextView = findViewById(R.id.tvTask2Des)
        val task3Des: TextView = findViewById(R.id.tvTask3Des)
        val task4Des: TextView = findViewById(R.id.tvTask4Des)

        val task1Finished: TextView = findViewById(R.id.tvTask1Done)
        val task2Finished: TextView = findViewById(R.id.tvTask2Done)
        val task3Finished: TextView = findViewById(R.id.tvTask3Done)
        val task4Finished: TextView = findViewById(R.id.tvTask4Done)

        val mHandler = Handler()

        task1.setOnClickListener {
            task1Des.text = getDate() + " " + task1.text
            futureOne = service?.submit(Callable {
                Thread.sleep(2)
                mHandler.post(Runnable {
                    runOnUiThread {
                        if (futureOne?.get() == null)
                            task1Finished.text = getDate() + " " + task1.text
                    }
                })
            })
        }

        task2.setOnClickListener {
            if (taskTwo == null) {
                task2Des.text = getDate() + " " + task2.text
                taskTwo = MyThread(task2.text.toString(), 1, 100)
                futureTwo = service?.submit(taskTwo) as Future<Any>?
            }
        }

        task3.setOnClickListener {
            if (taskThree == null) {
                task3Des.text = getDate() + " " + task3.text
                taskThree = MyThread(task3.text.toString(), 5, 200)
                service?.execute(taskThree)
                futureThree = service?.submit(taskThree) as Future<Any>?
            }
        }

        task4.setOnClickListener {
            if (taskFour == null) {
                task4Des.text = getDate() + " " + task4.text
                taskFour = MyThread(task4.text.toString(), 3, 100)
                service?.execute(taskFour)
                futureFour = service?.submit(taskFour) as Future<Any>?
            }
        }

    }

    fun getDate(): String {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        return sdf.format(Date())
    }

    override fun onDestroy() {
        super.onDestroy()
        service?.shutdown()
        service?.awaitTermination(1, TimeUnit.SECONDS);
    }
}