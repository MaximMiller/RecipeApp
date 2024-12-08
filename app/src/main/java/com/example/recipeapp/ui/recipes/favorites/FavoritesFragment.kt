package com.example.recipeapp.ui.recipes.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentFavoritesBinding
import com.example.recipeapp.ui.Constants
import com.example.recipeapp.ui.recipes.listrecipes.RecipesListAdapter
import com.example.recipeapp.ui.recipes.recipe.RecipeFragment

class FavoritesFragment : Fragment() {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalAccessException("Binding for FragmentFavoritesBinding must not be null")
    private var adapter: RecipesListAdapter = RecipesListAdapter(emptyList())
    private val viewModel: FavoritesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.loadFavoriteRecipes()
        setupObservers()
        initRecycler()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupObservers() {
        viewModel.favoritesState.observe(viewLifecycleOwner) { state ->
            adapter.updateRecipes(state.favoriteRecipes)
            if (state.favoriteRecipes.isNotEmpty()) {
                showRecipes()
            } else {
                showStub()
            }
        }
    }

    private fun initRecycler() {
        binding.rvFavoriteRecipes.adapter = adapter
        adapter.setOnClickListener { recipesId -> openRecipeByRecipeId(recipesId) }
    }


    private fun openRecipeByRecipeId(recipesId: Int) {
        val bundle = bundleOf(Constants.ARG_CATEGORY_ID to recipesId)
        parentFragmentManager.commit {
            replace<RecipeFragment>(R.id.mainContainer, args = bundle)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    private fun showRecipes() {
        binding.rvFavoriteRecipes.visibility = View.VISIBLE
        binding.tvNoFavorites.visibility = View.GONE
    }

    private fun showStub() {
        binding.rvFavoriteRecipes.visibility = View.GONE
        binding.tvNoFavorites.visibility = View.VISIBLE
    }
}