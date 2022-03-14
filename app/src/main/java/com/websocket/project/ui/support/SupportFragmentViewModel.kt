package com.websocket.project.ui.support

import android.content.Context
import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.websocket.project.R
import com.websocket.project.ui.support.SupportFragment.Companion.UPLOAD_FILE_MAX_SIZE_IN_BYTES
import com.websocket.project.ui.support.appeal_category.AppealCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SupportFragmentViewModel @Inject constructor(
): ViewModel() {

    private val _appealCategoryChosenPosition = MutableLiveData<Int>()
    val appealCategoryChosenPosition: LiveData<Int>
        get() = _appealCategoryChosenPosition

    private val _attachedFilesCount = MutableLiveData(0)
    val attachedFilesCount: LiveData<Int>
        get() = _attachedFilesCount

    private var attachedFilesSizeInBytes = 0

    var subjectOfAppeal = MutableStateFlow<String?>(null)
    var categoryOfAppeal = MutableStateFlow<String?>(null)
    var textOfAppeal = MutableStateFlow<String?>(null)

    var subjectOfAppealValid = MutableStateFlow<Boolean?>(null)
    var categoryOfAppealValid = MutableStateFlow<Boolean?>(null)
    var textOfAppealValid = MutableStateFlow<Boolean?>(null)

    val categoryError = ObservableBoolean(false)
    val subjectError = ObservableBoolean(false)
    val textOfAppealError = ObservableBoolean(false)
    val subjectOfAppealMaxSymbolsCount = ObservableField("")
    val textOfAppealMaxSymbolsCount = ObservableField("")
    val deleteAllFilesButtonVisible = ObservableBoolean(false)
    val deleteAllTextButtonVisible = ObservableBoolean(false)

    val formIsValid = combine(
        subjectOfAppeal,
        categoryOfAppeal,
        textOfAppeal
    ) { subject, category, text ->
        val subjectOfAppealIsValid = isValidText(subject)
        val categoryOfAppealIsValid = isValidText(category)
        val textOfAppealIsValid = isValidText(text)
        var formIsValid: Boolean

        subjectOfAppealValid.emit(subjectOfAppealIsValid)
        categoryOfAppealValid.emit(categoryOfAppealIsValid)
        textOfAppealValid.emit(textOfAppealIsValid)

        subjectOfAppealIsValid.let {
            categoryOfAppealIsValid.let {
                textOfAppealIsValid.let {
                    categoryError.set(!categoryOfAppealIsValid)
                    subjectError.set(!subjectOfAppealIsValid)
                    textOfAppealError.set(!textOfAppealIsValid)
                    formIsValid = subjectOfAppealIsValid && categoryOfAppealIsValid && textOfAppealIsValid
                }
            }
        }

        formIsValid
    }

    private fun isValidText(text: String?): Boolean {
        return text?.isEmpty() == false
    }

    fun setSubjectMaxSymbolsCount(context: Context, symbols: Int) {
        subjectOfAppealMaxSymbolsCount.set(context.getString(R.string.support_subject_of_appeal_max_symbols, symbols))
    }

    fun setTextOfAppealMaxSymbolsCount(context: Context, symbols: Int) {
        textOfAppealMaxSymbolsCount.set(context.getString(R.string.support_text_of_appeal_max_symbols, symbols))
        deleteAllTextButtonVisible.set(symbols > 0)
    }

    fun getSelectedAppealCategoryPosition(): Int {
        return _appealCategoryChosenPosition.value ?: -1
    }

    fun setSelectedAppealCategory(position: Int) {
        viewModelScope.launch {
            categoryOfAppeal.emit(AppealCategory.values()[position].name)
        }
        _appealCategoryChosenPosition.postValue(position)
    }

    fun getAttachedFilesCount(): Int {
        return attachedFilesCount.value ?: 0
    }

    fun increaseAttachedFilesCount() {
        _attachedFilesCount.postValue(_attachedFilesCount.value?.plus(1))
        deleteAllFilesButtonVisible.set(true)
    }

    fun checkIsSizeAvailable(fileSize: Int): Boolean {
        return if (attachedFilesSizeInBytes + fileSize < UPLOAD_FILE_MAX_SIZE_IN_BYTES) {
            attachedFilesSizeInBytes += fileSize
            true
        } else {
            false
        }
    }

    fun decreaseAttachedFilesCount(sizeInBytes: Int) {
        val attachedFilesCount = _attachedFilesCount.value?.minus(1)
        _attachedFilesCount.postValue(attachedFilesCount)
        if (attachedFilesCount != null) {
            deleteAllFilesButtonVisible.set(attachedFilesCount > 0)
        }
        attachedFilesSizeInBytes -= sizeInBytes
    }

    fun removeAllAttachedFiles() {
        _attachedFilesCount.postValue(0)
        attachedFilesSizeInBytes = 0
        deleteAllFilesButtonVisible.set(false)
    }
}