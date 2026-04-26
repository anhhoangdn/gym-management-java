package controller;

import util.Operation;
import view.PackageView;

public class DeletePackage implements Operation {

    private PackageView packageView;

    public DeletePackage() {
        // TODO: initialize PackageView and any required dependencies
        this.packageView = new PackageView();
    }

    @Override
    public void execute() {
        // TODO: display the package list, allow the admin to select a package,
        //       confirm deletion, and remove the package from the database
    }
}
