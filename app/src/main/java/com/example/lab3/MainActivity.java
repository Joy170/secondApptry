package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button website = (Button) findViewById(R.id.website);
        website.setOnClickListener(this);

        final Button dial = (Button) findViewById(R.id.dial);
        dial.setOnClickListener(this);

        final Button contact = (Button) findViewById(R.id.contact);
        contact.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        EditText text = (EditText)findViewById(R.id.editText);
        String typed = text.getText().toString();//sets input to be equal to what user types in
        switch(v.getId()) {
            case R.id.website:
                if(typed.contains ("https://")){
                Intent intent = new Intent (MainActivity.this, BrowserActivity.class);
                intent.putExtra("dataKey", typed); //according to what has been typed in
                startActivity(intent);
            }
             else{
                    Toast.makeText(getApplicationContext(), "please add https://", Toast.LENGTH_SHORT).show();
                }
                break;
        case R.id.dial:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + typed));
                startActivity(intent);
                break;
        case R.id.contact:
                Intent findNumber = new Intent(Intent.ACTION_PICK);
                findNumber.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(findNumber, 1);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent i) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            getContactNumber(i);
        }
    }

    private void getContactNumber(Intent intent){
        Uri contactUri = intent.getData(); // get data from intent
        String[] projection = new String[]{ // create a search filter for all values we need by putting their types into an array
                ContactsContract.CommonDataKinds.Phone.NUMBER // we only use 1 here - the phone number
        };
        // run a query on the SQLite Database containing all contacts using the parameters from above, ignore the last 3 nulls for now
        Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
        // If the cursor returned is valid, get the phone number
        if (cursor != null && cursor.moveToFirst()) {
            // since we will only have 1 entry for the phone number, no need to loop through the entire table, just get the FIRST row
            int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER); // find out what the index for the phone number column is
            String number = cursor.getString(numberIndex); // use this index to get the string value from the column in the current row
            ((EditText)findViewById(R.id.editText)).setText(number); // set the text of the input field to the phone number string
        }
    }
}
