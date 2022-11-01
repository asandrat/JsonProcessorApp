package org.json.filter.model;

import lombok.Data;
import java.util.List;

@Data
public class Filter {

    private String filterId;
    private String filterGroupId;
    private List<String> parameters;
}
