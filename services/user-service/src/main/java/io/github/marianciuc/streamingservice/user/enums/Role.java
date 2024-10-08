package io.github.marianciuc.streamingservice.user.enums;

/**
 * The {@code Role} enum represents the different roles
 * that a user can have in the system.
 */
public enum Role {
    /**
     * Represents the ADMIN role in the system.
     * {@code MANAGE_USERS},
     * {@code MANAGE_CONTENT},
     * {@code VIEW_CONTENT},
     * {@code EDIT_CUSTOMER_PROFILE},
     * {@code MANAGE_SUBSCRIPTIONS},
     * {@code MODERATE_COMMENTS},
     * {@code HANDLE_CUSTOMER_REQUESTS},
     * {@code MANAGE_INTERNAL_RESOURCES},
     * {@code POST_COMMENTS}
     */
    ROLE_ADMIN("admin"),

    /**
     * Represents a role of a content moderator in the system.
     * {@code MANAGE_CONTENT},
     * {@code VIEW_CONTENT},
     * {@code MODERATE_COMMENTS},
     * {@code POST_COMMENTS}
     */
    ROLE_CONTENT_MODERATOR("content_moderator"),
    /**
     * Represents a role of a support staff in the system.
     * {@code VIEW_CONTENT},
     * {@code EDIT_CUSTOMER_PROFILE},
     * {@code MANAGE_SUBSCRIPTIONS},
     * {@code HANDLE_CUSTOMER_REQUESTS}
     */
    ROLE_SUPPORT_STAFF("support_staff"),
    /**
     * Represents a role of an unsubscribed user in the system.
     * {@code MANAGE_SUBSCRIPTIONS},
     * {@code EDIT_CUSTOMER_PROFILE},
     * <p>
     * Possible values:
     * - unsubscribed_user
     */
    ROLE_UNSUBSCRIBED_USER("unsubscribed_user"),
    /**
     * Represents a role of a subscribed user in the system.
     * {@code MANAGE_SUBSCRIPTIONS}
     * {@code VIEW_CONTENT},
     * {@code EDIT_CUSTOMER_PROFILE},
     * <p>
     * Possible values:
     * - subscribed_user
     */
    ROLE_SUBSCRIBED_USER("subscribed_user"),
    ROLE_SERVICE("service");

    public final String roleName;

    /**
     * Initializes a new instance of the {@code Role} class with the specified role name.
     *
     * @param roleName the name of the role
     */
    Role(String roleName) {
        this.roleName = roleName;
    }
}