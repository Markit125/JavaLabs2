package Lab3;

import Lab3.InitialListFetch.InitialCandidatesListFetcher;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.List;

import static Lab3.InputHelpMethods.InputHelper.getNotEmptyString;

public class CandidatesGUI extends JFrame {
    private List<Candidate> candidates;
    private final JTextArea candidatesListArea;
    private final InitialCandidatesListFetcher initialCandidatesListFetcher;

    private final Map<String, Integer> popularityOptionToIndex = Map.of(
        "Supported by the President", 70,
        "Supported by the opposition party", 15,
        "Opposition candidate stepping down for #1", 10,
        "Other", 5
    );

    public CandidatesGUI(InitialCandidatesListFetcher initialCandidatesListFetcher) {
        this.initialCandidatesListFetcher = initialCandidatesListFetcher;

        setTitle("Governor Election Candidates");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        candidatesListArea = new JTextArea();
        candidatesListArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        candidatesListArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(candidatesListArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = getPanel();
        add(buttonPanel, BorderLayout.WEST);

        SwingUtilities.invokeLater(this::onWindowVisible);
    }

    private void onWindowVisible() {
        try {
            candidates = initialCandidatesListFetcher.getInitialList();
        } catch (Exception e) {
            candidates = new ArrayList<>();
            JOptionPane.showMessageDialog(
                    this,
                    "Failed to load initial candidates list\n",
                    "Load Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private JPanel getPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1));

        JButton addButton = new JButton("Add Candidate");
        addButton.addActionListener(e -> addCandidate());
        buttonPanel.add(addButton);

        JButton removeButton = new JButton("Remove Candidate");
        removeButton.addActionListener(e -> removeCandidate());
        buttonPanel.add(removeButton);

        JButton showButton = new JButton("Show Candidates List");
        showButton.addActionListener(e -> displayCandidatesList());
        buttonPanel.add(showButton);

        JButton highestRatedButton = new JButton("Show Highest-Rated Candidate");
        highestRatedButton.addActionListener(e -> displayHighestRatedCandidate());
        buttonPanel.add(highestRatedButton);

        JButton saveButton = new JButton("Save List to File");
        saveButton.addActionListener(e -> saveListToFile());
        buttonPanel.add(saveButton);
        return buttonPanel;
    }

    private void displayCandidatesList() {
        StringBuilder list = new StringBuilder();
        for (Candidate candidate : candidates) {
            list.append(candidate).append("\n\n");
        }
        candidatesListArea.setText(list.toString());
    }

    private int askPopularityIndex() {
        String popularityOption = (String) JOptionPane.showInputDialog(
                this,
                "Select popularity index source:",
                "Popularity Index",
                JOptionPane.QUESTION_MESSAGE,
                null,
                popularityOptionToIndex.keySet().toArray(),
                popularityOptionToIndex.keySet().toArray()[0]
        );

        return popularityOptionToIndex.get(popularityOption);
    }

    private Date askBirthDate() {
        JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(editor);

        int result = JOptionPane.showConfirmDialog(
                this,
                dateSpinner,
                "Select birth date",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {
            return (Date) dateSpinner.getValue();
        }
        return null;
    }

    private void addCandidate() {
        try {
            String firstName = getNotEmptyString(message -> JOptionPane.showInputDialog(this, message), "first name");
            String middleName = getNotEmptyString(message -> JOptionPane.showInputDialog(this, message), "middle name");
            String lastName = getNotEmptyString(message -> JOptionPane.showInputDialog(this, message), "last name");
            Date birthDate = askBirthDate();
            String placeOfBirth = getNotEmptyString(message -> JOptionPane.showInputDialog(this, message), "place of birth");

            Candidate candidate = new Candidate(firstName, middleName, lastName, birthDate, placeOfBirth, askPopularityIndex());
            candidates.add(candidate);
        } catch (NullPointerException e) {
            return;
        }

        JOptionPane.showMessageDialog(this, "Candidate added!");
    }

    private void removeCandidate() {
        String lastName = JOptionPane.showInputDialog(this, "Enter last name of candidate to remove:");
        if (lastName == null) {
            return;
        }

        for (Candidate candidate : candidates) {
            if (candidate.lastName.equals(lastName)) {
                candidates.remove(candidate);
                JOptionPane.showMessageDialog(this, "Candidate removed!");
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "No such candidate!");
    }

    private void displayHighestRatedCandidate() {
        Candidate highestRated = null;
        for (Candidate candidate : candidates) {
            if (highestRated == null || candidate.popularityIndex > highestRated.popularityIndex) {
                highestRated = candidate;
            }
        }
        if (highestRated != null) {
            JOptionPane.showMessageDialog(this, "Highest Rated\n: " + highestRated);
        } else {
            JOptionPane.showMessageDialog(this, "No candidates available.");
        }
    }

    private void saveListToFile() {
        JFileChooser fileChooser = new JFileChooser("C:\\Labs\\JavaLabs2\\Lab3\\src\\Lab3");
        fileChooser.setDialogTitle("Save Candidates List");

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return;
        }

        try (PrintWriter writer = new PrintWriter(fileChooser.getSelectedFile())) {
            for (Candidate candidate : candidates) {
                String line = String.format(
                        "%s,%s,%s,%tF,%s,%d",
                        candidate.lastName,
                        candidate.middleName,
                        candidate.firstName,
                        candidate.birthDate,
                        candidate.placeOfBirth,
                        candidate.popularityIndex
                );
                writer.println(line);
            }

            JOptionPane.showMessageDialog(this, "List saved successfully!");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error saving file: " + e.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
