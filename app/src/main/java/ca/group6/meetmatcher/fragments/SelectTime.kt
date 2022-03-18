package ca.group6.meetmatcher.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.group6.meetmatcher.Meeting
import ca.group6.meetmatcher.databinding.FragmentSelectTimeBinding

interface OnFinishSelectTimeListener
{
    //fun OnFinishSelectTimeTapped(times: Array<String>?)
    fun OnFinishSelectTimeTapped(confirmMeetng: Meeting)
}

class SelectTime : Fragment() {
    private var _binding: FragmentSelectTimeBinding? = null
    private val binding get() = _binding!!
    private var list_times : Array<String>? = null
    private lateinit var caller_finishSelect: OnFinishSelectTimeListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            list_times = it.getStringArray("listTime")
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectTimeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(times : Array<String>) =
            SelectTime().apply {
                arguments = Bundle().apply {
                    putStringArray("listTime", times)
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
            var meeting: Meeting = Meeting("Feb 15", "8:00", "")
            caller_finishSelect.OnFinishSelectTimeTapped(meeting)
        }
        setUpCheckBoxes()
    }

    private fun setUpCheckBoxes() {
        binding.checkBox1.text = list_times?.get(0)
        binding.checkBox2.text = list_times?.get(1)
        binding.checkBox3.text = list_times?.get(2)
    }
}