package hellocucumber;

import io.cucumber.java.en.*;
import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitions {
    private WebDriver driver;
    private final int DELAY_BETWEEN_STEPS = 2000;
    // mainPage
    private final String LoginButton_In_Main_Page = "/html[1]/body[1]/div[2]/nav[1]/div[1]/div[2]/div[1]/div[1]/span[1]/a[1]";
    // loginPage
    private final String Username_Text_Box = "//input[@placeholder=\"Username\"]";
    private final String Password_Text_Box = "//input[@id=\"password\"]";
    private final String LoginButton_In_Login_Page = "//div[3]/button[1]";
    //navigate to course - From StudentUser
    private final String Mycourses_Buttom= "/html[1]/body[1]/div[2]/nav[1]/div[1]/div[1]/nav[1]/ul[1]/li[3]/a[1]";
    private final String QA_Course_In_My_Courses= "//div[1]/div[1]/div[1]/div[1]/a[1]/div[1]";
    // navigate to Assignment1 - to Add Submission
    private final String Assignment_1_Button= "//li[2]/div[1]/div[2]/ul[1]/li[1]/div[1]/div[2]/div[2]/div[1]/div[1]/a[1]";
    private final String Add_submission_Button= "//body/div[2]/div[4]/div[1]/div[2]/div[1]/section[1]/div[2]/div[1]/div[1]/div[1]/div[1]/form[1]/button[1]";
    // Add Files and Submit. Note that The Files where Uploaded to "Private Files" Before The Task
    private final String Add_Files_Button= "//div[2]/div[1]/div[1]/div[1]/a[1]/i[1]";
    private final String Go_To_Private_files= "//div[2]/div[1]/div[1]/div[1]/div[2]/a[1]";
    private final String Upload_File1= "repo_upload_file";
    private final String Upload_File2= "fp-upload-btn";
    private final String Select_This_File_Button= "//form[1]/div[4]/div[1]/button[1]";
    private final String Save_Changes_Button= "//div[1]/span[1]/input[1]";
    private final String View_Submission = "//body/div[2]/div[4]/div[1]/div[2]/div[1]/section[1]/div[2]/div[2]/div[1]/div[1]/table[1]/tbody[1]/tr[5]/td[1]";
    // Teacher Reduces The Maximum Size Of The File To Submit To 1
    private final String Setting_Button= "//div[2]/nav[1]/ul[1]/li[2]/a[1]";
    private final String find_MaxSize_Button_Location = "//body/div[3]/div[4]/div/div[3]/div/section/div[2]/form/fieldset[3]/div[2]/div[3]/div[2]/select";
    private final String Find_MaxSize_Button_Location= "//body/div[3]/div[4]/div[1]/div[3]/div[1]/section[1]/div[2]/form[1]/fieldset[3]/div[2]/div[3]/div[2]/select[1]";
    private final String Find_Save_Button_Location= "//body/div[3]/div[4]/div/div[3]/div/section/div[2]/form/div[4]/div[2]/div[1]/div/div[2]/span/input";
    private final String Error_Box ="//body/div[2]/div[4]/div/div[2]/div/section/div[2]/div/form/fieldset/div[2]/div/div[2]/div";
    private final String Error_Message= "You must not attach more than 1 files here.";
    // Student Remove Subbmission
    private final String Remove_Submission_Button= "//div[2]/div[1]/form[1]/button[1]";
    private final String Confirm_Remove_Submission= "//div[2]/form[1]/button[1]";

    private final String teacher_username = "admin";
    private final String teacher_password = "Gilandlidor123!";
    private final String student_username = "student";
    private final String student_password = "Student123!";

    private String LocalMoodleURL;

    public StepDefinitions() throws InterruptedException {
        String chromeDriverPath;
        try {
            // connect to the configuration file
            String configFilePath = "./config.properties";
            FileInputStream propsInput = new FileInputStream(configFilePath);
            Properties prop = new Properties();
            prop.load(propsInput);

            // use the properties of the configuration file
            chromeDriverPath = prop.getProperty("CHROME_DRIVER_PATH");
            if (chromeDriverPath.equals("\\Selenium\\chromedriver.exe")) { // default state
                // set the chrome driver path executable
                String filePath = System.getProperty("user.dir");
                File file = new File(filePath);
                file = file.getParentFile();
                chromeDriverPath = file + chromeDriverPath; // add the local path at the start
            }
            LocalMoodleURL = prop.getProperty("LOCAL_MOODLE_LINK");
        }
        catch (Exception e) {
            String filePath = System.getProperty("user.dir");
            File file = new File(filePath);
            file = file.getParentFile();
            chromeDriverPath = file + "\\Selenium\\chromedriver.exe";
            LocalMoodleURL = "http://localhost/";
        }

        //link the webdriver
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
    }

    private void closeMoodle()  {driver.quit();}

    @BeforeAll
    public static void beforeAll() throws InterruptedException {
        //create files for the test
        File file1 = new File("file1.txt");
        File file2 = new File("file2.txt");
        try {
            file1.createNewFile();
            file2.createNewFile();
            FileWriter file1writer = new FileWriter("file1.txt");
            file1writer.write(".");
            file1writer.close();
            FileWriter file2writer = new FileWriter("file2.txt");
            file2writer.write(".");
            file2writer.close();
        }
        catch (IOException e) { }
    }

    @AfterAll
    public static void afterAll() throws InterruptedException {
        // Runs after all scenarios
        File file1 = new File("file1.txt");
        File file2 = new File("file2.txt");
        file1.delete();
        file2.delete();
    }

    // -----------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------
    // First scenario: Student adds submits two files to an assignment with a maximum size of 2.
    // -----------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------

    //login to the student user with the supplied username and password
    @Given("A logged in student with " + student_username + " and " + student_password)
    public void studentLogin() throws InterruptedException {
        driver = new ChromeDriver();
        driver.get(LocalMoodleURL);
        Thread.sleep(3500);


        WebElement loginPageButton = driver.findElement(By.xpath(LoginButton_In_Main_Page));
        loginPageButton.click();
        Thread.sleep(DELAY_BETWEEN_STEPS);
        WebElement UserNameButton = driver.findElement(By.xpath(Username_Text_Box));
        UserNameButton.sendKeys(student_username);
        Thread.sleep(DELAY_BETWEEN_STEPS);
        WebElement PasswordButton = driver.findElement(By.xpath(Password_Text_Box));
        PasswordButton.sendKeys(student_password);
        Thread.sleep(DELAY_BETWEEN_STEPS);
        WebElement loginButton = driver.findElement(By.xpath(LoginButton_In_Login_Page));
        loginButton.click();
        Thread.sleep(DELAY_BETWEEN_STEPS);
    }

    //navigate to the assignment in the moodle course
    @And("An assignment with a maximum size of two in topic one in course Software Quality")
    public void goToAssignmentStudent() throws InterruptedException {
        WebElement myCoursesButton = driver.findElement(By.xpath(Mycourses_Buttom));
        myCoursesButton.click();
        Thread.sleep(DELAY_BETWEEN_STEPS);
        WebElement softwareQualityCourseButton = driver.findElement(By.xpath(QA_Course_In_My_Courses));
        softwareQualityCourseButton.click();
        Thread.sleep(DELAY_BETWEEN_STEPS);
        WebElement assignmentButton = driver.findElement(By.xpath(Assignment_1_Button));
        assignmentButton.click();
        Thread.sleep(DELAY_BETWEEN_STEPS);
    }


    //add to the assignment box 2 txt files
    @When("Student submit {int} files")
    public void submitFiles(Integer limit) throws InterruptedException {
        File file1 = new File("file1.txt");
        File file2 = new File("file2.txt");
        String file1Name = file1.getAbsolutePath();
        String file2Name = file2.getAbsolutePath();
        WebElement addSubmitButton = driver.findElement(By.xpath(Add_submission_Button));
        addSubmitButton.click();
        Thread.sleep(DELAY_BETWEEN_STEPS);

        //add the first file
        WebElement addFilesButton = driver.findElement(By.xpath(Add_Files_Button));
        addFilesButton.click();
        Thread.sleep(DELAY_BETWEEN_STEPS);
        WebElement uploadFilesButton = driver.findElement(By.xpath(Go_To_Private_files));
        uploadFilesButton.click();
        Thread.sleep(DELAY_BETWEEN_STEPS);
        WebElement chooseFileButton = driver.findElement(By.name(Upload_File1));
        chooseFileButton.sendKeys(file1Name);
        Thread.sleep(DELAY_BETWEEN_STEPS);
        WebElement uploadThisFileButton = driver.findElement(By.className(Upload_File2));
        uploadThisFileButton.click();
        Thread.sleep(DELAY_BETWEEN_STEPS);

        //add the second file
        addFilesButton.click();
        Thread.sleep(DELAY_BETWEEN_STEPS);
        uploadFilesButton.click();
        Thread.sleep(DELAY_BETWEEN_STEPS);
        chooseFileButton = driver.findElement(By.name(Upload_File1));
        chooseFileButton.sendKeys(file2Name);
        Thread.sleep(DELAY_BETWEEN_STEPS);
        uploadThisFileButton = driver.findElement(By.className(Upload_File2));
        uploadThisFileButton.click();
        Thread.sleep(DELAY_BETWEEN_STEPS);

        //save the submission
        WebElement saveChangesButton = driver.findElement(By.xpath(Save_Changes_Button));
        saveChangesButton.click();
        Thread.sleep(DELAY_BETWEEN_STEPS);
    }

    //checks if the assignment box has 2 files as it should be
    @Then("The assignment submitted successfully")
    public void theAssignmentSubmittedSuccessfully() {
        WebElement fileSubmissions = driver.findElement(By.xpath(View_Submission));
        String fileSubmissionsText = fileSubmissions.getText();

        //Checks the submitted file names, if any.
        boolean file1Submitted = fileSubmissionsText.contains("file1.txt");
        boolean file2Submitted = fileSubmissionsText.contains("file2.txt");
        assertEquals(true, file1Submitted);
        assertEquals(true, file2Submitted);

        //now we will remove the submission for initialize the state of the system
        WebElement removeSubmission = driver.findElement(By.xpath(Remove_Submission_Button));
        removeSubmission.click();
        WebElement confirmRemoveSubmission = driver.findElement(By.xpath(Confirm_Remove_Submission));
        confirmRemoveSubmission.click();
        driver.quit();
    }

    // -----------------------------------------------------------------------------
    // -----------------------------------------------------------------------------
    // Second scenario: Teacher reduces the maximum size of the file to submit to 1.
    // -----------------------------------------------------------------------------
    // -----------------------------------------------------------------------------

    //login to the teacher account
    @Given("A logged in teacher with " + teacher_username + " and " + teacher_password)
    public void loginToTeacher() throws InterruptedException {
        driver = new ChromeDriver();
        driver.get(LocalMoodleURL);
        Thread.sleep(3500);

        WebElement loginPageButton = driver.findElement(By.xpath(LoginButton_In_Main_Page));
        loginPageButton.click();
        Thread.sleep(DELAY_BETWEEN_STEPS);
        WebElement UserNameButton = driver.findElement(By.xpath(Username_Text_Box));
        UserNameButton.sendKeys(teacher_username);
        Thread.sleep(DELAY_BETWEEN_STEPS);
        WebElement PasswordButton = driver.findElement(By.xpath(Password_Text_Box));
        PasswordButton.sendKeys(teacher_password);
        Thread.sleep(DELAY_BETWEEN_STEPS);
        WebElement loginButton = driver.findElement(By.xpath(LoginButton_In_Login_Page));
        loginButton.click();
        Thread.sleep(DELAY_BETWEEN_STEPS);
    }

    //navigating to the assignment in the moodle course
    @And("An assignment with a maximum size of {int}")
    public void goToAssignmentTeacher(Integer limit) throws InterruptedException {
        WebElement myCoursesButton = driver.findElement(By.xpath(Mycourses_Buttom));
        myCoursesButton.click();
        Thread.sleep(DELAY_BETWEEN_STEPS);
        WebElement softwareQualityCourseButton = driver.findElement(By.xpath(QA_Course_In_My_Courses));
        softwareQualityCourseButton.click();
        Thread.sleep(DELAY_BETWEEN_STEPS);
        WebElement assignmentButton = driver.findElement(By.xpath(Assignment_1_Button));
        assignmentButton.click();
        Thread.sleep(DELAY_BETWEEN_STEPS);
        WebElement settingsButton = driver.findElement(By.xpath(Setting_Button));
        settingsButton.click();
        Thread.sleep(DELAY_BETWEEN_STEPS);
        WebElement maxNumberOfFilesButton = driver.findElement(By.xpath(Find_MaxSize_Button_Location));
        Select maxNumberOfFilesSelect = new Select(maxNumberOfFilesButton);
        assertEquals(maxNumberOfFilesSelect.getFirstSelectedOption().getAccessibleName().compareTo(limit.toString()),0);
    }

    @When("Teacher reduces the maximum size of the file to submit to {int}")
    public void teacherReducesTheLimit(Integer limit) throws InterruptedException {
        WebElement maxNumberOfFilesButton = driver.findElement(By.xpath(Find_MaxSize_Button_Location));
        Select maxNumberOfFilesSelect = new Select(maxNumberOfFilesButton);
        maxNumberOfFilesSelect.selectByVisibleText(limit.toString()); //select the value of the argument limit
        WebElement saveButton = driver.findElement(By.xpath(Find_Save_Button_Location));
        saveButton.click();
        Thread.sleep(DELAY_BETWEEN_STEPS);
    }

    @Then("The student can't submit more than {int} file")
    public void theStudentCanTSubmitMoreThanFile(int limit) throws InterruptedException {
        //new driver for student login
        WebDriver _driver = new ChromeDriver();
        _driver.get(LocalMoodleURL);

        //the student logs in
        WebElement loginPageButton = _driver.findElement(By.xpath(LoginButton_In_Main_Page));
        loginPageButton.click();
        Thread.sleep(DELAY_BETWEEN_STEPS);
        WebElement UserNameButton = _driver.findElement(By.xpath(Username_Text_Box));
        UserNameButton.sendKeys(student_username);
        Thread.sleep(DELAY_BETWEEN_STEPS);
        WebElement PasswordButton = _driver.findElement(By.xpath(Password_Text_Box));
        PasswordButton.sendKeys(student_password);
        Thread.sleep(DELAY_BETWEEN_STEPS);
        WebElement loginButton = _driver.findElement(By.xpath(LoginButton_In_Login_Page));
        loginButton.click();
        Thread.sleep(DELAY_BETWEEN_STEPS);

        //navigates to the assignment page
        WebElement myCoursesButton = _driver.findElement(By.xpath(Mycourses_Buttom));
        myCoursesButton.click();
        Thread.sleep(DELAY_BETWEEN_STEPS);
        WebElement softwareQualityCourseButton = _driver.findElement(By.xpath(QA_Course_In_My_Courses));
        softwareQualityCourseButton.click();
        Thread.sleep(DELAY_BETWEEN_STEPS);
        WebElement assignmentButton = _driver.findElement(By.xpath(Assignment_1_Button));
        assignmentButton.click();
        Thread.sleep(DELAY_BETWEEN_STEPS);

        //add submission
        File file1 = new File("file1.txt");
        File file2 = new File("file2.txt");
        String file1Name = file1.getAbsolutePath();
        String file2Name = file2.getAbsolutePath();
        WebElement addSubmitButton = _driver.findElement(By.xpath(Add_submission_Button));
        addSubmitButton.click();
        Thread.sleep(DELAY_BETWEEN_STEPS);

        //add the first file
        WebElement addFilesButton = _driver.findElement(By.xpath(Add_Files_Button));
        addFilesButton.click();
        Thread.sleep(DELAY_BETWEEN_STEPS);
        WebElement uploadFilesButton = _driver.findElement(By.xpath(Go_To_Private_files));
        uploadFilesButton.click();
        Thread.sleep(DELAY_BETWEEN_STEPS);
        WebElement chooseFileButton = _driver.findElement(By.name(Upload_File1));
        chooseFileButton.sendKeys(file1Name);
        Thread.sleep(DELAY_BETWEEN_STEPS);
        WebElement uploadThisFileButton = _driver.findElement(By.className(Upload_File2));
        uploadThisFileButton.click();
        Thread.sleep(DELAY_BETWEEN_STEPS);

        //try to add the second file
        try {
            addFilesButton.click();
            fail();
        }
        catch (Exception e) { }

        _driver.quit();
    }

    @And("The maximum size of the file to submit on the teacher's side is {int}")
    public void teacherCheckFilesLimit(Integer limit) throws InterruptedException {
        WebElement settingsButton = driver.findElement(By.xpath(Setting_Button));
        settingsButton.click();
        Thread.sleep(DELAY_BETWEEN_STEPS);
        WebElement maxNumberOfFilesButton = driver.findElement(By.xpath(Find_MaxSize_Button_Location));
        Select maxNumberOfFilesSelect = new Select(maxNumberOfFilesButton);
        assertEquals(maxNumberOfFilesSelect.getFirstSelectedOption().getAccessibleName().compareTo("1"),0);

        //reinitialize the state of the system after testing
        maxNumberOfFilesSelect.selectByVisibleText("2"); //Re-selected the 2 file limit
        WebElement saveButton = driver.findElement(By.xpath(Find_Save_Button_Location));
        saveButton.click();
        Thread.sleep(DELAY_BETWEEN_STEPS);
        closeMoodle();
    }
}
