package uz.gita.bookappShoxruh.di
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.bookappShoxruh.navigation.AppNavigation
import uz.gita.bookappShoxruh.navigation.NavigationDispatcher
import uz.gita.bookappShoxruh.navigation.NavigationHandler


@Module
@InstallIn(SingletonComponent::class)
interface NavigatorModule {

    @Binds
    fun appNavigator(dispatcher: NavigationDispatcher): AppNavigation

    @Binds
    fun navigationHandler(dispatcher: NavigationDispatcher): NavigationHandler
}