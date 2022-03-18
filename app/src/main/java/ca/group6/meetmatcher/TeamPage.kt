package ca.group6.meetmatcher

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import ca.group6.meetmatcher.databinding.FragmentTeamPageBinding

interface OnMakeMeetingButtonTapListener
{
    fun OnMakeMeetingButtonTapped(times: Array<String>)
}

interface OnEditAvailabilityButtonTapListener
{
    fun OnEditAvailabilityButtonTapped()
}

class TeamPage : Fragment() {
//    private var meeting: Meeting? = null
    private lateinit var meeting: Meeting
    private lateinit var caller_makeMeeting: OnMakeMeetingButtonTapListener
    private lateinit var caller_editAvailability: OnEditAvailabilityButtonTapListener
    private var _binding: FragmentTeamPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTeamPageBinding.inflate(inflater, container, false)
        return binding.root
        //return inflater.inflate(R.layout.activity_team_page, container, false).rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val groupName = "A Team"
        val listTimes = arrayOf("Feb. 15 19:00 - 20:00", "Feb. 20 17:00 - 18:00", "Feb. 21 9:00 - 10:00")
        arguments?.let {
            meeting = it.getParcelable<Meeting>("meetingTime")!!
            //meeting = it.getParcelable("meetingTime").toString()
            Log.i("meetingdets", meeting.toString())
        }
//        arguments?.let {
//            meeting = it.getString("meetingTime")
//        }
        displayMeetingDetails()
        setUpGenerateMeetingButton(groupName, listTimes)
    }

    override fun onAttach(context: Context)
    {
        super.onAttach(context)
        if (context is OnMakeMeetingButtonTapListener) {
            caller_makeMeeting = context
        }
        if (context is OnEditAvailabilityButtonTapListener) {
            caller_editAvailability = context
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(addMeeting: Meeting) =
        //fun newInstance(addMeeting: String) =
            TeamPage().apply {
                arguments = Bundle().apply {
                    //putString("meetingTime", addMeeting)
                    putParcelable("meetingTime", addMeeting)
                }
            }
    }

    private fun displayMeetingDetails() {
        if (!this::meeting.isInitialized) {
            val meeting: TextView = binding.teamPageMeetingDetails
            meeting.text = "No meeting planned."
        } else {
//            val details = meeting!!.date + "\n" + meeting!!.time
            if (this::meeting.isInitialized) {
                //var details = meeting!!.subSequence(0, 7)
                var details = meeting.date + ": " + meeting.time
                binding.teamPageMeetingDetails.text = details
            }
        }
    }

    private fun setUpGenerateMeetingButton(groupName : String, times : Array<String>) {
        binding.FABmeeting.setOnClickListener {
            Log.i("onclicksetup", "HJDKFJA;LFJISE;FA")
            val builder = activity.let { it1 -> AlertDialog.Builder(it1) }
            builder.setTitle("Use current availability?")

            builder.setPositiveButton("YES") { dialog, _ ->
                caller_makeMeeting.OnMakeMeetingButtonTapped(times)
            }
            builder.setNegativeButton("EDIT") { dialog, _ ->
                caller_editAvailability.OnEditAvailabilityButtonTapped()
            }

            builder.create().show()
            Log.i("FABCLICKED", "CLICKED----------")
        }
    }

//    private fun replaceFragment(fragment: Fragment) {
//        val transaction = fragmentManager?.beginTransaction()
//        transaction.replace(R.id.fragment_container, fragment)
//        transaction.commit()
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}