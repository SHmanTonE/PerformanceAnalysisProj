package actor_map;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;
import timed_sizable_map.IDedItem;


import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import static akka.pattern.Patterns.ask;

public class ActorMap<K,V> extends AbstractActor {


    public static class PutMsg<K,V>{
        public final K key;
        public final V value;
        public final TimeUnit unit;
        public final long duration;

        public PutMsg(K key, V value, long duration, TimeUnit unit){
            this.key = key;
            this.value=value;
            this.unit = unit;
            this.duration = duration;
        }
    }
    public static class GetMsg<K>{
        public final K key;
        public GetMsg(K key){
            this.key = key;
        }
    }
    public static class RemoveMsg<K>{
        public final K key;
        public RemoveMsg(K key){
            this.key = key;
        }
    }
    public static class InnerRemoveMsg<K>{
        public final K key;
        public final long id;
        public InnerRemoveMsg(K key, long id){
            this.key = key;
            this.id = id;
        }
    }

    public static class SizeMsg{}

    public static class ShutdownMsg {}

    private final Map<K, IDedItem<V>> innerMap = new HashMap<>();
    private final ScheduledExecutorService cleaner = Executors.newSingleThreadScheduledExecutor();
    private final AtomicLong id = new AtomicLong(1);

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(PutMsg.class, this::put)
                .match(RemoveMsg.class, this::remove)
                .match(GetMsg.class, this::get)
                .match(SizeMsg.class, this::size)
                .match(InnerRemoveMsg.class, this::innerRemove)
                .match(ShutdownMsg.class,this::shutdown)
                .build();
    }

    private void shutdown(ShutdownMsg msg){
        cleaner.shutdownNow();
        getContext().stop(getSelf());
    }

    private void put(PutMsg msg) {
        long id = this.id.getAndIncrement();
        innerMap.put((K)msg.key, new IDedItem<>((V)msg.value,id));
        cleaner.schedule(()->{
            getSelf().tell(new InnerRemoveMsg<>(msg.key, id),
                    ActorRef.noSender());
        },msg.duration,msg.unit);
    }

    private void remove(RemoveMsg msg){
        IDedItem i = innerMap.remove(msg.key);
        if (i == null)
            sender().tell("", self());
        else
            sender().tell(i.item, self());
    }
    private void get(GetMsg msg){
        IDedItem i = innerMap.get(msg.key);
        if (i==null)
            sender().tell("", self());
        else
            sender().tell(i.item, self());
    }
    private void size(SizeMsg msg){
        sender().tell((long)innerMap.size(), self());
    }
    private void innerRemove(InnerRemoveMsg msg){
        IDedItem item = innerMap.get(msg.key);
        if (item.id == msg.id)
            innerMap.remove(msg.key);

    }
    public static void main(String[] args) throws Exception {

        ActorSystem system = ActorSystem.create("MatanSystem");
        Props p = Props.create(ActorMap.class,() -> new ActorMap<Integer,String>());
        ActorRef actorMap = system.actorOf(p, "Actor1");
        actorMap.tell(new PutMsg<Integer,String>(
                        1,
                        "kobi",
                        3,
                        TimeUnit.SECONDS),
                actorMap);
        Timeout timeout = Timeout.create(Duration.ofSeconds(1));

        Future<Object> future = ask(actorMap,new ActorMap.GetMsg<>(1),timeout);
        String s = (String)Await.result(future,timeout.duration());
        System.out.println("Before Wait");
        System.out.println(s);
        Thread.sleep(5000);
        future = ask(actorMap,new ActorMap.GetMsg<>(1),timeout);
        s = (String)Await.result(future,timeout.duration());
        System.out.println("After Wait");
        System.out.println(s);
        actorMap.tell(new ShutdownMsg(),ActorRef.noSender());
        system.terminate();


    }
}
