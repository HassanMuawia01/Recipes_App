package com.example.recipesapp

import android.annotation.SuppressLint
import android.content.Intent
import android.inputmethodservice.InputMethodService
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.recipesapp.databinding.ActivityHomeBinding
import com.example.recipesapp.databinding.ActivitySearchBinding
import com.example.recipesapp.room_database.AppDatabase
import com.example.recipesapp.room_database.RecipeEntity

class SearchActivity : AppCompatActivity() {
    private lateinit var datalist: ArrayList<RecipeEntity>
    private lateinit var rvAdapter: SearchAdapter
    private lateinit var binding: ActivitySearchBinding
    private lateinit var recipeEntity: List<RecipeEntity?>
    @SuppressLint("ServiceCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.searchView.requestFocus()

        //create roomDatabase
        var db = Room.databaseBuilder(this@SearchActivity, AppDatabase::class.java, "db_name")
            .allowMainThreadQueries() //koi b chez main thred ko block na kary
            .fallbackToDestructiveMigration()     //old version par b ye kaam kary
            .createFromAsset("recipe.db")  //ye agr pely se database bana ho tb use hota h
            .build()
        //dao class object create
        var daoObject = db.getDao()
        // jitna b data h dao wo le kar aya h or wo recipeEntitye mein add ho gya h
        recipeEntity = daoObject.getAll()!!
        setUpRecyclerView()
        binding.goBackHome.setOnClickListener {
            finish()
        }
        binding.searchView.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
               if (s.toString()!="") {
                   filterData(s.toString())
               }else{
                   setUpRecyclerView()
               }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
//        var imm=getSystemService(INPUT_METHOD_SERVICE) as InputMethodService
//        binding.rvSearch.setOnTouchListener { v, event ->
//            imm.hideStatusIcon()
//        }


    }
    private fun filterData(filterText: String) {
        var filterData=ArrayList<RecipeEntity>()
        for (i in recipeEntity.indices){
            if (recipeEntity[i]!!.tittle.lowercase().contains(filterText.lowercase())){
                filterData.add(recipeEntity[i]!!)
            }
            rvAdapter.filterList(filterList = filterData)
        }
    }
    private fun setUpRecyclerView() {
        datalist = ArrayList()
        binding.rvSearch.layoutManager =
            LinearLayoutManager(this)
        for (i in recipeEntity!!.indices) {
            if (recipeEntity[i]!!.category.contains("Popular")) {
                datalist.add(recipeEntity[i]!!)
            }
            rvAdapter = SearchAdapter(this, datalist)
            binding.rvSearch.adapter = rvAdapter

        }
    }
}