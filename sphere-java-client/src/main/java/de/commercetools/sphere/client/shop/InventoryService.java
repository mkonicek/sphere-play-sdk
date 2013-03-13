package de.commercetools.sphere.client.shop;

import de.commercetools.sphere.client.FetchRequest;
import de.commercetools.sphere.client.model.Reference;
import de.commercetools.sphere.client.shop.model.Catalog;
import de.commercetools.sphere.client.shop.model.InventoryEntry;

/** Sphere HTTP API for retrieving product inventory information. */
public interface InventoryService {

    /** Finds an inventory entry by id. */
    FetchRequest<InventoryEntry> byId(String id);

    /** Finds an inventory entry for given variant and a catalog.
     *
     * If {@code catalog} is null, the master catalog is addressed. */
    FetchRequest<InventoryEntry> byVariantInCatalog(String productId, String variantId, Reference<Catalog> catalog);

    /** Finds an inventory entry for given variant in the master catalog. */
    FetchRequest<InventoryEntry> byVariantInMasterCatalog(String productId, String variantId);
}
