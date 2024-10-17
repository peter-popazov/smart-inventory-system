package org.inventory.product.helpers;

import org.inventory.product.exceptions.UnauthorizedAccessException;

public class UserHelpers {

    public static void validateUser(String userId, Integer productUserId) {
        if (productUserId != Integer.parseInt(userId)) {
            throw new UnauthorizedAccessException("User cannot perform actions due to insufficient permissions");
        }
    }
}
