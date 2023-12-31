package com.example.recipesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.recipesapp.databinding.ActivityCategoryBinding
import com.example.recipesapp.room_database.AppDatabase
import com.example.recipesapp.room_database.RecipeEntity

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    private lateinit var datalist:ArrayList<RecipeEntity>
    private lateinit var rvAdapter: CategoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title=intent.getStringExtra("TITTLE")
      setUpRecyclerView()
        binding.goBackHome.setOnClickListener {
            finish()
        }

    }
    private fun setUpRecyclerView() {
        datalist = ArrayList()
        binding.rvCategory.layoutManager =
            LinearLayoutManager(this)
        //create roomDatabase
        val db = Room.databaseBuilder(this@CategoryActivity, AppDatabase::class.java, "db_name")
            .allowMainThreadQueries() //koi b chez main thred ko block na kary
            .fallbackToDestructiveMigration()     //old version par b ye kaam kary
            .createFromAsset("recipe.db")  //ye agr pely se database bana ho tb use hota h
            .build()
        //dao class object create
        var daoObject = db.getDao()
        // jitna b data h dao wo le kar aya h or wo recipeEntitye mein add ho gya h
        var recipeEntity = daoObject.getAll()
        for (i in recipeEntity!!.indices) {
            if (recipeEntity[i]!!.category.contains(intent.getStringExtra("CATEGORY")!!)) {
                datalist.add(recipeEntity[i]!!)
            }
            rvAdapter = CategoryAdapter(datalist, this)
            binding.rvCategory.adapter = rvAdapter

        }
    }
}