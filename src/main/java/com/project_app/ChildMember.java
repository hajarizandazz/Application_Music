package main.java.com.project_app;



public class ChildMember extends Member {
    private String associatedAdult;

    public ChildMember(String username, String fullName, String email) {
        super(username, fullName, email);
    }

    public String getAssociatedAdult() {
        return associatedAdult;
    }

    public void setAssociatedAdult(String associatedAdult) {
        this.associatedAdult = associatedAdult;
    }
}
