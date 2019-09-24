package actor_map;

import Mapinterface.Maps;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;
import timed_sizable_map.TimedSizableConcurrentHashMap;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static akka.pattern.Patterns.ask;

public  class ActorTimedSizableHashMap<K,V> implements Maps.TimedSizableMap<K,V> {

    private final ActorSystem system = ActorSystem.create("ActorSystem");
    private final Props p = Props.create(ActorMap.class,() -> new ActorMap<K,V>());
    private final ActorRef actorMap = system.actorOf(p, "theMap");


    @Override
    public void put(K key, V value, int duration, TimeUnit unit) {
        actorMap.tell(new ActorMap.PutMsg<>(key, value, duration, unit), ActorRef.noSender());
    }

    @Override
    public Optional<V> get(K key) {
        Timeout timeout = Timeout.create(Duration.ofSeconds(1));
        Future<Object> future = ask(actorMap,new ActorMap.GetMsg<>(key),timeout);
        try {
            V v = (V)Await.result(future,timeout.duration());
            if (v != ""){
                return Optional.ofNullable(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(null);
    }

    @Override
    public Optional<V> remove(K key) {
        Timeout timeout = Timeout.create(Duration.ofSeconds(1));
        Future<Object> future = ask(actorMap, new ActorMap.RemoveMsg<>(key),timeout);
        try{
            return Optional.ofNullable((V)Await.result(future,timeout.duration()));
        }catch(Exception e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public void shutdown() {
        actorMap.tell(new ActorMap.ShutdownMsg(),ActorRef.noSender());
        system.terminate();
    }

    @Override
    public long size() {
        Timeout timeout = Timeout.create(Duration.ofSeconds(1));
        Future<Object> future = ask(actorMap,new ActorMap.SizeMsg(),timeout);
        try{
            return (long)Await.result(future,timeout.duration());
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}
