package util;

import model.User;

public final class UserDisplayHelper {

    private UserDisplayHelper() {}

    public static String buildMemberLabel(User user, int userId) {
        if (user == null) {
            return "ID = " + userId;
        }
        String firstName = user.getFirstName() != null ? user.getFirstName().trim() : "";
        String lastName = user.getLastName() != null ? user.getLastName().trim() : "";
        String name = (firstName + " " + lastName).trim();
        if (name.isEmpty()) {
            return "ID = " + userId;
        }
        return name + " (ID = " + userId + ")";
    }
}
