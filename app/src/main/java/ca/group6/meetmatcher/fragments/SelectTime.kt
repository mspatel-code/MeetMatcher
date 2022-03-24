package ca.group6.meetmatcher.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.group6.meetmatcher.model.Meeting
import ca.group6.meetmatcher.databinding.FragmentSelectTimeBinding

interface OnFinishSelectTimeListener
{
    //fun OnFinishSelectTimeTapped(times: Array<String>?)
    //fun OnFinishSelectTimeTapped(confirmMeetng: Meeting)
    fun OnFinishSelectTimeTapped(confirmMeetng: ArrayList<Meeting>)
}

class SelectTime : Fragment() {
    private var _binding: FragmentSelectTimeBinding? = null
    private val binding get() = _binding!!
    private lateinit var list_times : ArrayList<Meeting>
    private lateinit var caller_finishSelect: OnFinishSelectTimeListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            list_times = it.getParcelableArrayList("listTime")!!
            //list_times = it.getStringArray("listTime")
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectTimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(times : ArrayList<Meeting>) =
            SelectTime().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList("listTime", times)
                }
            }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFinishSelectTimeListener) {
            caller_finishSelect = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.selectTimeDone.setOnClickListener {
//            val transaction = activity?.supportFragmentManager?.beginTransaction()
//            transaction?.replace(R.id.fragment_container, TeamPage())
//            transaction?.disallowAddToBackStack()
//            transaction?.commit()
            val selectedList: ArrayList<Meeting> = arrayListOf()
            if (binding.checkBox1.isChecked) {
                selectedList.add(list_times[0])
            }
            if (binding.checkBox2.isChecked) {
                selectedList.add(list_times[1])
            }
            if (binding.checkBox3.isChecked) {
                selectedList.add(list_times[2])
            }
            //var meeting: Meeting = Meeting("Feb 15", "8:00", "")
            caller_finishSelect.OnFinishSelectTimeTapped(selectedList)
        }
        setUpCheckBoxes()
    }

    private fun setUpCheckBoxes() {
        binding.checkBox1.text = list_times[0].toString()
        binding.checkBox2.text = list_times[1].toString()
        binding.checkBox3.text = list_times[2].toString()
    }
}