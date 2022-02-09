package com.example.basehelpers.data.model

class SampleModel {

    class ApiError(message: String) : Exception(message) {

    }

    internal class NoConnectivityException(message: String) : Exception(message) {

    }
}
