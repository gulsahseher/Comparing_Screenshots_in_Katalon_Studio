import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

WebUI.openBrowser('')

WebUI.navigateToUrl('http://www.baskent.edu.tr/~tkaracay/etudio/ders/internet/html/tables/table_05.html')

WebUI.maximizeWindow()

WebUI.delay(3)

String img1_path = 'C:/Users/gulsah.seher/Katalon Studio/Compare_Screenshot/screenshots/test1.png'

String img1 = WebUI.takeElementScreenshot(img1_path, findTestObject('compare_two_screenshots_2/tablo_1'))

WebUI.delay(3)

String img2_path = 'C:/Users/gulsah.seher/Katalon Studio/Compare_Screenshot/screenshots/test2.png'

img2 = WebUI.takeElementScreenshot(img2_path, findTestObject('compare_two_screenshots_2/tablo_2'))

WebUI.delay(3)

CustomKeywords.'test.test.compareImages2'(img1, img2)

WebUI.closeBrowser()

