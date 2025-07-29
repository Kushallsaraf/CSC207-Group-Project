package Cache;

/** To make caching requests easier, this class helps normalize user input.
 *
 */
public class NameFormatter {

    public String normalizeName(String gameName){

        if (gameName == null){
            return "";
        }
        return gameName.toLowerCase().replace(" ", "");


    }


    public static void main(String[] args) {
        NameFormatter f = new NameFormatter();
        System.out.println(f.normalizeName("God of War"));
        System.out.println(f.normalizeName("ThE WiTCHER3"));
        System.out.println(f.normalizeName("mass effect       2"));
        System.out.println(f.normalizeName(""));
    }



}
