package com.gmailapi;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;
import android.telephony.SmsManager;

import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;

import static androidx.core.content.ContextCompat.startActivity;

public class MyAsynk extends AsyncTask<Void, Void, Void> {

    javax.mail.Message msg;
    BodyPart bp;

    public static String no;
    public static String mesaj;

    public static int a;

    @Override
    protected Void doInBackground(Void... params) {

        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        try {
            Session session = Session.getInstance(props, null);
            Store store = session.getStore();
            store.connect("imap.gmail.com", "erkanm11t@gmail.com", "rzdakarmafnfxgmz");
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            msg = inbox.getMessage(inbox.getMessageCount());
            javax.mail.Address[] in = msg.getFrom();
            for (javax.mail.Address address : in) {
                System.out.println("FROM:" + address.toString());
            }
            Multipart mp = (Multipart) msg.getContent();
            bp = mp.getBodyPart(0);
            System.out.println("SENT DATE:" + msg.getSentDate());
            System.out.println("SUBJECT:" + msg.getSubject());
            System.out.println("CONTENT:" + bp.getContent());
            if (msg.getSubject().length() == 9 && msg.getSubject().startsWith("05") || msg.getSubject().startsWith("+90 05")|| msg.getSubject().startsWith("+90 5")|| msg.getSubject().startsWith("5") ){
                System.out.println("Bu bir numara");
                no = msg.getSubject();
                mesaj = bp.getContent().toString();
                a =1;
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra("no",msg.getSubject());
                intent.putExtra("mesaj",bp.getContent().toString());

            }
        } catch (Exception mex) {
            mex.printStackTrace();
        }
        return null;
    }
}
