package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

public class PackageView {

    private JFrame frame;
    private JPanel panel;
    private JTable table;

    public PackageView() {
        // TODO: initialize Swing components
    }

    public void showPackageList() {
        // TODO: fetch package data and display it in a JTable within a JFrame
    }

    public void showAddPackageForm() {
        // TODO: display a form with input fields for adding a new package
        //       Fields: packageName, duration, price, description, status
    }

    public void showEditPackageForm() {
        // TODO: display a pre-filled form with existing package data for editing
        //       Fields: packageName, duration, price, description, status
    }
}
