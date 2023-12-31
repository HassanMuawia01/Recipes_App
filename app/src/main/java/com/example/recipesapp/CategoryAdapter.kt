package com.example.recipesapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipesapp.databinding.CategoryRvBinding
import com.example.recipesapp.room_database.RecipeEntity

class CategoryAdapter(var datalist:ArrayList<RecipeEntity>,var context: Context):RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CategoryRvBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
     val view=CategoryRvBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datalist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(datalist.get(position).img).into(holder.binding.img)
        holder.binding.categoryTittle.text=datalist.get(position).tittle
        var time=datalist.get(position).ing.split("\n".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        holder.binding.categoryTime.text=time[0]
        holder.binding.next.setOnClickListener {
            var intent= Intent(context,RecipeActivity::class.java)
            intent.putExtra("img",datalist.get(position).img)
            intent.putExtra("tittle",datalist.get(position).tittle)
            intent.putExtra("des",datalist.get(position).des)
            intent.putExtra("ing",datalist.get(position).ing)
            intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }

    }
}