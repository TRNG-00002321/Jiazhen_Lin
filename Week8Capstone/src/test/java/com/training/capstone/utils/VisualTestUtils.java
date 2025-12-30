package com.training.capstone.utils;
import com.microsoft.playwright.Page;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class VisualTestUtils {
    public static boolean compareWithBaseline(Page page, String baselineName, Page.ScreenshotOptions screenshotOptions) throws IOException {
        byte[] actual = page.screenshot(screenshotOptions);
        Path baseline = Paths.get("visual-baselines/" + baselineName + ".png");
        System.out.println("Baseline path: " + baseline);
        byte[] expected = Files.readAllBytes(baseline);

        return Arrays.equals(actual, expected);
    }

    public static boolean compareElementWithBaseline(byte[] screenshot, String baselineName) throws IOException {
        Path baseline = Paths.get("visual-baselines/" + baselineName + ".png");
        byte[] expected = Files.readAllBytes(baseline);

        return Arrays.equals(screenshot, expected);
    }


}
