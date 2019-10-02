package com.newgat.quaint

import android.app.Application
import com.newgat.quaint.data.db.QuaintDatabse
import com.newgat.quaint.data.repository.QuaintRepository
import com.newgat.quaint.data.repository.QuaintRepositoryImpl
import com.newgat.quaint.ui.fragment.locationssection.LocationsSectionViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class QuaintApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@QuaintApplication))

        bind() from singleton { QuaintDatabse(instance()) }
        bind() from singleton { instance<QuaintDatabse>().locationsDao() }
        bind<QuaintRepository>() with singleton { QuaintRepositoryImpl(instance()) }
        bind() from provider { LocationsSectionViewModelFactory(instance()) }
    }
}