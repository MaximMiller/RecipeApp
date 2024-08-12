package com.example.recipeapp

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.recipeapp.databinding.FragmentRecipeBinding


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
        initParcelable()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initParcelable() {
        val recipe: Recipe? = if (Build.VERSION.SDK_INT >= 33) {
            arguments?.getParcelable("arg_recipe_data", Recipe::class.java)
        } else {
            arguments?.getParcelable("arg_recipe_data")
        }
        recipe?.let {
            binding.tvHeading.text = it.title

        } ?: run {
            Log.e("RecipeNotFound", "Recipe object not found")
        }
    }
}