package com.sdl.selenium.conditions;

import com.extjs.selenium.Utils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Example how to add conditions to ConditionManager:
 * <pre>
ConditionManager manager = new ConditionManager().add(new SuccessCondition() {
    public boolean execute() {
        return logoutButton.isElementPresent();
    }
});
 * </pre>
 * OR more specific cases:
 * <pre>
 ConditionManager manager = new ConditionManager();
 manager.add(
     new MessageBoxFailCondition("Wrong email or password.")).add(
     new RenderSuccessCondition(logoutButton)
 );
 Condition condition = manager.execute();
 logged = condition.isSuccess();
 </pre>
 */
public class ConditionManager  {

    private static final Logger logger = Logger.getLogger(ConditionManager.class);

    public static int SLEEP_INTERVAL = 50;

    private long timeout = 10000;

    private long startTime;

    private List<Condition> conditionList = new ArrayList<Condition>();

    /**
     * default timeout in milliseconds is 10000.
     */
    public ConditionManager() {
        this.add(new FailCondition("@TimeoutCondition@") {
            public boolean execute() {
                return new Date().getTime() - startTime > timeout;
            }
            public boolean isTimeout(){
                return true;
            }
            public String toString() {
                return "TimeoutCondition@" + timeout;
            }
        });
    }

    /**
     * @param timeout milliseconds
     */
    public ConditionManager(long timeout) {
        this();
        this.timeout = timeout;
    }

    public ConditionManager add(Condition condition) {
        //logger.debug("ConditionManager add condition : " + condition);
        conditionList.add(condition);
        return this;
    }

    public ConditionManager remove(Condition condition) {
        //logger.debug("ConditionManager remove condition : " + condition);
        conditionList.remove(condition);
        return this;
    }

    public ConditionManager removeConditions(String... messages){
        for (String message : messages){
            removeCondition(message);
        }
        return this;
    }

    public ConditionManager removeCondition(String message){
        if(message != null && message.length() > 0){
            for (Condition condition : conditionList) {
                if(condition.equals(message)){
                    conditionList.remove(condition);
                    break;
                }
            }
        }
        return this;
    }

    public long getTimeout() {
        return timeout;
    }

    /**
     *
     * @param timeout milliseconds
     */
    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public Condition execute() {

        startTime = new Date().getTime();
        Collections.sort(conditionList);
        while (true) {
            for (Condition condition : conditionList) {
                if (condition.execute()) {
                    logger.debug(condition + " - executed");
                    // TODO we could add press OK on MessageBoxFailCondition and MessageBoxSuccessCondition
                    return condition; //TODO set timeout when don't execute any condition Matei.
                }
//                logger.debug(condition + " is false");
            }
            Utils.sleep(SLEEP_INTERVAL);
        }
    }
}