import java.awt.event.KeyEvent;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.KeyAdapter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class AcruxWhatsAppSender extends JFrame {

    private JTextField campoNumeros;
    private JTextArea areaMensaje;
    private JTextArea areaEnlaces;
    private JButton botonGenerar;
    private JButton botonCopiarTodo;
    private JButton botonLimpiarNumeros;
    private JButton botonLimpiarMensaje;
    private JButton botonLimpiarEnlaces;
    private JButton botonEnviarMensajes;
    private JPanel panelEnlaces;
    private JSplitPane divisor;

    public AcruxWhatsAppSender() {
        configurarVentana();
        inicializarComponentes();
        configurarPaneles();
        configurarBotones();
        ajustarDivisor();
    }

    private void configurarVentana() {
        setTitle("AcruxWhatsAppSender by Leonardohr26");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
    }

    private void inicializarComponentes() {
        campoNumeros = new JTextField();
        campoNumeros.setToolTipText("Ingresa números de teléfono separados por comas");
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
        botonGenerar.setBackground(new Color(0x52F7D1));
        botonGenerar.setForeground(Color.BLACK);
        botonGenerar.setOpaque(true);
        botonGenerar.setBorderPainted(false);
        botonGenerar.setFont(new Font("Arial", Font.BOLD, 20));

        botonCopiarTodo = new JButton("Copiar Todos");
        botonCopiarTodo.setBackground(Color.BLUE);
        botonCopiarTodo.setForeground(Color.WHITE);
        botonCopiarTodo.setOpaque(true);
        botonCopiarTodo.setBorderPainted(false);
        botonCopiarTodo.setFont(new Font("Arial", Font.BOLD, 20));

        botonLimpiarEnlaces = new JButton("Limpiar Enlaces Generados");
        botonLimpiarEnlaces.setBackground(new Color(0xFFCCCC));
        botonLimpiarEnlaces.setForeground(Color.BLACK);
        botonLimpiarEnlaces.setOpaque(true);
        botonLimpiarEnlaces.setBorderPainted(false);
        botonLimpiarEnlaces.setFont(new Font("Arial", Font.BOLD, 20));

        botonLimpiarNumeros = new JButton("Limpiar Números");
        botonLimpiarNumeros.setBackground(new Color(0xFFCCCC));
        botonLimpiarNumeros.setForeground(Color.BLACK);
        botonLimpiarNumeros.setOpaque(true);
        botonLimpiarNumeros.setBorderPainted(false);
        botonLimpiarNumeros.setFont(new Font("Arial", Font.BOLD, 20));

        botonLimpiarMensaje = new JButton("Limpiar Mensaje");
        botonLimpiarMensaje.setBackground(new Color(0xFFCCCC));
        botonLimpiarMensaje.setForeground(Color.BLACK);
        botonLimpiarMensaje.setOpaque(true);
        botonLimpiarMensaje.setBorderPainted(false);
        botonLimpiarMensaje.setFont(new Font("Arial", Font.BOLD, 20));

        botonEnviarMensajes = new JButton("Enviar Mensajes");
        botonEnviarMensajes.setBackground(new Color(0x28A745));
        botonEnviarMensajes.setForeground(Color.WHITE);
        botonEnviarMensajes.setOpaque(true);
        botonEnviarMensajes.setBorderPainted(false);
        botonEnviarMensajes.setFont(new Font("Arial", Font.BOLD, 20));

        panelEnlaces = new JPanel();
        panelEnlaces.setLayout(new BoxLayout(panelEnlaces, BoxLayout.Y_AXIS));
        panelEnlaces.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void configurarPaneles() {
        JPanel panelNumeros = crearPanel("Números de Teléfono", new JLabel("Ingresa números de teléfono (separados por comas):"), campoNumeros, botonLimpiarNumeros);
        JPanel panelMensaje = crearPanel("Mensaje", new JLabel("Ingresa tu mensaje:"), new JScrollPane(areaMensaje), botonLimpiarMensaje);

        JPanel panelTextoEnlaces = new JPanel(new BorderLayout());
        panelTextoEnlaces.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelTextoEnlaces.add(new JLabel("Todos los enlaces generados:"), BorderLayout.NORTH);
        panelTextoEnlaces.add(new JScrollPane(areaEnlaces), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new BorderLayout());
        panelBotones.add(botonCopiarTodo, BorderLayout.NORTH);
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

        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelSuperior.add(botonLimpiarEnlaces);
        panelSuperior.add(botonEnviarMensajes);
        add(panelSuperior, BorderLayout.NORTH);
    }

    private JPanel crearPanel(String titulo, JComponent etiqueta, JComponent componente, JButton botonLimpiar) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0x007BFF)),
                titulo,
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 20),
                new Color(0x007BFF)
        ));
        JPanel panelInterno = new JPanel(new BorderLayout());
        panelInterno.add(etiqueta, BorderLayout.NORTH);
        panelInterno.add(componente, BorderLayout.CENTER);
        panelInterno.add(botonLimpiar, BorderLayout.SOUTH);
        panel.add(panelInterno, BorderLayout.CENTER);
        return panel;
    }

    private void configurarBotones() {
        botonGenerar.addActionListener(e -> generarEnlaces());
        botonCopiarTodo.addActionListener(e -> copiarAlPortapapeles(areaEnlaces.getText()));
        botonLimpiarEnlaces.addActionListener(e -> limpiarTodosEnlaces());
        botonEnviarMensajes.addActionListener(e -> abrirEnlacesEnNavegador());
        botonLimpiarNumeros.addActionListener(e -> campoNumeros.setText(""));
        botonLimpiarMensaje.addActionListener(e -> areaMensaje.setText(""));
    }

    private void ajustarDivisor() {
        SwingUtilities.invokeLater(() -> {
            int width = getWidth();
            divisor.setDividerLocation(width / 2);
        });
    }

    private void generarEnlaces() {
        String numeros = campoNumeros.getText().trim();
        String mensaje = areaMensaje.getText().trim();

        if (numeros.isEmpty() || mensaje.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa al menos un número y el mensaje.", "Error: Campos vacíos", JOptionPane.ERROR_MESSAGE);
            return;
        }

        panelEnlaces.removeAll();
        areaEnlaces.setText("");

        String[] arregloNumeros = numeros.split("\\s*,\\s*");

        for (String numero : arregloNumeros) {
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

    private void limpiarTodosEnlaces() {
        areaEnlaces.setText("");
        panelEnlaces.removeAll();
        panelEnlaces.revalidate();
        panelEnlaces.repaint();
    }

    private void abrirEnlacesEnNavegador() {
        String texto = areaEnlaces.getText().trim();
        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay enlaces generados para abrir.", "Error: No hay enlaces", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String[] enlaces = texto.split("\\s+");

        for (String enlace : enlaces) {
            try {
                Desktop.getDesktop().browse(new URI(enlace));
                Thread.sleep(1000);
            } catch (IOException | URISyntaxException | InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void aplicarFiltroNumeros(JTextField campo) {
        campo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != ',' && c != ' ') {
                    e.consume();
                }
            }
        });
    }

    private void copiarAlPortapapeles(String texto) {
        Clipboard portapapeles = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection seleccion = new StringSelection(texto);
        portapapeles.setContents(seleccion, null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AcruxWhatsAppSender app = new AcruxWhatsAppSender();
            app.setVisible(true);
        });
    }
}
