import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ChatApiService {
    @POST("v1/chat/completions")
    fun getChatResponse(
        @Body request: GeminiRequest,
        @Header("Authorization") auth: String = "Bearer AIzaSyCfjjedKE5d8Jt6tZZ6ZGmz1Swk5Iug2zM"
    ): Call<GeminiResponse>
}
