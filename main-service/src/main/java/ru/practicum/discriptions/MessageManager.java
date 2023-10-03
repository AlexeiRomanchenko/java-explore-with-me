package ru.practicum.discriptions;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MessageManager {
    public final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";
    public final String UNEXPECTED_ERROR = "An unexpected error occurred.";
    public final String NOT_FOUND = "NOT_FOUND";
    public final String BAD_REQUEST = "BAD_REQUEST";
    public final String CONFLICT = "CONFLICT";
    public final String FORBIDDEN = "FORBIDDEN";
    public final String REQUEST_INCORRECTLY = "Incorrectly made request.";
    public final String END_BEFORE_START = "The start date must be Before the end date.";
    public final String RECEIVED_GET = "Request received GET {}";
    public final String RECEIVED_GET_ID = "Request received GET {}/{}";
    public final String RECEIVED_POST = "Request received POST {}";
    public final String RECEIVED_POST_ID = "Request received POST {}/{}";
    public final String RECEIVED_PATCH = "Request received PATCH {}/{}";
    public final String RECEIVED_DELETE = "Request received DELETE {}/{}";
    public final String INCORRECT_DATA = "Incorrectly made request.";
    public final String DATE_START_AFTER_END = "Date start is after date end.";
    public final String REQUIRED_NOT_FOUND = "The required object was not found.";
    public final String INTEGRITY_CONSTRAINT = "Integrity constraint has been violated.";
    public final String WRONG_CONDITIONS = "For the requested operation the conditions are not met.";
    public final String INCORRECT_OFFSET = "The start element of the page cannot be less than zero.";
    public final String INCORRECT_LIMIT = "Page limit cannot be less than or equal to zero.";
    public final String NOT_VALID = "Parameter sort is not valid";
    public final String WRONG_STATES = "Wrong states!";
    public final String ONLY_CHANGED_EVENTS = "Only pending or canceled events can be changed";
    public final String CANNOT_CREATE_THE_EVENT_2_HOURS = "Cannot create the event, because less 2 hours before event datetime";
    public final String CANNOT_CREATE_THE_EVENT_1_HOURS = "Cannot create the event, because less 2 hours before event datetime";
    public final String CANNOT_PUBLISHER_NOT_IN_RIGHT_STATE = "Cannot publish the event because it's not in the right state: ";
    public final String CANNOT_REJECT_ALREADY_PUBLISHER = "Cannot reject the event because it's already published";
    public final String CATEGOORY_NO_EMPLY = "The category is not empty";
}