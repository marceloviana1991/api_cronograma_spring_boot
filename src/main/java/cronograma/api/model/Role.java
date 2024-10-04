package cronograma.api.model;

public enum Role {
    ADMIN("admin"),
    USER("user"),
    BLOQUEADO("bloqueado");

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
