package io.sphere.internal;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.sphere.client.shop.model.Category;

import java.util.*;

public class CategoryCache {
    private ImmutableList<Category> roots;
    private ImmutableList<Category> all;
    private ImmutableMap<String, Category> byIdMap;
    private ImmutableMap<String, Category> bySlugMap;

    private CategoryCache(
            ImmutableList<Category> roots,
            ImmutableList<Category> all,
            ImmutableMap<String, Category> categoriesById,
            ImmutableMap<String, Category> categoriesBySlug) {
        this.roots = roots;
        this.byIdMap = categoriesById;
        this.bySlugMap = categoriesBySlug;
        this.all = all;
    }

    /** Caches category tree in multiple different ways for fast lookup. */
    public static CategoryCache create(Iterable<Category> roots, Locale locale) {
        List<Category> all = getAllRecursive(roots);
        return new CategoryCache(ImmutableList.copyOf(roots), sortByName(all, locale), buildByIdMap(all), buildBySlugMap(all, locale));
    }

    public List<Category> getRoots() { return roots; }
    public Category getById(String id) { return byIdMap.get(id); }
    public Category getBySlug(String slug) { return bySlugMap.get(slug); }
    public List<Category> getAsFlatList() { return all; }

    // --------------------------------------------------
    // Helpers for create()
    // --------------------------------------------------

    private static List<Category> getAllRecursive(Iterable<Category> categories) {
        List<Category> result = new ArrayList<Category>();
        for (Category c: categories) {
            result.add(c);
            for (Category child: getAllRecursive(c.getChildren())) {
                result.add(child);
            }
        }
        return result;
    }

    private static ImmutableMap<String, Category> buildByIdMap(Collection<Category> categories) {
        Map<String, Category> map = new HashMap<String, Category>(categories.size());
        for (Category c: categories) {
            map.put(c.getId(), c);
        }
        return ImmutableMap.copyOf(map);
    }

    private static ImmutableMap<String, Category> buildBySlugMap(Collection<Category> categories, Locale locale) {
        Map<String, Category> map = new HashMap<String, Category>(categories.size());
        for (Category category: categories) {
            map.put(category.getSlug(locale), category);
        }
        return ImmutableMap.copyOf(map);
    }


    private static ImmutableList<Category> sortByName(Collection<Category> categories, Locale locale) {
        List<Category> categoriesCopy = new ArrayList<Category>(categories);
        Collections.sort(categoriesCopy, new ByNameComparator(locale));
        return ImmutableList.copyOf(categoriesCopy);
    }

    private static class ByNameComparator implements Comparator<Category> {
        private final Locale locale;

        private ByNameComparator(Locale locale) { this.locale = locale; }

        @Override public int compare(Category category, Category other) {
            if (category == null && other == null) return 0;
            if (category == null && other != null) return -1;
            if (category != null && other == null) return 1;
            return category.getName(locale).compareTo(other.getName(locale));
        }

        /** Indicates whether some other object is equal to this comparator. */
        @Override public boolean equals(Object other) {
            return this == other;
        }
    }
}
