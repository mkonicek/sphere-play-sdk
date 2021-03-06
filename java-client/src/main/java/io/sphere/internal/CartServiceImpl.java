package io.sphere.internal;

import java.util.Currency;
import io.sphere.client.CommandRequest;
import io.sphere.client.FetchRequest;
import io.sphere.client.ProjectEndpoints;
import io.sphere.client.QueryRequest;
import io.sphere.client.model.QueryResult;
import io.sphere.client.model.VersionedId;
import io.sphere.client.shop.ApiMode;
import io.sphere.client.shop.CartService;
import io.sphere.client.shop.model.Cart;
import io.sphere.client.shop.model.CartUpdate;
import io.sphere.internal.command.CartCommands;
import io.sphere.internal.command.Command;
import io.sphere.internal.command.UpdateCommand;
import io.sphere.internal.request.RequestFactory;
import com.google.common.base.Optional;
import com.neovisionaries.i18n.CountryCode;
import org.codehaus.jackson.type.TypeReference;

public class CartServiceImpl implements CartService {
    private ProjectEndpoints endpoints;
    private RequestFactory requestFactory;

    public CartServiceImpl(RequestFactory requestFactory, ProjectEndpoints endpoints) {
        this.requestFactory = requestFactory;
        this.endpoints = endpoints;
    }

    @Override public FetchRequest<Cart> byId(String id) {
        return requestFactory.createFetchRequest(
                endpoints.carts.byId(id),
                Optional.<ApiMode>absent(),
                new TypeReference<Cart>() {});
    }

    @Override public FetchRequest<Cart> forCustomer(String customerId) {
        return requestFactory.createFetchRequest(
                endpoints.carts.forCustomer(customerId),
                Optional.<ApiMode>absent(),
                new TypeReference<Cart>() {});
    }

    @Override public QueryRequest<Cart> all() {
        return requestFactory.createQueryRequest(
                endpoints.carts.root(),
                Optional.<ApiMode>absent(),
                new TypeReference<QueryResult<Cart>>() {});
    }

    @Override public CommandRequest<Cart> createCart(Currency currency, String customerId, CountryCode country, Cart.InventoryMode inventoryMode) {
        return createCommandRequest(
                endpoints.carts.root(),
                new CartCommands.CreateCart(currency, customerId, country, inventoryMode));
    }

    @Override public CommandRequest<Cart> createCart(Currency currency, String customerId, Cart.InventoryMode inventoryMode) {
        return createCommandRequest(
                endpoints.carts.root(),
                new CartCommands.CreateCart(currency, customerId, null, inventoryMode));
    }

    @Override public CommandRequest<Cart> createCart(Currency currency, Cart.InventoryMode inventoryMode) {
        return createCart(currency, null, null, inventoryMode);
    }

    @Override public CommandRequest<Cart> createCart(Currency currency, CountryCode country, Cart.InventoryMode inventoryMode) {
        return createCart(currency, null, country, inventoryMode);
    }

    @Override public CommandRequest<Cart> updateCart(VersionedId cartId, CartUpdate update) {
        return createCommandRequest(
                endpoints.carts.byId(cartId.getId()),
                new UpdateCommand<CartCommands.CartUpdateAction>(cartId.getVersion(), update));
    }

    /** Helper to save some repetitive code. */
    private CommandRequest<Cart> createCommandRequest(String url, Command command) {
        return requestFactory.createCommandRequest(url, command, new TypeReference<Cart>() {});
    }
}
