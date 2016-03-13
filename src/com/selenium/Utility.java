package com.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tom on 1/15/2016.
 */
public class Utility {
    private WebDriver driver;

    //    instantiation for methods using driver
    public Utility(WebDriver driver) {
        this.driver = driver;
    }

    /* Generate full xpath for given element
    *  Code from StackOverflow
    *  It is recursive, pass "" to parameter current */
    public static String generateXPATH(WebElement childElement, String
            current) {
        String childTag = childElement.getTagName();
        if (childTag.equals("html")) {
            return "/html[1]" + current;
        }
        WebElement parentElement = childElement.findElement(By.xpath(".."));
        List<WebElement> childrenElements = parentElement.findElements(By.xpath("*"));
        int count = 0;
        for (int i = 0; i < childrenElements.size(); i++) {
            WebElement childrenElement = childrenElements.get(i);
            String childrenElementTag = childrenElement.getTagName();
            if (childTag.equals(childrenElementTag)) {
                count++;
            }
            if (childElement.equals(childrenElement)) {
                return generateXPATH(parentElement, "/" + childTag + "[" + count + "]" + current);
            }
        }
        return null;
    }

    /* No exception check for element presence */
    public static void checkCssSelector(WebDriver driver, String sel) {
        try {
            driver.findElement(By.cssSelector(sel));
            System.out.println("[ok] " + sel);
        } catch (Exception e) {
            System.out.println("[exception] " + sel);
            e.printStackTrace();
        }
    }

    /* No exception check for element presence */
    public static void checkCssSelector(WebElement element, String sel) {
        try {
            element.findElement(By.cssSelector(sel));
            System.out.println("[ok] " + sel);
        } catch (Exception e) {
            System.out.println("[exception] " + sel);
            e.printStackTrace();
        }
    }

    //    Print details on the element object without raising exception
    public static void printElementInfo(WebElement elem) {
        System.out.println(">> elem info [ " + elem.getTagName() + " ]");
        String[] commonAttrib = {"name", "class", "id", "type", "value"};
        for (String attr : commonAttrib) {
            try {
                System.out.println(attr + "=" + elem.getAttribute(attr));
            } catch (Exception e) {
                System.out.println(attr + " not found");
            }
        }
        System.out.println("text=" + elem.getText());
    }

    //    Element text may be discarded through parameter
    public static void printElementInfo(WebElement elem, boolean printText) {
        System.out.println(">> elem info [ " + elem.getTagName() + " ]");
        String[] commonAttrib = {"name", "class", "id", "type", "value"};
        for (String attr : commonAttrib) {
            try {
                System.out.println(attr + "=" + elem.getAttribute(attr));
            } catch (Exception e) {
                System.out.println(attr + " not found");
            }
        }
        if (printText) {
            System.out.println("text=" + elem.getText());
        }
    }

    public static void sleepNow(int seconds) {
//        System.out.println("[sleep] " + seconds + " sec");
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
        }
    }

    public static Map<String, String> getAttrMap(WebElement elem) {
        String[] attrs = {"name", "class", "id", "type", "value"};
        Map<String, String> attrMap = new HashMap<>();

        for (String attr : attrs) {
            try {
                attrMap.put(attr, elem.getAttribute(attr));
            } catch (NoSuchElementException e) {
            }
        }
        return attrMap;
    }

    //    @return frame name, id or index
    public String getFrameHavingElem(By byLoc) {
        List<WebElement> frames = driver.findElements(By.tagName("frame"));
        WebElement targetFrame = null;
        String frName = null;
        int frameIdx = -1;

        for (WebElement frame : frames) {
            frameIdx++;
            driver.switchTo().defaultContent();
            driver.switchTo().frame(frame);
            try {
                driver.findElement(byLoc);
                targetFrame = frame;
//                System.out.println("[hit] " + frame);
            } catch (NoSuchElementException e) {
                continue;
            }
//            switch back to the root, otherwise stale element ref
            driver.switchTo().defaultContent();
            break;
        }

        if (targetFrame == null) {
            throw new NoSuchElementException("None of frames has the class");
        }

//        get frame identification
        Map<String, String> attrs = Utility.getAttrMap(targetFrame);
        frName = String.valueOf(frameIdx);
        for (String id : Arrays.asList("name", "id")) {
            if (attrs.containsKey(id)) {
                frName = attrs.get(id);
            }
        }

        return frName;
    }

    public boolean hasElementInFrame(By byLoc) {
        try {
            getFrameHavingElem(byLoc);
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }
}

