import java.net.UnknownHostException;
import java.util.*;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteException;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.ScanQuery;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.lang.IgniteBiPredicate;
import org.apache.ignite.configuration.CacheConfiguration;

import javax.cache.Cache;

public class Operacje {

    public static void wyswietlKonkretneZwierze(Ignite client) throws IgniteException {

        CacheConfiguration<Long, Zoo> personCfg = new CacheConfiguration<Long, Zoo>("Zoo");
        IgniteCache<Long, Zoo> zierzetaCache = client.getOrCreateCache(personCfg);

        //IgniteCache<Long, Zoo> zierzetaCache = client.cache("atrakcje");

        Scanner in = new Scanner(System.in);
        System.out.println("Podaj nazwę zwierzęcia które chcesz wyszukać");
        String wybor = "";
        wybor = in.nextLine();
        String finalWybor = wybor;

        IgniteBiPredicate<Long, Zoo> filter = (key, p) -> p.getZwierze().equals(finalWybor);

        try (QueryCursor<Cache.Entry<Long, Zoo>> qryCursor = zierzetaCache.query(new ScanQuery<>(filter))) {
            qryCursor.forEach(
                    entry -> System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()));
        }
    }


    public static void wyswietlHipopotamyZKlatek(Ignite client) throws IgniteException {

        CacheConfiguration<Long, Zoo> personCfg = new CacheConfiguration<Long, Zoo>("Zoo");
        personCfg.setName("Zoo");
        personCfg.setIndexedTypes(Long.class, Zoo.class);
        IgniteCache<Long, Zoo> zwierzetaCache = client.getOrCreateCache(personCfg);

        SqlFieldsQuery sql = new SqlFieldsQuery("select concat('Zoo ',zwierze, ' nr_klatki: ', nr_klatki) from Zoo WHERE (zwierze ='Hipopotam') AND (nr_klatki BETWEEN 1 AND 50)");

        try (QueryCursor<List<?>> cursor = zwierzetaCache.query(sql)) {
            for (List<?> row : cursor)
                System.out.println(row.get(0));
        }
    }

    public static void wyswietlHipopotamyILwy(Ignite client) throws IgniteException {

        CacheConfiguration<Long, Zoo> personCfg = new CacheConfiguration<Long, Zoo>("Zoo");
        personCfg.setName("Zoo");
        personCfg.setIndexedTypes(Long.class, Zoo.class);
        IgniteCache<Long, Zoo> zierzetaCache = client.getOrCreateCache(personCfg);

        SqlFieldsQuery sql = new SqlFieldsQuery("select concat('Zoo ',zwierze, ' Nr klatki: ', nr_klatki) from Zoo WHERE (zwierze = 'Hipopotam' OR zwierze = 'Lew')");

        try (QueryCursor<List<?>> cursor = zierzetaCache.query(sql)) {
            for (List<?> row : cursor)
                System.out.println(row.get(0));
        }
    }

    public static void wyswietlWszystko(Ignite client) throws IgniteException {

        CacheConfiguration<Long, Zoo> personCfg = new CacheConfiguration<Long, Zoo>("Zoo");
        IgniteCache<Long, Zoo> zierzetaCache = client.getOrCreateCache(personCfg);;

        Iterator<Cache.Entry<Long, Zoo>> itr = zierzetaCache.iterator();
        while(itr.hasNext()) {
            Zoo object = itr.next().getValue();
            System.out.println(object);
        }
    }



    final private static Random r = new Random(System.currentTimeMillis());

    public static void zapiszDoMapy(Ignite client) throws IgniteException {

        CacheConfiguration<Long, Zoo> personCfg = new CacheConfiguration<Long, Zoo>("Zoo");
        personCfg.setIndexedTypes(Long.class, Zoo.class);

        IgniteCache<Long, Zoo> zwierzeCache = client.getOrCreateCache(personCfg);

        Long key1 = (long) Math.abs(r.nextInt());
        short   key2 = (byte) Math.abs(r.nextInt(100)+1);
        Zoo zoo1 = new Zoo("Lew", key2 );
        System.out.println("PUT " + key1 + " => " + zoo1);
        zwierzeCache.put(key1, zoo1);
        key1 = (long) Math.abs(r.nextInt());
        key2 = (byte) Math.abs(r.nextInt(100)+1);
        zoo1 = new Zoo("Hipopotam", key2);
        zwierzeCache.put(key1, zoo1);
        System.out.println("PUT " + key1 + " => " + zoo1);
        key1 = (long) Math.abs(r.nextInt());
        key2 = (byte) Math.abs(r.nextInt(100)+1);
        zoo1 = new Zoo("Małpa", key2);
        zwierzeCache.put(key1, zoo1);
        System.out.println("PUT " + key1 + " => " + zoo1);
        key1 = (long) Math.abs(r.nextInt());
        key2 = (byte) Math.abs(r.nextInt(100)+1);
        zoo1 = new Zoo("Słoń", key2);
        zwierzeCache.put(key1, zoo1);
        System.out.println("PUT " + key1 + " => " + zoo1);
        key1 = (long) Math.abs(r.nextInt());
        key2 = (byte) Math.abs(r.nextInt(100)+1);
        zoo1 = new Zoo("Niedzwiedż", key2);
        zwierzeCache.put(key1, zoo1);
        System.out.println("PUT " + key1 + " => " + zoo1);
    }

    public static void dodawanieZwierzencia(Ignite client) throws IgniteException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Podaj nazwę Zwierzencia");
        String zwierze = "";
        zwierze = scan.nextLine();

        System.out.println("Podaj numer klatki");
        int nr_klatki;
        nr_klatki = scan.nextInt();

        CacheConfiguration<Long, Zoo> personCfg = new CacheConfiguration<Long, Zoo>("Zoo");
        personCfg.setIndexedTypes(Long.class, Zoo.class);

        IgniteCache<Long, Zoo> zwierzeCache = client.getOrCreateCache(personCfg);

        Long key1 = (long) Math.abs(r.nextInt());
        Zoo zoo1 = new Zoo(zwierze, nr_klatki);
        System.out.println("PUT " + key1 + " => " + zoo1);
        zwierzeCache.put(key1, zoo1);

    }


    public static void usunWszystkieDane(Ignite client ) throws IgniteException {
        CacheConfiguration<Long, Zoo> personCfg = new CacheConfiguration<Long, Zoo>("Zoo");
        IgniteCache<Long, Zoo> zierzetaCache = client.getOrCreateCache(personCfg);
        //IgniteCache<Long, Zoo> zierzetaCache = client.cache("atrakcje");
        zierzetaCache.destroy();

    }




    public static void Edycja(Ignite client ) throws IgniteException {

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

        Iterator<Cache.Entry<Long, Zoo>> itr = ZwierzeCache.iterator();

        System.out.println("Wszystkie zwierzeta: ");
        while(itr.hasNext()) {
            Cache.Entry<Long, Zoo> e = itr.next();
            Zoo zoo = e.getValue();
            Long key = e.getKey();
            String nazwa1 = zoo.getZwierze();

            if (nazwa.equals(nazwa1)) {
                System.out.println("Przed przeniesieniem: " + zoo);
                zoo.setNr_klatki(nr_klatki);
                System.out.println("Po przeniesieniu:" + zoo);
                //e.setValue(zoo);

                ZwierzeCache.replace(key, zoo);

            }
        }
    }
}
