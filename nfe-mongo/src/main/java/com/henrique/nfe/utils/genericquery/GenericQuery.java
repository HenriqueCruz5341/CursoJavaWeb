package com.henrique.nfe.utils.genericquery;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;

public class GenericQuery {
    private List<SearchCriteria> list;

    public GenericQuery() {
        this.list = new ArrayList<>();
    }

    public void add(SearchCriteria criteria) {
        this.list.add(criteria);
    }

    public Criteria generate() {
        Criteria criteria = new Criteria();

        list.stream().forEach(searchCriteria -> {
            if (searchCriteria.getOperation().equals(SearchOperation.GREATER_THAN))
                criteria.and(searchCriteria.getKey()).gt(searchCriteria.getValue());
            else if (searchCriteria.getOperation().equals(SearchOperation.LESS_THAN))
                criteria.and(searchCriteria.getKey()).lt(searchCriteria.getValue());
            else if (searchCriteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL))
                criteria.and(searchCriteria.getKey()).gte(searchCriteria.getValue());
            else if (searchCriteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL))
                criteria.and(searchCriteria.getKey()).lte(searchCriteria.getValue());
            else if (searchCriteria.getOperation().equals(SearchOperation.NOT_EQUAL))
                criteria.and(searchCriteria.getKey()).ne(searchCriteria.getValue());
            else if (searchCriteria.getOperation().equals(SearchOperation.EQUAL))
                criteria.and(searchCriteria.getKey()).is(searchCriteria.getValue());
            else if (searchCriteria.getOperation().equals(SearchOperation.MATCH))
                criteria.and(searchCriteria.getKey()).regex("(?i)" + searchCriteria.getValue().toString());
            else if (searchCriteria.getOperation().equals(SearchOperation.MATCH_START))
                criteria.and(searchCriteria.getKey()).regex("(?i)^" + searchCriteria.getValue().toString());
            else if (searchCriteria.getOperation().equals(SearchOperation.MATCH_END))
                criteria.and(searchCriteria.getKey()).regex("(?i)" + searchCriteria.getValue().toString() + "$");
            else if (searchCriteria.getOperation().equals(SearchOperation.IN)
                    && searchCriteria.getValue() instanceof List)
                criteria.and(searchCriteria.getKey()).in((List<String>) searchCriteria.getValue());

        });

        return criteria;
    }
}
