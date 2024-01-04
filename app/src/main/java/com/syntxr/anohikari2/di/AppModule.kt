package com.syntxr.anohikari2.di

import android.content.Context
import androidx.room.Room
import com.syntxr.anohikari2.R
import com.syntxr.anohikari2.data.repository.QoranRepositoryImpl
import com.syntxr.anohikari2.data.source.QoranDatabase
import com.syntxr.anohikari2.domain.repository.QoranRepository
import com.syntxr.anohikari2.domain.usecase.AppUseCase
import com.syntxr.anohikari2.domain.usecase.UseCaseInteractor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
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
    fun provideQoranRepository(db: QoranDatabase): QoranRepository {
        return QoranRepositoryImpl(db.qoranDao)
    }

    @Provides
    @Singleton
    fun provideUseCase(qoran: QoranRepository): AppUseCase{
        return UseCaseInteractor(qoran)
    }
}