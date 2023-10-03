package ru.practicum.discriptions;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MessageManager {
    public String internalServerError = "INTERNAL_SERVER_ERROR";
    public String unexpectedError = "An unexpected error occurred.";
    public String notFound = "NOT_FOUND";
    public String badRequest = "BAD_REQUEST";
    public String conflict = "CONFLICT";
    public String forbidden = "FORBIDDEN";
    public String requestIncorrectly = "Incorrectly made request.";
    public String endBeforeStart = "The start date must be Before the end date.";
    public String receivedGet = "Request received GET {}";
    public String receivedGetId = "Request received GET {}/{}";
    public String receivedPost = "Request received POST {}";
    public String receivedPostId = "Request received POST {}/{}";
    public String receivedPatch = "Request received PATCH {}/{}";
    public String receivedDelete = "Request received DELETE {}/{}";
    public String dateStartAfterEnd = "Date start is after date end.";
    public String requiredNotFound = "The required object was not found.";
    public String integrityConstraint = "Integrity constraint has been violated.";
    public String wrongConditions = "For the requested operation the conditions are not met.";
    public String notValid = "Parameter sort is not valid";
    public String wrongStates = "Wrong states!";
    public String onlyChangedEvents = "Only pending or canceled events can be changed";
    public String cannotCreateTheEvent2Hours = "Cannot create the event, because less 2 hours before event datetime";
    public String cannotCreateTheEvent1Hours = "Cannot create the event, because less 2 hours before event datetime";
    public String cannotPublisherNotInRightState = "Cannot publish the event because it's not in the right state: ";
    public String cannotRejectAlreadyPublisher = "Cannot reject the event because it's already published";
    public String categooryNoEmply = "The category is not empty";
}