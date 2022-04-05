package ca.group6.meetmatcher

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import ca.group6.meetmatcher.databinding.FragmentTeamPageBinding
import ca.group6.meetmatcher.dialogs.SelectedCity
import ca.group6.meetmatcher.model.LocationPlace
import ca.group6.meetmatcher.model.Meeting
import org.w3c.dom.Text
import kotlin.random.Random

interface OnMakeMeetingButtonTapListener
{
    fun OnMakeMeetingButtonTapped(times: ArrayList<Meeting>)
}

interface OnCancelMeetingButtonTapListener
{
    fun OnCancelMeetingButtonTapped()
}

interface OnEditAvailabilityButtonTapListener
{
    fun OnEditAvailabilityButtonTapped()
}

interface OnAddLocationButtonTapListener
{
    fun OnAddLocationButtonTapped()
}

interface OnAddLocationPlaceButtonTapListener
{
    fun OnAddLocationPlaceButtonTapped(city: String)
}

class TeamPage : Fragment(), SelectedCity {
    private lateinit var meeting: Meeting
    private lateinit var meetings: ArrayList<Meeting>
    private lateinit var caller_makeMeeting: OnMakeMeetingButtonTapListener
    private lateinit var caller_cancelMeeting: OnCancelMeetingButtonTapListener
    private lateinit var caller_editAvailability: OnEditAvailabilityButtonTapListener
    private lateinit var caller_addLocation: OnAddLocationButtonTapListener
    private lateinit var caller_addLocationPlace: OnAddLocationPlaceButtonTapListener
    private var _binding: FragmentTeamPageBinding? = null
    private val binding get() = _binding!!

    private lateinit var city: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeamPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val groupName  = "Team A"
        binding.root.rootView.findViewById<TextView>(R.id.toolbar_title).text = groupName

        val meeting1 = Meeting("Feb. 15", "19:00 - 20:00", "")
        val meeting2 = Meeting("Feb. 20", "17:00 - 18:00", "")
        val meeting3 = Meeting("Feb. 21", "9:00 - 10:00", "")
        val listTimes = arrayListOf<Meeting>(meeting1, meeting2, meeting3)
        arguments?.let {
            meetings = it.getParcelableArrayList<Meeting>("meetingTime")!!
            Log.i("meetingdets", meetings.toString())
        }
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
        if (context is OnCancelMeetingButtonTapListener) {
            caller_cancelMeeting = context
        }
        if (context is OnEditAvailabilityButtonTapListener) {
            caller_editAvailability = context
        }
        if (context is OnAddLocationButtonTapListener) {
            caller_addLocation = context
        }
        if (context is OnAddLocationPlaceButtonTapListener) {
            caller_addLocationPlace = context
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(addMeeting: ArrayList<Meeting>) =
            TeamPage().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList("meetingTime", addMeeting)
                }
            }
    }

    fun updateLocationTextView(chosenCity: String) {
        binding.cityChoice.text = chosenCity
        city = chosenCity
        if (city != "Remote") {
            binding.buttonAddLocation.text = getString(R.string.PickLocation)
        } else {
            binding.buttonAddLocation.visibility = GONE
        }
    }

    fun updateMeetingPlace(places: ArrayList<LocationPlace>) {
        val tempLocation = places[0]
        meeting.location = tempLocation.name
        val location = "$city:\n${meeting.location}"
        view?.findViewById<TextView>(R.id.cityChoice)?.text = location
        view?.findViewById<TextView>(R.id.cityChoice)?.setOnClickListener {
            val intent = Intent(activity,MapsActivity::class.java)
            intent.putExtra("placeName", tempLocation.name)
            intent.putExtra("placeLat", tempLocation.latLng.latitude)
            intent.putExtra("placeLong", tempLocation.latLng.longitude)
            activity?.startActivity(intent)
        }
        view?.findViewById<Button>(R.id.button_addLocation)?.visibility = GONE
    }

    private fun displayMeetingDetails() {
        if (!this::meetings.isInitialized) {
            val meeting: TextView = binding.teamPageMeetingDetails
            meeting.text = "No meeting planned."
        } else {
            if (this::meetings.isInitialized) {
                meeting = meetings[0]
                if (meeting.date == "") {
                    binding.teamPageMeetingDetails.text = "No meeting planned."
                } else {
                    val details = meeting.date + ": " + meeting.time
                    binding.teamPageMeetingDetails.text = details
                }
            }
        }
    }

    private fun setUpGenerateMeetingButton(groupName : String, times : ArrayList<Meeting>) {
        if (this::meetings.isInitialized) {
            if (meetings[0].date != "") {
                binding.FABmeeting.visibility = GONE
            }
        }
        binding.FABmeeting.setOnClickListener {
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
        if (!this::meetings.isInitialized) {
            binding.FABcancel.visibility = GONE
        } else {
            binding.FABcancel.visibility = VISIBLE
            binding.FABcancel.setOnClickListener {
                meetings[0].date = ""
                caller_cancelMeeting.OnCancelMeetingButtonTapped()
            }
        }
    }

    private fun setUpAddLocationButton() {
        if (!this::meetings.isInitialized) {
            binding.buttonAddLocation.visibility = GONE
        } else {
            if (this::meetings.isInitialized) {
                if (meetings[0].date == "") {
                    binding.buttonAddLocation.visibility = GONE
                } else {
                    val randIndex = Random.nextInt(0, meetings.size)
                    var startTime = meetings[randIndex].time!!.subSequence(0,2).toString()
                    if (startTime[1] == ':') {
                        startTime = startTime[0].toString()
                    }
                    if (startTime.toInt() >= 16) {
                        meeting.location = getString(R.string.Remote)
                        val location = meeting.location
                        view?.findViewById<TextView>(R.id.cityChoice)?.text = location
                        binding.buttonAddLocation.visibility = GONE
                    } else {
                        binding.buttonAddLocation.visibility = VISIBLE
                        binding.buttonAddLocation.setOnClickListener {
                            if (binding.buttonAddLocation.text == getString(R.string.PickLocation)) {
                                caller_addLocationPlace.OnAddLocationPlaceButtonTapped(city)
                            } else {
                                caller_addLocation.OnAddLocationButtonTapped()
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSelectedCity(city: String) {
        this.city = city
        Log.i("Selected city", city)
    }
}