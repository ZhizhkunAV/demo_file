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

    public professionalInner getProfessional() {
        return professional;
    }

    public void setProfessional(professionalInner professional) {
        this.professional = professional;
    }

    public professionalInner professional;
}
