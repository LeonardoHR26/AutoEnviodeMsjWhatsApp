import java.awt.event.KeyEvent;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.KeyAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class Sender extends JFrame {

    private JTextField phoneNumbersField;
    private JTextArea messageArea;
    private JTextArea linksTextArea;
    private JButton sendButton;
    private JButton copyAllButton;
    private JButton clearPhoneButton;
    private JButton clearMessageButton;
    private JButton clearLinksButton;
    private JButton sendMessagesButton;
    private JPanel linksPanel;
    private JSplitPane splitPane;

    public Sender() {
        configurarVentana();
        inicializarComponentes();
        configurarPaneles();
        configurarBotones();
        ajustarDivisor();
    }

    private void configurarVentana() {
        setTitle("Sender by Leonardohr26");
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
    }

    private void inicializarComponentes() {
        phoneNumbersField = new JTextField();
        phoneNumbersField.setToolTipText("Ingresa números de teléfono separados por comas");
        aplicarFiltroNumeros(phoneNumbersField);
        phoneNumbersField.setFont(new Font("Arial", Font.PLAIN, 20)); 

        messageArea = new JTextArea(10, 30);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        messageArea.setFont(new Font("Arial", Font.PLAIN, 20)); 

        linksTextArea = new JTextArea(10, 40);
        linksTextArea.setEditable(false);
        linksTextArea.setLineWrap(true);
        linksTextArea.setWrapStyleWord(true);
        linksTextArea.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        linksTextArea.setFont(new Font("Arial", Font.PLAIN, 16)); 

        sendButton = new JButton("Generar Enlaces");
        sendButton.setBackground(new Color(0x52F7D1)); 
        sendButton.setForeground(Color.BLACK);
        sendButton.setOpaque(true);
        sendButton.setBorderPainted(false);
        sendButton.setFont(new Font("Arial", Font.BOLD, 20)); 

        copyAllButton = new JButton("Copiar Todos");
        copyAllButton.setBackground(Color.BLUE); 
        copyAllButton.setForeground(Color.WHITE);
        copyAllButton.setOpaque(true);
        copyAllButton.setBorderPainted(false);
        copyAllButton.setFont(new Font("Arial", Font.BOLD, 20)); 

        clearLinksButton = new JButton("Limpiar Enlaces Generados");
        clearLinksButton.setBackground(new Color(0xFFCCCC));
        clearLinksButton.setForeground(Color.BLACK);
        clearLinksButton.setOpaque(true);
        clearLinksButton.setBorderPainted(false);
        clearLinksButton.setFont(new Font("Arial", Font.BOLD, 20)); 

        clearPhoneButton = new JButton("Limpiar Números");
        clearPhoneButton.setBackground(new Color(0xFFCCCC)); 
        clearPhoneButton.setForeground(Color.BLACK);
        clearPhoneButton.setOpaque(true);
        clearPhoneButton.setBorderPainted(false);
        clearPhoneButton.setFont(new Font("Arial", Font.BOLD, 20)); 

        clearMessageButton = new JButton("Limpiar Mensaje");
        clearMessageButton.setBackground(new Color(0xFFCCCC)); 
        clearMessageButton.setForeground(Color.BLACK);
        clearMessageButton.setOpaque(true);
        clearMessageButton.setBorderPainted(false);
        clearMessageButton.setFont(new Font("Arial", Font.BOLD, 20)); 

        sendMessagesButton = new JButton("Enviar Mensajes"); 
        sendMessagesButton.setBackground(new Color(0x28A745)); 
        sendMessagesButton.setForeground(Color.WHITE);
        sendMessagesButton.setOpaque(true);
        sendMessagesButton.setBorderPainted(false);
        sendMessagesButton.setFont(new Font("Arial", Font.BOLD, 20)); 

        linksPanel = new JPanel();
        linksPanel.setLayout(new BoxLayout(linksPanel, BoxLayout.Y_AXIS));
        linksPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void configurarPaneles() {
        JPanel phonePanel = crearPanel("Números de Teléfono", new JLabel("Ingresa números de teléfono (separados por comas):"), phoneNumbersField, clearPhoneButton);
        JPanel messagePanel = crearPanel("Mensaje", new JLabel("Ingresa tu mensaje:"), new JScrollPane(messageArea), clearMessageButton);

        JPanel linksTextPanel = new JPanel(new BorderLayout());
        linksTextPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        linksTextPanel.add(new JLabel("Todos los enlaces generados:"), BorderLayout.NORTH);
        linksTextPanel.add(new JScrollPane(linksTextArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(copyAllButton, BorderLayout.NORTH);
        buttonPanel.add(clearLinksButton, BorderLayout.SOUTH);

        linksTextPanel.add(buttonPanel, BorderLayout.SOUTH);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(new JScrollPane(linksPanel), BorderLayout.CENTER);
        rightPanel.add(linksTextPanel, BorderLayout.SOUTH);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(phonePanel, BorderLayout.NORTH);
        mainPanel.add(messagePanel, BorderLayout.CENTER);
        mainPanel.add(sendButton, BorderLayout.SOUTH);

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mainPanel, rightPanel);
        add(splitPane, BorderLayout.CENTER);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(clearLinksButton);
        topPanel.add(sendMessagesButton); 
        add(topPanel, BorderLayout.NORTH);
    }

    private JPanel crearPanel(String title, JComponent label, JComponent component, JButton clearButton) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0x007BFF)), 
                title,
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 20), 
                new Color(0x007BFF) 
        ));
        JPanel innerPanel = new JPanel(new BorderLayout());
        innerPanel.add(label, BorderLayout.NORTH);
        innerPanel.add(component, BorderLayout.CENTER);
        innerPanel.add(clearButton, BorderLayout.SOUTH);
        panel.add(innerPanel, BorderLayout.CENTER);
        return panel;
    }

    private void configurarBotones() {
        sendButton.addActionListener(e -> generarEnlaces());
        copyAllButton.addActionListener(e -> copiarAlPortapapeles(linksTextArea.getText()));
        clearLinksButton.addActionListener(e -> limpiarTodosEnlaces());
        sendMessagesButton.addActionListener(e -> abrirEnlacesEnNavegador()); 

        clearPhoneButton.addActionListener(e -> phoneNumbersField.setText(""));
        clearMessageButton.addActionListener(e -> messageArea.setText(""));
    }

    private void ajustarDivisor() {
        SwingUtilities.invokeLater(() -> {
            int width = getWidth();
            splitPane.setDividerLocation(width / 2);
        });
    }

    private void generarEnlaces() {
        String phoneNumbers = phoneNumbersField.getText().trim();
        String message = messageArea.getText().trim();

        if (phoneNumbers.isEmpty() || message.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa al menos un número y el mensaje.", "Error: Campos vacíos", JOptionPane.ERROR_MESSAGE);
            return;
        }

        linksPanel.removeAll();
        linksTextArea.setText(""); 

        String[] numbersArray = phoneNumbers.split("\\s*,\\s*");

        for (String number : numbersArray) {
            try {
                String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8.toString());
                String link = "https://web.whatsapp.com/send?phone=" + number + "&text=" + encodedMessage;
                

                
                JLabel linkLabel = new JLabel("<html><a href='" + link + "'>" + link + "</a></html>");
                linkLabel.setForeground(Color.BLUE);
                linkLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

                
                JButton copyButton = new JButton("Copiar");
                copyButton.addActionListener(e -> copiarAlPortapapeles(link));

                JPanel linkPanel = new JPanel(new BorderLayout());
                linkPanel.add(linkLabel, BorderLayout.CENTER);
                linkPanel.add(copyButton, BorderLayout.EAST);
                linksPanel.add(linkPanel);
                
                linksTextArea.append(link + "\n");

            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
        }
        linksPanel.revalidate();
        linksPanel.repaint();
    }

    private void copiarAlPortapapeles(String texto) {
        StringSelection stringSelection = new StringSelection(texto);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    private void limpiarTodosEnlaces() {
        linksTextArea.setText("");
        linksPanel.removeAll();
        linksPanel.revalidate();
        linksPanel.repaint();
    }

    private void aplicarFiltroNumeros(JTextField textField) {
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_COMMA && c != KeyEvent.VK_SPACE) {
                    e.consume();
                }
            }
        });
    }

    private void abrirEnlacesEnNavegador() {
        String[] enlaces = linksTextArea.getText().split("\\s*\\n\\s*");
        int mensajesEnviados = 0;
        try {
            Robot robot = new Robot();
            
            Runtime.getRuntime().exec("cmd /c start chrome");
            Thread.sleep(1000); 

            for (String enlace : enlaces) {
                
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_L);
                robot.keyRelease(KeyEvent.VK_L);
                robot.keyRelease(KeyEvent.VK_CONTROL);
                Thread.sleep(6000);

                StringSelection stringSelection = new StringSelection(enlace);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_V);
                robot.keyRelease(KeyEvent.VK_V);
                robot.keyRelease(KeyEvent.VK_CONTROL);
                Thread.sleep(4000);

                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
                Thread.sleep(4000);

                Thread.sleep(6000);

                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
                Thread.sleep(6000);

                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_L);
                robot.keyRelease(KeyEvent.VK_L);
                robot.keyRelease(KeyEvent.VK_CONTROL);
                Thread.sleep(5000);

                Thread.sleep(5000);

                mensajesEnviados++;

                if (mensajesEnviados % 25 == 0) {
                    System.out.println("Esperando 3600 segundos...");
                    Thread.sleep(7000);
                }
            }
        } catch (AWTException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Sender().setVisible(true));
    }
}
