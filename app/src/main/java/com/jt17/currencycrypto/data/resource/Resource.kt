package com.jt17.currencycrypto.data.resource

sealed class Resource<out T : Any>(
    open val data: T? = null,
    open val message: String? = null
) {

    val status: Status
        get() = when (this) {
            is Success -> Status.SUCCESS
            is Loading -> Status.LOADING
            is Error -> Status.ERROR
            is Empty -> Status.EMPTY
        }

    enum class Status {
        SUCCESS,
        LOADING,
        ERROR,
        EMPTY
    }

    data class Success<out T : Any>(override val data: T) : Resource<T>(data, null)
    data class Loading<out T : Any>(override val data: T? = null) : Resource<T>(data, null)
    data class Error<out T : Any>(override val message: String?, override val data: T? = null) :
        Resource<T>(data, message)

    object Empty : Resource<Nothing>()

}