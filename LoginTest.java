package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.HomePage;
import pages.LoginPage;

public class LoginTest extends BaseTest {
	@Test
    public void validLoginTest() {
        LoginPage login = new LoginPage(driver);
        login.login("Admin", "admin123");
        
        HomePage home = new HomePage(driver);
        Assert.assertTrue(home.isDashboardDisplayed(), "Login Failed!");
    }
	@Test
	public void invalidPasswordTest() {
		LoginPage login = new LoginPage(driver);
		login.login("Admin", "wrong123");
		Assert.assertTrue(driver.getPageSource().contains("Invalid credentials"), "Error message not displayed for invalid password");
	}
	@Test
	public void invalidUsernameTest() {
		LoginPage login = new LoginPage(driver);
		login.login("WrongUser", "admin123");
		
		Assert.assertTrue(driver.getPageSource().contains("Invalid credentials"), "Error message not displayed for invalid username");
	}
	@Test
	public void logoutTest() {
	    LoginPage login = new LoginPage(driver);
	    login.login("Admin", "admin123");

	    HomePage home = new HomePage(driver);
	    home.logout();

	    Assert.assertTrue(driver.getCurrentUrl().contains("login"),
	            "Logout failed");
	}
	@Test
	public void emptyLoginTest() {
	    LoginPage login = new LoginPage(driver);
	    login.login("", "");

	    Assert.assertTrue(driver.getPageSource().contains("Required"),
	            "Validation message not shown for empty login");
	}
	@Test
	public void verifyErrorMessageTest() {
	    LoginPage login = new LoginPage(driver);
	    login.login("Admin", "wrong123");

	    Assert.assertTrue(driver.getPageSource().toLowerCase().contains("invalid"),
	            "Proper error message not displayed");
	}
}
