package com.example.fizikaforall

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleObserver
import com.example.fizikaforall.databinding.ActivityMainBinding
import com.example.fizikaforall.fragments.ArchiveFragment
import com.example.fizikaforall.fragments.DrawingWorkbenchFragment
import com.example.fizikaforall.fragments.HelperFragment
import com.example.fizikaforall.fragments.contract.*

class MainActivity : AppCompatActivity(),Navigator {

    private lateinit var binding: ActivityMainBinding

    private val currentFragment: Fragment
        get()= supportFragmentManager.findFragmentById(R.id.fragmentContainer)!!

    private val fragmentListener = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            updateUi()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        if (savedInstanceState == null) {
        supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, ArchiveFragment())
                .commit()
        }
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, false)

    }

    fun getScreensCount():Int{
        return supportFragmentManager.backStackEntryCount+1
    }

    override fun showHelperScreen() {
        launchFragment(HelperFragment())
    }

    override fun showDrawingWorkbench(id: Long) {
        launchFragment(DrawingWorkbenchFragment.newInstance(
            counterValue = id
        ))
    }

    override fun goBack() {
       onBackPressed()
    }

    override fun goToArchive() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    override fun <T : Parcelable> publishResult(result: T) {
        TODO("Not yet implemented")
    }
    override fun <T : Parcelable> listenResult(
        clazz: Class<T>,
        owner: LifecycleObserver,
        listener: ResultListener<T>
    ) {
        TODO("Not yet implemented")
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
         updateUi()
         return true
     }

     private fun updateUi() {
         //Toast.makeText(this,supportFragmentManager.backStackEntryCount.toString(),Toast.LENGTH_LONG).show()
         val fragment = currentFragment
         if (fragment is HasCustomTitle) {
             binding.toolbar.title = getString(fragment.getTitleRes())
         } else {
             binding.toolbar.title = getString(R.string.app_name)
         }

         if (supportFragmentManager.backStackEntryCount > 0) {
             supportActionBar?.setDisplayHomeAsUpEnabled(true)
             supportActionBar?.setDisplayShowHomeEnabled(true)
         } else {
             supportActionBar?.setDisplayHomeAsUpEnabled(false)
             supportActionBar?.setDisplayShowHomeEnabled(false)
         }

         if (fragment is HasCustomAction) {
             createCustomToolbarAction(fragment.getCustomAction())
         } else {
             binding.toolbar.menu.clear()
         }
     }


     private fun createCustomToolbarAction(actions: List<CustomAction>) {
        // binding.toolbar.menu.clear()

         actions.forEach{
            var action = it
             val iconDrawable =
                 DrawableCompat.wrap(ContextCompat.getDrawable(this, action.iconRes)!!)
             iconDrawable.setTint(Color.WHITE)

             val menuItem = binding.toolbar.menu.add(action.textRes)
             menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
             menuItem.icon = iconDrawable
             menuItem.setOnMenuItemClickListener {
                 action.onCustomAction.run()
                 return@setOnMenuItemClickListener true
             }
         }
     }


    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentListener)
    }
}