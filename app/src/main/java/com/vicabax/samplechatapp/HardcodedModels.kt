package com.vicabax.samplechatapp

import com.vicabax.samplechatapp.data.model.Message
import com.vicabax.samplechatapp.data.model.User
import java.time.LocalDateTime
import java.util.UUID

@Suppress("MaxLineLength")
object HardcodedModels {
    val ALICE = User("1", "Alice", R.drawable.alice_avatar)
    val BOB = User("2", "Bob", R.drawable.bob_avatar)
    val STEVE = User("AndYourFriendSteve!", "Steve", null)

    val messages = listOf(
        Message(
            id = "1",
            text = "Hi Bob!",
            from = ALICE,
            to = BOB,
            time = LocalDateTime.parse("2024-02-15T08:30"),
        ),
        Message(
            id = "2",
            text = "Hello Alice. How are you?",
            from = BOB,
            to = ALICE,
            time = LocalDateTime.parse("2024-02-15T08:32"),
        ),
        Message(
            id = "3",
            text = "I'm good! And you?",
            from = ALICE,
            to = BOB,
            time = LocalDateTime.parse("2024-02-15T08:33:10"),
        ),
        Message(
            id = "4",
            text = "Hi, I'm Steve!",
            from = STEVE,
            to = ALICE,
            time = LocalDateTime.parse("2024-02-15T08:38")
        ),
        Message(
            id = "5",
            text = "I'm writing a small app. Care to have a look?",
            from = ALICE,
            to = BOB,
            time = LocalDateTime.parse("2024-02-15T08:33:12"),
        ),
        Message(
            id = "6",
            text = "Are you in there?",
            from = ALICE,
            to = BOB,
            time = LocalDateTime.parse("2024-02-15T11:43"),
        ),
        Message(
            id = "7",
            text = "Sorry, I was busy, had an important interview. Sure, I'd be happy to look at your app! Can you share a link with me?",
            from = BOB,
            to = ALICE,
            time = LocalDateTime.parse("2024-02-15T11:43"),
        ),
    )
}
