package com.yandey.githubapp.ui.detail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.yandey.core.data.Resource
import com.yandey.core.domain.model.User
import com.yandey.core.utils.Constants.EXTRA_USER
import com.yandey.core.utils.loadImage
import com.yandey.core.utils.showSnackBar
import com.yandey.core.utils.toShortNumber
import com.yandey.githubapp.R
import com.yandey.githubapp.databinding.ActivityDetailBinding
import com.yandey.githubapp.ui.follows.FollowsPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val viewModel: DetailViewModel by viewModels()

    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USER)
        viewModel.setDetailUser(username.toString())
        observeUserDetail(username.toString())
        openGithub(getString(R.string.github_url, username.toString()))
        shareGithub(getString(R.string.text_hello_there, username.toString()))

        binding.ivBack.setOnClickListener {
            finish()
        }

        followsPager()
        onClickFavorite()
    }

    private fun observeUserDetail(username: String) {
        viewModel.detailUser.observe(this) { response ->
            when (response) {
                is Resource.Error -> {
                    false.showLoading()
                }
                is Resource.Loading -> {
                    true.showLoading()
                }
                is Resource.Success -> {
                    false.showLoading()
                    viewModel.getFavoriteStateUser(username).observe(this) {
                        isFavorite = it.isFavorite == true
                        setDrawableFavorite(isFavorite)
                    }
                    setDataUserDetail(response)
                }
            }
        }
    }

    override fun onDestroy() {
        Glide.get(this).clearMemory()
        super.onDestroy()
    }

    private fun Boolean.showLoading() {
        if (this) {
            binding.progressBar.visibility = VISIBLE
        } else {
            binding.progressBar.visibility = INVISIBLE
        }
    }

    private fun openGithub(username: String) {
        binding.btnOpenOnGitHub.setOnClickListener {
            Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(username)
            }.also {
                startActivity(it)
            }
        }
    }

    private fun shareGithub(username: String) {
        binding.ivShare.setOnClickListener {
            val sub = getString(R.string.text_github_connection)
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_SUBJECT, sub)
            intent.putExtra(Intent.EXTRA_TEXT, username)
            startActivity(Intent.createChooser(intent, getString(R.string.text_share_via)))
        }
    }

    private fun setDataUserDetail(user: Resource<User>) {
        binding.apply {
            tvUsername.text = user.data?.login ?: "-"
            ivUser.loadImage(this@DetailActivity, user.data?.avatar_url)
            tvTotalRepository.text = user.data?.public_repos.toString().toShortNumber()
            tvTotalFollowers.text = user.data?.followers.toString().toShortNumber()
            tvTotalFollowing.text = user.data?.following.toString().toShortNumber()
            tvName.text = user.data?.name ?: "-"
            tvBio.text = user.data?.bio ?: "-"
            tvCompany.text = user.data?.company ?: "-"
            tvLocation.text = user.data?.location ?: "-"
            tvBlog.text = (if (user.data?.blog == "") "-" else user.data?.blog)
        }
    }

    private fun followsPager() {
        val followsPagerAdapter = FollowsPagerAdapter(this)
        binding.viewPager.adapter = followsPagerAdapter
        binding.tabs.let {
            binding.viewPager.let { it1 ->
                TabLayoutMediator(it, it1) { tab, position ->
                    tab.text = resources.getString(TAB_TITLES_FOLLOWS[position])
                }.attach()
            }
        }
    }

    private fun favoriteStatus() = if (!isFavorite) {
        viewModel.detailUser.observe(this) { response ->
            response.data?.let {
                it.isFavorite = !isFavorite
                viewModel.insertUser(it)
                isFavorite = !isFavorite
            }
        }
        showSnackBar(binding.root, getString(R.string.text_success_added))
    } else {
        viewModel.detailUser.observe(this) { response ->
            response.data?.let {
                it.isFavorite = !isFavorite
                viewModel.deleteUser(it)
                isFavorite = !isFavorite
            }
        }
        showSnackBar(binding.root, getString(R.string.text_deleted_success))
    }

    private fun setDrawableFavorite(status: Boolean) = if (status) {
        binding.fabFavorite.setImageResource(R.drawable.ic_favorite)
    } else {
        binding.fabFavorite.setImageResource(R.drawable.ic_favorite_border)
    }

    private fun onClickFavorite() {
        setDrawableFavorite(isFavorite)
        binding.fabFavorite.setOnClickListener {
            favoriteStatus()
            setDrawableFavorite(isFavorite)
        }
    }

    companion object {
        val TAB_TITLES_FOLLOWS = intArrayOf(
            R.string.text_followers,
            R.string.text_following
        )
    }
}