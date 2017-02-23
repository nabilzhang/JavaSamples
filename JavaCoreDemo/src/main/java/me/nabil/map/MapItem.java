package me.nabil.map;

/**
 * Map
 *
 * @author zhangbi
 */
public class MapItem {

    private String item;

    public MapItem(String item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "MapItem{" +
                "item='" + item + '\'' +
                '}';
    }
}
