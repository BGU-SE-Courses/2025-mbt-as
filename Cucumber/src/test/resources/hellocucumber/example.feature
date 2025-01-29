Feature: update files limit

  Scenario Outline: Student adds submits two files to an assignment with a maximum size of 2.
    Given A logged in student with <username> and <password>
    And An assignment with a maximum size of two in topic one in course Software Quality
    When Student submit <limit> files
    Then The assignment submitted successfully

    Examples:
      | username | password    | limit |
      | student  | Student123! | 2     |

  Scenario Outline: Teacher reduces the maximum size of the file to submit to 1.
    Given A logged in teacher with <username> and <password>
    And An assignment with a maximum size of <old_limit>
    When Teacher reduces the maximum size of the file to submit to <new_limit>
    Then The student can't submit more than <new_limit> file
    And The maximum size of the file to submit on the teacher's side is <new_limit>

    Examples:
      | username | password        | old_limit | new_limit |
      | admin    | Gilandlidor123! | 2         | 1         |
