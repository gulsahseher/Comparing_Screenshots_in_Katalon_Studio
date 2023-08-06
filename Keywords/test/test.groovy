package test;

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable
import java.awt.image.BufferedImage
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

import javax.imageio.ImageIO

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import com.kms.katalon.core.configuration.RunConfiguration

import com.kms.katalon.core.webui.driver.DriverFactory

import com.kms.katalon.core.webui.common.WebUiCommonHelper
import ru.yandex.qatools.ashot.AShot
import ru.yandex.qatools.ashot.Screenshot
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider
import ru.yandex.qatools.ashot.shooting.ShootingStrategies
import ru.yandex.qatools.ashot.comparison.ImageDiff
import ru.yandex.qatools.ashot.comparison.ImageDiffer

import java.awt.Image
import java.awt.Toolkit

import java.awt.image.PixelGrabber

import org.openqa.selenium.By;
import com.kms.katalon.core.logging.KeywordLogger

public class test {

	@Keyword
	def compareImages2(String test, String test1, String diffimg_path) {
		//      Görüntüleri yükleme işlemi
		Image image1 = Toolkit.getDefaultToolkit().getImage(test);
		Image image2 = Toolkit.getDefaultToolkit().getImage(test1);

		//		System.out.println(test);
		//		System.out.println(test1);

		//		Resimlerdeki piksel değerlerini almak için kullanılır.
		PixelGrabber grab1 = new PixelGrabber(image1, 0, 0, -1, -1, false);
		PixelGrabber grab2 = new PixelGrabber(image2, 0, 0, -1, -1, false);

		//		System.out.println(grab1.grabPixels())
		//		System.out.println(grab1.grabPixels())

		// İlk görüntünün piksel verilerini alıp diziye atama
		int[] data1 = null;

		if (grab1.grabPixels())
		{
			int width = grab1.getWidth(); // Görüntünün genişliğini al
			int height = grab1.getHeight(); // Görüntünün yüksekliğini al
			data1 = new int[width * height]; // Görüntüden alınan piksel verilerini saklamak için int dizisi oluştur
			data1 = (int[]) grab1.getPixels(); // Piksel verilerini alıp, data1 dizisine atama işlemi
		}
		else
		{
			System.out.println("Grabbing pixels from image1 failed.");
		}

		// İkinci görüntünün piksel verilerini alıp diziye atama
		int[] data2 = null;

		if (grab2.grabPixels())
		{
			int width = grab2.getWidth(); // Görüntünün genişliğini al
			int height = grab2.getHeight(); // Görüntünün yüksekliğini al
			data2 = new int[width * height]; // Görüntüden alınan piksel verilerini saklamak için int dizisi oluştur
			data2 = (int[]) grab2.getPixels(); // Piksel verilerini alıp, data2 dizisine atama işlemi
		}
		else
		{
			System.out.println("Grabbing pixels from image1 failed.");
		}

		// KeywordLogger sınıfı kullanarak sonuçları loglama işlemi
		KeywordLogger logger = new KeywordLogger();

		// Her iki dizi de boşsa (yani her iki görüntü de başarıyla alınmışsa), görüntülerin piksel verilerini karşılaştırma ve sonuçları loglama
		if (data1 == null && data2 == null)
		{
			logger.logPassed("aynı");
		}
		// Sadece bir görüntü alınmışsa (diğerinin alınması başarısız olmuşsa) farklı olarak loglama
		else if (data1 == null || data2 == null)
		{
			logger.logFailed("farklı");
		}
		// Her iki görüntünün piksel verileri eşitse "aynı" olarak loglama
		else if (arePixelArraysEqual(data1, data2))
		{
			logger.logPassed("aynı");
		}
		// İki görüntü farklı ise farklı olarak loglama ve farklılığı gösteren görüntüyü oluşturma
		else
		{
			try
			{
				BufferedImage expectedImage3 = ImageIO.read(new File(test)); // 1. görüntünün alınması
				BufferedImage expectedImage4 = ImageIO.read(new File(test1)); // 2. görüntünün alınması
				ImageDiffer imgDiff = new ImageDiffer();
				ImageDiff diff = imgDiff.makeDiff(expectedImage4, expectedImage3); // ImageDiffer sınıfı ile görüntü farkını bulma
				BufferedImage diffImage = diff.getDiffImage(); // Farklılık görüntüsünü alma
				ImageIO.write(diff.getMarkedImage(), "png", new File(diffimg_path)); // Görseldeki farklılığın işaretlenerek bir dosyaya kaydedilmesi (PNG formatında)
				System.out.println("\n diffImage=" + diffImage.getColorModel()); // Farklılık görüntüsünün renk modelini ekrana yazdır
				logger.logFailed("farklı"); //farklı olarak loglama
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	// İki int dizisinin piksel verilerini karşılaştıran metot
	private boolean arePixelArraysEqual(int[] data1, int[] data2) {
		if (data1.length != data2.length) {
			return false;
		}

		for (int i = 0; i < data1.length; i++) {
			if (data1[i] != data2[i]) {
				return false;
			}
		}

		return true;
	}
}
