package ca.group6.meetmatcher.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.group6.meetmatcher.MapsActivity
import ca.group6.meetmatcher.R
import ca.group6.meetmatcher.databinding.FragmentChooseLocationPlaceBinding
import ca.group6.meetmatcher.databinding.FragmentTeamPageBinding
import ca.group6.meetmatcher.databinding.RowLocationplaceBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple ChooseLocationPlace subclass.
 * Use the [ChooseLocationPlace.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChooseLocationPlace : Fragment(), RadioGroup.OnCheckedChangeListener {
    private var _binding: FragmentChooseLocationPlaceBinding? = null
    private val binding get() = _binding!!
    private var listCafe_Van: Array<String> = arrayOf()
    private var listLib_Van: Array<String> = arrayOf()
    private lateinit var cafeAdapter: LocationPlaceAdapter
    private lateinit var  libraryAdapter: LocationPlaceAdapter
    private var checkedPlaces: ArrayList<Boolean> = ArrayList()
//    private var param1: String? = null
//    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChooseLocationPlaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listCafe_Van = resources.getStringArray(R.array.cafe_Van)
        listLib_Van = resources.getStringArray(R.array.library_Van)
        val radioGroup = binding.locationTypes
        radioGroup.setOnCheckedChangeListener(this)

        val recyclerView = binding.rvPlaceCafe
        recyclerView.layoutManager = LinearLayoutManager(activity)

        cafeAdapter = LocationPlaceAdapter(listCafe_Van)
        libraryAdapter = LocationPlaceAdapter(listLib_Van)

        recyclerView.visibility = GONE
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ChooseLocationPlace().apply {
                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }

    class MyViewHolder(val itemBinding: RowLocationplaceBinding) :
            RecyclerView.ViewHolder(itemBinding.root)

    internal inner class LocationPlaceAdapter(private val array: Array<String>):
            RecyclerView.Adapter<MyViewHolder> () {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val itemBinding = RowLocationplaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            //val inflater = LayoutInflater.from(parent.context)
            return MyViewHolder(itemBinding)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//            (holder.itemView as CheckBox).text = array[position]
            with(holder) {
                val placeName = array[position]
                itemBinding.placeName.text = placeName
                itemBinding.FABMap.setOnClickListener {
                    val intent: Intent = Intent(activity,MapsActivity::class.java)
                    intent.putExtra("placeName", placeName)
                    intent.putExtra("placeLat", 49.27574947559409)
                    intent.putExtra("placeLong", -123.12371080205423)
                    activity?.startActivity(intent)
                }
            }
        }

        override fun getItemCount() = array.size
    }

    override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
        val checkedRadioButton = p0?.let { p0.findViewById<RadioButton>(it.checkedRadioButtonId) }
        binding.rvPlaceCafe.visibility = VISIBLE
        checkedRadioButton?.let {
            if (checkedRadioButton.text == getString(R.string.cafe)) {
                binding.rvPlaceCafe.swapAdapter(cafeAdapter,false)
            } else {
                binding.rvPlaceCafe.swapAdapter(libraryAdapter, false)
            }
        }
    }
}