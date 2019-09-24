package timed_sizable_map;

import Mapinterface.Maps;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TimedSizableConcurrentHashMap<K,V> implements Maps.TimedSizableMap<K,V> {


    private final Map<K, IDedItem<V>> myMap = new HashMap<>();
    private final ScheduledExecutorService swipper = Executors.newSingleThreadScheduledExecutor();
    private Lock lock = new ReentrantLock();
    private final AtomicLong id = new AtomicLong(1);


    @Override
    public void put(K key, V value, int duration, TimeUnit unit) {
        lock.lock();
        long myID= id.getAndIncrement();
        myMap.put(key,new IDedItem<>(value,myID));
        lock.unlock();
        swipper.schedule(()->tryRemove(key,myID),duration,unit);
    }

    private void tryRemove(K key, long id) {
        lock.lock();
        if(markEquals(key,id))
            myMap.remove(key);
        lock.unlock();


    }

    private boolean markEquals(K key, long myID) {
        if(myMap.get(key)!=null)
            return myMap.get(key).id==myID;
        else return false;

    }

    @Override
    public Optional<V> get(K key) {
        IDedItem<V> idedItem= myMap.get(key);
        if(idedItem!=null)
            return  Optional.ofNullable(idedItem.item);
        return Optional.ofNullable(null);
    }

    @Override
    public Optional<V> remove(K key) {
        return Optional.ofNullable(myMap.remove(key)).map(x -> x.item);
    }

    @Override
    public void shutdown() {
        swipper.shutdownNow();
    }

    @Override
    public long size() {
        return myMap.size();
    }
}
