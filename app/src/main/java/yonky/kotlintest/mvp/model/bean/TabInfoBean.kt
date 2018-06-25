package yonky.kotlintest.mvp.model.bean

/**
 * Created by Administrator on 2018/6/25.
 */
data class TabInfoBean(val tabInfo: TabInfo) {
    data class TabInfo(val tabList: ArrayList<Tab>)

    data class Tab(val id: Long, val name: String, val apiUrl: String)
}
