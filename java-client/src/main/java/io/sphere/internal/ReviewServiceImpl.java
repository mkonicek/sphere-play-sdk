package io.sphere.internal;

import io.sphere.client.CommandRequest;
import io.sphere.client.FetchRequest;
import io.sphere.client.ProjectEndpoints;
import io.sphere.client.QueryRequest;
import io.sphere.client.model.QueryResult;
import io.sphere.client.model.VersionedId;
import io.sphere.client.shop.ApiMode;
import io.sphere.client.shop.ReviewService;
import io.sphere.client.shop.model.Review;
import io.sphere.client.shop.model.ReviewUpdate;
import io.sphere.internal.command.Command;
import io.sphere.internal.command.ReviewCommands;
import io.sphere.internal.command.UpdateCommand;
import io.sphere.internal.request.RequestFactory;
import com.google.common.base.Optional;
import org.codehaus.jackson.type.TypeReference;

public class ReviewServiceImpl extends ProjectScopedAPI implements ReviewService {
    private final RequestFactory requestFactory;

    public ReviewServiceImpl(RequestFactory requestFactory, ProjectEndpoints endpoints) {
        super(endpoints);
        this.requestFactory = requestFactory;
    }

    @Override public FetchRequest<Review> byId(String id) {
        return requestFactory.createFetchRequest(
                endpoints.reviews.byId(id),
                Optional.<ApiMode>absent(),
                new TypeReference<Review>() {});
    }

    @Override public QueryRequest<Review> all() {
        return requestFactory.createQueryRequest(
                endpoints.reviews.root(),
                Optional.<ApiMode>absent(),
                new TypeReference<QueryResult<Review>>() {});
    }

    @Override public QueryRequest<Review> forCustomer(String customerId) {
        return requestFactory.createQueryRequest(
                endpoints.reviews.queryByCustomerId(customerId),
                Optional.<ApiMode>absent(),
                new TypeReference<QueryResult<Review>>() {});
    }

    @Override public QueryRequest<Review> forCustomerAndProduct(String customerId, String productId) {
        return requestFactory.createQueryRequest(
                endpoints.reviews.queryByCustomerIdProductId(customerId, productId),
                Optional.<ApiMode>absent(),
                new TypeReference<QueryResult<Review>>() {});
    }

    @Override public QueryRequest<Review> forProduct(String productId) {
        return requestFactory.createQueryRequest(
                endpoints.reviews.queryByProductId(productId),
                Optional.<ApiMode>absent(),
                new TypeReference<QueryResult<Review>>() {});
    }

    @Override public CommandRequest<Review> createReview(
            String productId, String customerId, String authorName, String title, String text, Double score) {
        return createCommandRequest(
                endpoints.reviews.root(),
                new ReviewCommands.CreateReview(productId, customerId, authorName, title, text, score));
    }

    @Override public CommandRequest<Review> updateReview(VersionedId reviewId, ReviewUpdate update) {
        return createCommandRequest(
                endpoints.reviews.byId(reviewId.getId()),
                new UpdateCommand<ReviewCommands.ReviewUpdateAction>(reviewId.getVersion(), update));
    }

    /** Helper to save some repetitive code in this class. */
    private CommandRequest<Review> createCommandRequest(String url, Command command) {
        return requestFactory.createCommandRequest(url, command, new TypeReference<Review>() {});
    }
}
