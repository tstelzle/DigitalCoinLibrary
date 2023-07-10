package com.coinlibrary.backend.specification;

import com.coinlibrary.backend.model.Edition;
import org.springframework.data.jpa.domain.Specification;

public class EditionSpecification {
    public static Specification<Edition> hasCountry(String country) {
        return (root, query, builder) -> builder.equal(root.get("country"), country);
    }

    public static Specification<Edition> hasEdition(Integer edition) {
        return (root, query, builder) -> builder.equal(root.get("edition"), edition);
    }

    public static Specification<Edition> isSpecial() {
        return ((root, query, builder) -> builder.equal(root.get("edition"), 0));
    }
}

