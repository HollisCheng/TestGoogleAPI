package google.hollischeng.com.googleapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.NativeExpressAdView;

public class MainActivity extends AppCompatActivity {

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private Button BtnMap, BtnPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, getString(R.string.application_id_code));

        BtnMap = (Button) findViewById(R.id.BtnMap);
        BtnPlay = (Button) findViewById(R.id.BtnPlay);

        BtnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map();
            }
        });

        BtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
            }
        });


        mInterstitialAd = newInterstitialAd();

        requestNewInterstitial();

        mAdView = (AdView) findViewById(R.id.ad_view);

        AdRequest adRequest = new AdRequest.Builder().build();

        mAdView.loadAd(adRequest);

        NativeExpressAdView adView = (NativeExpressAdView) findViewById(R.id.adView);
        if (adView != null) {
            adView.loadAd(new AdRequest.Builder().build());
        }
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder().build();

        mInterstitialAd.loadAd(adRequest);
    }

    public void map() {
        Log.e("MA", "Google Map");
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void play() {
        Log.e("MA", "Play");
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            Log.e("MA", "Ad show");
            mInterstitialAd.show();
        } else {
            Log.e("MA", "requestNewInterstitial");
            mInterstitialAd = newInterstitialAd();
            requestNewInterstitial();
        }

    }

    private InterstitialAd newInterstitialAd() {
        InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Log.e("MA", "onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.e("MA", "onAdFailedToLoad");
            }

            @Override
            public void onAdClosed() {
                Log.e("MA", "onAdClosed");
                play();
            }

            @Override
            public void onAdLeftApplication() {
                Log.e("MA", "onAdLeftApplication");
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                Log.e("MA", "onAdOpened");
                super.onAdOpened();
            }

        });
        return interstitialAd;
    }

    /**
     * Called when leaving the activity
     */
    @Override
    public void onPause() {
        Log.e("MA", "onPause");
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    /**
     * Called when returning to the activity
     */
    @Override
    public void onResume() {
        super.onResume();
        Log.e("MA", "onResume");
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    /**
     * Called before the activity is destroyed
     */
    @Override
    public void onDestroy() {
        Log.e("MA", "onDestroy");
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
}
