import java.time.LocalDate;
import java.util.*;

public class User implements Comparable<User> {

    // ............... Fields ....................

    private String id;
    private String name;
    private String username;
    private String email;
    private String password;
    private String field;
    private String workplace;
    private String universityLocation;
    private LocalDate dateOfBirth;
    private String profile_url;
    private List<String> specialities;
    private Set<User> connections;
    private Set<User> requests;
    private String[] priorities;
    private User target;

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

    public String[] getPriorities() {
        return priorities;
    }

    public void setPriorities(String[] priorities) {
        this.priorities = priorities;
    }

    // ............... Methods ....................

    /*
    private Map<Priority , Integer> mapPriority(String priority){
        String[] splitPriority = priority.split(",");
        int maxPriority = splitPriority.length;
        Map<Priority , Integer> prioritiesMap = new HashMap<>();
        for(String s : splitPriority){
            prioritiesMap.put(Priority.valueOf(s) , maxPriority );
            maxPriority--;
        }
        return prioritiesMap;
    }

    private int calcDegree(User user){
        int degree = 0;
        if(Objects.equals(this.priorities, "")) {
            if(Objects.equals(user.getField(), this.field))
                degree++;

            if(Objects.equals(user.getWorkplace(), this.workplace))
                degree++;

            if(Objects.equals(user.getUniversityLocation(), this.universityLocation))
                degree++;

            for(String s : this.specialities){
                if(user.getSpecialities().contains(s))
                    degree++;
            }

            for(User s : this.connections){
                if(user.getConnections().contains(s))
                    degree++;
            }
        }
        else{
            Map<Priority , Integer> mapPriority = this.mapPriority(this.priorities);
            for(Map.Entry<Priority , Integer> e : mapPriority.entrySet()){
                Priority p = e.getKey();
                switch (p) {
                    case field -> {
                        if (Objects.equals(user.getField(), this.field))
                            degree += e.getValue();
                    }
                    case workplace -> {
                        if (Objects.equals(user.getField(), this.workplace))
                            degree += e.getValue();
                    }
                    case universityLocation -> {
                        if (Objects.equals(user.getField(), this.universityLocation))
                            degree += e.getValue();
                    }
                    case specialities -> {
                        for (String s : this.specialities) {
                            if (user.getSpecialities().contains(s))
                                degree += e.getValue();
                        }
                    }
                    case connections -> {
                        for (User s : this.connections) {
                            if (user.getConnections().contains(s))
                                degree += e.getValue();
                        }
                    }
                }
            }
        }
        return degree;
    }
     */

    @Override
    public int compareTo(User o) {
        for (String priority : this.target.priorities) {
            switch (priority) {
                case "field" -> {
                    if (this.field.equals(this.target.field) && !o.field.equals(this.target.field))
                        return 1;
                    if (!this.field.equals(this.target.field) && o.field.equals(this.target.field))
                        return -1;
                }
                case "workplace" -> {
                    if (this.workplace.equals(this.target.workplace) && !o.workplace.equals(this.target.workplace))
                        return 1;
                    if (!this.workplace.equals(this.target.workplace) && o.workplace.equals(this.target.workplace))
                        return -1;
                }
                case "universityLocation" -> {
                    if (this.universityLocation.equals(this.target.universityLocation) && !o.universityLocation.equals(this.target.universityLocation))
                        return 1;
                    if (!this.universityLocation.equals(this.target.universityLocation) && o.universityLocation.equals(this.target.universityLocation))
                        return -1;
                }
                case "specialities" -> {
                    int count1 = 0, count2 = 0;
                    for (String s : this.target.specialities) {
                        if (this.specialities.contains(s))
                            count1++;
                        if (o.specialities.contains(s))
                            count2++;
                    }
                    return count1 - count2;
                }
                case "connections" -> {
                    int count1 = 0, count2 = 0;
                    for (User u : this.target.connections) {
                        if (this.connections.contains(u))
                            count1++;
                        if (o.connections.contains(u))
                            count2++;
                    }
                    return count1 - count2;
                }
            }
        } return 0;
    }
}
