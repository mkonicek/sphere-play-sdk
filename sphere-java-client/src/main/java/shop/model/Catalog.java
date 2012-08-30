package de.commercetools.sphere.client.shop.model;

import de.commercetools.sphere.client.model.Reference;

/** A Catalog is a grouping of products from the master catalog for a certain
 * purpose or under a certain theme.
 *
 * Catalogs are used to organize workflows within the lifecycle of a shop (eg staging
 * and online catalogs for different seasons) as well as to organize multiple published
 * catalogs, e.g. distinguished by locale/country (e.g. shop.com, shop.de, ...).
 *
 * Catalogs exist separately from the master catalog. The master catalog is an
 * implicit concept and is comprised of all Products in a Project. Thus, the
 * master catalog cannot be published and consequently a Project must have at
 * least one Catalog in order to publish its Products. */
public class Catalog {
    private String id;
    private String version;
    private String name;
    private String description;
    private Reference<Category> rootCategory = Reference.empty("rootCategory");

    // for JSON deserializer
    private Catalog() { }

    /** Unique id of this catalog. */
    public String getId() {
        return id;
    }
    /** Version of this catalog that increases when the catalog is changed. */
    public String getVersion() {
        return version;
    }
    /** Display name of this catalog. */
    public String getName() {
        return name;
    }
    /** Description for this catalog. */
    public String getDescription() {
        return description;
    }
    /** Root of the category tree of this catalog. */
    public Reference<Category> getRootCategory() {
        return rootCategory;
    }
}
