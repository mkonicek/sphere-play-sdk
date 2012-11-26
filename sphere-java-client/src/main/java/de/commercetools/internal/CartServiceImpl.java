package de.commercetools.internal;

import java.util.Currency;

import com.google.common.base.Optional;
import de.commercetools.internal.command.CartCommands;
import de.commercetools.internal.command.Command;
import de.commercetools.internal.request.RequestFactory;
import de.commercetools.sphere.client.FetchRequest;
import de.commercetools.sphere.client.QueryRequest;
import de.commercetools.sphere.client.model.Reference;
import de.commercetools.sphere.client.shop.AuthenticationResult;
import de.commercetools.sphere.client.shop.CartService;
import de.commercetools.sphere.client.shop.model.*;
import de.commercetools.sphere.client.model.QueryResult;
import de.commercetools.sphere.client.ProjectEndpoints;
import de.commercetools.sphere.client.CommandRequest;

import org.codehaus.jackson.type.TypeReference;

public class CartServiceImpl implements CartService {
    private ProjectEndpoints endpoints;
    private RequestFactory requestFactory;

    public CartServiceImpl(RequestFactory requestFactory, ProjectEndpoints endpoints) {
        this.requestFactory = requestFactory;
        this.endpoints = endpoints;
    }

    /** {@inheritDoc}  */
    public FetchRequest<Cart> byId(String id) {
        return requestFactory.createFetchRequest(endpoints.carts.byId(id), new TypeReference<Cart>() {
        });
    }

    /** {@inheritDoc}  */
    public FetchRequest<Cart> byCustomer(String customerId) {
        return requestFactory.createFetchRequest(endpoints.carts.byCustomer(customerId), new TypeReference<Cart>() {
        });
    }

    /** {@inheritDoc}  */
    public QueryRequest<Cart> all() {
        return requestFactory.createQueryRequest(endpoints.carts.root(), new TypeReference<QueryResult<Cart>>() {});
    }

    /** Helper to save some repetitive code in this class. */
    private CommandRequest<Cart> createCommandRequest(String url, Command command) {
        return requestFactory.createCommandRequest(url, command, new TypeReference<Cart>() {});
    }

    /** {@inheritDoc}  */
    public CommandRequest<Cart> createCart(Currency currency, String customerId) {
        return createCommandRequest(
                endpoints.carts.root(),
                new CartCommands.CreateCart(currency, customerId));
    }

    /** {@inheritDoc}  */
    public CommandRequest<Cart> createCart(Currency currency) {
        return createCart(currency, null);
    }

    /** {@inheritDoc}  */
    public CommandRequest<Cart> addLineItem(String cartId, int cartVersion, String productId, String variantId, int quantity, Reference catalog) {
        return createCommandRequest(
                endpoints.carts.addLineItem(),
                new CartCommands.AddLineItem(cartId, cartVersion, productId, quantity, variantId, catalog));
    }

    /** {@inheritDoc}  */
    public CommandRequest<Cart> removeLineItem(String cartId, int cartVersion, String lineItemId) {
        return createCommandRequest(
                endpoints.carts.removeLineItem(),
                new CartCommands.RemoveLineItem(cartId, cartVersion, lineItemId));
    }

    /** {@inheritDoc}  */
    public CommandRequest<Cart> updateLineItemQuantity(String cartId, int cartVersion, String lineItemId, int quantity) {
        return createCommandRequest(
                endpoints.carts.updateLineItemQuantity(),
                new CartCommands.UpdateLineItemQuantity(cartId, cartVersion, lineItemId, quantity));
    }

    /** {@inheritDoc}  */
    public CommandRequest<Cart> increaseLineItemQuantity(String cartId, int cartVersion, String lineItemId, int quantityAdded) {
        return createCommandRequest(
                endpoints.carts.increaseLineItemQuantity(),
                new CartCommands.IncreaseLineItemQuantity(cartId, cartVersion, lineItemId, quantityAdded));
    }

    /** {@inheritDoc}  */
    public CommandRequest<Cart> decreaseLineItemQuantity(String cartId, int cartVersion, String lineItemId, int quantityRemoved) {
        return createCommandRequest(
                endpoints.carts.decreaseLineItemQuantity(),
                new CartCommands.DecreaseLineItemQuantity(cartId, cartVersion, lineItemId, quantityRemoved));
    }

    /** {@inheritDoc}  */
    public CommandRequest<Cart> setShippingAddress(String cartId, int cartVersion, Address address) {
        return createCommandRequest(
                endpoints.carts.setShippingAddress(),
                new CartCommands.SetShippingAddress(cartId, cartVersion, address));
    }

    /** {@inheritDoc}  */
    public CommandRequest<Optional<AuthenticationResult>> loginWithAnonymousCart(String cartId, int cartVersion, String email, String password) {
        return requestFactory.createCommandRequestWithErrorHandling(
                endpoints.carts.loginWithAnonymousCart(),
                new CartCommands.LoginWithAnonymousCart(cartId, cartVersion, email, password),
                401,
                new TypeReference<AuthenticationResult>() {});
    }

    /** {@inheritDoc}  */
    public CommandRequest<Order> order(String cartId, int cartVersion, PaymentState paymentState) {
        return requestFactory.createCommandRequest(
                endpoints.carts.order(),
                new CartCommands.OrderCart(cartId, cartVersion, paymentState),
                new TypeReference<Order>() {});
    }

    /** {@inheritDoc}  */
    public CommandRequest<Order> order(String cartId, int cartVersion) {
        return order(cartId, cartVersion, null);
    }
}
