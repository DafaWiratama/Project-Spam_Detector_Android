package tech.jayamakmurdigital.spamdetector.utils

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.Telephony
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import tech.jayamakmurdigital.spamdetector.model.SMS
import tech.jayamakmurdigital.spamdetector.model.SMSType


class SMSLoader(private val context: Context) : LoaderManager.LoaderCallbacks<Cursor> {
    private val _messages = MutableLiveData<ArrayList<SMS>>()
    val messages: LiveData<ArrayList<SMS>> get() = _messages

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        val projection = arrayOf(Telephony.Sms._ID, Telephony.Sms.ADDRESS, Telephony.Sms.BODY, Telephony.Sms.READ, Telephony.Sms.DATE, Telephony.Sms.TYPE)
        val uri = Uri.parse("content://sms/")
        return CursorLoader(context, uri, projection, null, null, null)
    }

    override fun onLoadFinished(loader: Loader<Cursor>, cursor: Cursor?) {
        val list = ArrayList<SMS>()
        var sms: SMS
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                for (i in 0 until cursor.count) {
                    sms = SMS()
                    sms.id = cursor.getString(cursor.getColumnIndexOrThrow("_id"))
                    sms.name = cursor.getString(cursor.getColumnIndexOrThrow("address"))
                    sms.text = cursor.getString(cursor.getColumnIndexOrThrow("body"))
                    sms.read = cursor.getInt(cursor.getColumnIndex("read")) == 1
                    sms.time = cursor.getLong(cursor.getColumnIndexOrThrow("date"))
                    if (cursor.getString(cursor.getColumnIndexOrThrow("type")).contains("1")) sms.type = SMSType.INBOX
                    else sms.type = SMSType.SEND
                    if (sms.text != "") list.add(sms)
                    cursor.moveToNext()
                }
            }
        }
        _messages.value = list
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
    }
}

