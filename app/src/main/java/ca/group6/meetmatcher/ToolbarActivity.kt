package ca.group6.meetmatcher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import ca.group6.meetmatcher.databinding.ActivityToolbarBinding
import ca.group6.meetmatcher.fragments.*

class ToolbarActivity : AppCompatActivity(), OnMakeMeetingButtonTapListener, OnEditAvailabilityButtonTapListener, OnFinishSelectTimeListener {
    private lateinit var binding: ActivityToolbarBinding
    private val homeFragment = HomeFragment()
    private val availabilityFragment = AvailabilityFragment()
    private val profileFragment = ProfileFragment()
    //private val selectTimeFragment = SelectTime()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityToolbarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(homeFragment, "homeTag")

       binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> {
                    replaceFragment(homeFragment, "homeTag")
                    binding.toolbarTitle.text = getString(R.string.bottom_ic_home)
                }
                R.id.ic_availability -> {
                    replaceFragment(availabilityFragment, "availabilityTag")
                    binding.toolbarTitle.text = getString(R.string.bottom_ic_availability)
                }
                R.id.ic_profile -> {
                    replaceFragment(profileFragment, "profileTag")
                    binding.toolbarTitle.text = getString(R.string.bottom_ic_profile)
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment, tag: String) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment, tag)
        transaction.commit()
    }

    override fun OnMakeMeetingButtonTapped(times: Array<String>) {
        replaceFragment(SelectTime.newInstance(times), "selectTimeTag")
    }

    override fun OnEditAvailabilityButtonTapped() {
        replaceFragment(availabilityFragment, "availabilityTag")
    }

    //override fun OnFinishSelectTimeTapped(times: Array<String>?) {
    override fun OnFinishSelectTimeTapped(meeting: Meeting) {
        // get first time for now
//        var time: String = times[0]
//        var meeting: Meeting = Meeting(time,"")

        replaceFragment(TeamPage.newInstance(meeting), "teamPageTag")
        //replaceFragment(TeamPage.newInstance(times[0]), "teamPageTag")
    }
}