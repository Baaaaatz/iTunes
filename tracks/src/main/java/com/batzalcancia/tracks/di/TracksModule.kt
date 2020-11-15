package com.batzalcancia.tracks.di

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.room.Room
import com.batzalcancia.tracks.data.api.ItunesTracksApi
import com.batzalcancia.tracks.data.local.ItunesTrackLocalSource
import com.batzalcancia.tracks.data.local.dao.ItunesTracksDao
import com.batzalcancia.tracks.data.local.database.ItunesTracksDatabase
import com.batzalcancia.tracks.data.remote.ItunesTrackRemoteSource
import com.batzalcancia.tracks.data.remote.ItunesTrackRemoteSourceImpl
import com.batzalcancia.tracks.data.repositories.ItunesTracksRepositoryImpl
import com.batzalcancia.tracks.domain.repositories.ItunesTracksRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
@OptIn(ExperimentalSerializationApi::class)
object TracksModule {

    @Singleton
    @Provides
    fun providesDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        context.createDataStore(
            name = "Tracks_Data_Store"
        )

    @Singleton
    @Provides
    fun providesItunesTracksLocalSource(dataStore: DataStore<Preferences>) =
        ItunesTrackLocalSource(dataStore)

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(120, TimeUnit.SECONDS)
        .readTimeout(120, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://itunes.apple.com/")
        .client(okHttpClient)
        .addConverterFactory(Json {
            ignoreUnknownKeys = true
        }.asConverterFactory("application/json".toMediaType()))
        .build()

    @Singleton
    @Provides
    fun providesItunesTracksApi(retrofit: Retrofit) = retrofit.create(ItunesTracksApi::class.java)

    @Singleton
    @Provides
    fun providesItunesTrackRemoteSource(api: ItunesTracksApi): ItunesTrackRemoteSource =
        ItunesTrackRemoteSourceImpl(api)

    @Singleton
    @Provides
    fun providesTracksDatabase(@ApplicationContext app: Context) =
        Room.databaseBuilder(app, ItunesTracksDatabase::class.java, "iTunes Tracks").build()

    @Singleton
    @Provides
    fun providesItunesTracksDao(database: ItunesTracksDatabase) = database.getItunesTracksDao()

    @Singleton
    @Provides
    fun providesItunesTrackRepository(
        itunesTracksRemoteSource: ItunesTrackRemoteSource,
        itunesTracksDao: ItunesTracksDao,
        itunesTrackLocalSource: ItunesTrackLocalSource
    ): ItunesTracksRepository =
        ItunesTracksRepositoryImpl(itunesTracksRemoteSource, itunesTracksDao, itunesTrackLocalSource)

}