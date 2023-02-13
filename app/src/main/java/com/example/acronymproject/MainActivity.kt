package com.example.acronymproject

import androidx.appcompat.app.AppCompatActivity
import com.example.acronymproject.models.Longform
import com.example.acronymproject.adapters.LongformRecyclerViewAdapter
import com.example.acronymproject.viewmodels.AcronymViewModel
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.acronymproject.R
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.acronymproject.api.APIResponse
import com.example.acronymproject.databinding.ActivityMainBinding
import com.example.acronymproject.models.Acronym
import java.util.ArrayList

/**
 * This is MainActivity class, launcher Activity containing logic for fetching long form
 * data corresponding to the short form provided by user, and display the list on screen.
*/

class MainActivity : AppCompatActivity() {

    private var activityMainBinding: ActivityMainBinding? = null
    private var longformArrayList: ArrayList<Longform>? = null
    private var myRecyclerViewAdapter: LongformRecyclerViewAdapter? = null
    private var viewModel: AcronymViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("onCreate")
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(AcronymViewModel::class.java)
        longformArrayList = ArrayList()
        val recyclerView = activityMainBinding!!.recyclerView
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            layoutManager.orientation
        )
        recyclerView.addItemDecoration(dividerItemDecoration)
        myRecyclerViewAdapter = LongformRecyclerViewAdapter(longformArrayList!!, this)
        recyclerView.adapter = myRecyclerViewAdapter
        viewModel!!.acronymListLiveData.observe(
            this,
            Observer { response ->
                println("onChanged: ViewModel")
                if (response == null) {
                    println("onChanged: Not yet set")
                    return@Observer
                }

                if(response.isSuccess() == false){
                    updateLongformList(ArrayList())
                    activityMainBinding!!.searchStatus.text = response.errorMessage
                    return@Observer
                }

                val acronyms = response.body
                println("onChanged: $acronyms")
                if (acronyms?.isEmpty() == true) {
                    println("onChanged: No Acronyms found")
                    updateLongformList(ArrayList())
                    return@Observer
                } else {
                    val longforms = acronyms?.get(0)?.longForms
                    if (longforms!!.isEmpty()) {
                        println("onChanged: No Longforms found")
                    }
                    updateLongformList(longforms)
                    return@Observer
                }
            })
        activityMainBinding!!.searchButton.setOnClickListener { performSearch() }
    }

    fun performSearch() {
        val queryWord = activityMainBinding!!.searchBox.text.toString();

        if(queryWord.length == 0) {
            Toast.makeText(this, "Please enter any Acronym", Toast.LENGTH_LONG).show()
        }else {
            hideKeyboard()
            viewModel!!.searchAcronyms(queryWord)
        }
    }

    fun updateLongformList(newLongforms: ArrayList<Longform>?) {
        longformArrayList!!.clear()
        if (newLongforms != null && newLongforms.size > 0) longformArrayList!!.addAll(newLongforms)
        activityMainBinding!!.searchStatus.text =
            String.format("%d search results found", longformArrayList!!.size)
        myRecyclerViewAdapter!!.notifyDataSetChanged()
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            view.clearFocus()
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}