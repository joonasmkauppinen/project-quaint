package com.newgat.quaint

import android.app.Application
import com.newgat.quaint.data.db.QuaintDatabase
import com.newgat.quaint.data.network.*
import com.newgat.quaint.data.repository.QuaintRepository
import com.newgat.quaint.data.repository.QuaintRepositoryImpl
import com.newgat.quaint.ui.activity.NewActionViewModelFactory
import com.newgat.quaint.ui.fragment.addresssearch.AddressSearchViewModelFactory
import com.newgat.quaint.ui.fragment.location.NewLocationFormViewModelFactory
import com.newgat.quaint.ui.fragment.mainfeed.MainFeedViewModelFactory
import com.newgat.quaint.ui.fragment.note.NewNoteFormViewModelFactory
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

        bind() from singleton { QuaintDatabase(instance()) }
        bind() from singleton { instance<QuaintDatabase>().locationsDao() }
        bind() from singleton { instance<QuaintDatabase>().notesDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { GooglePlacesApiService(instance()) }
        bind() from singleton { GoogleGeocodingService(instance()) }
        bind<GooglePlacesDataSource>() with singleton { GooglePlacesDataSourceImpl(instance()) }
        bind<GoogleGeocodingDataSource>() with singleton { GoogleGeocodingDataSourceImpl(instance()) }
        bind<QuaintRepository>() with singleton { QuaintRepositoryImpl(instance(), instance(), instance(), instance()) }
        bind() from provider { NewActionViewModelFactory(instance()) }
        bind() from provider { NewNoteFormViewModelFactory(instance()) }
        bind() from provider { NewLocationFormViewModelFactory(instance()) }
        bind() from provider { MainFeedViewModelFactory(instance()) }
        bind() from provider { AddressSearchViewModelFactory(instance()) }
    }

}