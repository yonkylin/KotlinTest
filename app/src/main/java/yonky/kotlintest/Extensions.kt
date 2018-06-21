package yonky.kotlintest

import android.content.Context
import android.widget.Toast

/**
 * Created by Administrator on 2018/6/21.
 */
fun Context.showToast(content: String): Toast {
    val toast = Toast.makeText(MyApplication.context, content, Toast.LENGTH_SHORT)
    toast.show()
    return toast
}