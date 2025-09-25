data class Event(
    val title: String,
    val description: String? = null,
    val event: String,
    val timeEvent: Int
)

fun main() {
    val e = Event(
        title = "Study Kotlin",
        description = "Commit to studying Kotlin at least 15 minutes per day.",
        event = "Evening",
        timeEvent = 15
    )
    println(e)
}
