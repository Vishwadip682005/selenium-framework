package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import pages.PIMPage;

public class PIMTest extends BaseTest {
    @Test
    public void addEmployeeTest() {
        // Login first
        LoginPage login = new LoginPage(driver);
        login.login("Admin", "admin123");

        // Add employee
        PIMPage pim = new PIMPage(driver);
        pim.addEmployee("John", "Doe");
        Assert.assertTrue(pim.isEmployeeAdded(), "Employee not added!");
    }
    @Test
    public void specialCharacterEmployeeTest() {
        LoginPage login = new LoginPage(driver);
        login.login("Admin", "admin123");

        PIMPage pim = new PIMPage(driver);
        pim.addEmployee("@@@", "###");

        Assert.assertTrue(pim.isEmployeeAdded(), "Employee with special chars not handled");
    }
    @Test
    public void emptySearchTest() {
        LoginPage login = new LoginPage(driver);
        login.login("Admin", "admin123");

        PIMPage pim = new PIMPage(driver);
        pim.searchEmployee("");

        Assert.assertTrue(driver.getPageSource().toLowerCase().contains("record"),
                "Search result not displayed");
    }

    @Test
    public void verifyEmployeeDetailsTest() {
        LoginPage login = new LoginPage(driver);
        login.login("Admin", "admin123");

        PIMPage pim = new PIMPage(driver);
        pim.addEmployee("John", "Doe");

        Assert.assertTrue(pim.verifyEmployeeDetails(), "Employee details not visible");
    }

    @Test
    public void editEmployeeTest() {
        LoginPage login = new LoginPage(driver);
        login.login("Admin", "admin123");

        PIMPage pim = new PIMPage(driver);
        pim.addEmployee("John", "Doe");
        pim.editEmployee("Mike");

        Assert.assertTrue(driver.getPageSource().toLowerCase().contains("mike"),
                "Employee edit not reflected");
    }

    @Test
    public void deleteEmployeeTest() {
        LoginPage login = new LoginPage(driver);
        login.login("Admin", "admin123");

        PIMPage pim = new PIMPage(driver);
        pim.deleteEmployee();

        Assert.assertTrue(driver.getPageSource().contains("No Records Found")
                || driver.getPageSource().contains("Successfully Deleted"),
                "Delete not verified");
    }
    @Test
    public void emptyEmployeeTest() {
        LoginPage login = new LoginPage(driver);
        login.login("Admin", "admin123");

        PIMPage pim = new PIMPage(driver);
        pim.addEmployee("", "");

        Assert.assertTrue(driver.getPageSource().toLowerCase().contains("required"),
                "Validation not shown for empty employee form");
    }
    @Test
    public void missingFirstNameTest() {
        LoginPage login = new LoginPage(driver);
        login.login("Admin", "admin123");

        PIMPage pim = new PIMPage(driver);

        // Only last name given, first name is empty
        pim.addEmployee("", "Doe");

        Assert.assertTrue(driver.getPageSource().toLowerCase().contains("required")
                || driver.getPageSource().toLowerCase().contains("first name"),
                "Validation message for missing first name not shown");
    }
    @Test
    public void missingLastNameTest() {
        LoginPage login = new LoginPage(driver);
        login.login("Admin", "admin123");

        PIMPage pim = new PIMPage(driver);
        pim.addEmployee("John", "");

        Assert.assertTrue(driver.getPageSource().toLowerCase().contains("required"),
                "Validation not shown for missing last name");
    }
}
