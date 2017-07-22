/*
 * Main class for the chatbot
 */
package chatbot;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *
 * @author David
 */
public class ChatBot extends javax.swing.JFrame {

    /**
     * Creates new form ChatBotGUI_V2
     */
    private final Music mc;
    private final Music mcAlarm;
    // for alarm clock
    DateTimeFormatter date;
    DateTimeFormatter time;
    LocalDateTime currentDateTime;
    String currentDate;
    private String currentTime;
    private String alarmTime;
    // inputs
    private String input;
    private String filteredInput;
    // For font color
    private final StyledDocument doc;
    private final Style orange;
    private final Style white;
    // Replies
    private HashMap<String, String> questionAnswer;
    private final ArrayList<String> greetings;
    private final ArrayList<String> jokes;
    private final ArrayList<String> goodbye;
    private final ArrayList<String> defaultReply;
    private final ArrayList<String> sorry;
    // RNG
    private final RNG rngReply;
    private final RNG rngTime;

    public ChatBot() {
        this.alarmTime = "";
        this.rngReply = new RNG();
        this.rngTime = new RNG();
        this.mc = new Music();
        this.mcAlarm = new Music();
        this.date = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
        this.time = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM);
        initComponents();

        // Set font color
        doc = chatArea.getStyledDocument();
        orange = chatArea.addStyle("style", null);
        white = chatArea.addStyle("style2", null);
        StyleConstants.setForeground(orange, Color.ORANGE);
        StyleConstants.setForeground(white, Color.WHITE);

        // Read stored replies from file
        greetings = Methods.readFile(System.getProperty("user.dir") + "\\replies\\greetings.txt");
        jokes = Methods.readFile(System.getProperty("user.dir") + "\\replies\\jokes.txt");
        goodbye = Methods.readFile(System.getProperty("user.dir") + "\\replies\\goodbye.txt");
        defaultReply = Methods.readFile(System.getProperty("user.dir") + "\\replies\\default.txt");
        sorry = Methods.readFile(System.getProperty("user.dir") + "\\replies\\sorry.txt");

        if (mc.getProp("fileChoosen").equals("yes")) {
            Display.setText("Click the three dots to start");
        }

        // Initiate convo
        try {
            doc.insertString(doc.getLength(), "Type " + "\"" + "help" + "\"" + " to show the list of commands available.\n", white);
            doc.insertString(doc.getLength(), "Chatbot: Hi nice to meet you here is a quote for you!\n"
                    + Methods.getQuote() + "\n", null);
        } catch (BadLocationException e) {
            System.out.println(e);
        }

