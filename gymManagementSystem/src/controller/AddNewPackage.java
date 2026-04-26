package controller;

import util.Operation;
import view.PackageView;

public class AddNewPackage implements Operation {

    private PackageView packageView;

    public AddNewPackage() {
        // TODO: initialize PackageView and any required dependencies
        this.packageView = new PackageView();
    }

    @Override
    public void execute() {
        // TODO: display the add package form, collect input,
        //       validate data, and save the new package to the database
    }
}
