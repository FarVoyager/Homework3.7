package Classes;

import Annotations.AfterSuite;
import Annotations.BeforeSuite;
import Annotations.Test;

public class TestClass {

    @BeforeSuite
    public static void beforeSuiteTest() {
        System.out.println("beforeSuiteTest");
    }

    @Test(priority = 4)
    public static void twoPlusTwoTest() {
        System.out.println("Test: " + (2 + 2));
    }

    @Test(priority = 2)
    public static void fiveMinusThreeTest() {
        System.out.println("Test: " + (5 - 3));
    }

    @Test(priority = 5)
    public static void tenPercentOfFiftyTest() {
        System.out.println("Test: " + (50 / 10));
    }

    @AfterSuite
    public static void AfterSuiteTest() {
        System.out.println("AfterSuiteTest");
    }
}
