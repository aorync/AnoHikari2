package com.syntxr.anohikari2.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.android.gms.location.LocationServices
import com.syntxr.anohikari2.BuildConfig
import com.syntxr.anohikari2.R
import com.syntxr.anohikari2.data.repository.AdzanRepositoryImpl
import com.syntxr.anohikari2.data.repository.BookmarkRepositoryImpl
import com.syntxr.anohikari2.data.repository.QoranRepositoryImpl
import com.syntxr.anohikari2.data.source.local.adzan.database.AdzanDatabase
import com.syntxr.anohikari2.data.source.local.bookmark.database.BookmarkDatabase
import com.syntxr.anohikari2.data.source.local.qoran.database.QoranDatabase
import com.syntxr.anohikari2.data.source.remote.service.AdzanApi
import com.syntxr.anohikari2.domain.repository.AdzanRepository
import com.syntxr.anohikari2.domain.repository.BookmarkRepository
import com.syntxr.anohikari2.domain.repository.QoranRepository
import com.syntxr.anohikari2.domain.usecase.AppUseCase
import com.syntxr.anohikari2.domain.usecase.UseCaseInteractor
import com.syntxr.anohikari2.service.location.LocationClient
import com.syntxr.anohikari2.service.location.LocationClientImpl
import com.syntxr.anohikari2.service.player.MyPlayerService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import snow.player.PlayerClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideExternalCoroutineScope(): CoroutineScope {
        return CoroutineScope(Dispatchers.IO)
    }

    @Provides
    @Singleton
    fun provideLocationClient(
        app: Application,
        coroutineScope: CoroutineScope,
    ): LocationClient {
        return LocationClientImpl(
            app,
            coroutineScope,
            LocationServices.getFusedLocationProviderClient(app.applicationContext)
        )
    }

    @Provides
    @Singleton
    fun provideQoranDatabase(
        @ApplicationContext context: Context,
    ): QoranDatabase {
        return Room.databaseBuilder(
            context,
            QoranDatabase::class.java,
            QoranDatabase.DB_NAME
        ).createFromInputStream {
            context.resources.openRawResource(R.raw.qoran)
        }
            .build()
    }

    @Provides
    @Singleton
    fun provideBookmarkDatabase(
        @ApplicationContext context: Context,
    ): BookmarkDatabase {
        return Room.databaseBuilder(
            context,
            BookmarkDatabase::class.java,
            BookmarkDatabase.DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideAdzanDatabase(
        @ApplicationContext context: Context,
    ): AdzanDatabase {
        return Room.databaseBuilder(
            context,
            AdzanDatabase::class.java,
            AdzanDatabase.DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideAdzanApi(): AdzanApi {
        val loggingInterceptor =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            else HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.ADZAN_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AdzanApi::class.java)
    }

    @Provides
    @Singleton
    fun provideQoranRepository(db: QoranDatabase): QoranRepository {
        return QoranRepositoryImpl(db.qoranDao)
    }

    @Provides
    @Singleton
    fun provideBookmarkRepository(db: BookmarkDatabase): BookmarkRepository {
        return BookmarkRepositoryImpl(db.bookmarkDao)
    }

    @Provides
    @Singleton
    fun provideAdzanRepository(
        api: AdzanApi,
        db: AdzanDatabase,
    ): AdzanRepository {
        return AdzanRepositoryImpl(api, db.dao)
    }

    @Provides
    @Singleton
    fun provideUseCase(
        qoran: QoranRepository,
        bookmark: BookmarkRepository,
        adzan: AdzanRepository,
    ): AppUseCase {
        return UseCaseInteractor(qoran, bookmark, adzan)
    }

    @Provides
    @Singleton
    fun providePlayerClient(@ApplicationContext context: Context): PlayerClient {
        return PlayerClient.newInstance(context, MyPlayerService::class.java)
    }
}