data class Event(
    val title: String,
    val description: String? = null,
    val daypart: Daypart,
    val timeEvent: Int
)

enum class Daypart{
    MORNING, AFTERNOON, EVENING
}

fun main() {
    val e = Event(
        title = "Study Kotlin",
        description = "Commit to studying Kotlin at least 15 minutes per day.",
        daypart = Daypart.EVENING,
        timeEvent = 15
    )
    println(e)
}