package jp.cordea.libros.api

import jp.cordea.libros.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior

/**
 * Created by Yoshihiro Tanaka on 2017/01/01.
 */
object LibrosClient {

    private val retrofit: Retrofit
        get() {
            val client = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) {
                client.addInterceptor(HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BASIC))
            }
            return Retrofit.Builder()
                    .baseUrl(BuildConfig.API_BASE_URL)
                    .client(client.build())
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build()
        }

    private val mockRetrofit: MockRetrofit
        get() {
            return MockRetrofit.Builder(retrofit)
                    .networkBehavior(NetworkBehavior.create())
                    .build()
        }

    val librosService: LibrosService
        get() {
            if (BuildConfig.MOCK) {
                return MockLibrosService(mockRetrofit.create(LibrosService::class.java))
            }
            return retrofit.create(LibrosService::class.java)
        }
}