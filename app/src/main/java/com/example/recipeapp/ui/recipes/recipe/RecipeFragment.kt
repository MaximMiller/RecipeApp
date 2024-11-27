package com.example.recipeapp.ui.recipes.recipe

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentRecipeBinding
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.ui.Constants
import com.google.android.material.divider.MaterialDividerItemDecoration


class RecipeFragment : Fragment() {
    private var _binding: FragmentRecipeBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalAccessException("Binding for FragmentRecipeBinding most not be null")

    private val viewModel: RecipeViewModel by viewModels()
    private lateinit var ingredientsAdapter: IngredientsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recipeId = arguments?.getInt(Constants.ARG_CATEGORY_ID)
        recipeId?.let {
            viewModel.loadRecipe(it)
        }
        initUI()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("ResourceAsColor")
    private fun initUI() {
        setupObservers()
        setupClickListenersIbLike()
        setupRecyclerView()
        setupSeekBar()
    }

    private fun setupObservers() {
        viewModel.recipeState.observe(viewLifecycleOwner) { state ->
            state.recipe?.let { recipe ->
                state.imageUrl?.let { imageUrl ->
                    updateUI(recipe, state.isFavorite, imageUrl)
                }
            }
        }
    }

    private fun updateUI(recipe: Recipe, isFavorite: Boolean, imageUrl: String) {
        binding.tvHeading.text = recipe.title
        Glide.with(this).load(imageUrl).into(binding.ivHeading)

        if (!::ingredientsAdapter.isInitialized) {
            ingredientsAdapter = IngredientsAdapter(recipe.ingredients)
            binding.rvIngredients.adapter = ingredientsAdapter
        }
        ingredientsAdapter.updateIngredients(viewModel.recipeState.value?.portionCount ?: 1)

        binding.rvMethod.adapter = MethodAdapter(recipe.method)
        binding.ibLike.setImageResource(if (isFavorite) R.drawable.ic_heart else R.drawable.ic_heart_empty)
    }

    private fun setupClickListenersIbLike() {
        binding.ibLike.setOnClickListener {
            viewModel.recipeState.value?.recipe?.let { recipe ->
                viewModel.onFavoritesClicked(recipe.id)
            }
        }
    }

    private fun setupRecyclerView() {
        val dividerItemDecoration = MaterialDividerItemDecoration(
            binding.rvIngredients.context, LinearLayoutManager.VERTICAL
        ).apply {
            setDividerColor(
                ContextCompat.getColor(
                    binding.rvIngredients.context, R.color.dividerItemDecoration
                )
            )
            dividerInsetStart = resources.getDimensionPixelSize(R.dimen.text_size_small)
            dividerInsetEnd = resources.getDimensionPixelSize(R.dimen.text_size_small)
        }

        binding.rvIngredients.addItemDecoration(dividerItemDecoration)
        binding.rvMethod.addItemDecoration(dividerItemDecoration)
    }

    private fun setupSeekBar() {
        binding.sbCountPortion.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            private var lastProgress = 1
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, p2: Boolean) {
                binding.tvCountPortion.text = progress.toString()
                if (progress != lastProgress) {
                    val currentState = viewModel.recipeState.value
                    currentState?.let {
                        (binding.rvIngredients.adapter as? IngredientsAdapter)?.updateIngredients(
                            progress
                        )
                        viewModel.onPortionsCountChanged(progress)
                        lastProgress = progress
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    companion object {
        const val SHARED_PREFS_NAME = "recipe_prefs"
        const val FAVORITES_KEY = "favorites"
    }

}
