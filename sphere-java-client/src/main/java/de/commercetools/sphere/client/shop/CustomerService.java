package de.commercetools.sphere.client.shop;

import de.commercetools.sphere.client.CommandRequest;
import de.commercetools.sphere.client.QueryRequest;
import de.commercetools.sphere.client.shop.model.Address;
import de.commercetools.sphere.client.shop.model.Customer;
import de.commercetools.sphere.client.shop.model.CustomerToken;
import de.commercetools.sphere.client.shop.model.CustomerUpdate;

/** Sphere HTTP API for working with customers in a given project. */
public interface CustomerService extends BasicCustomerService {

    /** Returns a customer that matches the given credentials.
     * Returns null if a customer with the credentials does not exist. */
    public QueryRequest<Customer> login(String email, String password);

    /** Creates a new customer. */
    public CommandRequest<Customer> signup(String email,
                                           String password,
                                           String firstName,
                                           String lastName,
                                           String middleName,
                                           String title);

    /** Sets a new customer password. */
    public CommandRequest<Customer> changePassword(String customerId,
                                                   int customerVersion,
                                                   String currentPassword,
                                                   String newPassword);

    /** The address in shippingAddresses list referenced by addressIndex is replaced with the given address. */
    public CommandRequest<Customer> changeShippingAddress(String customerId,
                                                          int customerVersion,
                                                          int addressIndex,
                                                          Address address);

    /** Removes the address in shippingAddresses list referenced by addressIndex. */
    public CommandRequest<Customer> removeShippingAddress(String customerId,
                                                          int customerVersion,
                                                          int addressIndex);

    /** The Customer.defaultShippingAddress is set to addressIndex. */
    public CommandRequest<Customer> setDefaultShippingAddress(String customerId,
                                                              int customerVersion,
                                                              int addressIndex);

    /** Updates a customer with the CustomerUpdate object. */
    public CommandRequest<Customer> updateCustomer(String customerId,
                                                   int customerVersion,
                                                   CustomerUpdate customerUpdate);


    /** Sets a new password with a token. Token must be previously generated by the createPasswordResetToken method. */
    public CommandRequest<Customer> resetPassword(String customerId, int customerVersion, String tokenValue, String newPassword);

    /** Creates a token used to verify customers email (set the Customer.isEmailVerified to true).
     * The ttlMinutes sets the time-to-live of the token in minutes. The token becomes invalid after the ttl expires.
     * Maximum ttlMinutes value can be 1 month. The created token is then used with the verifyEmail method.
     *
     * Customer's email could be verified as follows:
     *   1. Customer click on a verify email button.
     *   2. A token is created with the createEmailVerificationToken for the current customer.
     *   3. A link containing the token is sent to the customer by email.
     *   4. The link points to a page where the customer has to log in (if not already logged in) which calls the
     *      verifyEmail command with the customer and the token value extracted from the link. */
    public CommandRequest<CustomerToken> createEmailVerificationToken(String customerId, int customerVersion, int ttlMinutes);

    /** Sets the Customer.isEmailVerified to true. It requeires a token that was created with the
     * createEmailVerificationToken method. */
    public CommandRequest<Customer> verifyEmail(String customerId, int customerVersion, String tokenValue);

}