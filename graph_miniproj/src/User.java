import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class User implements Comparable<User> {

    // ............... Fields ....................

    private String id;
    private String name;
    private String username;
    private String email;
    private String password;
    private String field; //
    private String workplace; //
    private String universityLocation; //
    private LocalDate dateOfBirth;
    private String profile_url;
    private List<String> specialities; //
    private Set<User> connections; //
    private Set<User> requests;

    // ............... Constructor ....................

    public User(
            String id, String name, String username,
                String email, String password, String field,
                String workplace, String universityLocation,
                LocalDate dateOfBirth, String profile_url,
                List<String> specialities, Set<User> connections,
                Set<User> requests)
    {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.field = field;
        this.workplace = workplace;
        this.universityLocation = universityLocation;
        this.dateOfBirth = dateOfBirth;
        this.profile_url = profile_url;
        this.specialities = specialities;
        this.connections = connections;
        this.requests = requests;
    }


    // ............... Getters & Setters ....................

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getField() {
        return field;
    }

    public String getWorkplace() {
        return workplace;
    }

    public String getUniversityLocation() {
        return universityLocation;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getProfile_url() {
        return profile_url;
    }

    public List<String> getSpecialities() {
        return specialities;
    }

    public Set<User> getConnections() {
        return connections;
    }

    public Set<User> getRequests() {
        return requests;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public void setUniversityLocation(String universityLocation) {
        this.universityLocation = universityLocation;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setProfile_url(String profile_url) {
        this.profile_url = profile_url;
    }

    public void setSpecialities(List<String> specialities) {
        this.specialities = specialities;
    }

    public void setConnections(Set<User> connections) {
        this.connections = connections;
    }

    public void setRequests(Set<User> requests) {
        this.requests = requests;
    }

    @Override
    public int compareTo(User o) {
        return 0;
    }
}
