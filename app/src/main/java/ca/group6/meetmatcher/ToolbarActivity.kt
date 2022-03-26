package ca.group6.meetmatcher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import ca.group6.meetmatcher.databinding.ActivityToolbarBinding
import ca.group6.meetmatcher.dialogs.ChooseLocationCityDialog
import ca.group6.meetmatcher.dialogs.SelectedCity
import ca.group6.meetmatcher.fragments.*
import ca.group6.meetmatcher.model.Meeting

class ToolbarActivity : AppCompatActivity(), OnMakeMeetingButtonTapListener, OnEditAvailabilityButtonTapListener, OnFinishSelectTimeListener,
    SelectedCity, OnAddLocationButtonTapListener {
    private lateinit var binding: ActivityToolbarBinding
    private val homeFragment = HomeFragment()
    private val availabilityFragment = AvalFragment()
    private val profileFragment = ProfileFragment()
    //private val selectTimeFragment = SelectTime()
    private lateinit var teamPage: TeamPage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityToolbarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarLayout.backButton.setOnClickListener {
            val last = supportFragmentManager.backStackEntryCount-1
            val tag = supportFragmentManager.getBackStackEntryAt(last).name
            val fragment = supportFragmentManager.findFragmentByTag(tag)
            if (fragment != null && tag != null) {
                replaceFragment(fragment, tag)
            }
        }

        replaceFragment(homeFragment, getString(R.string.bottom_ic_home))
        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> {
                    replaceFragment(homeFragment, getString(R.string.bottom_ic_home))
                }
                R.id.ic_availability -> {
                    replaceFragment(availabilityFragment, getString(R.string.bottom_ic_availability))
                }
                R.id.ic_profile -> {
                    replaceFragment(profileFragment, getString(R.string.bottom_ic_profile))
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment, title: String) {
        val tran = supportFragmentManager
        tran.beginTransaction()
            .replace(R.id.fragment_container, fragment, title)
            .addToBackStack(title)
            .commit()
        tran.executePendingTransactions()
        binding.toolbarLayout.toolbarTitle.text = title
    }

    override fun OnMakeMeetingButtonTapped(times: ArrayList<Meeting>) {
        replaceFragment(SelectTime.newInstance(times), "Select Time")
    }

    override fun OnEditAvailabilityButtonTapped() {
        replaceFragment(availabilityFragment, "Availability")
    }

    //override fun OnFinishSelectTimeTapped(times: Array<String>?) {
    override fun OnFinishSelectTimeTapped(confirmMeetng: ArrayList<Meeting>) {
        // get first time for now
//        var time: String = times[0]
//        var meeting: Meeting = Meeting(time,"")
        teamPage = TeamPage.newInstance(confirmMeetng)
        replaceFragment(teamPage, "Team Page")
        //replaceFragment(TeamPage.newInstance(times[0]), "teamPageTag")
        //replaceFragment(TeamPage.newInstance("Feb. 15 19:00 - 20:00"), "Team Page")
    }

    override fun onSelectedCity(city: String) {
        if (this::teamPage.isInitialized) {
            Log.i("DIALOGtoolbar", "$city WAS SELECTED----------")
            teamPage.city = city
            teamPage.updateLocationTextView()
        }
    }

    override fun OnAddLocationButtonTapped() {
        ChooseLocationCityDialog().show(supportFragmentManager, "ChooseCityFragment")
    }
}
