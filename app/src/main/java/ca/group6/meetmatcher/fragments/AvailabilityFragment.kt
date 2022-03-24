package ca.group6.meetmatcher.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.group6.meetmatcher.databinding.FragmentAvailabilityBinding
import android.app.TimePickerDialog
import java.text.SimpleDateFormat
import java.util.*
import android.widget.ListView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import ca.group6.meetmatcher.R

import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
/**
 * A simple [Fragment] subclass.
 * Use the [AvailabilityFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AvailabilityFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentAvailabilityBinding? = null
    private val binding get() = _binding!!
    lateinit var editText: EditText
    lateinit var button: Button
    lateinit var listView: ListView
    var list:ArrayList<String> = ArrayList()

    lateinit var arrayAdapter: ArrayAdapter<String>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAvailabilityBinding.inflate(inflater, container, false)
        //------------------------------------------------------------
        // MONDAY: START-TIME
        //-----------------------------------------------------------
        binding.startTime1.setOnClickListener{
            val calS1 = Calendar.getInstance()
            val timeSetListenerS1 = TimePickerDialog.OnTimeSetListener{timePicker, hour, minute ->
                calS1.set(Calendar.HOUR_OF_DAY,hour)
                calS1.set(Calendar.MINUTE,minute)
                val mondayST = SimpleDateFormat("hh:mm aa").format(calS1.time)
                binding.startTime1.text = mondayST
            }
            TimePickerDialog(requireContext(),timeSetListenerS1,calS1.get(Calendar.HOUR_OF_DAY), calS1.get(Calendar.MINUTE),false).show()
        }

        //------------------------------------------------------------
        // TUESDAY START-TIME
        //------------------------------------------------------------
        binding.startTime2.setOnClickListener{
            val calS2 = Calendar.getInstance()
            val timeSetListenerS2 = TimePickerDialog.OnTimeSetListener{timePicker, hour, minute ->
                calS2.set(Calendar.HOUR_OF_DAY,hour)
                calS2.set(Calendar.MINUTE,minute)
                val tuesdayST = SimpleDateFormat("hh:mm aa").format(calS2.time)
                binding.startTime2.text = tuesdayST

            }
            TimePickerDialog(requireContext(),timeSetListenerS2,calS2.get(Calendar.HOUR_OF_DAY), calS2.get(Calendar.MINUTE),false).show()
        }

        //--------------------------------------------------------------
        // WEDNESDAY START-TIME
        //-------------------------------------------------------------
        binding.startTime3.setOnClickListener{
            val calS3 = Calendar.getInstance()
            val timeSetListenerS3 = TimePickerDialog.OnTimeSetListener{timePicker, hour, minute ->
                calS3.set(Calendar.HOUR_OF_DAY,hour)
                calS3.set(Calendar.MINUTE,minute)
                val wednesdayST = SimpleDateFormat("hh:mm aa").format(calS3.time)
                binding.startTime3.text = wednesdayST

            }
            TimePickerDialog(requireContext(),timeSetListenerS3,calS3.get(Calendar.HOUR_OF_DAY), calS3.get(Calendar.MINUTE),false).show()
        }

        //--------------------------------------------------------------
        // THURSDAY START-TIME
        //-------------------------------------------------------------
        binding.startTime4.setOnClickListener{
            val calS4 = Calendar.getInstance()
            val timeSetListenerS4 = TimePickerDialog.OnTimeSetListener{timePicker, hour, minute ->
                calS4.set(Calendar.HOUR_OF_DAY,hour)
                calS4.set(Calendar.MINUTE,minute)
                val thursdayST = SimpleDateFormat("hh:mm aa").format(calS4.time)
                binding.startTime4.text = thursdayST
            }
            TimePickerDialog(requireContext(),timeSetListenerS4,calS4.get(Calendar.HOUR_OF_DAY), calS4.get(Calendar.MINUTE),false).show()
        }

        //--------------------------------------------------------------
        // FRIDAY START-TIME
        //-------------------------------------------------------------
        binding.startTime5.setOnClickListener{
            val calS5 = Calendar.getInstance()
            val timeSetListenerS5 = TimePickerDialog.OnTimeSetListener{timePicker, hour, minute ->
                calS5.set(Calendar.HOUR_OF_DAY,hour)
                calS5.set(Calendar.MINUTE,minute)
                val fridayST = SimpleDateFormat("hh:mm aa").format(calS5.time)
                binding.startTime5.text = fridayST

            }
            TimePickerDialog(requireContext(),timeSetListenerS5,calS5.get(Calendar.HOUR_OF_DAY), calS5.get(Calendar.MINUTE),false).show()
        }

        //--------------------------------------------------------------
        // SATURDAY START-TIME
        //-------------------------------------------------------------
        binding.startTime6.setOnClickListener{
            val calS6 = Calendar.getInstance()
            val timeSetListenerS6 = TimePickerDialog.OnTimeSetListener{timePicker, hour, minute ->
                calS6.set(Calendar.HOUR_OF_DAY,hour)
                calS6.set(Calendar.MINUTE,minute)

                val saturdayST = SimpleDateFormat("hh:mm aa").format(calS6.time)
                binding.startTime6.text = saturdayST

            }
            TimePickerDialog(requireContext(),timeSetListenerS6,calS6.get(Calendar.HOUR_OF_DAY), calS6.get(Calendar.MINUTE),false).show()
        }

        //--------------------------------------------------------------
        // SUNDAY START-TIME
        //-------------------------------------------------------------
        binding.startTime7.setOnClickListener{
            val calS7 = Calendar.getInstance()
            val timeSetListenerS7 = TimePickerDialog.OnTimeSetListener{timePicker, hour, minute ->
                calS7.set(Calendar.HOUR_OF_DAY,hour)
                calS7.set(Calendar.MINUTE,minute)

                val sundayST = SimpleDateFormat("hh:mm aa").format(calS7.time)
                binding.startTime7.text = sundayST

            }
            TimePickerDialog(requireContext(),timeSetListenerS7,calS7.get(Calendar.HOUR_OF_DAY), calS7.get(Calendar.MINUTE),false).show()
        }

        //--------------------------------------------------------------
        // MONDAY END-TIME
        //-------------------------------------------------------------
        binding.endTime1.setOnClickListener{
            val calE1 = Calendar.getInstance()
            val timeSetListenerE1 = TimePickerDialog.OnTimeSetListener{timePicker, hour, minute ->
                calE1.set(Calendar.HOUR_OF_DAY,hour)
                calE1.set(Calendar.MINUTE,minute)

                val mondayET = SimpleDateFormat("hh:mm aa").format(calE1.time)
                binding.endTime1.text = mondayET

            }
            TimePickerDialog(requireContext(),timeSetListenerE1,calE1.get(Calendar.HOUR_OF_DAY), calE1.get(Calendar.MINUTE),false).show()
        }

        //--------------------------------------------------------------
        // TUESDAY END-TIME
        //-------------------------------------------------------------
        binding.endTime2.setOnClickListener{
            val calE2 = Calendar.getInstance()
            val timeSetListenereE2 = TimePickerDialog.OnTimeSetListener{timePicker, hour, minute ->
                calE2.set(Calendar.HOUR_OF_DAY,hour)
                calE2.set(Calendar.MINUTE,minute)

                val tuesdayET = SimpleDateFormat("hh:mm aa").format(calE2.time)
                binding.endTime2.text = tuesdayET

            }
            TimePickerDialog(requireContext(),timeSetListenereE2,calE2.get(Calendar.HOUR_OF_DAY), calE2.get(Calendar.MINUTE),false).show()
        }

        //--------------------------------------------------------------
        // WEDNESDAY END-TIME
        //-------------------------------------------------------------
        binding.endTime3.setOnClickListener{
            val calE3 = Calendar.getInstance()
            val timeSetListenereE3 = TimePickerDialog.OnTimeSetListener{timePicker, hour, minute ->
                calE3.set(Calendar.HOUR_OF_DAY,hour)
                calE3.set(Calendar.MINUTE,minute)

                val wednesdayET = SimpleDateFormat("hh:mm aa").format(calE3.time)
                binding.endTime3.text = wednesdayET

            }
            TimePickerDialog(requireContext(),timeSetListenereE3,calE3.get(Calendar.HOUR_OF_DAY), calE3.get(Calendar.MINUTE),false).show()
        }

        //--------------------------------------------------------------
        // THURSDAY END-TIME
        //-------------------------------------------------------------
        binding.endTime4.setOnClickListener{
            val calE4 = Calendar.getInstance()
            val timeSetListenereE4 = TimePickerDialog.OnTimeSetListener{timePicker, hour, minute ->
                calE4.set(Calendar.HOUR_OF_DAY,hour)
                calE4.set(Calendar.MINUTE,minute)

                val thursdayET = SimpleDateFormat("hh:mm aa").format(calE4.time)
                binding.endTime4.text = thursdayET

            }
            TimePickerDialog(requireContext(),timeSetListenereE4,calE4.get(Calendar.HOUR_OF_DAY), calE4.get(Calendar.MINUTE),false).show()
        }

        //--------------------------------------------------------------
        // FRIDAY END-TIME
        //-------------------------------------------------------------
        binding.endTime5.setOnClickListener{
            val calE5 = Calendar.getInstance()
            val timeSetListenereE5 = TimePickerDialog.OnTimeSetListener{timePicker, hour, minute ->
                calE5.set(Calendar.HOUR_OF_DAY,hour)
                calE5.set(Calendar.MINUTE,minute)

                val fridayET = SimpleDateFormat("hh:mm aa").format(calE5.time)
                binding.endTime5.text = fridayET

            }
            TimePickerDialog(requireContext(),timeSetListenereE5,calE5.get(Calendar.HOUR_OF_DAY), calE5.get(Calendar.MINUTE),false).show()
        }

        //--------------------------------------------------------------
        // SATURDAY END-TIME
        //-------------------------------------------------------------
        binding.endTime6.setOnClickListener{
            val calE6 = Calendar.getInstance()
            val timeSetListenereE6 = TimePickerDialog.OnTimeSetListener{timePicker, hour, minute ->
                calE6.set(Calendar.HOUR_OF_DAY,hour)
                calE6.set(Calendar.MINUTE,minute)

                val saturdayET = SimpleDateFormat("hh:mm aa").format(calE6.time)
                binding.endTime6.text = saturdayET

            }
            TimePickerDialog(requireContext(),timeSetListenereE6,calE6.get(Calendar.HOUR_OF_DAY), calE6.get(Calendar.MINUTE),false).show()
        }

        //--------------------------------------------------------------
        // SUNDAY END-TIME
        //-------------------------------------------------------------
        binding.endTime7.setOnClickListener{
            val calE7 = Calendar.getInstance()
            val timeSetListenereE7 = TimePickerDialog.OnTimeSetListener{timePicker, hour, minute ->
                calE7.set(Calendar.HOUR_OF_DAY,hour)
                calE7.set(Calendar.MINUTE,minute)
                val sundayET = SimpleDateFormat("hh:mm aa").format(calE7.time)
                binding.endTime7.text = sundayET


            }
            TimePickerDialog(requireContext(),timeSetListenereE7,calE7.get(Calendar.HOUR_OF_DAY), calE7.get(Calendar.MINUTE),false).show()
        }

        //listView = binding.root.findViewById(R.id.MondayLV)
        //editText = binding.root.findViewById(R.id.xyz)
       //mo button = binding.root.findViewById(R.id.testB)

        //arrayAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,list)

      //  button.setOnClickListener{
        //    list.add(editText.text.toString())
         //   editText.setText("")
          //  arrayAdapter.notifyDataSetChanged()
         //   listView.adapter = arrayAdapter
      //  }



        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AvailabilityFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AvailabilityFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}