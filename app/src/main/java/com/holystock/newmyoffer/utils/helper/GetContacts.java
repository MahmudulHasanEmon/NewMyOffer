package com.holystock.newmyoffer.utils.helper;

import static com.holystock.newmyoffer.utils.Helper.isBangladeshiMobile;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import com.holystock.newmyoffer.model.Contact;

import java.text.Collator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class GetContacts {

    private static final String TAG = "GetContacts";

    private static class ContactData {

        String name;
        String image;
        Set<String> phones;

        ContactData(String name, String image) {
            this.name = name;
            this.image = image;
            this.phones = new HashSet<>();
        }
    }

    public static ArrayList<Contact> getContacts(Context context) {

        ArrayList<Contact> contacts = new ArrayList<>();

        Map<String, ContactData> contactMap = new LinkedHashMap<>();

        Cursor cursor = null;

        try {

            cursor = context.getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    new String[]{
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY,
                            ContactsContract.CommonDataKinds.Phone.NUMBER,
                            ContactsContract.CommonDataKinds.Phone.PHOTO_URI,
                            ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI
                    },
                    null,
                    null,
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY + " COLLATE LOCALIZED ASC"
            );

            if (cursor == null) {
                return contacts;
            }

            int nameIndex = cursor.getColumnIndexOrThrow(
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY
            );

            int phoneIndex = cursor.getColumnIndexOrThrow(
                    ContactsContract.CommonDataKinds.Phone.NUMBER
            );

            int photoIndex = cursor.getColumnIndexOrThrow(
                    ContactsContract.CommonDataKinds.Phone.PHOTO_URI
            );

            int thumbIndex = cursor.getColumnIndexOrThrow(
                    ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI
            );

            while (cursor.moveToNext()) {

                String name = cursor.getString(nameIndex);
                String phone = cursor.getString(phoneIndex);

                if (name == null || name.trim().isEmpty()
                        || phone == null || phone.trim().isEmpty()) {
                    continue;
                }

                phone = normalizePhone(phone);

                if (!isBangladeshiMobile(phone)) {
                    continue;
                }

                String photoUri = cursor.getString(photoIndex);
                String thumbUri = cursor.getString(thumbIndex);

                String image =
                        thumbUri != null && !thumbUri.isEmpty()
                                ? thumbUri
                                : photoUri;

                ContactData data = contactMap.get(name);

                if (data == null) {

                    data = new ContactData(name, image);

                    contactMap.put(name, data);
                }

                // duplicate number avoid
                if (!data.phones.contains(phone)) {
                    data.phones.add(phone);
                }
            }

            for (ContactData data : contactMap.values()) {

                contacts.add(
                        new Contact(
                                data.name,
                                new ArrayList<>(data.phones),
                                data.image
                        )
                );
            }

            Collator collator = Collator.getInstance(
                    java.util.Locale.getDefault()
            );

            collator.setStrength(Collator.PRIMARY);

            Collator banglaCollator =
                    Collator.getInstance(
                            new Locale("bn", "BD")
                    );

            contacts.sort((c1, c2) -> {

                String n1 =
                        c1.getName() == null
                                ? ""
                                : c1.getName().trim();

                String n2 =
                        c2.getName() == null
                                ? ""
                                : c2.getName().trim();

                int p1 = getPriority(n1);
                int p2 = getPriority(n2);

                // English -> Bangla -> Number -> Others
                if (p1 != p2) {
                    return Integer.compare(p1, p2);
                }

                switch (p1) {

                    // English A-Z
                    case 0:
                        return n1.compareToIgnoreCase(n2);

                    // Bangla অ-হ
                    case 1:
                        return banglaCollator.compare(n1, n2);

                    // Numbers 0-9
                    case 2:
                        return compareNumericNames(n1, n2);

                    default:
                        return n1.compareToIgnoreCase(n2);
                }
            });

            Log.d(TAG, "Total Contacts : " + contacts.size());

        } catch (Exception e) {

            Log.e(TAG, "Error loading contacts", e);

        } finally {

            if (cursor != null) {
                cursor.close();
            }
        }

        return contacts;
    }

    private static int getPriority(String name) {

        if (name == null || name.isEmpty()) {
            return 3;
        }

        char first = name.charAt(0);

        // English A-Z
        if ((first >= 'A' && first <= 'Z')
                || (first >= 'a' && first <= 'z')) {
            return 0;
        }

        // Bangla Unicode Range
        if (first >= '\u0980'
                && first <= '\u09FF') {
            return 1;
        }

        // Number 0-9
        if (Character.isDigit(first)) {
            return 2;
        }

        // Others
        return 3;
    }

    private static int compareNumericNames(
            String n1,
            String n2
    ) {

        try {

            String d1 =
                    n1.replaceAll("[^0-9]", "");

            String d2 =
                    n2.replaceAll("[^0-9]", "");

            if (!d1.isEmpty()
                    && !d2.isEmpty()) {

                long v1 = Long.parseLong(d1);
                long v2 = Long.parseLong(d2);

                return Long.compare(v1, v2);
            }

        } catch (Exception ignored) {
        }

        return n1.compareToIgnoreCase(n2);
    }

    private static String normalizePhone(String phone) {

        if (phone == null) {
            return "";
        }

        phone = phone.replaceAll("[^0-9+]", "");

        if (phone.startsWith("+880")) {

            phone = "0" + phone.substring(4);

        } else if (phone.startsWith("880")) {

            phone = "0" + phone.substring(3);
        }

        return phone;
    }

    public static ArrayList<Contact> filterContacts(
            ArrayList<Contact> contacts,
            String query
    ) {

        ArrayList<Contact> result = new ArrayList<>();

        if (contacts == null) {
            return result;
        }

        if (query == null || query.trim().isEmpty()) {
            result.addAll(contacts);
            return result;
        }

        query = query.toLowerCase().trim();

        for (Contact contact : contacts) {

            boolean matched =
                    contact.getName()
                            .toLowerCase()
                            .contains(query);

            if (!matched) {

                for (String phone : contact.getPhones()) {

                    if (phone.contains(query)) {

                        matched = true;
                        break;
                    }
                }
            }

            if (matched) {
                result.add(contact);
            }
        }

        return result;
    }

    public static Contact findContactByNumber(
            ArrayList<Contact> contacts,
            String number
    ) {

        if (contacts == null || number == null) {
            return null;
        }

        number = normalizePhone(number);

        for (Contact contact : contacts) {

            for (String phone : contact.getPhones()) {

                if (normalizePhone(phone)
                        .equals(number)) {

                    return contact;
                }
            }
        }

        return null;
    }

    public static boolean hasContact(
            ArrayList<Contact> contacts,
            String number
    ) {

        return findContactByNumber(
                contacts,
                number
        ) != null;
    }
}