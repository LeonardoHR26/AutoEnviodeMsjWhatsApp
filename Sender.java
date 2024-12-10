import java.awt.event.KeyEvent;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.KeyAdapter;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Sender extends JFrame {

    private JTextField campoNumeros;
    private JTextArea areaMensaje;
    private JTextArea areaEnlaces;
    private JButton botonGenerar;
    private JButton botonCopiarTodos;
    private JButton botonLimpiarNumeros;
    private JButton botonLimpiarMensaje;
    private JButton botonLimpiarEnlaces;
    private JButton botonEnviarMensajes;
    private JPanel panelEnlaces;
    private JSplitPane divisor;

    public Sender() {
        configurarVentana();
        inicializarComponentes();
        configurarPaneles();
        configurarBotones();
        ajustarDivisor();
    }

    private void configurarVentana() {
        setTitle("Sender");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
    }

    private void inicializarComponentes() {
        campoNumeros = new JTextField();
        aplicarFiltroNumeros(campoNumeros);
        campoNumeros.setFont(new Font("Arial", Font.PLAIN, 20));

        areaMensaje = new JTextArea(10, 30);
        areaMensaje.setLineWrap(true);
        areaMensaje.setWrapStyleWord(true);
        areaMensaje.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        areaMensaje.setFont(new Font("Arial", Font.PLAIN, 20));

        areaEnlaces = new JTextArea(10, 40);
        areaEnlaces.setEditable(false);
        areaEnlaces.setLineWrap(true);
        areaEnlaces.setWrapStyleWord(true);
        areaEnlaces.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        areaEnlaces.setFont(new Font("Arial", Font.PLAIN, 16));

        botonGenerar = new JButton("Generar Enlaces");
        botonCopiarTodos = new JButton("Copiar Todos");
        botonLimpiarNumeros = new JButton("Limpiar Números");
        botonLimpiarMensaje = new JButton("Limpiar Mensaje");
        botonLimpiarEnlaces = new JButton("Limpiar Enlaces");
        botonEnviarMensajes = new JButton("Enviar Mensajes");

        panelEnlaces = new JPanel();
        panelEnlaces.setLayout(new BoxLayout(panelEnlaces, BoxLayout.Y_AXIS));
        panelEnlaces.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void configurarPaneles() {
        JPanel panelNumeros = crearPanel("Números de Teléfono", campoNumeros, botonLimpiarNumeros);
        JPanel panelMensaje = crearPanel("Mensaje", new JScrollPane(areaMensaje), botonLimpiarMensaje);

        JPanel panelTextoEnlaces = new JPanel(new BorderLayout());
        panelTextoEnlaces.add(new JScrollPane(areaEnlaces), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new BorderLayout());
        panelBotones.add(botonCopiarTodos, BorderLayout.NORTH);
        panelBotones.add(botonLimpiarEnlaces, BorderLayout.SOUTH);

        panelTextoEnlaces.add(panelBotones, BorderLayout.SOUTH);

        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.add(new JScrollPane(panelEnlaces), BorderLayout.CENTER);
        panelDerecho.add(panelTextoEnlaces, BorderLayout.SOUTH);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.add(panelNumeros, BorderLayout.NORTH);
        panelPrincipal.add(panelMensaje, BorderLayout.CENTER);
        panelPrincipal.add(botonGenerar, BorderLayout.SOUTH);

        divisor = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelPrincipal, panelDerecho);
        add(divisor, BorderLayout.CENTER);
    }

    private JPanel crearPanel(String titulo, JComponent componente, JButton botonLimpiar) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0x007BFF)),
                titulo,
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 20),
                new Color(0x007BFF)
        ));
        panel.add(componente, BorderLayout.CENTER);
        panel.add(botonLimpiar, BorderLayout.SOUTH);
        return panel;
    }

    private void configurarBotones() {
        botonGenerar.addActionListener(e -> generarEnlaces());
        botonCopiarTodos.addActionListener(e -> copiarAlPortapapeles(areaEnlaces.getText()));
        botonLimpiarEnlaces.addActionListener(e -> limpiarTodosEnlaces());
        botonEnviarMensajes.addActionListener(e -> abrirEnlacesEnNavegador());
        botonLimpiarNumeros.addActionListener(e -> campoNumeros.setText(""));
        botonLimpiarMensaje.addActionListener(e -> areaMensaje.setText(""));
    }

    private void ajustarDivisor() {
        SwingUtilities.invokeLater(() -> divisor.setDividerLocation(getWidth() / 2));
    }

    private void generarEnlaces() {
        String numeros = campoNumeros.getText().trim();
        String mensaje = areaMensaje.getText().trim();

        if (numeros.isEmpty() || mensaje.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa al menos un número y el mensaje.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        panelEnlaces.removeAll();
        areaEnlaces.setText("");
        String[] listaNumeros = numeros.split("\\s*,\\s*");

        for (String numero : listaNumeros) {
            try {
                String mensajeCodificado = URLEncoder.encode(mensaje, StandardCharsets.UTF_8.toString());
                String enlace = "https://web.whatsapp.com/send?phone=" + numero + "&text=" + mensajeCodificado;

                JLabel etiquetaEnlace = new JLabel("<html><a href='" + enlace + "'>" + enlace + "</a></html>");
                etiquetaEnlace.setForeground(Color.BLUE);
                etiquetaEnlace.setCursor(new Cursor(Cursor.HAND_CURSOR));

                JButton botonCopiar = new JButton("Copiar");
                botonCopiar.addActionListener(e -> copiarAlPortapapeles(enlace));

                JPanel panelEnlace = new JPanel(new BorderLayout());
                panelEnlace.add(etiquetaEnlace, BorderLayout.CENTER);
                panelEnlace.add(botonCopiar, BorderLayout.EAST);
                panelEnlaces.add(panelEnlace);

                areaEnlaces.append(enlace + "\n");
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
        }
        panelEnlaces.revalidate();
        panelEnlaces.repaint();
    }

    private void copiarAlPortapapeles(String texto) {
        StringSelection seleccion = new StringSelection(texto);
        Clipboard portapapeles = Toolkit.getDefaultToolkit().getSystemClipboard();
        portapapeles.setContents(seleccion, null);
    }

    private void limpiarTodosEnlaces() {
        areaEnlaces.setText("");
        panelEnlaces.removeAll();
        panelEnlaces.revalidate();
        panelEnlaces.repaint();
    }

    private void aplicarFiltroNumeros(JTextField campoTexto) {
        campoTexto.addKeyListener(new KeyAdapter() {
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
    try {
        Robot robot = new Robot();
        Runtime.getRuntime().exec("cmd /c start chrome");
        Thread.sleep(1000);

        for (String enlace : enlaces) {
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_L);
            robot.keyRelease(KeyEvent.VK_L);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            Thread.sleep(1000);

            StringSelection stringSelection = new StringSelection(enlace);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);

            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            Thread.sleep(1000);

            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            Thread.sleep(5000); // Esperar entre enlaces
        }
    } catch (AWTException | IOException | InterruptedException e) {
        e.printStackTrace();
    }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Sender().setVisible(true));
    }
}
