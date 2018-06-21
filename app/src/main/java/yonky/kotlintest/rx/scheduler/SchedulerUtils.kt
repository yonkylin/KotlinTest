package yonky.kotlintest.rx.scheduler

/**
 * Created by Administrator on 2018/6/21.
 */

object SchedulerUtils {

    fun <T> ioToMain(): IoMainScheduler<T> {
        return IoMainScheduler()
    }
}
