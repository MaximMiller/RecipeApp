package com.example.recipeapp.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import com.example.recipeapp.ui.Constants.ARG_CATEGORY_ID
import com.example.recipeapp.ui.Constants.ARG_CATEGORY_IMAGE_URI
import com.example.recipeapp.ui.Constants.ARG_CATEGORY_NAME
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentListCategoriesBinding
import com.example.recipeapp.model.Category
import com.example.recipeapp.ui.recipes.listrecipes.RecipesListFragment

class CategoriesListFragment : Fragment() {
    private var currentCategory: String = "Default Category"
    private var _binding: FragmentListCategoriesBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalAccessException("Binding for FragmentListCategoriesBinding most not be null")
    private val viewModel: CategoriesListViewModel by viewModels()
    private var adapter: CategoriesListAdapter = CategoriesListAdapter(emptyList())


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
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            initRecycler(categories)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecycler(categories: List<Category>) {
        binding.rvCategories.adapter = adapter
        adapter.updateCategories(categories)
        adapter.setOnClickListener(object : CategoriesListAdapter.OnItemClickListener {
            override fun onItemClick(categoryId: Int) {
                openRecipesByCategoryId(categoryId)
            }
        })
    }

    private fun openRecipesByCategoryId(categoryId: Int) {
        val category = viewModel.getCategoryById(categoryId)
        val categoryName = category?.title
        val categoryImageUrl = category?.imageUrl
        val bundle = bundleOf(
            ARG_CATEGORY_ID to categoryId,
            ARG_CATEGORY_NAME to categoryName,
            ARG_CATEGORY_IMAGE_URI to categoryImageUrl
        )
        parentFragmentManager.commit {
            replace<RecipesListFragment>(R.id.mainContainer, args = bundle)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }
}

