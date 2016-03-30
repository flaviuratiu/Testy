package com.sdl.selenium.extjs6.tab;

import com.sdl.selenium.conditions.ConditionManager;
import com.sdl.selenium.conditions.RenderSuccessCondition;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.XPathBuilder;
import com.sdl.selenium.web.link.WebLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tab extends WebLocator {
    private static final Logger LOGGER = LoggerFactory.getLogger(Tab.class);

    public Tab() {
        withClassName("TabPanel");
        withBaseCls("x-tabpanel-child");
        WebLink activeTab = new WebLink().withClasses("x-tab-active");
        withTemplateTitle(new WebLocator(activeTab));
    }

    public Tab(String title) {
        this();
        withTitle(title, SearchType.EQUALS, SearchType.DEEP_CHILD_NODE);
    }

    public Tab(WebLocator container) {
        this();
        withContainer(container);
    }

    public Tab(WebLocator container, String text) {
        this(text);
        withContainer(container);
    }

    private WebLocator getTitleInactiveEl() {
        WebLocator container = new WebLocator(getPathBuilder().getContainer()).withClasses(getPathBuilder().getBaseCls());
//        SearchType[] st = getPathBuilder().getSearchTextType().toArray(new SearchType[getPathBuilder().getSearchTextType().size() + 1]);
        return new WebLink(container).withText(getPathBuilder().getTitle(), SearchType.DEEP_CHILD_NODE, SearchType.EQUALS).withExcludeClasses("x-tab-active")
                .withInfoMessage(getPathBuilder().getTitle() + " Tab");
    }

    /**
     * this method return the path of the main Tab (that contains also this Tab/Panel)
     *
     * @return the path of the main TabPanel
     */
    private String getBaseTabPanelPath() {
        String selector = getPathBuilder().getBasePath();
        return getPathBuilder().getRoot() + getPathBuilder().getTag() + "[" + selector + "]";
    }

    protected XPathBuilder createXPathBuilder() {
        return new XPathBuilder() {
            /**
             * this method return the path of only one visible div from the main TabPanel
             * @param disabled disabled
             * @return the path of only one visible div from the main TabPanel
             */
            @Override
            public String getItemPath(boolean disabled) {
                WebLocator body = new WebLocator().withTag("following-sibling::*").withClasses("x-panel-body");
                WebLocator tab = new WebLocator(body).withRoot("/").withExcludeClasses("x-hidden-offsets").withClasses("x-tabpanel-child");
                return  getBaseTabPanelPath() + tab.getXPath();
            }
        };
    }

    /**
     * After the tab is set to active
     *
     * @return true or false
     */
    public boolean setActive() {
        boolean activated = isActive() || getTitleInactiveEl().click();
        if (activated) {
            LOGGER.info("setActive : " + toString());
        }
        return activated;
    }

    public boolean isActive() {
        return new ConditionManager(200).add(new RenderSuccessCondition(this)).execute().isSuccess();
    }

//    public static void main(String[] args) {
//        Tab tab = new Tab("API Keys");
//        LOGGER.debug(tab.getXPath());
//    }
}
