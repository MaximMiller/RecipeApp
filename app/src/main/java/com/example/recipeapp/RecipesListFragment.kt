package com.example.recipeapp

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
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
        initRecycler()
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

    private fun initRecycler() {
        categoryId?.let { idCategory ->
            val adapter = RecipesListAdapter(STUB.getRecipesByCategoryId(idCategory))
            binding.rvRecipesBurgers.adapter = adapter
            adapter.setOnClickListener(object : RecipesListAdapter.OnItemClickListener {
                override fun onItemClick(recipesId: Int) {
                    openRecipeByRecipeId(recipesId)
                }
            })
        } ?: run {
            Log.e("RecyclerError", "Category ID is null")
        }
    }

    private fun openRecipeByRecipeId(recipesId: Int) {

        parentFragmentManager.commit {
            replace<RecipeFragment>(R.id.mainContainer)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }


}


