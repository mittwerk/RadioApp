package space.mittwerk.radioapp.di

import android.content.Context
import com.bumble.appyx.navigation.modality.NodeContext
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import space.mittwerk.radioapp.dispatcher.ContextProvider
import space.mittwerk.radioapp.dispatcher.ContextProviderImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideContextProvider(): ContextProvider = ContextProviderImpl()

    @Provides
    @Singleton
    fun provideApplicationContext(
        @ApplicationContext appContext: Context,
    ): Context = appContext

    @Provides
    @Singleton
    fun provideNodeContext(
        @ApplicationContext nodeContext: NodeContext,
    ): NodeContext = nodeContext
}
