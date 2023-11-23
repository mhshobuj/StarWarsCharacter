package com.mhs.starwarscharacter.utils


data class DataStatus<out T>(val status: Status, val data: T? = null, val message: String? = null) {
    /**
     * Enum representing the possible states of an asynchronous operation.
     */
    enum class Status {
        LOADING, SUCCESS, ERROR
    }

    /**
     * Companion object providing convenient factory methods for creating instances of DataStatus.
     */
    companion object {
        /**
         * Creates a DataStatus instance representing a loading state.
         */
        fun <T> loading(): DataStatus<T> {
            return DataStatus(Status.LOADING)
        }

        /**
         * Creates a DataStatus instance representing a successful operation with the given data.
         *
         * @param data The result data of the successful operation.
         */
        fun <T> success(data: T?): DataStatus<T> {
            return DataStatus(Status.SUCCESS, data)
        }

        /**
         * Creates a DataStatus instance representing an error state with the given error message.
         *
         * @param error A descriptive error message explaining the failure.
         */
        fun <T> error(error: String): DataStatus<T> {
            return DataStatus(Status.ERROR, message = error)
        }
    }
}