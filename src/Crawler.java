import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by kyle on 2017. 6. 19..
 * OpenSource Class http://blog.naver.com/ndb796/220990407718
 */
public class Crawler {
    private static String Address;
    private static String filename;
    private static URL Url;
    private static BufferedReader br;
    private static HttpURLConnection con;
    private static String protocol = "GET";
    private static IOManager io;

    Crawler(String Address, String filename) {
        this.Address = Address;
        this.filename = filename;
//        io = new IOManager(filename);
        io = new IOManager();
        io.init(filename);
        try {
            init(Address);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init(String Address) throws IOException {
        try {
            Url = new URL(Address);
            con = (HttpURLConnection) Url.openConnection();
            con.setRequestMethod(protocol);
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            br = new BufferedReader(new InputStreamReader(con.getInputStream(), "Euc-kr"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            String temp;
            String input = "";
            io.openWrite();
            while ((temp = br.readLine()) != null) {
                io.write(temp + "\n");
//                System.out.println(temp);
            }
            io.closeWrite();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void extractChosunTopnews() {
        try {
            String article[];
            String articles;
            io.init(filename);
            io.openRead();
            articles = io.read();
//            System.out.println(article);
            io.closeRead();
            article = articles.split("<div id=\"top_news\">");
            article = article[1].split("<!-- top_news -->");
            System.out.println(article[0]);
            io.init("DB/Chosun/articles");
            io.openWrite();
            io.write("<div id=\"top_news\">\n"+article[0]);
            io.closeWrite();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
