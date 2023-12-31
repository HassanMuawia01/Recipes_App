package com.example.recipesapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.recipesapp.databinding.PopularRvItemBinding
import com.example.recipesapp.room_database.RecipeEntity

class PopularAdapter(var datalist:ArrayList<RecipeEntity>,var context: Context):RecyclerView.Adapter<PopularAdapter.viewHolder>() {

    inner class viewHolder(var binding: PopularRvItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
       var binding=PopularRvItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return viewHolder(binding)
    }

    override fun getItemCount(): Int {
       return datalist.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        //image load
        Glide.with(context).load(datalist.get(position).img).into(holder.binding.popularImg)
        holder.binding.popularText.text=datalist.get(position).tittle
        var time=datalist.get(position).ing.split("\n".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        holder.binding.popularTime.text=time.get(0)
        holder.binding.popularImg.setOnClickListener {
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