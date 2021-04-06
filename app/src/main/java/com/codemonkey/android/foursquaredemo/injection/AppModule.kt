package com.codemonkey.android.foursquaredemo.injection

import android.content.Context
import com.codemonkey.android.foursquaredemo.data.local.AppDatabase
import com.codemonkey.android.foursquaredemo.data.local.VenueDetailDao
import com.codemonkey.android.foursquaredemo.data.local.VenueOverviewDao
import com.codemonkey.android.foursquaredemo.data.remote.IVenuesRemoteDataSource
import com.codemonkey.android.foursquaredemo.data.remote.VenuesRemoteDataSource
import com.codemonkey.android.foursquaredemo.data.remote.VenuesService
import com.codemonkey.android.foursquaredemo.data.repository.IVenuesRepository
import com.codemonkey.android.foursquaredemo.data.repository.VenuesRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.foursquare.com/v2/venues/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideVenuesService(retrofit: Retrofit): VenuesService =
        retrofit.create(VenuesService::class.java)

    @Singleton
    @Provides
    fun provideVenuesRemoteDataSource(venuesService: VenuesService) =
        VenuesRemoteDataSource(venuesService) as IVenuesRemoteDataSource

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideVenueOverviewDao(db: AppDatabase) = db.venueOverviewDao()

    @Singleton
    @Provides
    fun provideVenueDetailDao(db: AppDatabase) = db.venueDetailDao()

    @Singleton
    @Provides
    fun provideRepository(
        remoteDataSource: IVenuesRemoteDataSource,
        localVenueOverviewDataSource: VenueOverviewDao,
        localVenueDetailDataSource: VenueDetailDao
    ) =
        VenuesRepository(remoteDataSource, localVenueOverviewDataSource, localVenueDetailDataSource) as IVenuesRepository

}