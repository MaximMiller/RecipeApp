package com.example.recipeapp

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.databinding.ItemCategoryBinding
import java.io.IOException
import java.io.InputStream

class CategoriesListAdapter(
    private val dataSet: List<Category>,
) : RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {
    fun interface OnItemClickListener {
        fun onItemClick()
    }

    private var itemClickListener: OnItemClickListener? = null

    fun setOnClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemCategoryBinding.bind(itemView)
        val ivCategoryImage: ImageView = binding.ivCategoryImage
        val tvHeadingTitleCategory: TextView = binding.tvHeadingTitleCategory
        val tvHeadingDescriptionCategory: TextView = binding.tvHeadingDescriptionCategory
        val cvItemCategory: CardView = binding.cvItemCategory

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.cvItemCategory.setOnClickListener { itemClickListener?.onItemClick() }
        viewHolder.tvHeadingDescriptionCategory.text = dataSet[position].description
        viewHolder.tvHeadingTitleCategory.text = dataSet[position].title
        var inputStream: InputStream? = null
        try {
            inputStream =
                viewHolder.itemView.context?.assets?.open(dataSet[position].imageUrl)
            val drawable = Drawable.createFromStream(inputStream, null)
            viewHolder.ivCategoryImage.setImageDrawable(drawable)
        } catch (e: IOException) {
            Log.e("AssetsHelper", "Error loading asset file", e)
        } finally {
            inputStream?.close()
        }
    }


}