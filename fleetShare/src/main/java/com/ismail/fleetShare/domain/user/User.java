package com.ismail.fleetShare.domain.user;

/**
 * Represents a user in the system.
 * Stores user ID, name, and membership tier.
 */
public class User {

    private final String id;
    private final String name;
    private MembershipTier membershipTier;

    /**
     * Creates a new user with the given ID, name, and membership tier.
     *
     * @param id the unique identifier of the user
     * @param name the name of the user
     * @param membershipTier the membership tier of the user
     */
    public User(String id, String name, MembershipTier membershipTier)
    {
        this.id = id;
        this.name = name;
        this.membershipTier = membershipTier;
    }

    /**
     * Returns the unique ID of the user.
     *
     * @return the user ID
     */
    public String getId()
    {
        return id;
    }

    /**
     * Returns the name of the user.
     *
     * @return the user name
     */
    public String getName() {

        return name;
    }

    /**
     * Returns the membership tier of the user.
     *
     * @return the membership tier
     */
    public MembershipTier getMembershipTier()
    {
        return membershipTier;
    }

    /**
     * Sets the membership tier of the user.
     *
     * @param membershipTier the new membership tier
     */
    public void setMembershipTier(MembershipTier membershipTier)
    {
        this.membershipTier = membershipTier;
    }
}
