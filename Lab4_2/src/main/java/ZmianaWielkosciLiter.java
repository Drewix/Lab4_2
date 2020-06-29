import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteException;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.lang.IgniteRunnable;

import javax.cache.Cache;
import java.util.Iterator;



public class ZmianaWielkosciLiter {

    public static void main(Ignite client) throws IgniteException {

        CacheConfiguration<Long, Zoo> personCfg = new CacheConfiguration<Long, Zoo>("Zoo");
        IgniteCache<Long, Zoo> atrakcjaCache = client.getOrCreateCache(personCfg);
        client.compute().run(new InnerRunnable(atrakcjaCache));
    }


    public static class InnerRunnable implements IgniteRunnable{
        IgniteCache<Long, Zoo> atrakcjaCache;

        public InnerRunnable(IgniteCache<Long, Zoo> atrakcjaCache) {
            this.atrakcjaCache = atrakcjaCache;
        }

        @Override
        public void run() {
            Iterator<Cache.Entry<Long, Zoo>> itr = atrakcjaCache.iterator();

            while(itr.hasNext()) {
                Cache.Entry<Long, Zoo> entry = itr.next();

                Zoo zoo = entry.getValue();
                Long key = entry.getKey();
                String name = zoo.getZwierze();

                System.out.println("Before Processing = " + zoo);
                if (name.equals(name.toLowerCase())) {
                    name = name.toUpperCase();
                    zoo.setZwierze(name);
                } else{
                    name = name.toLowerCase();
                    zoo.setZwierze(name);
                }
                System.out.println("After Processing = " + zoo);
                atrakcjaCache.replace(key, zoo);

            }
        }
    }
}

