package com.example.recipesapp

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract.Colors
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.recipesapp.databinding.ActivityHomeBinding
import com.example.recipesapp.room_database.AppDatabase
import com.example.recipesapp.room_database.RecipeEntity

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var rvAdapter: PopularAdapter
    private lateinit var datalist: ArrayList<RecipeEntity>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecyclerView()
        binding.search.setOnClickListener {
            startActivity(Intent(this,SearchActivity::class.java))
        }
        binding.bottomNav.setOnClickListener {
            val dialog=Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.bottom_sheet)
            dialog.show()
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window!!.setGravity(Gravity.BOTTOM)
        }
        binding.salad.setOnClickListener {
            val myIntent=Intent(this@HomeActivity,CategoryActivity::class.java)
            myIntent.putExtra("TITTLE","Salad")
            myIntent.putExtra("CATEGORY","Salad")
            startActivity(myIntent)
        }
        binding.maindish.setOnClickListener {
            val myIntent=Intent(this@HomeActivity,CategoryActivity::class.java)
            myIntent.putExtra("TITTLE","Main Dish")
            myIntent.putExtra("CATEGORY","Dish")
            startActivity(myIntent)
        }
        binding.Drinks.setOnClickListener {
            val myIntent=Intent(this@HomeActivity,CategoryActivity::class.java)
            myIntent.putExtra("TITTLE","Drinks")
            myIntent.putExtra("CATEGORY","Drinks")
            startActivity(myIntent)
        }
        binding.dessert.setOnClickListener {
            val myIntent=Intent(this@HomeActivity,CategoryActivity::class.java)
            myIntent.putExtra("TITTLE","Desserts")
            myIntent.putExtra("CATEGORY","Desserts")
            startActivity(myIntent)
        }
    }

    private fun setUpRecyclerView() {
        datalist = ArrayList()
        binding.rvPopular.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        //create roomDatabase
        val db = Room.databaseBuilder(this@HomeActivity, AppDatabase::class.java, "db_name")
            .allowMainThreadQueries() //koi b chez main thred ko block na kary
            .fallbackToDestructiveMigration()     //old version par b ye kaam kary
            .createFromAsset("recipe.db")  //ye agr pely se database bana ho tb use hota h
            .build()
        //dao class object create
        var daoObject = db.getDao()
        // jitna b data h dao wo le kar aya h or wo recipeEntitye mein add ho gya h
        var recipeEntity = daoObject.getAll()
        for (i in recipeEntity!!.indices) {
            if (recipeEntity[i]!!.category.contains("Popular")) {
                datalist.add(recipeEntity[i]!!)
            }
            rvAdapter = PopularAdapter(datalist, this)
            binding.rvPopular.adapter = rvAdapter

        }
    }
}