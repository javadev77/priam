package fr.sacem.priam.batch.participants.rep.domaine;

public enum StatutRoleParticipant {

    A("ROLE_A", "A"),
    AR("ROLE_AR", "AR"),
    C("ROLE_C", "C"),
    CA("ROLE_CA", "CA"),
    SA("ROLE_SA", "SA");

    private final String code;
    private final String codeStatutRole;

    StatutRoleParticipant(String code, String codeStatutRole) {
        this.code = code;
        this.codeStatutRole = codeStatutRole;
    }

    public static StatutRoleParticipant getValue(String code) {

        for( StatutRoleParticipant statutRoleParticipant : StatutRoleParticipant.values()) {
            if(code.equals(statutRoleParticipant.getCode())) {
                return statutRoleParticipant;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getCodeStatutRole() { return codeStatutRole; }
}
