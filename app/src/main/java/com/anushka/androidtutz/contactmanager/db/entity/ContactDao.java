package com.anushka.androidtutz.contactmanager.db.entity;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

public interface ContactDao {
    @Insert
    public long addContact(Contact contact);

    @Update
    public void updateContact(Contact contact);

    @Delete
    public void deleteContact(Contact contact);

    @Query("select * from contacts")
    public List<Contact> getContact();

    @Query("select * from contacts where contact_id == :contactId")
    public Contact getContact(long contactId);
}
