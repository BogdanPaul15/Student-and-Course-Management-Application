# Secretary - Student and Course Management Application

## Description
This project is a simplified implementation of a university secretary system, which manages students and their courses within an university. The main purpose of this application is to manage information about students, their grades, and their allocation to preferred courses.

## Project Structure
- `Main.java`: The class that receives and sends tests related to the secretariat.
- `Secretariat.java`: The main class containing the logic for managing students and courses.
- `Student.java`, `StudentLicenta.java`, `StudentMaster.java`: The classes defining students and their types - undergraduate and master's.
- `Course.java`: The class defining course objects and managing students enrolled in a specific course.

## Used Collections
- `ArrayList<Student>` to store and manage information about students.
- `HashMap<String, Course<?>>` to manage courses by their names.
- `ArrayList<String>` to manage each student's preferences.

### Reason for Choosing Collections:
1. **ArrayList**: It was used to store students due to its ability to manage a dynamic list of objects. This collection is ideal for quickly adding and traversing students as well as sorting them based on certain criteria.
2. **HashMap**: It was chosen to manage courses due to its ability to associate a course name with a specific course object. This allows quick access to information about courses and ease of adding and removing them.

## Main Features
- Addition and management of undergraduate and master's students.
- Loading and updating student grades from external files.
- Publishing and managing student averages.
- Enrollment of students in courses and allocation based on their preferences.

## How to Use
1. **Command Processing**: The `executaComenzi` method receives commands from external files and processes them according to the logic defined within the application, in the Main class.
2. **Adding Students**: Use the `adauga_student` command to add new students by specifying their names and program types.
3. **Loading and Managing Grades**: Use the `citeste_mediile` commands to load grades and `posteaza_mediile` to publish student averages.
4. **Course Enrollment**: Use the `adauga_curs`, `adauga_preferinte`, and `repartizeaza` commands to manage and allocate students to desired courses.
5. **Publishing Information**: Use the `posteaza_curs` and `posteaza_student` commands to publish information about courses and students in external files.

## BONUS
I have also implemented the situation where a student fails to be assigned to any of the courses on their priority list, so they are assigned to another course with available seats.
I have added the files and a corresponding test. It can be found in the `13-bonus_inroleaza_alt_curs` folder in the `resources` folder.
