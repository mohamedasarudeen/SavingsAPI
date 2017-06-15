package com.temenos.interaction.test;

/**
 * Defines the T24 related constants being used across tests.
 *
 * @author ssethupathi
 *
 */
public class T24Constants {
    public enum Action {
        INPUT_ACTION("I"), AUTHORISE_ACTION("A"), REVERSE_ACTION("R"), DELETE_ACTION("D"), HISTORY_ACTION("H");
        private String value;

        Action(String value) {
            this.value = value;
        }
        public String getValue(){
            return this.value;
        }
    }
    
    public static final String RECORD_STATUS = "RecordStatus";
    public static final String RECORD_STATUS_REVERSE_UNAUTHORISED = "RNAU";
    public static final String RECORD_STATUS_REVERSE = "REVE";
    public static final String RECORD_STATUS_INPUT_UNAUTHORISED = "INAU";
    public static final String RECORD_STATUS_INPUT_HOLD = "IHLD";
    public static final String RECORD_STATUS_LIVE = "";

    

    public static final String ERROR_ELEMENT = "Errors_ErrorsMvGroup";
    public static final String ERROR_CODE = ERROR_ELEMENT + "(%s)/Code";
    public static final String ERROR_TYPE = ERROR_ELEMENT + "(%s)/Type";
    public static final String ERROR_TEXT = ERROR_ELEMENT + "(%s)/Text";
    public static final String ERROR_INFO = ERROR_ELEMENT + "(%s)/Info";

    public static final String MESSAGE_ELEMENT = "Messages_MessagesMvGroup";
    public static final String MESSAGE_CODE = MESSAGE_ELEMENT + "(%s)/Code";
    public static final String MESSAGE_TYPE = MESSAGE_ELEMENT + "(%s)/Type";
    public static final String MESSAGE_TEXT = MESSAGE_ELEMENT + "(%s)/Text";
    public static final String MESSAGE_INFO = MESSAGE_ELEMENT + "(%s)/Info";
}
