package org.example.services;

import java.util.Map;

public class ValidationResult<Entity> {
    private Entity entity;
    private Map<String, String> errors;

    public ValidationResult(Entity entity, Map<String, String> errors) {
        this.entity = entity;
        this.errors = errors;
    }

    public Entity getEntity() {
        return entity;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
