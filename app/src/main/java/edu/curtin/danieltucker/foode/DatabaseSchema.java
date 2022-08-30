package edu.curtin.danieltucker.foode;

public class DatabaseSchema {
    public static class UsersTable {
        public static final String NAME = "users";

        public static class Cols {
            public static final String ID = "userId";
            public static final String FIRST_NAME = "firstName";
            public static final String LAST_NAME = "lastName";
            public static final String EMAIL = "email";
            public static final String PASSWORD = "password";
        }
    }

    public static class RestaurantTable {
        public static final String NAME = "restaurant";

        public static final class Cols {
            public static final String ID = "storeCode";
            public static final String NAME = "name";
        }
    }

    public static class ResourceTable {
        public static final String NAME = "resource";

        public static final class Cols {
            public static final String REF = "ref";
            public static final String RESTAURANT = "restaurant";
        }
    }

    public static class FoodTable {
        public static final String NAME = "food";

        public static final class Cols {
            public static final String ID = "itemCode";
            public static final String RESTAURANT = "storeCode";
            public static final String RESOURCE = "resource";
            public static final String PRICE = "price";
            public static final String DESC = "desc";
        }
    }

    public static class OrderTable {
        public static final String NAME = "order";

        public static final class Cols {
            public static final String ID = "orderId";
            public static final String USER = "userId";
            public static final String ITEM_CODE = "itemCode";
            public static final String QUANTITY = "quantity";
        }
    }
}
