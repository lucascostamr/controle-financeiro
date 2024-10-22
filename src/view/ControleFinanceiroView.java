package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import controller.ControleFinanceiroController;
import model.dto.RecordDTO;
import model.dto.TotalDTO;

@SuppressWarnings("serial")
public class ControleFinanceiroView extends JFrame {

    private static record FormComponent(JLabel name, JTextField input) { }

    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    private static final String WINDOW_TITLE = "Sistema de Controle Financeiro";
    private static final String[] TABLE_COLUMNS = { "Nome", "Classificação", "Valor", "Data Entrada", "Data Cadastro", "Ações" };;

    private static String MONETARY_ENTRY_REGEX_1 = "(((\\d{1,3})\\.?)+(,\\d{2})?)";
    private static String MONETARY_ENTRY_REGEX_2 = "(((\\d{1,3}),?)+(\\.\\d{2})?)";
    private static String DATE_REGEX = "\\d{2}\\/\\d{2}\\/\\d{4}";

    private ControleFinanceiroController controller;

    private JLabel titleLabel;
    private JTable recordTable;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;

    private JPanel formPanel;
    private JPanel westPanel;
    private JPanel tablePanel;
    private FormComponent formName;
    private FormComponent formClassification;
    private FormComponent formValue;
    private FormComponent formEntryDate;
    private JButton buttonIncome;
    private JButton buttonExpense;
    private JButton buttonRegister;

    private JLabel messageLabel;
    private JLabel totalValues;

    private boolean isIncome = false;

    public ControleFinanceiroView() {
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setTitle(WINDOW_TITLE);

        initComponents();
        createListeners();

        pack();
        setLocationRelativeTo(null);
    }

    public void setController(ControleFinanceiroController controller) {
        this.controller = controller;
        updateTable();
    }

    private void initComponents() {
        setPanels();

        titleLabel = new JLabel(WINDOW_TITLE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBackground(Color.WHITE);
        titleLabel.setOpaque(true);
        add(titleLabel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(TABLE_COLUMNS, 0);
        recordTable = new JTable(tableModel);
        scrollPane = new JScrollPane(recordTable);

        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, WINDOW_HEIGHT / 2));

        tablePanel.add(scrollPane);

        int gapSize = 10;
        tablePanel.add(Box.createRigidArea(new Dimension(0, gapSize)));

        totalValues = new JLabel("Recebido: R$ 00.00 Gastos: R$ 00.00 Diferença: R$00.00");
        totalValues.setAlignmentX(Component.CENTER_ALIGNMENT);
        tablePanel.add(totalValues);

        addFormComponents();

        messageLabel = new JLabel();
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        gapSize = 5;
        westPanel.add(Box.createRigidArea(new Dimension(0, gapSize)));
        westPanel.add(messageLabel);
    }

    private void addComponents(JComponent target, JComponent... components) {
        for (var comp : components) {
            target.add(comp);
        }
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        List<RecordDTO> records = controller.getAllRecords();

        for (var record : records) {
            tableModel.addRow(new Object[] { record.name(), record.classification(), record.value(),
                    record.entryDate(), record.registrationDate(), "DELETAR" });
        }

        int rowHeight = recordTable.getRowHeight();
        int headerHeight = recordTable.getTableHeader().getHeight();
        int maxVisibleRows = 10;

        int height = headerHeight + Math.min(records.size(), maxVisibleRows) * rowHeight;

        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
        scrollPane.revalidate();
        tablePanel.revalidate();

        TotalDTO total = controller.getTotal();

        String text = String.format("Recebido: R$ %.2f Gastos: R$ %.2f Diferença: R$ %.2f",
                total.totalIncome(), total.totalExpense(), total.totalDifference());

        totalValues.setText(text);

    }

    private void setPanels() {
        westPanel = new JPanel();
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
        westPanel.setPreferredSize(new Dimension(250, WINDOW_HEIGHT));
        add(westPanel, BorderLayout.WEST);

        formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(6, 2, 5, 5));
        formPanel.setMaximumSize(new Dimension(250, 200));
        formPanel.setPreferredSize(new Dimension(250, 100));
        westPanel.add(formPanel);

