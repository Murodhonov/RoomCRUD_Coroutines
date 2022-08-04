package uz.umarxon.roomcrud_coroutines.viewmodels

import android.util.Patterns
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.umarxon.roomcrud_coroutines.db.entity.User
import uz.umarxon.roomcrud_coroutines.repositories.UserRepository
import uz.umarxon.roomcrud_coroutines.events.Event

class UserViewModel(private val repository: UserRepository) : ViewModel(), Observable {

    val users = repository.users
    private var isUpdateOrDelete = false
    private lateinit var userToUpdateOrDelete: User

    @Bindable
    val inputName = MutableLiveData<String?>()

    @Bindable
    val inputEmail = MutableLiveData<String?>()

    val saveOrUpdateButtonText = MutableLiveData<String>()
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()

    val message: LiveData<Event<String>>
        get() = statusMessage

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun saveOrUpdate() {

        when {
            inputName.value == null -> {
                statusMessage.value = Event("Enter user name")
            }
            inputEmail.value == null -> {
                statusMessage.value = Event("Enter user email")
            }
            !Patterns.EMAIL_ADDRESS.matcher(inputEmail.value!!).matches() -> {
                statusMessage.value = Event("Enter correct email address")
            }
            else -> {
                when {
                    isUpdateOrDelete -> {
                        userToUpdateOrDelete.name = inputName.value!!
                        userToUpdateOrDelete.email = inputEmail.value!!
                        update(userToUpdateOrDelete)
                    }
                    else -> {
                        val name = inputName.value!!
                        val email = inputEmail.value!!
                        insert(User(0, name, email))
                        inputName.value = null
                        inputEmail.value = null
                    }
                }
            }
        }
    }

    fun clearAllOrDelete() {
        if (isUpdateOrDelete) {
            delete(userToUpdateOrDelete)
        } else clearAll()
    }

    private fun insert(user: User) {
        viewModelScope.launch {
            val newRowId = repository.insert(user)
            if (newRowId>-1){
                statusMessage.value = Event("User inserted successfully $newRowId")
            }else{
                statusMessage.value = Event("Error Occurred")
            }
        }
    }

    private fun update(user: User) {
        viewModelScope.launch {
            val noOfRows = repository.update(user)
            if (noOfRows>0){
                inputName.value = null
                inputEmail.value = null
                isUpdateOrDelete = false
                saveOrUpdateButtonText.value = "Save"
                clearAllOrDeleteButtonText.value = "Clear All"
                statusMessage.value = Event("$noOfRows User updated successfully")
            }else{
                statusMessage.value = Event("Error occurred")
            }
        }
    }

    private fun delete(user: User) {
        viewModelScope.launch {
            val noOfRowsDeleted = repository.delete(user)

            if (noOfRowsDeleted>0){
                inputName.value = null
                inputEmail.value = null
                isUpdateOrDelete = false
                saveOrUpdateButtonText.value = "Save"
                clearAllOrDeleteButtonText.value = "Clear All"
                statusMessage.value = Event("$noOfRowsDeleted User deleted successfully")
            }else{
                statusMessage.value = Event("Error occurred")

            }
        }
    }

    private fun clearAll() {
        viewModelScope.launch {
            val noOfRowsDeleted = repository.deleteAll()
            if (noOfRowsDeleted>0){
                statusMessage.value = Event("$noOfRowsDeleted User deleted successfully")
            }else{
                statusMessage.value = Event("Error Occurred")
            }
        }
    }

    fun initUpdateAndDelete(user: User) {
        inputName.value = user.name
        inputEmail.value = user.email
        isUpdateOrDelete = true
        userToUpdateOrDelete = user
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }

    /**
     * Adds a callback to listen for changes to the Observable.
     * @param callback The callback to start listening.
     */
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    /**
     * Removes a callback from those listening for changes.
     * @param callback The callback that should stop listening.
     */
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}