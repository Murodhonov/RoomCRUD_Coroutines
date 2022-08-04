package uz.umarxon.roomcrud_coroutines.ViewModelFactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import uz.umarxon.roomcrud_coroutines.repositories.UserRepository
import uz.umarxon.roomcrud_coroutines.viewmodels.UserViewModel
import java.lang.IllegalArgumentException

class UserViewModelFactory(private val repository: UserRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)){
            return UserViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class (Class topilmadi!!)")
    }

}