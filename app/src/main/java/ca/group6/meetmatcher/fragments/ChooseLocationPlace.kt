package ca.group6.meetmatcher.fragments

import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
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
import ca.group6.meetmatcher.BuildConfig.MAPS_API_KEY
import ca.group6.meetmatcher.MapsActivity
import ca.group6.meetmatcher.R
import ca.group6.meetmatcher.databinding.FragmentChooseLocationPlaceBinding
import ca.group6.meetmatcher.databinding.RowLocationplaceBinding
import ca.group6.meetmatcher.model.LocationPlace
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FetchPlaceResponse
import com.google.android.libraries.places.api.net.PlacesClient
import java.io.FileInputStream
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

//import com.google.android.libraries.places.api.net.FetchPlaceRequest
//import com.google.android.libraries.places.api.net.PlacesClient

interface OnPlaceDoneButtonListener {
    fun OnPlaceDoneButtonTapped(places: ArrayList<LocationPlace>, city: String)
}


class ChooseLocationPlace : Fragment(), RadioGroup.OnCheckedChangeListener {
    private lateinit var caller_onPlaceDone: OnPlaceDoneButtonListener

    private var _binding: FragmentChooseLocationPlaceBinding? = null
    private val binding get() = _binding!!
    private lateinit var placesClient: PlacesClient
    private var listCafe_Van: ArrayList<LocationPlace> = arrayListOf()
    private var listLib_Van: ArrayList<LocationPlace> = arrayListOf()
    private var listCafe_Rich: ArrayList<LocationPlace> = arrayListOf()
    private var listLib_Rich: ArrayList<LocationPlace> = arrayListOf()
    private var listLib_Burn: ArrayList <LocationPlace> = arrayListOf()
    private var listCafe_Burn: ArrayList<LocationPlace> = arrayListOf()

    private lateinit var cafeVanAdapter: LocationPlaceAdapter
    private lateinit var  libVanAdapter: LocationPlaceAdapter
    private lateinit var cafeRichAdapter: LocationPlaceAdapter
    private lateinit var libRichAdapter: LocationPlaceAdapter
    private lateinit var cafeBurnAdapter: LocationPlaceAdapter
    private lateinit var libBurnAdapter: LocationPlaceAdapter

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

        if (!Places.isInitialized()) {
            context?.let { Places.initialize(it, MAPS_API_KEY) }
        }
        placesClient = Places.createClient(requireContext())

        setUpLocationPlaces()
        val radioGroup = binding.locationTypes
        radioGroup.setOnCheckedChangeListener(this)

        val recyclerView = binding.rvPlaceCafe
        recyclerView.layoutManager = LinearLayoutManager(activity)

        cafeVanAdapter = LocationPlaceAdapter(listCafe_Van)
        libVanAdapter = LocationPlaceAdapter(listLib_Van)
        cafeRichAdapter = LocationPlaceAdapter(listCafe_Rich)
        libRichAdapter = LocationPlaceAdapter(listLib_Rich)
        cafeBurnAdapter = LocationPlaceAdapter(listCafe_Burn)
        libBurnAdapter = LocationPlaceAdapter(listLib_Burn)
        for (i in 0..listCafe_Van.size) {
            checkedPlaces.add(false)
        }

        recyclerView.visibility = GONE

