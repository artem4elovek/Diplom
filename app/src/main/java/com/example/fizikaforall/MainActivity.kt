package com.example.fizikaforall

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleObserver
import com.example.fizikaforall.databinding.ActivityMainBinding
import com.example.fizikaforall.fragments.ArchiveFragment
import com.example.fizikaforall.fragments.HelperFragment
import com.example.fizikaforall.fragments.contract.Navigator
import com.example.fizikaforall.fragments.contract.ResultListener

class MainActivity : AppCompatActivity(),Navigator {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)


        if (savedInstanceState == null){
           /* val fragment= ArchiveFragment.newInstance(
                counterValue = 1,
                quote = "adfasdfadsfadsfadsfasdfasdfasdfadsfasdfasdfadsfa"
            )*/
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer,ArchiveFragment())
                .commit()

        }
    }
    fun createQuote(): String{
        return "askdjfhkjahdsf"
    }
    fun getScreensCount():Int{
        return supportFragmentManager.backStackEntryCount+1
    }

    override fun showHelperScreen() {
        TODO("Not yet implemented")
    }


    override fun showCongratulationsScreen() {
        TODO("Not yet implemented")
    }

    override fun goBack() {
       onBackPressed()
    }

    override fun goToMenu() {
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

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

}