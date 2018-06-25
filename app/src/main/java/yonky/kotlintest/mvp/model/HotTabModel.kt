package yonky.kotlintest.mvp.model

import io.reactivex.Observable
import yonky.kotlintest.mvp.model.bean.TabInfoBean
import yonky.kotlintest.net.RetrofitManager
import yonky.kotlintest.rx.scheduler.SchedulerUtils

/**
 * Created by Administrator on 2018/6/25.
 */
class HotTabModel {

    /**
     * 获取 TabInfo
     */
    fun getTabInfo(): Observable<TabInfoBean> {

        return RetrofitManager.service.getRankList()
                .compose(SchedulerUtils.ioToMain())
    }

}