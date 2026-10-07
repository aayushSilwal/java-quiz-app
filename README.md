# Java Quiz App (Swing + MySQL)

A desktop quiz application built with Java Swing. Players pick a difficulty level, answer multiple-choice questions one at a time, and their scores are saved to a MySQL database and shown on a results leaderboard.


## Features

- Home screen with login (username entry), start, player details, high scores, and exit
- Difficulty selection (Beginner / Intermediate / Advanced) before each quiz
- Questions loaded from MySQL by difficulty level (up to 10 per category, via `QuizData`)
- One question per screen with four radio-button options
- "Next" button stays disabled until an option is selected
- Per-question scoring (1 or 0) plus a total score
- Final score screen with a "Play Again" button
- Results saved to MySQL, including player name, level, each question's score, and total
- Results page showing all competitors (name, level, total score) sorted by highest score
- Admin control panel that lists all questions stored in the database and lets an admin add new ones (edit, copy, and delete are in progress)

## Tech Stack

Java, Swing (GUI), JDBC, MySQL

## Database Setup

The app uses a database named `QuizDB` with two tables: `competitors` (player results) and `quiz_questions` (the questions managed from the admin panel). Example schema (adjust types as needed):

```sql
CREATE DATABASE QuizDB;
USE QuizDB;

CREATE TABLE competitors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(100),
    Level VARCHAR(50),
    Score1 INT,
    Score2 INT,
    Score3 INT,
    Score4 INT,
    TotalScore INT
);

CREATE TABLE quiz_questions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    category VARCHAR(50),
    question TEXT,
    option1 VARCHAR(255),
    option2 VARCHAR(255),
    option3 VARCHAR(255),
    option4 VARCHAR(255),
    correct_option INT,
    created_at VARCHAR(50)
);
```

Connection settings are in `saveScoreToDatabase()` in `quizPage.java` and `loadResults()` in `displayPage.java`, and the constants at the top of `adminPage.java` (default: `localhost:3306`, user `root`, empty password). Change them to match your setup.

## Getting Started

1. Install a JDK and MySQL, and add the **MySQL Connector/J** JAR to your project's classpath.
2. Create the database and table above.
3. Clone the repo and open it in your IDE (Eclipse, IntelliJ, etc.).
4. Run `Homepage.java` (the main entry point). To manage questions, run `adminPage.java` separately.

## How It Works

1. **Home screen** (`Homepage`): the player logs in with a username, then presses Start.
2. **Difficulty screen** (`difficultyPage`): choose a level, which becomes the question category.
3. **Quiz** (`quizPage`): questions for that category are loaded from MySQL and answered one at a time.
4. **Results**: the score is saved to the `competitors` table and can be viewed under **High Scores** (`displayPage`) or **See Players Details**.

## Project Structure

- `Homepage.java`: main window and entry point; login, start, player details, high scores, and exit buttons
- `UsernamePage.java`: username entry window
- `difficultyPage.java`: difficulty selection; opens `quizPage` with the chosen category and username
- `CompetitiorList.java`: player details window
- `quizPage.java`: quiz screen, scoring logic, score screen, and database save
- `adminPage.java`: admin control panel; displays all rows from `quiz_questions` in a table and adds new questions through a dialog (has its own `main` method)
- `displayPage.java`: results screen; queries the `competitors` table and shows a `JTable` ordered by total score (descending)
- `QuizData.java`: loads questions for a category from the `quiz_questions` table via JDBC and exposes them as `Question` objects

## Author

**Aayush Silwal**
