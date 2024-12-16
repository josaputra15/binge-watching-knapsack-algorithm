# TV Show Binge Planner

## Overview
The TV Show Binge Planner is a Java-based application designed to help users create an optimal binge-watching schedule. Using dynamic programming (Knapsack algorithm) and the Levenshtein Edit Distance algorithm, the system recommends TV shows based on user preferences, available time, and IMDb ratings.

## Features
- Show Recommendations: Suggests TV shows or genres closest to user input using Levenshtein Edit Distance.
- Optimal Scheduling: Generates a binge-watching schedule that maximizes user satisfaction within a given time constraint.
- Preference-Based Values: Assigns values to shows based on genres, user preferences, and IMDb ratings.
- Interactive Interface: Users can select preferred genres, shows, and available timeHow to Run the Code

## Prerequisites
Java Development Kit (JDK) 8 or above
Any Java IDE (e.g., IntelliJ IDEA, Eclipse) or a text editor with terminal support
A Java runtime environment (JRE) to execute the program

### Setup Instructions
- Clone the Repository: Download or clone the repository containing the project files.
- Compile the Code: Use your IDE or run the following command in your terminal:
  `javac Main.java`
- Run the Application: Execute the compiled code with: `java Main`

## Usage
View TV Shows and Genres:
- The program displays a list of TV shows and their genres.

Enter Preferences:
- Input your available time, preferred genres, and TV shows.
- The system uses intelligent suggestions to correct typos.

View Optimal Schedule:
- The program calculates and displays the optimal binge-watching schedule.