        // Clock
        new Thread("Clock") {
            @Override
            public void run() {
                while (true) {
                    currentDateTime = LocalDateTime.now();
                    currentTime = time.format(currentDateTime);
                    currentDate = date.format(currentDateTime);

                    clock.setText(time.format(currentDateTime));
                    displayDate.setText(currentDate);

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
                        mcAlarm.Play(Paths.get(".").toAbsolutePath().normalize().toString() + "\\alarm.mp3");
                        Display.setText("Alarm ringing");
                        notiBarChat.setText("Alarm ringing");
                        musicStatus.setText("Alarm ringing");
                    }

                } // for
            } // run()
        }.start();

        // Read questions from file
        try {
            FileInputStream fileIn = new FileInputStream(Paths.get(".").toAbsolutePath().normalize().toString() + "/questions.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            questionAnswer = (HashMap<String, String>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException i) {
            JOptionPane.showMessageDialog(rootPane, "questions.ser might be missing", "Error", 0);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        questionField = new javax.swing.JTextField();
        answerField = new javax.swing.JTextField();
        mainPanel = new javax.swing.JPanel();
        homePanel = new javax.swing.JPanel();
        noOfSongs = new javax.swing.JLabel();
        snoozeHome = new javax.swing.JLabel();
        notiBarHome = new javax.swing.JLabel();
        homeButtonHome = new javax.swing.JLabel();
        chatButtonHome = new javax.swing.JLabel();
        displayDate = new javax.swing.JLabel();
        clock = new javax.swing.JLabel();
        play = new javax.swing.JLabel();
        pause = new javax.swing.JLabel();
        stop = new javax.swing.JLabel();
        Display = new javax.swing.JLabel();
        next = new javax.swing.JLabel();
        previous = new javax.swing.JLabel();
        choose = new javax.swing.JLabel();
        musicStatus = new javax.swing.JLabel();
        backgroundHome = new javax.swing.JLabel();
        chatPanel = new javax.swing.JPanel();
        snoozeChat = new javax.swing.JLabel();
        notiBarChat = new javax.swing.JLabel();
        inputField = new javax.swing.JTextField();
        sendButton = new javax.swing.JButton();
        ScrollPane = new javax.swing.JScrollPane();
        chatArea = new javax.swing.JTextPane();
        homeButtonChat = new javax.swing.JLabel();
        chatButtonChat = new javax.swing.JLabel();
        typingStatus = new javax.swing.JLabel();
        backgroundChat = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Project Chatbot");
        setMinimumSize(new java.awt.Dimension(1365, 763));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        mainPanel.setMaximumSize(new java.awt.Dimension(1365, 763));
        mainPanel.setMinimumSize(new java.awt.Dimension(1365, 763));
        mainPanel.setPreferredSize(new java.awt.Dimension(1365, 763));
        mainPanel.setLayout(new java.awt.CardLayout());

        homePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        noOfSongs.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        noOfSongs.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        homePanel.add(noOfSongs, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 610, 130, 30));

        snoozeHome.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        snoozeHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                snoozeHomeMouseReleased(evt);
            }
        });
        homePanel.add(snoozeHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(1285, 0, 65, 65));

        notiBarHome.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        notiBarHome.setForeground(new java.awt.Color(0, 204, 204));
        notiBarHome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        notiBarHome.setText("Remember to key in your ATS!");
        homePanel.add(notiBarHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 14, 1030, 20));

        homeButtonHome.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        homePanel.add(homeButtonHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 190, 80));

        chatButtonHome.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        chatButtonHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                chatButtonHomeMouseReleased(evt);
            }
        });
        homePanel.add(chatButtonHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 190, 80));

        displayDate.setFont(new java.awt.Font("DS-Digital", 0, 50)); // NOI18N
        displayDate.setForeground(new java.awt.Color(255, 255, 255));
        displayDate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        displayDate.setText("Sunday, 7 May, 2017");
        homePanel.add(displayDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 270, 1060, 60));

        clock.setFont(new java.awt.Font("DS-Digital", 0, 150)); // NOI18N
        clock.setForeground(new java.awt.Color(255, 255, 255));
        clock.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        clock.setText("6:23:25 PM");
        homePanel.add(clock, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, 1070, 310));

        play.setToolTipText("Play");
        play.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        play.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                playMouseReleased(evt);
            }
        });
        homePanel.add(play, new org.netbeans.lib.awtextra.AbsoluteConstraints(668, 511, 90, 90));

        pause.setToolTipText("Pause");
        pause.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pause.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                pauseMouseReleased(evt);
            }
        });
        homePanel.add(pause, new org.netbeans.lib.awtextra.AbsoluteConstraints(778, 530, 70, 70));

        stop.setToolTipText("Stop");
        stop.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        stop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                stopMouseReleased(evt);
            }
        });
        homePanel.add(stop, new org.netbeans.lib.awtextra.AbsoluteConstraints(589, 539, 60, 60));

        Display.setFont(new java.awt.Font("Serif", 0, 36)); // NOI18N
        Display.setForeground(new java.awt.Color(255, 255, 255));
        Display.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Display.setText("Open your music folder to play songs");
        homePanel.add(Display, new org.netbeans.lib.awtextra.AbsoluteConstraints(411, 423, 600, 50));

        next.setToolTipText("Next");
        next.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        next.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                nextMouseReleased(evt);
            }
        });
        homePanel.add(next, new org.netbeans.lib.awtextra.AbsoluteConstraints(864, 552, 60, 40));

        previous.setToolTipText("Previous");
        previous.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        previous.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                previousMouseReleased(evt);
            }
        });
        homePanel.add(previous, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 550, 60, 40));

        choose.setToolTipText("Select your music directory");
        choose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        choose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                chooseMouseReleased(evt);
            }
        });
        homePanel.add(choose, new org.netbeans.lib.awtextra.AbsoluteConstraints(952, 560, 50, 30));

        musicStatus.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        musicStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        musicStatus.setText("Not playing");
        homePanel.add(musicStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 610, 170, -1));

        backgroundHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/chatbotbgHome.png"))); // NOI18N
        backgroundHome.setMaximumSize(new java.awt.Dimension(10, 10));
        backgroundHome.setMinimumSize(new java.awt.Dimension(10, 10));
        backgroundHome.setPreferredSize(new java.awt.Dimension(10, 10));
        homePanel.add(backgroundHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -350, 1820, 1460));

        mainPanel.add(homePanel, "card3");

        chatPanel.setMaximumSize(new java.awt.Dimension(1365, 763));
        chatPanel.setMinimumSize(new java.awt.Dimension(1365, 763));
        chatPanel.setPreferredSize(new java.awt.Dimension(1365, 763));
        chatPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        snoozeChat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        snoozeChat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                snoozeChatMouseReleased(evt);
            }
        });
        chatPanel.add(snoozeChat, new org.netbeans.lib.awtextra.AbsoluteConstraints(1285, 0, 65, 65));

        notiBarChat.setFont(new java.awt.Font("Serif", 0, 18)); // NOI18N
        notiBarChat.setForeground(new java.awt.Color(0, 204, 204));
        notiBarChat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        notiBarChat.setText("Remember to key in your ATS!");
        chatPanel.add(notiBarChat, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 14, 1030, 20));

        inputField.setBackground(new java.awt.Color(4, 4, 4));
        inputField.setFont(new java.awt.Font("SansSerif", 0, 28)); // NOI18N
        inputField.setForeground(new java.awt.Color(233, 202, 6));
        inputField.setCaretColor(new java.awt.Color(255, 255, 255));
        inputField.setFocusCycleRoot(true);
        inputField.setNextFocusableComponent(sendButton);
        inputField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inputFieldKeyPressed(evt);
            }
        });
        chatPanel.add(inputField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 630, 990, 50));

        sendButton.setBackground(new java.awt.Color(105, 105, 105));
        sendButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Chat-48.png"))); // NOI18N
        sendButton.setToolTipText("Click to send");
        sendButton.setOpaque(false);
        sendButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sendButtonMouseClicked(evt);
            }
        });
        chatPanel.add(sendButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(1200, 630, 90, 50));

        ScrollPane.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 204, 204)));
        ScrollPane.setAutoscrolls(true);
        ScrollPane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        ScrollPane.setFocusable(false);
        ScrollPane.setNextFocusableComponent(inputField);
        ScrollPane.setRequestFocusEnabled(false);

        chatArea.setEditable(false);
        chatArea.setBackground(new java.awt.Color(0, 0, 0));
        chatArea.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        chatArea.setFont(new java.awt.Font("SansSerif", 0, 28)); // NOI18N
        chatArea.setForeground(new java.awt.Color(70, 173, 212));
        chatArea.setFocusCycleRoot(false);
        chatArea.setFocusable(false);
        chatArea.setRequestFocusEnabled(false);
        ScrollPane.setViewportView(chatArea);

        chatPanel.add(ScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 90, 1090, 460));

        homeButtonChat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        homeButtonChat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                homeButtonChatMouseReleased(evt);
            }
        });
        chatPanel.add(homeButtonChat, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 190, 80));

        chatButtonChat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        chatPanel.add(chatButtonChat, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 190, 80));

        typingStatus.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        typingStatus.setForeground(new java.awt.Color(70, 173, 212));
        typingStatus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chatPanel.add(typingStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 550, 280, 60));

        backgroundChat.setForeground(new java.awt.Color(255, 255, 255));
        backgroundChat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/chatbotbg.png"))); // NOI18N
        backgroundChat.setMaximumSize(new java.awt.Dimension(10, 10));
        backgroundChat.setMinimumSize(new java.awt.Dimension(10, 10));
        backgroundChat.setPreferredSize(new java.awt.Dimension(10, 10));
        chatPanel.add(backgroundChat, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -350, 1820, 1460));

        mainPanel.add(chatPanel, "card2");

        getContentPane().add(mainPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1360, 760));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void sendButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sendButtonMouseClicked
        inputFunction();
    }//GEN-LAST:event_sendButtonMouseClicked

    private void inputFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputFieldKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            inputFunction();
        }
    }//GEN-LAST:event_inputFieldKeyPressed

    private void homeButtonChatMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeButtonChatMouseReleased
        Methods.changePanel(mainPanel, homePanel);
    }//GEN-LAST:event_homeButtonChatMouseReleased

    private void chatButtonHomeMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chatButtonHomeMouseReleased
        Methods.changePanel(mainPanel, chatPanel);
    }//GEN-LAST:event_chatButtonHomeMouseReleased

    private void stopMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stopMouseReleased
        mc.Stop();
        mc.stopped = true;
    }//GEN-LAST:event_stopMouseReleased

    private void playMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_playMouseReleased
        try {
            mc.Resume();
        } catch (IOException ex) {
            Logger.getLogger(ChatBot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_playMouseReleased

    private void pauseMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pauseMouseReleased
        mc.Pause();
    }//GEN-LAST:event_pauseMouseReleased

    private void chooseMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chooseMouseReleased
        try {
            mc.chooseDir();
        } catch (IndexOutOfBoundsException ex) {
            JOptionPane.showMessageDialog(null, "No playable files", "Error", 0);
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(null, "No such directory", "Error", 0);
        }
    }//GEN-LAST:event_chooseMouseReleased

    private void nextMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nextMouseReleased
        mc.Stop();
        mc.next();
    }//GEN-LAST:event_nextMouseReleased

    private void previousMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_previousMouseReleased
        if (mc.songNo > 0) {
            mc.Stop();
            mc.prev();
        }
    }//GEN-LAST:event_previousMouseReleased

    private void snoozeHomeMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_snoozeHomeMouseReleased
        if (!alarmTime.equals("")) {
            JOptionPane.showMessageDialog(null, "Alarm dismissed");
        } else {
            JOptionPane.showMessageDialog(null, "No alarms to dismiss");
        }
        mcAlarm.Stop();
        alarmTime = "";
    }//GEN-LAST:event_snoozeHomeMouseReleased

    private void snoozeChatMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_snoozeChatMouseReleased
        if (!alarmTime.equals("")) {
            JOptionPane.showMessageDialog(null, "Alarm dismissed");
        } else {
            JOptionPane.showMessageDialog(null, "No alarms to dismiss");
        }
        mcAlarm.Stop();
        alarmTime = "";
    }//GEN-LAST:event_snoozeChatMouseReleased

    public static void main(String args[]) {
        /* Set the Windows look and feel */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChatBot.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            ChatBot frame = new ChatBot();
            frame.setVisible(true);
            // set icon
            frame.setIconImage(Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir") + "\\images\\icon.png"));
        });
    }

    // handles user input
    private void inputFunction() {
        new Thread("Reply") {
            @Override
            public void run() {
                try {
                    // Get user input and filter it, if input is empty do nothing.
                    input = inputField.getText();
                    if (input.equals("")) {
                        return;
                    }
                    filteredInput = Methods.filter(input);

                    // Display input
                    doc.insertString(doc.getLength(), "You: " + input + "\n", orange);

                    // Allow user to add question and answer
                    if (filteredInput.equals("set question")) {
                        Object[] input = {"Question:", questionField, "Answer:", answerField};

                        int option = JOptionPane.showConfirmDialog(rootPane, input, "Set Question", JOptionPane.OK_CANCEL_OPTION);
                        if (option == JOptionPane.OK_OPTION) {
                            String questionText = Methods.filter(questionField.getText());
                            String answerText = answerField.getText();
                            if (!questionText.isEmpty() && !answerText.isEmpty()) {
                                questionAnswer.put(questionText, answerText);
                                questionField.setText("");
                                answerField.setText("");
                                try {
                                    FileOutputStream fileOut = new FileOutputStream(Paths.get(".").toAbsolutePath().normalize().toString() + "/questions.ser");
                                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                                    out.writeObject(questionAnswer);
                                    out.close();
                                    fileOut.close();
                                    doc.insertString(doc.getLength(), "Chatbot: Question successfully added" + "\n", null);
                                } catch (IOException i) {
                                    doc.insertString(doc.getLength(), "Chatbot: IOException Error" + "\n", null);
                                }
                            } else {
                                doc.insertString(doc.getLength(), "Chatbot: Invalid input" + "\n", null);
                            }
                        } else {
                            doc.insertString(doc.getLength(), "Chatbot: Cancelled" + "\n", null);
                            questionField.setText("");
                            answerField.setText("");
                            resetInputField();
                            return;
                        }
                        resetInputField();
                        return;
                    }
                    // Checks if input is a set question, reply with set answer if it is
                    for (Map.Entry question : questionAnswer.entrySet()) {
                        String key = "" + question.getKey();
                        if (filteredInput.equals(key)) {
                            doc.insertString(doc.getLength(), "Chatbot: " + question.getValue() + "\n", null);
                            resetInputField();
                            return;
                        }
                    }

                    // Commands that require String manipulation
                    if (filteredInput.contains("set alarm")) {
                        alarmTime = input.substring(10);
                        doc.insertString(doc.getLength(), "Chatbot: Alarm set at " + alarmTime + "\n", null);
                    } else if (filteredInput.contains("encode")) {
                        String value = input.substring(7);
                        doc.insertString(doc.getLength(), "Chatbot: " + Methods.Decimal2Bin(value) + "\n", null);
                    } else if (filteredInput.contains("decode")) {
                        String v1 = input.substring(7, input.length() - 2);
                        String v2 = input.substring(input.length() - 1);
                        doc.insertString(doc.getLength(), "Chatbot: " + v1 + " converted to base 10 is " + Methods.decode(v1, v2) + "\n", null);
                    } else if (filteredInput.contains("remove question")) {
                        String x = filteredInput.substring(16);
                        boolean valid = false;
                        for (String key : questionAnswer.keySet()) {
                            if (x.equals(key)) {
                                questionAnswer.remove(x);
                                valid = true;
                                try {
                                    FileOutputStream fileOut = new FileOutputStream(Paths.get(".").toAbsolutePath().normalize().toString() + "/questions.ser");
                                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                                    out.writeObject(questionAnswer);
                                    out.close();
                                    fileOut.close();
                                    doc.insertString(doc.getLength(), "Chatbot: Question successfully removed" + "\n", null);
                                } catch (IOException i) {
                                    doc.insertString(doc.getLength(), "Chatbot: IOException Error" + "\n", null);
                                }
                                break;
                            }
                        }
                        if (!valid) {
                            doc.insertString(doc.getLength(), "Chatbot: Error No such question" + "\n", null);
                        }
                        // Normal replies
                    } else if (Methods.checkContains(filteredInput, "hello", "hi", "sup", "hey", "annyeong", "konichiwa")) {
                        typingStatus.setText("Chatbot is typing...");
                        Thread.sleep(rngTime.getNum(251) + 500);
                        typingStatus.setText("");
                        doc.insertString(doc.getLength(), "Chatbot: " + greetings.get(rngReply.getNum(greetings.size())) + "\n", null);
                    } else if (Methods.checkContains(filteredInput, "quote")) {
                        typingStatus.setText("Chatbot is typing...");
                        doc.insertString(doc.getLength(), "Chatbot: " + Methods.getQuote() + "\n", null);
                        typingStatus.setText("");
                    } else if (Methods.checkContains(filteredInput, "bye", "see you", "zai jian")) {
                        typingStatus.setText("Chatbot is typing...");
                        Thread.sleep(rngTime.getNum(251) + 500);
                        typingStatus.setText("");
                        doc.insertString(doc.getLength(), "Chatbot: " + goodbye.get(rngReply.getNum(goodbye.size())) + "\n", null);
                    } else if (Methods.checkContains(filteredInput, "do you like")) {
                        typingStatus.setText("Chatbot is typing...");
                        Thread.sleep(rngTime.getNum(251) + 500);
                        typingStatus.setText("");
                        doc.insertString(doc.getLength(), "Chatbot: I don\'t really have a preference.\n", null);
                    } else if (Methods.checkContains(filteredInput, "who are you", "who you")) {
                        typingStatus.setText("Chatbot is typing...");
                        Thread.sleep(rngTime.getNum(251) + 500);
                        typingStatus.setText("");
                        doc.insertString(doc.getLength(), "Chatbot: I'm a chitty chatty little bot.\n", null);
                    } else if (Methods.checkContains(filteredInput, "what is your name", "how do i address you")) {
                        typingStatus.setText("Chatbot is typing...");
                        Thread.sleep(rngTime.getNum(251) + 500);
                        typingStatus.setText("");
                        doc.insertString(doc.getLength(), "Chatbot: I am just called chatbot cause my creator have no creativity :(\n", null);
                    } else if (Methods.checkContains(filteredInput, "what do you do", "what can you do")) {
                        typingStatus.setText("Chatbot is typing...");
                        Thread.sleep(rngTime.getNum(251) + 500);
                        typingStatus.setText("");
                        doc.insertString(doc.getLength(), "Chatbot: I can do quite a few things for example playing music. You can see more by typing \"help\"\n", null);
                    } else if (Methods.checkContains(filteredInput, "ok", "yes", "no", "right")) {
                        typingStatus.setText("Chatbot is typing...");
                        Thread.sleep(rngTime.getNum(251) + 500);
                        typingStatus.setText("");
                        doc.insertString(doc.getLength(), "Chatbot: Okay\n", null);
                    } else if (Methods.checkContains(filteredInput, "are you real")) {
                        typingStatus.setText("Chatbot is typing...");
                        Thread.sleep(rngTime.getNum(251) + 500);
                        typingStatus.setText("");
                        doc.insertString(doc.getLength(), "Chatbot: I am more real than most people.\n", null);
                    } else if (Methods.checkContains(filteredInput, "how are you", "how is it going")) {
                        typingStatus.setText("Chatbot is typing...");
                        Thread.sleep(rngTime.getNum(251) + 500);
                        typingStatus.setText("");
                        doc.insertString(doc.getLength(), "Chatbot: I'm doing quite okay\n", null);
                    } else if (Methods.checkContains(filteredInput, "love you", "muacks", "xoxo")) {
                        typingStatus.setText("Chatbot is typing...");
                        Thread.sleep(rngTime.getNum(251) + 500);
                        typingStatus.setText("");
                        doc.insertString(doc.getLength(), "Chatbot: Aww That's nice.\n", null);
                    } else if (Methods.checkContains(filteredInput, "how are you created", "what are you written in")) {
                        typingStatus.setText("Chatbot is typing...");
                        Thread.sleep(rngTime.getNum(251) + 500);
                        typingStatus.setText("");
                        doc.insertString(doc.getLength(), "Chatbot: I am created in the Programming language called Java.\n", null);
                    } else if (Methods.checkContains(filteredInput, "sorry", "apologise")) {
                        typingStatus.setText("Chatbot is typing...");
                        Thread.sleep(rngTime.getNum(251) + 500);
                        typingStatus.setText("");
                        doc.insertString(doc.getLength(), "Chatbot: " + sorry.get(rngReply.getNum(sorry.size())) + "\n", null);
                    } else if (Methods.checkContains(filteredInput, "thanks", "xie xie")) {
                        typingStatus.setText("Chatbot is typing...");
                        Thread.sleep(rngTime.getNum(251) + 500);
                        typingStatus.setText("");
                        doc.insertString(doc.getLength(), "Chatbot: No problem!\n", null);
                    } else if (Methods.checkContains(filteredInput, "joke", "cheer me up", "need motivation", "bored")) {
                        typingStatus.setText("Chatbot is typing...");
                        Thread.sleep(rngTime.getNum(501) + 500);
                        typingStatus.setText("");
                        doc.insertString(doc.getLength(), "Chatbot: " + jokes.get(rngReply.getNum(jokes.size())) + "\n", null);
                    } else {
                        chatbot();
                    }

                    resetInputField();
                } catch (BadLocationException | InterruptedException ex) {
                    Logger.getLogger(ChatBot.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();
    }

    // clear and reset input text field
    private void resetInputField() {
        inputField.setText("");
        inputField.requestFocus();
    }

    // commands
    private void chatbot() {
        // Help msg
        String help = "ChatBot:    Commands avaliable\n"
                + "----------------------------------------------------\n"
                + "help\t\t\t\t\t\t- Displays this message\n"
                + "clear\t\t\t\t\t\t- Clears the screen\n"
                + "encode\t\t\t\t\t- Converts decimal number to binary/hex\n"
                + "decode <bin/hex> <base>\t\t- Converts a binary/hex to decimal\n"
                + "set question\t\t\t\t- Set/update a question\n"
                + "list questions\t\t\t\t- Lists all the set questions\n"
                + "uv\t\t\t\t\t\t- Shows the UV readings of the day\n"
                + "new quote\t\t\t\t\t- Shows a random quote\n"
                + "coinflip\t\t\t\t\t- Flips a coin\n"
                + "joke\t\t\t\t\t\t- Tells a joke\n"
                + "mc dir\t\t\t\t\t- Choose your music directory\n"
                + "mc stop\t\t\t\t\t- Stops the music\n"
                + "mc pause\t\t\t\t\t- Pause the music\n"
                + "mc resume\t\t\t\t\t- Resume the music\n"
                + "mc next\t\t\t\t\t- Plays the next song\n"
                + "mc prev\t\t\t\t\t- Plays the previous song\n"
                + "mc change dir\t\t\t\t- Change music dir\n"
                + "alarm\t\t\t\t\t\t- Displays any alarm set\n"
                + "set alarm 00:00:00 AM/PM\t\t- Set at entered time\n"
                + "dismiss alarm\t\t\t\t- Dismiss any alarm set\n"
                + "exit\t\t\t\t\t\t- Exits the program\n";

        try {
            // Send replies
            switch (filteredInput) {
                // Commands
                case "exit":
                    mc.Stop();
                    System.exit(0);
                    break;
                case "clear":
                    chatArea.setText("");
                    break;
                case "help":
                    doc.insertString(doc.getLength(), help, null);
                    break;
                case "coinflip":
                    doc.insertString(doc.getLength(), "Chatbot: " + Methods.coinFlip() + "\n", null);
                    break;
                case "list questions":
                    doc.insertString(doc.getLength(), "List of User added Questions\n----------------------------------------------------\n", null);
                    for (String s : questionAnswer.keySet()) {
                        doc.insertString(doc.getLength(), s + "\n", null);
                    }
                    break;
                case "mc resume":
                    mc.Resume();
                    doc.insertString(doc.getLength(), "Chatbot: Music resumed\n", null);
                    break;
                case "mc stop":
                    mc.Stop();
                    mc.stopped = true;
                    doc.insertString(doc.getLength(), "Chatbot: Music stopped\n", null);
                    break;
                case "mc pause":
                    mc.Pause();
                    doc.insertString(doc.getLength(), "Chatbot: Music paused\n", null);
                    break;
                case "mc next":
                    mc.Stop();
                    mc.next();
                    doc.insertString(doc.getLength(), "Chatbot: Playing next song\n", null);
                    break;
                case "mc prev":
                    if (mc.songNo > 0) {
                        mc.Stop();
                        mc.prev();
                        doc.insertString(doc.getLength(), "Chatbot: Playing previous song\n", null);
                    } else {
                        doc.insertString(doc.getLength(), "Chatbot: No previous song\n", null);
                    }
                    break;
                case "mc dir":
                    try {
                        mc.chooseDir();
                    } catch (IndexOutOfBoundsException ex) {
                        JOptionPane.showMessageDialog(null, "No playable files", "Error", 0);
                    } catch (NullPointerException ex) {
                        JOptionPane.showMessageDialog(null, "No such directory", "Error", 0);
                    }
                    doc.insertString(doc.getLength(), "Chatbot: Music directory choosen " + mc.folder + "\n", null);
                    break;
                case "uv":
                    typingStatus.setText("Getting data...");
                    doc.insertString(doc.getLength(), Methods.getData(), null);
                    typingStatus.setText("");
                    break;
                case "alarm":
                    if (!alarmTime.equals("")) {
                        doc.insertString(doc.getLength(), "Chatbot: Alarm set at " + alarmTime + "\n", null);
                    } else {
                        doc.insertString(doc.getLength(), "Chatbot: No alarm set\n", null);
                    }
                    break;
                case "dismiss alarm":
                    if (alarmTime.equals("")) {
                        doc.insertString(doc.getLength(), "Chatbot: No alarms to dismiss\n", null);
                    } else {
                        doc.insertString(doc.getLength(), "Chatbot: Alarm dismissed\n", null);
                    }
                    mcAlarm.Stop();
                    alarmTime = "";
                    break;
                case "mc change dir":
                    mc.changeDir();
                    doc.insertString(doc.getLength(), "Chatbot: Music directory changed\n", null);
                    break;
                default:
                    doc.insertString(doc.getLength(), "Chatbot: " + defaultReply.get(rngReply.getNum(defaultReply.size())) + "\n", null);
                    break;
            } // switch
        } catch (BadLocationException | IOException ex) {
            Logger.getLogger(ChatBot.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    static javax.swing.JLabel Display;
    private javax.swing.JScrollPane ScrollPane;
    private javax.swing.JTextField answerField;
    private javax.swing.JLabel backgroundChat;
    private javax.swing.JLabel backgroundHome;
    private javax.swing.JTextPane chatArea;
    private javax.swing.JLabel chatButtonChat;
    private javax.swing.JLabel chatButtonHome;
    private javax.swing.JPanel chatPanel;
    private javax.swing.JLabel choose;
    private javax.swing.JLabel clock;
    private javax.swing.JLabel displayDate;
    private javax.swing.JLabel homeButtonChat;
    private javax.swing.JLabel homeButtonHome;
    private javax.swing.JPanel homePanel;
    private javax.swing.JTextField inputField;
    private javax.swing.JPanel mainPanel;
    static javax.swing.JLabel musicStatus;
    private javax.swing.JLabel next;
    static javax.swing.JLabel noOfSongs;
    static javax.swing.JLabel notiBarChat;
    private javax.swing.JLabel notiBarHome;
    private javax.swing.JLabel pause;
    private javax.swing.JLabel play;
    private javax.swing.JLabel previous;
    private javax.swing.JTextField questionField;
    private javax.swing.JButton sendButton;
    private javax.swing.JLabel snoozeChat;
    private javax.swing.JLabel snoozeHome;
    private javax.swing.JLabel stop;
    private javax.swing.JLabel typingStatus;
    // End of variables declaration//GEN-END:variables
}
