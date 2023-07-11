package com.coinlibrary.backend.specification;

import com.coinlibrary.backend.model.Coin;
import org.springframework.data.jpa.domain.Specification;

public class CoinSpecification {

    public static Specification<Coin> isSize(int size) {
        return (root, query, builder) -> builder.equal(root.get("size"), size);
    }

    public static Specification<Coin> isEdition(int editionId) {
        return (root, query, builder) -> builder.equal(root.get("edition").get("id"), editionId);
    }
}
