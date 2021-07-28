package com.kppmining.core.data

import com.kppmining.core.utils.Status

data class Resource<T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> = Resource(Status.SUCCESS, data, null)
        fun <T> loading(): Resource<T> = Resource(Status.LOADING, null, null)
        fun <T> error(msg: String?): Resource<T> = Resource(Status.ERROR, null, msg)
    }
}