package map_tests;




import actor_map.ActorTimedSizableHashMap;
//import org.junit.Test;

import java.util.concurrent.TimeUnit;

//import static junit.framework.TestCase.assertEquals;
//import static org.junit.Assert.assertFalse;

import java.util.concurrent.TimeUnit;

public class ActorMapTest {

//    @Test
//    public void shouldInsertValue(){
//        Mapinterface.Maps.TimedSizableMap<String, Integer> map = new ActorTimedSizableHashMap<>();
//        assertEquals(0,map.size());
//        map.put("matan",5,4,TimeUnit.SECONDS);
//        assertEquals(1,map.size());
//        map.shutdown();
//    }
//
//    @Test
//    public void shouldGcRemove() throws InterruptedException {
//        Mapinterface.Maps.TimedSizableMap<String, Integer> map = new ActorTimedSizableHashMap<>();
//        map.put("matan",5,1,TimeUnit.SECONDS);
//        Thread.sleep(2000);
//        assertFalse(map.get("matan").isPresent());
//        assertEquals(0,map.size());
//        map.shutdown();
//    }
//
//    @Test
//    public void shouldReturnNull() throws InterruptedException {
//        Mapinterface.Maps.TimedSizableMap<String, Integer> map = new ActorTimedSizableHashMap<>();
//        map.put("matan",5,1,TimeUnit.SECONDS);
//        Thread.sleep(1100);
//        assertFalse(map.get("matan").isPresent());
//        assertEquals(0,map.size());
//        map.shutdown();
//    }
//
//    @Test
//    public void shouldInsertParallel() throws InterruptedException {
//        for (int j=0;j<10;j++){
//            Mapinterface.Maps.TimedSizableMap<Integer, Integer> map = new ActorTimedSizableHashMap<>();
//
//            Thread t1 = new Thread(()->{
//                for(int i=0; i<100;i++)
//                    map.put(i,i,2,TimeUnit.SECONDS);
//            });
//
//            Thread t2 = new Thread(()->{
//                for(int i=100; i<200;i++)
//                    map.put(i,i,2,TimeUnit.SECONDS);
//            });
//            t1.start();
//            t2.start();
//
//            t1.join();
//            t2.join();
//
//            assertEquals(200,map.size());
//            Thread.sleep(2100);
//            assertFalse(map.get(199).isPresent());
//            assertEquals(0,map.size());
//            map.shutdown();
//        }
//
//    }
//
//    @Test
//    public void shouldReturnLastValueOfKey() throws InterruptedException {
//        Mapinterface.Maps.TimedSizableMap<String, Integer> map = new ActorTimedSizableHashMap<>();
//        map.put("kobi",5,10,TimeUnit.SECONDS);
//        map.put("kobi",6,10,TimeUnit.SECONDS);
//        map.get("kobi").ifPresent(val->{
//            assertEquals(6,(int)val);
//        });
//        map.shutdown();
//    }
//    @Test
//    public void shouldDropHalfInputs() throws InterruptedException {
//        Mapinterface.Maps.TimedSizableMap<Integer, Integer> map = new ActorTimedSizableHashMap<>();
//        int size = 10_000;
//        Thread t1 = new Thread(()->{
//            for(int i=0;i<size;++i){
//                map.put(i,i,10,TimeUnit.MILLISECONDS);
//            }
//        });
//        Thread t2 = new Thread(()->{
//            for(int i=0;i<size;++i){
//                map.put(i+size,i,50,TimeUnit.SECONDS);
//            }
//        });
//
//        t1.start();
//        t2.start();
//        t1.join();
//        t2.join();
//        Thread.sleep(2000);
//        assertEquals(size,map.size());
//        map.shutdown();
//    }
}
