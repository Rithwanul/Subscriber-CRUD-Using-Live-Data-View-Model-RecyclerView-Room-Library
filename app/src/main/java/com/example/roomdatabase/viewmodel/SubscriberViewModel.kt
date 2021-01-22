package com.example.roomdatabase.viewmodel

import android.content.Context
import android.util.Patterns
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdatabase.R
import com.example.roomdatabase.db.Entity.Subscriber
import com.example.roomdatabase.repository.SubscriberRepository
import com.example.roomdatabase.utilis.Event
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class SubscriberViewModel(
        private val subscriberRepository: SubscriberRepository,
        private val context: Context
    ) : ViewModel(),
    Observable {

    val subscribers = subscriberRepository.subscribers
    private var isUpdateOrDelete = false
    private lateinit var subscriberToUpdateOrDelete : Subscriber

    @Bindable
    val inputName  =  MutableLiveData<String>()

    @Bindable
    val inputEmail =  MutableLiveData<String>()

    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()

    @Bindable
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()

    val message : LiveData<Event<String>>
        get() = statusMessage

    init {
        saveOrUpdateButtonText.value = context.resources.getString(R.string.text_save)
        clearAllOrDeleteButtonText.value = context.resources.getString(R.string.text_clear_all)
    }

    fun saveOrUpdate(){

        if (inputName.value == null){
            statusMessage.value = Event(context.resources.getString(R.string.enter_name))
        }else if (inputEmail.value == null){
            statusMessage.value = Event(context.resources.getString(R.string.enter_email))
        }else if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.value!!).matches()){
            statusMessage.value = Event(context.resources.getString(R.string.enter_correct_email))
        }else{
            if (isUpdateOrDelete){
                subscriberToUpdateOrDelete.name = inputName.value!!
                subscriberToUpdateOrDelete.email = inputEmail.value!!
                update(subscriberToUpdateOrDelete)
            }else{
                val name = inputName.value!!
                val email = inputEmail.value!!

                insert(Subscriber(0, name, email))
                inputName.value = null
                inputEmail.value = null
            }
        }
    }

    fun clearAllOrDeleteAll(){
        if (isUpdateOrDelete){
            delete(subscriberToUpdateOrDelete)
        }else{
            deleteAll()
        }
    }

    fun insert(subscriber : Subscriber) : Job = viewModelScope.launch {
        val result = subscriberRepository.insert(subscriber)
        if (result > -1){
            statusMessage.value = Event(context.resources.getString(R.string.subscriber_inserted))
        }else{
            statusMessage.value = Event(context.resources.getString(R.string.subscriber_inserted_error))
        }

    }

    fun update(subscriber : Subscriber) : Job = viewModelScope.launch {
        val result = subscriberRepository.update(subscriber)
        if (result > 0){
            inputName.value                  =      null
            inputEmail.value                 =      null
            isUpdateOrDelete                 =      false
            saveOrUpdateButtonText.value     =      context.resources.getString(R.string.text_save)
            clearAllOrDeleteButtonText.value =      context.resources.getString(R.string.text_clear_all)
            statusMessage.value              = Event(context.resources.getString(R.string.subscriber_updated))
        }else{
            statusMessage.value  =  Event(context.resources.getString(R.string.subscriber_updated_error))
        }

    }

    fun delete(subscriber : Subscriber) : Job = viewModelScope.launch {
        val result = subscriberRepository.delete(subscriber)
        if (result > 0) {
            inputName.value                  =      null
            inputEmail.value                 =      null
            isUpdateOrDelete                 =      false
            saveOrUpdateButtonText.value     =      context.resources.getString(R.string.text_save)
            clearAllOrDeleteButtonText.value =      context.resources.getString(R.string.text_clear_all)
            statusMessage.value              =      Event(context.resources.getString(R.string.subscriber_deleted))
        }else{
            statusMessage.value = Event(context.resources.getString(R.string.subscriber_deleted_error))
        }

    }

    fun deleteAll() : Job = viewModelScope.launch {
        val result = subscriberRepository.deleteAll()
        if (result > 0){
            statusMessage.value     =       Event(context.resources.getString(R.string.subscriber_all_deleted))
        }else{
            statusMessage.value     =       Event(context.resources.getString(R.string.subscriber_all_deleted_error))
        }

    }

    fun initUpdateAndDelete(subscriber: Subscriber){
        inputName.value                  =      subscriber.name
        inputEmail.value                 =      subscriber.email
        isUpdateOrDelete                 =      true
        subscriberToUpdateOrDelete       =      subscriber
        saveOrUpdateButtonText.value     =      context.resources.getString(R.string.text_update)
        clearAllOrDeleteButtonText.value =      context.resources.getString(R.string.text_delete)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}