package yonky.kotlintest.mvp.model.bean

import com.flyco.tablayout.listener.CustomTabEntity

/**
 * Created by Administrator on 2018/6/22.
 */
class TabEntity(var title: String, private var selectedIcon: Int, private var unSelectedIcon: Int) : CustomTabEntity {

    override fun getTabTitle(): String {
        return title
    }

    override fun getTabSelectedIcon(): Int {
        return selectedIcon
    }

    override fun getTabUnselectedIcon(): Int {
        return unSelectedIcon
    }
}