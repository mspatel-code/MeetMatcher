package ca.group6.meetmatcher

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import ca.group6.meetmatcher.databinding.FragmentTeamPageBinding
import ca.group6.meetmatcher.model.Meeting

interface OnMakeMeetingButtonTapListener
{
    fun OnMakeMeetingButtonTapped(times: ArrayList<Meeting>)
}

interface OnEditAvailabilityButtonTapListener
{
    fun OnEditAvailabilityButtonTapped()
}

class TeamPage : Fragment() {
//    private var meeting: Meeting? = null
    private lateinit var meeting: ArrayList<Meeting>
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
        //val listTimes = arrayOf("Feb. 15 19:00 - 20:00", "Feb. 20 17:00 - 18:00", "Feb. 21 9:00 - 10:00")
        var meeting1 = Meeting("Feb. 15", "19:00 - 20:00", "")
        var meeting2 = Meeting("Feb. 20", "17:00 - 18:00", "")
        var meeting3 = Meeting("Feb. 21", "9:00 - 10:00", "")
        val listTimes = arrayListOf<Meeting>(meeting1, meeting2, meeting3)
        arguments?.let {
            meeting = it.getParcelableArrayList<Meeting>("meetingTime")!!
            //meeting = it.getParcelable("meetingTime").toString()
            Log.i("meetingdets", meeting.toString())
        }
//        arguments?.let {
//            meeting = it.getString("meetingTime")
//        }
        displayMeetingDetails()
        setUpGenerateMeetingButton(groupName, listTimes)
        setUpCancelButton()
        setUpAddLocationButton()
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
        fun newInstance(addMeeting: ArrayList<Meeting>) =
        //fun newInstance(addMeeting: String) =
            TeamPage().apply {
                arguments = Bundle().apply {
                    //putString("meetingTime", addMeeting)
                    putParcelableArrayList("meetingTime", addMeeting)
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
                if (meeting[0].date == "") {
                    binding.teamPageMeetingDetails.text = "No meeting planned."
                } else {
                    //var details = meeting!!.subSequence(0, 7)
                    val details = meeting[0].date + ": " + meeting[0].time
                    binding.teamPageMeetingDetails.text = details
                }
            }
        }
    }

    private fun setUpGenerateMeetingButton(groupName : String, times : ArrayList<Meeting>) {
        if (this::meeting.isInitialized) {
            if (meeting[0].date != "") {
                binding.FABmeeting.visibility = GONE
            }
        }
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
        }
    }

    private fun setUpCancelButton() {
        if (!this::meeting.isInitialized) {
            binding.FABcancel.visibility = GONE
        } else {
            binding.FABcancel.visibility = VISIBLE
            binding.FABcancel.setOnClickListener {
                meeting[0].date = ""
            }
        }
    }

    private fun setUpAddLocationButton() {
        if (!this::meeting.isInitialized) {
            binding.buttonAddLocation.visibility = GONE
        } else {
//            val details = meeting!!.date + "\n" + meeting!!.time
            if (this::meeting.isInitialized) {
                if (meeting[0].date == "") {
                    binding.buttonAddLocation.visibility = GONE
                } else {
                    binding.buttonAddLocation.visibility = VISIBLE
                    binding.buttonAddLocation.setOnClickListener {
                        Log.i("Location", "Add location button clicked")
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}