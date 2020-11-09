package com.example.weatherpractice

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.beust.klaxon.Klaxon
import java.io.File


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CityFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CityFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var selectedCity: String = "Moscow"

    var cities: Cities = Cities(arrayListOf())
    var cit: ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

       var cito: Cities = Cities(arrayListOf<City>())
        var citl = arrayListOf<City>()
        citl.add(City("Moscow"))
        citl.add(City("london"))
        cito = Cities(citl)

        var file: File;
        if (!File("/storage/emulated/0/Alarms/Cities.txt").exists()) {
            var path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS)
            file = File(path, "/Cities.txt")

            File("/storage/emulated/0/Alarms/Cities.txt").printWriter().use { out ->
                out.println(Klaxon().toJsonString(cit))
            }
        }
        else file = File("/storage/emulated/0/Alarms/Cities.txt")

        cities = Klaxon().parse<Cities>(File("/storage/emulated/0/Alarms/Cities.txt"))!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v = inflater.inflate(R.layout.fragment_city, container, false)
        var CitiesList = v?.findViewById<ListView>(R.id.CityList)
        var AddCityBtn = v?.findViewById<Button>(R.id.AddCityBtn)
        var NewNameCity = v?.findViewById<EditText>(R.id.NewNameCity)

        for (t in cities.Cities)
            cit.add(t.City)

        CitiesList?.adapter = ArrayAdapter<String>(requireActivity(), android.R.layout.simple_list_item_1, cit.sortedBy { x -> x.toLowerCase() })!!

        AddCityBtn?.setOnClickListener {
            if (cit.all { x-> x.toLowerCase() != NewNameCity?.text.toString().toLowerCase()  }) {
                cit.add(NewNameCity?.text.toString())

                cities.Cities.add(City(NewNameCity?.text.toString()))

                File("/storage/emulated/0/Alarms/Cities.txt").printWriter().use { out ->
                    out.println(Klaxon().toJsonString(cities))
                }

                CitiesList?.adapter = ArrayAdapter<String>(
                    requireActivity(),
                    android.R.layout.simple_list_item_1,
                    cit.sortedBy { x -> x.toLowerCase() })!!

                NewNameCity?.text?.clear()
            }
        }

        CitiesList?.setOnItemClickListener{ parent, view, position, id ->
            selectedCity = cities.Cities.sortedBy { x -> x.City.toLowerCase() }[position].City

            var file: File

            if (!File("/storage/emulated/0/Alarms/SelectedCity.txt").exists()) {
                var path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS)
                file = File(path, "/SelectedCity.txt")
            }
            else file = File("/storage/emulated/0/Alarms/SelectedCity.txt")

            File("/storage/emulated/0/Alarms/SelectedCity.txt").printWriter().use { out ->
                out.println("$selectedCity")
            }
        }

        return v
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CityFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CityFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
