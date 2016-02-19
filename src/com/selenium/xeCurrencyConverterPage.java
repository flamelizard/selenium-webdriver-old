package com.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by Tom on 1/14/2016.
 */
/* Page Object Class that hides implementation from API
 *
 *  Page Object represents a single web page to be tested and expose only
 *  methods necessary to enter test data and get results
 *
 * Not sure of best practices but I basically cover here two pages - first to
 * enter values and second to read result. Result table seems to be
 * re-initialized after a method call (table does not work on initial page)
 *
*/

//"http://www.xe.com/currencyconverter/"
public class xeCurrencyConverterPage {
    WebDriver driver;
    //    variable names correlate to element id or class name
    private WebElement amount;
    private WebElement from;
    private WebElement to;
    private WebElement uccForm;
    private WebElement from_var;
    //    Set explicitly how to locate element - class, id etc.
    @FindBy(className = "ucc-result-table")
    private WebElement resultTable;  // web table holding result

    public xeCurrencyConverterPage(WebDriver driver) {
/*
Init each instance variable in "this" with corresponding element object.
By default, instance var name is used as element locator, either as
id or name. @FindBy can override this behaviour.
*/
        PageFactory.initElements(driver, this);
    }

    //    accessors methods for interaction with page's form
    public void setAmount(int amount) {
        this.amount.sendKeys(String.valueOf(amount));
    }

    /*
This does NOT work - I cannot set currency type despite big effort
It is probably page's intention since it says that automated extraction
of currency rates is prohibited
    */
    public void setFrom(String fromCurr) {
/*
Elaborate way to select currency from JS generated drop down
Select class supports only traditional HTML dropdown <select..>
*/

/*
XPATH breakdown
Search from root, match any element, followed by element <li>
whose text string contains custom string - currency name "." refers to current
object and its string
*/
//        String from_xpath = String.format("//*[.=\"%s\"]", fromCurr);
        from.click();
        String from_xpath = String.format("//*/li[contains(.,'%s')]", fromCurr);
        WebElement jsDropdown = from.findElement(By.xpath(from_xpath));
        jsDropdown.click();

    }

    // This does NOT work too
    public void setTo(String toCurr) {
        to.click();
//        select target currency
        String to_xpath = String.format("//*[contains(.,'%s')]", toCurr);
        WebElement jsDropdownItem = from.findElement(By.xpath(to_xpath));
        jsDropdownItem.click();
        System.out.println("[dropdown] " + jsDropdownItem.getText());
    }

    public void convertAmount() {
        uccForm.submit();
    }

    public String getResult() {
/*
I must use initialized object, it does NOT work to query driver directly
Not sure why.
Table does not exist on first page but it shows upon form submit

System.out.println(driver.findElement(By.cssSelector("table.ucc-result-table tbody tr:first-child " +
"td:nth-child(3)")).getText());
*/
        String sel = "tbody tr.uccRes td.rightCol";
        String amount = resultTable.findElement(By.cssSelector(sel)).getText();
        System.out.println("[amount] " + amount);
//        strip off currency name
        return amount.split(" ")[0];
    }
}
