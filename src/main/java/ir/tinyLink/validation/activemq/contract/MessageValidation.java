package ir.tinyLink.validation.activemq.contract;


import ir.pod.podBusinessPanel.model.modelSrv.ValidateBeanSrv;

public interface MessageValidation {

    /**
     *  Checks constrains (JSR-303) on dto models
     * @param obj
     * @param aClass
     * @return Boolean
     */
    public ValidateBeanSrv validateBean(Object obj, Class aClass);

    /**
     *  checks the validation of data based on pattern
     * @param pattern
     * @param message
     * @param data
     * @return
     */
    public ValidateBeanSrv validateRegex(String pattern, String message, String data);

}
