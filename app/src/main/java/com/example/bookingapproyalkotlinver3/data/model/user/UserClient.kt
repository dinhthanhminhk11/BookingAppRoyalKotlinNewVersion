package com.example.bookingapproyalkotlinver3.data.model.user

object UserClient {
    var id: String? = null
    var name: String? = null
    var email: String? = null
    var image: String? = null
    var phone: String? = null
    var address: String? = null
    var countBooking: Int = 0

    fun setUserFromUser(user: User) {
        id = user.id
        name = user.name
        email = user.email
        image = user.image
        phone = user.phone
        address = user.address
        countBooking = user.countBooking
    }
}