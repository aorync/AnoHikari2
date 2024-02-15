package com.syntxr.anohikari2.di

import android.content.Context
import androidx.room.Room
import com.syntxr.anohikari2.R
import com.syntxr.anohikari2.data.repository.BookmarkRepositoryImpl
import com.syntxr.anohikari2.data.repository.QoranRepositoryImpl
import com.syntxr.anohikari2.data.source.local.bookmark.BookmarkDatabase
import com.syntxr.anohikari2.data.source.local.qoran.QoranDatabase
import com.syntxr.anohikari2.domain.repository.BookmarkRepository
import com.syntxr.anohikari2.domain.repository.QoranRepository
import com.syntxr.anohikari2.domain.usecase.AppUseCase
import com.syntxr.anohikari2.domain.usecase.UseCaseInteractor
import com.syntxr.anohikari2.service.player.MyPlayerService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import snow.player.PlayerClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideQoranDatabase(
        @ApplicationContext context: Context
    ) : QoranDatabase {
        return Room.databaseBuilder(
            context,
            QoranDatabase::class.java,
            QoranDatabase.DB_NAME
        ).createFromInputStream{
            context.resources.openRawResource(R.raw.qoran)
        }
            .build()
    }

    @Provides
    @Singleton
    fun provideBookmarkDatabase(
        @ApplicationContext context: Context
    ) : BookmarkDatabase {
        return Room.databaseBuilder(
            context,
            BookmarkDatabase::class.java,
            BookmarkDatabase.DB_NAME
        ).build()
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
    fun provideUseCase(qoran: QoranRepository, bookmark: BookmarkRepository): AppUseCase{
        return UseCaseInteractor(qoran, bookmark)
    }

    @Provides
    @Singleton
    fun providePlayerClient(@ApplicationContext context: Context) : PlayerClient {
        return PlayerClient.newInstance(context, MyPlayerService::class.java)
    }
}