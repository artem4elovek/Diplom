package com.example.fizikaforall.fragments.contract

import android.database.sqlite.SQLiteDatabase
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import com.example.fizikaforall.db_Projects.Repositories

typealias ResultListener<T> = (T) -> Unit

fun Fragment.navigator(): Navigator {
        return requireActivity() as Navigator
}

interface Navigator {
        fun showHelperScreen()
        fun showDrawingWorkbench(id: Int)
        fun goBack()
        fun goToArchive()
        fun<T: Parcelable> publishResult(result: T)
        fun <T: Parcelable> listenResult(clazz: Class<T>,owner:LifecycleObserver, listener: ResultListener<T>)
        fun getRepository(): Repositories
}