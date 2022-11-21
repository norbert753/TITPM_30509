import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.stream.Collectors;

public class Alg {
    private String message;
    private Scanner scanner = new Scanner(System.in);
    private Scanner scannerPlik = new Scanner(System.in);
    private String pattern ="____";
    private String buffor = "";
    private String mapKey;
    private Integer SizeSlownika;
    private LinkedHashMap<String, String> baseMap = new LinkedHashMap<>();
    private Integer indexStart;
    private Integer indexEnd;

    public void SaveWiadomosc(){
        System.out.print("Podaj wiadomość: ");
        message = scanner.nextLine();

        System.out.println("Podaj Rozmiar slownika: ");
        SizeSlownika = scanner.nextInt();

        BaseSlownik();
    }

    private void BaseSlownik() {
        char firstLetter = message.charAt(0);

        mapKey = String.format("(0, 0, %s)", firstLetter);
        baseMap.put(mapKey, pattern);

        pattern = pattern.substring(1) + firstLetter;
        rozszerzSlownik();
    }

    private void rozszerzSlownik(){
        for(int j = 1; j<= message.length()+ SizeSlownika; j++){
            if(j >= message.length()-1){
                pattern = buffor;
                buffor = String.valueOf(message.charAt(message.length()-1));
                if (conatinsValue(buffor)) {
                    mapKey = String.format("(%d, %d, %s)", indexStart, indexEnd, "_");
                    baseMap.put(mapKey, pattern);
                    break;
                }
            }else {
                if(buffor.length() == 0){
                    buffor = message.substring(j, j+4);
                }else {
                    pattern = pattern.substring(indexEnd+1) + buffor.substring(0, indexEnd+1);
                    buffor = buffor.substring(indexEnd+1) + message.substring(j, j+indexEnd+1);
                }
                baseMap.put("#", pattern);
                if (conatinsValue(buffor)){
                    baseMap.remove("#");
                    char firstLetter = buffor.charAt(indexEnd);
                    mapKey = String.format("(%d, %d, %s)", indexStart, indexEnd, firstLetter);
                    baseMap.put(mapKey, pattern);
                    if(indexEnd + 1 == SizeSlownika){
                        j += indexEnd-1;
                    }else {
                        j += indexEnd + 1;
                    }
                }
                else {
                    char firstLetter = buffor.charAt(0);
                    mapKey = String.format("(0, 0, %s)", firstLetter);
                    baseMap.put(mapKey, pattern);
                    pattern = pattern.substring(1) + firstLetter;
                    buffor = "";
                }
            }
        }
        czytajSzyfr();
    }

    private void czytajSzyfr(){
        for (String index : baseMap.keySet()){
            System.out.print(index + " ");
        }
    }

    public void zapiszDoPlikuONazwie() {
        if (baseMap.isEmpty()){
            System.out.println("\nSłownik jest pusty\nZapis do pliku nie udany");
            return;
        }
        try{
            System.out.print("\nPodaj nazwe pliku: ");
            String nazwaPliku = scannerPlik.nextLine();
            File file = new File(nazwaPliku);
            if(!file.exists()){
                System.out.println("Plik zostal utworzony");
                file.createNewFile();
            }
            if(file.canWrite()) {
                FileWriter fileWriter = new FileWriter(file);
                Formatter formatter = new Formatter(fileWriter);

                for (Map.Entry<String, String> mapka : baseMap.entrySet()){
                    formatter.format("%s | %s\r\n", mapka.getKey(), mapka.getValue());
                }
                formatter.close();
                fileWriter.close();
            }
            System.out.println("Plik zostal zapisany");
        }catch (Exception e){
            System.out.println("Wystapil problem podczas zapisu do pliku");
            System.out.println(e.getMessage());
        }
    }

    public void decryptMessage(){
        String decryptedMessage = "";
        for(Map.Entry<String, String> index : baseMap.entrySet()){
            String key = index.getKey().replaceAll("[()]", "");
            String[] messageIndex = key.split(", ");
            if(messageIndex[2].equals("_")){
                break;
            }
            int lowerBound = Integer.parseInt(messageIndex[0]);
            int upperBound = Integer.parseInt(messageIndex[1]) + lowerBound;
            decryptedMessage += index.getValue().substring(lowerBound, upperBound) + messageIndex[2];
        }
        System.out.println("\nWiadomosc po odszyforwaniu: " + decryptedMessage);
    }

    public void odczytajZPliku(){
        try{
            baseMap.clear();
            System.out.print("Podaj nazwe pliku: ");
            String nazwaPliku = scannerPlik.nextLine();
            File file = new File(nazwaPliku);
            String odczytZpliku;
            if (file.exists()){
                Scanner fileScanner = new Scanner(file);
                while(fileScanner.hasNextLine()){
                    odczytZpliku = fileScanner.nextLine();
                    String[] split = odczytZpliku.split("[|]");
                    String index = split[0].replace(") ", ")");
                    String values = split[1].replace(" ", "");
                    baseMap.put(index, values);
                }
                fileScanner.close();
            }
            else{
                System.out.println("Plik nie istnieje");
            }
        }catch (Exception e){
            System.out.println("Wystapil blad podczas odczytu pliku");
        }
        finally {
            scannerPlik.close();
            scanner.close();
        }
    }

    private boolean conatinsValue(String buffor){
        int bufforSize = SizeSlownika /2;
        LinkedList<String> slownikValues = baseMap.values().stream().collect(Collectors.toCollection(LinkedList::new));
        for(int s=slownikValues.size()-1; s>=0; s--){
            String simpleWord = slownikValues.get(s).replaceAll("_", "");
            if(buffor.length()==1){
                if(simpleWord.contains(buffor)){
                    indexStart = slownikValues.get(s).indexOf(buffor);
                    indexEnd = 1;
                    return true;
                }
                return false;
            }
            for(int i =0; i<2; i++) {
                String bufSub = buffor.substring(i, i + 3);
                if(simpleWord.contains(bufSub)){
                    indexStart = slownikValues.get(s).indexOf(bufSub);
                    indexEnd = 3;
                    return true;
                }
            }

            for(int i =0; i<=buffor.length()-bufforSize; i++){
                String bufSub = buffor.substring(i, i + 2);
                if(simpleWord.contains(bufSub)){
                    indexStart = slownikValues.get(s).indexOf(bufSub);
                    indexEnd = 2;
                    return true;
                }
            }
        }
        return false;
    }
}