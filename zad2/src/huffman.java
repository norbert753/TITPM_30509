

        import java.util.*;
        import java.util.stream.Collectors;

        public class huffman {
        Scanner cin = new Scanner(System.in);
        String ciagZnakow;
        public void wypisz(){
        System.out.print("Podaj ciag znakow: ");
        ciagZnakow = cin.nextLine();
        Map<Character, Long> wysapienia = zliczWystapienia(ciagZnakow);
        System.out.println(wysapienia);
        Map<Character, String> binarMap = zamNaBin(wysapienia);
        System.out.println(binarMap);
        String encryptedMessage = zaszyfruj(binarMap, ciagZnakow);
        System.out.println("Zaszyfrowana wiad: " + encryptedMessage);
        System.out.println("Odszyfrowana wiad: " + odszyfruj(binarMap, encryptedMessage));
    }

    public  Map<Character, Long> zliczWystapienia(String ciagZnakow){
        Map <Character, Long> mapStringToCount = ciagZnakow
                .chars().mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));
        return sort(mapStringToCount);
    }

    public  Map<Character, Long> sort(Map<Character, Long> mapa){
        LinkedHashMap<Character, Long> result = new LinkedHashMap<>();
        mapa.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(value -> result.put(value.getKey(), value.getValue()));

        return result;
    }

    public  Map<Character, String> zamNaBin(Map<Character, Long> mapa){
        Map<Character, String> wartoscBinarna = new LinkedHashMap<>();
        int i = mapa.size()-1;
        String bin = "1";
        for (Character wystapienie: mapa.keySet()){
            if(i == 0){
                wartoscBinarna.put(wystapienie, "0");
            }
            else {
                wartoscBinarna.put(wystapienie, bin.repeat(i) + "0");
            }
            i--;
        }
        return wartoscBinarna;
    }

    public  String zaszyfruj(Map<Character, String> wartoscBinarna, String wiadomosc){
        for (Map.Entry<Character, String> szyfr : wartoscBinarna.entrySet()){
            wiadomosc = wiadomosc.replace(szyfr.getKey().toString(), szyfr.getValue());
        }
        return wiadomosc;
    }

    private  String odszyfruj(Map<Character, String> wartoscBinarna, String encryptedMessage){
        for (Map.Entry<Character, String> szyfr : wartoscBinarna.entrySet()){
            encryptedMessage = encryptedMessage.replace(szyfr.getValue(), szyfr.getKey().toString());
        }
        return encryptedMessage;
    }
}
