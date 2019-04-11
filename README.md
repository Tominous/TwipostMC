# TwipostMC
**Spigot Plugin**  
Post a tweet from chat console of Minecraft  
Notice a player login or logout on Twitter

## Functions
- Tweet – Link a Minecraft user UUID to a Twitter account, tweet from the chat console. You cannot see timeline, retweet and send DM etc.
- Login / logout notification – Detect user login / logout and automatically tweet. A notification delay time can be set to prevent continue notification of the same user.


## Features
- Send a tweet including screenshots or images.
- Overall highly configurable and customizable.


## Requirement
- Twitter account

## Commands
- `/tw` – Base command
- `/tw register` – Attempts to link Minecraft user UUID and Twitter account. To link, access the presented URL and press the link button.
- `/tw pin <PIN code>` – Attempts to enter the PIN code displayed at the link destination of the register command.
- `/tw post <message | “tweet message”>` – Attempts to post tweet message. Link with Twitter account is required with register and pin command.
- `/tw notification` – Attempts to switch on / off the login / logout notification.
- `/tw delaytime <delay time>` - Attempts to set notification delay time (seconds).
- `/tw debug` – Attempts to switch debug mode. On debug mode, debug messages are flowed at the server console.

## Permissions
- `tw.*` - Give access to all TwipostMC functions
- `tw.user` - Give access to TwipostMC functions for user
- `tw.register` – Allows /tw regster
- `tw.pin` – Allows /tw ping
- `tw.notification` – Allows /tw notification
- `tw.delaytime` – Allows /tw delaytime
- `tw.debug` – Allows /tw debug

## Confituration
Configure settings in config.yml
Refer to [config.yml](https://github.com/kanakiyo314/TwipostMC/blob/master/src/main/resources/config.yml)

## Installation
Copy TwipostMC-*.jar to plugins directory

## Licence
[MIT](https://github.com/kanakiyo314/TwipostMC/blob/master/LICENSE)

## Author
[kanakiyo314](https://github.com/kanakiyo314)
