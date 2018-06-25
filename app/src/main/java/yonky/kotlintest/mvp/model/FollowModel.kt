package yonky.kotlintest.mvp.model

import io.reactivex.Observable
import yonky.kotlintest.mvp.model.bean.HomeBean
import yonky.kotlintest.net.RetrofitManager
import yonky.kotlintest.rx.scheduler.SchedulerUtils

/**
 * Created by Administrator on 2018/6/25.
 */
class FollowModel {

    /**
     * 获取关注信息
     */
    fun requestFollowList(): Observable<HomeBean.Issue> {

        return RetrofitManager.service.getFollowInfo()
                .compose(SchedulerUtils.ioToMain())
    }

    /**
     * 加载更多
     */
    fun loadMoreData(url:String):Observable<HomeBean.Issue>{
        return RetrofitManager.service.getIssueData(url)
                .compose(SchedulerUtils.ioToMain())
    }


}