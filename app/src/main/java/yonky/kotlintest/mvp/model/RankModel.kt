package yonky.kotlintest.mvp.model

import io.reactivex.Observable
import yonky.kotlintest.mvp.model.bean.HomeBean
import yonky.kotlintest.net.RetrofitManager
import yonky.kotlintest.rx.scheduler.SchedulerUtils

/**
 * Created by Administrator on 2018/6/25.
 */
class RankModel {

    /**
     * 获取排行榜
     */
    fun requestRankList(apiUrl:String): Observable<HomeBean.Issue> {

        return RetrofitManager.service.getIssueData(apiUrl)
                .compose(SchedulerUtils.ioToMain())
    }

}
