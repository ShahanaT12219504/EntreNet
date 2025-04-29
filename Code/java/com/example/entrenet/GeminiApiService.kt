import GeminiRequest
import GeminiResponse
//import retrofit2.Call
//import retrofit2.http.Body
//import retrofit2.http.POST
//import retrofit2.http.Query

//interface GeminiApiService {
//    @POST("v1beta/models/gemini-pro:generateContent")
//    fun generateContent(
//        @Query("key") apiKey: String,
//        @Body request: GeminiRequest
//    ): Call<GeminiResponse>
//}

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface GeminiApiService {
    @POST("v1beta/models/gemini-2.0-flash:generateContent")
    fun generateContent(
        @Query("key") apiKey: String,
        @Body request: GeminiRequest
    ): Call<GeminiResponse>
}

