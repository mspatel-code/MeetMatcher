package ca.group6.meetmatcher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import ca.group6.meetmatcher.databinding.ActivityToolbarBinding
import ca.group6.meetmatcher.dialogs.ChooseLocationCityDialog
import ca.group6.meetmatcher.dialogs.SelectedCity
import ca.group6.meetmatcher.fragments.*
import ca.group6.meetmatcher.model.LocationPlace
import ca.group6.meetmatcher.model.Meeting

class ToolbarActivity : AppCompatActivity(), OnMakeMeetingButtonTapListener, OnEditAvailabilityButtonTapListener, OnFinishSelectTimeListener,
    SelectedCity, OnAddLocationButtonTapListener, OnAddLocationPlaceButtonTapListener,
    OnPlaceDoneButtonListener, OnCancelMeetingButtonTapListener {
    private lateinit var binding: ActivityToolbarBinding
    private val homeFragment = HomeFragment()
    private val availabilityFragment = AvalFragment()
    private val profileFragment = ProfileFragment()
    //private val selectTimeFragment = SelectTime()
    private lateinit var teamPage: TeamPage
    private lateinit var chooseLocationPlace: ChooseLocationPlace

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

    override fun OnFinishSelectTimeTapped(confirmMeetng: ArrayList<Meeting>) {
        // get first time for now
        teamPage = TeamPage.newInstance(confirmMeetng)
        replaceFragment(teamPage, "Team Page")
    }

    override fun onSelectedCity(city: String) {
        if (this::teamPage.isInitialized) {
            teamPage.updateLocationTextView(city)
        }
    }

    override fun OnAddLocationButtonTapped() {
        ChooseLocationCityDialog().show(supportFragmentManager, "ChooseCityFragment")
    }

    override fun OnAddLocationPlaceButtonTapped(city: String) {
        chooseLocationPlace = ChooseLocationPlace.newInstance(city)
        Log.i("LocationPlace", "clicked")
        replaceFragment(chooseLocationPlace, "Pick Location")
    }

    override fun OnPlaceDoneButtonTapped(places: ArrayList<LocationPlace>, city: String) {
        if (this::teamPage.isInitialized) {
            supportFragmentManager.popBackStack()
            replaceFragment(teamPage,"Team Page")
            teamPage.updateMeetingPlace(places)
        }
    }

    override fun OnCancelMeetingButtonTapped() {
        supportFragmentManager.popBackStack()
        teamPage = TeamPage()
        replaceFragment(teamPage, "Team Page")
    }
}
