package aleks;

import java.util.UUID;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        UUID uuid = UUID.randomUUID();
        String longLink = "https://stackoverflow.com/questions/17984975/convert-int-to-char-in-java";
        String link = "http://short/";
        System.out.println( uuid );
        System.out.println((int)(Math.random()*10));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(link);
        for(int i = 0; i < 8; i++){
            int a,b, average, random, result;
            a = uuid.toString().charAt(i);
            b = longLink.charAt(longLink.length() - 1 - i);
            average = (a + b) / 2;
            random = (int)(Math.random() * 10);
            result = random + average;
            stringBuilder.append((char)result);
        }
        System.out.println(stringBuilder.toString());
    }
}
