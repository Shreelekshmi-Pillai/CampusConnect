
package util;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class UITheme {

    public static final Color FOREST =
            new Color(23, 77, 59);

    public static final Color FOREST_LIGHT =
            new Color(35, 102, 78);

    public static final Color CREAM =
            new Color(247, 245, 238);

    public static final Color WHITE =
            Color.WHITE;

    public static final Color TEXT =
            new Color(30, 43, 38);

    public static final Color GOLD =
            new Color(182, 138, 67);

    public static final Font TITLE_FONT =
            new Font("Segoe UI", Font.BOLD, 24);

    public static final Font BODY_FONT =
            new Font("Segoe UI", Font.PLAIN, 14);

    private UITheme() {
    }

    public static void applyTheme() {
        UIManager.put("Panel.background", CREAM);
        UIManager.put("OptionPane.background", CREAM);
        UIManager.put("Button.font", BODY_FONT);
        UIManager.put("Label.font", BODY_FONT);
        UIManager.put("TextField.font", BODY_FONT);
        UIManager.put("PasswordField.font", BODY_FONT);
    }

    public static void styleButton(JButton button) {
        button.setBackground(FOREST);
        button.setForeground(WHITE);
        button.setFont(BODY_FONT);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(10, 18, 10, 18));
        button.setOpaque(true);
    }

    public static void styleTitle(JLabel label) {
        label.setFont(TITLE_FONT);
        label.setForeground(FOREST);
    }

    public static void styleTextField(JTextField field) {
        field.setFont(BODY_FONT);
        field.setForeground(TEXT);
        field.setBackground(WHITE);
    }

    public static void stylePasswordField(JPasswordField field) {
        field.setFont(BODY_FONT);
        field.setForeground(TEXT);
        field.setBackground(WHITE);
    }
}
