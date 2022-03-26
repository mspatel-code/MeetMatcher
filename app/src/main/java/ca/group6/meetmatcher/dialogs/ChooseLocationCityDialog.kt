package ca.group6.meetmatcher.dialogs

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment
import ca.group6.meetmatcher.TeamPage
import ca.group6.meetmatcher.databinding.ChooseLocationCityBinding
import ca.group6.meetmatcher.model.Meeting

interface SelectedCity {
    fun onSelectedCity(city: String);
}

// reference site: https://medium.com/swlh/alertdialog-and-customdialog-in-android-with-kotlin-f42a168c1936
class ChooseLocationCityDialog: DialogFragment() {
    private var _binding: ChooseLocationCityBinding? = null
    private val binding get() = _binding!!
    private lateinit var caller_selectedCity: SelectedCity


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ChooseLocationCityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SelectedCity) {
            caller_selectedCity = context
            Log.i("DIALOG", context.toString())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val radioGroup = binding.radioGroupCities
        radioGroup.check(binding.radioButtonBurnaby.id)

        val doneButton = binding.chooseCityDone
        doneButton.setOnClickListener {
            val radioCity = binding.root.findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            caller_selectedCity.onSelectedCity(radioCity.text as String)

            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}