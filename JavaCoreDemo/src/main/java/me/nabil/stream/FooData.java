package me.nabil.stream;

public class FooData {
    private long id;
    private String name;
    private String groupName;

    public FooData(long id, String name, String groupName) {
        this.id = id;
        this.name = name;
        this.groupName = groupName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "FooData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", groupName='" + groupName + '\'' +
                '}';
    }
}
