# usc-schedule-planner
The main repository for our 201 project, SChedule Gurus. Please use branches to edit and merge with master/main.

USC CSCI 201 Fall 2020 Final Project: Schedule Gurus

Nirav Adunuthula, Jaehyung Choi, Sachi Desale, Camden Jones, Kevin Lee, Pilar Luiz

November 23, 2020

The main functionality of our program is to allow users to:

- sign up and login in
- add their Fall 2020 classes and generate a schedule
- view their schedule
- add friends
- view their friends' schedules
- log out

The gui folder has all our PHP files for the frontend.
The guru-server folder has the Schedule of Classes API, RMP API, Algorithm, and Servlet, along with some testing code. 
The main files for these can be viewed in guru-server/src.

=========================================

IMPORTANT NOTES:

Ignore the WebContent/META-INF folder. 

The GUI folder contains the PHP files, but you do not need to run any of those files. 
The reason for this is that our frontend is hosted on the USC ITP server. 
The guru-server contains the code for the backend.

To run the program, follow this [Deployment Document](https://docs.google.com/document/d/15cLzYvqX1b6iO6VtGKKN-vLj2gbJfWBv9RrHi4jWPQM/edit?usp=sharing)

The front end is hosted at: 303.itpwebdev.com/~pluiz/gui/main

Notes for interpreting front end:
-1 RMP values mean that instructor's Rate My Professor score could not be found.
-1 course times mean that section did not have a set start/end time.
