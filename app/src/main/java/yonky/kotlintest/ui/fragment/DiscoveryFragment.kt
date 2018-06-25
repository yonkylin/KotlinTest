package yonky.kotlintest.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import kotlinx.android.synthetic.main.fragment_hot.*
import yonky.kotlintest.R
import yonky.kotlintest.base.BaseFragment
import yonky.kotlintest.base.BaseFragmentAdapter
import yonky.kotlintest.utils.StatusBarUtil
import yonky.kotlintest.view.TabLayoutHelper

/**
 * Created by Administrator on 2018/6/25.
 */
class DiscoveryFragment : BaseFragment(){
    private val tabList =ArrayList<String>()
    private val fragments =ArrayList<Fragment>()
    private var mTitle: String?=null

    companion object {
        fun getInstance(title:String):DiscoveryFragment{
            val fragment =DiscoveryFragment()
            val bundle =Bundle()
            fragment.arguments =bundle
            fragment.mTitle =title
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_hot

    override fun initView() {

        //状态栏透明和间距处理
        StatusBarUtil.darkMode(activity)
        StatusBarUtil.setPaddingSmart(activity, toolbar)

        tv_header_title.text = mTitle

        tabList.add("关注")
        tabList.add("分类")
        fragments.add(FollowFragment.getInstance("关注"))
        fragments.add(CategoryFragment.getInstance("分类"))

        /**
         * getSupportFragmentManager() 替换为getChildFragmentManager()
         */
        mViewPager.adapter = BaseFragmentAdapter(childFragmentManager, fragments, tabList)
        mTabLayout.setupWithViewPager(mViewPager)
        TabLayoutHelper.setUpIndicatorWidth(mTabLayout)


    }
    override fun lazyLoad() {
    }
}