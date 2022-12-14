package com.yandey.githubapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.View.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.yandey.core.data.Resource
import com.yandey.core.domain.model.User
import com.yandey.core.ui.UserAdapter
import com.yandey.core.utils.Constants.EXTRA_USER
import com.yandey.githubapp.R
import com.yandey.githubapp.databinding.FragmentHomeBinding
import com.yandey.githubapp.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), UserAdapter.ItemClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private var userAdapter: UserAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        setSearchView()
        true.showDefault()
    }

    override fun onDestroyView() {
        userAdapter = null
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClicked(user: User) {
        startActivity(
            Intent(requireContext(), DetailActivity::class.java).putExtra(
                EXTRA_USER,
                user.login
            )
        )
    }

    private fun setSearchView() {
        binding.apply {
            searchView.setOnSearchClickListener {
                ivGithub.visibility = GONE
            }

            searchView.setOnCloseListener {
                ivGithub.visibility = VISIBLE
                false
            }

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(q: String): Boolean {
                    viewModel.searchUser(q)
                    false.showDefault()
                    return true
                }

                override fun onQueryTextChange(q: String?): Boolean {
                    return false
                }

            })
        }
        observeUser()
    }

    private fun initRecyclerView() {
        binding.rvUser.apply {
            userAdapter = UserAdapter(requireActivity(), this@HomeFragment)
            adapter = userAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeUser() {
        viewModel.user.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Error -> {
                    false.showLoading()
                    false.showDefault()
                    true.showEmpty()
                }
                is Resource.Loading -> {
                    true.showLoading()
                    false.showEmpty()
                }
                is Resource.Success -> {
                    false.showLoading()
                    false.showDefault()
                    false.showEmpty()
                    userAdapter?.submitList(response.data)
                }
            }
            binding.tvResultCount.text =
                getString(R.string.text_showing_results, response.data?.size ?: 0)
        }
    }

    private fun Boolean.showLoading() {
        if (this) {
            binding.progressBar.visibility = VISIBLE
        } else {
            binding.progressBar.visibility = INVISIBLE
        }
    }

    private fun Boolean.showDefault() {
        if (this) {
            binding.apply {
                ivSearchIllustration.visibility = VISIBLE
                tvSearch.visibility = VISIBLE
            }
        } else {
            binding.apply {
                ivSearchIllustration.visibility = INVISIBLE
                tvSearch.visibility = INVISIBLE
            }
        }
    }

    private fun Boolean.showEmpty() {
        if (this) {
            binding.apply {
                rvUser.visibility = GONE
                ivNoDataIllustration.visibility = VISIBLE
                tvNoData.visibility = VISIBLE
            }
        } else {
            binding.apply {
                rvUser.visibility = VISIBLE
                ivNoDataIllustration.visibility = INVISIBLE
                tvNoData.visibility = INVISIBLE
            }
        }
    }
}