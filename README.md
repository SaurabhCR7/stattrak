# Stattrak

**Valorant Discord Bot**

Stattrak is a Discord bot written in Scala that keeps you up-to-date with your Valorant account by sending notifications for:

* **Rank updates:** Get notified instantly when your rank changes, so you can celebrate your victories and strategize for your next climb.
* **Match updates:** Never miss a match update again! Receive notifications for match wins, losses, and key performance stats.
* **Patch notes:** Stay informed about the latest Valorant updates with patch note notifications delivered directly to your Discord server.

**Invite the discord bot:**
[Invite Link](https://discord.com/api/oauth2/authorize?client_id=876016856555728906&permissions=1084479764544&scope=bot)


**Technology Stack:**

* **Programming Language:** Scala
* **Build Tool:** sbt
* **Database:** ScyllaDB
* **Design Patterns:** Observer, Singleton

## Getting Started:

1. **Clone the repository:**
    ```bash
    git clone https://github.com/SaurabhCR7/stattrak.git
    ```

2. **Download required libraries:**
    ```bash
    sbt update
    ```

3. **Configure the bot:**
    ```bash
    Create a token.env in the root directory of the project.
    Add the discord bot token in this file.
    ```

4. **Start ScyllaDB container in docker:**
    ```bash
    docker run --name scylla -p 9042:9042 -d scylladb/scylla
    ```

5. **Run the bot:**
    ```bash
    sbt run
    ```

6. **Add the bot to your Discord server:**
    ```bash
    Create a bot application in the Discord Developer Portal and copy its url.
    Use the url to invite the bot to your server.
    Follow the bot's instructions to link your Valorant account and start receiving notifications!
    ```

## Commands:
1. **Subscribe:**
``` bash
>subscribe username#tag
```
![ScreenRecording2024-02-13at9 28 05PM-ezgif com-video-to-gif-converter](https://github.com/SaurabhCR7/stattrak/assets/19225940/bc9fb906-e7c3-4c78-b57b-ed65b3972058)

2. **Unsubscribe:**
``` bash
>unsubscribe username#tag
```
![ScreenRecording2024-02-13at9 33 05PM-ezgif com-video-to-gif-converter](https://github.com/SaurabhCR7/stattrak/assets/19225940/3f3c7e3b-5ad7-4b70-b220-825aa4035d1e)

## Contributing:

We encourage contributions to Stattrak! If you're interested in helping out, please raise a pull request with a meaningful description.
