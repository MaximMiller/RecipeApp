package com.example.recipeapp

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.recipeapp.databinding.FragmentListRecipesBinding
import java.io.IOException
import java.io.InputStream

class RecipesListFragment : Fragment() {
    private var _binding: FragmentListRecipesBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalAccessException("Binding for FragmentListRecipesBinding most not be null")
    private var categoryId: Int? = null
    private var categoryName: String? = null
    private var categoryImageUrl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListRecipesBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initBundleData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initBundleData() {
         arguments?.let { argument ->
            categoryId = argument.getInt(Constants.ARG_CATEGORY_ID)
            categoryName = argument.getString(Constants.ARG_CATEGORY_NAME) ?: ""
            categoryImageUrl =
                argument.getString(Constants.ARG_CATEGORY_IMAGE_URI) ?: ""

            binding.tvHeading.text = categoryName
            var inputStream: InputStream? = null
            try {
                inputStream =
                    categoryImageUrl?.let { view?.context?.assets?.open(it) }
                val drawable = Drawable.createFromStream(inputStream, null)
                binding.ivHeading.setImageDrawable(drawable)
            } catch (e: IOException) {
                Log.e("AssetsHelper", "Error loading asset file", e)
            } finally {
                inputStream?.close()
            }
        } ?: run {
            Log.e("BundleError", "Arguments are null")
        }
    }
}