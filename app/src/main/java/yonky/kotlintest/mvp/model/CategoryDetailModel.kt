package yonky.kotlintest.mvp.model

import io.reactivex.Observable
import yonky.kotlintest.mvp.model.bean.HomeBean
import yonky.kotlintest.net.RetrofitManager
import yonky.kotlintest.rx.scheduler.SchedulerUtils

/**
 * Created by Administrator on 2018/6/25.
 */
class CategoryDetailModel {

    /**
     * 获取分类下的 List 数据
     */
    fun getCategoryDetailList(id: Long): Observable<HomeBean.Issue> {
        return RetrofitManager.service.getCategoryDetailList(id)
                .compose(SchedulerUtils.ioToMain())
    }

    /**
     * 加载更多数据
     */
    fun loadMoreData(url: String): Observable<HomeBean.Issue> {
        return RetrofitManager.service.getIssueData(url)
                .compose(SchedulerUtils.ioToMain())
    }
}