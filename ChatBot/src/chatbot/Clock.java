/*
 * Clock
 */
package chatbot;

import static chatbot.ChatBot.doc;
import static chatbot.ChatBot.mc;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 *
 * @author David
 */
public class Clock {

    private final DateTimeFormatter dateFormat;
    private final DateTimeFormatter timeFormat;
    private LocalDateTime currentDateTime;
    private String currentDate;
    private String currentTime;
    private String alarmTime;

    private FileInputStream FIS;
    private BufferedInputStream BIS;
    private Player player;

    public Clock() {
        this.alarmTime = "";
        this.dateFormat = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
        this.timeFormat = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM);
        start();
    }

    public void setAlarmTime(String time) {
        alarmTime = time;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void stopAlarm() {
        stop();
    }

    private void start() {
        // Clock
        new Thread("Clock") {
            @Override
            public void run() {
                while (true) {
                    currentDateTime = LocalDateTime.now();
                    currentTime = timeFormat.format(currentDateTime);
                    currentDate = dateFormat.format(currentDateTime);

                    ChatBot.setTime(timeFormat.format(currentDateTime));
                    ChatBot.setDisplayDate(currentDate);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }

                    if (currentTime.equals(alarmTime)) {
                        try {
                            doc.insertString(doc.getLength(), "Chatbot: Alarm rang at " + currentTime + "\n", null);
                        } catch (BadLocationException ex) {
                            Logger.getLogger(ChatBot.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        mc.Pause();
                        System.out.println(Paths.get(".").toAbsolutePath().normalize().toString() + "\\alarm.mp3");
                        ring(Paths.get(".").toAbsolutePath().normalize().toString() + "\\alarm.mp3");
                        ChatBot.setmusicDisplay("Alarm ringing");
                        ChatBot.setnotiBarChat("Alarm ringing");
                        ChatBot.setmusicStatus("Alarm ringing");
                    }
                } // for
            } // run()
        }.start();
    }

    public void ring(String path) {
        try {
            FIS = new FileInputStream(path);
            BIS = new BufferedInputStream(FIS);

            player = new Player(BIS);

        } catch (FileNotFoundException | JavaLayerException ex) {
        }

        new Thread() {
            @Override
            public void run() {
                try {
                    player.play();

                    if (player.isComplete()) {
                    }
                } catch (JavaLayerException ex) {
                }
            }
        }.start();
    }
    
    public void stop() {
        if (player != null) {
            player.close();

            ChatBot.setmusicDisplay("Open your music folder to play songs");
            ChatBot.setnotiBarChat("Remeber to key in your ATS!");
            ChatBot.setmusicStatus("Stopped");
            ChatBot.setnoOfSongs("");
        }
    }
}
