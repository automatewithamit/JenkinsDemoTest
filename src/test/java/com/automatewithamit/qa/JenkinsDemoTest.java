package com.automatewithamit.qa;

import com.microsoft.playwright.*;
import org.testng.annotations.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class JenkinsDemoTest {


    @Test
    public void testFlightSearch() {

        System.out.println("Testing FlightSearch");


    }
    @Test
    public void testRail(){
        System.out.println("Testing TestRail");
    }
    @Test
    public void testPlaywright(){
        System.out.println("Starting Playwright");
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        Page page = browser.newPage();

        // Navigate to a sample file upload page
        page.navigate("https://the-internet.herokuapp.com/upload");

        // --- Approach 1: Using setInputFiles directly ---
        // The element with id 'file-upload' is the input tag
        Path filePath = Paths.get("src/test/resources/testfile.txt");
        page.setInputFiles("#file-upload", filePath);
        page.click("#file-submit");

        // Verify upload
        System.out.println("Uploaded with setInputFiles: " + page.locator("h3").textContent());

        // --- Approach 2: Using waitForFileChooser ---
        page.navigate("https://the-internet.herokuapp.com/upload"); // Reset for second approach

        FileChooser fileChooser = page.waitForFileChooser(() -> {
            page.locator("#file-upload").click(); // Clicks to open the native chooser
        });

        fileChooser.setFiles(filePath);
        page.click("#file-submit");

        System.out.println("Uploaded with FileChooser: " + page.locator("h3").textContent());
        browser.close();
        playwright.close();
    }

}
