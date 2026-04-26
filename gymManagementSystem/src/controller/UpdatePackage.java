package controller;

import util.Operation;
import view.PackageView;

public class UpdatePackage implements Operation {

    private PackageView packageView;

    public UpdatePackage() {
        // TODO: initialize PackageView and any required dependencies
        this.packageView = new PackageView();
    }

    @Override
    public void execute() {
        // TODO: display the package list, allow the admin to select a package,
        //       show the edit package form, collect updated data,
        //       validate input, and update the package record in the database
    }
}
