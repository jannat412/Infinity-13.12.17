package com.infinitymegamall.infinity.model;

/**
 * Created by shuvo on 13-Jan-18.
 */

public class ChildCategory {
    String category;
    String id;

    public ChildCategory(String id,String category ) {
        this.category = category;
        this.id = id;
    }

    public ChildCategory() {

    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChildCategory)) return false;

        ChildCategory that = (ChildCategory) o;

        if (getCategory() != null ? !getCategory().equals(that.getCategory()) : that.getCategory() != null)
            return false;
        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        int result = getCategory() != null ? getCategory().hashCode() : 0;
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        return result;
    }
}
