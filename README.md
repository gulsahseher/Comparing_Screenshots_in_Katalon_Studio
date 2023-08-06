# Comparing Screenshots in Katalon Studio
In this repository, the comparison of two screenshots has been implemented using test cases created in Katalon Studio. To achieve this, a custom keyword has been created. The primary focus is on comparing the screenshots taken during test execution.

## What is Keyword in Katalon Studio?
In Katalon Studio, a "keyword" refers to a reusable action or function that performs a specific task or set of tasks within a test case. Keywords are a fundamental concept in Katalon Studio's test automation framework and play a crucial role in building modular and maintainable test scripts.

Custom keywords can be created using Groovy or Java programming languages in Katalon Studio. Once created, they can be saved in a "Keywords" folder within the project structure, and Katalon Studio automatically recognizes them as part of the available keyword set.

By utilizing keywords, testers can build test cases by combining and arranging these actions in a logical sequence, making it easier to manage and update test scripts when changes in the application occur.

## Creation of Keyword
The process starts with the creation of a custom keyword called "compareImages2". This keyword is designed to compare two screenshots captured from different websites and identify any differences between them.

```ruby
public class test 
{
    @Keyword
    def compareImages2(String test, String test1, String diffimg_path) {
      ...
    }
}
```
The keyword uses the AShot library, which provides utilities for capturing screenshots and performing image comparisons. 

```ruby
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
```

The "PixelGrabber" class is used to extract pixel data from the loaded images. The keyword then compares the pixel data of the two screenshots to determine if they are the same or different.

```ruby
PixelGrabber grab1 = new PixelGrabber(image1, 0, 0, -1, -1, false);
PixelGrabber grab2 = new PixelGrabber(image2, 0, 0, -1, -1, false);
```

The results of the comparison are logged using the "KeywordLogger" class. If both images are successfully captured and their pixel data matches, the keyword logs "same". If there are differences between the images or if the capturing process fails for one of them, the keyword logs "different" and creates an image highlighting the differences.


```ruby
KeywordLogger logger = new KeywordLogger();
```

Overall, this repository demonstrates how to perform image comparison in Katalon Studio using a custom keyword, allowing for efficient testing of graphical elements and identifying potential issues in web applications.

## Creation of Test Case
Creating a test case in Katalon Studio involves defining a sequence of test steps to verify the functionality of a specific feature or scenario in the application under test. 

To create a test case where two screenshots are compared, the pages where the screenshots will be taken must be identified first. Then, the URLs of these pages should be placed inside the WebUI.navigateToUrl() function. 

```ruby
WebUI.navigateToUrl('URL_OF_PAGE')
```
The folder path where the screenshots will be saved should be written to String screenshot1 and String screenshot2.

![13_Compare_2_Screenshots_new_test_case](https://github.com/gulsahseher/Comparing_Screenshots_in_Katalon_Studio/assets/70262153/5884ed13-ded4-4cba-a3f7-6b8e4ecdbdae)

### Call a keyword in a Test Case:

**1.** Open your Test Case in Katalon Studio.

**2.** In the Test Case editor, add a new test step by clicking the "+" icon or right-clicking and selecting "Add."

**3.** From the list of available keywords, choose the keyword you want to call. If it's a custom keyword, it should appear under the "Custom Keywords" section.

**4.** Configure the parameters for the selected keyword by providing the necessary input values. For example, if you are calling a keyword to click on a button, you may need to specify the target object (e.g., the button's locator).

## Running the Test Case

This test case navigates to two web pages, captures screenshots of each page, compares the screenshots using a custom keyword, and logs the results of the comparison. The custom keyword is likely designed to detect visual differences between the two screenshots, which can be helpful for visual regression testing and identifying layout or design issues.

### The First Screenshot
![test1](https://github.com/gulsahseher/Comparing_Screenshots_in_Katalon_Studio/assets/70262153/6d6776d2-bc70-4757-9164-2983ae0bb409)

### The Second Screenshot
![test2](https://github.com/gulsahseher/Comparing_Screenshots_in_Katalon_Studio/assets/70262153/ab616718-2bda-4eb7-8f17-04afb9589e41)

### The Result Image

![difference_test1_test2](https://github.com/gulsahseher/Comparing_Screenshots_in_Katalon_Studio/assets/70262153/adcde5b9-ca3b-42a8-84ea-285ad1e53ae2)


