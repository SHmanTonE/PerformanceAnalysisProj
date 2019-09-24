package timed_sizable_map;

public class IDedItem<V> {

    public final V item;
    public final long id;

    public IDedItem(V item, long id){
        this.item = item;
        this.id = id;
    }

}