        tablePanel = new JPanel();
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
        add(tablePanel, BorderLayout.CENTER);
    }

    private void addFormComponents() {
        formName = new FormComponent(new JLabel("Nome:"), new JTextField());
        formValue = new FormComponent(new JLabel("Valor:"), new JTextField());
        formClassification = new FormComponent(new JLabel("Classificação:"), new JTextField());

        try {
            var maskFormatter = new MaskFormatter("##/##/####");
            maskFormatter.setPlaceholderCharacter('_');
            formEntryDate = new FormComponent(new JLabel("Data Entrada:"), new JFormattedTextField(maskFormatter));
        } catch (ParseException e) {
            formEntryDate = new FormComponent(new JLabel("Data Entrada:"), new JTextField());
        }

        buttonIncome = new JButton("Ganho (+)");
        buttonExpense = new JButton("Gasto (-)");
        buttonRegister = new JButton("Cadastrar");

        addComponents(formPanel, formName.name(), formName.input(), formClassification.name(),
                formClassification.input(), formValue.name(), formValue.input(), formEntryDate.name(),
                formEntryDate.input(), buttonIncome, buttonExpense, buttonRegister);

        buttonIncomePressed();
    }

    private void createListeners() {
        buttonIncome.addActionListener(e -> buttonIncomePressed());
        buttonExpense.addActionListener(e -> buttonExpensePressed());
        buttonRegister.addActionListener(e -> buttonRegisterPressed());
        recordTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = recordTable.rowAtPoint(e.getPoint());
                int column = recordTable.columnAtPoint(e.getPoint());

                if (row >= 0 && column == 5) {
                	deletePressed(row);
                }
            }
        });
    }

    private void deletePressed(int row) {
        int response = JOptionPane.showConfirmDialog(this, "Deseja realmente deletar este registro?", "Confirmação", JOptionPane.YES_NO_OPTION);

        if (response == JOptionPane.YES_OPTION) {
            RecordDTO recordToDelete = controller.getRecordAt(row);

            if(recordToDelete != null) {
            	controller.deleteRecord(recordToDelete);
            } else {
                JOptionPane.showMessageDialog(this, "Registro Null");
            }

            updateTable();

            JOptionPane.showMessageDialog(this, "Registro deletado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void buttonIncomePressed() {
        isIncome = true;

        buttonIncome.setText("Ganho (+) ✔");
        buttonExpense.setText("Gasto (-)");

        buttonIncome.setEnabled(false);
        buttonExpense.setEnabled(true);
    }

    private void buttonExpensePressed() {
        isIncome = false;

        buttonIncome.setText("Ganho (+)");
        buttonExpense.setText("Gasto (-) ✔");

        buttonIncome.setEnabled(true);
        buttonExpense.setEnabled(false);
    }

    private void buttonRegisterPressed() {
        messageLabel.setText("");

        messageLabel.setForeground(Color.RED);

        String name = formName.input().getText();
        if (name.isBlank()) {
            messageLabel.setText("O nome é obrigatório!");
            return;
        }

        String classification = formClassification.input().getText();
        if (classification.isBlank()) {
            messageLabel.setText("A classificação é obrigatória!");
            return;
        }

        String valueStr = formValue.input().getText();
        float value;

        if (valueStr.matches(MONETARY_ENTRY_REGEX_1)) {
            valueStr = valueStr.replaceAll("\\.", "").replace(',', '.');
            value = Float.parseFloat(valueStr);
        } else if (valueStr.matches(MONETARY_ENTRY_REGEX_2)) {
            valueStr = valueStr.replaceAll(",", "");
            value = Float.parseFloat(valueStr);
        } else {
            messageLabel.setText("Formato de valor inválido!");
            return;
        }

        if (!isIncome) {
            value *= -1;
        }

        String entryDateStr = formEntryDate.input().getText();
            messageLabel.setText("Data de entrada inválida!");
            if (!entryDateStr.matches(DATE_REGEX)) {
            return;
        }

        var dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate entrydate;
        try {
        	entrydate = LocalDate.parse(entryDateStr, dateFormat);
        } catch (DateTimeParseException e) {
            messageLabel.setText("Formato de data inválido!");
            return;
        }

        var record = new RecordDTO(0L, name, classification, value, entrydate, LocalDate.now());
        controller.insertRecord(record);

        formName.input().setText("");
        formClassification.input().setText("");
        formValue.input().setText("");
        formEntryDate.input().setText("");

        messageLabel.setForeground(Color.GREEN);

        messageLabel.setText("Registro criado com sucesso!");

        updateTable();
    }
}