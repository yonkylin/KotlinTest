package yonky.kotlintest.mvp.model

import io.reactivex.Observable
import yonky.kotlintest.mvp.model.bean.CategoryBean
import yonky.kotlintest.net.RetrofitManager
import yonky.kotlintest.rx.scheduler.SchedulerUtils

/**
 * Created by Administrator on 2018/6/25.
 */
class CategoryModel {


    /**
     * 获取分类信息
     */
    fun getCategoryData(): Observable<ArrayList<CategoryBean>> {
        return RetrofitManager.service.getCategory()
                .compose(SchedulerUtils.ioToMain())
    }
}