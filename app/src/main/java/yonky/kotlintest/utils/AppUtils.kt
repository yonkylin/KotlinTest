package yonky.kotlintest.utils

import android.content.Context
import android.content.pm.PackageManager

/**
 * Created by Administrator on 2018/6/21.
 */
class AppUtils private constructor(){
    init {
        throw Error("Do not need instantiate")
    }
    companion object {
        private val DEBUG =true
        private val TAG="AppUtils"

        /**
         * 得到软件版本号
         *
         * @param context 上下文
         * @return 当前版本Code
         */
        fun getVerCode(context: Context):Int{
            var verCode =-1
            try{
                val packageName=context.packageName
                verCode=context.packageManager
                        .getPackageInfo(packageName,0).versionCode
            }catch(e:PackageManager.NameNotFoundException){
                e.printStackTrace()
            }
            return verCode
        }

        /**
         * 获取应用运行的最大内存
         *
         * @return 最大内存
         */
        val maxMemory: Long
            get() = Runtime.getRuntime().maxMemory() / 1024


        /**
         * 得到软件显示版本信息
         *
         * @param context 上下文
         * @return 当前版本信息
         */
        fun getVerName(context:Context):String{
            var verName = ""
            try{
                val packageName =context.packageName
                verName=context.packageManager.getPackageInfo(packageName,0).versionName
            }catch (e:PackageManager.NameNotFoundException){
                e.printStackTrace()
            }
            return  verName
        }

    }
}