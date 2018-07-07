package br.com.nglauber.tdcapp.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import br.com.nglauber.tdcapp.repository.remote.model.TdcModality
import br.com.nglauber.tdcapp.ui.fragment.ModalityListFragment

class ModalitiesPagerAdapter(
        fm: FragmentManager,
        private val eventId: Int,
        private val modalitiesDates: List<String>
) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        val date = modalitiesDates[position]
        return ModalityListFragment.newInstance(eventId, date)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return modalitiesDates[position]
    }

    override fun getCount(): Int {
        return modalitiesDates.count()
    }
}