// IBookManager.aidl
package com.wing.android.aidl;
import com.wing.android.aidl.Book;
import com.wing.android.aidl.IOnNewBookArrivedListener;
import com.wing.android.aidl.ITestServerCallClient;

// Declare any non-default types here with import statements
//build/generated/aidl_source_output_dir/debug/out/com.wing.android.messenger
interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
    void registerListener(IOnNewBookArrivedListener listener);
    void unRegisterListener(IOnNewBookArrivedListener listener);

    void setClientBinder(ITestServerCallClient binder);
}