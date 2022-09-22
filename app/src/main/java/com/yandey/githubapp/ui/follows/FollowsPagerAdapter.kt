package com.yandey.githubapp.ui.follows

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yandey.githubapp.ui.detail.DetailFragment.Companion.TAB_TITLES_FOLLOWS
import com.yandey.githubapp.ui.follows.followers.FollowersFragment
import com.yandey.githubapp.ui.follows.following.FollowingFragment

class FollowsPagerAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = TAB_TITLES_FOLLOWS.size

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        return fragment as Fragment
    }
}