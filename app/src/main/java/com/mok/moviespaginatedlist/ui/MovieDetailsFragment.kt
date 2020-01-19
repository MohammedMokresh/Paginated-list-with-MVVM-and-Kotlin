package com.mok.moviespaginatedlist.ui


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.mok.moviespaginatedlist.BuildConfig
import com.mok.moviespaginatedlist.R
import com.mok.moviespaginatedlist.databinding.FragmentMovieDetailsBinding
import com.mok.moviespaginatedlist.genres.GenreViewModel
import com.mok.moviespaginatedlist.languages.LanguagesViewModel
import com.mok.moviespaginatedlist.utils.ImageUtil
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class MovieDetailsFragment : Fragment() {
    private lateinit var binding: FragmentMovieDetailsBinding

    private val moviesViewModel: MoviesViewModel by viewModel()

    private val genreViewModel: GenreViewModel by viewModel()

    private val languagesViewModel: LanguagesViewModel by viewModel()


    companion object {
        fun newInstance(movieId: Int): MovieDetailsFragment {
            val args = Bundle()
            args.putInt("movieId", movieId)

            val fragment = MovieDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_movie_details, container, false)

        if (arguments != null) {
            moviesViewModel.getMovieById(arguments!!.getInt("movieId"))
                .observe(viewLifecycleOwner, Observer {
                    ImageUtil.renderImage(it.posterPath,
                        binding.posterImageView,
                        R.drawable.image_not_found
                    )
                    binding.movieNameTextView.text = it.originalTitle
                    binding.synopsisTextView.text = it.overview
                    binding.releaseDateTextView.text = it.releaseDate

                    if (it.originalLanguage != null)
                        languagesViewModel.getLanguageFromIso(it.originalLanguage).observe(
                            viewLifecycleOwner,
                            Observer { it -> binding.languageTextView.text = it.englishName })


                    var genres = ""

                    for (item: Int? in it.genreIds) {


                        if (item != null) {
                            genreViewModel.getGenreById(item).observe(viewLifecycleOwner, Observer {
                                genres += it.name+" "

                                binding.genresTextView.text = genres

                            })
                        }
                    }


                })

        }

        return binding.root
    }


}
