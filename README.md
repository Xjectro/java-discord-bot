# 🤖 java-discord-bot

A customizable Discord bot developed with Java.

## ✨ Features
- 📝 Command-based structure  
- ⚡ Event listeners  
- 🧩 Easily extensible architecture  
- 🛠️ Modern Java and Gradle usage  

## ⚙️ Installation

### 📋 Requirements
- ☕ Java 17 or higher  
- 🛠️ [Gradle](https://gradle.org/) (or local setup with `gradlew`)  
- 🔑 A Discord bot token ([Discord Developer Portal](https://discord.com/developers/applications))  

### 🚦 Steps
1. 📥 Clone the repository:
   ```sh
   git clone https://github.com/Xjectro/java-discord-bot.git
   cd java-discord-bot

2.  📦 Install dependencies and build:

   ```sh
   ./gradlew build
   ```

3. 🔐 Add your bot token and (optionally) database connection string to the `app/.env` file. Example:

   ```env
   DISCORD_TOKEN=YOUR_DISCORD_BOT_TOKEN
   MONGO_URI=mongodb://localhost:27017
   ```

4. 🚀 Start the bot:

   ```sh
   ./gradlew run
   ```

## 🕹️ Usage

After adding the bot to your server, you can use the following commands:

### 👑 Auto Role System

* `!autorole <enabled|disabled> [@role1 @role2 ...]`

    * 🔄 Enables or disables the auto role system and sets the roles to assign.
    * 💡 Example: `!autorole enabled @Member @Registered`

* Slash command version:

    * `/autorole status:<enabled|disabled> roles:@role1,@role2,...`
    * 💡 Example: `/autorole status:enabled roles:@Member,@Registered`

> ⚠️ You need the **Manage Roles** permission to use these commands.

### 🏓 Ping Command

* `!ping` — Checks if the bot is running.

### 🛠️ Add Your Own Commands

* 🗂️ To add new commands, create a new class in the
  `app/src/main/java/org/example/commands/` directory.

## 🤝 Contributing

Contributions are welcome! Please open an issue before submitting a pull request.

## 📄 License

This project is licensed under the [MIT License](LICENSE).

## 📬 Contact

* 👤 Developer: [Xjectro](https://github.com/Xjectro)
* 👤 Developer2: [range79](https://github.com/range79)
* 🔗 Project Link: [github.com/Xjectro/java-discord-bot](https://github.com/Xjectro/java-discord-bot)

## 🚀 Architecture & Technologies Used

* ☕ **Java 17+**: Main programming language.
* 🛠️ **Gradle**: Build and project management tool.
* 🤖 **JDA (Java Discord API)**: Core library for Discord bot functionality.
* 🍃 **MongoDB**: NoSQL database for storing server-specific settings and data.
* 🟢 **Morphia**: ORM library for easy data access between Java and MongoDB.
* 🗝️ **dotenv-java**: Loads environment variables from `.env` files.

### 🏗️ Architecture

* 📝 **Command System**: All commands are in the `org.example.commands` package, making it easy to add new ones.
* ⚡ **Event System**: Handles Discord events under `org.example.events`.
* 🗄️ **Database Management**: MongoDB connection and models are under `org.example.database`.
  Server-specific settings (e.g., auto role) are stored in MongoDB.
* ⚙️ **Configuration**: Database settings are in
  `app/src/main/resources/META-INF/morphia-config.properties`,
  and the connection string is in `app/.env`.

### 🗃️ Example Database Model

Server-specific auto role settings are stored in MongoDB as follows:

* 🆔 Server ID
* 🔄 Auto role enabled/disabled status
* 🏷️ IDs of roles to assign

### 📦 Main Libraries Used

* [🤖 JDA](https://github.com/DV8FromTheWorld/JDA)
* [🟢 Morphia](https://morphia.dev/)
* [🗝️ dotenv-java](https://github.com/cdimascio/dotenv-java)
* [🍃 MongoDB Java Driver](https://mongodb.github.io/mongo-java-driver/)

