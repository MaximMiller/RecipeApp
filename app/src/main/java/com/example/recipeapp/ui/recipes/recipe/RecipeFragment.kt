package com.example.recipeapp.ui.recipes.recipe

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentRecipeBinding
import com.example.recipeapp.model.Recipe
import com.google.android.material.divider.MaterialDividerItemDecoration


class RecipeFragment : Fragment() {
    private var _binding: FragmentRecipeBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalAccessException("Binding for FragmentRecipeBinding most not be null")

    private val viewModel: RecipeViewModel by viewModels()
    private var ingredientsAdapter: IngredientsAdapter = IngredientsAdapter(emptyList())
    private var methodAdapter: MethodAdapter = MethodAdapter(emptyList())
    private val args: RecipeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recipeId = args.recipeId
        recipeId.let {
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
                updateUI(recipe, state.isFavorite, state.portionCount)
            }
        }
    }


    private fun updateUI(recipe: Recipe, isFavorite: Boolean, portionCount: Int) {
        binding.tvHeading.text = recipe.title
        Glide.with(this).load("file:///android_asset/${recipe.imageUrl}").into(binding.ivHeading)

        binding.tvCountPortion.text = portionCount.toString()
        methodAdapter.updateMethod(recipe.method)
        ingredientsAdapter.updateIngredients(recipe.ingredients, portionCount)
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

        binding.rvIngredients.adapter = ingredientsAdapter
        binding.rvMethod.adapter = methodAdapter
    }

    private fun setupSeekBar() {
        binding.sbCountPortion.setOnSeekBarChangeListener(PortionSeekBarListener { progress ->
            viewModel.onPortionsCountChanged(progress)
        })
    }

    open class PortionSeekBarListener(
        val onChangeIngredients: (Int) -> Unit
    ) : OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, p2: Boolean) {
            onChangeIngredients(progress)
        }

        override fun onStartTrackingTouch(p0: SeekBar?) {
        }

        override fun onStopTrackingTouch(p0: SeekBar?) {
        }

    }

    companion object {
        const val SHARED_PREFS_NAME = "recipe_prefs"
        const val FAVORITES_KEY = "favorites"
    }

}
