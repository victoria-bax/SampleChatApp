# Sample Chat App

This app was implemented as an excercise.

## Developing steps
### Creating minimal funcionality
I started with making basic functionality: messages list without new message functionality, coming from a hardcoded repository. I used Hilt for dependency injection, and created repositories interfaces and hardcoded implementations, in order to easily replace them later.
### Adding more step by step
As soon the PoC app started functioning, I began adding functionality, applying designs step by step, including but not limited to:
- Displaying active user name and companion user name and image;
- Using a switcher to switch between two users (Alice and Bob)
- Adding Send message input, at first storing messages in memory;
- Adding Room DB to store messages;
- Adding DataStore to store current selected user;
- Creating styles and drawables, finding open-source icon for achieving desired look.

## Selected libraries:
I tried to follow Google recommendations and use my own preferences in terms of choosing libraries.
- DI: Hilt
- DB: Room
- Image loading: Glide
- ViewBinding

## What's missing :(
- Tests. I get the importance of tests, but here I decided to skip them for the sake of timing.
- Normal user storage: I just use hardcoded Bob and Alice.
- Error handling. As I just use local storage, there is not much space to get an error, so I skipped that.
- Working with message delivery statuses. I thought about using delay to change them, but decided to keep it simpler and just set Delivered status for all outgoing messages.
- Working with timezones. As my app is purely local, I let myself be lazy and just used LocalDateTime. That would be unacceptable in production of course :)
- Pretty sweet features that make the app feel more intuitive, like Swipe-to-refresh, ripples etc
