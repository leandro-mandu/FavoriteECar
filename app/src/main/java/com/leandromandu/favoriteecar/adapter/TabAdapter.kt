package com.leandromandu.favoriteecar.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.leandromandu.favoriteecar.fragments.CarFragment
import com.leandromandu.favoriteecar.fragments.FavoritesFragment

class TabAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> CarFragment()
            1 -> FavoritesFragment()
            else -> FavoritesFragment()
        }

    }

    override fun getItemCount(): Int {
        return 2;
    }
}