        binding.buttonPlaceDone.setOnClickListener {
            val typeSelected = view.findViewById<RadioButton>(binding.locationTypes.checkedRadioButtonId)
            val placesSelected: ArrayList<LocationPlace> = ArrayList()
            if (typeSelected.text == getString(R.string.cafe)) {
                for (i in 0 until checkedPlaces.size) {
                    if (checkedPlaces[i]) {
                        when(city) {
                            getString(R.string.Vancouver) -> placesSelected.add(listCafe_Van[i])
                            getString(R.string.Richmond) -> placesSelected.add(listCafe_Rich[i])
                            getString(R.string.Burnaby) -> placesSelected.add(listCafe_Burn[i])
                        }
                    }
                }
            } else {
                for (i in 0 until checkedPlaces.size) {
                    if (checkedPlaces[i]) {
                        when(city) {
                            getString(R.string.Vancouver) -> placesSelected.add(listLib_Van[i])
                            getString(R.string.Richmond) -> placesSelected.add(listLib_Rich[i])
                            getString(R.string.Burnaby) -> placesSelected.add(listLib_Burn[i])
                        }
                    }
                }
            }
            caller_onPlaceDone.OnPlaceDoneButtonTapped(placesSelected, city)
            //activity?.supportFragmentManager?.popBackStack()
        }
    }

    private fun getLatLng(placeID: String): LocationPlace {
        val placeFields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)
        val request = FetchPlaceRequest.newInstance(placeID, placeFields)
        var locationPlace: LocationPlace = LocationPlace("empty", LatLng(0.0,0.0))
        placesClient.fetchPlace(request)
            .addOnSuccessListener { response: FetchPlaceResponse? ->
                if (response != null) {
                    Log.i("ChoosePlace", "Place found: ${response.place}")
                } else {
                    Log.i("ChoosePlace", "RESPONSE IS NULL---------------")
                }
                val place = response!!.place
                val name = place.name
                val latLng = place.latLng
                locationPlace.name = name!!
                locationPlace.latLng = latLng!!
                Log.i("ChoosePlace", "Place found: ${place.name}")
            }.addOnFailureListener { exception: Exception ->
                if (exception is ApiException) {
                    Log.e("ChoosePlace", "Place not found: ${exception.message}")
                    //val statusCode = exception.statusCode
                }
            }
        return locationPlace
    }

    private fun setUpLocationPlaces() {
        for (i in 0 until (resources.getStringArray(R.array.cafe_Van)).size) {
            val placeID = resources.getStringArray(R.array.cafe_Van_ID)[i]
            val locationPlace = getLatLng(placeID)
            listCafe_Van.add(locationPlace)
        }
        for (i in 0 until (resources.getStringArray(R.array.library_Van)).size) {
            val placeID = resources.getStringArray(R.array.lib_Van_ID)[i]
            val locationPlace = getLatLng(placeID)
            listLib_Van.add(locationPlace)
        }
        for (i in 0 until (resources.getStringArray(R.array.cafe_Richmond)).size) {
            val placeID = resources.getStringArray(R.array.cafe_Richmond_ID)[i]
            val locationPlace = getLatLng(placeID)
            listCafe_Rich.add(locationPlace)
        }
        for (i in 0 until (resources.getStringArray(R.array.library_Richmond)).size) {
            val placeID = resources.getStringArray(R.array.library_Richmond_ID)[i]
            val locationPlace = getLatLng(placeID)
            listLib_Rich.add(locationPlace)
        }
        for (i in 0 until (resources.getStringArray(R.array.cafe_Burnaby)).size) {
            val placeID = resources.getStringArray(R.array.cafe_Burnaby_ID)[i]
            val locationPlace = getLatLng(placeID)
            listCafe_Burn.add(locationPlace)
        }
        for (i in 0 until (resources.getStringArray(R.array.library_Burn)).size) {
            val placeID = resources.getStringArray(R.array.library_Burn_ID)[i]
            val locationPlace = getLatLng(placeID)
            listLib_Burn.add(locationPlace)
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

    internal inner class LocationPlaceAdapter(private val array: ArrayList<LocationPlace>):
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
                Log.i("RecyclerViewCheckBox", placeName.toString())
                itemBinding.placeName.text = placeName.name
                itemBinding.FABMap.setOnClickListener {
                    val intent = Intent(activity,MapsActivity::class.java)
                    intent.putExtra("placeName", placeName.name)
//                    intent.putExtra("placeLat", 49.27574947559409)
//                    intent.putExtra("placeLong", -123.12371080205423)
                    intent.putExtra("placeLat", placeName.latLng.latitude)
                    intent.putExtra("placeLong", placeName.latLng.longitude)
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
                when(city) {
                    getString(R.string.Vancouver) -> binding.rvPlaceCafe.swapAdapter(cafeVanAdapter,false)
                    getString(R.string.Richmond) -> binding.rvPlaceCafe.swapAdapter(cafeRichAdapter,false)
                    getString(R.string.Burnaby) -> binding.rvPlaceCafe.swapAdapter(cafeBurnAdapter,false)
                }
            } else {
                when(city) {
                    getString(R.string.Vancouver) -> binding.rvPlaceCafe.swapAdapter(libVanAdapter,false)
                    getString(R.string.Richmond) -> binding.rvPlaceCafe.swapAdapter(libRichAdapter,false)
                    getString(R.string.Burnaby) -> binding.rvPlaceCafe.swapAdapter(libBurnAdapter,false)
                }
            }
        }
    }
}