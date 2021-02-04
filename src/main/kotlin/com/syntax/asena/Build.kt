package com.syntax.asena

object Build{

    object VersionCodes{

        /**
         * API First version
         */
        private const val V1: Int = 1

        /**
         * Current development version
         */
        const val CUR_DEVELOPMENT: Int = V1

    }

    object Security{
        const val HEADER: String = "Authorization"
        const val TOKEN_PREFIX: String = "Bearer "

        const val ENDPOINT: String = "com.syntax.asena-secure.authentication"
    }

}