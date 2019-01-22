package com.arctouch.codechallenge.util

data class Resource(val status: Status, val message: String?) {

    companion object {
        fun error(msg: String): Resource {
            return Resource(Status.ERROR, msg)
        }

        fun loading(): Resource {
            return Resource(Status.LOADING, null)
        }

        fun success(): Resource {
            return Resource(Status.SUCCESS, null)
        }
    }
}