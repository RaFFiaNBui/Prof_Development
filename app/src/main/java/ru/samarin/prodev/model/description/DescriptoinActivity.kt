package ru.samarin.prodev.model.description

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_descriptoin.*
import ru.samarin.prodev.R
import ru.samarin.prodev.utils.AlertDialogFragment
import ru.samarin.prodev.utils.isOnline

class DescriptoinActivity : AppCompatActivity() {

    companion object {
        private const val DIALOG_TAG = "ru.samarin.prodev.model.description_DIALOG_TAG"
        private const val WORD_EXTRA = "ru.samarin.prodev.model.description_WORD_EXTRA"
        private const val DESCRIPTION_EXTRA =
            "ru.samarin.prodev.model.description_DESCRIPTION_EXTRA"
        private const val URL_EXTRA = "ru.samarin.prodev.model.description_URL_EXTRA"

        fun getIntent(
            context: Context, word: String, description: String, url: String?
        ): Intent = Intent(context, DescriptoinActivity::class.java).apply {
            putExtra(WORD_EXTRA, word)
            putExtra(DESCRIPTION_EXTRA, description)
            putExtra(URL_EXTRA, url)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_descriptoin)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        swipe_refresh_layout.setOnRefreshListener { startLoadingOrShowError() }
        setData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setData() {
        val bandle = intent.extras
        description_header.text = bandle?.getString(WORD_EXTRA)
        description_text.text = bandle?.getString(DESCRIPTION_EXTRA)
        val imageLink = bandle?.getString(URL_EXTRA)
        if (imageLink.isNullOrBlank()) {
            stopRefreshAnimation()
        } else {
            //loadFotoWithGlide(description_image, imageLink)
            loadFotoWithPicasso(description_image, imageLink)
        }
    }

    private fun startLoadingOrShowError() {
        if (isOnline(applicationContext)) {
            setData()
        } else {
            AlertDialogFragment.newInstance(
                getString(R.string.dialog_title_offline),
                getString(R.string.dialog_message_offline)
            ).show(supportFragmentManager, DIALOG_TAG)
            stopRefreshAnimation()
        }
    }

    private fun stopRefreshAnimation() {
        if (swipe_refresh_layout.isRefreshing) {
            swipe_refresh_layout.isRefreshing = false
        }
    }

    private fun loadFotoWithGlide(imageView: ImageView, imageLink: String) {
        Glide.with(imageView)
            .load("https:$imageLink")
            .listener(object : RequestListener<Drawable> {
                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    stopRefreshAnimation()
                    return false
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    stopRefreshAnimation()
                    imageView.setImageResource(R.drawable.ic_baseline_error_24)
                    return false
                }
            })
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_baseline_image_24)
                    .centerCrop()
            )
            .into(imageView)
    }

    private fun loadFotoWithPicasso(imageView: ImageView, imageLink: String) {
        Picasso.with(applicationContext)
            .load("https:$imageLink")
            .placeholder(R.drawable.ic_baseline_image_24)
            .error(R.drawable.ic_baseline_error_24)
            .fit()
            .centerCrop()
            .into(imageView, object : Callback {
                override fun onSuccess() {
                    stopRefreshAnimation()
                }

                override fun onError() {
                    stopRefreshAnimation()
                }
            })
    }
}