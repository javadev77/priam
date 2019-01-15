package fr.sacem.priam.model.domain;

/**
 * Created with IntelliJ IDEA.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 1.0
 */
public enum TypeDroit {

    DE("DE"), PH("PH"), DR("DR"), DE_DR("DE/DR");

    private String value;

    TypeDroit(final String value) {
        this.value = value;
    }

    public static TypeDroit getEnumValue(String code) {
        for (TypeDroit typeDroit: values()) {
            if(code.equals(typeDroit.value)) {
                return typeDroit;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }}
