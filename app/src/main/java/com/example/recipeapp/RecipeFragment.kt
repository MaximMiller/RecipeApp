package com.example.recipeapp

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.recipeapp.databinding.FragmentRecipeBinding
import com.google.android.material.divider.MaterialDividerItemDecoration


class RecipeFragment : Fragment() {
    private var isFavorite = false
    private var _binding: FragmentRecipeBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalAccessException("Binding for FragmentRecipeBinding most not be null")

    companion object {
         const val SHARED_PREFS_NAME = "recipe_prefs"
         const val FAVORITES_KEY = "favorites"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecycler()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("isFavorite", isFavorite)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            isFavorite = savedInstanceState.getBoolean("isFavorite", false)
        }
    }

    private fun initRecycler() {
        val recipe: Recipe? = if (Build.VERSION.SDK_INT >= 33) {
            arguments?.getParcelable("arg_recipe_data", Recipe::class.java)
        } else {
            arguments?.getParcelable("arg_recipe_data")
        }
        recipe?.let {
            val listIngredients = it.ingredients
            val ingredientsAdapter = IngredientsAdapter(listIngredients)
            binding.rvIngredients.adapter = ingredientsAdapter
            val listMethod = it.method
            val methodAdapter = MethodAdapter(listMethod)
            binding.rvMethod.adapter = methodAdapter
            binding.sbCountPortion.setOnSeekBarChangeListener(object :
                SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, p2: Boolean) {
                    binding.tvCountPortion.text = progress.toString()
                    ingredientsAdapter.updateIngredients(progress)

                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}

                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })
            initUI(it)
        } ?: run {
            Log.e("RecipeNotFound", "Recipe object not found")
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun initUI(recipe: Recipe) {
        binding.tvHeading.text = recipe.title
        Glide.with(this)
            .load("file:///android_asset/${recipe.imageUrl}")
            .into(binding.ivHeading)
        val rvIngredients = binding.rvIngredients
        val dividerItemDecoration = MaterialDividerItemDecoration(
            rvIngredients.context,
            LinearLayoutManager.VERTICAL
        ).apply {
            setDividerColor(
                ContextCompat.getColor(
                    rvIngredients.context,
                    R.color.dividerItemDecoration
                )
            )
            dividerInsetStart = resources.getDimensionPixelSize(R.dimen.text_size_small)
            dividerInsetEnd = resources.getDimensionPixelSize(R.dimen.text_size_small)
        }
        rvIngredients.addItemDecoration(dividerItemDecoration)
        val rvMethod = binding.rvMethod
        rvMethod.addItemDecoration(dividerItemDecoration)
        val favorites = getFavorites()
        val isFavorite = favorites.contains(recipe.id.toString())

        binding.ibLike.apply {
            setImageResource(if (isFavorite) R.drawable.ic_heart else R.drawable.ic_heart_empty)
            setOnClickListener {
                val newFavorites = favorites.toMutableSet()
                if (isFavorite) {
                    newFavorites.remove(recipe.id.toString())
                    setImageResource(R.drawable.ic_heart_empty)
                } else {
                    newFavorites.add(recipe.id.toString())
                    setImageResource(R.drawable.ic_heart)
                }
                saveFavorites(newFavorites)
            }
        }
    }

    private fun saveFavorites(favorites: Set<String>) {
        val sharedPrefs = context?.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        sharedPrefs?.edit()?.let { editor ->
            with(editor) {
                putStringSet(FAVORITES_KEY, favorites)
                apply()
            }
        }
    }

    private fun getFavorites(): MutableSet<String> {
        val sharedPrefs = context?.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        val favorites = sharedPrefs?.getStringSet(FAVORITES_KEY, emptySet())
        return favorites?.toMutableSet() ?: mutableSetOf()
    }
}
