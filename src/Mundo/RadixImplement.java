package Mundo;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RadixImplement extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtValue;
    private JPanel unsortedListPanel;
    private JPanel sortedListPanel;
    private RxNodos head = null;
    private java.util.List<RxNodos> steps;
    private int currentStep;
    private Map<RxNodos, Color> nodeColors;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    RadixImplement frame = new RadixImplement();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public RadixImplement() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Reposition the buttons
        JButton btnback = new JButton("Back");
        btnback.setBounds(800, 500, 150, 50);
        contentPane.add(btnback);

        JButton btnnext = new JButton("Next");
        btnnext.setBounds(1000, 500, 150, 50);
        contentPane.add(btnnext);

        txtValue = new JTextField();
        txtValue.setBounds(800, 100, 150, 50);
        contentPane.add(txtValue);
        txtValue.setColumns(10);

        JButton btnNewValue = new JButton("New Value");
        btnNewValue.setBounds(1000, 100, 150, 50);
        contentPane.add(btnNewValue);

        JButton btnFillArray = new JButton("Fill Array");
        btnFillArray.setBounds(1000, 200, 150, 50);
        contentPane.add(btnFillArray);

        JLabel lblUnsortedList = new JLabel("Unsorted List");
        lblUnsortedList.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblUnsortedList.setBounds(50, 20, 150, 20);
        contentPane.add(lblUnsortedList);

        unsortedListPanel = new JPanel();
        unsortedListPanel.setBounds(50, 50, 300, 700);  
        unsortedListPanel.setLayout(new GridLayout(0, 1, 10, 10));
        contentPane.add(unsortedListPanel);

        JLabel lblSortedList = new JLabel("Sorted List");
        lblSortedList.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblSortedList.setBounds(300, 20, 150, 20);
        contentPane.add(lblSortedList);

        sortedListPanel = new JPanel();
        sortedListPanel.setBounds(400, 50, 300, 700);
        sortedListPanel.setLayout(new GridLayout(0, 1, 10, 10));
        contentPane.add(sortedListPanel);

        btnNewValue.addActionListener(e -> addValue());
        btnnext.addActionListener(e -> nextStep());
        btnback.addActionListener(e -> previousStep());
        btnFillArray.addActionListener(e -> fillArrayWithRandomValues());

        steps = new java.util.ArrayList<>();
        currentStep = -1;
        nodeColors = new HashMap<>();

        // Make the window full screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void addValue() {
        String text = txtValue.getText();
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingresar valor", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int value = Integer.parseInt(text);
            RxNodos newNode = new RxNodos(value);
            if (head == null) {
                head = newNode;
            } else {
                RxNodos current = head;
                while (current.next != null) {
                    current = current.getNext();
                }
                current.setNext(newNode);
            }
            txtValue.setText("");
            nodeColors.put(newNode, Color.GREEN);  // Initial color for the new node
            updateUnsortedList();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingresar un número válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void fillArrayWithRandomValues() {
        String input = JOptionPane.showInputDialog(this, "¿Cuántos valores aleatorios deseas agregar?", "Valores Aleatorios", JOptionPane.QUESTION_MESSAGE);
        int numValues = Integer.parseInt(input);
        if (numValues>20){
            JOptionPane.showInputDialog(this, "MAXIMO DE VALORES PERMITIDOS 20", "ERROR", JOptionPane.QUESTION_MESSAGE);
        }
        if (input != null && !input.isEmpty()) {
            try {
                
                head = new RxNodos(1); // Inicia con el primer valor aleatorio
                head = head.fillOut(head, numValues); // Llenar la lista con la cantidad de valores aleatorios especificada
                
                // Asignar colores a cada nodo generado
                RxNodos current = head;
                while (current != null) {
                    nodeColors.put(current, getRandomColor());
                    current = current.getNext();
                }
        
                updateUnsortedList(); // Actualiza la interfaz para reflejar los valores aleatorios generados
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Por favor ingresa un número válido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    

    private void updateUnsortedList() {
        unsortedListPanel.removeAll();
        RxNodos current = head;
        while (current != null) {
            JLabel label = createNodeLabel(current.value, nodeColors.getOrDefault(current, Color.GREEN));
            unsortedListPanel.add(label);
            current = current.getNext();
        }
        unsortedListPanel.revalidate();
        unsortedListPanel.repaint();
    }

    private void nextStep() {
        if (steps.isEmpty()) {
            computeSteps();
        }
        if (currentStep < steps.size() - 1) {
            currentStep++;
            updateNodeColors();
            head = steps.get(currentStep);
            updateSortedList();
        }
    }

    private void previousStep() {
        if (currentStep > 0) {
            currentStep--;
            updateNodeColors();
            head = steps.get(currentStep);
            updateSortedList();
        }
    }

    private void computeSteps() {
        steps.clear();
        currentStep = -1;
        RxNodos currentHead = cloneList(head);
        steps.add(cloneList(currentHead));

        for (int x = 0; x < Integer.SIZE; x++) {
            RxNodos zeroBucketHead = new RxNodos(0), zeroBucket = zeroBucketHead;
            RxNodos oneBucketHead = new RxNodos(0), oneBucket = oneBucketHead;

            RxNodos current = currentHead;
            while (current != null) {
                RxNodos next = current.getNext();
                if ((current.value & (1 << x)) == 0) {
                    zeroBucket.next = current;
                    zeroBucket = zeroBucket.next;
                } else {
                    oneBucket.next = current;
                    oneBucket = oneBucket.next;
                }
                current = next;
            }

            zeroBucket.next = oneBucketHead.next;
            oneBucket.next = null;

            currentHead = zeroBucketHead.next;
            steps.add(cloneList(currentHead));
        }
    }

    private RxNodos cloneList(RxNodos head) {
        if (head == null) return null;

        RxNodos newHead = new RxNodos(head.value);
        nodeColors.put(newHead, nodeColors.getOrDefault(head, Color.GREEN));  // Maintain the color
        RxNodos current = head.getNext();
        RxNodos newCurrent = newHead;

        while (current != null) {
            newCurrent.next = new RxNodos(current.value);
            nodeColors.put(newCurrent.next, nodeColors.getOrDefault(current, Color.GREEN));  // Maintain the color
            newCurrent = newCurrent.next;
            current = current.getNext();
        }

        return newHead;
    }

    private void updateNodeColors() {
        if (currentStep < 0 || currentStep >= steps.size()) return;

        RxNodos current = steps.get(currentStep);
        while (current != null) {
            if (!nodeColors.containsKey(current)) {
                nodeColors.put(current, getRandomColor());
            }
            current = current.getNext();
        }
    }

    private void updateSortedList() {
        sortedListPanel.removeAll();
        RxNodos current = head;
        while (current != null) {
            JLabel label = createNodeLabel(current.value, nodeColors.getOrDefault(current, Color.GREEN));
            sortedListPanel.add(label);
            current = current.getNext();
        }
        sortedListPanel.revalidate();
        sortedListPanel.repaint();
    }

    private JLabel createNodeLabel(int value, Color color) {
        JLabel label = new JLabel(String.valueOf(value));
        label.setOpaque(true);
        label.setBorder(new LineBorder(color, 3));  // Set border color and thickness
        label.setFont(new Font("Tahoma", Font.PLAIN, 18));
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }

    private Color getRandomColor() {
        return new Color((int)(Math.random() * 0x1000000));
    }
}
