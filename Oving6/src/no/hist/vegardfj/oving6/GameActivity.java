package no.hist.vegardfj.oving6;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class GameActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_ta_shansen, menu);
        return true;
    }
    
    public void onClickSendAnswer(View v) throws ClientProtocolException, IOException {
    }
}
