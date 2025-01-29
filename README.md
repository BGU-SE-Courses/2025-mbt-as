# Software Quality Engineering - System Testing
This is a repository for the system-testing assignment of the Software Quality Engineering course at the [Ben-Gurion University](https://in.bgu.ac.il/), Israel.

## Assignment Description
In this assignment, we tested an open-source software called [Moodle](https://moodle.com/).
Moodle is an open-source learning platform that makes it easy for educators to create and manage online courses.
It’s flexible, user-friendly, and widely used in schools, universities, and organizations around the world. 
One of its standout features is the ability to create interactive lessons, where teachers can easily integrate multimedia content like videos, quizzes, and assignments. 
Moodle also offers powerful tools for communication, including forums and messaging, so students and teachers can stay connected. 
Another key feature is its grading system, which allows educators to track student progress and provide feedback. 
Plus, because it’s open-source, it’s highly customizable to fit the unique needs of any educational institution.



## Installation
1. Local Moodle :
   * We followed the instraction [here](https://docs.moodle.org/405/en/Complete_install_packages_for_Windows?_gl=1*65l900*_ga*MTA1Njg2MDk3Ni4xNzM2NTAyNTM0*_ga_QWYJYEY9P5*MTczNjUwODY0MS4yLjEuMTczNjUwOTQyOC4wLjAuMA).
   * We download it from [here](https://download.moodle.org/windows/?_gl=1*1x9n0gr*_ga*MTA1Njg2MDk3Ni4xNzM2NTAyNTM0*_ga_QWYJYEY9P5*MTczNjUwODY0MS4yLjEuMTczNjUxMDI0My4wLjAuMA)
   * We chose to do our tests on 4.5.1+ version
   * We created an administrator user with the <code>username</code>: "admin" and the <code>password</code>: "Gilandlidor123!"
   * Under <code>Site administration->Users->Accounts->Add a new user</code>, we created new user with the <code>username</code>: "student" and the <code>password</code>: "Student123!"
   * Under <code>Site administration->Courses->Courses->Add a new course</code>, we created new course with <code>course full name</code>: "QA_course" and <code>course short name</code>: "QA_course"
   * On course page, after turn on the <code>Edit mode</code>, we added a section "Assignment_1". On this section we added new assignment by <code>Add content->Activity or resource->Assignment</code> with the name "assignment1" and Due date of 30.1.2050 12:00 AM.
2. Chrome Driver
   * We download it from [here](https://googlechromelabs.github.io/chrome-for-testing/)
   * We Chose the 132 Stable-Version
3. Graphiz
   * We download it from  [Graphviz](http://graphviz.org)
4. For running task5 you have the .\Cucumber\config.properties for set the local Moodle url on your device, and path for another browser driver. Yo can change it if the default values doesn't match your runtime enviroment.

Note: there is screen recording also for task 5 in Cucumber directory. 

## What we tested
We chose to focus on examining a situation where a student submits 2 documents for an assignment, and subsequently, the course instructor limits the number of submittable documents to one. We selected this scenario based on the user stories we received.

1. Student submits two files to an assignment with a maximum size of 2
   * Preconditions:
      1. There is a student in the system
      2. The student is enrolled in the instructor's course
      3. There is an open assignment
      4. The assignment allows submission of more than one document (specifically, we chose to allow 2 documents)
      5. The student has 2 documents in their Private Files
   * Expected outcome:
      1. The student should successfully submit both documents

2. Teacher reduces the maximum size of the file to submit to 1
   * Preconditions:
      1. The instructor is part of the teaching staff in the course where the student is enrolled
      2. There is an open assignment within the instructor's course
      3. The assignment allows enrolled students to submit up to 2 documents
   * Expected outcome - We anticipated two possible scenarios:
      1. The action would be prevented for the instructor - since a student has already submitted 2 documents
      2. The action would delete the student's submission that no longer meets the new restrictions

## How we tested
We used two different testing methods:
1. [Cucumber](https://cucumber.io/), a behavior-driven testing framework.
2. [Provengo](https://provengo.tech/), a story-based testing framework.

Each of the testing methods is elaborated in its own directory. 

## Results
* Cucumber Results:
    We ran a script that checks the use-cases from both teacher and student sides. The tests ran well and returned results as expected.
   

* Provengo Results:
   The model was analyzed to generate a concise state-space graph, and various test suites were run to ensure comprehensive coverage of the user stories. All tests passed with no unexpected behaviors.




## ⚠️ ATTENTION - Test Configuration:
For The Provengo Only
To run the tests correctly, you must select the appropriate TEST_TYPE in data.js.

* The system supports three modes:
    1. DOMAIN_SPECIFIC: Tests the basic student submission and teacher limitation flow
    2. TWO_WAY: Tests the interaction between teacher limitations and student submissions
    3. RESET: Resets the system to its initial state after running DOMAIN_SPECIFIC tests (Note: This is not a test type, but rather a system reset mechanism)

* To select a test type:
    1. Uncomment the desired TEST_TYPE line in data.js
    2. Comment out the other TEST_TYPE lines
    3. The RESET type should only be used after running DOMAIN_SPECIFIC tests

* For example:
        //let TEST_TYPE = TEST_TYPES.DOMAIN_SPECIFIC; 
        let TEST_TYPE = TEST_TYPES.TWO_WAY;  // Currently testing TWO_WAY
        //let TEST_TYPE = TEST_TYPES.RESET;
