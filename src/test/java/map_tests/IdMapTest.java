package map_test;

//import org.junit.Assert;
//import org.junit.Test;
import timed_sizable_map.TimedSizableConcurrentHashMap;

import java.util.concurrent.TimeUnit;

//import static junit.framework.TestCase.assertEquals;
public class IdMapTest {

//    @Test
//    public void expectInsertValue(){
//        TimedSizableConcurrentHashMap<String, Integer> map = new TimedSizableConcurrentHashMap<String,Integer>();
//        Assert.assertEquals(0,map.size());
//        map.put("ViNi",5,4, TimeUnit.SECONDS);
//        assertEquals(1,map.size());
//        map.shutdown();
//    }
//
//    @Test
//    public void expectReturnNull() throws InterruptedException {
//        TimedSizableConcurrentHashMap<String, Integer> map = new TimedSizableConcurrentHashMap<String,Integer>();
//        map.put("ViNi",5,1,TimeUnit.SECONDS);
//        Thread.sleep(1250);
//        assertEquals(0,map.size());
//        map.shutdown();
//    }
//
//    @Test
//    public void expectInsertParallel() throws InterruptedException {
//        for (int i=0;i<10;i++){
//            TimedSizableConcurrentHashMap<Integer, Integer> map = new TimedSizableConcurrentHashMap<Integer, Integer>();
//
//            Thread thread1 = new Thread(()->{
//                for(int j=0; j<50;j++)
//                    map.put(j,j,2,TimeUnit.SECONDS);
//            });
//
//            Thread thread2 = new Thread(()->{
//                for(int k=50; k<100;k++)
//                    map.put(k,k,2,TimeUnit.SECONDS);
//            });
//
//            Thread thread3 = new Thread(()->{
//                for(int j=100; j<150;j++)
//                    map.put(j,j,2,TimeUnit.SECONDS);
//            });
//
//            thread1.start();
//            thread2.start();
//            thread3.start();
//
//            thread1.join();
//            thread2.join();
//            thread3.join();
//
//            assertEquals(150,map.size());
//            Thread.sleep(2500);
//            assertEquals(0,map.size());
//            map.shutdown();
//        }
//
//    }
//
//
//


}
