/*
 * The methods for the chatbot are here
 */
package chatbot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import static chatbot.ChatBotGUI_V2.clock;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author David
 */
public class Methods {

    // Takes a decimal number as arg then returns binary and
    // hex value of the decimal as a string
    public void Decimal2Bin() {
        // Keep window on top
        final JDialog onTop = new JDialog();
        onTop.setAlwaysOnTop(true);

        String value = JOptionPane.showInputDialog(onTop, "Enter a decimal");
        int intValue = Integer.parseInt(value);
        if (intValue == JOptionPane.CANCEL_OPTION || intValue == JOptionPane.CLOSED_OPTION) {
            System.exit(0);
        }

        // Converts and prints the answer
        JOptionPane.showMessageDialog(onTop, intValue + " in binary: " + Integer.toBinaryString(intValue) + "\n" + intValue
                + " in hexadecimal: " + (Integer.toHexString(intValue)).toUpperCase());
    } // end Decimal2Bin

    // Takes binary/hex value and converts to decimal
    public void decode() {
        // Keep window on top
        final JDialog onTop = new JDialog();
        onTop.setAlwaysOnTop(true);

        String value = JOptionPane.showInputDialog(onTop, "Enter a binary/hex number");

        String base = JOptionPane.showInputDialog(onTop, "Enter the base (2 for binary, 16 for hex)");
        int intBase = Integer.parseInt(base);
        if (intBase == JOptionPane.CANCEL_OPTION || intBase == JOptionPane.CLOSED_OPTION) {
            System.exit(0);
        }

        //Integer.valueOf("AB", 16);
        JOptionPane.showMessageDialog(onTop, Integer.valueOf(value, intBase));
    } // end decode

    // Flips a coin
    public String coinFlip() {
        // new Random object
        Random randomGenerator = new Random();

        String[] flip = {"heads", "tails"};
        return (flip[randomGenerator.nextInt(2)]);
    } // end coinFlip()

    public void clock() {
        DateTimeFormatter time = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM);
        LocalDateTime currentDateTime = LocalDateTime.now();
        String currentTime = time.format(currentDateTime);

        clock.setText(time.format(currentDateTime));

        try {
            TimeUnit.SECONDS.sleep(1);
            currentDateTime = LocalDateTime.now();
        } catch (InterruptedException e) {

        }

        if (!currentTime.equals(time.format(currentDateTime))) {
            clock();
        }

    } // end clock()

    String json;

    // Gets current uv levels
    public String getData() {

        try {

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet getRequest = new HttpGet("https://api.data.gov.sg/v1/environment/uv-index");
            getRequest.addHeader("api-key", "EPIFphWuoUyovMc6GGbJTm91B4JzAkE5");

            HttpResponse response = httpClient.execute(getRequest);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

            String output;
            while ((output = br.readLine()) != null) {
                //System.out.println(output);
                json = output;
            }

            httpClient.getConnectionManager().shutdown();

        } catch (ClientProtocolException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();
        }

        Gson gson = new Gson();

        UV uvAPI = gson.fromJson(json, UV.class);
        Items[] array = uvAPI.getItems();
        String items = array[0] + "";
        JsonElement jelement = new JsonParser().parse(items);
        JsonArray jarray = jelement.getAsJsonArray();

        Index index;
        String level;
        String output = "";

        for (int i = 0; i < jarray.size(); i++) {
            index = gson.fromJson(jarray.get(i), Index.class);

            String time = index.getTimestamp();
            time = time.substring(11, time.length() - 12); // formats the time
            String morn_night = "AM";
            int intTime = Integer.parseInt(time);

            // Check is am or pm
            if (intTime >= 12) {
                morn_night = "PM";
                intTime -= 12;
                if (intTime == 0) {
                    intTime = 12;
                }
            }

            // Checks uv level
            if (Integer.parseInt(index.getValue()) <= 3) {
                level = "Low";
            } else if (Integer.parseInt(index.getValue()) <= 5) {
                level = "Mod";
            } else if (Integer.parseInt(index.getValue()) <= 7) {
                level = "High";
            } else if (Integer.parseInt(index.getValue()) <= 10) {
                level = "Very High";
            } else {
                level = "Extreme";
            }

            output += "UV index at " + intTime + " " + morn_night + " is " + index.getValue() + " (" + level + ")\n";
        } // End for loop

        return output;
        //System.out.println(items);
    } // End getData()
} // End Methods class