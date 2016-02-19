package com.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by Tom on 1/14/2016.
 */

//    http://www.kurzy.cz/kurzy-men/prevodnik-men/
public class kurzyCzPrevodnikMenPageV2 {
    private WebDriver driver;
    private String url = "http://www.kurzy.cz/kurzy-men/prevodnik-men/";
    @FindBy(name = "c")
    private WebElement amount;
    //    from and to are HTML select dropdowns
    @FindBy(name = "mena1")
    private WebElement from;
    @FindBy(name = "mena2")
    private WebElement to;
    private WebElement convert;
    @FindBy(className = "pd")
    private WebElement resultTable;

    public kurzyCzPrevodnikMenPageV2() {
        driver = new ChromeDriver();
        PageFactory.initElements(driver, this);
    }

    public void load() {
        driver.get(url);
    }

    public void close() {
        driver.close();
    }

    //    the only exposed method to separate implementation from API
    public Double convertAmountFromTo(int amount, String from, String to) {
        setAmount(amount);
        setFrom(from);
        setTo(to);
        convertAmount();
        try {
            return getResult();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    void setAmount(int amount) {
        this.amount.clear();
        this.amount.sendKeys(String.valueOf(amount));
    }

    void setFrom(String currency) {
        Select menu = new Select(from);
        menu.selectByValue(currency);
    }

    void setTo(String currency) {
        Select menu = new Select(to);
        menu.selectByValue(currency);
//    System.out.println(menu.getFirstSelectedOption().getText());
    }

    void convertAmount() {
        convert.submit();
    }

    public void testSelector() {
        Utility.checkCssSelector(resultTable, "tr.ps td:nth-child(3) span");
    }

    Double getResult() {
//        return resultTable.findElements(By.className("result")).get(1).getText();
        String amount = resultTable.findElement(By.cssSelector("tr.ps " +
                "td:nth-child(3) " +
                "span"))
                .getText();
        return Double.parseDouble(amount.replaceAll("\\s", ""));
    }
}
