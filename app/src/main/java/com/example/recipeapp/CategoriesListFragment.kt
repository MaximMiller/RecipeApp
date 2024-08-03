package com.example.recipeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.recipeapp.databinding.FragmentListCategoriesBinding

class CategoriesListFragment : Fragment() {
    private var currentCategory: String = "Default Category"
    private var _binding: FragmentListCategoriesBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalAccessException("Binding for FragmentListCategoriesBinding most not be null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListCategoriesBinding.inflate(inflater, container, false)
        val view = binding.root
        val contentDescription = getString(R.string.iv_image_of_the_categories, currentCategory)
        binding.ivHeading.contentDescription = contentDescription
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
        val adapter = CategoriesListAdapter(STUB.getCategories())
        binding.rvCategories.adapter = adapter
        adapter.setOnClickListener(object : CategoriesListAdapter.OnItemClickListener {
            override fun onItemClick() {
                openRecipesByCategoryId()
            }
        })
    }

    private fun openRecipesByCategoryId() {
        parentFragmentManager.commit {
            add<RecipesListFragment>(R.id.mainContainer)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
        parentFragmentManager.commit {
            replace<RecipesListFragment>(R.id.mainContainer)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }
}
