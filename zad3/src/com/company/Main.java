package com.company;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        String ciagZnakow;
        System.out.print("Podaj ciag znakow: ");
        ciagZnakow = cin.nextLine();
        LinkedHashMap<Character, Long> characterLongMap = zliczWystapienia(ciagZnakow);
        System.out.println(characterLongMap);
        Map<Character, String> characterStringMap = convertToBinar(characterLongMap);
        System.out.println(characterStringMap);
        String encryptedMessage = encryptMessage(characterStringMap, ciagZnakow);
        System.out.println("Zaszyfrowana wiadomosc: " + encryptedMessage);
        System.out.println("Odszyfrowana wiadomosc: " + decryptMessage(characterStringMap, encryptedMessage));
        System.out.println(podzielNR(characterLongMap)) ;
    }

    public static LinkedHashMap<Character, Long> zliczWystapienia(String ciagZnakow){
        Map <Character, Long> mapStringToCount = ciagZnakow
                .chars().mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));
        return sort(mapStringToCount);
    }

    public static LinkedHashMap<Character, Long> sort(Map<Character, Long> mapa){
        LinkedHashMap<Character, Long> result = new LinkedHashMap<>();
        mapa.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(value -> result.put(value.getKey(), value.getValue()));

        return result;
    }




    public static Map<Character, String> convertToBinar(Map<Character, Long> mapa) {
        Map<Character, String> wartoscBinarna = new LinkedHashMap<>();
        int i = mapa.size() - 1;
        String bin = "1";
        for (Character wystapienie : mapa.keySet()) {
            if (i == 0) {
                wartoscBinarna.put(wystapienie, "0");
            } else {
                wartoscBinarna.put(wystapienie, bin.repeat(i) + "0");
            }
            i--;
        }
        return wartoscBinarna;
    }
        public static LinkedHashMap<Long, Long> podzielNR(LinkedHashMap<Character, Long> mapa) {
            long sumaP = 0;
            long sumaT = 0;
          LinkedHashMap<Long, Long> resultMap = new LinkedHashMap<>();
            List<Long> mapValues = mapa.values().stream().collect(Collectors.toList());
            int listSize = mapValues.size()-1;
            for (int i = 0; i < mapValues.size(); i++){
                if(sumaP <= sumaT){
                    sumaP += mapValues.get(i);
                }else if(listSize == i){
                    break;
                }else {
                    sumaT += mapValues.get(listSize);
                    listSize--;
                }
            }
            resultMap.put(sumaP, 0L);
            resultMap.put(sumaT, 1L);
            return resultMap;
        }



    public static String encryptMessage(Map<Character, String> wartoscbin, String wiadomosc){
        for (Map.Entry<Character, String> szyfr : wartoscbin.entrySet()){
            wiadomosc = wiadomosc.replace(szyfr.getKey().toString(), szyfr.getValue());
        }
        return wiadomosc;
    }

    private static String decryptMessage(Map<Character, String> wartoscbin, String encryptedMessage){
        for (Map.Entry<Character, String> szyfr : wartoscbin.entrySet()){
            encryptedMessage = encryptedMessage.replace(szyfr.getValue(), szyfr.getKey().toString());
        }
        return encryptedMessage;
    }
}