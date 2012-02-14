package groupid.artifactid;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.android.AndroidDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.util.Date;  

@SuppressWarnings("unused")
public class AndroidTest {
	 
	 WebDriver driver = new AndroidDriver();
	 String cash = "Zero";
	 String level = "Zero";
	
	@Before
	public void setUp() {
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}

	
	@After
	public void tearDown() {
		driver.quit();
		
	}

	
	
	
	@Test
	public void testFVTweeter()  {
		
	facebookLogin();
	farmVillefarmer();
	tweetIt();

	}
	
	
	public void facebookLogin()  {
		// facebook
		   
	    driver.get("http://m.facebook.com");
	    System.out.println("LOGGING INTO FACEBOOK");
	    WebElement fbEmail = driver.findElement(By.name("email"));
	    fbEmail.sendKeys("mos6y@me.com");
	    
	    WebElement fbPw = driver.findElement(By.name("pass"));
	    fbPw.sendKeys("mo$6ycantest");
	    
	    WebElement fbLoginBtn = driver.findElement(By.name("login"));
	    fbLoginBtn.click();
	    
	    System.out.println("LOGGED INTO FACEBOOK HEADING TO THE FARM");
		
		
	}
	
	public void farmVillefarmer(){
		
		   // farmville
		   driver.get("http://m.farmville.com/");
		   
		   // checking to see if spinner is gone and page loaded
		   WebElement fvSpinner = driver.findElement(By.className("spinnerBorder"));
		   while (fvSpinner.isDisplayed()) {
			   System.out.println("waiting for spinner");   
		   }
		   
		   
		   
		   // determine if ready to harvest!
		   // more stale elements adding catch try
		   int fvcount = 0; 
		    while (fvcount < 4){
		        try {
		        	WebElement harvest = driver.findElement(By.className("list-txt"));
		        	System.out.println("FOUND HARVEST - :  " + harvest.getText());
		        	fvcount = 4;
		        	
		        	// check ready to harvest otherwise fail test
		        	Assert.assertTrue("Not ready to harvest", harvest.getText().contains("ready to harvest!")); 
		        	System.out.println("READY TO HARVEST!");
		        	
		        }
		        catch (StaleElementReferenceException e){
			           e.toString();
			           System.out.println("ERROR STALE - Trying to recover from a stale element :" + e.getMessage());
			           fvcount = fvcount+1;
			         }
		    }
	       
		   // if we got this far we are ready to harvest lets go!
		    WebElement fvNextBtn = driver.findElement(By.className("list-bttnNext"));
		    fvNextBtn.click();
		    
		    
		    
		    WebElement fvHarvestBtn = driver.findElement(By.className("cropBttnTxt"));
		    WebElement fvHarvestCount = driver.findElement(By.className("crops-amount"));
		    
		    // loop didn't stop the text doesn't change the background color does
		    // might need to check and see if harvest = x 0
		    // i think they changed the website...no longer go to 0 on last click the buttons go away
		    // changing logic to look for isDisplayed
		    // well now it seems to be behaving like it did before...so i guess i will need to check for 0 as well
		    
		   
		    try{
		    while ((fvHarvestCount.isDisplayed()) || (fvHarvestCount.getText().contains("x 0"))){
		    	System.out.println("HARVESTING! " + fvHarvestCount.getText());
		    	fvHarvestBtn.click();
		    	
		    }
		    }
		    catch (StaleElementReferenceException e){
		    	System.out.println("Caught a stale error I think we are done harvesting");
		    	
		    	
		    }
		    
		    System.out.println("DONE HARVESTING LETS GO TWEET");
		    
		   // get my farmville cash value and set to var for tweeting
		   WebElement myBank = driver.findElement(By.id("user-coins"));
		   cash = myBank.getText();
		   System.out.println("FOUND myBank - and my cash is:  " + cash);
		  
		   // get farmville level and set to var for tweeting
		   WebElement myLevel = driver.findElement(By.id("user-level"));
		   level = myLevel.getText();
		   System.out.println("FOUND myLevel -- " + level);
		   
		
		
	}
	
	public void tweetIt(){
		
		// twitter
	    driver.get("https://mobile.twitter.com/session/new");
	    
	    // twitter login
	    WebElement usernamebox = driver.findElement(By.id("username"));
	    usernamebox.sendKeys("mos6ycantest");
	    WebElement passwordbox = driver.findElement(By.id("password"));
	    passwordbox.sendKeys("mo$6ycantest");
	    passwordbox.submit();
	    
	    // move to compose url...easier then finding menu and clicking
	    driver.get("https://mobile.twitter.com/compose/tweet");
	    
	    // lots of problems with the text area box finding it and then it being visible
	    // WebElement is Stale & WebElement is not visible
	    // loops with try catch help make sure test runs
	    // might be emulator performance issue??  My fan starts running hard :-)
	   
	    int count = 0; 
	    while (count < 4){
	        try {
	           WebElement tweetBox= driver.findElement(By.className("tweet-box-textarea"));
	           Date dt = new Date();
	           System.out.println("FOUND TWEETBOX - now going to try and type in it");
	           
	           
	           try {
	           tweetBox.sendKeys("FV$" + cash + "     Level:" + level + "      #FarmvilleRobot");
	           count = 4;
	           }
	           catch (Exception e2)
	           {
	        	   System.out.println("ERROR TWEETBOX -  " + e2.getMessage() );
	        	  
	        	   count = count+1;
	           }
	           
	         } 
	           catch (StaleElementReferenceException e){
	           e.toString();
	           System.out.println("ERROR STALE - Trying to recover from a stale element :" + e.getMessage());
	           count = count+1;
	         }
	      
	    }
	    
	    
	   // find button and submit tweet!
	   WebElement tweetBtn = driver.findElement(By.className("tweet-button"));
	   tweetBtn.click();
	   
		
		
	}

}



