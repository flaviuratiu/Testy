package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RadioGroup extends WebLocator {
    private static final Logger LOGGER = LoggerFactory.getLogger(RadioGroup.class);

    private Radio radio = new Radio(this);

    public RadioGroup() {
        withClassName("RadioGroup");
        withAttribute("role", "radiogroup");
    }

    public RadioGroup(WebLocator container) {
        this();
        withContainer(container);
    }

    public RadioGroup(WebLocator container, String name) {
        this(container);
        radio.withName(name);
    }

    public boolean selectByLabel(String label) {
        return selectByLabel(label, SearchType.EQUALS);
    }

    public boolean selectByLabel(String label, SearchType searchType) {
        radio.withLabel(label, searchType);
        boolean selected = !radio.isSelected() || radio.click();
        radio.withLabel(null);
        return selected;
    }

    public String getLabelName(String label) {
        WebLocator locator = new WebLocator(radio).withElxPath("/following-sibling::label[contains(text(),'" + label + "')]");
        return locator.getText();
    }

//    public boolean isDisabled() {
//        WebLocator locator = new WebLocator().withElxPath(radio.getXPath(true));
//        return locator.exists();
//    }
}