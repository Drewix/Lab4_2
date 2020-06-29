import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;

import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Scanner;



public class Klient {

    public static void main(String[] args) throws UnknownHostException {

        IgniteConfiguration cfg = new IgniteConfiguration();
        cfg.setClientMode(true);
        Ignite client = Ignition.start(cfg);


        Scanner in = new Scanner(System.in);
        String wybor = "";

        while (wybor != "0") {
            boolean wyjscie = false;

            System.out.println("Proszę wybrac numer operacji:");
            System.out.println("0. Wyjście");
            System.out.println("1. Dodaj zwierzeta testowe (Dane testowe)");
            System.out.println("2. Dodaj konkretne zwierze");
            System.out.println("3. Wypisz wszytskie zwierzeta (pobiera z serwera)");
            System.out.println("4. Zmiana wielkosći liter w nazwie zwierząt");
            System.out.println("5. Zapytanie sql które wyświetla Hipopotamy i lwy ");
            System.out.println("6. Zapytanie sql które wyświetla Hipopotamy z klatek 1 - 50 ");
            System.out.println("7. Wyświetla konkretne zwierze");
            System.out.println("8. Usunięcie wszytskich danych ");
            System.out.println("9. Edycja numeru klatki");


            wybor = in.nextLine();

            switch (wybor) {
                case "1":
                    Operacje.zapiszDoMapy(client);
                    break;
                case "2":
                    Operacje.dodawanieZwierzencia(client);
                    break;
                case "3":
                    Operacje.wyswietlWszystko(client);
                    break;
                case "4":
                    ZmianaWielkosciLiter.main(client);
                    break;
                case "5":
                    Operacje.wyswietlHipopotamyILwy(client);
                    break;
                case "6":
                    Operacje.wyswietlHipopotamyZKlatek(client);
                    break;
                case "7":
                    Operacje.wyswietlKonkretneZwierze(client);
                    break;
                case "8":
                    Operacje.usunWszystkieDane(client);
                    break;
                case "9":
                    Operacje.Edycja(client);
                    break;
                case "0":
                    wyjscie = true;
                    break;
                default:
                    System.out.println("Zły znak");
            }
            if (wyjscie)
                break;
        }
    }
}
