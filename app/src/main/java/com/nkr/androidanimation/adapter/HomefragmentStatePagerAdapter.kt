package com.nkr.androidanimation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class HomefragmentStatePagerAdapter(fm : FragmentManager ) : FragmentStatePagerAdapter(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){


    var listOfFragments = arrayListOf<Fragment>()

    override fun getItem(position: Int): Fragment {
        return listOfFragments[position]
    }

    override fun getCount(): Int {
       return listOfFragments.size
    }

}