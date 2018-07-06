package br.com.nglauber.tdcapp.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import br.com.nglauber.tdcapp.repository.remote.model.TdcModality
import br.com.nglauber.tdcapp.ui.fragment.ModalityListFragment

class ModalitiesPagerAdapter(
        fm: FragmentManager,
        private val eventId: Int,
        private val modalitiesByDay: Map<String, List<TdcModality>>
) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        val key = modalitiesByDay.keys.toList()[position]
        val sessions = modalitiesByDay[key] ?: emptyList()
        return ModalityListFragment().apply {
            setSessionList(eventId, sessions)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return modalitiesByDay.keys.toList()[position]
    }

    override fun getCount(): Int {
        return modalitiesByDay.count()
    }
}