package com.example.recipeapp

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.recipeapp.databinding.FragmentRecipeBinding
import com.google.android.material.divider.MaterialDividerItemDecoration


class RecipeFragment : Fragment() {
    private var _binding: FragmentRecipeBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalAccessException("Binding for FragmentRecipeBinding most not be null")

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
            initUI(it)
        } ?: run {
            Log.e("RecipeNotFound", "Recipe object not found")
        }
    }

    private fun initUI(it: Recipe) {
        binding.tvHeading.text = it.title
        Glide.with(this)
            .load("file:///android_asset/${it.imageUrl}")
            .into(binding.ivHeading)
        val rvIngredients = binding.rvIngredients
        val rvIngredientsItemDecoration = MaterialDividerItemDecoration(
            requireContext(),
            LinearLayoutManager.VERTICAL
        )
        rvIngredientsItemDecoration.isLastItemDecorated = false
        rvIngredients.addItemDecoration(rvIngredientsItemDecoration)

        val rvMethod = binding.rvMethod
        val rvMethodItemDecoration = MaterialDividerItemDecoration(
            requireContext(),
            LinearLayoutManager.VERTICAL
        )
        rvMethodItemDecoration.isLastItemDecorated = false
        rvMethod.addItemDecoration(rvMethodItemDecoration)
    }
}
