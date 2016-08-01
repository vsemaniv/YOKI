package com.cusbee.yoki.entity;

/**
 * Represents all entities that could be enabled or disabled
 */
public interface Activatable extends BaseEntity {

    Boolean getEnabled();

    void setEnabled(Boolean enabled);
}
