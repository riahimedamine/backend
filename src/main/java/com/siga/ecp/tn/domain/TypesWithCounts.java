package com.siga.ecp.tn.domain;

public class TypesWithCounts {

    private TypeConge type;

    private Integer count;

    public TypesWithCounts() {
        // Empty constructor needed for Jackson.
    }

    public TypesWithCounts(TypeConge type, Integer count) {
        this.type = type;
        this.count = count;
    }

    @Override
    public String toString() {
        return "TopTypesWithCounts{" +
            "type='" + type + '\'' +
            ", count=" + count +
            '}';
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public TypeConge getType() {
        return type;
    }

    public void setType(TypeConge type) {
        this.type = type;
    }

}
