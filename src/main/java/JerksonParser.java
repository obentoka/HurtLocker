import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JerksonParser {

    List<JerksonObject> grocery;

    public JerksonParser(){
        grocery = new ArrayList<JerksonObject>();
    }

    public String[] getObjects(String input){
        Pattern patt = Pattern.compile("[!;%*^#@]");
        return patt.split(input);
    }

    public void makeObjects(String[] inputArr){
        for(int i = 0; i < inputArr.length; i++){
            JerksonObject obj = new JerksonObject();
            putName(obj, inputArr[i]);
            i++;
            putPrice(obj, inputArr[i]);
            i++;
            putType(obj,inputArr[i]);
            i++;
            putExp(obj, inputArr[i]);
            grocery.add(obj);
            i++;
        }
    }

    public void putName(JerksonObject obj, String keyValue){
        Pattern name = Pattern.compile("(?i)(?<=name:).*");
        Matcher match = name.matcher(keyValue);
        if(match.find())
            obj.getObj().put("name", makeCorrectName(match.group()));
        else
            obj.getObj().put("name", "null");
    }

    public void putPrice(JerksonObject obj, String keyValue){
        Pattern price = Pattern.compile("(?i)(?<=price:).*");
        Matcher match = price.matcher(keyValue);
        if(match.find()) {
            if(!match.group().equals(""))
                obj.getObj().put("price", match.group());
            else
                obj.getObj().put("price", "null");
        }
        else
            obj.getObj().put("price", "null");
    }

    public void putType(JerksonObject obj, String keyValue){
        Pattern type = Pattern.compile("(?i)(?<=type:).*");
        Matcher match = type.matcher(keyValue);
        if(match.find())
            obj.getObj().put("type", match.group());
        else
            obj.getObj().put("type", "null");
    }

    public void putExp(JerksonObject obj, String keyValue){
        Pattern exp = Pattern.compile("(?i)(?<=expiration:).*");
        Matcher match = exp.matcher(keyValue);
        if(match.find())
            obj.getObj().put("exp", match.group());
        else
            obj.getObj().put("exp", "null");
    }

    public String makeCorrectName(String wrong){
        Pattern milk = Pattern.compile("(?i)milk");
        Matcher match = milk.matcher(wrong);
        if(match.find())
            return "Milk";
        Pattern bread = Pattern.compile("(?i)bread");
        match = bread.matcher(wrong);
        if(match.find())
            return "Bread";
        Pattern cookies = Pattern.compile("(?i)c..kies");
        match = cookies.matcher(wrong);
        if(match.find())
            return "Cookies";
        Pattern apple = Pattern.compile("(?i)apples");
        match = apple.matcher(wrong);
        if(match.find())
            return "Apples";
        return "null";
    }

    public void count(){
        int error, milk, mp1, mp2, bread, bp, cookies, cp, apples, ap1, ap2;
        error = milk = mp1 = mp2 = bread = bp = cookies = cp = apples = ap1 = ap2 = 0;
        for(JerksonObject i : grocery){
            if(i.getObj().containsKey("null") || i.getObj().containsValue("null"))
                error++;
            else {
                if(i.getObj().get("name").equals("Milk")){
                    milk++;
                    if(i.getObj().get("price").equals("3.23"))
                        mp1++;
                    else
                        mp2++;
                }
                if(i.getObj().get("name").equals("Bread")){
                    bread++;
                    bp++;
                }
                if(i.getObj().get("name").equals("Cookies")){
                    cookies++;
                    cp++;
                }
                if(i.getObj().get("name").equals("Apples")){
                    apples++;
                    if(i.getObj().get("price").equals("0.25"))
                        ap1++;
                    else
                        ap2++;
                }
            }
        }
        printOutput(milk,mp1,mp2,bread,bp,cookies,cp,apples,ap1,ap2,error);
    }

    public void printOutput(int m, int mp1, int mp2, int b, int bp,
                            int c, int cp, int a, int ap1, int ap2,int err){
        String line = "=============";
        String lin = "-------------";
        System.out.printf("name:%8s%12s: %d times\n", "Milk", "seen", m);
        System.out.printf("%s%8s%s\n", line, "", line);
        System.out.printf("Price:%7s%12s: %d times\n", "3.23", "seen", mp1);
        System.out.printf("%s%8s%s\n", lin, "", lin);
        System.out.printf("Price:%7s%12s: %d times\n\n", "1.23", "seen", mp2);
        System.out.printf("name:%8s%12s: %d times\n", "Bread", "seen", b);
        System.out.printf("%s%8s%s\n", line, "", line);
        System.out.printf("Price:%7s%12s: %d times\n", "1.23", "seen", bp);
        System.out.printf("%s%8s%s\n\n", lin, "", lin);
        System.out.printf("name:%8s%12s: %d times\n", "Cookies", "seen", c);
        System.out.printf("%s%8s%s\n", line, "", line);
        System.out.printf("Price:%7s%12s: %d times\n", "2.25", "seen", cp);
        System.out.printf("%s%8s%s\n\n", lin, "", lin);
        System.out.printf("name:%8s%12s: %d times\n", "Apples", "seen", a);
        System.out.printf("%s%8s%s\n", line, "", line);
        System.out.printf("Price:%7s%12s: %d times\n", "0.25", "seen", ap1);
        System.out.printf("%s%8s%s\n", lin, "", lin);
        System.out.printf("Price:%7s%12s: %d times\n\n", "0.23", "seen", ap2);
        System.out.printf("%-15s%10s: %d times\n", "Errors", "seen", err);
    }
}
