import java.time.LocalDate;
import java.util.*;

public class User implements Comparable<User> {

    // ............... Fields ....................

    private String id;
    private String name;
    private String field;
    private String workplace;
    private String universityLocation;
    private String dateOfBirth; //
    private List<String> specialities;
    private Set<String> connectionId;
    private String[] priorities;
    private Target target;

    // ............... Constructor ....................

    public User(
            //id, name, dateOfBirth, universityLocation, field, workplace, specialties, connectionId
            String id, String name, String dateOfBirth, String universityLocation, String field,String workplace,
            List<String> specialities, Set<String> connectionId)
    {
        this.id = id;
        this.name = name;
        this.field = field;
        this.workplace = workplace;
        this.universityLocation = universityLocation;
        this.dateOfBirth = dateOfBirth;
        this.specialities = specialities;
        this.connectionId = connectionId;
        this.priorities = new String[]{"field", "specialities", "connectionId", "universityLocation", "workplace"};
    }


    // ............... Getters & Setters ....................

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public List<String> getSpecialities() {
        return specialities;
    }

    public Set<String> getConnections() {
        return connectionId;
    }

    public Target getTarget() {
        return target;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setSpecialities(List<String> specialities) {
        this.specialities = specialities;
    }

    public void setConnections(Set<String> connections) {
        this.connectionId = connections;
    }

    public void setTarget(Target target) {
        this.target = target;
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
        for (String priority : this.target.getTrg().priorities) {
            switch (priority) {
                case "field" -> {
                    if (this.field.equals(this.target.getTrg().field) && !o.field.equals(this.target.getTrg().field))
                        return -1;
                    if (!this.field.equals(this.target.getTrg().field) && o.field.equals(this.target.getTrg().field))
                        return 1;
                }
                case "workplace" -> {
                    if (this.workplace.equals(this.target.getTrg().workplace) && !o.workplace.equals(this.target.getTrg().workplace))
                        return -1;
                    if (!this.workplace.equals(this.target.getTrg().workplace) && o.workplace.equals(this.target.getTrg().workplace))
                        return 1;
                }
                case "universityLocation" -> {
                    if (this.universityLocation.equals(this.target.getTrg().universityLocation) && !o.universityLocation.equals(this.target.getTrg().universityLocation))
                        return -1;
                    if (!this.universityLocation.equals(this.target.getTrg().universityLocation) && o.universityLocation.equals(this.target.getTrg().universityLocation))
                        return 1;
                }
                case "specialities" -> {
                    int count1 = 0, count2 = 0;
                    for (String s : this.target.getTrg().specialities) {
                        if (this.specialities.contains(s))
                            count1++;
                        if (o.specialities.contains(s))
                            count2++;
                    }
                    return count2 - count1;
                }
                case "connectionId" -> {
                    int count1 = 0, count2 = 0;
                    for (String u : this.target.getTrg().connectionId) {
                        if (this.connectionId.contains(u))
                            count1++;
                        if (o.connectionId.contains(u))
                            count2++;
                    }
                    return count2 - count1;
                }
            }
        }
        // to consider level ordering
        return Integer.compare(o.target.getDst(), this.target.getDst());
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", field='" + field + '\'' +
                ", workplace='" + workplace + '\'' +
                ", universityLocation='" + universityLocation + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", specialities=" + specialities +
                ", connectionId=" + connectionId +
                ", priorities=" + Arrays.toString(priorities) +
                ", target=" + target +
                '}';
    }
}
