package com.example.recipeapp.ui.recipes.listrecipes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentListRecipesBinding
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.ui.Constants.ARG_CATEGORY_ID
import com.example.recipeapp.ui.recipes.recipe.RecipeFragment

class RecipesListFragment : Fragment() {
    private var _binding: FragmentListRecipesBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalAccessException("Binding for FragmentListRecipesBinding most not be null")
    private val viewModel: RecipeListViewModel by viewModels()


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
        val categoryId = arguments?.getInt(ARG_CATEGORY_ID) ?: return
        viewModel.initBundleData(categoryId)
        initObservers()
    }

    private fun initObservers() {
        viewModel.recipesListState.observe(viewLifecycleOwner) { state ->
            state.category?.let { category ->
                binding.tvHeading.text = category.title
                Glide.with(this)
                    .load("file:///android_asset/${category.imageUrl}")
                    .into(binding.ivHeading)
            }
            initRecycler(state.recipes)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecycler(recipes: List<Recipe>) {
        if (recipes.isNotEmpty()) {
            val adapter = RecipesListAdapter(recipes)
            binding.rvRecipesBurgers.adapter = adapter
            adapter.setOnClickListener(object : RecipesListAdapter.OnItemClickListener {
                override fun onItemClick(recipesId: Int) {
                    openRecipeByRecipeId(recipesId)
                }
            })
        } else {
            Log.e("RecyclerError", "No recipes found for this category")
        }
    }

    private fun openRecipeByRecipeId(recipesId: Int) {
        val bundle = bundleOf(
            ARG_CATEGORY_ID to recipesId,
        )
        parentFragmentManager.commit {
            replace<RecipeFragment>(R.id.mainContainer, args = bundle)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

}


