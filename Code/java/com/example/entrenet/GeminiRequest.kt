//data class GeminiRequest(val contents: List<com.example.entrenet.Content>)
//data class Content(val parts: List<Part>)
//data class Part(val text: String)

data class GeminiRequest(val contents: List<com.example.entrenet.Content>)
data class Content(val parts: List<Part>)
data class Part(val text: String)

data class GeminiResponse(val candidates: List<Candidate>)
data class Candidate(val content: Content)
