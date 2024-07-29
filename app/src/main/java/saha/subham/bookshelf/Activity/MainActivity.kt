package saha.subham.bookshelf.Activity

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import saha.subham.bookshelf.Api.ApiInterface
import saha.subham.bookshelf.Api.ApiUtilities
import saha.subham.bookshelf.Fragment.BookListFragment
import saha.subham.bookshelf.MyApplication
import saha.subham.bookshelf.R
import saha.subham.bookshelf.Repository.BookRepository
import saha.subham.bookshelf.ViewModel.BookViewModel
import saha.subham.bookshelf.ViewModel.BooksViewModelFactory
import saha.subham.bookshelf.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
        val background: Drawable? = ContextCompat.getDrawable(this, R.drawable.background)
        window.setBackgroundDrawable(background)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}