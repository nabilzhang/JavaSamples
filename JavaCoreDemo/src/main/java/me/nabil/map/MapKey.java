package me.nabil.map;

/**
 * @author zhangbi
 */
public class MapKey {
    private String a;

    public MapKey(String a) {
        this.a = a;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    @Override
    public int hashCode() {
        return a != null ? a.hashCode() : 0;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MapKey mapKey = (MapKey) o;

        return !(a != null ? !a.equals(mapKey.a) : mapKey.a != null);

    }

    @Override
    public String toString() {
        return "MapKey{" +
                "a='" + a + '\'' +
                '}';
    }
}
