package com.vicabax.samplechatapp

import com.vicabax.samplechatapp.data.model.Message
import com.vicabax.samplechatapp.data.model.User
import java.time.LocalDateTime

@Suppress("MaxLineLength")
object HardcodedModels {
    val ALICE = User("1", "Alice")
    val BOB = User("2", "Bob")
    val STEVE = User("AndYourFriendSteve!", "Steve")

    val messages = listOf(
        Message(
            text = "Hi Bob!",
            from = ALICE,
            to = BOB,
            time = LocalDateTime.parse("2024-02-15T08:30"),
        ),
        Message(
            text = "Hello Alice. How are you?",
            from = BOB,
            to = ALICE,
            time = LocalDateTime.parse("2024-02-15T08:32"),
        ),
        Message(
            text = "I'm good! And you?",
            from = ALICE,
            to = BOB,
            time = LocalDateTime.parse("2024-02-15T08:33:10"),
        ),
        Message(
            text = "Hi, I'm Steve!",
            from = STEVE,
            to = ALICE,
            time = LocalDateTime.parse("2024-02-15T08:38")
        ),
        Message(
            text = "I'm writing a small app. Care to have a look?",
            from = ALICE,
            to = BOB,
            time = LocalDateTime.parse("2024-02-15T08:33:12"),
        ),
        Message(
            text = "Are you in there?",
            from = ALICE,
            to = BOB,
            time = LocalDateTime.parse("2024-02-15T11:43"),
        ),
        Message(
            text = "Sorry, I was busy, had an important interview. Sure, I'd be happy to look at your app! Can you share a link with me?",
            from = BOB,
            to = ALICE,
            time = LocalDateTime.parse("2024-02-15T11:43"),
        ),
    )
}
