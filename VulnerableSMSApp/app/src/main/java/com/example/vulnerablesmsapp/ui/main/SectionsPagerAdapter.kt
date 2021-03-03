package com.example.vulnerablesmsapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(fm: AppCompatActivity) : FragmentStateAdapter(fm) {

    /**
     * Get the number of fragments.
     * @return Int
     */
    override fun getItemCount(): Int {
        return 2
    }


    /**
     * Creating the fragment depending on the position.
     * @param position Int
     * @return Fragment
     */
    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment = MessagesFragment.newInstance()
        when (position) {
            0 -> {
                fragment = MessagesFragment.newInstance()
            }
            1 -> {
                fragment = CreateContactFragment.newInstance()
            }
        }
        return fragment
    }
}