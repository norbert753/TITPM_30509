import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.stream.Collectors;

public class Alg {
    private String pattern ="";
    private String message;
    private Integer i= 0;
    private Scanner scanner = new Scanner(System.in);
    LinkedHashMap<Integer, String> baseMap = new LinkedHashMap<>();
    LinkedList<Integer> lisat2 = new LinkedList<>();

    public void  zapiszWaid(){
        System.out.println( " podaj wiadomość");
        message = scanner.nextLine();


        slowMessage();

    }
    public void slowMessage() {

        List<Character> collect = message.chars().mapToObj(o -> (char) o).distinct().collect(Collectors.toList());
        Collections.sort(collect);

        for (Character value : collect) {
            i++;
            baseMap.put(i, value.toString());

        }
        upgradeslow();
    }

    private void upgradeslow() {
        for(int j = 0; j<=message.length()-1; j++) {
            if (j == message.length() - 1) {
                String lastChar = String.valueOf(message.charAt(j));
                List<Integer> lastPattern = baseMap.entrySet().stream().filter(v -> v.getValue().equals(lastChar)).map(v -> v.getKey()).collect(Collectors.toList());
                lisat2.add(lastPattern.get(0));
            } else {
                pattern += message.substring(j, j + 2);
                if (baseMap.containsValue(pattern)) {
                    pattern = String.valueOf(message.charAt(j));
                } else {
                    int index = pattern.length() - 1;
                    String krotkaWiad = pattern.substring(0, index);
                    List<Integer> indexpattern = baseMap.entrySet().stream().filter(v -> v.getValue().equals(krotkaWiad)).map(v -> v.getKey()).collect(Collectors.toList());
                    lisat2.add(indexpattern.get(0));
                    i++;
                    baseMap.put(i, pattern);
                    pattern = "";
                }
            }
        }
        readCod();
    }

    private void readCod(){
        for (Integer index: lisat2){
            System.out.print(index + " ");
        }
    }
public void decodeMEssage(){
        String encodedMessage = "";
        for (Integer nazwa:lisat2){
            encodedMessage += baseMap.get(nazwa);
        }
    System.out.println("Odszyfowana:" + encodedMessage);
}

    public void zapDoPLiku() {
        if (baseMap.isEmpty()) {
            System.out.println("Słownik jest pusta\nZapis do pliku przerwany");
            return;
        }
        try {
            System.out.print("Podaj nazwe pliku: ");
            String nazwaPliku = scanner.nextLine();
            File file = new File(nazwaPliku);
            if (!file.exists()) {
                System.out.println("Plik zostal utworzony");
                file.createNewFile();
            }
            if (file.canWrite()) {
                FileWriter fileWriter = new FileWriter(file);
                Formatter formatter = new Formatter(fileWriter);
                for(Map.Entry<Integer,String>mapo:baseMap.entrySet()){
                    formatter.format("%s | %s\r\n", mapo.getKey(), mapo.getValue());
                }
                formatter.close();
                fileWriter.close();
            }
            System.out.println("Plik zostal zapisany");
        } catch (Exception e) {
            System.out.println("Wystapil problem podczas zapisu do pliku");
            System.out.println(e.getMessage());
        }
    }
    public void odczytajZPliku(){
        try{
            baseMap.clear();
            System.out.print("Podaj nazwe pliku: ");
            String nazwaPliku = scanner.nextLine();
            File file = new File(nazwaPliku);
            String odczytZpliku;
            if (file.exists()){
                Scanner fileScanner = new Scanner(file);
                while(fileScanner.hasNextLine()){
                    odczytZpliku = fileScanner.nextLine();
                    String[] split = odczytZpliku.split("[|]\s");
                    int index = Integer.parseInt(split[0].replace(" ", ""));
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
    }
}
