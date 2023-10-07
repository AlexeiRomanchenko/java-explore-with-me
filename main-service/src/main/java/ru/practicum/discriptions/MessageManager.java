package ru.practicum.discriptions;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MessageManager {
    public final String internalServerError = "INTERNAL_SERVER_ERROR";
    public final String unexpectedError = "An unexpected error occurred.";
    public final String notFound = "NOT_FOUND";
    public final String badRequest = "BAD_REQUEST";
    public final String conflict = "CONFLICT";
    public final String forbidden = "FORBIDDEN";
    public final String requestIncorrectly = "Incorrectly made request.";
    public final String receivedGet = "Request received GET {}";
    public final String receivedGetId = "Request received GET {}/{}";
    public final String receivedPost = "Request received POST {}";
    public final String receivedPostId = "Request received POST {}/{}";
    public final String receivedPatch = "Request received PATCH {}/{}";
    public final String receivedDelete = "Request received DELETE {}/{}";
    public final String dateStartAfterEnd = "Date start is after date end.";
    public final String requiredNotFound = "The required object was not found.";
    public final String integrityConstraint = "Integrity constraint has been violated.";
    public final String wrongConditions = "For the requested operation the conditions are not met.";
    public final String notValid = "Parameter sort is not valid";
    public final String onlyChangedEvents = "Only pending or canceled events can be changed";
    public final String cannotCreateTheEvent2Hours = "Cannot create the event, because less 2 hours before event datetime";
    public final String cannotCreateTheEvent1Hours = "Cannot create the event, because less 2 hours before event datetime";
    public final String cannotPublisherNotInRightState = "Cannot publish the event because it's not in the right state: ";
    public final String cannotRejectAlreadyPublisher = "Cannot reject the event because it's already published";
    public final String categooryNoEmply = "The category is not empty";
}