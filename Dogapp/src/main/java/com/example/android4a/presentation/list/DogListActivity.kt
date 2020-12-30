package com.example.android4a.presentation.list

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.android4a.data.local.DogAPI
import com.example.android4a.domain.entity.Dog
import com.example.android4a.domain.entity.RestDogResponse
import com.example.android4a.R
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class DogListActivity : AppCompatActivity() {
    private val BASE_URL : String = "https://raw.githubusercontent.com/Houyu0926/Houyu_app/master/"

    private var recyclerView: RecyclerView? = null
    private var mAdapter: ListAdapter? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var mySwipeRefreshLayout: SwipeRefreshLayout? = null
    private var sharedPreferences: SharedPreferences? = null
    private var gson: Gson? = null
    private var detailsInfo: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        sharedPreferences = getSharedPreferences("houyu_application", Context.MODE_PRIVATE)
        gson = GsonBuilder()
            .setLenient()
            .create()

        refreshCall()

        val dogList: MutableList<Dog?>? = getDataFromCache()
        if (dogList != null){
            showList(dogList)
        }else{
            makeApiCall()
        }

    }

    private fun getDataFromCache(): MutableList<Dog?>? {
        val jsonDog = sharedPreferences!!.getString("jsonDogList", null)
        return if (jsonDog == null) {
            null
        } else {
            val listType = object : TypeToken<List<Dog?>?>() {}.type
            gson!!.fromJson(jsonDog, listType)
        }
    }

    fun refreshCall(){
        mySwipeRefreshLayout = findViewById(R.id.swiperefresh) as SwipeRefreshLayout

        //Color set
        mySwipeRefreshLayout!!.setColorSchemeResources(
            android.R.color.holo_blue_light,
            android.R.color.holo_red_light,
            android.R.color.holo_orange_light
        )
        mySwipeRefreshLayout!!.setProgressBackgroundColorSchemeResource(android.R.color.white)

        mySwipeRefreshLayout!!.setOnRefreshListener {

            myUpdateOperation()
        }
    }

    fun showList(dogList: MutableList<Dog?>?){
        recyclerView = findViewById(R.id.recycler_view) as RecyclerView

        recyclerView!!.setHasFixedSize(true)
        // use a linear layout manager
        layoutManager = LinearLayoutManager(this)
        recyclerView!!.layoutManager = layoutManager


        mAdapter = ListAdapter(
            dogList,
            applicationContext,
            object : ListAdapter.OnItemClickListener {
                override fun onItemClick(item: Dog?) {
                     navigateToDetails(item)
                }
            })
        recyclerView!!.adapter = mAdapter
    }

    private fun myUpdateOperation() {
        makeApiCall()
        mySwipeRefreshLayout?.setRefreshing(false) // Disables the refresh icon
        //Toast.makeText(getApplicationContext(), "Refresh success !", Toast.LENGTH_SHORT).show()

    }

    fun makeApiCall() {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val dogApi: DogAPI = retrofit.create(DogAPI::class.java)

        val call: Call<RestDogResponse> = dogApi.breedResponse()
        call.enqueue(object : Callback<RestDogResponse> {
            override fun onResponse(
                call: Call<RestDogResponse?>?,
                response: Response<RestDogResponse?>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val dogList: MutableList<Dog?>? = response.body()!!.getInformation()
                    showList(dogList)
                    saveList(dogList)
                }
            }
            override fun onFailure(call: Call<RestDogResponse?>?, t: Throwable?) {
                showError()
            }
        })
    }
    private fun saveList(dogList: MutableList<Dog?>?){
        val jsonString: String = gson!!.toJson(dogList)

        sharedPreferences
            ?.edit()
            ?.putString("dogList", jsonString)
            ?.apply()
        //Toast.makeText(getApplicationContext(), "List saved !", Toast.LENGTH_SHORT).show()
    }

    private fun showError(){
        AlertDialog.Builder(this)
            .setTitle("API Error !")
            .setMessage("Loading failed, please check network connection.")
            .setPositiveButton("OK") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }

    fun navigateToDetails(dog: Dog?) {
        AlertDialog.Builder(this)
            .setTitle(dog?.getBreed().toString())
            .setMessage(dog?.getdetailInfo().toString())
            .setPositiveButton("OK") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }

}



