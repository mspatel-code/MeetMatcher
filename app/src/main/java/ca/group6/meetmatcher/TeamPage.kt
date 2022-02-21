package ca.group6.meetmatcher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import ca.group6.meetmatcher.fragments.AvailabilityFragment
import ca.group6.meetmatcher.fragments.HomeFragment
import ca.group6.meetmatcher.fragments.ProfileFragment
import kotlinx.android.synthetic.main.activity_team_page.*
import kotlinx.android.synthetic.main.activity_toolbar.*
import kotlinx.android.synthetic.main.app_bar.*

class TeamPage : AppCompatActivity() {
    private val homeFragment = HomeFragment()
    private val availabilityFragment = AvailabilityFragment()
    private val profileFragment = ProfileFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        val groupName = "A Team"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_page)
        setUpToolbar(groupName)
        setUpGenerateMeetingButton(groupName)
    }

    private fun setUpGenerateMeetingButton(groupName : String) {
        FABmeeting.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Use current availability?")

            builder.setPositiveButton("YES") { dialog, _ ->

            }
            builder.setNegativeButton("EDIT") { dialog, _->
                replaceFragment(availabilityFragment)
            }

            val dialog : AlertDialog = builder.create()
            dialog.show()
        }
    }

    private fun setUpToolbar(groupName : String) {
        toolbar_title.text = groupName
        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> {
                    replaceFragment(homeFragment)
                    toolbar_title.text = getString(R.string.bottom_ic_home)
                }
                R.id.ic_availability -> {
                    replaceFragment(availabilityFragment)
                    toolbar_title.text = getString(R.string.bottom_ic_availability)
                }
                R.id.ic_profile -> {
                    replaceFragment(profileFragment)
                    toolbar_title.text = getString(R.string.bottom_ic_profile)
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }
}