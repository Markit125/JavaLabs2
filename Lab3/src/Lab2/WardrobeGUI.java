package Lab2;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WardrobeGUI extends JFrame {
    private Wardrobe wardrobe;
    private final JPanel clothesPanel;
    private final JComboBox<Clothes.Size> sizeBox;
    private final JComboBox<Clothes.Color> colorBox;
    private final JComboBox<String> typeBox;
    private final JComboBox<String> extraBox;
    private final JLabel extraLabel;
    private final JTextField searchResult;
    private final JComboBox<Clothes.Size> searchSizeBox;
    private final JComboBox<Clothes.Color> searchColorBox;

    private final List<Clothes> clothesStorage = new ArrayList<>();
    private final List<JButton> removeButtons = new ArrayList<>();

    public WardrobeGUI() {
        setTitle("Wardrobe Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        clothesPanel = new JPanel();
        clothesPanel.setLayout(new BoxLayout(clothesPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(clothesPanel);
        add(scrollPane, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(11, 2));

        JButton createButton = new JButton("Create Wardrobe with Current Clothes");
        controlPanel.add(createButton);
        createButton.addActionListener(_ -> createWardrobe());

        JButton deleteWardrobeButton = new JButton("Delete Wardrobe");
        controlPanel.add(deleteWardrobeButton);
        deleteWardrobeButton.addActionListener(_ -> deleteWardrobe());

        controlPanel.add(new JLabel("Clothing Type:"));
        typeBox = new JComboBox<>(new String[] {"Shirt", "Coat", "Jeans"});
        controlPanel.add(typeBox);

        controlPanel.add(new JLabel("Size:"));
        sizeBox = new JComboBox<>(Clothes.Size.values());
        controlPanel.add(sizeBox);

        controlPanel.add(new JLabel("Color:"));
        colorBox = new JComboBox<>(Clothes.Color.values());
        controlPanel.add(colorBox);

        extraLabel = new JLabel("Extra:");
        controlPanel.add(extraLabel);
        extraBox = new JComboBox<>();
        controlPanel.add(extraBox);
        typeBox.addActionListener(_ -> updateExtra());
        updateExtra();

        JButton addButton = new JButton("Add Clothing");
        controlPanel.add(addButton);
        addButton.addActionListener(_ -> addClothing());

        controlPanel.add(new JLabel(""));
        controlPanel.add(new JLabel("Search by Size & Color"));
        controlPanel.add(new JLabel(""));

        controlPanel.add(new JLabel("Size:"));
        searchSizeBox = new JComboBox<>(Clothes.Size.values());
        controlPanel.add(searchSizeBox);

        controlPanel.add(new JLabel("Color:"));
        searchColorBox = new JComboBox<>(Clothes.Color.values());
        controlPanel.add(searchColorBox);

        JButton searchButton = new JButton("Search");
        controlPanel.add(searchButton);
        searchResult = new JTextField();
        searchResult.setEditable(false);
        controlPanel.add(searchResult);

        searchButton.addActionListener(_ -> searchClothes());

        add(controlPanel, BorderLayout.EAST);
    }

    private void createWardrobe() {
        if (clothesStorage.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Add clothes before creating a wardrobe.");
            return;
        }
        wardrobe = new Wardrobe(clothesStorage.size(), clothesStorage.toArray(new Clothes[0]));
        JOptionPane.showMessageDialog(this, "Wardrobe created with " + clothesStorage.size() + " items.");

        for (JButton removeButton : removeButtons) {
            removeButton.setEnabled(false);
        }
    }

    private void deleteWardrobe() {
        wardrobe = null;
        clothesStorage.clear();
        clothesPanel.removeAll();
        clothesPanel.revalidate();
        clothesPanel.repaint();
        removeButtons.clear();
        JOptionPane.showMessageDialog(this, "Wardrobe and all clothes deleted.");
    }

    private void updateExtra() {
        String type = (String) typeBox.getSelectedItem();
        extraBox.removeAllItems();
        switch (type) {
            case "Shirt" -> {
                for (Shirt.SleeveLength s : Shirt.SleeveLength.values()) {
                    extraBox.addItem(s.name());
                }
                extraLabel.setText("Sleeve length:");
            }
            case "Coat" -> {
                for (Coat.Material m : Coat.Material.values()) {
                    extraBox.addItem(m.name());
                }
                extraLabel.setText("Material:");
            }
            case "Jeans" -> {
                for (Jeans.FitType f : Jeans.FitType.values()) {
                    extraBox.addItem(f.name());
                }
                extraLabel.setText("Fit type:");
            }
            case null -> {}
            default -> throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    private void addClothing() {
        Clothes.Size size = (Clothes.Size) sizeBox.getSelectedItem();
        Clothes.Color color = (Clothes.Color) colorBox.getSelectedItem();
        String type = (String) typeBox.getSelectedItem();
        String extra = (String) extraBox.getSelectedItem();

        if (type == null) {
            JOptionPane.showMessageDialog(this, "Type of the clothe is null.");
            return;
        }

        Clothes clothing = switch (type) {
            case "Shirt" -> new Shirt(size, color, Shirt.SleeveLength.valueOf(extra));
            case "Coat" -> new Coat(size, color, Coat.Material.valueOf(extra));
            case "Jeans" -> new Jeans(size, color, Jeans.FitType.valueOf(extra));
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
        clothesStorage.add(clothing);
        addClothingBox(clothing);
    }

    private void addClothingBox(Clothes clothing) {
        JPanel itemPanel = new JPanel(new BorderLayout());
        JTextArea text = new JTextArea(clothing.toString());
        text.setEditable(false);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        itemPanel.add(text, BorderLayout.CENTER);

        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(e -> {
            clothesPanel.remove(itemPanel);
            clothesPanel.revalidate();
            clothesPanel.repaint();
            clothesStorage.remove(clothing);
            removeButtons.remove(removeButton);
        });
        itemPanel.add(removeButton, BorderLayout.EAST);
        itemPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        clothesPanel.add(itemPanel);
        clothesPanel.revalidate();

        removeButtons.add(removeButton);
    }

    private void searchClothes() {
        if (wardrobe == null) {
            JOptionPane.showMessageDialog(this, "Create a wardrobe first.");
            return;
        }
        Clothes.Size size = (Clothes.Size) searchSizeBox.getSelectedItem();
        Clothes.Color color = (Clothes.Color) searchColorBox.getSelectedItem();
        int count = wardrobe.countFitClothes(size, color);
        searchResult.setText(String.valueOf(count));
    }
}
