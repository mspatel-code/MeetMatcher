package ca.group6.meetmatcher.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.group6.meetmatcher.BuildConfig
import ca.group6.meetmatcher.MapsActivity
import ca.group6.meetmatcher.R
import ca.group6.meetmatcher.databinding.FragmentChooseLocationPlaceBinding
import ca.group6.meetmatcher.databinding.RowLocationplaceBinding
//import com.google.android.libraries.places.api.Places
//import com.google.android.libraries.places.api.model.Place
//import com.google.android.libraries.places.api.net.FetchPlaceRequest
//import com.google.android.libraries.places.api.net.PlacesClient

interface OnPlaceDoneButtonListener {
    fun OnPlaceDoneButtonTapped(places: ArrayList<String>, city: String)
}


class ChooseLocationPlace : Fragment(), RadioGroup.OnCheckedChangeListener {
    private lateinit var caller_onPlaceDone: OnPlaceDoneButtonListener

    private var _binding: FragmentChooseLocationPlaceBinding? = null
    private val binding get() = _binding!!
//    private var placesClient: PlacesClient? = null
    private var listCafe_Van: Array<String> = arrayOf()
    private var listLib_Van: Array<String> = arrayOf()
    private lateinit var cafeAdapter: LocationPlaceAdapter
    private lateinit var  libraryAdapter: LocationPlaceAdapter
    private var checkedPlaces: ArrayList<Boolean> = ArrayList()
    private lateinit var city: String

    companion object {
        @JvmStatic
        fun newInstance(inpCity: String) =
            ChooseLocationPlace().apply {
                arguments = Bundle().apply {
                    putString("LocationCity", inpCity)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            city = it.getString("LocationCity").toString()
//            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentChooseLocationPlaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        if (!Places.isInitialized()) {
//            context?.let { Places.initialize(it, { BuildConfig.MAPS_API_KEY }.toString()) }
//        }
//        placesClient = context?.let { Places.createClient(it) }
//        var placeFields: ArrayList<Place.Field> = ArrayList()
//        placeFields.add(Place.Field.NAME)
//        placeFields.add(Place.Field.TYPES)
//        placeFields.add(Place.Field.LAT_LNG)



        listCafe_Van = resources.getStringArray(R.array.cafe_Van)
        listLib_Van = resources.getStringArray(R.array.library_Van)
        val radioGroup = binding.locationTypes
        radioGroup.setOnCheckedChangeListener(this)

        val recyclerView = binding.rvPlaceCafe
        recyclerView.layoutManager = LinearLayoutManager(activity)

        cafeAdapter = LocationPlaceAdapter(listCafe_Van)
        libraryAdapter = LocationPlaceAdapter(listLib_Van)
        for (i in 0..listCafe_Van.size) {
            checkedPlaces.add(false)
        }

        recyclerView.visibility = GONE

        binding.buttonPlaceDone.setOnClickListener {
            val typeSelected = view.findViewById<RadioButton>(binding.locationTypes.checkedRadioButtonId)
            val placesSelected: ArrayList<String> = ArrayList()
            if (typeSelected.text == getString(R.string.cafe)) {
                for (i in 0 until checkedPlaces.size) {
                    if (checkedPlaces[i]) {
                        placesSelected.add(listCafe_Van[i])
                    }
                }
            } else {
                for (i in 0 until checkedPlaces.size) {
                    if (checkedPlaces[i]) {
                        placesSelected.add(listLib_Van[i])
                    }
                }
            }
            caller_onPlaceDone.OnPlaceDoneButtonTapped(placesSelected, city)
            //activity?.supportFragmentManager?.popBackStack()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnPlaceDoneButtonListener) {
            caller_onPlaceDone = context
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
                    val intent = Intent(activity,MapsActivity::class.java)
                    intent.putExtra("placeName", placeName)
                    intent.putExtra("placeLat", 49.27574947559409)
                    intent.putExtra("placeLong", -123.12371080205423)
                    activity?.startActivity(intent)
                }
                itemBinding.placeName.setOnClickListener {
                    checkedPlaces[position] = !checkedPlaces[position]
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