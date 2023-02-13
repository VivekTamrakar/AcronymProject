package com.example.acronymproject.api

import com.example.acronymproject.models.Acronym
import retrofit2.Response
import java.io.IOException

/**
 * This class fetches response for Acronym provided by user.
 */

class APIResponse<T>(private val success: Boolean, var body: T, var errorMessage: String?) {

    fun isSuccess(): Boolean {
        return success
    }

    fun setSuccess(success: Boolean) {
        var success = success
        success = success
    }

    companion object {
        fun <T> forErrorMessage(errorMessage: String?): APIResponse<T?> {
            return APIResponse(false, null, errorMessage)
        }

        fun <T> forErrorMessage(throwable: Throwable?): APIResponse<T?> {
            return APIResponse(false, null, throwable?.message)
        }

        @JvmStatic
        fun <T> forErrorMessage(response: Response<T>?): APIResponse<T?> {
            var errorMessage: String? = "Unknown"
            errorMessage = try {
                response?.errorBody()?.string()
            } catch (e: IOException) {
                e.printStackTrace()
                response?.message()
            }
            return APIResponse(false, null, errorMessage)
        }

        @JvmStatic
        fun <T> forSuccess(body: T): APIResponse<T?> {
            return APIResponse(true, body, "")
        }
    }
}