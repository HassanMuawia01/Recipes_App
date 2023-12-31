package com.example.recipesapp.room_database

import androidx.room.Dao
import androidx.room.Query

@Dao
interface Dao {
    @Query("Select * FROM recipe")
   fun getAll():List<RecipeEntity?>?
}