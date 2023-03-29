package gutierrez.galan.german.mygps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LocationListener
{
    private Button button1;
    private Button button2;
    private TextView textView1;
    private TextView textView2;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = findViewById(R.id.capturarId);
        button2 = findViewById(R.id.detenerId);
        textView1 = findViewById(R.id.latitudId);
        textView2 = findViewById(R.id.longitudId);
        button2.setEnabled( false );
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startGps();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                stopGps();
            }
        });
    }

    private void stopGps( )
    {
        locationManager.removeUpdates( this );
        locationManager = null;
        button1.setEnabled( true );
        button2.setEnabled( false );
    }

    private void startGps()
    {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions( new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION} , 3);
            return;
        }
        if( locationManager == null )
        {
            locationManager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this );
            button1.setEnabled( false );
            button2.setEnabled( false );
            textView1.setText( "" );
            textView2.setText( "" );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults )
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case 3:
                if( android.Manifest.permission.ACCESS_FINE_LOCATION.equals( permissions[ 0 ]) && grantResults[ 0 ] == 0 )
                {
                    startGps();
                    return;
                }
                break;
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location)
    {
        textView1.setText("" + location.getLatitude( ) );
        textView2.setText("" + location.getLongitude( ) );
        if( !button2.isEnabled( ) )
        {
            button2.setEnabled( true );
        }
    }

    @Override
    public void onProviderEnabled(@NonNull String provider)
    {
    }

    @Override
    public void onProviderDisabled(@NonNull String provider)
    {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {
    }


}