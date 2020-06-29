import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;

import java.util.Scanner;

public class Serwer {
    public static void main(String[] args) throws IgniteException {

        IgniteConfiguration cfg = new IgniteConfiguration();
        Ignite serwer = Ignition.start(cfg);

        //-------------------------------
        Scanner in = new Scanner(System.in);
        String wybor = "";

        boolean wyjscie = false;
        while (wybor != "0") {

            System.out.println("Proszę wybrac numer operacji:");
            System.out.println("0. Wyjście");
            System.out.println("1. Edycja po stronie serwera");

            wybor = in.nextLine();

            switch (wybor) {
                case "1":
                    Edycja.main(serwer);
                    break;
                case "0":
                    wyjscie = true;
                    break;
                default:
                    System.out.println("Zły znak");
                    if (wyjscie)
                        break;
            }
        }
    }

}