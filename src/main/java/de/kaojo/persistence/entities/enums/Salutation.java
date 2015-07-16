package de.kaojo.persistence.entities.enums;

/**
 *
 * @author jwinter
 */
public enum Salutation {

    Mr("Mr."),
    Mrs("Mrs."),
    Miss("Miss"),
    Dr("Dr."),
    Ms("Ms."),
    Prof("Prof.");

    private final String salutation;

    private Salutation(String salutation) {
        this.salutation = salutation;
    }

    public static Salutation getType(String salutation) {
        if (salutation == null) {
            return null;
        }

        for (Salutation sal : Salutation.values()) {
            if (salutation.equals(sal.getSalutation())) {
                return sal;
            }
        }
        throw new IllegalArgumentException("No matching type for salutation " + salutation);
    }

    public String getSalutation() {
        return salutation;
    }
}
