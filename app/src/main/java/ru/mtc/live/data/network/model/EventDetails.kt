package ru.mtc.live.data.network.model

data class EventDetails(
    val id: Int,
    val name: String,
    val description: String,
    val images: List<String>,
    val categories: List<Category>,
    val schedules: List<Schedule>,
    val persons: List<EventPerson>
)

data class Schedule(
    val id: Int,
    val dateTime: String
)

data class EventPerson(
    val person: Person,
    val post: PersonPost
)

data class Person(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val middleName: String?,
    val photo: String?
) {
    fun getName() = "$firstName $lastName"
}

data class PersonPost(
    val id: Int,
    val name: String
)