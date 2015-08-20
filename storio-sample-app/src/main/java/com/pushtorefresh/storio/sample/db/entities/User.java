package com.pushtorefresh.storio.sample.db.entities;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.pushtorefresh.storio.sample.db.tables.UsersTable;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

// This annotation will trigger annotation processor
// Which will generate type mapping code in compile time,
// You just need to link it in your code.
@StorIOSQLiteType(table = UsersTable.TABLE)
public class User {

    /**
     * If object was not inserted into db, id will be null
     */
    @Nullable
    @StorIOSQLiteColumn(name = UsersTable.COLUMN_ID, key = true)
    Long id;

    // For example: "artem_zin", without "@".
    @NonNull
    @StorIOSQLiteColumn(name = UsersTable.COLUMN_NICK)
    String nick;

    // leave default constructor for AutoGenerated code!
    User() {

    }

    @NonNull
    public static User newUser(@Nullable Long id, @NonNull String nick) {
        User user = new User();
        user.id = id;
        user.nick = nick;
        return user;
    }
}
