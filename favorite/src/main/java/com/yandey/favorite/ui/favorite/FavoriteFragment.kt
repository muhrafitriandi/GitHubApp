package com.yandey.favorite.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.yandey.core.domain.model.User
import com.yandey.core.ui.UserAdapter
import com.yandey.core.utils.Constants
import com.yandey.favorite.ViewModelFactory
import com.yandey.favorite.databinding.FragmentFavoriteBinding
import com.yandey.favorite.di.DaggerFavoriteComponent
import com.yandey.githubapp.R
import com.yandey.githubapp.di.FavoriteModule
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteFragment : Fragment(), UserAdapter.ItemClickListener {

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: FavoriteViewModel by viewModels {
        factory
    }

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerFavoriteComponent.builder()
            .context(requireContext())
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireContext(),
                    FavoriteModule::class.java
                )
            ).build().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        observeFavorite()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClicked(user: User) {
        val bundle = Bundle().apply {
            requireActivity().intent.putExtra(
                Constants.EXTRA_USER,
                user.login
            )
        }
        findNavController().navigate(
            R.id.action_favoriteFragment_to_detailFragment,
            bundle
        )
    }

    private fun observeFavorite() {
        viewModel.favoriteUsers.observe(viewLifecycleOwner) { listUser ->
            userAdapter.submitList(listUser)
            if (listUser.isNotEmpty()) {
                binding.tvNoFavorite.visibility = View.INVISIBLE
                binding.ivNoFavorite.visibility = View.INVISIBLE
            } else {
                binding.tvNoFavorite.visibility = View.VISIBLE
                binding.ivNoFavorite.visibility = View.VISIBLE
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvUser.apply {
            userAdapter = UserAdapter(requireContext(), this@FavoriteFragment)
            adapter = userAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}