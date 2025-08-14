# Game Central

## Authors and Contributors

This project was created by the **Game Central Team**.

## Project Overview

### What is Game Central?

Game Central is a desktop application for video game enthusiasts. It allows users to discover new games, keep track of their personal game library and wishlist, get personalized recommendations, and connect with other users.

### Why was this project made?

This project was developed to provide a centralized platform for gamers to manage their gaming life. It solves the problem of having game libraries, wishlists, and social interactions scattered across different platforms by bringing them all into one convenient application.

-----

## Table of Contents

* [Features](https://github.com/Kushallsaraf/CSC207-Group-Project)
* [Installation](https://github.com/Kushallsaraf/CSC207-Group-Project)
* [Usage Guide](https://github.com/Kushallsaraf/CSC207-Group-Project)
* [License](https://github.com/Kushallsaraf/CSC207-Group-Project)
* [Feedback](https://github.com/Kushallsaraf/CSC207-Group-Project)
* [Contributing](https://github.com/Kushallsaraf/CSC207-Group-Project)

-----

## Features

Game Central comes with a wide range of features designed for gamers:

* **User Authentication**: Secure user login and registration system.
* **Game Discovery**:
    * Search for games by **name**.
    * Search for games by **genre**.
* **Personalized Profiles**:
    * Maintain a personal **game library** and **wishlist**.
    * Customize your profile with a **bio** and **profile picture**.
* **Social Connectivity**:
    * **Follow** other users to see their profiles and activity.
    * View your **followers** and the users you are **following**.
* **Game Recommendations**: A recommendation engine that suggests new games based on your activity.
* **Gaming News Feed**: Stay up-to-date with the latest news in the gaming world.
* **Detailed Game Pages**:
    * View game **achievements**.
    * Browse game **screenshots**.
    * Find links to buy the game from various **online stores**.
    * Read and write **reviews** for games.

-----

## Installation

### Requirements

To run Game Central, you will need the following installed on your system:

* **Java Development Kit (JDK)**: Version 11 or higher.
* **JavaFX**: The application is built using JavaFX for the user interface.
* **Maven** or **Gradle**: To manage project dependencies.

### Dependencies

The project uses the following external libraries, which will be automatically downloaded by your build tool:

* **OkHttp**: For handling HTTP requests.
* **jBCrypt**: For password hashing and security.
* **Unirest**: For making API calls.
* **Jackson**: For parsing JSON data from APIs.

### API Keys

The application requires API keys for the following services:

* **RAWG API**: For game data, screenshots, and achievements.
* **IGDB API**: For game details and recommendations.
* **NewsAPI**: For the gaming news feed.

You will need to obtain your own API keys from these services and place them in the appropriate files (e.g., `RawgApiClient.java`, `IgdbApiClient.java`, `NewsClient.java`).

### Setup Instructions

1.  **Clone the repository**:
    ```bash
    git clone https://github.com/your-username/Game-Central.git
    ```
2.  **Navigate to the project directory**:
    ```bash
    cd Game-Central
    ```
3.  **Install dependencies**:
    * Using **Maven**:
      ```bash
      mvn install
      ```
4.  **Run the application**:
    * You can run the application from your IDE by running the `GameCentralLauncher` class.

-----

## Usage Guide

1.  **Launch the application**: Run the `GameCentralLauncher.java` file.
2.  **Login or Sign Up**:
    * If you are a new user, create an account using the "Sign Up" button.
    * If you already have an account, enter your credentials and click "Login".
3.  **Home Page**:
    * Use the search bar at the top to search for games by **name** or **genre**.
    * Browse personalized game recommendations.
4.  **Navigation**:
    * Use the top navigation bar to switch between the **Home** page, the **News** feed, and **Your Library** (your personal profile).
5.  **Game Pages**:
    * Click on any game to view its detailed page.
    * On the game page, you can:
        * View screenshots and achievements.
        * Find links to buy the game.
        * Add the game to your library or wishlist.
        * Read and write reviews.

-----

## License

This project is licensed under the MIT License. See the `LICENSE` file for more details.

-----

## Feedback

We welcome your feedback\! If you have any suggestions or bug reports, please open an issue on the GitHub repository.

-----

## Contributing

Contributions are welcome\! If you would like to contribute to the project, please follow these steps:

1.  **Fork the repository**.
2.  Create a new branch for your feature (`git checkout -b feature/AmazingFeature`).
3.  Commit your changes (`git commit -m 'Add some AmazingFeature'`).
4.  Push to the branch (`git push origin feature/AmazingFeature`).
5.  Open a **Pull Request**.