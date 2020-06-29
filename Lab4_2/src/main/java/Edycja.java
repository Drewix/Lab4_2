import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteException;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.lang.IgniteRunnable;


import javax.cache.Cache;
import java.util.Iterator;
import java.util.Scanner;


public class Edycja {

    public static void main(Ignite client) throws IgniteException {

        CacheConfiguration<Long, Zoo> personCfg = new CacheConfiguration<Long, Zoo>("Zoo");
        IgniteCache<Long, Zoo> ZwierzeCache = client.getOrCreateCache(personCfg);

        Scanner scan = new Scanner(System.in);
        System.out.println("Przenieś wszytskie zwierzecia jednego gatunku do jednej klatki");
        System.out.println("Podaj nazwe zwierzecia:");
        String nazwa = "";
        nazwa = scan.nextLine();
        System.out.println("Podaj nowy numer klatki");
        int nr_klatki = 0;
        nr_klatki = scan.nextInt();

        client.compute().run(new InnerRunnable(nazwa, nr_klatki, ZwierzeCache));
    }


    public static class InnerRunnable implements IgniteRunnable{

        String zwierze = "";
        int nr_klatki;
        IgniteCache<Long, Zoo> ZwierzeCache;


        public InnerRunnable(String zwierze, int nr_klatki, IgniteCache<Long, Zoo> ZwierzeCache) {
            this.ZwierzeCache = ZwierzeCache;
            this.zwierze = zwierze;
            this.nr_klatki = nr_klatki;
        }

        @Override
        public void run() {
            Iterator<Cache.Entry<Long, Zoo>> itr = ZwierzeCache.iterator();

            System.out.println("Wszystkie zwierzeta: ");
            while(itr.hasNext()) {
                Cache.Entry<Long, Zoo> e = itr.next();
                Zoo zoo = e.getValue();
                Long key = e.getKey();
                String nazwa1 = zoo.getZwierze();

                if (zwierze.equals(nazwa1)) {
                    System.out.println("Przed przeniesieniem: " + zoo);
                    zoo.setNr_klatki(nr_klatki);
                    System.out.println("Po przeniesieniu:" + zoo);
                    //e.setValue(zoo);

                    ZwierzeCache.replace(key, zoo);
                }
            }
        }
    }
}

