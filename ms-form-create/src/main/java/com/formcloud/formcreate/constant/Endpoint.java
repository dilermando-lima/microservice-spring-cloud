package com.formcloud.formcreate.constant;

public interface Endpoint {

    final public static String TAG_COMMMAND_OPER = "Command operations";
    final public static String TAG_ALTER_STATUS_FORM = "Alter states of forms";
    final public static String TAG_QUERY_OPER = "Reading operations";

    final public static String FORM_CREATE_TITLE = "Create a new form with no question associated yet";
    final public static String FORM_CLONE_TITLE = "Clone a new form from an existed form and pull all question";
    final public static String FORM_DRAFT_TITLE = "Create a new version as draft from an existed form and pull all question ";
    final public static String FORM_PUBLISH_TITLE = "Publish a draft form";
    final public static String FORM_UNPUBLISH_TITLE = "Unpublish a form already published";
    final public static String FORM_DISABLE_VERSION_TITLE = "Disable a form in specific version";
    final public static String FORM_DISABLE_ALL_VERSION_TITLE = "Disable all version of specific form";

    final public static String FORM_REMOVE_VERSION_TITLE = "Delete a disabled form in specific version";
    final public static String FORM_REMOVE_ALL_VERSION_TITLE = "Delete all disabled version of specific form";

    final public static String FORM_UPDATE_TITLE = "Alter basic information of form";
    final public static String FORM_LIST_TITLE = "List all forms with filters and pagination";
    final public static String FORM_GET_BY_KEY_AND_VERSION_TITLE = "Get a specific form by key and version";
    final public static String FORM_INSERT_LIST_QUESTION = "Insert a list of question into a specific form";
    final public static String FORM_LIST_QUESTION = "List all question from a specific form";
}
