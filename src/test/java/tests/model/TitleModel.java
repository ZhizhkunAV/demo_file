package tests.model;

public class TitleModel {
    public String title;
    public String names;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public ProfessionalInner getProfessional() {
        return professional;
    }

    public void setProfessional(ProfessionalInner professional) {
        this.professional = professional;
    }

    public ProfessionalInner professional;
}
