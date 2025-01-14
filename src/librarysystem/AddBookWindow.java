package librarysystem;

import business.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AddBookWindow extends JFrame{
    public JPanel getMainPanel() {
        return mainPanel;
    }

    private JPanel topPanel;
    private JPanel mainPanel;
    private JPanel outerMiddle;

    private JTextField bookISBN;
    private JTextField titleField;
    private JTextField maxCheckoutLength;
    private JTextField authLastNameField;
    private JTextField authFirstNameField;
    private JTextField authPhoneNumberField;

    private final ControllerInterface ci = new SystemController();

    public void clearData() {
        authPhoneNumberField.setText("");
        authFirstNameField.setText("");
        authLastNameField.setText("");
        maxCheckoutLength.setText("");
        titleField.setText("");
        bookISBN.setText("");
    }

    public AddBookWindow() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        defineTopPanel();
        defineOuterMiddle();
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(outerMiddle, BorderLayout.CENTER);
    }

    public void defineTopPanel() {
        topPanel = new JPanel();
        JLabel AddBookLabel = new JLabel("Add Book");
        Util.adjustLabelFont(AddBookLabel, Util.DARK_BLUE, true);
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(AddBookLabel);
    }

    public void defineOuterMiddle() {
        outerMiddle = new JPanel();
        outerMiddle.setLayout(new BorderLayout());

        //set up left and right panels
        JPanel middlePanel = new JPanel();
        FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 25, 25);
        middlePanel.setLayout(fl);
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        JLabel authLastNameLabel = new JLabel("Author's Last Name");
        JLabel authFirstNameLabel = new JLabel("Author's First Name");
        JLabel authPhoneNumberLabel = new JLabel("Author's Phone Number");
        JLabel titleLabel = new JLabel("Book Title");
        JLabel bookISBNLabel = new JLabel("Book ISBN");
        JLabel maxCheckoutLengthLabel = new JLabel("Max Checkout Length");

        bookISBN = new JTextField(10);
        titleField = new JTextField(10);
        maxCheckoutLength = new JTextField(10);
        authLastNameField = new JTextField(10);
        authFirstNameField = new JTextField(10);
        authPhoneNumberField = new JTextField(10);

        leftPanel.add(authFirstNameLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        leftPanel.add(authLastNameLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        leftPanel.add(authPhoneNumberLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        leftPanel.add(titleLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        leftPanel.add(bookISBNLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        leftPanel.add(maxCheckoutLengthLabel);

        rightPanel.add(authFirstNameField);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        rightPanel.add(authLastNameField);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        rightPanel.add(authPhoneNumberField);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        rightPanel.add(titleField);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        rightPanel.add(bookISBN);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        rightPanel.add(maxCheckoutLength);

        middlePanel.add(leftPanel);
        middlePanel.add(rightPanel);
        outerMiddle.add(middlePanel, BorderLayout.NORTH);

        //add button at bottom
        JButton save = new JButton("Save");
        attachAddBookButtonListener(save);
        JPanel addBookButtonPanel = new JPanel();
        addBookButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        addBookButtonPanel.add(save);
        outerMiddle.add(addBookButtonPanel, BorderLayout.CENTER);
    }

    private void attachAddBookButtonListener(JButton btn) {
        btn.addActionListener(evt -> {
            String isbn = bookISBN.getText().trim();
            String title = titleField.getText().trim();
            String lName = authLastNameField.getText().trim();
            String fName = authFirstNameField.getText().trim();
            String phoneNumber = authPhoneNumberField.getText().trim();
            String maxCheckoutLen = maxCheckoutLength.getText().trim();
            if (lName.equals("") ||fName.equals("") ||
                    phoneNumber.equals("") || isbn.equals("") ||
                            maxCheckoutLen.equals("")
            ) {
                JOptionPane.showMessageDialog(mainPanel, "All are required fields!");
            } else if (!Util.isNumeric(maxCheckoutLen)) {
                JOptionPane.showMessageDialog(mainPanel, "Checkout length not an number!");
            } else {
                Address address = new Address("", "", "", "");
                Author author = new Author(fName, lName, phoneNumber, address, "");
                try {
                    List<Author> authors = new ArrayList<>();
                    authors.add(author);
                    ci.addBook(isbn, title, Integer.parseInt(maxCheckoutLen), authors);
                    bookISBN.setText("");
                    titleField.setText("");
                    authLastNameField.setText("");
                    authFirstNameField.setText("");
                    authPhoneNumberField.setText("");
                    maxCheckoutLength.setText("");
                    JOptionPane.showMessageDialog(mainPanel, "Saved...");
                } catch (LibrarySystemException e) {
                    JOptionPane.showMessageDialog(mainPanel, e.getMessage());
                }
            }
        });
    }

    public void updateData() {
    }
}
