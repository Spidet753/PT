/**
 * Sablona pro vytvareni prioritni fronty pozadavku
 * @author Vaclav Prokop, Filip Valtr
 */
public class PrioritniFronta {
//== Atributy instanci
    /**
     * halda obsahujici instance pozadavku
     */
	private Pozadavek[] halda;
    /**
     * pocet prvku v halde
     */
	private int velikost;
	 
//== Konstruktor
    /**
     * Konstruktor, ktery vytvori pole pozadavku pro haldu na predane kapacite
     * @param kapacita - inicializacni kapacita
     */
	public PrioritniFronta(int kapacita) {
	        halda = new Pozadavek[kapacita];
	        velikost = 0;
	}
//== Getry, setry k atributum
    
    /**
     * Metoda zkontroluje privatni metodou (jePrazdna) jestli halda neni prazdna,
     * pokud ano vyhodi vyjimku, jinak vrati koren haldy
     * @return koren haldy (pozadavek)
     */ 
	public Pozadavek get() {
        if (jePrazdna()) {
            throw new IllegalStateException("Prioritni fronta je prazdna.");
        }
        return halda[0];
    }
 //== Public metody instanci
    /**
     * Metoda zkontroluje jestli je halda plna, pokud ano, zvysi kapacitu. Prvek vlozi na misto v halde a
     * zavola metodu swim, kterou zachova vlastnosti haldy a nasledne iteruje velikost o jeden
     * @param pozadavek (Pozadavek)
     */
    public void add(Pozadavek pozadavek) {
        if (velikost == halda.length) {
            ZvysKapacitu();
        }

        halda[velikost] = pozadavek;
        swim(velikost);
        velikost++;
    }
    /**
     * Metoda otestuje jestli je co odstranovat. Pokud ano, odstrani koren haldy, prohodi prvky metodou swap a 
     * odecte velikost. Nasledne haldu opravi metodou sink a odstraneny prvek vrati
     * @return max - koren haldy (prvni prvek ve fronte -> (vypis)
     */
    public Pozadavek remove() {
        if (jePrazdna()) {
            throw new IllegalStateException("Prioritni fronta je prazdna.");
        }

        Pozadavek max = halda[0];
        swap(0, velikost - 1);
        velikost--;
        sink(0);

        return max;
    }
    /**
     * Metoda slouzi k otestovani jestli je fronta prazdna. Otestuje porovnanim atributem velikost. 
     * @return true -> pokud velikost 0 jinak false
     */
    public boolean jePrazdna() {
        return velikost == 0;
    }
    //== Private metody instanci
    /**
     * Metoda prohodi predka s naslednikem v halde, jestli je index prvku vetsi nez 0 a 
     * zaroven je predek prvku na indexu vetsi nez prvek samotny. Prvek tak proplave haldou 
     * na sve misto a tim zajisti zachovani jejich vlastnosti.
     * @param index (int)
     */
    private void swim(int index) {
        int prohozeni = index;
        while (prohozeni > 0 && halda[predek(prohozeni)].compareTo(halda[prohozeni]) == 1) {
            swap(prohozeni, predek(prohozeni));
            prohozeni = predek(prohozeni);
        }
    }
    /**
	 * Metoda vybere nejmensiho z nasledniku prvku na indexu v halde a pokud je tento 
	 * naslednik mensi nez prvek na indexu, tak je prohodi metodou swap. To cele opakuje,
	 * dokud nejsou zachovany vlastnosti haldy.
	 * @param index (int)
	 */
    private void sink(int index) {
        int prohozeni = index;
        while (levyPotomek(prohozeni) < velikost) {
            int nejmensiPotomek = levyPotomek(prohozeni);
            if (pravyPotomek(prohozeni) < velikost && halda[pravyPotomek(prohozeni)].compareTo(halda[levyPotomek(prohozeni)]) ==-1 	) {
                nejmensiPotomek = pravyPotomek(prohozeni);
            }

            if ( halda[prohozeni].compareTo(halda[nejmensiPotomek]) <= 0 ) {
                break;
            }

            swap(prohozeni, nejmensiPotomek);
            prohozeni = nejmensiPotomek;
        }
    }
    /**
     * Metoda prohodi prvky v halde.
     * @param i (int)
     * @param j	(int)
     */
    private void swap(int i, int j) {
        Pozadavek temp = halda[i];
        halda[i] = halda[j];
        halda[j] = temp;
    }
    /**
     * Metoda zvysi kapacitu haldy o 2 nasobek a nakopiruje prvky
     */
    private void ZvysKapacitu() {
        if (halda[velikost - 1] != null) {           //pokud se data nevejdou do pole, přidá místo
            Pozadavek[] novaHalda = new Pozadavek[halda.length * 2];
            for (int i = 0; i < halda.length; i++) {
                novaHalda[i] = halda[i];
            }
            halda = novaHalda;
        }
    }
    /**
     * Metoda vrati index predka prvku na indexu v halde
     * @param index prvku jehoz predka chceme ziskat
     * @return index predka
     */
    private int predek(int index) {
        return (index - 1) / 2;
    }
    /**
     * Metoda zjisti index leveho potomka predka, kteri se nachazeji v poli na indexech 2 index + 1
     * @param index prvku jehoz potomka chceme ziskat
     * @return index leveho potomka
     */
    private int levyPotomek(int index) {
        return 2 * index + 1;
    }
    /**
     * Metoda zjisti index praveho potomka predka, kteri se nachazeji v poli na indexech 2 index + 2
     * @param index prvku jehoz potomka chceme ziskat
     * @return index praveho potomka
     */
    private int pravyPotomek(int index) {
        return 2 * index + 2;
    }
}