package test;

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint;
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase;
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData;
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject;
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject;

import com.kms.katalon.core.annotation.Keyword;
import com.kms.katalon.core.checkpoint.Checkpoint;
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW;
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile;
import com.kms.katalon.core.model.FailureHandling;
import com.kms.katalon.core.testcase.TestCase;
import com.kms.katalon.core.testdata.TestData;
import com.kms.katalon.core.testobject.TestObject;
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS;
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI;
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows;

import internal.GlobalVariable;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import javax.imageio.ImageIO;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.kms.katalon.core.configuration.RunConfiguration;

import com.kms.katalon.core.webui.driver.DriverFactory;

import com.kms.katalon.core.webui.common.WebUiCommonHelper;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;

import java.awt.Image;
import java.awt.Toolkit;

import java.awt.image.PixelGrabber;

import org.openqa.selenium.By;
import com.kms.katalon.core.logging.KeywordLogger;

public class test 
{
	@Keyword
	def compareImages2(String test, String test1, String diffimg_path) 
	{
		// Load images
		Image image1 = Toolkit.getDefaultToolkit().getImage(test);
		Image image2 = Toolkit.getDefaultToolkit().getImage(test1);

		// PixelGrabber to get pixel data from images
		PixelGrabber grab1 = new PixelGrabber(image1, 0, 0, -1, -1, false);
		PixelGrabber grab2 = new PixelGrabber(image2, 0, 0, -1, -1, false);

		// Arrays to store pixel data of images
		int[] data1 = null;

		// Get pixel data of the first image and store it in data1 array
		if (grab1.grabPixels()) 
		{
			int width = grab1.getWidth();
			int height = grab1.getHeight();
			data1 = new int[width * height];
			data1 = (int[]) grab1.getPixels();
		} 
		else 
		{
			System.out.println("Grabbing pixels from image1 failed.");
		}

		int[] data2 = null;

		// Get pixel data of the second image and store it in data2 array
		if (grab2.grabPixels()) 
		{
			int width = grab2.getWidth();
			int height = grab2.getHeight();
			data2 = new int[width * height];
			data2 = (int[]) grab2.getPixels();
		} 
		else 
		{
			System.out.println("Grabbing pixels from image1 failed.");
		}

		// Use KeywordLogger to log the results
		KeywordLogger logger = new KeywordLogger();

		// If both arrays are null (both images are successfully captured), log "aynı" (same)
		if (data1 == null && data2 == null) 
		{
			logger.logPassed("same");
		}
		// If only one of the images is captured (the other one is failed), log "farklı" (different)
		else if (data1 == null || data2 == null) 
		{
			logger.logFailed("different");
		}
		// If both arrays are not null, compare the pixel data and log "aynı" (same) if they are equal
		else if (arePixelArraysEqual(data1, data2)) 
		{
			logger.logPassed("same");
		}
		// If the images are different, log "different" and create an image showing the differences
		else 
		{
			try 
			{
				BufferedImage expectedImage3 = ImageIO.read(new File(test));
				BufferedImage expectedImage4 = ImageIO.read(new File(test1));
				ImageDiffer imgDiff = new ImageDiffer();
				ImageDiff diff = imgDiff.makeDiff(expectedImage4, expectedImage3);
				BufferedImage diffImage = diff.getDiffImage();
				ImageIO.write(diff.getMarkedImage(), "png", new File(diffimg_path));
				System.out.println("\n diffImage=" + diffImage.getColorModel());
				logger.logFailed("farklı");
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}

	// Method to compare two int arrays (pixel data)
	private boolean arePixelArraysEqual(int[] data1, int[] data2) 
	{
		if (data1.length != data2.length) 
		{	
			return false;
		}

		for (int i = 0; i < data1.length; i++) 
		{
			if (data1[i] != data2[i]) 
			{
				return false;
			}
		}

		return true;
	}
}
