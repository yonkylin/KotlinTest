package yonky.kotlintest.view.recyclerview

/**
 * Created by Administrator on 2018/6/22.
 */
interface MultipleType<in T> {
    fun getLayoutId(item: T, position: Int): Int
}