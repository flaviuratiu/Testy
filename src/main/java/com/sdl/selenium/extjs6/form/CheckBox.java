package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.ICheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckBox extends WebLocator implements ICheck {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckBox.class);

    public CheckBox() {
        withClassName("CheckBox");
        withBaseCls("x-form-checkbox");
    }

    public CheckBox(WebLocator container) {
        this();
        withContainer(container);
    }

    public CheckBox(WebLocator container, String label) {
        this(container);
        withLabel(label, SearchType.CONTAINS);
    }

    public CheckBox(WebLocator container, String label, boolean isInternationalized) {
        this(container);
        withLabel(label, isInternationalized, SearchType.CONTAINS);
    }

    public CheckBox(String boxLabel, WebLocator container) {
        this(container);
        withLabel(boxLabel);
        withLabelPosition("/../");
    }

    public CheckBox(String boxLabel, boolean isInternationalized, WebLocator container) {
        this(container);
        withLabel(boxLabel, isInternationalized);
        withLabelPosition("/../");
    }

    @Override
    public boolean isSelected() {
        WebLocator el = new WebLocator(this).withElxPath("/../input");
        String select = el.getAttribute("aria-checked");
        return select != null && select.contains("true");
    }

    @Override
    public boolean isDisabled() {
        String cls = getAttributeClass();
        return (cls != null && cls.contains("disabled")) || getAttribute("disabled") != null;
    }
}