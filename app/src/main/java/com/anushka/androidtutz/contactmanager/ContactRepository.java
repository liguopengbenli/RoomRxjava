package com.anushka.androidtutz.contactmanager;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.anushka.androidtutz.contactmanager.db.ContactsAppDatabase;
import com.anushka.androidtutz.contactmanager.db.entity.Contact;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

public class ContactRepository {

    private Application application;
    private ContactsAppDatabase contactsAppDatabase;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<List<Contact>> contactLiveData = new MutableLiveData<>();
    private long rowIdOfItemInserted;




    public ContactRepository(Application application){
            this.application = application;

        contactsAppDatabase = Room.databaseBuilder(application.getApplicationContext(), ContactsAppDatabase.class, "ContactDB")
                .build();

        compositeDisposable.add(
                contactsAppDatabase.getContactDAO().getContact()
                        .subscribeOn(Schedulers.computation())
                        .observeOn(Schedulers.newThread())       //AndroidSchedulers.maintThread but not compatible in RxAndroid3
                        .subscribe(new Consumer<List<Contact>>() {
                                       @Override
                                       public void accept(List<Contact> contacts) throws Exception {
                                            contactLiveData.postValue(contacts);
                                       }
                                   }, new Consumer<Throwable>() {
                                       @Override
                                       public void accept(Throwable throwable) throws Exception {

                                       }
                                   }
                        ));
    }

    public void createContact(final String name,  final String email) {

        compositeDisposable.add(
                Completable.fromAction(new Action() {
                    @Override
                    public void run() throws Exception {
                        rowIdOfItemInserted = contactsAppDatabase.getContactDAO().addContact(new Contact(0, name, email));
                    }
                }).subscribeOn(Schedulers.io()).observeOn(Schedulers.newThread()).subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Toast.makeText(application.getApplicationContext(), "contact add successfully " + rowIdOfItemInserted, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(application.getApplicationContext(), "Error occurred", Toast.LENGTH_LONG).show();

                    }
                }));
    }


    private void updateContact(final Contact contact) {
        compositeDisposable.add(
                Completable.fromAction(new Action() {
                    @Override
                    public void run() throws Exception {
                        contactsAppDatabase.getContactDAO().updateContact(contact);
                    }
                }).subscribeOn(Schedulers.io()).observeOn(Schedulers.newThread()).subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Toast.makeText(application.getApplicationContext(), "contact updated successfully " , Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(application.getApplicationContext(), "Error occurred", Toast.LENGTH_LONG).show();

                    }
                }));
    }

    private void deleteContact(final Contact contact) {

        compositeDisposable.add(
                Completable.fromAction(new Action() {
                    @Override
                    public void run() throws Exception {
                        contactsAppDatabase.getContactDAO().deleteContact(contact);
                    }
                }).subscribeOn(Schedulers.io()).observeOn(Schedulers.newThread()).subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Toast.makeText(application.getApplicationContext(), "contact delete successfully " , Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(application.getApplicationContext(), "Error occurred", Toast.LENGTH_LONG).show();

                    }
                }));
    }

    public MutableLiveData<List<Contact>> getContactLiveData() {
        return contactLiveData;
    }


    public void clear(){
        compositeDisposable.clear();
    }

}
